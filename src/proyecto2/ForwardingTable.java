/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pablo
 */
public class ForwardingTable {
    
    JTable tabla;
    ArrayList<Node> nodes;
    ArrayList<ArrayList<String>> forwardingTable;
    Router router;
    ArrayList<Message> mensajesRecibidos;
    
    public ForwardingTable(ArrayList<Node> nodes /*, Router router*/){    
        this.nodes = nodes; 
        this.router = new Router(nodes);
        mensajesRecibidos = new ArrayList<Message>();
        
        /*Object rowData[][] = { { "Node A", "Node D"},
                               { "Node B", "Node A"} };
        Object columnNames[] = { "Destino", "A Traves De"};*/        
        
        tabla = new JTable(1, 2);
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        
        ArrayList<String> columnName = new ArrayList<String>();
        columnName.add("Destination");
        columnName.add("Through");
        model.setColumnIdentifiers(columnName.toArray());
        
        /*for(Node n: nodes){
            //agregar columnas por cada nodo
            columnName.add(n.id); //ESTO DEBE SER LA IP, agregarlo a nodo
        }
        //setearlo al modelo de la JTable*/        
        
        this.forwardingTable = new ArrayList<ArrayList<String>>();
        for(Node n: nodes){
            //agregar una fila por nodo de la red
            ArrayList<String> row = new ArrayList<String>();
            //nodo de la red
            row.add(n.id); //DEBE SER LA IP
            //interfaz de salida
            row.add(n.id); //DEBE SER LA IP
            this.forwardingTable.add(row);
        }        
        //RECALCULAR
        recalculateTable();       
        
    }
    
    public void forwardMessage(Message msg) throws IOException{
        //asume que el destinatario no es LOCALHOST
        Socket tempSocket = new Socket(msg.to, 9081);                
        DataOutputStream output = new DataOutputStream(tempSocket.getOutputStream());                     
        //enviar el mensaje
        String linea = msg.messageToFormat();
        byte[] bytesEnviar = linea.getBytes();
        output.write(bytesEnviar);
        //cerrar el socket
        tempSocket.close();
    }
    
    public void recalculateTable(){
        //Recalcular la tabla de routing 
        //es decir llenar el segundo valor de cada fila
        //con la interfaz de salida correspondiente
        
        //LEER LA TABLA DE ROUTING        
    }
    
    public String queryTable(String ip){              
        //Obtener de la tabla la ip correspondiente a 
        //el nodo n ingresado        
        //debe ser la IP
        for(ArrayList<String> row : forwardingTable){
            //por cada fila de la tabla de forwarding
            //revisar sea el destino
            if(row.get(0).equals(ip)){
                return row.get(1); //regresar la ip
            }
        }
        //no hay ruta ERROR?
        return "none";
    }    
}