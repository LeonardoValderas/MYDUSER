package com.valdroide.gonzalezdanielauser.main.navigation;

import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.valdroide.gonzalezdanielauser.api.APIService;
import com.valdroide.gonzalezdanielauser.entities.Category;
import com.valdroide.gonzalezdanielauser.entities.DateTable;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.navigation.events.NavigationActivityEvent;

import java.util.List;


public class NavigationActivityRepositoryImpl implements NavigationActivityRepository {
    private EventBus eventBus;
    private APIService service;
//    List<ResponseWS> responseWses;
//    List<Category> categories;
//    List<SubCategory> subCategories;
//    List<Clothes> clothes;
//    List<DateTable> dateTables;


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
    // @Override
//    public void getDateTable() {
//        try {
//            List<DateTable> dateTables = SQLite.select().from(DateTable.class).queryList();
//            if (dateTables != null)
//                if (dateTables.isEmpty())
//                    post(SplashActivityEvent.GETALLDATA);
//                else
//                    post(SplashActivityEvent.GOTOTAB);
//        } catch (Exception e) {
//            post(SplashActivityEvent.ERROR, e.getMessage());
//        }
//    }

//    @Override
//    public void getAllData(final Context context) {
//        try {
//            Call<Result> splashService = service.getAllData();
//            splashService.enqueue(new Callback<Result>() {
//                @Override
//                public void onResponse(Call<Result> call, Response<Result> response) {
//                    if (response.isSuccessful()) {
//
//                        responseWses = response.body().getResponseData();
//                        if (responseWses.get(0).getSuccess().equals("0")) {
//                            Delete.table(Category.class);
//                            Delete.table(SubCategory.class);
//                            Delete.table(Clothes.class);
//                            Delete.table(DateTable.class);
//
//                            categories = response.body().getCategory();
//                            subCategories = response.body().getSubcategory();
//                            clothes = response.body().getClothes();
//                            dateTables = response.body().getDate_table();
//
//                            for (DateTable dateTable : dateTables) {
//                                dateTable.save();
//                            }
//                            for (Clothes clothe : clothes) {
//                                clothe.save();
//                            }
//                            for (SubCategory subCategory : subCategories) {
//                                subCategory.save();
//                            }
//                            for (Category category : categories) {
//                                category.save();
//                            }
//                            post(SplashActivityEvent.GOTOTAB);
//                        } else {
//                            post(SplashActivityEvent.ERROR, responseWses.get(0).getMessage());
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Result> call, Throwable t) {
//                    post(SplashActivityEvent.ERROR, t.getMessage());
//                }
//            });
//        } catch (Exception e) {
//            post(SplashActivityEvent.ERROR, e.getMessage());
//        }
//    }

    public void post(int type) {
        post(type, null, null, null);
    }

    public void post(int type, List<Category> categories, List<SubCategory> subCategories) {
        post(type, categories, subCategories, null);
    }

    public void post(int type, String error) {
        post(type, null, null, error);
    }

    public void post(int type, List<Category> categories, List<SubCategory> subCategories, String error) {
        NavigationActivityEvent event = new NavigationActivityEvent();
        event.setType(type);
        event.setCategories(categories);
        event.setSubCategories(subCategories);
        event.setError(error);
        eventBus.post(event);
    }


}
