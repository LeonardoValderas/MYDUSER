package com.valdroide.gonzalezdanielauser.main.FragmentMain.events;

import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.entities.DateTable;

import java.util.List;

/**
 * Created by LEO on 30/1/2017.
 */
public class FragmentMainEvent {
    private int type;
    public static final int GETLISTCLOTHES = 0;
    public static final int ERROR = 1;
    public static final int WITHOUTCHANGE = 2;
    public static final int CALLCLHOTHES = 3;
    public static final int GETDATETABLE = 4;
    private List<Clothes> clothesList;
    private List<DateTable> dateTables;
    private String error;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Clothes> getClothesList() {
        return clothesList;
    }

    public void setClothesList(List<Clothes> clothesList) {
        this.clothesList = clothesList;
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
