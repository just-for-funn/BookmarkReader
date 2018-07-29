package com.davutozcan.bookmarkreader.splash;

interface SplashContacts {

    interface IView {

        void onLoadFinished(Class<?> clazz);

        void onLoadFailed(Throwable throwable);

    }

}
