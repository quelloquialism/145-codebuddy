/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import javax.swing.*;
import java.awt.*;
import jsyntaxpane.DefaultSyntaxKit;


/**
 *
 * @author Mike
 */
public class ProjFile
{
    public static final int TYPE_FILE = 0;
    public static final int TYPE_PACKAGE = 1;
    public static final int TYPE_SYSTEM = 2;

    private String text;
    private String path;
    private JEditorPane textPane;
    private JScrollPane scrollPane;
    private JPanel panel;
    private int type;

    public ProjFile(String text, String path, int type)
    {
        this.text = text;
        this.type = type;
        this.path = path;

        if (type == TYPE_FILE)
        {
            DefaultSyntaxKit.initKit();

            this.textPane = new JEditorPane();
            this.textPane.setContentType("text/java");
            
            this.panel = new JPanel(false);            
            this.scrollPane = new JScrollPane(this.textPane);

            this.panel.add(this.scrollPane);
            this.panel.setLayout(new GridLayout(1, 1));            
        }
    }

    public String getText()
    {
        return this.text;
    }

    public String getPath()
    {
        return this.path;
    }

    public boolean isFile()
    {
        return this.type == TYPE_FILE;
    }

    public boolean isPackage()
    {
        return this.type == TYPE_PACKAGE;
    }

    public JPanel getPanel()
    {
        return this.panel;
    }

    @Override
    public String toString()
    {
        return this.text;
    }
}
