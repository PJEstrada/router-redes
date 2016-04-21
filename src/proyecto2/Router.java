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
    
    public void showTable(){
    
    
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
            for(Node n: this.nodes){
                String message =  "From:"+Proyecto2.nodeName+"\n" +"Type:DV\n";
                
                
                
                Thread threadSender = new Thread(new Sender(n,message,1,this));
                threadSender.start();
            }
            
        
        }
        else{
            System.out.println("Router: Sending Keep Alive ");
            String check = "From:"+Proyecto2.nodeName+"\n" +
                            "Type:KeepAlive";
            for(Node n: nodes){
            
            }
            
        }
    
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
    public void updateTable(ArrayList<String> newDistanceVectors){
        System.out.println("Updating DV Table");
        for(String line: newDistanceVectors){
            
        
        
        }

    
    }
}
