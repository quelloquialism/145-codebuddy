/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;
import com.sun.tools.javac.Main;

/**
 *
 * @author Mike
 */
public class Compiler
{
    private ClientGUI client;
    private JTree sourceTree;
    private Main javaC = new Main();
    private CompilerOutStream outStream;
    private String runnableFile;

    public Compiler(ClientGUI client)
    {
        this.client = client;
        this.sourceTree = client.getSrcTree();
        this.outStream = new CompilerOutStream(this.client.getOutputPane());
        this.runnableFile = null;
    }

    public void SetRunnable(String run)
    {
        this.runnableFile = run;
    }

    public void compileAll() throws Exception
    {
        this.client.getOutputPane().setText("");
        compileSrcTree((DefaultMutableTreeNode)
                this.sourceTree.getModel().getRoot());
    }

    public void compileSrcTree(DefaultMutableTreeNode parentNode) throws Exception
    {
        ProjFile srcNode = (ProjFile)parentNode.getUserObject();

        if (srcNode.isFile())
        {
            if (srcNode.getText().endsWith(".java"))
            {
                String output;
                PrintWriter errors = new PrintWriter(System.err);

                System.setErr(new PrintStream(this.outStream, true));
                String [] source = 
                        { srcNode.getPath() + "\\" + srcNode.getText() };
                int result = Main.compile(source, errors);
                System.setErr(System.err);

                if (result == 0)
                    this.client.getOutputPane().append(srcNode.getPath()+"\\"+
                            srcNode.getText() + " compiled successfully.\n");
            }
        }

        for (int i = 0; i < parentNode.getChildCount(); i++)
            compileSrcTree((DefaultMutableTreeNode)parentNode.getChildAt(i));
    }

    void runRunnable()
    {
        if (this.runnableFile == null)
        {
            JOptionPane.showMessageDialog(null,
                    "Error: You have not set a runnable file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.client.getOutputPane().setText("");
        
        try
        {
            Process p = Runtime.getRuntime().exec("java "+this.runnableFile);
            
            StreamThread outThread = new StreamThread(p.getInputStream());
            StreamThread errThread = new StreamThread(p.getErrorStream());

            outThread.start();
            errThread.start();

            p.waitFor();

            this.client.getOutputPane().append("STDOUT: " +
                    outThread.getOutput() + "\n");
            this.client.getOutputPane().append("STDERR: " +
                    errThread.getOutput() + "\n");

            outThread.interrupt();
            errThread.interrupt();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public String BuildPackageString(DefaultMutableTreeNode node, String build)
    {
        DefaultMutableTreeNode parent =
                (DefaultMutableTreeNode)node.getParent();

        if (parent == null)
            return build;

        ProjFile srcNode = (ProjFile)node.getUserObject();

        if (srcNode.isPackage() && !srcNode.getText().equals("src"))
        {
            if (build.equals(""))
                build += srcNode.getText();
            else
                build = srcNode.getText() + "." + build;
        }

        return BuildPackageString(parent, build);
    }
}
