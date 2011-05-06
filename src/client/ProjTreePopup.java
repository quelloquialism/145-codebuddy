/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.*;

/**
 *
 * @author Mike
 */
public class ProjTreePopup extends JPopupMenu
{
    private JMenuItem menuItem;
    private DefaultMutableTreeNode node;
    private String path;
    private String filename;
    private ClientGUI client;

    public ProjTreePopup(DefaultMutableTreeNode node, String path,
            ClientGUI client)
    {
        ProjFile srcNode = (ProjFile)node.getUserObject();

        if (srcNode.getText().equals("src"))
        {
            menuItem = new JMenuItem("New Source File");
            add(menuItem);            
        }

        this.node = node;
        this.path = path;
        this.client = client;
    }

    public void init()
    {
        ProjFile srcNode = (ProjFile)this.node.getUserObject();

        if (srcNode.getText().equals("src"))
        {
            menuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    new NewFileGUI(null, true, path, 
                            ProjTreePopup.this).setVisible(true);

                    if (!filename.equals(""))
                    {
                        String fileOnly = filename.substring(
                                filename.lastIndexOf("/")+1);

                        ProjFile pf = new ProjFile(fileOnly, true);
                        DefaultMutableTreeNode child =
                                new DefaultMutableTreeNode(pf);                        
                        ((DefaultTreeModel)client.getSrcTree().getModel()).
                                insertNodeInto(child, node,
                                node.getChildCount());

                        ProjFile srcNode = (ProjFile)child.getUserObject();

                        JTabbedPane pane = client.getCodePane();
                        JPanel panel = srcNode.getPanel();

                        pane.addTab(fileOnly, null, panel, fileOnly);
                    }
                }
            });
        }
    }

    public void setFilename(String file)
    {
        this.filename = file;
    }

}
