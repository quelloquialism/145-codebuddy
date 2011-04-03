/*
 * ClientGUI.java
 *
 * Created on Apr 2, 2011, 7:49:52 PM
 */

package client;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author Nathan
 */
public class ClientGUI extends javax.swing.JFrame {

    /** Creates new form ClientsGUI */
    public ClientGUI() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chatInput = new javax.swing.JTextField();
        chatViewer = new javax.swing.JScrollPane();
        chatOutput = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CodeBuddy Alpha");

        chatInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatInputActionPerformed(evt);
            }
        });
        chatInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chatInputKeyPressed(evt);
            }
        });

        chatViewer.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatViewer.setHorizontalScrollBar(null);

        chatOutput.setColumns(20);
        chatOutput.setEditable(false);
        chatOutput.setLineWrap(true);
        chatOutput.setRows(5);
        chatViewer.setViewportView(chatOutput);

        sendButton.setLabel("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(chatInput, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton))
            .addComponent(chatViewer, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(chatViewer, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chatInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        handleChatSubmission();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void chatInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chatInputKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            handleChatSubmission();
        }
    }//GEN-LAST:event_chatInputKeyPressed

    private void chatInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chatInputActionPerformed

    private void handleChatSubmission() {
        // TODO update this to do db writes
        String inputText = chatInput.getText();

        if (isValidInput(inputText)) {
            String chatText = chatOutput.getText();
            if (!chatText.endsWith("\n") && chatText.length() > 0) {
                chatText += "\n";
            }

            // This is dirty, but it will go away when we start using a db,
            // since we'll be retrieving the time from a db column instead of
            // measuring it here
            String currentTime = DateFormat.getTimeInstance(DateFormat.MEDIUM)
                    .format(new Date());

            // The notion of a user doesn't exist yet, so this is just a
            // placeholder in the meantime
            String user = "testuser";

            chatText += "(" + currentTime + ") " + user + "> " + inputText;
            chatOutput.setText(chatText);
            chatInput.setText("");
        } else {
            // TODO pop up warning for invalid input
        }
    }

    private boolean isValidInput(String input) {
        return input.length() > 0 && input.length() < 256;
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField chatInput;
    private javax.swing.JTextArea chatOutput;
    private javax.swing.JScrollPane chatViewer;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables

}
