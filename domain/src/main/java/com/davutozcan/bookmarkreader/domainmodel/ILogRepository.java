package com.davutozcan.bookmarkreader.domainmodel;

import java.util.List;

/**
 * Created by davut on 9/7/2017.
 */

public interface ILogRepository
{
     List<Log> logs();

     void info(String message);
     void warning(String message);
     void error(String message);
     void clear();
     void close();
}
