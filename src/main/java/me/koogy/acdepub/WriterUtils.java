package me.koogy.acdepub;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author adean
 */
public class WriterUtils {
    
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Date date = new Date();
    
    public static void writeHead(PrintStream p, String title) {
        
        p.println("<?xml version='1.0' encoding='utf-8'?>");
//        p.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        p.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");

        p.println("<head>");
        p.println("<!-- generated by acdepub " + formatter.format(date) + " -->");
        p.println("<title>" + title + "</title>");
        p.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
        p.println("<link href=\"stylesheet.css\" rel=\"stylesheet\" type=\"text/css\"/>");
        p.println("</head>");
    }

    public static void startTitlePage(PrintStream p) {
        p.println("<body class=\"text\">");
        p.println("<div style=\"margin-top: 200px;\">");
    }

    public static void endTitlePage(PrintStream p) {
        p.println("</div>");
        p.println("</body>");
    }
}
