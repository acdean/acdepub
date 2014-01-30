package me.koogy.acdepub;

import java.io.File;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.GenericChapter;
import me.koogy.acdepub.objects.Options;
import me.koogy.acdepub.objects.Parser;
import me.koogy.acdepub.objects.Part;
import org.w3c.dom.Document;

/**
 * Hello world!
 *
 */
public class Main 
{
//    private static final String BOOK_XML = "/home/adean/dev/NetBeansProjects/acdepub/src/test/xml/short_stories.xml";
    private static final String PREFACE_ID_FORMAT           = "pre_%03d";
    private static final String CHAPTER_ID_FORMAT           = "ch_%03d";
    private static final String PART_ID_FORMAT              = "pt_%02d";
    private static final String PART_CHAPTER_ID_FORMAT      = "ch_%02d%03d";
    private static final String APPENDIX_ID_FORMAT          = "app_%03d";

    public static void main( String[] args ) {
        String filename;
        if (args.length != 1) {
//            System.out.println("Usage: generate filename");
//            System.exit(-1);
            filename = "/home/adean/Documents/eBooks/dickens/grebt/acdepub/grebt.xml";
        } else {
            filename = args[0];
        }
        File file = new File(filename);
        File dir = new File("/tmp/acdepub_" + file.getName());
        dir.mkdirs();
        File metadir = new File(dir, "META-INF");
        metadir.mkdirs();
        System.out.println("Dir: " + dir);
        UUID uid = UUID.randomUUID();
        Options options = new Options();
        
        try {
            Book book = parse(filename);
            
            // generate numbers and filenames
            numberParts(book, options);
            
            // write everything
            ContentWriter.write(dir, book, uid);
            CoverWriter.write(dir, book);
            MetaWriter.write(metadir);
            MimetypeWriter.write(dir);
            StylesheetWriter.write(dir);
            TitlePageWriter.write(dir, book);
            TocWriter.write(dir, book, uid);

            // Chapters
            if (book.getPrefaces() != null) {
                for (GenericChapter preface : book.getPrefaces()) {
                    ChapterWriter.write(dir, options, book, preface);
                }
            }
            if (book.getChapters() != null) {
                for (GenericChapter chapter : book.getChapters()) {
                    ChapterWriter.write(dir, options, book, chapter);
                }
            }
            if (book.getParts() != null) {
                for (Part part : book.getParts()) {
                    PartWriter.write(dir, options, book, part);
                    for (GenericChapter chapter : part.getChapters()) {
                        ChapterWriter.write(dir, options, book, chapter);
                    }
                }
            }
            if (book.getAppendices() != null) {
                for (GenericChapter appendix : book.getAppendices()) {
                    ChapterWriter.write(dir, options, book, appendix);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // jaxb version (out of memory)
    static Book parseJaxb(String filename) {
        Book book = null;
//      JAXBContext context = JAXBContext.newInstance(Book.class);
//      Unmarshaller um = context.createUnmarshaller();
//      // read book from xml
//      book = (Book) um.unmarshal(new FileReader(filename));
        return book;
    }
    
    // dom version
    static Book parse(String filename) {
        Book book = new Book();
        try {
            File file = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            Parser.parseBook(book, doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }
    
    static void numberParts(Book book, Options options) {
        int count;
        
        // preamble - no numbering
        count = 1;
        if (book.getPrefaces() != null) {
            for(GenericChapter chap : book.getPrefaces()) {
                chap.setId(String.format(PREFACE_ID_FORMAT, count));
                count++;
            }
        }

        // there'll be one of chapters or parts

        // chapters - with numbering
        if (book.getChapters() != null) {
            count = 1;
            for(GenericChapter chap : book.getChapters()) {
                chap.setId(String.format(CHAPTER_ID_FORMAT, count));
                chap.setNumbering(numbering(options.getChapterName(), options.getChapterNumberStyle(), count));
                count++;
            }
        }
        // parts / chapters - with numbering
        if (book.getParts() != null) {
            int partCount = 1;
            for(Part part : book.getParts()) {
                part.setId(String.format(PART_ID_FORMAT, partCount));
                count = 1;
                if (part.getChapters() != null) {
                    for(GenericChapter chap : part.getChapters()) {
                        chap.setId(String.format(PART_CHAPTER_ID_FORMAT, partCount, count));
                        chap.setNumbering(numbering(options.getPartName(), options.getPartNumberStyle(), count));
                        count++;
                    }
                }
                partCount++;
            }
        }

        // postamble - no numbering
        if (book.getAppendices() != null) {
            count = 1;
            for(GenericChapter chap : book.getAppendices()) {
                chap.setId(String.format(APPENDIX_ID_FORMAT, count));
                count++;
            }
        }
    }

    // sets the numbering
    // 'Part III' or 'Three' or null
    private static String numbering(String name, String style, int i) {
        StringBuilder sb = new StringBuilder();
        if (style.equalsIgnoreCase("NONE")) {
            return null;
        }
        if (name != null) {
            sb.append(name);
            sb.append(" ");
        }
        sb.append(Numbers.number(style, i));
        return sb.toString();
    }
}