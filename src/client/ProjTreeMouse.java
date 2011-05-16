/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Mike
 */
public class ProjTreeMouse extends MouseAdapter
{
    private JTree sourceTree;
    private ClientGUI client;

    public ProjTreeMouse(ClientGUI client)
    {
        super();
        this.sourceTree = client.getSrcTree();
        this.client = client;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        super.mousePressed(e);

        if (e.getClickCount() == 2)
        {
            Point p = e.getPoint();
            TreePath path = this.sourceTree.getPathForLocation(p.x, p.y);
            
            if (path == null)
                return;

            DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode)path.getLastPathComponent();
            ProjFile srcNode = (ProjFile)node.getUserObject();

            if (!srcNode.isFile())
                return;

            JTabbedPane pane = this.client.getCodePane();

            int i;
            for (i = 0; i < pane.getTabCount(); i++)
            {
                ProjFile pfnode = ((ButtonTabComponent)
                        pane.getTabComponentAt(i)).getProjFileNode();
                if (pfnode == srcNode)
                {
                    pane.setSelectedIndex(i);
                    break;
                }
            }

            // it's not open, load from file
            if (i == pane.getTabCount())
            {
                JPanel panel = srcNode.getPanel();

                pane.add(srcNode.getText(), panel);
                pane.setTabComponentAt(pane.getTabCount()-1,
                        new ButtonTabComponent(pane, srcNode, this.client));
                this.client.getFileIO().readSrcFile(srcNode);
                srcNode.setLineNumbers();
                pane.setSelectedIndex(pane.getTabCount()-1);
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {   
        if (SwingUtilities.isRightMouseButton(e))
        {   
            Point p = e.getPoint();
            Component mc = e.getComponent();
          
            TreePath path = sourceTree.getPathForLocation(p.x, p.y);
          
            if (path != null)
            {
                DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode)path.getLastPathComponent();
                
                ProjFile srcNode = (ProjFile)node.getUserObject();

                ProjTreePopup ptpop = new ProjTreePopup(node,
                        srcNode.getPath(), this.client);
                ptpop.init();
              
                ptpop.show(mc, e.getX(),e.getY());
              
                if (ptpop.getParent().getX() == 0)
                    ptpop.show(mc,e.getX(),e.getY()-ptpop.getHeight());
            }
        }
    }
}
