/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pablo
 */
public class Router {
    
    JTable tableRouter;
    ArrayList<Node> nodes;
    ArrayList<ArrayList<Integer>> routingTable;
    ArrayList<String> tableUpdates;
    DefaultTableModel model;
    
    public Router(ArrayList<Node> nodes){
        
        tableUpdates = new ArrayList<String>();
        this.nodes = nodes;
        //Inicializando tabla GUI
        Object columnNames1[] = { "Node A", "Node B","Node C"};
        tableRouter = new JTable(nodes.size()+1,nodes.size());
        model = (DefaultTableModel) tableRouter.getModel();
        model.removeRow(model.getRowCount()-1);
        ArrayList<String> colName = new ArrayList<String>();
        colName.add("Destination");
        for(Node n: nodes){
            colName.add(n.id);
        }
        model.setColumnIdentifiers(colName.toArray());
        //Inicializamos tabla con infinitos
        this.routingTable = new ArrayList<ArrayList<Integer>>();
        int i = 0;
        for(Node n: nodes){
            int j =0;
            ArrayList<Integer> row = new ArrayList<Integer>();
            for(Node n1:nodes){
                row.add(99);
                model.setValueAt(99, i, j+1);
                j++;
            }
            i++;
            this.routingTable.add(row);
        }
        //Seteando header de filas
        i=0;
        for(Node n: nodes){
            model.setValueAt(n.id,i , 0);
            i++;
        }
        for(Node n:this.nodes){
            n.setRouter(this);
            
        }
        //Creamos conexiones
        this.createConnectionsWithNodes();
        //Seteamos costos de enlaces directos a nodos vecinos
    
    }

    public void createConnectionsWithNodes(){
        for(Node n: this.nodes){
            

            if(!n.senderThread.isAlive()&&n.isVecino){
                System.out.println("ROUTER: Creating connection with node: "+n.id);
                String hello = "From:"+Proyecto2.nodeName+"\n" +"Type:HELLO\n";
                n.senderThread.stop();
                n.nodeSender.message = hello;
                n.nodeSender.type = 1;
                n.senderThread = new Thread(n.nodeSender);
                n.senderThread.start();              
            }
            else{
                System.out.println("ROUTER: cannot send HELLO. Sender thread waiting for response,");
            }
            
                
                         
            
        }
        
    
    }
    
    public void createConnectionWithNode(Node n){
        
        if(!n.senderThread.isAlive()&&n.isVecino){
                System.out.println("ROUTER: Creating connection with node: "+n.id);
                String hello = "From:"+Proyecto2.nodeName+"\n" +"Type:HELLO\n";
                n.senderThread.stop();
                n.nodeSender.message = hello;
                n.nodeSender.type = 1;
                n.senderThread = new Thread(n.nodeSender);
                n.senderThread.start();   
        
        }
        else{
            System.out.println("ROUTER: cannot send HELLO. Sender thread waiting for response,");
        }

    }
    
    public int min(ArrayList<Integer> arr){
        int min = 99;
        for(int n:arr){
            if(n<min){
                min =n;
            }
        }
        return min;
    }
    
    public Node getNodeByTableIdRow(int id){
        for(Node n: this.nodes){
            if(n.tableIdRow==id){
                return n;
            }
        
        
        } 
        return null;
    }
    public Node getNodeByTableIdCol(int id){
        for(Node n: this.nodes){
            if(n.tableIdCols==id){
                return n;
            }
        
        
        } 
        return null;
    }
    public ArrayList<String> constructDVMessage(){
        ArrayList<String> result = new ArrayList<String>();
        int i = 0;
        for(ArrayList<Integer> row: this.routingTable){
            int num = min(row);
            if(num != 99){
                Node n = this.getNodeByTableIdRow(i);
                String msg =  n.id+":"+num;
                result.add(msg);
            
            }
            i++;
        }
        return result;
    }
    
