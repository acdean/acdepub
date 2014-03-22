package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.GenericChapter;
import me.koogy.acdepub.objects.Info;
import me.koogy.acdepub.objects.Options;

/**
 * Outputs a chapter file
 * @author adean
 */
public class ChapterWriter {
    
    public static void write(File dir, Info info, Options options, GenericChapter chapter) {

        PrintStream p = null;
        try {
            File file = new File(dir, chapter.getId() + ".xhtml");
            p = new PrintStream(new FileOutputStream(file));

            WriterUtils.writeHead(p, info.getTitle());
            
            p.println("<body class=\"text\">");
            if (!chapter.isNormalChapter() || options.getChapterTitles()) {
                // Chapter Heading
                p.print("<h1>");
                if (chapter.getNumbering() != null) {
                    p.print("<span class=\"chapterNumber\">" + chapter.getNumbering() + "</span>");
                    if (chapter.getTitle() != null) {
                        p.print("<br/>");
                    }
                }
                if (chapter.getTitle() != null) {
                    p.print("<span class=\"chapterTitle\">" + chapter.getTitle() + "</span>");
                }
                p.println("</h1>");
            }
            if (chapter.isNormalChapter() && !options.getChapterTitles()) {
                // no chapters - use book title as chapter title
                p.print("<h1>");
                p.print("<span class=\"chapterTitle\">" + info.getTitle() + "</span>");
                p.println("</h1>");
            }
            
            // Paragraphs
            p.println(chapter.getContent());
//            for (String para : chapter.getParas()) {
//                // TODO translate special paragraphs -o- = hr etc
//                p.println("<p>" + para + "</p>");
//            }
            
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
