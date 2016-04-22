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
import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pablo
 */
public class ForwardingTable {
    
    JTable tabla;
    ArrayList<Node> nodes;
    ArrayList<String> vecinos;
    ArrayList<ArrayList<String>> forwardingTable;
    Router router;
    ArrayList<Message> mensajesRecibidos;
    
    public ForwardingTable(ArrayList<Node> nodes , Router router){    
        this.nodes = nodes; 
        this.router = router;
        //guardar nodos vecinos
        vecinos = new ArrayList<String>();
        for(Node n : nodes){
            vecinos.add(n.ip);
        }
        //this.router = new Router(nodes);
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
            row.add(n.ip); //DEBE SER LA IP
            //interfaz de salida
            row.add(n.ip); //DEBE SER LA IP
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
        
        ArrayList<ArrayList<String>> newFT = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<Integer>> rt = router.routingTable;
        ArrayList<String> ipNodos = new ArrayList<String>(); // = router.getIpNodos(); !!!!!!!!!!
        ipNodos = sortNodeIP(ipNodos);
        //agregar tantas filas como tenga rt
        //INICIALIZAR NUEVA TABLA DE FORWARDING
        int i = 0;
        System.out.println("Recalculando Forwarding");
        for(String nod : ipNodos){ //for nod in ipNodo
            ArrayList<String> rowFT = new ArrayList<String>();
            rowFT.add(nod);
            rowFT.add("none");
            //agregarlo a la nueva forwarding table
            newFT.add(rowFT);
            i++;
        }
        
        //por cada fila de ft
        //ACTUALIZAR FILA LEYENDO ROUTING
        ArrayList<ArrayList<String>> updatedFT = new ArrayList<ArrayList<String>>();
        updatedFT.addAll(newFT);
        int j = 0;
        for(ArrayList<String> fila: newFT){
            //setear el Through recursivamente
            updatedFT.get(j).set(1, calculateRoute(fila.get(0), rt, ipNodos));
            j++;
        }       
        
        this.forwardingTable = updatedFT; //setear la nueva tabla
    }
    
    public ArrayList<String> sortNodeIP(ArrayList<String> ipNodos){
        //ordenar el ipNodo 
        //en base a los nodos       
        int sizeNodes = nodes.size();
        //llenar
        ArrayList<String> res = new ArrayList<String>();
        for(int i = 0; i < sizeNodes; i++){
            res.add("");
        }
        
        //setear
        int i = 0;        
        for(Node n : nodes){     
            System.out.println(n.tableId);
            res.set(n.tableId, n.ip);           
        }            
        return res;
    }
    
    public String calculateRoute(String dest, ArrayList<ArrayList<Integer>> rt, ArrayList<String> ipNodos){
        //obtener indice de origin
        int index = ipNodos.indexOf(dest);
        //obtener la fila del indice
        ArrayList<Integer> fila = rt.get(index);
        
        //verificar el menor de los pesos
        int menorPeso = 100;
        ArrayList<String> candidatos = new ArrayList<String>();
        
        int i = 0;
        for(Integer peso : fila){
            if(peso == menorPeso){ //agregar a candidatos
                candidatos.add(ipNodos.get(i));
            }
            else if(peso < menorPeso){ //resetear candidados y agregar
                candidatos.clear();
                candidatos.add(ipNodos.get(i));
                menorPeso = peso;
            }            
            i++;
        }
        
        //Elegir candidato
        String candidatoElegido = "";
        if(candidatos.size() == 1){
            candidatoElegido = candidatos.get(0);
        }
        else{
            //elegir
            //si algun candidato es vecino preferir ese
            boolean hayVecino = false;
            for(String cand : candidatos){
                if(vecinos.contains(cand)){
                    //elegir ese
                    candidatoElegido = cand;
                    hayVecino = true;
                    break;
                }
            }
            
            //si no elegir aleatoriamente
            Random r = new Random();
            if(!hayVecino){
                candidatoElegido = candidatos.get(r.nextInt(candidatos.size()));
            }            
        }
        
        //Si es un vecino devolverlo
        if(vecinos.contains(candidatoElegido)){
            return candidatoElegido;
        }       
        
        //Si no continuar recursivamente con destino = candidato
        return calculateRoute(candidatoElegido, rt, ipNodos);        
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