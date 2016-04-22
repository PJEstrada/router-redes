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
    static final String  nodeName = "192.168.1.12";
    public static void main(String[] args) throws IOException {
        JFrame frameTablas = new JFrame();

        ConfigParser config = new ConfigParser();
        //Obtenemos datos de los nodos vecinos
        ArrayList<Node> nodes = config.parseConfigFile();        
        Router router = new Router(nodes);
        ForwardingTable ft = new ForwardingTable(nodes, router);
        
        //Creamos listener para escuchar puerto 9080 mensajes de nodos vecinos
        Listener listener = new Listener(router);
        Thread threadListener = new Thread(listener);
        threadListener.start();
        
        //crear listenerApp y threadApp y correrlo en el puerto 9081
        ListenerApp listenerApp = new ListenerApp(ft);
        Thread threadApp = new Thread(listenerApp);
        threadApp.start();

        //Creamos el thread de envio de DV
        DistanceVectorSender dvSender = new DistanceVectorSender(router);
        Thread dv = new Thread(dvSender);
        dv.start();
        //Creamos el thread de verificacion de keep alive
        ReconectionChecker rChecker = new ReconectionChecker(router);
        Thread rc = new Thread(rChecker);
        rc.start();

        
        JScrollPane scrollPane1 = new JScrollPane(router.tableRouter);
        scrollPane1.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
        JScrollPane scrollPane2 = new JScrollPane(ft.tabla);
        scrollPane2.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
        frameTablas.add(scrollPane1, BorderLayout.CENTER);
        frameTablas.add(scrollPane2, BorderLayout.SOUTH);
        frameTablas.validate();
        frameTablas.setSize(800, 800);
        frameTablas.setVisible(true);
        
        
        UserFrame frame = new UserFrame(ft);
        frame.setVisible(true);
        // 1000 milliseconds in a second * 60 per minute * the MINUTES variable. 1 
        }
    
}
