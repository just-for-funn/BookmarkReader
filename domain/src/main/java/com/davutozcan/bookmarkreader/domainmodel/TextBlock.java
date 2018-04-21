package com.davutozcan.bookmarkreader.domainmodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by davut on 1/1/2018.
 */
public class TextBlock  extends RealmObject{

    public RealmList<String> lines = new RealmList<>();


    public List<String> lines()
    {
        if(lines == null)
            return Collections.emptyList();
        return lines;
    }

    public void add(String line) {
        lines.add(line);
    }


    public static  TextBlock textBlock(String ... lines) {
        TextBlock tb = new TextBlock();
        for (int i = 0; i < lines.length; i++) {
            tb.add(lines[i]);
        }
        return tb;
    }
}
