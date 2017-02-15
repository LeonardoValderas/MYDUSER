package com.valdroide.gonzalezdanielauser.main.FragmentMain;

import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.valdroide.gonzalezdanielauser.api.APIService;
import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.entities.Clothes_Table;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.entities.SubCategory_Table;
import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.events.FragmentMainEvent;

import java.util.List;


public class FragmentMainRepositoryImpl implements FragmentMainRepository {
    private EventBus eventBus;
    /*
    private List<Category> categories;
    private List<SubCategory> subCategories;
        */
    private List<Clothes> clothesList;

    private APIService service;
    final String[] success = {""};
    final String[] message = {""};

    public FragmentMainRepositoryImpl(EventBus eventBus, APIService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getListClothes(SubCategory subCategory) {
//        ConditionGroup conditionGroup = ConditionGroup.clause();
//        conditionGroup.and(Condition.column(new NameAlias("ID_CATEGORY")).is(subCategory.getID_CATEGORY())).and(Condition.column(new NameAlias("ID_SUBCATEGORY")).is(subCategory.getID_SUBCATEGORY_KEY()));
        ConditionGroup conditionGroup = ConditionGroup.clause();
        conditionGroup.and(Condition.column(new NameAlias("Clothes.ID_CATEGORY")).is(subCategory.getID_CATEGORY()));
        conditionGroup.and(Condition.column(new NameAlias("Clothes.ID_SUBCATEGORY")).is(subCategory.getID_SUBCATEGORY_KEY()));
        try {

            clothesList = SQLite.select()
                    .from(Clothes.class).innerJoin(SubCategory.class)
                    .on(Clothes_Table.ID_SUBCATEGORY.withTable().eq(SubCategory_Table.ID_SUBCATEGORY_KEY.withTable()))
                    .where(conditionGroup).orderBy(new NameAlias("SUBCATEGORY"), true).queryList();

         //   clothesList = SQLite.select().from(Clothes.class).where(conditionGroup).orderBy(new NameAlias("ID_SUBCATEGORY"), true).queryList();
            if (clothesList != null)
                post(FragmentMainEvent.GETLISTCLOTHES, clothesList);
            else
                post(FragmentMainEvent.ERROR, "Error en la base de datos.");
        } catch (Exception e){
            post(FragmentMainEvent.ERROR, e.getMessage());
        }
    }
/*
    @Override
    public void getListCategory() {
        categories = SQLite.select().from(Category.class).where().orderBy(new NameAlias("CATEGORY"), true).queryList();
        if (categories != null)
            post(FragmentEditClothesEvent.GETLISTCATEGORY, categories);
        else
            post(FragmentEditClothesEvent.ERROR, "Error en la base de datos.");
    }

    @Override
    public void getListSubCategory(int id_category) {
        ConditionGroup conditionGroup = ConditionGroup.clause();
        conditionGroup.and(Condition.column(new NameAlias("ID_CATEGORY")).is(id_category));
        subCategories = SQLite.select().from(SubCategory.class).where(conditionGroup).orderBy(new NameAlias("SUBCATEGORY"), true).queryList();

        if (subCategories != null)
            post(FragmentEditClothesEvent.GETLISTSUBCATEGORY, subCategories, true);
        else
            post(FragmentEditClothesEvent.ERROR, "Error en la base de datos.");
    }

    @Override
    public void getListClothes(int id_category, int id_sub_category) {
        ConditionGroup conditionGroup = ConditionGroup.clause();
        conditionGroup.and(Condition.column(new NameAlias("Clothes.ID_CATEGORY")).is(id_category));
        conditionGroup.and(Condition.column(new NameAlias("Clothes.ID_SUBCATEGORY")).is(id_sub_category));

        clothesList = SQLite.select()
                .from(Clothes.class).innerJoin(SubCategory.class)
                .on(Clothes_Table.ID_SUBCATEGORY.withTable().eq(SubCategory_Table.ID_SUBCATEGORY_KEY.withTable()))
                .where(conditionGroup).orderBy(new NameAlias("SUBCATEGORY"), true).queryList();

        if (clothesList != null)
            post(FragmentEditClothesEvent.GETLISTCLOTHES, 0, clothesList);
        else
            post(FragmentEditClothesEvent.ERROR, "Error en la base de datos.");
    }

    @Override
    public void deleteClothes(final Clothes clothes, final DateTable dateTable) {
        try {
            Call<Result> clothesService = service.deleteClothes(clothes.getID_CLOTHES_KEY(), clothes.getNAME_PHOTO(), dateTable.getDATE());
            clothesService.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {
                        message[0] = response.body().getMessage();
                        success[0] = response.body().getSuccess();

                        if (success[0].equals("0")) {
                            clothes.delete();
                            if (Utils.dateTables() != null) {
                                if (Utils.dateTables().size() <= 0) {
                                    Utils.switchTable();
                                }
                            }
                            Utils.updateDateTable(dateTable);
                            post(FragmentEditClothesEvent.DELETE, clothes);
                        } else {
                            post(FragmentEditClothesEvent.ERROR, message[0]);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    post(FragmentEditClothesEvent.ERROR, t.getMessage());
                }
            });
        } catch (Exception e) {
            post(FragmentEditClothesEvent.ERROR, e.getMessage());
        }
    }

    @Override
    public void clickSwitch(final Clothes clothes, final DateTable dateTable) {
        try {
            Call<Result> clothesService = service.updateActiveClothes(clothes.getID_CLOTHES_KEY(), clothes.getISACTIVE(), dateTable.getDATE());
            clothesService.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {
                        message[0] = response.body().getMessage();
                        success[0] = response.body().getSuccess();

                        if (success[0].equals("0")) {
                            clothes.update();
                            if (Utils.dateTables() != null) {
                                if (Utils.dateTables().size() <= 0) {
                                    Utils.switchTable();
                                }
                            }
                            Utils.updateDateTable(dateTable);
                            post(FragmentEditClothesEvent.ACTIVE, clothes);
                        } else {
                            post(FragmentEditClothesEvent.ERROR, message[0]);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    post(FragmentEditClothesEvent.ERROR, t.getMessage());
                }
            });
        } catch (Exception e) {
            post(FragmentEditClothesEvent.ERROR, e.getMessage());
        }
    }
*/
    public void post(int type) {
        post(type, null, null);
    }

    public void post(int type, List<Clothes> clothesList) {
        post(type, clothesList, null);
    }

    public void post(int type, String error) {
        post(type, null, error);
    }

    public void post(int type, List<Clothes> clothesList, String error) {
        FragmentMainEvent event = new FragmentMainEvent();
        event.setType(type);
        event.setClothesList(clothesList);
        event.setError(error);
        eventBus.post(event);
    }

}
