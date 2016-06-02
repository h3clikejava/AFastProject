package com.h3c.afastproject.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.h3c.afastproject.R;
import com.h3c.afastproject.base.baseInterface.IKeyboardStateControl;
import com.h3c.afastproject.base.baseInterface.IProcessDialog;
import com.h3c.afastproject.dialog.LoadingDialog;

import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by H3c on 16/5/22.
 */

public abstract class BaseActivity extends RequestPermissionActivity
        implements IKeyboardStateControl, IProcessDialog {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overrideCustomTransitionAnim();
        overridePendingTransition(getEnterTransitionInAnim(), getEnterTransitionOutAnim());
        getIntentDataInActivityBase(savedInstanceState);
        super.onCreate(savedInstanceState);

        int resLayout = initResLayout();
        if(resLayout > 0) {
            setContentView(resLayout);
        }
        initView();
        initData();
    }

    /**
     * 返回布局资源
     * @return
     */
    protected abstract void getIntentDataInActivityBase(Bundle savedInstanceState);
    protected abstract int initResLayout();
    protected abstract void initView();
    protected abstract void initData();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void setPrimaryDarkColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void showLoadingDialog() {
        showLoadingInfoDialog(null);
    }

    private LoadingDialog mLoadingDialog;
    @Override
    public void showLoadingInfoDialog(String info) {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog();
            mLoadingDialog.setCancelable(false);
        }

        mLoadingDialog.setContent(info);
        if(mLoadingDialog.isResumed()
                || mLoadingDialog.isAdded()) {
            return;
        }

        mLoadingDialog.show(getSupportFragmentManager(), "LoadingDialog");
    }

    @Override
    public void showUncancelDialog() {

    }

    @Override
    public void hideProcessDialog() {
        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                if(mLoadingDialog != null) {
                    mLoadingDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, 0);

//        InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, 0);

//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

//        View view = getCurrentFocus() != null ? getCurrentFocus() : findViewById(android.R.id.content);
//        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus() != null ? getCurrentFocus() : findViewById(android.R.id.content);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(getQuitTransitionInAnim(), getQuitTransitionOutAnim());
    }

    // ===== 动画 ====
    private int defaultEnterAnim = R.anim.slide_in_from_right;
    private int defaultQuitAnim = R.anim.slide_out_to_right;
    public int getEnterTransitionInAnim() {
        return defaultEnterAnim;
    }

    public int getEnterTransitionOutAnim() {
        return R.anim.fade_out;
    }

    public int getQuitTransitionInAnim() {
        return 0;
    }

    public int getQuitTransitionOutAnim() {
        return defaultQuitAnim;
    }

    public void overrideCustomTransitionAnim() {}
    public void overrideCustomTransitionAnim(int inAnim, int outAnim) {
        this.defaultEnterAnim = inAnim;
        this.defaultQuitAnim = outAnim;
    }
}
