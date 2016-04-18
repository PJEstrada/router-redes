/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import javax.swing.JTable;

/**
 *
 * @author pablo
 */
public class ForwardingTable {
    
    JTable tabla;
    public ForwardingTable(){
    
            
        Object rowData[][] = { { "Node A", "Node D"},
                               { "Node B", "Node A"} };
        Object columnNames[] = { "Destino", "A Traves De"};
        
        
        tabla = new JTable(rowData, columnNames);
    }
    
}
