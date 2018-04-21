package com.davutozcan.bookmarkreader.assertions;

import android.support.annotation.NonNull;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.w3c.dom.Document;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by davut on 8/19/2017.
 */

public class IsDocumentContains {
    @NonNull
    public static  Matcher<Document> isDocumentContains(final String url) {
        return new BaseMatcher<Document>() {
            @Override
            public boolean matches(Object item) {
                Document doc = (Document) item;
                try {
                    return docAsString( doc).contains(url);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Web content not  matches");
            }
        };
    }

    private static String docAsString(Document newDoc) throws Exception{
        DOMSource domSource = new DOMSource(newDoc);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StringWriter sw = new StringWriter();
        StreamResult sr = new StreamResult(sw);
        transformer.transform(domSource, sr);
        return sw.toString();
    }
}
