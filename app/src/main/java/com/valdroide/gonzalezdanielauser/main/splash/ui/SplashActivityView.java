package com.valdroide.gonzalezdanielauser.main.splash.ui;


import com.valdroide.gonzalezdanielauser.entities.DateTable;

import java.util.List;

/**
 * Created by LEO on 8/2/2017.
 */

public interface SplashActivityView {
    void gotToNavigation();
    void setError(String msg);
    void getDateTableEmpty();
    void setDateTable(List<DateTable> dateTables);
}