    public void sendInitialDV(Node node){
            ArrayList<String> updates = constructDVMessage();
            System.out.println("Router: Sending Initial DV to Node: "+node.id);
            String message =  "From:"+Proyecto2.nodeName+"\n" +"Type:DV\n"+"Len:"+updates.size()+"\n";
            for(String s: updates){
                message += s+"\n";
            
            }                     
            if(node.isUpSender){
                if(!node.senderThread.isAlive()&&node.isVecino){
                    System.out.println("ROUTER: DV Message to send:"+message);
                    node.senderThread.stop();
                    node.nodeSender.message = message;
                    node.nodeSender.type = 4;
                    node.senderThread = new Thread(node.nodeSender);
                    node.senderThread.start();
                                      
                }

                else{
                    System.out.println("ROUTER: cannot send DV. Sender thread waiting for response,");
                }
            
            }
            else{
                this.createConnectionWithNode(node);
            }    
    
    
    }
    
    public ArrayList<String> getVecinos(){
        ArrayList<String> result = new ArrayList<String>();
        for(Node n: this.nodes){
            if(n.isVecino){
                result.add(n.id);
            }
        
        }
        return result;
    
    }
    public synchronized void setValue(int i, int j, int value){
        if(value>99){value = 99;}
        this.routingTable.get(i).set(j, value);
        model.setValueAt(value, i, j+1);
        Node n1 = this.getNodeByTableIdRow(i);
        Node n2 = this.getNodeByTableIdCol(j);
        Proyecto2.ft.recalculateTable();
        
    
    }
    public int getValue(int i , int j){
    
        return this.routingTable.get(i).get(j);
    }
    
    public Node getNode(String id){
        for(Node n: this.nodes){
            if(n.id.equalsIgnoreCase(id)||n.ip.equalsIgnoreCase(id)){
                return n;
            }
        
        }

        return null;
    }
    
    
    public void sendNewDistanceVectors(){
        System.out.println("Router: Sending DV to All Nodes... ");
        //Enviando DV iniciales si aun no se han enviado
        for(Node n: this.nodes){      
            if(n.initialDV==false&&n.isVecino){
                this.sendInitialDV(n);
            }
        }
        
        if(!tableUpdates.isEmpty()){
            
            String message =  "From:"+Proyecto2.nodeName+"\n" +"Type:DV\n"+"Len:"+this.tableUpdates.size()+"\n";
            for(String s: this.tableUpdates){
                message += s+"\n";
            
            }
            for(Node n: this.nodes){
                if(n.isUpSender){
                    if(!n.senderThread.isAlive()&&n.isVecino){
                        System.out.println("ROUTER: DV Message to send:"+message);
                        n.senderThread.stop();
                        n.nodeSender.message = message;
                        n.nodeSender.type = 2;
                        n.senderThread = new Thread(n.nodeSender);
                        n.senderThread.start();
                    }
                    else{
                        System.out.println("ROUTER: cannot send DV. Sender thread waiting for response,");
                    }
                }
                else{
                    this.createConnectionWithNode(n);
                }
            }
            
        
        }
        else{
           
            String check = "From:"+Proyecto2.nodeName+"\n" +
                            "Type:KeepAlive\n";
            for(Node n: nodes){
                //System.out.println("n.isSending="+n.isSending);
                //System.out.println("n.isUpSender="+n.isUpSender);
                if(!n.senderThread.isAlive()&&n.isVecino){
                   if(n.isUpSender){
                       System.out.println("Router: Sending Keep Alive to: "+n.id);
                       n.senderThread.stop();
                       n.nodeSender.message = check;
                       n.nodeSender.type = 3;
                       n.senderThread = new Thread(n.nodeSender);
                       n.senderThread.start();
                       
                       
                   }
                   else{
                       this.createConnectionWithNode(n);
                   }               
                }
                else{
                    System.out.println("ROUTER: cannot send Keep Alive. Sender thread waiting for response,");
                }

            
            }
            
        }
    
    }
    
