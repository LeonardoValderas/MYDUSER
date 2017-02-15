package com.valdroide.gonzalezdanielauser.main.FragmentMain;

import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.events.FragmentMainEvent;

public interface FragmentMainPresenter {
    void onCreate();
    void onDestroy();
    void getListClothes(SubCategory subCategory);
    /*
    void getListCategory();
    void getListSubCategory(int id_category);
    void getListClothes(int id_category, int id_sub_category);
    void deleteClothes(Clothes clothes, DateTable dateTable);
    void clickSwitch(Clothes clothes, DateTable dateTable);
    */
    void onEventMainThread(FragmentMainEvent event);

}
