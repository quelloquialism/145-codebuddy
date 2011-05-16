/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import javax.swing.text.*;
import java.awt.*;
import javax.swing.event.*;

/**
 *
 * @author Mike
 */
public class LineNumberCaret implements Caret
{
    public int getDot()
    {
        return 0;
    }

    public int getMark()
    {
        return 0;
    }

    public int getBlinkRate()
    {
        return 0;
    }

    public Point getMagicCaretPosition()
    {
        return new Point(0, 0);
    }

    public void moveDot(int dot)
    {
    }

    public void setDot(int dot)
    {
    }

    public void setBlinkRate(int rate)
    {
    }

    public void setMagicCaretPosition(Point p)
    {
    }

    public void setSelectionVisible(boolean v)
    {
    }

    public void setVisible(boolean v)
    {
    }

    public boolean isSelectionVisible()
    {
        return false;
    }

    public boolean isVisible()
    {
        return false;
    }

    public void addChangeListener(ChangeListener l)
    {
    }

    public void removeChangeListener(ChangeListener l)
    {
    }

    public void paint(Graphics g)
    {
    }

    public void install(JTextComponent c)
    {
    }

    public void deinstall(JTextComponent c)
    {
    }
}
