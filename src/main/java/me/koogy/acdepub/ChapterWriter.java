package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.Chapter;
import me.koogy.acdepub.objects.Info;
import me.koogy.acdepub.objects.Options;

/**
 * Outputs a chapter file
 * @author adean
 */
public class ChapterWriter {
    
    public static void write(File dir, Info info, Options options, Chapter chapter) {
        write(dir, info, options, chapter, 0);
    }
    public static void write(File dir, Info info, Options options, Chapter chapter, int count) {

        PrintStream p = null;
        try {
            File file = new File(dir, chapter.getId() + ".xhtml");
            p = new PrintStream(new FileOutputStream(file));

            WriterUtils.writeHead(p, info.getTitle());
            
            p.println("<body class=\"text\">");
            // appendixes, prefixes or things where chapter titles option is set use chapter names / numbers
            if (!chapter.isNormalChapter() || options.isChapterTitleEnabled()) {
                // Chapter Heading
                p.print("<h2>");
                if (chapter.getNumbering() != null) {
                    p.print("<span class=\"chapterNumber\">" + chapter.getNumbering() + "</span>");
                    if (chapter.getTitle() != null) {
                        p.print("<br/>");
                    }
                }
                if (chapter.getTitle() != null) {
                    p.print("<span class=\"chapterTitle\">" + chapter.getTitle() + "</span>");
                }
                p.println("</h2>");
            }
            // this is the only chapter in the book / part. use book / part name as title.
            // TODO not quite working.
            if (chapter.isNormalChapter() && count == 1) {
                // no chapters - use book title as chapter title
                p.print("<h2>");
                p.print("<span class=\"chapterTitle\">" + info.getTitle() + "</span>");
                p.println("</h2>");
            }
            
            // Paragraphs
            p.println(chapter.getContent());
            
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
