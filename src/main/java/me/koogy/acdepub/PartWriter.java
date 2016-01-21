package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.Info;
import me.koogy.acdepub.objects.Options;
import me.koogy.acdepub.objects.Part;

/**
 * Prints a part title page
 * @author adean
 */
public class PartWriter {
    
    public static void write(File dir, Part part) {
        Info info = part.getInfo();
        Options options = part.getOptions();

        PrintStream p = null;
        try {
            File file = new File(dir, part.getId() + ".xhtml");
            p = new PrintStream(new FileOutputStream(file));

            WriterUtils.writeHead(p, info.getTocTitle());
            WriterUtils.startTitlePage(p);

            p.println("<h1 class=\"partTitlePage\">");
            
            if (part.getNumbering() != null && options.isPartTitleEnabled()) {
                p.println(part.getNumbering());
                if (info.getTitle() != null) {
                    p.println("<br/>");
                }
            }
            if (info.getTitle() != null) {
                p.println(info.getTitle());
            }
            p.println("</h1>");
            if (info.getSubtitle() != null) {
                p.println("<h1 class=\"subtitle\">" + info.getSubtitle() + "</h1>");
            }
            if (info.getAuthor() != null) {
                p.println("<h2 class=\"author\">" + info.getAuthor() + "</h2>");
            }
            if (info.getDate() != null) {
                p.println("<h3 class=\"date\">" + info.getDate() + "</h3>");
            }

            WriterUtils.endTitlePage(p);
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
