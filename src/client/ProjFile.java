/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private ClientGUI client;
    private boolean isStale;

    public ProjFile(String txt, String path, int type, ClientGUI c)
    {
        this.text = txt;
        this.type = type;
        this.path = path;
        this.client = c;
        this.isStale = false;

        if (type == TYPE_FILE)
        {
            DefaultSyntaxKit.initKit();

            this.textPane = new JEditorPane();
            this.textPane.setContentType("text/java");
            
            this.panel = new JPanel(false);            
            this.scrollPane = new JScrollPane(this.textPane);

            this.textPane.addKeyListener(new KeyListener()
            {
                public void keyPressed(KeyEvent e)
                {
                }

                public void keyReleased(KeyEvent e)
                {
                }

                public void keyTyped(KeyEvent e)
                {
                    if (!isStale)
                    {
                        int i;
                        JTabbedPane tpane = client.getCodePane();
                        for (i = 0; i < tpane.getTabCount(); i++)
                            if (((ButtonTabComponent)
                                tpane.getTabComponentAt(i)).getProjFileNode() ==
                                ProjFile.this)
                                break;

                        tpane.setTitleAt(i, text+"*");
                        ((ButtonTabComponent)
                                tpane.getTabComponentAt(i)).fixBounds();
                        isStale = true;
                    }
                }
            });

            this.panel.add(this.scrollPane);
            this.panel.setLayout(new GridLayout(1, 1));
        }
    }

    public boolean getIsStale()
    {
        return this.isStale;
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

    public JEditorPane getPane()
    {
        return this.textPane;
    }

    public void setIsStale(boolean stale)
    {
        this.isStale = stale;
    }

    @Override
    public String toString()
    {
        return this.text;
    }
}
