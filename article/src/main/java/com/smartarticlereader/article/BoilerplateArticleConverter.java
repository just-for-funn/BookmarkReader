package com.smartarticlereader.article;

import android.util.Log;

import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.extractors.CommonExtractors;

/**
 * Created by davut on 12/19/2017.
 */

public class BoilerplateArticleConverter {


    private static final String TAG = BoilerplateArticleConverter.class.getSimpleName();

    public String convert(String html)
    {
        try {
            return CommonExtractors.ARTICLE_EXTRACTOR.getText(html);
        } catch (BoilerpipeProcessingException e) {
            e.printStackTrace();
            Log.e(TAG, "cannot convert html", e );
            return null;
        }
    }
}
