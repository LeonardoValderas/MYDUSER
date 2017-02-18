package com.valdroide.gonzalezdanielauser.main.splash;

import android.content.Context;

import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.splash.events.SplashActivityEvent;
import com.valdroide.gonzalezdanielauser.main.splash.ui.SplashActivityView;

import org.greenrobot.eventbus.Subscribe;

public class SplashActivityPresenterImpl implements SplashActivityPresenter {


    private SplashActivityView view;
    private EventBus eventBus;
    private SplashActivityInteractor interactor;

    public SplashActivityPresenterImpl(SplashActivityView view, EventBus eventBus, SplashActivityInteractor interactor) {
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
    public void getDateTable() {
        interactor.getDateTable();
    }

    @Override
    public void setDateTable(Context context, String date, String category, String subcategory, String clothes, String contact) {
        interactor.setDateTable(context, date, category, subcategory, clothes, contact);
    }

    @Override
    public void getAllData(Context context) {
        interactor.getAllData(context);
    }

    @Override
    public void validateToken(Context context) {
        interactor.validateToken(context);
    }

    @Override
    @Subscribe
    public void onEventMainThread(SplashActivityEvent event) {
        if (this.view != null) {
            switch (event.getType()) {
                case SplashActivityEvent.GETALLDATA:
                    view.getDateTableEmpty();
                    break;
                case SplashActivityEvent.SENDDATETABLE:
                    view.setDateTable(event.getDateTables());
                    break;
                case SplashActivityEvent.GOTONAVIGATION:
                    view.gotToNavigation();
                    break;
                case SplashActivityEvent.ERROR:
                    view.setError(event.getError());
                    break;
            }
        }
    }
}
