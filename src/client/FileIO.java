/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.text.*;

/**
 *
 * @author Mike
 */
public class FileIO
{
    private JTree sourceTree;
    private ClientGUI client;
    
    public FileIO(ClientGUI client)
    {
        this.client = client;
        this.sourceTree = client.getSrcTree();
    }

    public void readSrcFile(ProjFile srcNode)
    {
        String fileStr = srcNode.getPath() + "\\" + srcNode.getText();

        try
        {
            FileReader fstream = new FileReader(fileStr);
            BufferedReader in = new BufferedReader(fstream);

            String text = "";
            int ch;

            while ((ch = in.read()) >= 0)
            {
                text = text + (char)ch;
            }

            in.close();

            // load the text
            srcNode.getPane().setText(text);
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void saveSrcFile(ProjFile srcNode)
    {
        String text = srcNode.getPane().getText();
        String fileStr = srcNode.getPath() + "\\" + srcNode.getText();

        try
        {
            FileWriter fstream = new FileWriter(fileStr);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(text);
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }

        srcNode.setIsStale(false);

        // remove the asterisk
        JTabbedPane pane = this.client.getCodePane();
        int i;
        for (i = 0; i < pane.getTabCount(); i++)
            if (((ButtonTabComponent)
                pane.getTabComponentAt(i)).getProjFileNode() == srcNode)
                break;
        pane.setTitleAt(i, srcNode.getText());
        ((ButtonTabComponent)
                pane.getTabComponentAt(i)).fixBounds();
    }

    public void readProj(String projLoc)
    {
        String projName = projLoc.substring(projLoc.lastIndexOf("\\")+1,
            projLoc.lastIndexOf("."));
        String path = projLoc.substring(0, projLoc.lastIndexOf("\\"));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new ProjFile(
                projName, ".", ProjFile.TYPE_SYSTEM, this.client));
        DefaultMutableTreeNode src = new DefaultMutableTreeNode(new ProjFile(
                "src", path, ProjFile.TYPE_PACKAGE, this.client));
        root.add(src);

        try
        {
            FileInputStream fstream = new FileInputStream(projLoc);

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

            readSrcTree(src, br, "src");

            in.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }

        ProjTreeModel tm = new ProjTreeModel(root);
        this.sourceTree.setModel(tm);
    }

    public void readSrcTree(DefaultMutableTreeNode parentNode,
            BufferedReader br, String currPkg) throws Exception
    {
        String strLine = null;

        while(!(strLine = br.readLine()).equals("</"+currPkg+">"))
        {
            ProjFile srcNode = (ProjFile)parentNode.getUserObject();

            // if it's a package
            if (strLine.substring(0, 1).equals("<"))
            {
                String childPkg = strLine.substring(1, strLine.lastIndexOf(">"));

                String path = srcNode.getPath() + "\\" + childPkg;

                DefaultMutableTreeNode child = new DefaultMutableTreeNode(
                        new ProjFile(childPkg, path, ProjFile.TYPE_PACKAGE,
                        this.client));
                parentNode.add(child);

                readSrcTree(child, br, childPkg);
            }
            // it must be a file
            else
            {
                String path = srcNode.getPath();

                DefaultMutableTreeNode child = new DefaultMutableTreeNode(
                        new ProjFile(strLine, path, ProjFile.TYPE_FILE,
                        this.client));
                parentNode.add(child);
            }
        }
    }

    public void saveSrcTree(DefaultMutableTreeNode parentNode,
            BufferedWriter bw) throws Exception
    {
        ProjFile srcNode = (ProjFile)parentNode.getUserObject();

        if (srcNode.isFile())
            bw.write(srcNode.getText() + "\n");
        else if (srcNode.isPackage())
            bw.write("<"+srcNode.getText()+ ">\n");

        for (int i = 0; i < parentNode.getChildCount(); i++)
            saveSrcTree((DefaultMutableTreeNode)parentNode.getChildAt(i), bw);

        if (srcNode.isPackage())
            bw.write("</"+srcNode.getText()+ ">\n");
    }
}
