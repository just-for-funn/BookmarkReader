package com.codezilla.bookmarkreader.domainmodel;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by davut on 1/1/2018.
 */

public class Change extends RealmObject
{
    RealmList<TextBlock> newBlocks = new RealmList<>();

    public Change() {
    }

    public RealmList<TextBlock> getNewBlocks() {
        return newBlocks;
    }

    public void setNewBlocks(RealmList<TextBlock> newBlocks) {
        this.newBlocks = newBlocks;
    }

    public void setNewBlocks(TextBlock ... textBlocks)
    {
        newBlocks = new RealmList<>();
        for (int i = 0; i < textBlocks.length; i++) {
            newBlocks.add(textBlocks[i]);
        }
    }
}
