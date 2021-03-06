package com.valdroide.gonzalezdanielauser.main.navigation;

import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.navigation.events.NavigationActivityEvent;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.NavigationActivityView;

import org.greenrobot.eventbus.Subscribe;

public class NavigationActivityPresenterImpl implements NavigationActivityPresenter {


    private NavigationActivityView view;
    private EventBus eventBus;
    private NavigationActivityInteractor interactor;

    public NavigationActivityPresenterImpl(NavigationActivityView view, EventBus eventBus, NavigationActivityInteractor interactor) {
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
    public void getCategoriesAndSubCategories() {
        interactor.getCategoriesAndSubCategories();
    }

    @Override
    public void getContact() {
        interactor.getContact();
    }

    @Override
    @Subscribe
    public void onEventMainThread(NavigationActivityEvent event) {
        if (this.view != null) {
            switch (event.getType()) {
                case NavigationActivityEvent.GETCATEGORIESANDSUBCATEGORIES:
                    view.setListCategoriesAndSubCategories(event.getCategories(), event.getSubCategories());
                    break;
                case NavigationActivityEvent.ERROR:
                    view.setError(event.getError());
                    break;
                case NavigationActivityEvent.GETCONTACT:
                    view.setContact(event.getContact());
                    break;
            }
        }
    }
}
