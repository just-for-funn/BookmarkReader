package com.codezilla.bookmarkreader.sync;

import com.codezilla.bookmarkreader.domainmodel.ILogRepository;
import com.codezilla.bookmarkreader.domainmodel.Log;

import java.util.List;

/**
 * Created by davut on 9/9/2017.
 */

public class LogCatDecorator implements ILogRepository {
    private static final String TAG = LogCatDecorator.class.getSimpleName();
    ILogRepository logRepository;

    public LogCatDecorator(ILogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<Log> logs() {
        return logRepository.logs();
    }

    @Override
    public void info(String message)
    {
        android.util.Log.i(TAG , message);
        logRepository.info(message);
    }

    @Override
    public void warning(String message) {
        android.util.Log.w(TAG , message);
        logRepository.warning(message);
    }

    @Override
    public void error(String message) {
        android.util.Log.e(TAG , message);
        logRepository.error(message);
    }

    @Override
    public void clear() {
        logRepository.clear();
    }

    @Override
    public void close() {
        logRepository.close();
    }
}
