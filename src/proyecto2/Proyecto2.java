/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.awt.BorderLayout;
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
    public static void main(String[] args) {
        JFrame frameTablas = new JFrame();

        ConfigParser config = new ConfigParser();
        //Obtenemos datos de los nodos vecinos
        ArrayList<Node> nodes = config.parseConfigFile();
        ForwardingTable ft = new ForwardingTable();
        Router router = new Router();
        
        
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
        }, 0, 5000);
        // 1000 milliseconds in a second * 60 per minute * the MINUTES variable. 1 
        }
    
}
