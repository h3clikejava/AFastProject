package com.h3c.afastproject.demo.login;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.h3c.afastproject.base.BaseActivity;
import com.h3c.afastproject.demo.R;
import com.h3c.afastproject.demo.login.contract.LoginContract;
import com.h3c.afastproject.demo.login.persenter.LoginPresenter;
import com.h3c.afastproject.utils.TaskUtils;
import com.wilddog.client.Wilddog;

import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by H3c on 16/5/22.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    private LoginContract.Presenter mPersenter;

    @Override
    protected void getIntentDataInActivityBase(Bundle savedInstanceState) {

    }

    @Override
    protected int initResLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        showLoadingDialog();

        TaskUtils.timer(5, TimeUnit.SECONDS, new Action1() {
            @Override
            public void call(Object o) {
                hideProcessDialog();
            }
        });
    }

    @Override
    protected void initData() {
        Wilddog.setAndroidContext(this);
        new LoginPresenter(this, new Wilddog("https://h3c.wilddogio.com/"));
    }

    @OnClick(R.id.loginBtn)
    void loginBtnClick() {
        final String username = ((EditText) findViewById(R.id.usernameET)).getText().toString();
        final String password = ((EditText) findViewById(R.id.passwordET)).getText().toString();
        mPersenter.login(username, password);
    }

    @OnClick(R.id.registerBtn)
    void registerBtnClick() {
        final String username = ((EditText) findViewById(R.id.usernameET)).getText().toString();
        final String password = ((EditText) findViewById(R.id.passwordET)).getText().toString();
        mPersenter.register(username, password);
    }

    @Override
    public void showResult(String info) {
        ((TextView)findViewById(R.id.stateTV)).setText(info);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPersenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
