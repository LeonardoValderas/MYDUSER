package com.valdroide.gonzalezdanielauser.main.splash.events;

import com.valdroide.gonzalezdanielauser.entities.DateTable;

import java.util.List;

/**
 * Created by LEO on 30/1/2017.
 */
public class SplashActivityEvent {
    private int type;
    public static final int GETALLDATA = 0;
    public static final int ERROR = 1;
    public static final int GOTONAVIGATION = 2;
    public static final int SENDDATETABLE = 3;

    private String error;
    private List<DateTable> dateTables;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DateTable> getDateTables() {
        return dateTables;
    }

    public void setDateTables(List<DateTable> dateTables) {
        this.dateTables = dateTables;
    }
}
