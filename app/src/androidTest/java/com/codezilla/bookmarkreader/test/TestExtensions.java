package com.codezilla.bookmarkreader.test;

import org.awaitility.core.ThrowingRunnable;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Created by davut on 7/28/2017.
 */

public class TestExtensions {
    public static void untilAsserted(ThrowingRunnable throwingRunnable) {
        await().atMost(500 , TimeUnit.MILLISECONDS).untilAsserted(throwingRunnable);
    }
}
