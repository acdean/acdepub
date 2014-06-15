package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.Book;

/**
 * Outputs all the footnotes
 * @author adean
 */
public class FootnotesWriter {
    
    public static void write(File dir, Book book) {

        PrintStream p = null;
        try {
            File file = new File(dir, Book.FOOTNOTES_FILENAME);
            p = new PrintStream(new FileOutputStream(file));

            WriterUtils.writeHead(p, "Footnotes");
            
            p.println("<body class=\"text\">");
            
            p.println("<h1>Footnotes</h1>");

            for (int i = 0 ; i < book.getFootnotes().size() ; i++) {
                p.println("<a id=\"" + Book.FOOTNOTE_ANCHOR_PREFIX + (i + 1) + "\"/>");
                p.println("<div class=\"footnote\">");
                p.println("<div>[" + (i + 1) + "]</div>");
                // this already has newlines (hacky)
                p.print(book.getFootnotes().get(i).getContent());
                p.println("<a href=\""
                        + book.footnoteLinks.get(i) + ".xhtml"
                        + "#" + Book.FOOTNOTE_LINK_ANCHOR_PREFIX + (i + 1)
                        + "\">[back]</a>");
                p.println("</div>");
                p.println("<br/>");
                p.println("");
            }
            
            p.println("</body>");
            p.println("</html>");
        
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }
}
