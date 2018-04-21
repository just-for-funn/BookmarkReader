package com.davutozcan.bookmarkreader.summary;


import com.davutozcan.bookmarkreader.domainmodel.TextBlock;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by davut on 12/25/2017.
 */
public class HtmlComparerImpTest {


    HtmlComparerImp comparerImp = new HtmlComparerImp();


    @Ignore("its considered as newLines")
    @Test
    public void shouldReturnNewLineWhenItsChanged()
    {
        assertTrue(comparerImp.isChanged("<p>a</p>" ,"<p>b</p>" ));
    }

    @Test
    public void shouldReturnTrueWhenNewLineAddedToEnd()
    {
        assertTrue(comparerImp.isChanged(asContent("a") , asContent("a","b")));
    }

    @Test
    public void shouldReturnTrueWhenSomeTextPrepended()
    {
        assertTrue(comparerImp.isChanged(asContent("a","b") , asContent("c","a","b")));
    }


    @Test
    public void shouldReturnTrueWhenSomeTextAddedMiddle(){
        assertTrue(comparerImp.isChanged(asContent("a","b" ) , asContent("a","b","c")));
    }

    @Test
    public void shouldReturnPrependedText()
    {
        comparerImp.isChanged(asContent("a","b","c") , asContent("d","a","b","c"));
        assertThat(comparerImp.newLines() , hasSize(1));
        assertThat(asString(comparerImp.newLines().get(0)) , equalTo("d"));
    }

    private String asString(TextBlock textBlock) {
        StringBuilder sb = new StringBuilder();
        if(textBlock.lines().size() == 0)
            return "";
        sb.append(textBlock.lines().get(0));
        for (int i = 1; i < textBlock.lines().size(); i++) {
            sb.append("\n");
            sb.append(textBlock.lines().get(i) );
        }
        return sb.toString();
    }

    private String asContent(String ...lines) {
        StringBuilder sb = new StringBuilder();
        sb.append( lines[0]);
        for (int i = 1; i < lines.length; i++) {
            sb.append("<br/>");
            sb.append(lines[i]);
        }
        return sb.toString();
    }


    @Test
    public void shouldReturnAllInsertedLines()
    {
        String old = asContent("1","2","3","4","5","6","7","8","9");
        String neww = asContent("a","b","c","1","2","d","3","4","e","f","g","5","6","7","8","9","x","y");
        comparerImp.isChanged(old , neww);
        assertThat(comparerImp.newLines() , hasSize(4) );
        assertThat(asString(comparerImp.newLines().get(0)) , equalTo("a\nb\nc"));
        assertThat(asString(comparerImp.newLines().get(1)) , equalTo("d"));
        assertThat(asString(comparerImp.newLines().get(2)) , equalTo("e\nf\ng"));
        assertThat(asString(comparerImp.newLines().get(3)) , equalTo("x\ny"));
    }

    @Test
    public void shouldReturnAllNewLinesWhenExistingNull()
    {
        String neww = asContent("a","b","c");
        comparerImp.isChanged(null , neww );
        assertThat(comparerImp.newLines() , hasSize(1) );
        assertThat(comparerImp.newLines().get(0).lines() , hasItems("a","b","c"));
    }

}