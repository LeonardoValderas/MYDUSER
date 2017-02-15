package com.valdroide.gonzalezdanielauser.main.FragmentMain.ui;

import com.valdroide.gonzalezdanielauser.entities.Clothes;

import java.util.List;

/**
 * Created by LEO on 14/2/2017.
 */

public interface FragmentMainView {
    void setListClothes(List<Clothes> clothes);
    void setError(String mgs);
}
