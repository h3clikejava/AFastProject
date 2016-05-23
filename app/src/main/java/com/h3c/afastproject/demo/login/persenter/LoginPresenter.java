package com.h3c.afastproject.demo.login.persenter;

import com.h3c.afastproject.base.BasePresenter;
import com.h3c.afastproject.demo.login.contract.LoginContract;
import com.wilddog.client.AuthData;
import com.wilddog.client.Wilddog;
import com.wilddog.client.WilddogError;

/**
 * Created by H3c on 16/5/22.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    private Wilddog mRef;

    public LoginPresenter(LoginContract.View view, Wilddog ref) {
        super(view);
        view.setPresenter(this);
        this.mRef = ref;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void login(String username, String password) {
        mRef.authWithPassword(username, password, new Wilddog.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                getUI().showResult("登录成功");
            }

            @Override
            public void onAuthenticationError(WilddogError wilddogError) {
                getUI().showResult("登录失败:" + wilddogError.getMessage());
            }
        });
    }

    @Override
    public void register(String username, String password) {
        mRef.createUser(username, password, new Wilddog.ResultHandler() {
            @Override
            public void onSuccess() {
                getUI().showResult("注册成功");
            }

            @Override
            public void onError(WilddogError wilddogError) {
                getUI().showResult("注册失败:" + wilddogError.getMessage());
            }
        });
    }
}
