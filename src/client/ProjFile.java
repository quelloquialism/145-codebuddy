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
    private JTextArea lineNumberPane;
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
            this.lineNumberPane = new JTextArea("1");
            this.lineNumberPane.setBackground(new Color(238, 238, 238));
            this.lineNumberPane.setFont(new Font("Courier New", Font.PLAIN,
                    15));
            this.lineNumberPane.setBorder(
                    BorderFactory.createEmptyBorder(4, 0, 0, 0));
            this.lineNumberPane.setCaret(new LineNumberCaret());

            this.panel = new JPanel(false);            
            this.scrollPane = new JScrollPane(this.textPane);
            this.scrollPane.setRowHeaderView(this.lineNumberPane);

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
                        markStale();

                    setLineNumbers();
                }
            });

            this.panel.add(this.scrollPane);
            this.panel.setLayout(new GridLayout(1, 1));
        }
    }

    public JTextArea getLineNumberPane()
    {
        return this.lineNumberPane;
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

    public JScrollPane getScrollPane()
    {
        return this.scrollPane;
    }

    public void setIsStale(boolean stale)
    {
        this.isStale = stale;
    }

    public void markStale()
    {
        int i;
        JTabbedPane tpane = this.client.getCodePane();
        for (i = 0; i < tpane.getTabCount(); i++)
            if (((ButtonTabComponent)
                tpane.getTabComponentAt(i)).getProjFileNode() ==
                ProjFile.this)
                break;

        tpane.setTitleAt(i, this.text+"*");
        ((ButtonTabComponent)
                tpane.getTabComponentAt(i)).fixBounds();
        this.isStale = true;
    }

    public void setLineNumbers()
    {
        String text = this.textPane.getText();

        int idx = 0;
        int nlCnt = 1;

        while (idx < text.length())
        {
            if (text.substring(idx, idx+1).equals("\n"))
                nlCnt++;
            idx++;
        }

        String lineStr = "";
        String maxNum = Integer.toString(nlCnt);

        for (int i = 0; i < nlCnt; i++)
        {
            String currNum = Integer.toString(i+1);
            int spaces = maxNum.length() - currNum.length();

            for (int j = 0; j < spaces; j++)
                lineStr += " ";

            lineStr += Integer.toString(i+1) + "\n";
        }

        this.lineNumberPane.setText(lineStr);
    }

    @Override
    public String toString()
    {
        return this.text;
    }
}
