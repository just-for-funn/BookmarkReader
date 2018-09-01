package com.davutozcan.bookmarkreader.assertions;



import org.awaitility.Duration;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class AsyncExtensions {

    public static final int DEFAULT_DURAION = 6000;

    public static void invokeUntill(Runnable r , long millis){
        await().atMost(new Duration(millis , TimeUnit.MILLISECONDS)).until(()->{
            try{
                r.run();
                return true;
            }catch (Throwable e)
            {
                return false;
            }
        });
    }

    public static void invokeUntill(Runnable r) {
        invokeUntill(r , DEFAULT_DURAION);
    }
}
