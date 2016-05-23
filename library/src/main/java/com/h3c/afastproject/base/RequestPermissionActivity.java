package com.h3c.afastproject.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * 集成权限请求的Activity基类
 * 当Android6.0以上版本需要动态请求权限
 * 所以这里实现一个任意Activity均可请求权限的功能
 *
 * 注意，除了BaseActivity，其他对象尽量不要直接继承该Activity
 *
 * Created by H3c on 16/5/16.
 */
public abstract class RequestPermissionActivity extends AppCompatActivity {
    public static final int REQUEST_WRITE_STORAGE = 112;
    public static final int REQUEST_CAMERA = 113;
    public static final int REQUEST_READ_CONTACTS = 114;
    public static final int REQUEST_LOCATION = 115;
    public static final int REQUEST_RECORD = 116;

    private RequestPermissionListener mRequestPermissionListener;
    // 请求SD卡写权限
    public boolean requestWriteStoragePermission(RequestPermissionListener listener) {
        return requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_WRITE_STORAGE, listener);
    }

    // 请求相机权限
    public boolean requestCameraPermission(RequestPermissionListener listener) {
        return requestPermission(Manifest.permission.CAMERA, REQUEST_CAMERA, listener);
    }

    // 请求联系人读权限
    public boolean requestReadContactsPermission(RequestPermissionListener listener) {
        return requestPermission(Manifest.permission.READ_CONTACTS, REQUEST_READ_CONTACTS, listener);
    }

    // 请求定位权限
    public boolean requestLocationPermission(RequestPermissionListener listener) {
        return requestPermission(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_LOCATION, listener);
    }

    // 请求麦克风权限
    public boolean requestRecodePermission(RequestPermissionListener listener) {
        return requestPermission(Manifest.permission.RECORD_AUDIO, REQUEST_RECORD, listener);
    }

    private boolean requestPermission(String permissionName, int permissionTag, RequestPermissionListener listener) {
        return requestPermission(new String[]{permissionName}, permissionTag, listener);
    }
    private boolean requestPermission(String[] permissionNames, int permissionTag, RequestPermissionListener listener) {
        mRequestPermissionListener = listener;
        boolean hasPermission = true;

        for (String permissionName: permissionNames) {
            boolean flag = (ContextCompat.checkSelfPermission(this, permissionName)
                    == PackageManager.PERMISSION_GRANTED);
            if(!flag) {
                hasPermission = false;
                break;
            }
        }

        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, permissionNames, permissionTag);
        } else {
            if(listener != null) {
                listener.requestPermissionSuccess(permissionTag);
            }
        }

        return hasPermission;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(mRequestPermissionListener != null) {
                mRequestPermissionListener.requestPermissionSuccess(requestCode);
            }
        } else {
            if(mRequestPermissionListener != null) {
                mRequestPermissionListener.requestPermissionFail(requestCode);
            }
        }
    }

    public interface RequestPermissionListener {
        void requestPermissionSuccess(int permission);
        void requestPermissionFail(int permission);
    }
}
