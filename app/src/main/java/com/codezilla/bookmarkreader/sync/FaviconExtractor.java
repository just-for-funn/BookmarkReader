package com.codezilla.bookmarkreader.sync;

import com.codezilla.bookmarkreader.domainmodel.IFaviconExtractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by davut on 9/17/2017.
 */

public class FaviconExtractor implements IFaviconExtractor {
    //<link.*rel="shortcut icon".*>
    String shortCutIconRegEx = "(<link.*rel=\"shortcut icon\".*href=\"([^\"]*)\")";
    @Override
    public String faviconUrl(String html) {
        try
        {
            Pattern p = Pattern.compile(shortCutIconRegEx);
            Matcher m =  p.matcher(html);
            if(!m.find())
                return null;
            return m.group(2);
        }catch (Exception e)
        {
            return null;
        }
    }
}
