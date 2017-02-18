package com.valdroide.gonzalezdanielauser.main.FragmentMain.ui;

import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.entities.DateTable;

import java.util.List;

public interface FragmentMainView {
    void setListClothes(List<Clothes> clothes);
    void setError(String mgs);
    void withoutChange();
    void callClothes();
    void setDateTable(List<DateTable> dateTable);
}
