package me.koogy.acdepub;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.UUID;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import me.koogy.acdepub.objects.Book;
import me.koogy.acdepub.objects.GenericChapter;
import me.koogy.acdepub.objects.Part;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String BOOK_XML = "/home/adean/dev/NetBeansProjects/acdepub/src/test/xml/short_stories.xml";
    private static final String PREFACE_ID_FORMAT           = "pre_%03d";
    private static final String CHAPTER_ID_FORMAT           = "ch_%03d";
    private static final String CHAPTER_NUMBERING_FORMAT    = "%d";    // roman, alphabetic?
    private static final String PART_ID_FORMAT              = "pt_%02d";
    private static final String PART_CHAPTER_ID_FORMAT      = "ch_%02d%03d";
    private static final String APPENDIX_ID_FORMAT          = "app_%03d";

    public static void main( String[] args ) {
        
        File dir = new File("/tmp/acdepub_" + (int)(Math.random() * 10000));
        dir.mkdirs();
        File metadir = new File(dir, "META-INF");
        metadir.mkdirs();
        System.out.println("Dir: " + dir);
        UUID uid = UUID.randomUUID();
        
        try {
            JAXBContext context = JAXBContext.newInstance(Book.class);
            Unmarshaller um = context.createUnmarshaller();
            // read book from xml
            Book book = (Book) um.unmarshal(new FileReader(BOOK_XML));
            
            // generate numbers and filenames
            numberParts(book, true);
            
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
                    ChapterWriter.write(dir, book, preface);
                }
            }
            if (book.getChapters() != null) {
                for (GenericChapter chapter : book.getChapters()) {
                    ChapterWriter.write(dir, book, chapter);
                }
            }
            if (book.getParts() != null) {
                for (Part part : book.getParts()) {
    //                PartWriter.write(dir, book, part);
                    for (GenericChapter chapter : part.getChapters()) {
                        ChapterWriter.write(dir, book, chapter);
                    }
                }
            }
            if (book.getAppendices() != null) {
                for (GenericChapter appendix : book.getAppendices()) {
                    ChapterWriter.write(dir, book, appendix);
                }
            }

            System.out.println("Book: " + book);
            
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    static void numberParts(Book book, boolean numberFromPart) {
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
                chap.setNumbering(String.format(CHAPTER_NUMBERING_FORMAT, count));
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
                        chap.setNumbering(String.format(CHAPTER_NUMBERING_FORMAT, count));
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
}
