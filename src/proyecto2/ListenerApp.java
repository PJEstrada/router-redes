/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Anai
 */
public class ListenerApp implements Runnable{
    
    final ServerSocket socket;
    ForwardingTable ft;
    
    public ListenerApp(ForwardingTable ft) throws IOException{
        socket  = new ServerSocket(9081);  
        this.ft = ft;
    }
    
    @Override
    public void run() {
        System.out.println("Listener Initialized port: 9081");
        while(true){
            
            try{
                //aceptar conexiones al puerto 9081
                final Socket clientSocket = socket.accept();
                //Nueva request
                MessageConnection request = new MessageConnection(clientSocket, ft);
                //Crear y correr thread
                Thread thread = new Thread(request);                
                thread.start();               
            }
            catch (Exception e){
                System.out.println("Error App: "+e.getMessage());
            }
            
       }
    }
    
}
