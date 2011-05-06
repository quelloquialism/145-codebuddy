/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 *
 * @author Mike
 */
public class ProjFile
{
    private String text;
    private JTextPane textPane;
    private JScrollPane scrollPane;
    private JPanel panel;
    private boolean isFile;

    public ProjFile(String text, boolean isfile)
    {
        this.text = text;
        this.isFile = isfile;

        if (isfile)
        {
            this.textPane = new JTextPane();
            StyledDocument doc = textPane.getStyledDocument();
            addStylesToDocument(doc);

            try
            {
                doc.insertString(0, "Hello world!", doc.getStyle("regular"));
            }
            catch (BadLocationException e)
            {
                System.err.println("Couldn't insert initial text into text pane.");
            }

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

    public boolean getIsFile()
    {
        return this.isFile;
    }

    private void addStylesToDocument(StyledDocument doc)
    {
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "Courier New");

        Style s = doc.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);
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
