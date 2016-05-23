package com.h3c.afastproject.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.h3c.afastproject.R;
import com.h3c.afastproject.base.BaseDialog;
import com.h3c.afastproject.utils.StringUtils;
import com.h3c.afastproject.utils.ViewUtils;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by H3c on 16/5/23.
 */

public class LoadingDialog extends BaseDialog {
    private TextView contentTV;

    @Override
    public int setContentLayout() {
        return R.layout.dialog_loading;
    }

    @Override
    public void initView(View v) {
        contentTV = ViewUtils.fTV(v, R.id.dialog_loadingTV);
        setContent(content);
    }

    private String content;
    public void setContent(String content) {
        this.content = content;
        if(contentTV != null) {
            contentTV.setText(TextUtils.isEmpty(content) ? StringUtils.getString(R.string.loading) : content);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(false);
            setCancelable(false);
        }

        PublishSubject.create().timer(5, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                if (getDialog() != null && getDialog().isShowing()) {
                    getDialog().setCanceledOnTouchOutside(true);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listener != null) {
            listener.loadingDialogState(0);
        }
    }

    public LoadingDialogListener listener;
    public interface LoadingDialogListener {
        void loadingDialogState(int state);
    }
}
