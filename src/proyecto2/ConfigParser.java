/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author pablo
 */
public class ConfigParser {
    
    
    ArrayList<Node> nodes;
    public ConfigParser(){
        this.nodes = new ArrayList<Node>();
        
    }
    
    public ArrayList<Node> parseConfigFile(){
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("config.txt"));
            try {
                String x;
                while ( (x = br.readLine()) != null ) {
                    // printing out each line in the file
                    System.out.println(x);
                    String[] data = x.split(" ");
                    if(data.length<2){
                        throw new Exception("Error parsing config file.");
                    }
                    else if(data.length==3){
                        String id = data[0];
                        String ip = data[1];
                        int cost = Integer.parseInt(data[2]);
                        nodes.add(new Node(id,cost,ip));
                    }
                    
                    else if(data.length == 2){
                        if(data[0].equalsIgnoreCase("time")){
                            int time = Integer.parseInt(data[1]);
                            Proyecto2.time = time;
                        }
                        else if(data[0].equalsIgnoreCase("timeu")){
                             int time = Integer.parseInt(data[1]);
                            Proyecto2.timeU = time;
                            
                        }
                    
                    }

                } 
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        
        return this.nodes;
    
    }
    
}
