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
                System.out.println("Linea 1: "+linea);
                String[] fromData = linea.split(":");
                if(fromData.length==2&&fromData[0].equalsIgnoreCase("FROM")){
                    System.out.println("From Ok.");
                    routerName = fromData[1];
                    //Parseamos el tipo del mensaje
                    while(!input.ready()){
                        ;
                    }
                    linea = input.readLine();  
                    System.out.println("Linea 2: "+linea);
                    String[] typeData = linea.split(":");
                    if(typeData.length==2&&typeData[0].equalsIgnoreCase("TYPE")){
                       typeName  = typeData[1]; 
                       if(typeName.equalsIgnoreCase("HELLO")){
                           
                       
                       }
                       else if(typeName.equalsIgnoreCase("DV")){
                       
                       
                       }
                    }
                    else{
                        sendResponse("Bad Request.");
                    }
                   
                    
                    
                    
                    
                }
                else{
                    sendResponse("Bad Request.");
                }
               
                
                
                sendResponse("From:<Router que notifica>\n" +
                            "Type:WELCOME");
                
      
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
