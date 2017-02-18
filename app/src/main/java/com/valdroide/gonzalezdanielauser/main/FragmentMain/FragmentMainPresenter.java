package com.valdroide.gonzalezdanielauser.main.FragmentMain;

import android.content.Context;

import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.events.FragmentMainEvent;

public interface FragmentMainPresenter {
    void onCreate();
    void onDestroy();
    void getListClothes(SubCategory subCategory);
    void getDateTable();
    void refreshLayout(Context context, String date, String category, String subcategory, String clothes, String contact);
    void onEventMainThread(FragmentMainEvent event);
}
