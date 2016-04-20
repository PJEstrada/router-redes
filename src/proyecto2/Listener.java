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
 * @author pablo
 */
public class Listener implements Runnable{
    final ServerSocket socket;
    Router router;
    public Listener(Router r) throws IOException{
        socket  = new ServerSocket(9080);
        this.router = r;
    }
    
    @Override
    public void run() {
        System.out.println("Listener Initialized port: 9080");
        while(true){
            
            try{
                final Socket clientSocket = socket.accept();

                //2. Preparamos una respuesta HTTP
                //requestHttp(reader,output);
                NodeConnection request = new NodeConnection(clientSocket,this.router);
                //4. Cerramos el socket
                Thread thread = new Thread(request);
                
                thread.start();
               
            }
            catch (Exception e){
                System.out.println("Error: simon"+e.getMessage());
            }
            
       }
    }
    
    
    
    
    
}
