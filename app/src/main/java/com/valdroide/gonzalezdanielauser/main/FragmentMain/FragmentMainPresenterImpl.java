package com.valdroide.gonzalezdanielauser.main.FragmentMain;

import android.content.Context;

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

    @Override
    public void getDateTable() {
        interactor.getDateTable();
    }

    @Override
    public void refreshLayout(Context context, String date, String category, String subcategory, String clothes, String contact) {
        interactor.refreshLayout(context, date, category, subcategory, clothes, contact);
    }

    @Override
    @Subscribe
    public void onEventMainThread(FragmentMainEvent event) {
        if (this.view != null) {
            switch (event.getType()) {
                case FragmentMainEvent.GETLISTCLOTHES:
                    view.setListClothes(event.getClothesList());
                    break;
                case FragmentMainEvent.WITHOUTCHANGE:
                    view.withoutChange();
                    break;
                case FragmentMainEvent.CALLCLHOTHES:
                    view.callClothes();
                    break;
                case FragmentMainEvent.GETDATETABLE:
                    view.setDateTable(event.getDateTables());
                    break;
                case FragmentMainEvent.ERROR:
                    view.setError(event.getError());
                    break;
            }
        }
    }
}
