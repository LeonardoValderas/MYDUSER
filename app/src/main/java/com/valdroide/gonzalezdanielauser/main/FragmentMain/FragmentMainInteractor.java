package com.valdroide.gonzalezdanielauser.main.FragmentMain;


import android.content.Context;

import com.valdroide.gonzalezdanielauser.entities.SubCategory;

/**
 * Created by LEO on 30/1/2017.
 */
public interface FragmentMainInteractor {
    void getListClothes(SubCategory subCategory);
    void getDateTable();
    void refreshLayout(Context context, String date, String category, String subcategory, String clothes, String contact);
 }
