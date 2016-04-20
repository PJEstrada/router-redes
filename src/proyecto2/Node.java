/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.net.Socket;

/**
 *
 * @author pablo
 */
public class Node {
    public static int nodeCounter;
    
    public String id;
    public int cost;
    public Socket socket;
    public int tableId;
    
    
    public Node(String id, int cost){
        this.tableId = nodeCounter;
        nodeCounter++;
        this.id = id;
        this.cost = cost;
        
        
    }
    
    
}
