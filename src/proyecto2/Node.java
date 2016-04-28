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
    public String ip;
    public boolean isUpListener,isUpSender;
    public NodeConnection listenerConnection;
    public boolean keepAlive;
    public boolean isSending;
    public boolean isVecino;
    
    public Node(String id, int cost,String ip,boolean isVecino){
        this.tableId = nodeCounter;
        nodeCounter++;
        this.id = id;
        this.cost = cost;
        this.ip = ip;
        this.isUpListener = false;
        this.isUpSender = false;
        this.keepAlive = false;
        this.isSending = false;
        this.isVecino = isVecino;
    }
    
    
}
