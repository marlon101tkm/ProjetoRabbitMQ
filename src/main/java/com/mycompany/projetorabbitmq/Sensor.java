package com.mycompany.projetorabbitmq;

import com.rabbitmq.client.*;
import java.util.Random;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win10
 */
public class Sensor extends javax.swing.JFrame {

    /**
     * Creates new form Sensor
     */
    private String routingKey = "";
    private String tipoMsg = "";

    public JLabel getNomeSensor() {
        return nomeSensor;
    }
    
    private final String EXCHANGE_NAME = "topic_logs";

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public void setTipoMsg(String tipoMsg) {
        this.tipoMsg = tipoMsg;
    }

    public void setNomeSensor(JLabel nomeSensor) {
        this.nomeSensor = nomeSensor;
    }

    public Sensor() {
        initComponents();
    }

    private String taxa(Random rand) {
        String pri = "";
        switch (rand.nextInt(3)) {
            case 0:
                pri = "Baixa";
                break;
            case 1:
                pri = "Media";
                break;
            case 2:
                pri = "Alta";
                break;
        }
        return pri;
    }

    private String geraMensagem() {

        String msg = "";
        Random rand = new Random();
        switch (tipoMsg) {
            case "congest":
                msg = "Congestionamento: Possui: " + taxa(rand) + " taxa de Congestionamento no KM: " + rand.nextInt(1001) + " na BR";
                break;
            case "buracos":
                msg = "Buracos: Possui: " + taxa(rand) + " concetração de Buracos no KM: " + rand.nextInt(1001) + " na BR";
                break;
            case "radares":
                msg = "Radares: Possui: " + taxa(rand) + " concetração de Radares no KM: " + rand.nextInt(1001) + " na BR";
                break;
            case "obras":
                msg = "Obras: No KM: " + rand.nextInt(1001) + " da BR está em Obras ";
                break;

        }

        return msg;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ColetaDados = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        nomeSensor = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ColetaDados.setText("Coleta Dados");
        ColetaDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColetaDadosActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome Sensor:");

        nomeSensor.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(ColetaDados))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nomeSensor)))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nomeSensor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ColetaDados)
                .addGap(120, 120, 120))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ColetaDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColetaDadosActionPerformed

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try ( Connection connection = factory.newConnection();  Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");


            String routingKey = this.routingKey;
            String message = geraMensagem()+"\n";
            
            
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

        } catch (Exception e) {

        }
    }//GEN-LAST:event_ColetaDadosActionPerformed

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
            java.util.logging.Logger.getLogger(Sensor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sensor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sensor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sensor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sensor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ColetaDados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel nomeSensor;
    // End of variables declaration//GEN-END:variables
}
