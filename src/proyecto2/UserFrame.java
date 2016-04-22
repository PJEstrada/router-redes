/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Al
 */
public class UserFrame extends javax.swing.JFrame {

    /**
     * Creates new form UserFrame
     */
    public static ArrayList<Message> messages = new ArrayList();
    static ForwardingTable forwarder;
    public static boolean isNewMessage = false;
    
    public UserFrame(ForwardingTable forwarder) {
        this.forwarder = forwarder;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfPara = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaParaMensaje = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtfDe = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaDeMensaje = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableMessages = new javax.swing.JTable();
        jbEnviar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Mensajes Recibidos");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Enviar Nuevo Mensaje");

        jLabel3.setText("Para: ");

        jLabel4.setText("Mensaje: ");

        jtfPara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfParaActionPerformed(evt);
            }
        });

        jtaParaMensaje.setColumns(20);
        jtaParaMensaje.setRows(5);
        jScrollPane1.setViewportView(jtaParaMensaje);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Lista de Mensajes");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Mensaje Seleccionado");

        jLabel7.setText("De:");

        jtfDe.setEditable(false);
        jtfDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfDeActionPerformed(evt);
            }
        });

        jLabel8.setText("Mensaje: ");

        jtaDeMensaje.setEditable(false);
        jtaDeMensaje.setColumns(20);
        jtaDeMensaje.setRows(5);
        jScrollPane2.setViewportView(jtaDeMensaje);

        tableMessages.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"192.168.1.1", "Hola Mundo. Como te va?"},
                {"192.168.1.2", "Dos y dos son cuatro, cuatro y dos son seis, seis y dos son ocho y ocho dieciseis"},
                {"174.123.1.2", "1+2+26 hehehe resolve eso pues xD"},
                {"185.254.1.16", "Esto se va a descontrolaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaar"}
            },
            new String [] {
                "Desde", "Mensaje"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableMessages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMessagesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableMessages);
        if (tableMessages.getColumnModel().getColumnCount() > 0) {
            tableMessages.getColumnModel().getColumn(0).setResizable(false);
            tableMessages.getColumnModel().getColumn(1).setResizable(false);
        }

        jbEnviar.setText("Enviar");
        jbEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEnviarActionPerformed(evt);
            }
        });

        jButton2.setText("Actualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jtfDe)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jtfPara))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jbEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(196, 196, 196))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfPara, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jtfDe, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfParaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfParaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfParaActionPerformed

    private void jtfDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfDeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfDeActionPerformed

    private void jbEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEnviarActionPerformed
        // TODO add your handling code here:
        String paraEnviarA = jtfPara.getText();
        String paraEnviar = loadIPfromID(paraEnviarA);
        String mensajeEnviar = jtaParaMensaje.getText();
        String deEnviar = Proyecto2.nodeName;
        
        // Validacion de que el para y desde no sean iguales
        if(paraEnviar.equals(deEnviar))
        {
            JOptionPane.showMessageDialog(null, "No puedes enviarte mensajes a ti mismo, lo siento D:");
            return;
        }
        // Se crea un objeto mensaje
        Message enviar = new Message(deEnviar,paraEnviar,mensajeEnviar);
        try {
            //Envio a forwarder
            forwarder.forwardMessage(enviar);
        } catch (IOException ex) {
            Logger.getLogger(UserFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbEnviarActionPerformed
    
    public static void displayMessage()
    {
        DefaultTableModel model = (DefaultTableModel) tableMessages.getModel();
        model.setRowCount(0);
        for (Message m: messages)
        {
            Object[] objs = {loadIDfromIP(m.from),m.message};
            model.addRow(objs);
        }
    }
      
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        displayMessage();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tableMessagesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMessagesMouseClicked
        
       try{
           int row = tableMessages.getSelectedRow();
           String from = (tableMessages.getModel().getValueAt(row, 0).toString());
           String msg = (tableMessages.getModel().getValueAt(row, 1).toString());
           System.out.println(from);
           System.out.println(msg);
           jtfDe.setText(from);
           jtaDeMensaje.setText(msg);
       }
       catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
       } 
    }//GEN-LAST:event_tableMessagesMouseClicked

    private static String loadIDfromIP(String ip)
    {
        String resultado = "";
        for(Node n : forwarder.nodes)
        {
            if(n.ip.equals(ip))
            {
                return n.id;
            }
        }
        return resultado;
    }
    
    private String loadIPfromID(String id)
    {
        String resultado = "";
        for(Node n : forwarder.nodes)
        {
            if(n.id.equals(id))
            {
                return n.ip;
            }
        }
        return resultado;
    }
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       /* try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserFrame().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbEnviar;
    private javax.swing.JTextArea jtaDeMensaje;
    private javax.swing.JTextArea jtaParaMensaje;
    private javax.swing.JTextField jtfDe;
    private javax.swing.JTextField jtfPara;
    private static javax.swing.JTable tableMessages;
    // End of variables declaration//GEN-END:variables
}
