/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;


/**
 *
 * @author Mike
 */
public class ButtonTabComponent extends JPanel {
    private final JTabbedPane pane;
    private ProjFile node;
    private JLabel label;
    private ClientGUI client;

    public ButtonTabComponent(final JTabbedPane pane, ProjFile node,
                                ClientGUI client)
    {        
        //unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));

        this.node = node;
        this.client = client;

        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        setOpaque(false);

        //make JLabel read titles from JTabbedPane
        this.label = new JLabel() {
            @Override
            public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };

        add(this.label);
        //add more space between the label and the button
        this.label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        int minWidth = ((int)(this.label.getFontMetrics(
                this.label.getFont()).getStringBounds(node.getText(),
                this.label.getGraphics()).getWidth()) +
                this.label.getInsets().left + this.label.getInsets().right);
        this.label.setBounds(0, 0, minWidth, this.label.getHeight());

        //tab button
        JButton button = new TabButton();
        add(button);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    public ProjFile getProjFileNode()
    {
        return this.node;
    }

    public void fixBounds()
    {
        int minWidth = ((int)(this.label.getFontMetrics(
                this.label.getFont()).getStringBounds(this.label.getText(),
                this.label.getGraphics()).getWidth()) +
                this.label.getInsets().left + this.label.getInsets().right);
        this.label.setBounds(0, 0, minWidth, this.label.getHeight());
    }

    private class TabButton extends JButton implements ActionListener {
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e)
        {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            if (i != -1)
            {
                if (node.getIsStale())
                {
                    int res = JOptionPane.showConfirmDialog(null,
                            "Would you like "+
                            "to save your changes?",
                            "Choose an option",
                            JOptionPane.YES_NO_CANCEL_OPTION);

                    if (res == JOptionPane.YES_OPTION)
                        client.getFileIO().saveSrcFile(node);
                    else if (res == JOptionPane.CANCEL_OPTION)
                        return;
                }

                node.setIsStale(false);
                pane.remove(i);
            }
        }

        //we don't want to update UI for this button
        @Override
        public void updateUI() {
        }

        //paint the cross
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.RED);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}

