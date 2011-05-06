/*
 * ClientGUI.java
 *
 * Created on Apr 2, 2011, 7:49:52 PM
 */

package client;

import java.text.DateFormat;
import java.util.Date;
import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

import javax.swing.JOptionPane;

/**
 *
 * @author Nathan
 */
public class ClientGUI extends javax.swing.JFrame {

    private String user = null;
    private String currProjLoc;
    private JLabel lbNoProj = new JLabel();
    private JPanel panNoProj = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JScrollPane spNoProj = new javax.swing.JScrollPane();
    
    /** Creates new form ClientsGUI */
    public ClientGUI(String user) {
        this.user = user;
        initComponents();
        this.setLayout(new java.awt.BorderLayout());

        lbNoProj.setText("<No Project Loaded>");        
        lbNoProj.setLocation(0, 0);
        lbNoProj.setAlignmentX(LEFT_ALIGNMENT);
        
        panNoProj.add(lbNoProj);
        panNoProj.setPreferredSize(new Dimension(200, 200));
        panNoProj.setBackground(Color.white);
        panNoProj.setAlignmentX(LEFT_ALIGNMENT);

        spNoProj.getViewport().add(panNoProj);
        spNoProj.setVisible(true);
        spTreeAndEditor.setLeftComponent(spNoProj);
    }

    public void init()
    {
        this.sourceTree.addMouseListener(new ProjTreeMouse(this));
    }

    public JTabbedPane getCodePane()
    {
        return this.codePane;
    }

    public JTree getSrcTree()
    {
        return this.sourceTree;
    }

    public String getCurrProjLoc()
    {
        return this.currProjLoc;
    }

