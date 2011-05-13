/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import javax.swing.tree.*;

/**
 *
 * @author Mike
 */
public class ProjTreeModel extends DefaultTreeModel
{
    public ProjTreeModel(DefaultMutableTreeNode node)
    {
        super(node);
    }

    @Override
    public boolean isLeaf(Object onode)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)onode;
        return ((ProjFile)node.getUserObject()).isFile();
    }
}
