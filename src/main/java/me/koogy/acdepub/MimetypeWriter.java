package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author adean
 */
public class MimetypeWriter {

    public static void write(File dir) {

        PrintStream p = null;
        try {
            File file = new File(dir, "mimetype");
            p = new PrintStream(new FileOutputStream(file));
            
            p.print("application/epub+zip");
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }            
}
