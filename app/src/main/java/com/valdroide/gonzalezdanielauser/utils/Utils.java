package com.valdroide.gonzalezdanielauser.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.valdroide.gonzalezdanielauser.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Utils {


    public final static String TABLE = "TABLES";
    public final static String CATEGORY = "CATEGORY";
    public final static String SUBCATEGORY = "SUBCATEGORY";
    public final static String CLOTHES = "CLOTHES";
    public final static String CONTACT = "CONTACT";


    public static void showSnackBar(View conteiner, String msg) {
        Snackbar.make(conteiner, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void setPicasso(Context context, String url, final int resource, final ImageView imageView) {
        Picasso.with(context)
                .load(url).fit()
                .placeholder(resource)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        imageView.setImageResource(resource);
                    }
                });
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.FCM_TOKEN), "");
    }
    public static String getOldToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.FCM_OLD_TOKEN), "");
    }
    public static boolean getIsNewToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.FCM_IS_NEW), false);
    }

    public static void setOldAndNewToken(Context context, String old_token, String recent_token, boolean isNew) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.FCM_OLD_TOKEN), old_token);
        editor.putString(context.getString(R.string.FCM_TOKEN), recent_token);
        editor.putBoolean(context.getString(R.string.FCM_IS_NEW), isNew);
        editor.commit();
    }

    public static void resetOldToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.FCM_OLD_TOKEN), "");
        editor.putBoolean(context.getString(R.string.FCM_IS_NEW), false);
        editor.commit();
    }

    public static void processToken(Context context, String token){
        String current_token = "";
        if (token != null)
            if (!token.isEmpty()) {
                current_token = Utils.getToken(context);
                if (current_token != null)
                    if (current_token.isEmpty()) {
                        Utils.setOldAndNewToken(context, "", token, true);
                    } else {
                        if (token.compareTo(current_token) != 0) {
                            Utils.setOldAndNewToken(context, current_token, token, true);
                        }
                    }
            }
    }
    public static boolean isNetworkAvailable(Context context) {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
