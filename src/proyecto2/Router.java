/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pablo
 */
public class Router {
    
    JTable tableRouter;
    
    public Router(){
        Object columnNames1[] = { "Node A", "Node B","Node C"};
        tableRouter = new JTable(1,3);
        DefaultTableModel model = (DefaultTableModel) tableRouter.getModel();
        model.setColumnIdentifiers(columnNames1);
        model.addRow(new Object[]{"5","6","5"} );
        
    
    }
    
}
