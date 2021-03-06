/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetorabbitmq;

import com.rabbitmq.client.*;

import java.util.ArrayList;

/**
 *
 * @author Win10
 */
public class Notificado extends javax.swing.JFrame {

    /**
     * Creates new form Notificado
     */
    private static final String EXCHANGE_NAME = "topic_logs";

    public Notificado() {
        initComponents();
        histNot.setEditable(false);
    }

    public ArrayList<String> topicos() {
//    public String topicos() {
        ArrayList<String> listaTopic = new ArrayList();
//        String chave;
        int qtdTopic = 0;

        if (buracos.isSelected()) {
            listaTopic.add("buracos");
            qtdTopic++;
        }
        if (congest.isSelected()) {
            listaTopic.add("congest");
            qtdTopic++;
        }
        if (radares.isSelected()) {
            listaTopic.add("radares");
            qtdTopic++;
        }
        if (obras.isSelected()) {
            listaTopic.add("obras");
            qtdTopic++;
        }

        if (qtdTopic == 4) {
            listaTopic.clear();
            listaTopic.add("#");
        }

        return listaTopic;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        congest = new javax.swing.JCheckBox();
        buracos = new javax.swing.JCheckBox();
        radares = new javax.swing.JCheckBox();
        obras = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        histNot = new javax.swing.JTextArea();
        inscrever = new javax.swing.JButton();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Receba Notificação");

        congest.setText("Congestionamento");

        buracos.setText("Buracos na Pista");

        radares.setText("Radares");

        obras.setText("Obras na Pista");

        histNot.setColumns(20);
        histNot.setRows(5);
        jScrollPane1.setViewportView(histNot);

        inscrever.setText("Inscrever");
        inscrever.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inscreverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(congest)
                                .addGap(18, 18, 18)
                                .addComponent(buracos)
                                .addGap(18, 18, 18)
                                .addComponent(radares)
                                .addGap(10, 10, 10)
                                .addComponent(obras)
                                .addGap(0, 30, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(191, 191, 191)
                                .addComponent(inscrever))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(congest)
                    .addComponent(buracos)
                    .addComponent(radares)
                    .addComponent(obras))
                .addGap(11, 11, 11)
                .addComponent(inscrever)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inscreverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inscreverActionPerformed
        inscrever.setEnabled(false);
        congest.setEnabled(false);
        buracos.setEnabled(false);
        obras.setEnabled(false);
        radares.setEnabled(false);
        ArrayList<String> topicos = topicos();
//        String topicos = topicos();

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String queueName = channel.queueDeclare().getQueue();

//           
            for (String bindingKey : topicos) {
                channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
            }

            System.out.println(topicos.toString());

            String piilhamsg = "";
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
//                System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");

                histNot.setText(message + histNot.getText());

            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {

        }
    }//GEN-LAST:event_inscreverActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Notificado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Notificado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Notificado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Notificado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Notificado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox buracos;
    private javax.swing.JCheckBox congest;
    private javax.swing.JTextArea histNot;
    private javax.swing.JButton inscrever;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JCheckBox obras;
    private javax.swing.JCheckBox radares;
    // End of variables declaration//GEN-END:variables
}
