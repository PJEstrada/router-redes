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
            colName.add(n.ip);
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
            model.setValueAt(n.ip,i , 0);
            i++;
        }
        //Creamos conexiones
        this.createConnectionsWithNodes();
        //Seteamos costos de enlaces directos a nodos vecinos
    
    }

    public void createConnectionsWithNodes(){
        for(Node n: this.nodes){
            String hello = "From:"+Proyecto2.nodeName+"\n" +"Type:HELLO\n";
            Thread threadSender = new Thread(new Sender(n,hello,1,this));
            threadSender.start();
        }
    
    }
    
    public void createConnectionWithNode(Node n){
            String hello = "From:"+Proyecto2.nodeName+"\n" +"Type:HELLO\n";
            Thread threadSender = new Thread(new Sender(n,hello,1,this));
            threadSender.start();
    }
    
    public void setValue(int i, int j, int value){
        
        this.routingTable.get(i).set(j, value);
        model.setValueAt(value, i, j+1);
    
    }
    public int getValue(int i , int j){
    
        return this.routingTable.get(i).get(j);
    }
    
    public Node getNode(String id){
        for(Node n: this.nodes){
            if(n.id.equals(id)||n.ip.equals(id)){
                return n;
            }
        
        }
        return null;
    }
    
    
    public void sendNewDistanceVectors(){
        
        if(!tableUpdates.isEmpty()){
            System.out.println("Router: Sending DV ");
            String message =  "From:"+Proyecto2.nodeName+"\n" +"Type:DV\n";
            for(String s: this.tableUpdates){
                message += s+"\n";
            
            }
            for(Node n: this.nodes){
                Thread threadSender = new Thread(new Sender(n,message,2,this));
                threadSender.start();
                
            }
            
        
        }
        else{
            System.out.println("Router: Sending Keep Alive ");
            String check = "From:"+Proyecto2.nodeName+"\n" +
                            "Type:KeepAlive\n";
            for(Node n: nodes){
                Thread threadSender = new Thread(new Sender(n,check,3,this));
                threadSender.start();            
            }
            
        }
    
    }
    
    public void addNewNode(Node n,Node nodeFrom){
        ArrayList<Integer> newRow = new ArrayList<Integer>();
        for(Node n1: this.nodes){
            newRow.add(99);
        
        
        }
        this.model.addRow(newRow.toArray());
        this.setValue(n.tableId, nodeFrom.tableId, n.cost);
    
    }
    
    public void addNewNeighborNode(Node n){
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
        this.setValue(n.tableId, n.tableId, n.cost);
        
        this.model.addColumn(n.id);
        this.model.addRow(newRow.toArray());
    
    }
    
    public void checkKeepAlive(){
        System.out.println("ROUTER: checking if nodes are alive...");
        for(Node n: nodes){
            if(n.keepAlive == true){
                n.keepAlive = false;
            
            }
            else{
                System.out.println("ROUTER: Node: "+n.ip+" dead. Closing socket...");
                try {
                    //Cerramos el socket del nodo conectado
                    n.socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Seteamos el costo de enlace directo a 99
                this.setValue(n.tableId, n.tableId, n.cost);
                n.isUpListener = false;
                n.isUpSender = false;
            }
        
        }    
        
    
    }
    public void updateTable(ArrayList<String> newDistanceVectors,Node nodeFrom){
        System.out.println("Updating DV Table");
        for(String line: newDistanceVectors){
            String[] data= line.split(":");
            Node n = this.getNode(data[0]);
            if(n==null){
                int dv = Integer.parseInt(data[1]);
                dv = dv+nodeFrom.cost;
                //Agregamos nueva fila a la tabla
                Node newNode = new Node(data[0],dv,"");
                this.addNewNode(newNode, nodeFrom);
            }
            
            else{
                int oldCost = this.getValue(n.tableId,n.tableId);
                int dv = Integer.parseInt(data[1]);
                if(oldCost> (dv+nodeFrom.cost)){
                    this.setValue(n.tableId, nodeFrom.tableId, dv+nodeFrom.cost);
                    this.tableUpdates.add(n.id+":"+dv+nodeFrom.cost);
                
                }
            
            }
            
        
        
        }

    
    }
}
