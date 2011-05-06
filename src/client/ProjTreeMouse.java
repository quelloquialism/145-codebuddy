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
                
                String pathNoFile = 
                        this.client.getCurrProjLoc().substring(0,
                        this.client.getCurrProjLoc().lastIndexOf("/"));

                ProjTreePopup ptpop = new ProjTreePopup(node,
                        pathNoFile, this.client);
                ptpop.init();
              
                ptpop.show(mc, e.getX(),e.getY());
              
                if (ptpop.getParent().getX() == 0)
                    ptpop.show(mc,e.getX(),e.getY()-ptpop.getHeight());
            }
        }
    }
}
