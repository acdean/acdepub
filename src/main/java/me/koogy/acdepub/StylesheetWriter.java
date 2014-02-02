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
            
            p.println("body {left-margin:2%, right-margin:2%; text-align:justify;}");
            p.println("h1 {text-align:center; line-height:1em;}");
            p.println("h2 {text-align:center; line-height:1em;}");
            p.println("h3 {text-align:center; line-height:1em;}");
            p.println("h4 {text-align:center; line-height:1em;}");
            p.println("h5 {text-align:center; line-height:1em;}");
            p.println("h6 {text-align:center; line-height:1em;}");
            p.println("p {text-indent:2em; margin-top:0px; margin-bottom:0px;}");
            // classes for aligning things
            p.println(".centre {text-align:center}");
            p.println(".center {text-align:center}");
            p.println(".right {text-align:right}");
            p.println("hr {width:50%;}");
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }
}
