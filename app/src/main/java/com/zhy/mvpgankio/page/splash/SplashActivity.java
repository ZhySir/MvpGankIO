package com.zhy.mvpgankio.page.splash;

import android.os.Bundle;

import com.zhy.mvpgankio.MainActivity;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.base.activity.BaseActivity;
import com.zhy.mvpgankio.common.base.rx.BaseObserver;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 闪屏页
 * Created by zhy on 2019/1/17.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        skipActivity();
                    }

                    @Override
                    protected void onHandleSubscribe(Disposable d) {
                        getRxManager().add(d);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        skipActivity();
                    }
                });

    }

    private void skipActivity() {
        openActivity(MainActivity.class);
        finish();
    }

}
