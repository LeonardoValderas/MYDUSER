package com.valdroide.gonzalezdanielauser.main.splash;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.valdroide.gonzalezdanielauser.api.APIService;
import com.valdroide.gonzalezdanielauser.entities.Category;
import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.entities.DateTable;
import com.valdroide.gonzalezdanielauser.entities.ResponseWS;
import com.valdroide.gonzalezdanielauser.entities.Result;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.splash.events.SplashActivityEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivityRepositoryImpl implements SplashActivityRepository {
    private EventBus eventBus;
    private APIService service;
    List<ResponseWS> responseWses;
    List<Category> categories;
    List<SubCategory> subCategories;
    List<Clothes> clothes;
    List<DateTable> dateTables;


    public SplashActivityRepositoryImpl(EventBus eventBus, APIService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getDateTable() {
        try {
            List<DateTable> dateTables = SQLite.select().from(DateTable.class).queryList();
            if (dateTables != null)
                if (dateTables.isEmpty())
                    post(SplashActivityEvent.GETALLDATA);
                else
                    post(SplashActivityEvent.SENDDATETABLE, dateTables);
        } catch (Exception e) {
            post(SplashActivityEvent.ERROR, e.getMessage());
        }
    }

    @Override
    public void getAllData() {
        try {
            Call<Result> splashService = service.getAllData();
            splashService.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {

                        responseWses = response.body().getResponseData();
                        if (responseWses.get(0).getSuccess().equals("0")) {

                            Delete.table(Category.class);
                            Delete.table(SubCategory.class);
                            Delete.table(Clothes.class);
                            Delete.table(DateTable.class);

                            categories = response.body().getCategory();
                            subCategories = response.body().getSubcategory();
                            clothes = response.body().getClothes();
                            dateTables = response.body().getDate_table();

                            for (DateTable dateTable : dateTables) {
                                dateTable.save();
                            }
                            for (Clothes clothe : clothes) {
                                clothe.save();
                            }
                            for (SubCategory subCategory : subCategories) {
                                subCategory.save();
                            }
                            for (Category category : categories) {
                                category.save();
                            }
                            post(SplashActivityEvent.GOTONAVIGATION);
                        } else {
                            post(SplashActivityEvent.ERROR, responseWses.get(0).getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    post(SplashActivityEvent.ERROR, t.getMessage());
                }
            });
        } catch (Exception e) {
            post(SplashActivityEvent.ERROR, e.getMessage());
        }
    }

    @Override
    public void setDateTable(String date, String category, String subcategory, String clothesStg) {
        try {
            Call<Result> splashService = service.sendDateTable(date, category, subcategory, clothesStg);
            splashService.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {

                        responseWses = response.body().getResponseData();
                        if (responseWses.get(0).getSuccess().equals("0")) {

                            categories = response.body().getCategory();
                            if (categories != null) {
                                Delete.table(Category.class);

                                for (Category category : categories) {
                                    category.save();
                                }
                            }

                            subCategories = response.body().getSubcategory();
                            if (subCategories != null) {
                                Delete.table(SubCategory.class);

                                for (SubCategory subCategory : subCategories) {
                                    subCategory.save();
                                }
                            }
                            clothes = response.body().getClothes();
                            if (clothes != null) {
                                Delete.table(Clothes.class);

                                for (Clothes clothe : clothes) {
                                    clothe.save();
                                }
                            }

                            dateTables = response.body().getDate_table();
                            if (dateTables != null) {
                                Delete.table(DateTable.class);

                                for (DateTable dateTable : dateTables) {
                                    dateTable.save();
                                }
                            }

                            post(SplashActivityEvent.GOTONAVIGATION);
                        } else if (responseWses.get(0).getSuccess().equals("0")) {
                            post(SplashActivityEvent.GOTONAVIGATION);
                        } else {
                            post(SplashActivityEvent.ERROR, responseWses.get(0).getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    post(SplashActivityEvent.ERROR, t.getMessage());
                }
            });
        } catch (Exception e) {
            post(SplashActivityEvent.ERROR, e.getMessage());
        }
    }

    public void post(int type) {
        post(type, null, null);
    }

    public void post(int type, List<DateTable> dateTables) {
        post(type, dateTables, null);
    }

    public void post(int type, String error) {
        post(type, null, error);
    }

    public void post(int type, List<DateTable> dateTables, String error) {
        SplashActivityEvent event = new SplashActivityEvent();
        event.setType(type);
        event.setDateTables(dateTables);
        event.setError(error);
        eventBus.post(event);
    }
}
