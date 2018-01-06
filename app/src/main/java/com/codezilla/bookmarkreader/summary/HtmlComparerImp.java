package com.codezilla.bookmarkreader.summary;

import com.codezilla.bookmarkreader.domainmodel.IHtmlComparer;
import com.codezilla.bookmarkreader.domainmodel.TextBlock;
import com.smartarticlereader.article.BoilerplateArticleConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import difflib.Chunk;

/**
 * Created by davut on 12/25/2017.
 */

public class HtmlComparerImp implements IHtmlComparer {
    private List<Chunk> change;
    BoilerplateArticleConverter converter = new BoilerplateArticleConverter();
    @Override
    public boolean isChanged(String oldContent, String newContent)
    {
        List<String> oldContents = toList(converter.htmlToRawString(oldContent));
        List<String> newwContents = toList(converter.htmlToRawString(newContent));
        this.change = new DiffUtilImp(oldContents  , newwContents ).newLines();
        return change.size() > 0;
    }

    private List<String> toList(String doc) {
        if(doc == null || doc.length() == 0)
            return Collections.emptyList();
        return Arrays.asList(doc.split("\n"));
    }

    @Override
    public List<TextBlock> newLines()
    {
        return blocks(this.change);
    }

    private List<TextBlock> blocks(List<Chunk> chunks)
    {
        List<TextBlock> blocks = new ArrayList<>();
        for (int i = 0; i < chunks.size(); i++) {
            TextBlock block = new TextBlock();
            for (int j = 0; j <chunks.get(i).getLines().size() ; j++) {
                block.add(chunks.get(i).getLines().get(j).toString());
            }
            blocks.add(block);
        }
        return blocks;
    }
}
