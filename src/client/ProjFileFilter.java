/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.File;
import javax.swing.filechooser.*;

/**
 *
 * @author Mike
 */
public class ProjFileFilter extends FileFilter
{
    public boolean accept(File f)
    {
        if (f.isDirectory())
            return true;

        String extension = f.getName().substring(
                f.getName().lastIndexOf(".")+1);

        if (extension.equals("proj"))
            return true;

        return false;
    }

    public String getDescription()
    {
        return "Project Files";
    }
}
