/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FindReplaceGUI.java
 *
 * Created on May 20, 2011, 7:41:43 PM
 */

package client;

import javax.swing.*;
import javax.swing.text.*;
import java.util.regex.*;
import jsyntaxpane.*;

/**
 *
 * @author Mike
 */
public class FindReplaceGUI extends javax.swing.JFrame {

    private ClientGUI client;
    private int findNum = 0;

    /** Creates new form FindReplaceGUI */
    public FindReplaceGUI(ClientGUI client) {
        initComponents();

        this.client = client;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtFind = new javax.swing.JTextField();
        txtReplace = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        btnReplace = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnFindPrev = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Find/Replace");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setText("Find:");

        jLabel2.setText("Replace:");

        btnFind.setText("Find Next");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnReplace.setText("Replace");
        btnReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReplaceActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnFindPrev.setText("Find Previous");
        btnFindPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindPrevActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtReplace)
                    .addComponent(txtFind, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReplace, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFind, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFindPrev, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtReplace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFindPrev))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReplace)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void highlightSearchWords()
    {
        JTabbedPane pane = this.client.getCodePane();
        ButtonTabComponent tab = ((ButtonTabComponent)
                pane.getTabComponentAt(pane.getSelectedIndex()));
        tab.getProjFileNode().getPane().getHighlighter().removeAllHighlights();

        String words = txtFind.getText();
        int searchOffset;
        int currFind;

        JScrollPane spane = tab.getProjFileNode().getScrollPane();
        int numLines = tab.getProjFileNode().getPane().getDocument().
                getRootElements()[0].getElementCount();

        String line;
        int start, end;
        int totalCharCnt = 0;
        int findCnt = 0;

        for (int i = 0; i < numLines; i++)
        {
            start = tab.getProjFileNode().getPane().getDocument().
                    getRootElements()[0].getElement(i).getStartOffset();
            end = tab.getProjFileNode().getPane().getDocument().
                    getRootElements()[0].getElement(i).getEndOffset();
            try
            {
                line = tab.getProjFileNode().getPane().getDocument().getText(
                        start, end-start);
                searchOffset = 0;
                while ((currFind = line.indexOf(words, searchOffset)) > -1)
                {
                    findCnt++;

                    if (findCnt == this.findNum)
                    {
                        int pixelsDown = 4 + i * 18;
                        spane.getVerticalScrollBar().setValue(pixelsDown);
                    }

                    tab.getProjFileNode().getPane().getHighlighter().
                            addHighlight(totalCharCnt+currFind,
                            totalCharCnt+currFind+words.length(),
                            DefaultHighlighter.DefaultPainter);
                    searchOffset += words.length();
                }

                totalCharCnt += line.length();
            }
            catch (BadLocationException e)
            {
                System.err.println("Error: " + e.getMessage());
            }
        }

        // if we reached the last find, we want to stay there
        if (this.findNum == findCnt)
            this.findNum--;
    }

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed

        if (this.txtFind.getText().equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Error: Find text is invalid.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.findNum++;

        highlightSearchWords();
    }//GEN-LAST:event_btnFindActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        JTabbedPane pane = this.client.getCodePane();
        int idx = pane.getSelectedIndex();
        
        if (idx < 0)
            return;
        
        ButtonTabComponent tab = ((ButtonTabComponent)
                pane.getTabComponentAt(pane.getSelectedIndex()));
        tab.getProjFileNode().getPane().getHighlighter().removeAllHighlights();
    }//GEN-LAST:event_formWindowClosed

    private void btnFindPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindPrevActionPerformed
        if (this.txtFind.getText().equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Error: Find text is invalid.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.findNum--;
        if (this.findNum == 0)
            this.findNum = 1;

        highlightSearchWords();
    }//GEN-LAST:event_btnFindPrevActionPerformed

    private void btnReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReplaceActionPerformed
        if (this.txtFind.getText().equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Error: Find text is invalid.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTabbedPane pane = this.client.getCodePane();
        ButtonTabComponent tab = ((ButtonTabComponent)
                pane.getTabComponentAt(pane.getSelectedIndex()));
        String allText = tab.getProjFileNode().getPane().getText();
        int origLen = allText.length();

        Pattern p = Pattern.compile(this.txtFind.getText());
        Matcher m = p.matcher(allText); // get a matcher object
        int count = 0;
        while(m.find())
        {
            count++;

            String preText = "";
            String postText = "";

            if (m.start() > 0)
                preText = allText.substring(0, m.start());

            if (m.end() < allText.length())
                postText = allText.substring(m.end());

            allText = preText+this.txtReplace.getText()+postText;
            m = p.matcher(allText);
        }

        try
        {
            ((SyntaxDocument)tab.getProjFileNode().getPane().getDocument()).
                    replace(0, origLen, allText, null);
        }
        catch (BadLocationException e)
        {
            System.err.println("Error: " + e.getMessage());
        }

        tab.getProjFileNode().markStale();

        JOptionPane.showMessageDialog(null,
                ""+count+" matches were replaced.",
                "Replace",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnReplaceActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnFindPrev;
    private javax.swing.JButton btnReplace;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtFind;
    private javax.swing.JTextField txtReplace;
    // End of variables declaration//GEN-END:variables

}
