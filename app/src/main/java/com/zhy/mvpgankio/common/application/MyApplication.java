package com.zhy.mvpgankio.common.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.List;

/**
 * Created by zhy on 2019/1/16.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        String curProcess = getProcessName(this, android.os.Process.myPid());
        if (!getPackageName().equals(curProcess)) return;
        //所有操作往下写...
        mApplication = this;
//        initAppStatusListener();

    }

    private void initAppStatusListener() {
        ForegroundCallbacks.init(this).addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecameForeground() {

            }

            @Override
            public void onBecameBackground() {

            }
        });
    }

    public static Context getContext() {
        if (mApplication != null) {
            return mApplication;
        }
        throw new NullPointerException("u should init first");
    }

    /**
     * 获取进程名
     */
    private String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    // static 代码段可以防止内存泄露
//    static {
//        // 设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                // 全局设置主题颜色
//                layout.setPrimaryColorsId(android.R.color.white, android.R.color.holo_purple);
//                // .setTimeFormat(new DynamicTimeFormat("更新于 %s"));
//                // 指定为经典Header，默认是 贝塞尔雷达Header
//                return new ClassicsHeader(context);
//            }
//        });
//        // 设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
//            @Override
//            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
//                // 指定为经典Footer，默认是 BallPulseFooter
//                ClassicsFooter classicsFooter = new ClassicsFooter(context).setDrawableSize(20);
//                return classicsFooter;
//            }
//        });
//    }

}