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
            
            p.println("body.text {margin-left:2%; margin-right:2%; margin-top:5%; text-align:justify;}");
            p.println("body.image {margin:0px;}");
            // h1 = book title, part title
            p.println("h1 {text-align:center; line-height:1em;}");
            // h1 is 2em, h2 is 1.5em - http://davidwalsh.name/firefox-internal-rendering-css
            p.println("h1.subtitle {text-align:center; line-height:1em; font-size:1.5em; font-style:italic;}");
            // h2 is author and chapter title
            p.println("h2 {text-align:center; line-height:1em;}");
            // used for section title and title / part date
            p.println("h3 {text-align:center; margin-top:10px; margin-bottom:10px;}");
            // unused
            p.println("h4 {text-align:center; line-height:1em;}");
            p.println("h5 {text-align:center; line-height:1em;}");
            p.println("h6 {text-align:center; line-height:1em;}");
            p.println("table {margin-top:10px; margin-bottom:10px;}");
            // indent first line of paragraphs
            p.println("p {text-indent:30px; margin-top:0px; margin-bottom:0px;}");
            p.println("p.p0 {text-indent:0px;}");
            p.println("p.p1 {text-indent:30px;}");
            p.println("p.p2 {text-indent:60px;}");
            p.println("p.p3 {text-indent:90px;}");
            p.println("p.p4 {text-indent:120px;}");
            p.println("p.footnote {text-indent:0px}");
            // classes for aligning things
            p.println(".centre {text-indent:0px; text-align:center}");
            p.println(".center {text-indent:0px; text-align:center}");
            p.println(".right {text-align:right}");
            p.println(".hr {text-indent:0px; width:100%; text-align:center; margin-top:10px; margin-bottom:10px;}");
            p.println("img {max-width:100%;}"); // for cover
            // letters and poems
            p.println(".letter {margin-top:10px; margin-bottom:10px; margin-left:30px; margin-right:30px; font-style:italic;}");
            p.println(".letter p em {font-weight: bold;}");
            p.println(".poem {margin-top:15px; margin-bottom:15px; font-size:smaller; font-style:italic;}");
            p.println(".poem1 {margin-left:30px; margin-top:0px; margin-bottom:0px;}");
            p.println(".poem2 {margin-left:60px; margin-top:0px; margin-bottom:0px;}");
            p.println(".poem3 {margin-left:90px; margin-top:0px; margin-bottom:0px;}");
            p.println(".poem4 {margin-left:120px; margin-top:0px; margin-bottom:0px;}");
            p.println(".poem5 {margin-left:150px; margin-top:0px; margin-bottom:0px;}");
            p.println(".smallcaps {font-variant: small-caps; font-size: smaller;}");
            p.println("pre {font-family:monospace; font-size: smaller;}");
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }
}
