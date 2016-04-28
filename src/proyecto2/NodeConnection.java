/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class NodeConnection implements Runnable{
    
    Socket socket;
    BufferedReader input;
    DataOutputStream output;
    boolean connectionOpen;
    Router router;
    public NodeConnection(Socket clientSocket,Router router) throws IOException{
        this.socket = clientSocket;
        this.router = router;
                //Obteniendo IP del cliente
        InetAddress ipCliente = clientSocket.getInetAddress();
        //System.out.print("IP: "+ipCliente);
        //1. Leemos request HTTP del socket del cliente
        InputStreamReader input = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader reader = new BufferedReader(input);            
        //Preparamos el buffer del servidor al cliente para poder enviar responses de vuelta
        //Header + requested file
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());        
        this.input = reader;
        this.output = output;
        sendResponse("9080 Node Pablo ROUTER UVG: \n");   
        this.connectionOpen = true;
    
    }
    
    public void readRequest() throws IOException{
        InputStreamReader input = new InputStreamReader(this.socket.getInputStream());
        BufferedReader reader = new BufferedReader(input);      
        this.input = reader;
    }
    
    public void sendResponse(String s) throws IOException{
        byte[] bytes = s.getBytes();
        output.write(bytes);  
        System.out.println("LISTENER: Response sent: "+s);
        //
    
    }
    
    @Override
    public void run() {
        String routerName,typeName;
        while(connectionOpen){
            
            try{
                readRequest();
                String linea;
                while(!input.ready()){
                    ;
                }
                linea = input.readLine();
                //System.out.println("Line 1: "+linea);
                String[] fromData = linea.split(":");
                if(fromData.length==2&&fromData[0].equalsIgnoreCase("FROM")){
                    //System.out.println("From Ok.");
                    routerName = fromData[1];
                    //Parseamos el tipo del mensaje
                    while(!input.ready()){
                        ;
                    }
                    linea = input.readLine();  
                    //System.out.println("Line 2: "+linea);
                    String[] typeData = linea.split(":");
                    if(typeData.length==2&&typeData[0].equalsIgnoreCase("TYPE")){
                       typeName  = typeData[1]; 
                       if(typeName.equalsIgnoreCase("HELLO")){
                           Node n = router.getNode(routerName);
                           //Si el nodo no existe debemos crear uno nuevo como vecino
                           if(n==null){
                               //Costo por default 5
                               
                               Node newNode = new Node(routerName,5,routerName);
                               this.router.addNewNeighborNode(newNode);
                               newNode.isUpListener = true;
                               newNode.listenerConnection=this;
                               newNode.keepAlive = true;
                               sendResponse("From:"+Proyecto2.nodeName+"\n" +"Type:WELCOME\n");
                               router.sendInitialDV(newNode);
                               System.out.println("LISTENER: Received HELLO From: "+newNode.ip);
                           }
                           else{
                                //Verificamos si la conexion no habia sido levantada antes. Si se levanto antes cerramos esta conexion.
                                n.isUpListener = true;
                                n.listenerConnection=this;
                                n.keepAlive = true;
                                router.setValue(n.tableId, n.tableId, n.cost);
                                router.tableUpdates.add(n.ip+":"+n.cost);
                                sendResponse("From:"+Proyecto2.nodeName+"\n" +"Type:WELCOME\n");
                                System.out.println("LISTENER: Received HELLO From: "+n.id);
                                
                           }
                           

                       }
                       else if(typeName.equalsIgnoreCase("DV")){
                           
                           Node n = router.getNode(routerName);
                           if(n.isUpListener==false){
                               sendResponse("Bad Request. Say HELLO first.");
                           }
                           else{
                                   
                                //Parseamos el numero de lineas
                               while(!input.ready()){
                                   ;
                               }  
                               linea = input.readLine(); 
                               System.out.println("LISTENER("+n.ip+"): DV- "+linea);
                               String[] numberLines = linea.split(":");
                               if(!numberLines[0].equalsIgnoreCase("LEN")){
                                   System.out.println("LISTENER("+n.ip+"): Error. Expected Len:<lines>. Got: "+linea);
                               }
                               int numLines = Integer.parseInt(numberLines[1]);
                               ArrayList<String> newDistanceVectors =  new ArrayList<String>();
                               int i = 0;
                               while(i<numLines){
                                    while(!input.ready()){
                                        ;
                                    }  
                                    linea = input.readLine(); 
                                    System.out.println("LISTENER("+n.ip+"): DV- "+i+" - -"+linea);
                                    String[] dvData = linea.split(":");
                                    if(dvData.length==2){
                                        //Verificamos que el costo sea un numero
                                        String nodeName = dvData[0];
                                        try{
                                            int cost = Integer.parseInt(dvData[1]);
                                            newDistanceVectors.add(linea);
                                        }
                                        catch (Exception e ){
                                            sendResponse("LISTENER("+n.ip+"): Bad Request.DV value is not a number."); 
                                        
                                        }
                                    }
                                    else{
                                       sendResponse("LISTENER("+n.ip+"): Bad Request. Bad DV data."); 
                                    }

                                    i++;
                               }
                               router.updateTable(newDistanceVectors,n);
                               
                           }
                           
                       
                       
                       }
                       else if(typeName.equalsIgnoreCase("KeepAlive")){
                            Node n = router.getNode(routerName);
                            n.keepAlive=true;
                       }
                    }
                    else{
                        sendResponse("LISTENER(): Bad Request.");
                    }
                   
                    
                    
                    
                    
                }
                else{
                    sendResponse("LISTENER():Bad Request.");
                }
               
                

                
      
            }
            catch (Exception e){
                e.printStackTrace();
                try {
                    this.closeConnection();
                } catch (IOException ex1) {
                    Logger.getLogger(NodeConnection.class.getName()).log(Level.SEVERE, null, ex1);
                }
               Logger.getLogger(NodeConnection.class.getName()).log(Level.SEVERE, null, e);
               connectionOpen = false;
            }
        
        
        
        }
    }
    
    
    public void closeConnection() throws IOException{
        this.input.close();
        this.output.close();
        this.socket.close();
        System.out.println("LISTENER: Connection Closed.");
    
    }
    
    
}
