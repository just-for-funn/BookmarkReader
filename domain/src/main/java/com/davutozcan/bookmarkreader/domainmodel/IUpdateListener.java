package com.davutozcan.bookmarkreader.domainmodel;

/**
 * Created by davut on 1/14/2018.
 */

public interface IUpdateListener {
    void onStart();
    void onFinish();
    void onStart(WebUnit wu);
    void onFail(WebUnit wu);
    void onComplete(WebUnit wu);

    public static final IUpdateListener NULL = new IUpdateListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onStart(WebUnit wu) {

        }

        @Override
        public void onFail(WebUnit wu) {

        }

        @Override
        public void onComplete(WebUnit wu) {

        }
    };
}
