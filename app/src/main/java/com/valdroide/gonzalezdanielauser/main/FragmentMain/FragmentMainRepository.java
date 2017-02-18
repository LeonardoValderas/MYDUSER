package com.valdroide.gonzalezdanielauser.main.FragmentMain;


import android.content.Context;

import com.valdroide.gonzalezdanielauser.entities.SubCategory;

public interface FragmentMainRepository {
    void getListClothes(SubCategory subCategory);
    void getDateTable();
    void refreshLayout(Context context, String date, String category, String subcategory, String clothes, String contact);
}
