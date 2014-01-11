package me.koogy.acdepub;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import me.koogy.acdepub.objects.Book;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String BOOK_XML = "/home/adean/dev/acdepub/short.xml";

    public static void main( String[] args ) {
        
        try {
            JAXBContext context = JAXBContext.newInstance(Book.class);
            Unmarshaller um = context.createUnmarshaller();
            // read book from xml
            Book book = (Book) um.unmarshal(new FileReader(BOOK_XML));
            System.out.println("Book: " + book);            
            
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
