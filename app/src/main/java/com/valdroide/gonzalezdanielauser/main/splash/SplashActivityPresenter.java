package com.valdroide.gonzalezdanielauser.main.splash;

import android.content.Context;

import com.valdroide.gonzalezdanielauser.main.splash.events.SplashActivityEvent;

public interface SplashActivityPresenter {
    void onCreate();
    void onDestroy();
    void getDateTable();
    void setDateTable(Context context, String date, String category, String subcategory, String clothes, String contact);
    void getAllData(Context context);
    void validateToken(Context context);
    void onEventMainThread(SplashActivityEvent event);
}
