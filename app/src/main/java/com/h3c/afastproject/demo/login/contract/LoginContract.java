package com.h3c.afastproject.demo.login.contract;


import com.h3c.afastproject.base.baseInterface.BaseMVPView;

/**
 * Created by H3c on 16/5/22.
 */

public interface LoginContract {

    interface View extends BaseMVPView<Presenter> {
        void showResult(String info);
    }

    interface Presenter {
        void login(String username, String password);
        void register(String username, String password);
    }
}
