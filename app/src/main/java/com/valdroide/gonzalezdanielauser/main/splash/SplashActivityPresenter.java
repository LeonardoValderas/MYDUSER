package com.valdroide.gonzalezdanielauser.main.splash;

import com.valdroide.gonzalezdanielauser.main.splash.events.SplashActivityEvent;

public interface SplashActivityPresenter {
    void onCreate();
    void onDestroy();
    void getDateTable();
    void setDateTable(String date, String category, String subcategory, String clothes);
    void getAllData();
    void onEventMainThread(SplashActivityEvent event);
}
