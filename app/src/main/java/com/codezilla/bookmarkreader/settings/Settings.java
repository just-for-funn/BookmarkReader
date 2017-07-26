package com.codezilla.bookmarkreader.settings;

/**
 * Created by davut on 20.02.2017.
 */

public class Settings {
    private WorkMode workMode;

    public void workMode(WorkMode mode) {
        this.workMode = mode;
    }

    public WorkMode workMode() {
        return this.workMode;
    }

    public enum WorkMode
    {
        ONLY_WIFI , BOTH
    };
}
