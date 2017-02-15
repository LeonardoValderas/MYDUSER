package com.valdroide.gonzalezdanielauser.main.navigation;

import com.valdroide.gonzalezdanielauser.main.navigation.events.NavigationActivityEvent;

/**
 * Created by LEO on 11/2/2017.
 */

public interface NavigationActivityPresenter {
    void onCreate();
    void onDestroy();
    void getCategoriesAndSubCategories();
    void onEventMainThread(NavigationActivityEvent event);
}
