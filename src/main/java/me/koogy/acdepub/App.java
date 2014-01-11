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
    private static final String BOOK_XML = "/home/adean/dev/acdepub/normal.xml";
    private static final String PREAMBLE_ID_FORMAT          = "pre_%03d";
    private static final String CHAPTER_ID_FORMAT           = "chap_%03d";
    private static final String CHAPTER_NUMBERING_FORMAT    = "%d";    // roman, alphabetic?
    private static final String PART_ID_FORMAT              = "part_%02d";
    private static final String PART_CHAPTER_ID_FORMAT      = "chap_%02d%03d";
    private static final String POSTAMBLE_ID_FORMAT         = "post_%03d";

    public static void main( String[] args ) {
        
        File dir = new File("/tmp/acdepub_" + (int)(Math.random() * 10000));
        dir.mkdirs();
        System.out.println("Dir: " + dir);
        UUID uid = UUID.randomUUID();
        
        try {
            JAXBContext context = JAXBContext.newInstance(Book.class);
            Unmarshaller um = context.createUnmarshaller();
            // read book from xml
            Book book = (Book) um.unmarshal(new FileReader(BOOK_XML));
            
            // generate numbers and filenames
            numberParts(book, true);
            TocWriter.write(dir, book, uid);
            ContentWriter.write(dir, book, uid);

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
        if (book.getPreambles() != null) {
            for(GenericChapter chap : book.getPreambles()) {
                chap.setId(String.format(PREAMBLE_ID_FORMAT, count));
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
                for(GenericChapter chap : part.getChapters()) {
                    chap.setId(String.format(PART_CHAPTER_ID_FORMAT, partCount, count));
                    chap.setNumbering(String.format(CHAPTER_NUMBERING_FORMAT, count));
                    count++;
                }
                partCount++;
            }
        }

        // postamble - no numbering
        if (book.getPostambles() != null) {
            count = 1;
            for(GenericChapter chap : book.getPostambles()) {
                chap.setId(String.format(POSTAMBLE_ID_FORMAT, count));
                count++;
            }
        }
    }
}
