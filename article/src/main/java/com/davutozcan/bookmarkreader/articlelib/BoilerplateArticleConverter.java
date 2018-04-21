package com.davutozcan.bookmarkreader.articlelib;

import android.util.Log;

import com.kohlschutter.boilerpipe.extractors.CommonExtractors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by davut on 12/19/2017.
 */

public class BoilerplateArticleConverter {


    private static final String TAG = BoilerplateArticleConverter.class.getSimpleName();

    public String convert(String html)
    {
        try {
            String cleanedHtml = removeUnnecessaryElements(html);
            return CommonExtractors.ARTICLE_EXTRACTOR.getText(cleanedHtml);
        } catch (Exception e) {
            Log.e(TAG, "cannot convert html", e );
            return null;
        }
    }

    String removeUnnecessaryElements(String html) {
         Document doc =  Jsoup.parseBodyFragment(html);
         doc.select("script, style, .hidden").remove();
         return doc.html();
    }


    public String htmlToRawString(String html)
    {
        try {
            return CommonExtractors.KEEP_EVERYTHING_EXTRACTOR.getText(html);
        } catch (Exception e) {
            Log.e(TAG ,"Cannot convert html to article", e);
            return null;
        }
    }
}
