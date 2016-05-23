package com.h3c.afastproject.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.h3c.afastproject.AFastProject;

import java.util.HashSet;

/**
 * Created by H3c on 16/5/23.
 */

public class NetworkUtils {
    public final static int NETWORK_TYPE_NOT_CONN = 0;
    public final static int NETWORK_TYPE_WIFI = 1;
    public final static int NETWORK_TYPE_MOBILE = 2;
    public final static int NETWORK_TYPE_OTHER = 3;

    private static WifiManager mWifiManager;
    private static NetworkUtils instance;
    private ConnectivityManager mConnMgr;
    private NetworkReceiver receiver;
    int mCurrentNetworkConnType;// 当前网络类型

    private NetworkUtils() {
        initConnMgr(AFastProject.getApplicationContext());

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        AFastProject.getApplicationContext().registerReceiver(receiver, filter);
    }

    public static NetworkUtils getInstance() {
        if(instance == null) {
            instance = new NetworkUtils();
        }

        return instance;
    }

    public static WifiManager getWifiManager() {
        if(mWifiManager == null) {
            mWifiManager = (WifiManager) AFastProject.getApplicationContext().
                    getSystemService(Context.WIFI_SERVICE);
        }

        return mWifiManager;
    }

    public boolean isWifiConn() {
        if(mConnMgr == null) {
            return false;
        }

        NetworkInfo networkInfo = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean flag = (networkInfo != null && networkInfo.isConnected());
        if(flag) {
            mCurrentNetworkConnType = NETWORK_TYPE_WIFI;
        }
        return flag;
    }

    public boolean isMobileConn() {
        if(mConnMgr == null) {
            return false;
        }

        NetworkInfo networkInfo = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean flag = (networkInfo != null && networkInfo.isConnected());
        if(flag) {
            mCurrentNetworkConnType = NETWORK_TYPE_MOBILE;
        }
        return flag;
    }

    public boolean isNetworkConn() {
        if(mConnMgr == null) {
            return false;
        }

        NetworkInfo networkInfo = mConnMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public int getNetworkConnType() {
        if(mConnMgr == null) {
            mCurrentNetworkConnType = NETWORK_TYPE_NOT_CONN;
        } else {
            NetworkInfo networkInfo = mConnMgr.getActiveNetworkInfo();
            mCurrentNetworkConnType = setConnTypeByNetworkInfo(networkInfo);
        }

        return mCurrentNetworkConnType;
    }

    private void initConnMgr(Context context) {
        if(mConnMgr == null) {
            mConnMgr =  (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            initConnMgr(context);
            NetworkInfo networkInfo = mConnMgr.getActiveNetworkInfo();

            int currentConnType = setConnTypeByNetworkInfo(networkInfo);
            // 注意同一种状态会回调多次

            if(mCurrentNetworkConnType != currentConnType) {
                mCurrentNetworkConnType = currentConnType;
                if(listeners != null) {
                    for (NetWorkStateListener listener : listeners) {
                        listener.onNetWorkStateChanged(currentConnType);
                    }
                }
            }
        }
    }

    private int setConnTypeByNetworkInfo(NetworkInfo info) {
        if(info != null && info.isConnected()) {// 网络连接了
            if(info.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_TYPE_WIFI;
            } else if(info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NETWORK_TYPE_MOBILE;
            } else {
                return NETWORK_TYPE_OTHER;
            }
        } else {// 网络没有连接
            return NETWORK_TYPE_NOT_CONN;
        }
    }

    private HashSet<NetWorkStateListener> listeners;

    public void addNetworkStateListener(NetWorkStateListener listener) {
        if(listeners == null) {
            listeners = new HashSet<>(2);
        }

        listeners.add(listener);
    }

    public void removeNetworkStateListener(NetWorkStateListener listener) {
        if(listeners != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public interface NetWorkStateListener {
        public void onNetWorkStateChanged(int currentNetworkType);
    }

    public static final String WIFI = "wifi";
    public static final String WIMAX = "wimax";
    // mobile
    public static final String MOBILE = "mobile";
    // 2G network types
    public static final String GSM = "gsm";
    public static final String GPRS = "gprs";
    public static final String EDGE = "edge";
    // 3G network types
    public static final String CDMA = "cdma";
    public static final String UMTS = "umts";
    public static final String HSPA = "hspa";
    public static final String HSUPA = "hsupa";
    public static final String HSDPA = "hsdpa";
    public static final String ONEXRTT = "1xrtt";
    public static final String EHRPD = "ehrpd";
    // 4G network types
    public static final String LTE = "lte";
    public static final String UMB = "umb";
    public static final String HSPA_PLUS = "hspa+";
    // return type
    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_ETHERNET = "ethernet";
    public static final String TYPE_WIFI = "wifi";
    public static final String TYPE_2G = "2g";
    public static final String TYPE_3G = "3g";
    public static final String TYPE_4G = "4g";
    public static final String TYPE_NONE = "none";
    /**
     * 诊断并获取网络的联网方式
     *
     * @return the type of mobile network we are on
     */
    public static String getNetWorkType() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                AFastProject.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null) {
            String type = info.getTypeName();

            if (type.toLowerCase().equals(WIFI)) {
                return TYPE_WIFI;
            }
            else if (type.toLowerCase().equals(MOBILE)) {
                type = info.getSubtypeName();
                if (type.toLowerCase().equals(GSM) ||
                        type.toLowerCase().equals(GPRS) ||
                        type.toLowerCase().equals(EDGE)) {
                    return TYPE_2G;
                }
                else if (type.toLowerCase().startsWith(CDMA) ||
                        type.toLowerCase().equals(UMTS) ||
                        type.toLowerCase().equals(ONEXRTT) ||
                        type.toLowerCase().equals(EHRPD) ||
                        type.toLowerCase().equals(HSUPA) ||
                        type.toLowerCase().equals(HSDPA) ||
                        type.toLowerCase().equals(HSPA)) {
                    return TYPE_3G;
                }
                else if (type.toLowerCase().equals(LTE) ||
                        type.toLowerCase().equals(UMB) ||
                        type.toLowerCase().equals(HSPA_PLUS)) {
                    return TYPE_4G;
                }
            }
        }
        else {
            return TYPE_NONE;
        }
        return TYPE_UNKNOWN;
    }
}
