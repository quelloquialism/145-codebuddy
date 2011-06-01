/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.*;
import javax.swing.*;

/**
 *
 * @author Mike
 */
public class CompilerOutStream extends ByteArrayOutputStream
{
    private String lineSeparator;
    JTextArea outputTA;

    public CompilerOutStream(JTextArea ta)
    {
        super();
        this.lineSeparator = System.getProperty("line.separator");
        this.outputTA = ta;
    }

    @Override
    public void flush() throws IOException
    {
        String record;
        synchronized(this)
        {
            super.flush();
            record = this.toString();
            super.reset();

            if (record.length() == 0 || record.equals(lineSeparator)) {
                // avoid empty records
                return;
            }

            this.outputTA.append(record);
        }
    }
}
