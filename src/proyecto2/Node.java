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
    public static int nodeCounterNeighbors;
    public static int nodeCounter;
    public String id;
    public int cost;
    public Socket socket;
    public int tableIdCols,tableIdRow;
    public String ip;
    public boolean isUpListener,isUpSender;
    public NodeConnection listenerConnection;
    public boolean keepAlive;
    public boolean isSending;
    public boolean isVecino;
    public boolean initialDV;
    public Thread senderThread;
    public Sender nodeSender;
    
    public Node(String id, int cost,String ip,boolean isVecino,Router r){
        if(isVecino ==true){
            this.tableIdCols = nodeCounterNeighbors;
            nodeCounterNeighbors++;   
            this.tableIdRow = nodeCounter;
            nodeCounter++;
            
        }
        else{
            this.tableIdRow = nodeCounter;
            nodeCounter++;           
        }
        this.isVecino = isVecino;

        this.id = id;
        this.cost = cost;
        this.ip = ip;
        this.isUpListener = false;
        this.isUpSender = false;
        this.keepAlive = false;
        this.isSending = false;

        this.initialDV = false;
        String hello = "From:"+Proyecto2.nodeName+"\n" +"Type:HELLO\n";
        this.nodeSender = new Sender(this,hello,1,r);
        senderThread = new Thread(nodeSender);
        
    }
    public Node(String id, int cost,String ip,boolean isVecino){
        if(isVecino ==true){
            this.tableIdCols = nodeCounterNeighbors;
            nodeCounterNeighbors++;   
            this.tableIdRow = nodeCounter;
            nodeCounter++;
            
        }
        else{
            this.tableIdRow = nodeCounter;
            nodeCounter++;           
        }
        this.isVecino = isVecino;
        this.id = id;
        this.cost = cost;
        this.ip = ip;
        this.isUpListener = false;
        this.isUpSender = false;
        this.keepAlive = false;
        this.isSending = false;
        this.isVecino = isVecino;
        this.initialDV = false;

        
    }   
     public void setRouter(Router r){
        String hello = "From:"+Proyecto2.nodeName+"\n" +"Type:HELLO\n";
        this.nodeSender = new Sender(this,hello,1,r);
        senderThread = new Thread(nodeSender);
     }
    
}
