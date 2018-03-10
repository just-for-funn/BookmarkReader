package com.smartarticlereader.article;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by davut on 10.03.2018.
 */
public class BoilerplateArticleConverterTest {
    BoilerplateArticleConverter converter = new BoilerplateArticleConverter();


    @Test
    public void shouldRemoveUnnecessaryElements() throws IOException {
        String resource = resource("javaworld.html");
        String result = converter.removeUnnecessaryElements(resource);
        assertFalse(result.contains("<script>"));
    }

    private String resource(String resource) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
        try(ByteArrayOutputStream result = new ByteArrayOutputStream())
        {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        }
    }
}