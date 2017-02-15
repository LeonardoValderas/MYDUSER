package com.valdroide.gonzalezdanielauser.main.navigation.ui;

import com.valdroide.gonzalezdanielauser.entities.Category;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;

import java.util.List;

/**
 * Created by LEO on 11/2/2017.
 */

public interface NavigationActivityView {
   void setListCategoriesAndSubCategories(List<Category> categories, List<SubCategory> subCategories);
}
