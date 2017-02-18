package com.valdroide.gonzalezdanielauser.main.navigation;

import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.valdroide.gonzalezdanielauser.api.APIService;
import com.valdroide.gonzalezdanielauser.entities.Category;
import com.valdroide.gonzalezdanielauser.entities.Contact;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.navigation.events.NavigationActivityEvent;

import java.util.List;

public class NavigationActivityRepositoryImpl implements NavigationActivityRepository {
    private EventBus eventBus;
    private APIService service;

    public NavigationActivityRepositoryImpl(EventBus eventBus, APIService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getCategoriesAndSubCategories() {
        try {
            List<Category> categories = SQLite.select().from(Category.class).orderBy(new NameAlias("CATEGORY"), true).queryList();
            List<SubCategory> subCategories = SQLite.select().from(SubCategory.class).orderBy(new NameAlias("ID_CATEGORY"), true).queryList();
            if (categories != null && subCategories != null)
                post(NavigationActivityEvent.GETCATEGORIESANDSUBCATEGORIES, categories, subCategories);
            else
                post(NavigationActivityEvent.ERROR, "Error en la base de datos.");
        } catch (Exception e) {
            post(NavigationActivityEvent.ERROR, e.getMessage());
        }
    }

    @Override
    public void getContact() {
        try {
            Contact contact = SQLite.select().from(Contact.class).querySingle();
            post(NavigationActivityEvent.GETCONTACT, contact);
        } catch (Exception e) {
            post(NavigationActivityEvent.ERROR, e.getMessage());
        }
    }

    public void post(int type) {
        post(type, null, null, null, null);
    }

    public void post(int type, List<Category> categories, List<SubCategory> subCategories) {
        post(type, categories, subCategories, null, null);
    }

    public void post(int type, String error) {
        post(type, null, null, null, error);
    }
    public void post(int type, Contact contact) {
        post(type, null, null, contact, null);
    }

    public void post(int type, List<Category> categories, List<SubCategory> subCategories, Contact contact, String error) {
        NavigationActivityEvent event = new NavigationActivityEvent();
        event.setType(type);
        event.setCategories(categories);
        event.setSubCategories(subCategories);
        event.setContact(contact);
        event.setError(error);
        eventBus.post(event);
    }
}
