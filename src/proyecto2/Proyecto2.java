/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pablo
 */
public class Proyecto2 {
    static int i = 0;
    /**
     * @param args the command line arguments
     */
    static int time=0;
    static int timeU=0;
    public static void main(String[] args) throws IOException {
        JFrame frameTablas = new JFrame();

        ConfigParser config = new ConfigParser();
        //Obtenemos datos de los nodos vecinos
        ArrayList<Node> nodes = config.parseConfigFile();
        ForwardingTable ft = new ForwardingTable(nodes);
        Router router = new Router(nodes);
        //Creamos listener para escuchar puerto 9080 mensajes de nodos vecinos
        Listener listener = new Listener(router);
        Thread threadListener = new Thread(listener);
        threadListener.start();
        
        //crear listenerApp y threadApp y correrlo
        //ListenerApp listenerApp = new ListenerApp(ft);
        //Thread threadApp = new Thread(listenerApp);
        //threadApp.start();
        
        JScrollPane scrollPane1 = new JScrollPane(router.tableRouter);
        scrollPane1.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
        JScrollPane scrollPane2 = new JScrollPane(ft.tabla);
        scrollPane2.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
        frameTablas.add(scrollPane1, BorderLayout.CENTER);
        frameTablas.add(scrollPane2, BorderLayout.SOUTH);
        frameTablas.validate();
        frameTablas.setSize(800, 800);
        frameTablas.setVisible(true);
        
       
        
        float MINUTES = 0.1f; // The delay in minutes
        Timer timer = new Timer();
        
        timer.schedule(new TimerTask() {
        
        @Override
        public void run() { // Function runs every MINUTES minutes.
         // Run the code you want here
         
        
            
            
        }
        }, 0, 5000); //5 segundos
        // 1000 milliseconds in a second * 60 per minute * the MINUTES variable. 1 
        }
    
}
