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

    private String user = null;
    
    /** Creates new form ClientsGUI */
    public ClientGUI(String user) {
        this.user = user;
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

        jLabel1 = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageFrame = new javax.swing.JTextArea();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        chatViewer = new javax.swing.JScrollPane();
        chatOutput = new javax.swing.JTextArea();
        chatInput = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        jSplitPane3 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        sourceTree = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        codeFrame = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CodeBuddy Alpha");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabel1.setText("CoderBuddy");

        jSplitPane2.setDividerLocation(200);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setPreferredSize(new java.awt.Dimension(855, 474));

        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setLastDividerLocation(400);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(855, 365));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 113));

        messageFrame.setColumns(20);
        messageFrame.setEditable(false);
        messageFrame.setRows(5);
        messageFrame.setPreferredSize(new java.awt.Dimension(400, 94));
        jScrollPane1.setViewportView(messageFrame);

        jSplitPane1.setLeftComponent(jScrollPane1);

        chatViewer.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatViewer.setHorizontalScrollBar(null);

        chatOutput.setColumns(20);
        chatOutput.setEditable(false);
        chatOutput.setLineWrap(true);
        chatOutput.setRows(5);
        chatViewer.setViewportView(chatOutput);

        chatViewer.setBounds(0, 0, 450, 170);
        jLayeredPane1.add(chatViewer, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        chatInput.setBounds(10, 180, 360, 20);
        jLayeredPane1.add(chatInput, javax.swing.JLayeredPane.DEFAULT_LAYER);

        sendButton.setLabel("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        sendButton.setBounds(380, 180, 70, 23);
        jLayeredPane1.add(sendButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jSplitPane1.setRightComponent(jLayeredPane1);

        jSplitPane2.setRightComponent(jSplitPane1);

        jSplitPane3.setDividerLocation(200);

        sourceTree.setPreferredSize(new java.awt.Dimension(200, 200));
        jScrollPane2.setViewportView(sourceTree);

        jSplitPane3.setLeftComponent(jScrollPane2);

        codeFrame.setColumns(20);
        codeFrame.setRows(5);
        codeFrame.setPreferredSize(new java.awt.Dimension(164, 200));
        jScrollPane3.setViewportView(codeFrame);

        jSplitPane3.setRightComponent(jScrollPane3);

        jSplitPane2.setLeftComponent(jSplitPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField chatInput;
    private javax.swing.JTextArea chatOutput;
    private javax.swing.JScrollPane chatViewer;
    private javax.swing.JTextArea codeFrame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTextArea messageFrame;
    private javax.swing.JButton sendButton;
    private javax.swing.JTree sourceTree;
    // End of variables declaration//GEN-END:variables

}
