package com.zhy.mvpgankio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhy.libutils.ActivityUtil;
import com.zhy.mvpgankio.about.AboutFragment;
import com.zhy.mvpgankio.category.CategoryFragment;
import com.zhy.mvpgankio.common.Constants;
import com.zhy.mvpgankio.common.base.activity.BaseActivity;
import com.zhy.mvpgankio.home.HomeFragment;
import com.zhy.mvpgankio.welfare.WelfareFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

/**
 * Main
 * Created by zhy on 2019/1/16.
 */
public class MainActivity extends BaseActivity {

    private RelativeLayout pageHome, pageCategory, pageWelfare, pageAbout;
    private RadioButton rBtnHome, rBtnCategory, rBtnWelfare, rBtnAbout;
    private FragmentManager fragmentManager;
    //当前显示
    private Fragment currentFragment;
    private int currentIndex = 0;
    private HashMap<Integer, Fragment> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            currentIndex = savedInstanceState.getInt("currentIndex", 0);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentIndex", currentIndex);
    }

    @Override
    public void initParam() {
        super.initParam();
        EventBus.getDefault().register(this);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void initView() {
        super.initView();
        pageHome = findViewById(R.id.page_Home);
        pageCategory = findViewById(R.id.page_Category);
        pageWelfare = findViewById(R.id.page_Welfare);
        pageAbout = findViewById(R.id.page_About);
        rBtnHome = findViewById(R.id.btn_Home);
        rBtnCategory = findViewById(R.id.btn_Category);
        rBtnWelfare = findViewById(R.id.btn_Welfare);
        rBtnAbout = findViewById(R.id.btn_About);
    }

    @Override
    public void initData() {
        super.initData();
        showFragment(currentIndex);
    }

    @Override
    public void initListener() {
        super.initListener();
        pageHome.setOnClickListener(this);
        pageCategory.setOnClickListener(this);
        pageWelfare.setOnClickListener(this);
        pageAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.page_Home:
                currentIndex = 0;
                showFragment(currentIndex);
                selectBtnState(true, false, false, false);
                break;
            case R.id.page_Category:
                currentIndex = 1;
                showFragment(currentIndex);
                selectBtnState(false, true, false, false);
                break;
            case R.id.page_Welfare:
                currentIndex = 2;
                showFragment(currentIndex);
                selectBtnState(false, false, true, false);
                break;
            case R.id.page_About:
                currentIndex = 3;
                showFragment(currentIndex);
                selectBtnState(false, false, false, true);
                break;
        }
    }

    //显示选中的Fragment
    private void showFragment(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = getFragment(index);
        //如果该Fragment对象被添加到了它的Activity中，那么它返回true，否则返回false。
        //https://blog.csdn.net/k393393/article/details/78875838
        if (fragment.isAdded()) {
            ft.show(fragment);
        } else {
            ft.add(R.id.fragment_Container, fragment);
        }
        if (currentFragment != null) {
            if (currentFragment == fragment)
                ft.show(fragment);
            else
                ft.hide(currentFragment);
        }
        ft.commitAllowingStateLoss();
        currentFragment = fragment;
    }

    private Fragment getFragment(int index) {
        Fragment fragment = fragmentMap.get(index);
        if (fragment != null) return fragment;
        switch (index) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new CategoryFragment();
                break;
            case 2:
                fragment = new WelfareFragment();
                break;
            case 3:
                fragment = new AboutFragment();
                break;
        }
        fragmentMap.put(index, fragment);
        return fragment;
    }

    private void selectBtnState(boolean home, boolean category, boolean welfare, boolean about) {
        rBtnHome.setChecked(home);
        rBtnCategory.setChecked(category);
        rBtnWelfare.setChecked(welfare);
        rBtnAbout.setChecked(about);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doubleClickFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long lastClickBackMilli = 0;

    //根页面按两次返回才是真的退出应用
    @SuppressLint("WrongConstant")
    private void doubleClickFinish() {
        if (System.currentTimeMillis() - lastClickBackMilli < 2000) {
            ActivityUtil.getInstance().finishAllActivity();
        } else {
            Toast.makeText(this, getResources().getString(R.string.re_press_Exit), Toast.LENGTH_SHORT).show();
        }
        lastClickBackMilli = System.currentTimeMillis();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefresh(String tag) {
        if (tag.contains(Constants.MAIN_SHOW_HOME)) {
            currentIndex = 0;
            showFragment(currentIndex);
            selectBtnState(true, false, false, false);
            return;
        }
        if (tag.contains(Constants.MAIN_SHOW_CATEGORY)) {
            currentIndex = 1;
            showFragment(currentIndex);
            selectBtnState(false, true, false, false);
            return;
        }
        if (tag.contains(Constants.MAIN_SHOW_WELFARE)) {
            currentIndex = 2;
            showFragment(currentIndex);
            selectBtnState(false, false, true, false);
            return;
        }
        if (tag.contains(Constants.MAIN_SHOW_ABOUT)) {
            currentIndex = 3;
            showFragment(currentIndex);
            selectBtnState(false, false, false, true);
            return;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
