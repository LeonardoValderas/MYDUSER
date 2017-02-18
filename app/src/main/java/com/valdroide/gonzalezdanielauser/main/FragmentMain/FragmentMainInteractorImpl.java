package com.valdroide.gonzalezdanielauser.main.FragmentMain;


import android.content.Context;

import com.valdroide.gonzalezdanielauser.entities.SubCategory;

public class FragmentMainInteractorImpl implements FragmentMainInteractor {

    private FragmentMainRepository repository;

    public FragmentMainInteractorImpl(FragmentMainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getListClothes(SubCategory subCategory) {
        repository.getListClothes(subCategory);
    }

    @Override
    public void getDateTable() {
        repository.getDateTable();
    }

    @Override
    public void refreshLayout(Context context, String date, String category, String subcategory, String clothes, String contact) {
        repository.refreshLayout(context, date, category, subcategory, clothes, contact);
    }
}
