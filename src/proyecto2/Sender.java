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
public class Sender implements Runnable {
    
    Node node;
    int type;//1: Hello , 2: DV
    String message;
    Router router;
    public  Sender(Node n,String message, int type,Router r){
        this.node = n;
        this.message = message;
        this.type = type;
        this.router = r;
    }

    @Override
    public void run() {
        
        if(type==1){
            //Send Hello
            try {
                node.socket = new Socket(node.ip,9080);
                DataOutputStream outToServer = new DataOutputStream(node.socket.getOutputStream()); 
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(node.socket.getInputStream()));
                String response = inFromServer.readLine();

                //Enviamos mensaje de Hello
                String helloMessage = message;
                outToServer.writeBytes(helloMessage);
                //outToServer.flush();
                //Recibimos respuesta
                while(!inFromServer.ready()){
                    ;
                }
                String responseLine1 = inFromServer.readLine();
                String[] line1Data = responseLine1.split(":");
                if(line1Data[0].equalsIgnoreCase("From")){
                   if(line1Data.length==2&&line1Data[1].equalsIgnoreCase(node.ip)){
                        //Leemos el tipo para verificar que sea Welcome
                        while(!inFromServer.ready()){
                            ;
                        }
                        String responseLine2 = inFromServer.readLine();         
                        String line2Data[] = responseLine2.split(":");
                        if(line2Data.length==2&&line2Data[0].equalsIgnoreCase("Type")){
                            if(line2Data[0].equalsIgnoreCase("WELCOME")){
                                node.isUpSender = true;
                                //Seteamos costos de enlaces directos a nodos vecinos
                                router.setValue(node.tableId, node.tableId, node.cost);
                            }
                            else{
                                
                                System.out.println("Sender: Error. Expected WELCOME ");

                            }
                        }

                    }
                    else{
                        System.out.println("Sender: Error. Wrong node responded. Expected: "+node.id+" Got: "+line1Data[1]);
                    }         

                }
                else{

                    System.out.println("Sender: Bad Response: "+responseLine1);
                }
            } catch (IOException ex) {
                System.out.println("SENDER: Error Connecting to node. Retry in next cycle...");
                //Asumimos que el listener vecino murio y cerramos conexion
                //Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }     
        }
        else if(type==2){
            //Enviamos DV
              try{
                    DataOutputStream outToServer = new DataOutputStream(node.socket.getOutputStream()); 
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(node.socket.getInputStream()));
   
                    //Enviamos mensaje de DV
                    String dv = message;
                    outToServer.writeBytes(dv);                              
              }
              catch (Exception e){
                    System.out.println("SENDER: Error Connecting to node. Retry in next cycle...");
                   //Asumimos que el listener vecino murio y cerramos conexion
        
              }
        
        
        
        }
       
        else if(type == 3){
            //Enviamos DV
              try{
                    DataOutputStream outToServer = new DataOutputStream(node.socket.getOutputStream()); 
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(node.socket.getInputStream()));
   
                    //Enviamos mensaje de DV
                    String dv = message;
                    outToServer.writeBytes(dv);                              
              }
              catch (Exception e){
                    System.out.println("SENDER: Error Connecting to node. Retry in next cycle...");
                   //Asumimos que el listener vecino murio y cerramos conexion
        
              }
        
        
        }
        

    }
    
    public void closeConnection() throws IOException{
        node.socket.close();
        System.out.println("Sender: Connection Terminated");
    }
    
    
    
    
}
