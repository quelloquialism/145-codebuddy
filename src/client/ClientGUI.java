/*
 * ClientGUI.java
 *
 * Created on Apr 2, 2011, 7:49:52 PM
 */

package client;

import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.ListModel;

/**
 *
 * @author Nathan
 */
public class ClientGUI extends javax.swing.JFrame {

    private String user = null;
    private String currProjLoc = "";
    private JLabel lbNoProj = new JLabel();
    private JPanel panNoProj = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JScrollPane spNoProj = new javax.swing.JScrollPane();
    private JScrollPane spOutput;
    private JScrollPane spBuddyList;
    private JScrollPane spSourceTree;
    private JScrollPane spRemoteList;
    private JTextArea taOutput = new javax.swing.JTextArea("Output");
    private JList buddyList;
    private DefaultListModel buddyListModel = new DefaultListModel();
    private FileIO fileIO;
    private Compiler compiler;
    private JTree sourceTree = new JTree();
    private JList remoteFileList;
    private JPanel pRemoteFiles;
    private JButton btRefreshRemote = new JButton("Refresh");
    private JButton btMapRemote = new JButton("Map");
    private DefaultListModel remoteFileListModel = new DefaultListModel();
    
    /** Creates new form ClientsGUI */
    public ClientGUI(String user) {
        this.user = user;
        initComponents();
        this.setLayout(new java.awt.BorderLayout());

        this.lbNoProj.setText("<No Project Loaded>");
        this.lbNoProj.setLocation(0, 0);
        this.lbNoProj.setAlignmentX(LEFT_ALIGNMENT);
        
        this.panNoProj.add(lbNoProj);
        this.panNoProj.setPreferredSize(new Dimension(200, 200));
        this.panNoProj.setBackground(Color.white);
        this.panNoProj.setAlignmentX(LEFT_ALIGNMENT);

        this.spNoProj.getViewport().add(panNoProj);
        this.spNoProj.setVisible(true);
        this.spTreeAndEditor.setLeftComponent(spNoProj);

        this.taOutput.setEditable(false);
        this.spOutput = new JScrollPane(this.taOutput);
        this.tpOutputFrame.addTab("Output", this.spOutput);

        this.buddyList = new JList(this.buddyListModel);
        this.buddyList.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.spBuddyList = new JScrollPane(this.buddyList);
        this.tpOutputFrame.addTab("Buddy List", this.spBuddyList);

        this.spSourceTree = new JScrollPane(this.sourceTree);
        this.tpSource.addTab("Local", this.spSourceTree);
        
        this.remoteFileList = new JList(this.remoteFileListModel);
        this.remoteFileList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        this.spRemoteList = new JScrollPane(this.remoteFileList);
        this.pRemoteFiles = new JPanel();
        this.pRemoteFiles.setLayout(new CustomGridLayout(3,1));
        this.pRemoteFiles.add(this.spRemoteList);
        this.pRemoteFiles.add(this.btRefreshRemote);
        this.pRemoteFiles.add(this.btMapRemote);
        this.tpSource.addTab("Remote", this.pRemoteFiles);

        this.btRefreshRemote.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                populateRemoteList();
            }
        });

        populateRemoteList();

        ChatUpdateThread cut = new ChatUpdateThread();
        cut.start();
    }

    public void init()
    {
        this.sourceTree.addMouseListener(new ProjTreeMouse(this));
        this.fileIO = new FileIO(this);
        this.compiler = new Compiler(this);

        this.btMapRemote.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int select = remoteFileList.getSelectedIndex();

                if (select < 0)
                {
                    JOptionPane.showMessageDialog(null,
                        "Error: Nothing is selected for the map.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String map =
                        (String)remoteFileList.getModel().getElementAt(select);

                new MapFileGUI(map, ClientGUI.this, true).setVisible(true);
            }
        });
    }

    public JScrollPane getSrcScrollPane()
    {
        return this.spSourceTree;
    }

    public FileIO getFileIO()
    {
        return this.fileIO;
    }

    public JTextArea getOutputPane()
    {
        return this.taOutput;
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

    public Compiler getCompiler()
    {
        return this.compiler;
    }

    private void populateRemoteList()
    {
        String [] fileStrs = ConnectionManager.getFileList();

        if (fileStrs != null)
        {
            remoteFileListModel = new DefaultListModel();
            for (int i = 0; i < fileStrs.length; i++)
                remoteFileListModel.addElement(fileStrs[i]);
            remoteFileList.setModel(remoteFileListModel);
        }
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
        lpChatArea = new javax.swing.JLayeredPane();
        chatViewer = new javax.swing.JScrollPane();
        chatOutput = new javax.swing.JTextArea();
        chatInput = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        tpOutputFrame = new javax.swing.JTabbedPane();
        spTreeAndEditor = new javax.swing.JSplitPane();
        codePane = new javax.swing.JTabbedPane();
        tpSource = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        miNewProj = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miOpenProject = new javax.swing.JMenuItem();
        miCloseProject = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        miSaveProj = new javax.swing.JMenuItem();
        miSaveFile = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        miExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        miFindReplace = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        miCompileProj = new javax.swing.JMenuItem();
        miRun = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CodeBuddy Alpha");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        lbCaption.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbCaption.setText("CodeBuddy");

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

        tpOutputFrame.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tpOutputFrame.setPreferredSize(new java.awt.Dimension(400, 94));
        spMsgAndChat.setLeftComponent(tpOutputFrame);

        spMiddle.setRightComponent(spMsgAndChat);

        spTreeAndEditor.setDividerLocation(200);
        spTreeAndEditor.setRightComponent(codePane);

        tpSource.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        spTreeAndEditor.setLeftComponent(tpSource);

        spMiddle.setLeftComponent(spTreeAndEditor);

        jMenu1.setText("File");

        miNewProj.setText("New Project...");
        miNewProj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNewProjActionPerformed(evt);
            }
        });
        jMenu1.add(miNewProj);
        jMenu1.add(jSeparator1);

        miOpenProject.setText("Open Project...");
        miOpenProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miOpenProjectActionPerformed(evt);
            }
        });
        jMenu1.add(miOpenProject);

        miCloseProject.setText("Close Project");
        miCloseProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCloseProjectActionPerformed(evt);
            }
        });
        jMenu1.add(miCloseProject);
        jMenu1.add(jSeparator2);

        miSaveProj.setText("Save Project");
        miSaveProj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSaveProjActionPerformed(evt);
            }
        });
        jMenu1.add(miSaveProj);

        miSaveFile.setText("Save File");
        miSaveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSaveFileActionPerformed(evt);
            }
        });
        jMenu1.add(miSaveFile);
        jMenu1.add(jSeparator3);

        miExit.setText("Exit");
        miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExitActionPerformed(evt);
            }
        });
        jMenu1.add(miExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        miFindReplace.setText("Find/Replace...");
        miFindReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFindReplaceActionPerformed(evt);
            }
        });
        jMenu2.add(miFindReplace);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Run");

        miCompileProj.setText("Compile Project");
        miCompileProj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCompileProjActionPerformed(evt);
            }
        });
        jMenu3.add(miCompileProj);

        miRun.setText("Run Project");
        miRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRunActionPerformed(evt);
            }
        });
        jMenu3.add(miRun);

        jMenuBar1.add(jMenu3);

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

    private boolean showSavePromptDialog(java.awt.event.ActionEvent evt)
    {
        if (!this.currProjLoc.equals(""))
        {
            int res = JOptionPane.showConfirmDialog(null, "Would you like "+
                    "to save the current project?",
                    "Choose an option",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (res == JOptionPane.YES_OPTION)
                miSaveProjActionPerformed(evt);
            else if (res == JOptionPane.CANCEL_OPTION)
                return false;
        }

        return true;
    }

    private void miNewProjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNewProjActionPerformed

        if (!showSavePromptDialog(evt))
            return;
        
        new NewProjGUI(this, true).setVisible(true);

    }//GEN-LAST:event_miNewProjActionPerformed

    private void miSaveProjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSaveProjActionPerformed
        try
        {
            FileWriter fstream = new FileWriter(currProjLoc);
            BufferedWriter out = new BufferedWriter(fstream);

            DefaultMutableTreeNode node = ((DefaultMutableTreeNode)
                    ((DefaultMutableTreeNode)
                    this.sourceTree.getModel().getRoot()).getChildAt(0));

            fileIO.saveSrcTree(node, out);

            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_miSaveProjActionPerformed

    private void miOpenProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miOpenProjectActionPerformed
        
        if (!showSavePromptDialog(evt))
            return;

        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new ProjFileFilter());
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            this.currProjLoc = file.getPath();
            readCurrProj();
        }
    }//GEN-LAST:event_miOpenProjectActionPerformed

    private void miCloseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCloseProjectActionPerformed
        
        if (!showSavePromptDialog(evt))
            return;

        // Note: the src tree stays the same, but we cover it up until the
        // next open project or new project :)
        spTreeAndEditor.setLeftComponent(spNoProj);
        this.currProjLoc = "";
    }//GEN-LAST:event_miCloseProjectActionPerformed

    private void miSaveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSaveFileActionPerformed
        ButtonTabComponent comp = (ButtonTabComponent)
                this.codePane.getTabComponentAt(
                this.codePane.getSelectedIndex());

        ProjFile pf = comp.getProjFileNode();        
        fileIO.saveSrcFile(pf);
    }//GEN-LAST:event_miSaveFileActionPerformed

    private void miExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_miExitActionPerformed

    private void miCompileProjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCompileProjActionPerformed
        try
        {
            this.compiler.compileAll();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }//GEN-LAST:event_miCompileProjActionPerformed

    private void miFindReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFindReplaceActionPerformed
        new FindReplaceGUI(this).setVisible(true);
    }//GEN-LAST:event_miFindReplaceActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try
        {
            ConnectionManager.logout(this.user);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_formWindowClosing

    private void miRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRunActionPerformed
        this.compiler.runRunnable();
    }//GEN-LAST:event_miRunActionPerformed

    public void readCurrProj()
    {
        spTreeAndEditor.setLeftComponent(this.tpSource);
        fileIO.readProj(this.currProjLoc);
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
        		ConnectionManager.sendChat(user, inputText);
        		chatInput.setText("");
        	} else {
        		JOptionPane.showMessageDialog(null,
                        "Error: Message length is limited to 256 characters.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
        	}
        }
    }
    
    private class ChatUpdateThread extends Thread {
    	public void run() {
    		while (true) {
    			try {
    				String[] msgs = ConnectionManager.updateChat();
    				if (msgs.length > 0) {
    					for (String s : msgs) {
    						chatOutput.append("\n" + s);
    					}
    					chatOutput.setCaretPosition(chatOutput.getDocument().getLength());
    				}

                                String[] buddies =
                                        ConnectionManager.getBuddyList();

                                boolean listChanged = false;

                                if (buddies.length != buddyListModel.getSize())
                                    listChanged = true;
                                else
                                    for (int i = 0; i < buddies.length; i++)
                                    {
                                        if (!buddies[i].equals(buddyListModel.get(i)))
                                        {
                                            listChanged = true;
                                            break;
                                        }
                                    }

                                if (listChanged)
                                {
                                    buddyListModel = new DefaultListModel();
                                    for (int i = 0; i < buddies.length; i++)
                                        buddyListModel.addElement(buddies[i]);
                                    buddyList.setModel(buddyListModel);
                                }

    				sleep(100);
    			} catch (InterruptedException e) {
    				
    			} catch (IOException e) {
    				
    			}
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
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JLabel lbCaption;
    private javax.swing.JLayeredPane lpChatArea;
    private javax.swing.JMenuItem miCloseProject;
    private javax.swing.JMenuItem miCompileProj;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miFindReplace;
    private javax.swing.JMenuItem miNewProj;
    private javax.swing.JMenuItem miOpenProject;
    private javax.swing.JMenuItem miRun;
    private javax.swing.JMenuItem miSaveFile;
    private javax.swing.JMenuItem miSaveProj;
    private javax.swing.JButton sendButton;
    private javax.swing.JSplitPane spMiddle;
    private javax.swing.JSplitPane spMsgAndChat;
    private javax.swing.JSplitPane spTreeAndEditor;
    private javax.swing.JTabbedPane tpOutputFrame;
    private javax.swing.JTabbedPane tpSource;
    // End of variables declaration//GEN-END:variables

}
