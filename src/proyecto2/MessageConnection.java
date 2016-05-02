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
 * @author Anai
 */
public class MessageConnection implements Runnable{
    
    Socket socket;
    BufferedReader input;
    DataOutputStream output;
    //String localhost = "192.168.0.7";
    ForwardingTable ft; //Tabla de forwarding para hacer lookups
    
    public MessageConnection(Socket clientSocket, ForwardingTable ft) throws IOException{
        this.socket = clientSocket;
        this.ft = ft; 
        //Obteniendo IP del cliente
        //InetAddress ipCliente = clientSocket.getInetAddress();
        
        InputStreamReader input = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader reader = new BufferedReader(input);            
        //Preparamos el buffer del servidor al cliente para poder enviar responses de vuelta
        //Header + requested file
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());        
        this.input = reader;
        this.output = output;
        sendResponse("1981 Node Mike ROUTER UVG: \n");  
        System.out.println("Conexion entrante 1981");
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
        try{
            readRequest();
            String linea;
            while(!input.ready()){
                ; //wait
            }
            linea = input.readLine();
            
            //Convertir a Message 
            Message m = new Message();
            m.formatToMessage(linea);
            System.out.println("------------------------FORWARDER: "+m.from+" "+m.to+" "+m.message);
            //Verificar destinatario de mensaje
            //Redirigir o guardar mensaje
            String TO = m.getTo().trim();
            if(TO.equalsIgnoreCase(Proyecto2.nodeName)){
                //guardar mensaje               
                UserFrame.messages.add(m);
                UserFrame.displayMessage();
            }
            else{
                //redirigir
                String redirectTo = ft.queryTable(TO); //revisar la tabla
                if(redirectTo.equals("none")){
                    System.out.println("------------------FORWARDER: ERROR no hay RUTA!");
                }
                else{
                    //abrir Socket al puerto 1981 de la ip TO
                    System.out.println("------------------FORWARDER: forwarding "+m.from+" to "+TO+" through -> "+redirectTo);
                    Socket tempSocket = new Socket(redirectTo, 1981);
                    //InputStreamReader input = new InputStreamReader(tempSocket.getInputStream());
                    //BufferedReader reader = new BufferedReader(input);                 
                    DataOutputStream output = new DataOutputStream(tempSocket.getOutputStream());                     
                    //enviar el mensaje
                    byte[] bytesEnviar = linea.getBytes();
                    output.write(bytesEnviar);
                    //cerrar el socket
                    tempSocket.close();
                }                
            }           
            //sendResponse("From:<Router que notifica>\n" + "Type:WELCOME");
        }
        catch (Exception e){
            e.printStackTrace();
        }      
    }
}
