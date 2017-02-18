package com.valdroide.gonzalezdanielauser.main.splash;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.valdroide.gonzalezdanielauser.api.APIService;
import com.valdroide.gonzalezdanielauser.entities.Category;
import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.entities.Contact;
import com.valdroide.gonzalezdanielauser.entities.DateTable;
import com.valdroide.gonzalezdanielauser.entities.ResponseWS;
import com.valdroide.gonzalezdanielauser.entities.Result;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.fcm.FmcInstanceIdService;
import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.splash.events.SplashActivityEvent;
import com.valdroide.gonzalezdanielauser.utils.Utils;

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
    List<Contact> contacts;
    String old_token = "";
    String new_token = "";


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
    public void getAllData(Context context) {
        if (Utils.isNetworkAvailable(context)) {
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
                                Delete.table(Contact.class);

                                categories = response.body().getCategory();
                                subCategories = response.body().getSubcategory();
                                clothes = response.body().getClothes();
                                dateTables = response.body().getDate_table();
                                contacts = response.body().getContacts();

                                if(dateTables != null)
                                for (DateTable dateTable : dateTables) {
                                    dateTable.save();
                                }
                                if(clothes != null)
                                for (Clothes clothe : clothes) {
                                    clothe.save();
                                }
                                if(subCategories != null)
                                for (SubCategory subCategory : subCategories) {
                                    subCategory.save();
                                }
                                if(categories != null)
                                for (Category category : categories) {
                                    category.save();
                                }
                                if(contacts != null)
                                for (Contact contact : contacts) {
                                    contact.save();
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
        } else {
            post(SplashActivityEvent.ERROR, "Verificar su conexión de Internet.");
        }

    }

    @Override
    public void validateToken(final Context context) {
        if (Utils.isNetworkAvailable(context)) {
            Utils.processToken(context, FirebaseInstanceId.getInstance().getToken());
            if (Utils.getIsNewToken(context)) {
                old_token = Utils.getOldToken(context);
                new_token = Utils.getToken(context);
                try {
                    Call<Result> tokenService = service.insertToken(old_token, new_token);
                    tokenService.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccessful()) {
                                responseWses = response.body().getResponseData();
                                if (responseWses.get(0).getSuccess().equals("0")) {
                                    Utils.resetOldToken(context);
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
        } else {
            post(SplashActivityEvent.ERROR, "Verificar su conexión de Internet.");
        }
    }

    @Override
    public void setDateTable(Context context, String date, String category, String subcategory, String clothesStg, String contact) {
        if (Utils.isNetworkAvailable(context)) {
            try {
                Call<Result> splashService = service.sendDateTable(date, category, subcategory, clothesStg, contact);
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
                                contacts = response.body().getContacts();
                                if (contacts != null) {
                                    Delete.table(Contact.class);

                                    for (Contact contact : contacts) {
                                        contact.save();
                                    }
                                }
                                post(SplashActivityEvent.GOTONAVIGATION);
                            } else if (responseWses.get(0).getSuccess().equals("4")) {
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
        } else {
            post(SplashActivityEvent.ERROR, "Verificar su conexión de Internet.");
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
