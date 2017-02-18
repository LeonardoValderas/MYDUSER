package com.valdroide.gonzalezdanielauser.main.splash;

import android.content.Context;

public interface SplashActivityRepository {
    void getDateTable();
    void getAllData(Context context);
    void validateToken(Context context);
    void setDateTable(Context context, String date, String category, String subcategory, String clothes, String contact);
}
