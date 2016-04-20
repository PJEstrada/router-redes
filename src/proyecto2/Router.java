/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.util.ArrayList;
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
    
    public Router(ArrayList<Node> nodes){
        this.nodes = nodes;
        //Inicializando tabla GUI
        Object columnNames1[] = { "Node A", "Node B","Node C"};
        tableRouter = new JTable(1,3);
        DefaultTableModel model = (DefaultTableModel) tableRouter.getModel();
        
        ArrayList<String> colName = new ArrayList<String>();
        for(Node n: nodes){
            colName.add(n.id);
        }
        model.setColumnIdentifiers(colName.toArray());
        //Inicializamos tabla con infinitos
        this.routingTable = new ArrayList<ArrayList<Integer>>();
        for(Node n: nodes){
            ArrayList<Integer> row = new ArrayList<Integer>();
            for(Node n1:nodes){
                row.add(99);
            
            }
            this.routingTable.add(row);
        }
        
        //Seteamos costos de enlaces directos a nodos vecinos
        for(int i = 0; i<nodes.size();i++){
            this.setValue(i, i, nodes.get(i).cost);
        
        }
    
    }
    
    
    public void setValue(int i, int j, int value){
        
        this.routingTable.get(i).set(j, value);
    
    }
    
    public Node getNode(String id){
        for(Node n: this.nodes){
            if(n.id.equals(id)||n.ip.equals(id)){
                return n;
            }
        
        }
        return null;
    }
    
    public void processValues(){
        
        
    
    }
    
}
