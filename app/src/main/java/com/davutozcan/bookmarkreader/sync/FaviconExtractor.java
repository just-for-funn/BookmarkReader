package com.davutozcan.bookmarkreader.sync;

import android.support.annotation.Nullable;

import com.davutozcan.bookmarkreader.domainmodel.IFaviconExtractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by davut on 9/17/2017.
 */

public class FaviconExtractor implements IFaviconExtractor {
    //<link.*rel="shortcut icon".*>
    String shortCutIconRegExFormat = "(<link[^<]+rel=\"%s\"[^>]+>)";
    private static  String [] refNames = {"shortcut icon","icon","apple-touch-icon"};
    @Override
    public String faviconUrl(String url , String html) {
        String path =  searchAll(html);
        if(path == null)
            return null;
        if(path.startsWith("http"))
            return path;
        if(path.startsWith("//"))
            return "http:"+path;
        return basePath(url)+path;
    }

    private String basePath(String url) {
        int extensionIndex = url.lastIndexOf(".");
        int queryIndex = Integer.MAX_VALUE , subDirectoryIndex = Integer.MAX_VALUE;
        queryIndex = url.indexOf("?" , extensionIndex);
        subDirectoryIndex = url.indexOf("/" , extensionIndex);
        if(queryIndex == -1 && subDirectoryIndex == -1)
            return url;
        if(queryIndex == -1)
            return url.substring(0,subDirectoryIndex);
        if(subDirectoryIndex == -1)
            return url.substring(0,queryIndex);
        if(queryIndex < subDirectoryIndex)
            return url.substring(0,queryIndex);
        return url.substring(0,subDirectoryIndex);
    }

    @Nullable
    private String searchAll(String html) {
        for (int i = 0; i < refNames.length; i++) {
            String url = extractUsingRefName(html , refNames[i]);
            if(url != null)
                return url;
        }
        return null;
    }

    @Nullable
    private String extractUsingRefName(String html , String refName) {
        try
        {
            String regex = String.format(shortCutIconRegExFormat ,refName );
            Pattern p = Pattern.compile(regex);
            Matcher m =  p.matcher(html);
            if(!m.find())
                return null;
           return extranctHref(m.group(1));
        }catch (Exception e)
        {
            return null;
        }
    }

    private String extranctHref(String linkTag) {
       Pattern p = Pattern.compile("href=\"([^\"]+)\"");
       Matcher m =  p.matcher(linkTag);
        if(!m.find())
            return null;
        return m.group(1);
    }
}
