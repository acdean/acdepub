package me.koogy.acdepub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.UUID;
import me.koogy.acdepub.objects.Book;

/**
 * @author adean
 */
public class ContentWriter {
    
    public static void write(File dir, Book book, UUID uid) {

        PrintStream p = null;
        try {
            File file = new File(dir, "toc.ncx");
            p = new PrintStream(new FileOutputStream(file));
            
            p.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            p.println("<package version=\"2.0\" xmlns=\"http://www.idpf.org/2007/opf\" unique-identifier=\"BookId\">");
            p.println("  <metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:opf=\"http://www.idpf.org/2007/opf\">");
            p.println("    <dc:title>Four Weird Tales (Pandoc)</dc:title>");
            p.println("    <dc:language>en_GB</dc:language>");
            p.println("    <dc:identifier id=\"BookId\">urn:uuid:" + uid + "</dc:identifier>");
            p.println("    <dc:creator opf:role=\"aut\">Algernon Blackwood</dc:creator>");
            p.println("    <dc:date></dc:date>");
            p.println("    <meta name=\"cover\" content=\"cover-image\"/>");
            p.println("  </metadata>");
            p.println("  <manifest>");
            item(p, "ncx", "toc.ncx", "application/x-dtbncx+xml");
            item(p, "style", "stylesheet.css", "text/css");
            item(p, "cover", "cover.xhtml", "application/xhtml+xml");
            item(p, "title_page", "title_page.xhtml", "application/xhtml+xml");
            item(p, "cover-image", "cover-image.jpg", "image/jpeg");
            // TODO manifest chapter items
            // p.println("<item id=\"ch1\" href=\"ch1.xhtml\" media-type=\"application/xhtml+xml\"/>");
            p.println("  </manifest>");
            // spine
            p.println("  <spine toc=\"ncx\">");
            p.println("    <itemref idref=\"cover\" linear=\"no\"/>");
            p.println("    <itemref idref=\"title_page\"/>");
            // TODO spine chapter items
            p.println("    <itemref idref=\"ch1\"/>");
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
    
    private static void item(PrintStream p, String id, String href, String mediaType) {
        p.format("    <item id=\"%s\" href=\"%s\" media-type=\"%s\"/>", id, href, mediaType);
    }
}
