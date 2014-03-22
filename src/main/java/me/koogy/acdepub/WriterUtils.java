package me.koogy.acdepub;

import java.io.PrintStream;

/**
 * @author adean
 */
public class WriterUtils {
    
    public static void writeHead(PrintStream p, String title) {
        
        p.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        p.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        p.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");

        p.println("<head>");
        p.println("<title>" + title + "</title>");
        p.println("<link href=\"stylesheet.css\" rel=\"stylesheet\" type=\"text/css\"/>");
        p.println("</head>");
    }
    
//    public static void startTitlePage(PrintStream p) {
//        p.println("<body class=\"text\" height=\"600px\" border=\"solid\">");
//        p.println("<div style=\"display:table; height:100%; width:100%;\">");
//        p.println("<div style=\"display:table-cell; vertical-align:middle; height:100%;\">");
//    }
//
//    public static void endTitlePage(PrintStream p) {
//        p.println("</div>");
//        p.println("</div>");
//        p.println("</body>");
//    }

    public static void startTitlePage(PrintStream p) {
        p.println("<body class=\"text\">");
        p.println("<div style=\"margin-top: 200px;\">");
    }

    public static void endTitlePage(PrintStream p) {
        p.println("</div>");
        p.println("</body>");
    }
}
