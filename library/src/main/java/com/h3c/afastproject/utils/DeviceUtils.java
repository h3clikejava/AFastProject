package com.h3c.afastproject.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.h3c.afastproject.AFastProject;
import com.h3c.afastproject.sharedpreference.AppSPreferences;

import java.io.File;

/**
 * Created by H3c on 16/5/23.
 */

public class DeviceUtils {
    public static ApplicationInfo mApplicationInfo;
    public static PackageManager mPackageManager;
    public static PackageInfo mPackageInfo;
    public static TelephonyManager mTelephonyManager;

    public static float density;
    public static float scaledDensity;
    public static int screenWPixels;
    public static int screenHPixels;
    public static int statusBarHeight;

    static {
        WindowManager wm = (WindowManager) AFastProject.getApplicationContext().
                getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        density = (float) metrics.widthPixels / metrics.densityDpi;
        density = metrics.density;
        scaledDensity = metrics.scaledDensity;

        screenWPixels = AppSPreferences.getInstance().getInt("SCREEN_WIDTH", 0);
        if(screenWPixels < 1 && size.x < size.y) {
            screenWPixels = size.x;
            AppSPreferences.getInstance().putInt("SCREEN_WIDTH", screenWPixels);
        }
        screenHPixels = AppSPreferences.getInstance().getInt("SCREEN_HEIGHT", 0);
        if(screenHPixels < 1) {
            screenHPixels = size.y;
            AppSPreferences.getInstance().putInt("SCREEN_HEIGHT", screenHPixels);
        }

        int resourceId =  AFastProject.getApplicationContext()
                .getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = AFastProject.getApplicationContext().getResources()
                    .getDimensionPixelSize(resourceId);
        }
    }

    private static void initPackageInfo() {
        if(mPackageManager == null) {
            mPackageManager = AFastProject.getApplicationContext().getPackageManager();
        }

        if(mPackageInfo == null) {
            try {
                mPackageInfo = mPackageManager.getPackageInfo(
                        AFastProject.getApplicationContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initTelephonyManager() {
        if(mTelephonyManager == null) {
            mTelephonyManager = (TelephonyManager) AFastProject.getApplicationContext().
                    getSystemService(Context.TELEPHONY_SERVICE);
        }
    }

    public static int spToPx(int sp) {
        return (int) (sp * scaledDensity);
    }

    public static int dpToPx(double dp) {
        return (int) (dp * density);
    }

    /**
     * 手机型号简称(GT-N7102)
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 手机型号(n7102)
     * @return
     */
    public static String getDeviceName() {
        return Build.DEVICE;
    }

    /**
     * 手机厂家(samsung)
     * @return
     */
    public static String getManufacturer() {
        // Build.BRAND 也可以
        return Build.MANUFACTURER;
    }

    /**
     * 硬件序列号(4dfd09d082156009)
     * @return
     */
    public static String getSerial() {
        return Build.SERIAL;
    }

    /**
     * 获得Mac地址(34:23:ba:14:07:62)
     * Android M(6.0)开始不允许获取Mac地址及蓝牙地址
     * so一般情况下不要用Mac地址做为任何判定
     * 避免系统升级导致的Bug
     *
     * @return
     */
    public static String getMacAddress() {
        WifiManager wifiManager = (WifiManager) AFastProject.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if(wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            return info.getMacAddress();
        }

        return null;
    }

    /**
     * 设备IMEI(355546057471164)
     * 6.0上没有权限会崩溃
     *
     * @return
     */
    public static String getIMEI() {
        initTelephonyManager();

        String imei = null;
        if(mTelephonyManager != null) {
            try {
                imei = mTelephonyManager.getDeviceId();
            } catch (Exception e) {
                LogUtils.e("获取IMEI失败，可能没有权限:" + e.getMessage());
            }
        }

        return imei;
    }

    /**
     * SIM卡序列号(89860113871048601206)
     * @return
     */
    public static String getSIMCardSerial() {
        initTelephonyManager();

        if(mTelephonyManager != null) {
            return mTelephonyManager.getSimSerialNumber();
        }

        return null;
    }

    /**
     * SIM卡Id(460013242301689)
     * @return
     */
    public static String getSIMCardId() {
        initTelephonyManager();

        if(mTelephonyManager != null) {
            return mTelephonyManager.getSubscriberId();
        }

        return null;
    }

    /**
     * 手机号(+8615623240890)
     * @return
     */
    public static String getPhoneNum() {
        initTelephonyManager();

        if(mTelephonyManager != null) {
            String phoneNumStr = mTelephonyManager.getLine1Number();
            if(phoneNumStr != null && phoneNumStr.startsWith("+86")) {
                phoneNumStr = phoneNumStr.substring(3);
            }

            long phoneNum = 0;
            try {
                phoneNum = Long.valueOf(phoneNumStr);
            } catch (Exception e) {
            }

            if(phoneNum > 0) {
                return phoneNumStr;
            }
        }

        return null;
    }

    /**
     * 设备唯一ID(cd2e5f0b4f365e41)
     * @return
     */
    public static String getDeviceUniqueId() {
        return Settings.Secure.getString(
                AFastProject.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getOSName() {
        return Build.PRODUCT;
    }

    /**
     * 系统版本号(17)
     * @return
     */
    public static int getOSVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * App版本名
     * @return
     */
    public static String getAppVersionName() {
        initPackageInfo();

        if(mPackageInfo != null) {
            return mPackageInfo.versionName;
        }

        return null;
    }

    /**
     * App版本号
     * @return
     */
    public static int getAppVersionCode() {
        initPackageInfo();

        if(mPackageInfo != null) {
            return mPackageInfo.versionCode;
        }

        return 0;
    }

    public static String getPackageName() {
        initPackageInfo();

        if(mPackageInfo != null) {
            return mPackageInfo.packageName;
        }

        return null;
    }

    /**
     * 判断是否有物理按键
     * @return
     */
    public static boolean hasPermanentMenuKey() {
        return ViewConfiguration.get(AFastProject.getApplicationContext())
                .hasPermanentMenuKey();
    }

    /**
     * 是否透明底栏
     * @return
     */
    public static boolean isTranslucentNavigation() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && !DeviceUtils.hasPermanentMenuKey();

    }

    /**
     * 获得SD卡存储大小
     * @return
     */
    public static long getSDCardTotalSize() {
        String sDcString = Environment.getExternalStorageState();
        if (sDcString.equals(Environment.MEDIA_MOUNTED)) {
            // 取得sdcard文件路径
            File pathFile = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(pathFile.getPath());
            // 获取SDCard上BLOCK总数
            long nTotalBlocks;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                nTotalBlocks = statfs.getBlockCountLong();
            } else {
                nTotalBlocks = statfs.getBlockCount();
            }
            // 获取SDCard上每个block的SIZE
            long nBlocSize;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                nBlocSize = statfs.getBlockSizeLong();
            } else {
                nBlocSize = statfs.getBlockSize();
            }
            // 计算SDCard 总容量大小MB
            long nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;
            return nSDTotalSize;
        } else {
            return 0;
        }
    }

    /**
     * 获得SD卡可用存储空间，单位是M
     * @return
     */
    public static long getSDCardFreeSize() {
        String sDcString = Environment.getExternalStorageState();
        if (sDcString.equals(Environment.MEDIA_MOUNTED)) {
            // 取得sdcard文件路径
            File pathFile = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(pathFile.getPath());
            // 获取可供程序使用的Block的数量
            long nAvailaBlock;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                nAvailaBlock = statfs.getAvailableBlocksLong();
            } else {
                nAvailaBlock = statfs.getAvailableBlocks();
            }
            // 获取SDCard上每个block的SIZE
            long nBlocSize;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                nBlocSize = statfs.getBlockSizeLong();
            } else {
                nBlocSize = statfs.getBlockSize();
            }
            long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
            return nSDFreeSize;
        } else {
            return 0;
        }
    }

    /**
     * 检查某个包是否安装
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(String packageName) {
        if (TextUtils.isEmpty(packageName)) return false;
        try {
            AFastProject.getApplicationContext().getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void installAPK(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AFastProject.getApplicationContext().startActivity(intent);
    }
}
