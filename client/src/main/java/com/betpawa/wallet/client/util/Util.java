package com.betpawa.wallet.client.util;

import com.google.common.util.concurrent.ListenableFuture;
import io.reactivex.Observable;

public class Util {

    public static <T> Observable<T> toObservable(final ListenableFuture<T> future) {
        return Observable.fromFuture(future);
    }
}