    public void setCurrProjLoc(String cpl)
    {
        this.currProjLoc = cpl;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbCaption = new javax.swing.JLabel();
        spMiddle = new javax.swing.JSplitPane();
        spMsgAndChat = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageFrame = new javax.swing.JTextArea();
        lpChatArea = new javax.swing.JLayeredPane();
        chatViewer = new javax.swing.JScrollPane();
        chatOutput = new javax.swing.JTextArea();
        chatInput = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        spTreeAndEditor = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        sourceTree = new javax.swing.JTree();
        codePane = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        miNewProj = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CodeBuddy Alpha");
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        lbCaption.setFont(new java.awt.Font("Tahoma", 0, 18));
        lbCaption.setText("CoderBuddy");

        spMiddle.setDividerLocation(200);
        spMiddle.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        spMiddle.setPreferredSize(new java.awt.Dimension(855, 474));
        spMiddle.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spMiddlePropertyChange(evt);
            }
        });

        spMsgAndChat.setDividerLocation(400);
        spMsgAndChat.setLastDividerLocation(400);
        spMsgAndChat.setPreferredSize(new java.awt.Dimension(855, 365));
        spMsgAndChat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spMsgAndChatPropertyChange(evt);
            }
        });

        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 113));

        messageFrame.setColumns(20);
        messageFrame.setEditable(false);
        messageFrame.setRows(5);
        messageFrame.setPreferredSize(new java.awt.Dimension(400, 94));
        jScrollPane1.setViewportView(messageFrame);

        spMsgAndChat.setLeftComponent(jScrollPane1);

        chatViewer.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatViewer.setHorizontalScrollBar(null);

        chatOutput.setColumns(20);
        chatOutput.setEditable(false);
        chatOutput.setLineWrap(true);
        chatOutput.setRows(5);
        chatViewer.setViewportView(chatOutput);

        chatViewer.setBounds(0, 0, 450, 170);
        lpChatArea.add(chatViewer, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        chatInput.setBounds(10, 180, 360, -1);
        lpChatArea.add(chatInput, javax.swing.JLayeredPane.DEFAULT_LAYER);

        sendButton.setLabel("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        sendButton.setBounds(380, 180, 70, -1);
        lpChatArea.add(sendButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        spMsgAndChat.setRightComponent(lpChatArea);

        spMiddle.setRightComponent(spMsgAndChat);

        spTreeAndEditor.setDividerLocation(200);

        sourceTree.setPreferredSize(new java.awt.Dimension(200, 200));
        jScrollPane2.setViewportView(sourceTree);

        spTreeAndEditor.setLeftComponent(jScrollPane2);
        spTreeAndEditor.setRightComponent(codePane);

        spMiddle.setLeftComponent(spTreeAndEditor);

        jMenu1.setText("File");

        miNewProj.setText("New Project...");
        miNewProj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNewProjActionPerformed(evt);
            }
        });
        jMenu1.add(miNewProj);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spMiddle, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbCaption)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lbCaption)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spMiddle, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void spMsgAndChatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_spMsgAndChatPropertyChange
        String propertyName = evt.getPropertyName();

        if (propertyName.equals(
                javax.swing.JSplitPane.LAST_DIVIDER_LOCATION_PROPERTY))
        {  
          ResizeChatArea();
          
        }
    }//GEN-LAST:event_spMsgAndChatPropertyChange

    private void spMiddlePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_spMiddlePropertyChange
        String propertyName = evt.getPropertyName();

        if (propertyName.equals(
                javax.swing.JSplitPane.LAST_DIVIDER_LOCATION_PROPERTY))
        {
            ResizeChatArea();
        }
    }//GEN-LAST:event_spMiddlePropertyChange

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        if (this.getState() == 0)
        {
            validate();
        }
    }//GEN-LAST:event_formWindowStateChanged

    private void miNewProjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNewProjActionPerformed
        new NewProjGUI(this, true).setVisible(true);

        readCurrProj();
    }//GEN-LAST:event_miNewProjActionPerformed

    private void readCurrProj()
    {
        spTreeAndEditor.setLeftComponent(jScrollPane2);
        //sourceTree.removeAll();

        String projName = currProjLoc.substring(currProjLoc.lastIndexOf("/")+1,
            currProjLoc.lastIndexOf("."));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new ProjFile(
                projName, false));
        DefaultMutableTreeNode src = new DefaultMutableTreeNode(new ProjFile(
                "src", false));
        root.add(src);

        try
        {
            FileInputStream fstream = new FileInputStream(currProjLoc);

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine;            
            strLine = br.readLine();

            if (!strLine.equals("<src>"))
            {
                JOptionPane.showMessageDialog(null,
                    "Error: Invalid project file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                in.close();
                return;
            }

            while (!(strLine = br.readLine()).equals("</src>"))
            {              
              src.add(new DefaultMutableTreeNode(new ProjFile(
                    strLine.substring(strLine.lastIndexOf("/")+1), true)));
            }

            in.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }

        ProjTreeModel tm = new ProjTreeModel(root);
        this.sourceTree.setModel(tm);
    }

    @Override
    public void validate()
    {
        super.validate();

        int height = this.getHeight() - lbCaption.getHeight() - 59;
        int width = this.getWidth() - 15;

        spMiddle.setSize(width, height);
        spMiddle.setLocation(spMiddle.getX(), 23);

        ResizeChatArea();
    }

    private void ResizeChatArea()
    {
        int chatAreaHeight = spMiddle.getHeight() -
            spMiddle.getDividerLocation() - spMiddle.getDividerSize() - 4;
        int chatAreaWidth = spMiddle.getWidth() -
            spMsgAndChat.getDividerLocation() -
            spMsgAndChat.getDividerSize() - 4;
        lpChatArea.setSize(chatAreaWidth, chatAreaHeight);
                
        chatViewer.setSize(chatAreaWidth - 2, chatAreaHeight - 30);
        chatInput.setSize(chatAreaWidth - 92, 20);
        chatInput.setLocation(chatInput.getX(), chatAreaHeight - 24);
        sendButton.setSize(70, 23);
        sendButton.setLocation(chatAreaWidth - sendButton.getWidth() - 2,
            chatAreaHeight - 24);
    }

    private void handleChatSubmission() {
        String inputText = chatInput.getText();

        if (inputText.length() > 0) {
        	if (inputText.length() < 256) {
        		ConnectionManager.sendString(user + ":" + inputText);
        		chatInput.setText("");
        	} else {
        		JOptionPane.showMessageDialog(null,
                        "Error: Message length is limited to 256 characters.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
        	}
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField chatInput;
    private javax.swing.JTextArea chatOutput;
    private javax.swing.JScrollPane chatViewer;
    private javax.swing.JTabbedPane codePane;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbCaption;
    private javax.swing.JLayeredPane lpChatArea;
    private javax.swing.JTextArea messageFrame;
    private javax.swing.JMenuItem miNewProj;
    private javax.swing.JButton sendButton;
    private javax.swing.JTree sourceTree;
    private javax.swing.JSplitPane spMiddle;
    private javax.swing.JSplitPane spMsgAndChat;
    private javax.swing.JSplitPane spTreeAndEditor;
    // End of variables declaration//GEN-END:variables

}
