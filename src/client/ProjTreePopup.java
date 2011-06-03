/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.tree.*;

/**
 *
 * @author Mike
 */
public class ProjTreePopup extends JPopupMenu
{
    private JMenuItem miNewSrc;
    private JMenuItem miNewPkg;
    private JMenuItem miSetRun;
    private JMenuItem miSaveToServer;
    private JMenuItem miReadFromServer;
    private JMenuItem miUnmap;
    private DefaultMutableTreeNode node;
    private String path;
    private String name;
    private ClientGUI client;

    public ProjTreePopup(DefaultMutableTreeNode node, String path,
            ClientGUI client)
    {
        ProjFile srcNode = (ProjFile)node.getUserObject();

        if (srcNode.isPackage())
        {
            miNewSrc = new JMenuItem("New Source File");
            add(miNewSrc);

            miNewPkg = new JMenuItem("New Package");
            add(miNewPkg);
        }
        else if (srcNode.isFile())
        {
            miSetRun = new JMenuItem("Set As Runnable");
            add(miSetRun);

            JSeparator sep = new JSeparator();
            add(sep);

            miSaveToServer = new JMenuItem("Save To Server");
            add(miSaveToServer);

            if (srcNode.getRemoteFileMap() != null)
            {
                miReadFromServer = new JMenuItem("Read From Server");
                add(miReadFromServer);

                miUnmap = new JMenuItem("Unmap");
                add(miUnmap);
            }
        }

        this.node = node;
        this.path = srcNode.getPath();
        this.client = client;
    }

    public void init()
    {
        ProjFile srcNode = (ProjFile)this.node.getUserObject();

        if (srcNode.isPackage())
        {
            miNewSrc.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    new NewFileGUI(null, true, path,
                            ProjTreePopup.this).setVisible(true);

                    if (!name.equals(""))
                    {
                        String fileOnly = name.substring(
                                name.lastIndexOf("\\")+1);
                        String pathOnly = name.substring(0,
                                name.lastIndexOf("\\"));

                        ProjFile pf = new ProjFile(fileOnly, pathOnly,
                                ProjFile.TYPE_FILE, client);

                        DefaultMutableTreeNode child =
                                new DefaultMutableTreeNode(pf);                        

                        ((DefaultTreeModel)client.getSrcTree().getModel()).
                                insertNodeInto(child, node,
                                node.getChildCount());

                        ProjFile srcNode = (ProjFile)child.getUserObject();

                        JTabbedPane pane = client.getCodePane();
                        JPanel panel = srcNode.getPanel();

                        pane.add(fileOnly, panel);
                        pane.setTabComponentAt(pane.getTabCount()-1,
                                new ButtonTabComponent(pane, srcNode, client));

                    }
                }
            });

            miNewPkg.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    new NewPackageGUI(null, true, path,
                            ProjTreePopup.this).setVisible(true);

                    if (!name.equals(""))
                    {
                        String dirOnly = name.substring(
                                name.lastIndexOf("\\")+1);
                        ProjFile pf = new ProjFile(dirOnly, name,
                                ProjFile.TYPE_PACKAGE, client);

                        DefaultMutableTreeNode child =
                                new DefaultMutableTreeNode(pf);

                        ((DefaultTreeModel)client.getSrcTree().getModel()).
                                insertNodeInto(child, node,
                                node.getChildCount());
                    }
                }
            });
        }
        else if (srcNode.isFile())
        {
            miSetRun.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    String dir = client.getCurrProjLoc().substring(0,
                            client.getCurrProjLoc().lastIndexOf("\\"));
                    String pkgs = 
                            client.getCompiler().BuildPackageString(node, "");

                    if (!pkgs.equals(""))
                        pkgs += ".";

                    ProjFile srcNode = (ProjFile)node.getUserObject();

                    client.getCompiler().SetRunnable("-classpath " +
                            dir + " " +  pkgs +
                            srcNode.getText().substring(0,
                            srcNode.getText().lastIndexOf(".")));
                }
            });

            miSaveToServer.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    ProjFile srcNode = (ProjFile)node.getUserObject();
                    String projFile = client.getCurrProjLoc();
                    String projPath = projFile.substring(0,
                            projFile.lastIndexOf("\\"));
                    String txt = client.getFileIO().readSrcFile(srcNode);

                    String partialPath = srcNode.getPath();
                    partialPath = partialPath.substring(projPath.length());

                    String fullPath = partialPath + "\\" + srcNode.getText();

                    ConnectionManager.storeFile(fullPath, txt);
                }
            });

            if (srcNode.getRemoteFileMap() != null)
            {
                miReadFromServer.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        ProjFile srcNode = (ProjFile)node.getUserObject();

                        srcNode.openTabForFile();

                        String txt =
                            ConnectionManager.retrieveFile(
                            srcNode.getRemoteFileMap());

                        srcNode.getPane().setText(txt);
                        srcNode.markStale();
                        srcNode.setLineNumbers();
                        JTabbedPane pane = client.getCodePane();
                        pane.setSelectedIndex(pane.getTabCount()-1);
                        srcNode.getPane().requestFocusInWindow();
                    }
                });

                miUnmap.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        ProjFile srcNode = (ProjFile)node.getUserObject();
                        srcNode.setRemoteFileMap(null);
                    }
                });
            }
        }
    }

    public DefaultMutableTreeNode getNode()
    {
        return this.node;
    }

    public void setNodeName(String name)
    {
        this.name = name;
    }

}
