package com.valdroide.gonzalezdanielauser.main.FragmentMain;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.events.FragmentMainEvent;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.FragmentMainView;

import org.greenrobot.eventbus.Subscribe;

public class FragmentMainPresenterImpl implements FragmentMainPresenter {


    private FragmentMainView view;
    private EventBus eventBus;
    private FragmentMainInteractor interactor;

    public FragmentMainPresenterImpl(FragmentMainView view, EventBus eventBus, FragmentMainInteractor interactor) {
        this.view = view;
        this.eventBus = eventBus;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void getListClothes(SubCategory subCategory) {
        interactor.getListClothes(subCategory);
    }

    /*
        @Override
        public void getListCategory() {
            interactor.getListCategory();
        }

        @Override
        public void getListSubCategory(int id_category) {
            interactor.getListSubCategory(id_category);
        }

        @Override
        public void getListClothes(int id_category, int id_sub_category) {
            interactor.getListClothes(id_category, id_sub_category);
        }

        @Override
        public void deleteClothes(Clothes clothes, DateTable dateTable) {
            interactor.deleteClothes(clothes, dateTable);
        }

        @Override
        public void clickSwitch(Clothes clothes, DateTable dateTable) {
            interactor.clickSwitch(clothes, dateTable);
        }
    */
    @Override
    @Subscribe
    public void onEventMainThread(FragmentMainEvent event) {
        if (this.view != null) {
            switch (event.getType()) {
                case FragmentMainEvent.GETLISTCLOTHES:
                    view.setListClothes(event.getClothesList());
                    break;
                case FragmentMainEvent.ERROR:
                    view.setError(event.getError());
                    break;
            }
        }
    }
}
