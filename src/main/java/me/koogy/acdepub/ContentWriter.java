package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.Chapter;
import me.koogy.acdepub.objects.Info;
import me.koogy.acdepub.objects.Part;

/**
 * @author adean
 */
public class ContentWriter {
    
    public static void write(File dir, Book book) {
        Info info = book.getInfo();
        
        PrintStream p = null;
        try {
            File file = new File(dir, "content.opf");
            p = new PrintStream(new FileOutputStream(file));
            
            // redone these using The Martian
            p.println("<?xml version='1.0' encoding='utf-8'?>");
            p.println("<package xmlns=\"http://www.idpf.org/2007/opf\" unique-identifier=\"book_id\" version=\"2.0\">");
            p.println("  <metadata xmlns:opf=\"http://www.idpf.org/2007/opf\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:dcterms=\"http://purl.org/dc/terms/\">");
            p.println("    <dc:title>" + info.getTocTitle() + "</dc:title>");
            p.println("    <dc:language>en</dc:language>");
            p.println("    <dc:identifier id=\"book_id\" opf:scheme=\"uuid\">" + book.getUuid() + "</dc:identifier>");
            p.println("    <dc:creator opf:role=\"aut\">" + info.getAuthor() + "</dc:creator>");
            if (info.getDate() != null) {
                p.println("    <dc:date>" + info.getDate() + "</dc:date>");
            }
            if (info.hasCover()) {
                p.println("    <meta name=\"cover\" content=\"cover-image\"/>");
            }
            p.println("  </metadata>");

            // manifest
            p.println("  <manifest>");
            item(p, "ncx", "toc.ncx", "application/x-dtbncx+xml");
            item(p, "css", "stylesheet.css", "text/css"); 
            if (info.hasCover()) {
                item(p, "cover", "cover.html", "application/xhtml+xml");
                item(p, "cover-image", "cover-image.jpg", "image/jpeg");
            }
            item(p, "title_page", "title_page.html", "application/xhtml+xml");

            // manifest chapter items
            // p.println("<item id=\"ch1\" href=\"ch1.html\" media-type=\"application/xhtml+xml\"/>");
            if (book.getPrefaces() != null) {
                for (Chapter chap : book.getPrefaces()) {
                    item(p, chap.getId());
                }
            }
            if (book.getChapters() != null) {
                for (Chapter chap : book.getChapters()) {
                    item(p, chap.getId());
                }
            }
            if (book.getParts() != null) {
                for (Part part : book.getParts()) {
                    item(p, part.getId());
                    if (part.getChapters() != null) {
                        for (Chapter chap : part.getChapters()) {
                            item(p, chap.getId());
                        }
                    }
                }
            }
            if (book.getAppendices() != null) {
                for (Chapter chap : book.getAppendices()) {
                    item(p, chap.getId());
                }
            }
            if (book.getFootnotes() != null) {
                item(p, Book.FOOTNOTES_ID, Book.FOOTNOTES_FILENAME, "application/xhtml+xml");
            }
            p.println("  </manifest>");

            // spine
            p.println("  <spine toc=\"ncx\">");
            if (info.hasCover()) {
                p.println("    <itemref idref=\"cover\" linear=\"no\"/>");
            }
            p.println("    <itemref idref=\"title_page\"/>");

            // spine chapter items
            if (book.getPrefaces() != null) {
                for (Chapter chap : book.getPrefaces()) {
                    p.println("    <itemref idref=\"" + chap.getId() + "\"/>");
                }
            }
            if (book.getChapters() != null) {
                for (Chapter chap : book.getChapters()) {
                    p.println("    <itemref idref=\"" + chap.getId() + "\"/>");
                }
            }
            if (book.getParts() != null) {
                for (Part part : book.getParts()) {
                    p.println("    <itemref idref=\"" + part.getId() + "\"/>");
                    if (part.getChapters() != null) {
                        for (Chapter chap : part.getChapters()) {
                            p.println("    <itemref idref=\"" + chap.getId() + "\"/>");
                        }
                    }
                }
            }
            if (book.getAppendices() != null) {
                for (Chapter chap : book.getAppendices()) {
                    p.println("<itemref idref=\"" + chap.getId() + "\"/>");
                }
            }
            if (book.getFootnotes() != null) {
                p.println("    <itemref idref=\"" + Book.FOOTNOTES_ID + "\"/>");
            }
            p.println("  </spine>");
            p.println("</package>");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }
    
    private static void item(PrintStream p, String id) {
        item(p, id, id + ".html", "application/xhtml+xml");
    }
    private static void item(PrintStream p, String id, String href, String mediaType) {
        p.format("    <item id=\"%s\" href=\"%s\" media-type=\"%s\"/>\n", id, href, mediaType);
    }
}
