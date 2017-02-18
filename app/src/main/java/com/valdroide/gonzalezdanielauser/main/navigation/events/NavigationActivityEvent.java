package com.valdroide.gonzalezdanielauser.main.navigation.events;


import com.valdroide.gonzalezdanielauser.entities.Category;
import com.valdroide.gonzalezdanielauser.entities.Contact;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;

import java.util.List;

/**
 * Created by LEO on 30/1/2017.
 */
public class NavigationActivityEvent {
    private int type;
    public static final int GETCATEGORIESANDSUBCATEGORIES = 0;
    public static final int GETCONTACT = 1;
    public static final int ERROR = 2;
    private String error;
    private Contact contact;
    private List<Category> categories;
    private List<SubCategory> subCategories;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
