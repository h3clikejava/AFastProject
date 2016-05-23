package com.h3c.afastproject.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by H3c on 16/5/23.
 */

public abstract class BaseDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(setContentLayout(), null, false);
        ButterKnife.bind(v);
        initView(v);
        builder.setView(v);
        return builder.create();
    }

    abstract public int setContentLayout();
    abstract public void initView(View v);
}