    public void setCol(int colId, int value){
        int i = 0;
        for(ArrayList<Integer> a :this.routingTable){
            this.setValue(i, colId, value);
            i++;
        }
    
    }
    public void addNewNode(Node n,Node nodeFrom){
        n.isVecino = false;
        ArrayList<Integer> newRow = new ArrayList<Integer>();
        newRow.add(99);
        for(Node n1: this.nodes){
            newRow.add(99);
        
        
        }
        this.nodes.add(n);
        //Seteamos el header de la fila
        this.model.addRow(newRow.toArray());
        this.model.setValueAt(n.id,n.tableIdRow,0);

        newRow.remove(newRow.size()-1);
        this.routingTable.add(newRow);
        this.setValue(n.tableIdRow, nodeFrom.tableIdCols, n.cost);
    
    }
    
    public void addNewNeighborNode(Node n){
        n.isVecino = true;
        this.nodes.add(n);
        //Agregamos nueva fila
        ArrayList<Integer> newRow = new ArrayList<Integer>();
        for(Node n1: this.nodes){
            newRow.add(99);
            
        }
        for(ArrayList<Integer> a: this.routingTable ){
            //Agregamos columnas a cada fila
            a.add(99);
        
        }
        this.routingTable.add(newRow);
        //Seteamos el nuevo costo
        this.setValue(n.tableIdRow, n.tableIdCols, n.cost);
        
        this.model.addColumn(n.id);
        this.model.addRow(newRow.toArray());
    
    }
    
    public synchronized void checkKeepAlive(){
        
        for(Node n: nodes){
            if(n.isVecino==true){
                System.out.println("ROUTER: checking if "+n.id+" is alive...");
                if(n.keepAlive == true){
                    System.out.println("ROUTER: Node: "+n.id+" is up.");
                    n.keepAlive = false;

                }
                else{
                    System.out.println("ROUTER: Node: "+n.id+" dead. Closing socket...");
                    try {
                        //Cerramos el socket del nodo conectado
                        if(n.socket!=null){
                             n.socket.close();
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Seteamos el costo de enlace directo a 99
                    for(Node n4: this.nodes){
                        System.out.println("Node: "+n4.id+" Talbe idRow: "+n4.tableIdRow+"Tableid cols: "+n4.tableIdCols);
                    }
                    if(n.isVecino){
                        this.setCol(n.tableIdCols,99);
                    }
                    this.setValue(n.tableIdRow, n.tableIdCols, 99);
                    n.isUpListener = false;
                    n.isUpSender = false;
                }            
            }

        
        }    
        
    
    }
    public synchronized void updateTable(ArrayList<String> newDistanceVectors,Node nodeFrom){
        System.out.println("Updating DV Table");
        for(String line: newDistanceVectors){
            String[] data= line.split(":");
            System.out.println("Current Update:"+line);
            Node n = this.getNode(data[0]);
            if(data[0].equalsIgnoreCase(Proyecto2.nodeName)){
                continue;  //Si el nodo soy yo. Lo salto.
            }
            if(n==null){
                System.out.println("New Node Created: "+data[0]);
                int dv = Integer.parseInt(data[1]);
                dv = dv+nodeFrom.cost;
                //Agregamos nueva fila a la tabla
                Node newNode = new Node(data[0],dv,"No-IP",false,this);
                this.addNewNode(newNode, nodeFrom);
                this.tableUpdates.add(newNode.id+":"+newNode.cost);  //Cambio tambien en NodeConecction al recibir HELLO
            }
            
            else{
                System.out.println("Updating cost in col: "+nodeFrom.id+" Row:"+n.id);
                int oldCost = this.getValue(n.tableIdRow,nodeFrom.tableIdCols);
                int dv = Integer.parseInt(data[1]);
                if( (oldCost> (dv+nodeFrom.cost) )){
                    
                    System.out.println("UPDATE TABLE: "+(dv+nodeFrom.cost));
                    this.setValue(n.tableIdRow, nodeFrom.tableIdCols, dv+nodeFrom.cost);
                    this.tableUpdates.add(n.id+":"+dv+nodeFrom.cost);
                
                }   
                else if((dv+nodeFrom.cost)==99){
                    System.out.println("UPDATE TABLE: "+99);
                    this.setValue(n.tableIdRow, nodeFrom.tableIdCols, 99);
                    this.tableUpdates.add(n.id+":"+99);                
                }
            }
            
        
        
        }
        

    
    }
}
