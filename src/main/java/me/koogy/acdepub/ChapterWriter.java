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
    
    public static void write(File dir, Info bookInfo, Info partInfo, Options options, Chapter chapter) {
        write(dir, bookInfo, partInfo, options, chapter, 0);
    }
    public static void write(File dir, Info bookInfo, Info partInfo, Options options, Chapter chapter, int count) {

        PrintStream p = null;
        try {
            File file = new File(dir, chapter.getId() + ".xhtml");
            p = new PrintStream(new FileOutputStream(file));

            if (chapter.getNumbering() == null) {
                WriterUtils.writeHead(p, bookInfo.getTocTitle());
            } else {
                WriterUtils.writeHead(p, bookInfo.getTocTitle() + " - " + chapter.getNumbering());
            }
            
            p.println("<body class=\"text\">");
            // appendixes, prefixes or things where chapter titles option is set use chapter names / numbers
            // https://github.com/acdean/acdepub/issues/3
            //p.println("<!-- chapter: " + chapter + " -->");
            //p.println("<!-- chapterTitleEnabled: " + options.isChapterTitleEnabled() + " -->");
            //p.println("<!-- count: " + count + " -->");
            if (chapter.isNormalChapter() && count == 1) {
                // only chapter in part - use book title as chapter title
                p.println("<h2>");
                p.println("<span class=\"chapterTitle\">" + bookInfo.getTitle() + "</span>");
                if (chapter.getTitle() != null) {
                    // this is rare? single chapter with title (gets two chapterTitle spans)
                    p.println("<br/>");
                    p.println("<span class=\"chapterTitle\">" + chapter.getTitle() + "</span>");
                }
                p.println("</h2>");
            } else if (!chapter.isNormalChapter() || options.isChapterTitleEnabled()) {
                // Chapter Heading
                p.println("<h2>");
                if (chapter.getNumbering() != null) {
                    p.println("<span class=\"chapterNumber\">" + chapter.getNumbering() + "</span>");
                    if (chapter.getTitle() != null) {
                        p.println("<br/>");
                    }
                }
                if (chapter.getTitle() != null) {
                    p.println("<span class=\"chapterTitle\">" + chapter.getTitle() + "</span>");
                }
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
