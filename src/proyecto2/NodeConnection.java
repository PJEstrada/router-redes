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

/**
 *
 * @author pablo
 */
public class NodeConnection implements Runnable{
    
    Socket socket;
    BufferedReader input;
    DataOutputStream output;
    boolean connectionOpen;
    public NodeConnection(Socket clientSocket) throws IOException{
        this.socket = clientSocket;
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
        while(connectionOpen){
            
            try{
                readRequest();
                String linea;
                while(!input.ready()){
                    ;
                }
                linea = input.readLine();
                sendResponse("From:<Router que notifica>\n" +
                            "Type:WELCOME");
                
                
                      
            }
            catch (Exception e){
                e.printStackTrace();
            }
        
        
        
        }
    }
    
}
