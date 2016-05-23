package com.h3c.afastproject.base;

import android.support.v4.app.Fragment;

import com.h3c.afastproject.base.baseInterface.IKeyboardStateControl;
import com.h3c.afastproject.base.baseInterface.IProcessDialog;


/**
 * Created by H3c on 16/5/22.
 */

public class BaseFragment extends Fragment implements IKeyboardStateControl,
        IProcessDialog {
    @Override
    public void showKeyboard() {
        if(getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).showKeyboard();
        }
    }

    @Override
    public void hideKeyboard() {
        if(getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).hideKeyboard();
        }
    }

    @Override
    public void showLoadingDialog() {
        if(getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).showLoadingDialog();
        }
    }

    @Override
    public void showUncancelDialog() {
        if(getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).showUncancelDialog();
        }
    }

    @Override
    public void hideProcessDialog() {
        if(getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).hideProcessDialog();
        }
    }
}
