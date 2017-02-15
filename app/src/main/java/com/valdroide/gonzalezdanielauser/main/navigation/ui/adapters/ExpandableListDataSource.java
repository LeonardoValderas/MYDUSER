package com.valdroide.gonzalezdanielauser.main.navigation.ui.adapters;

import android.content.Context;

import com.valdroide.gonzalezdanielauser.entities.Category;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by LEO on 11/2/2017.
 */

public class ExpandableListDataSource {
    public static Map<String, List<SubCategory>> getData(List<Category> categories, List<SubCategory> subCategories) {
        Map<String, List<SubCategory>> expandableListData = new TreeMap<>();

        for (int i = 0; i <categories.size() ; i++) {
            List<SubCategory> subCategoriesAux = new ArrayList<>();
            for (int j = 0; j <subCategories.size() ; j++) {
                if(categories.get(i).getID_CATEGORY_KEY() == subCategories.get(j).getID_CATEGORY())
                subCategoriesAux.add(subCategories.get(j));
            }
            expandableListData.put(categories.get(i).getCATEGORY(), subCategoriesAux);
        }
        //expandableListData.put(categories.get(0), subCategories);

        return expandableListData;
    }
}
