/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.*;

/**
 * The idea behind this class is if we run a command prompt external program
 * and we don't know if it's going to spew into stdout or stderr we can
 * create a thread for each and kill it if it blocks on us.
 * @author Mike
 */
public class StreamThread extends Thread
{
    private InputStream is;
    private String output = "";
    private boolean isDone = false;

    public StreamThread(InputStream is)
    {
        this.is = is;
    }

    @Override
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ((line = br.readLine()) != null)
            {
                this.output += line + "\n";
            }
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }

        this.isDone = true;
    }

    public boolean isDone()
    {
        return this.isDone;
    }

    public String getOutput()
    {
        return this.output;
    }
}
