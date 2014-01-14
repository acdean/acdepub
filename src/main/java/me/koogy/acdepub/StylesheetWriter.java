package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Default stylesheet.
 * @author adean
 */
public class StylesheetWriter {

    public static void write(File dir) {

        PrintStream p = null;
        try {
            File file = new File(dir, "stylesheet.css");
            p = new PrintStream(new FileOutputStream(file));
            
            p.println("body { margin-left: 5%; margin-right: 5%; margin-top: 5%; margin-bottom: 5%; text-align: justify; font-size: medium; }");
            p.println("h1 { text-align: center; }");
            p.println("h2 { text-align: center; }");
            p.println("h3 { text-align: center; }");
            p.println("h4 { text-align: center; }");
            p.println("h5 { text-align: center; }");
            p.println("h6 { text-align: center; }");
            p.println("h1.title { }");
            p.println("h2.author { }");
            p.println("h3.date { }");
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }
}
