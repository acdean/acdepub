package me.koogy.acdepub;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.DomHandler;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * See
 * http://blog.bdoughan.com/2011/04/xmlanyelement-and-non-dom-properties.html
 * 
 * @author adean
 */
public class ParaHandler implements DomHandler<String, StreamResult> {

    private static final String BIO_START_TAG = "<para>";
    private static final String BIO_END_TAG = "</para>";
 
    private StringWriter xmlWriter = new StringWriter();
 
    public StreamResult createUnmarshaller(ValidationEventHandler errorHandler) {
        return new StreamResult(xmlWriter);
    }
 
    public String getElement(StreamResult rt) {
        String xml = rt.getWriter().toString();
        int beginIndex = xml.lastIndexOf(BIO_START_TAG) + BIO_START_TAG.length();
        int endIndex = xml.lastIndexOf(BIO_END_TAG);
        String element = xml.substring(beginIndex, endIndex);
        return element;
    }
 
    public Source marshal(String n, ValidationEventHandler errorHandler) {
        try {
            String xml = BIO_START_TAG + n.trim() + BIO_END_TAG;
            StringReader xmlReader = new StringReader(xml);
            return new StreamSource(xmlReader);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
