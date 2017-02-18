package com.valdroide.gonzalezdanielauser.main.FragmentMain;

import android.content.Context;

import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.valdroide.gonzalezdanielauser.api.APIService;
import com.valdroide.gonzalezdanielauser.entities.Category;
import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.entities.Clothes_Table;
import com.valdroide.gonzalezdanielauser.entities.Contact;
import com.valdroide.gonzalezdanielauser.entities.DateTable;
import com.valdroide.gonzalezdanielauser.entities.ResponseWS;
import com.valdroide.gonzalezdanielauser.entities.Result;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.entities.SubCategory_Table;
import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.events.FragmentMainEvent;
import com.valdroide.gonzalezdanielauser.main.splash.events.SplashActivityEvent;
import com.valdroide.gonzalezdanielauser.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentMainRepositoryImpl implements FragmentMainRepository {
    private EventBus eventBus;
    private List<Clothes> clothesList;
    private APIService service;
    List<ResponseWS> responseWses;
    List<Category> categories;
    List<SubCategory> subCategories;
    List<Clothes> clothes;
    List<DateTable> dateTables;
    List<Contact> contacts;

    public FragmentMainRepositoryImpl(EventBus eventBus, APIService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getListClothes(SubCategory subCategory) {
        ConditionGroup conditionGroup = ConditionGroup.clause();
        conditionGroup.and(Condition.column(new NameAlias("Clothes.ID_CATEGORY")).is(subCategory.getID_CATEGORY()));
        conditionGroup.and(Condition.column(new NameAlias("Clothes.ID_SUBCATEGORY")).is(subCategory.getID_SUBCATEGORY_KEY()));
        try {
            clothesList = SQLite.select()
                    .from(Clothes.class).innerJoin(SubCategory.class)
                    .on(Clothes_Table.ID_SUBCATEGORY.withTable().eq(SubCategory_Table.ID_SUBCATEGORY_KEY.withTable()))
                    .where(conditionGroup).orderBy(new NameAlias("SUBCATEGORY"), true).queryList();
            if (clothesList != null)
                post(FragmentMainEvent.GETLISTCLOTHES, clothesList);
            else
                post(FragmentMainEvent.ERROR, "Error en la base de datos.");
        } catch (Exception e){
            post(FragmentMainEvent.ERROR, e.getMessage());
        }
    }

    @Override
    public void getDateTable() {
        try {
            List<DateTable> dateTables = SQLite.select().from(DateTable.class).queryList();
            if (dateTables != null)
                post(FragmentMainEvent.GETDATETABLE, dateTables, true);
            else
                post(FragmentMainEvent.ERROR, "Error en la base de datos.");
        } catch (Exception e) {
            post(FragmentMainEvent.ERROR, e.getMessage());
        }
    }

    @Override
    public void refreshLayout(Context context, String date, String category, String subcategory, String clothesStg, String contact) {
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
                                post(FragmentMainEvent.CALLCLHOTHES);
                            } else if (responseWses.get(0).getSuccess().equals("4")) {
                                post(FragmentMainEvent.WITHOUTCHANGE);
                            } else {
                                post(FragmentMainEvent.ERROR, responseWses.get(0).getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        post(FragmentMainEvent.ERROR, t.getMessage());
                    }
                });
            } catch (Exception e) {
                post(FragmentMainEvent.ERROR, e.getMessage());
            }
        } else {
            post(FragmentMainEvent.ERROR, "Verificar su conexión de Internet.");
        }
    }

    public void post(int type) {
        post(type, null, null, null);
    }

    public void post(int type, List<Clothes> clothesList) {
        post(type, clothesList, null, null);
    }

    public void post(int type, String error) {
        post(type, null, null, error);
    }
    public void post(int type,  List<DateTable> dateTables, boolean isDate) {
        post(type, null, dateTables, null);
    }

    public void post(int type, List<Clothes> clothesList, List<DateTable> dateTables, String error) {
        FragmentMainEvent event = new FragmentMainEvent();
        event.setType(type);
        event.setClothesList(clothesList);
        event.setDateTables(dateTables);
        event.setError(error);
        eventBus.post(event);
    }
}
