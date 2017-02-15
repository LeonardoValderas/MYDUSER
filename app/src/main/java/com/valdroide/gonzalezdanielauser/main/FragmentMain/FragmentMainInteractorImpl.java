package com.valdroide.gonzalezdanielauser.main.FragmentMain;


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
/*
    @Override
    public void getListCategory() {
        repository.getListCategory();
    }

    @Override
    public void getListSubCategory(int id_category) {
        repository.getListSubCategory(id_category);
    }

    @Override
    public void getListClothes(int id_category, int id_sub_category) {
        repository.getListClothes(id_category, id_sub_category);
    }

    @Override
    public void deleteClothes(Clothes clothes, DateTable dateTable) {
        repository.deleteClothes(clothes, dateTable);
    }

    @Override
    public void clickSwitch(Clothes clothes, DateTable dateTable) {
        repository.clickSwitch(clothes, dateTable);
    }
    */
}
