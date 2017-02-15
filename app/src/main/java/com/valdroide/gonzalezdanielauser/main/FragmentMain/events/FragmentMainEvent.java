package com.valdroide.gonzalezdanielauser.main.FragmentMain.events;


import com.valdroide.gonzalezdanielauser.entities.Clothes;

import java.util.List;

/**
 * Created by LEO on 30/1/2017.
 */
public class FragmentMainEvent {
    private int type;
    public static final int GETLISTCLOTHES = 0;
    public static final int ERROR = 1;
    private List<Clothes> clothesList;
     /*
    private List<Category> listCategories;
    private List<SubCategory> listSubCategories;
    private List<Clothes> clothesList;
    private Clothes clothes;
    */
    private String error;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
/*
    public List<Category> getListCategories() {
        return listCategories;
    }

    public void setListCategories(List<Category> listCategories) {
        this.listCategories = listCategories;
    }

    public List<SubCategory> getListSubCategories() {
        return listSubCategories;
    }

    public void setListSubCategories(List<SubCategory> listSubCategories) {
        this.listSubCategories = listSubCategories;
    }
*/
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
/*
    public Clothes getClothes() {
        return clothes;
    }

    public void setClothes(Clothes clothes) {
        this.clothes = clothes;
    }
*/
}
