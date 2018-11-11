package com.davutozcan.bookmarkreader.backend;

import io.reactivex.Observable;

public interface IBackendCommand<T> {
    Observable<T> doWork();
}
