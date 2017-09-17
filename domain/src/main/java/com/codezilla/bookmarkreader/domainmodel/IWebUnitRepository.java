package com.codezilla.bookmarkreader.domainmodel;

import java.util.List;

/**
 * Created by davut on 9/2/2017.
 */

public interface IWebUnitRepository {
    void clearWebUnits();

    List<WebUnit> webUnits();

    void add(WebUnit wu);
    void close();


    WebUnit getWebUnit(String url);

    boolean exists(String url);

    void update(WebUnit wu);
}
