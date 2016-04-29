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
        System.out.println("Sender creado:  Type"+type);
        node.isSending = true;
        if(type==1){
            System.out.println("Seding Hello To: "+node.id);
            //Send Hello
            try {
                node.socket = new Socket(node.ip,9080);
                DataOutputStream outToServer = new DataOutputStream(node.socket.getOutputStream()); 
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(node.socket.getInputStream()));
                String response = inFromServer.readLine();
                System.out.println("SENDER("+node.id+"): received "+response);
                //Enviamos mensaje de Hello
                String helloMessage = message;
                outToServer.writeBytes(helloMessage);
                System.out.println("SENDER("+node.id+") writing "+helloMessage);
                //outToServer.flush();
                //Recibimos respuesta
                while(!inFromServer.ready()){
                    ;
                }
                String responseLine1 = inFromServer.readLine();
                System.out.println("SENDER("+node.id+") RESPONSELINE 1:"+responseLine1);
                String[] line1Data = responseLine1.split(":");
                if(line1Data[0].equalsIgnoreCase("From")){
                   if(line1Data.length==2&&line1Data[1].equalsIgnoreCase(node.id)){
                        //Leemos el tipo para verificar que sea Welcome
                        while(!inFromServer.ready()){
                            ;
                        }
                        String responseLine2 = inFromServer.readLine();         
                        String line2Data[] = responseLine2.split(":");
                        System.out.println("SENDER("+node.id+") RESPONSELINE 2:"+responseLine2);
                        if(line2Data.length==2&&line2Data[0].equalsIgnoreCase("Type")){
                            if(line2Data[1].equalsIgnoreCase("WELCOME")){
                                System.out.println("SENDER("+node.id+")  Connection Established with: "+node.id);
                                node.isUpSender = true;
                                //Seteamos costos de enlaces directos a nodos vecinos
                                router.setValue(node.tableId, node.tableId, node.cost);
                            }
                            else{
                                
                                System.out.println("SENDER("+node.id+")  Error. Expected WELCOME ");

                            }
                        }

                    }
                    else{
                        System.out.println("SENDER("+node.id+")  Error. Wrong node responded. Expected: "+node.id+" Got: "+line1Data[1]);
                    }         

                }
                else{

                    System.out.println("SENDER("+node.id+") Bad Response: "+responseLine1);
                }
            } catch (IOException e) {
                System.out.println("SENDER("+node.id+")  Error Connecting to node. Retry in next cycle...");
                //Asumimos que el listener vecino murio y cerramos conexion
                //Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                e.printStackTrace();
                node.isUpSender = false;
                router.setValue(node.tableId, node.tableId, 99);
                node.isSending = false;
                node.initialDV = false;
                try {
                    closeConnection();
                } catch (IOException ex) {
                    Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                }                
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
                    router.tableUpdates.clear(); 
                    System.out.println("SENDER("+node.id+") DV sent to: "+node.id);
              }
              catch (Exception e){
                    System.out.println("SENDER("+node.id+") Error Connecting to node. Retry in next cycle...");
                   //Asumimos que el listener vecino murio y cerramos conexion
                    e.printStackTrace();
                    node.isUpSender = false;
                    router.setValue(node.tableId, node.tableId, 99);
                    node.isSending = false;
                    node.initialDV = false;
                    
                   try {
                        closeConnection();
                    } catch (IOException ex) {
                        Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
              }
        
        
        
        }
       
        else if(type == 3){
            //Enviamos Keep Alive
              try{
                    DataOutputStream outToServer = new DataOutputStream(node.socket.getOutputStream()); 
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(node.socket.getInputStream()));
   
                    //Enviamos mensaje de KA
                    String dv = message;
                    outToServer.writeBytes(dv);     
                    System.out.println("SENDER("+node.id+")  Keep Alive sent to: "+node.id);
              }
              catch (Exception e){
                    System.out.println("SENDER("+node.id+")  Error Connecting to node. Retry in next cycle...");
                    e.printStackTrace();
                   //Asumimos que el listener vecino murio y cerramos conexion
                    e.printStackTrace();
                    node.isUpSender = false;
                    router.setValue(node.tableId, node.tableId, 99);                    
                    node.isSending = false;
                    node.initialDV = false;
                    try {
                        closeConnection();
                    } catch (IOException ex) {
                        Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                    }
              }
        
        
        }
        else if(type == 4){
            //Enviamos DV inicial (Toda la tabla)
              try{
                    DataOutputStream outToServer = new DataOutputStream(node.socket.getOutputStream()); 
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(node.socket.getInputStream()));
   
                    //Enviamos mensaje de DV
                    String dv = message;
                    outToServer.writeBytes(dv);   
                    router.tableUpdates.clear(); 
                    node.initialDV = true;
                    System.out.println("SENDER("+node.id+") DV sent to: "+node.id);
              }
              catch (Exception e){
                    System.out.println("SENDER("+node.id+") Error Connecting to node. Retry in next cycle...");
                   //Asumimos que el listener vecino murio y cerramos conexion
                    e.printStackTrace();
                    node.isUpSender = false;
                    router.setValue(node.tableId, node.tableId, 99);
                    node.isSending = false;
                    node.initialDV = false;
                    
                   try {
                        closeConnection();
                    } catch (IOException ex) {
                        Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
              }   
        
        }
        node.isSending = false;
        

    }
    
    public void closeConnection() throws IOException{
        if(node.socket!=null){
            node.socket.close();
        }

        System.out.println("Sender: Connection Terminated");
        node.isSending = false;
    }
    
    
    
    
}
