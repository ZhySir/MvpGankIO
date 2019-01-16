package com.zhy.mvpgankio.common.http;

import com.zhy.mvpgankio.common.base.rx.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 2
 * Created by zhy on 2017/5/26.
 */
public class HttpUtils {

    public static <T> void getData(Observable<T> observable, Consumer<T> action1) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1);
    }

    public static <T> void getData(Observable<T> observable, Observer<T> observer) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * MVP 专用
     */
    public static <T> Disposable loadData(
            Observable<T> observable,
            BaseObserver<T> observer
    ) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return observer.getDisposable();
    }

}
