package com.valdroide.gonzalezdanielauser;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;


import com.raizlabs.android.dbflow.config.FlowManager;
import com.valdroide.gonzalezdanielauser.lib.di.LibsModule;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.di.DaggerFragmentMainComponent;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.di.FragmentMainComponent;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.di.FragmentMainModule;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.FragmentMainView;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.adapters.OnItemClickListener;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.NavigationActivityView;
import com.valdroide.gonzalezdanielauser.main.navigation.di.DaggerNavigationActivityComponent;
import com.valdroide.gonzalezdanielauser.main.navigation.di.NavigationActivityComponent;
import com.valdroide.gonzalezdanielauser.main.navigation.di.NavigationActivityModule;
import com.valdroide.gonzalezdanielauser.main.splash.di.DaggerSplashActivityComponent;
import com.valdroide.gonzalezdanielauser.main.splash.di.SplashActivityComponent;
import com.valdroide.gonzalezdanielauser.main.splash.di.SplashActivityModule;
import com.valdroide.gonzalezdanielauser.main.splash.ui.SplashActivityView;

/**
 * Created by LEO on 29/1/2017.
 */
public class GonzalezDanielaUserApp extends Application {
    private LibsModule libsModule;
    private GonzalezDanielaUserAppModule gonzalezDanielaUserAppModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initModules();
        initDB();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBTearDown();
    }

    private void DBTearDown() {
        FlowManager.destroy();
    }

    private void initDB() {
        FlowManager.init(this);
    }


    private void initModules() {
        libsModule = new LibsModule();
        gonzalezDanielaUserAppModule = new GonzalezDanielaUserAppModule(this);
    }

    public NavigationActivityComponent getNavigationActivityComponent(Activity activity, Context context, NavigationActivityView view) {
        return DaggerNavigationActivityComponent
                .builder()
                .gonzalezDanielaUserAppModule(gonzalezDanielaUserAppModule)
                .libsModule(new LibsModule(activity))
                .navigationActivityModule(new NavigationActivityModule(context, view))
                .build();
    }
    public SplashActivityComponent getSplashActivityComponent(SplashActivityView view, Activity activity) {
        return DaggerSplashActivityComponent
                .builder()
                .gonzalezDanielaUserAppModule(gonzalezDanielaUserAppModule)
                .libsModule(new LibsModule(activity))
                .splashActivityModule(new SplashActivityModule(view))
                .build();
    }

    public FragmentMainComponent getFragmentMainComponent(FragmentMainView view, Fragment fragment, OnItemClickListener onItemClickListener) {
        return DaggerFragmentMainComponent
                .builder()
                .gonzalezDanielaUserAppModule(gonzalezDanielaUserAppModule)
                .libsModule(new LibsModule(fragment))
                .fragmentMainModule(new FragmentMainModule(view, fragment, onItemClickListener))
                .build();
    }
//    public FragmentEditClothesComponent getFragmentEditClothesComponent(FragmentEditClothesView view, Fragment fragment, com.valdroide.gonzalezdanielaadm.main.fragment_edit_clothes.adapters.OnItemClickListener onItemClickListener) {
//        return DaggerFragmentEditClothesComponent
//                .builder()
//                .gonzalezDanielaAdmAppModule(gonzalezDanielaAdmAppModule)
//                .libsModule(new LibsModule(fragment))
//                .fragmentEditClothesModule(new FragmentEditClothesModule(view, fragment, onItemClickListener))
//                .build();
//    }
//

//
//    public ActivitySubCategoryComponent getActivitySubCategoryComponent(ActivitySubCategoryView view, Activity activity, com.valdroide.gonzalezdanielaadm.main.subcategory.ui.adapters.OnItemClickListener onItemClickListener, Context context) {
//        return DaggerActivitySubCategoryComponent
//                .builder()
//                .gonzalezDanielaAdmAppModule(gonzalezDanielaAdmAppModule)
//                .libsModule(new LibsModule(activity))
//                .activitySubCategoryModule(new ActivitySubCategoryModule(context, view, onItemClickListener))
//                .build();
//    }
//
//    public NotificationActivityComponent getNotificationActivityComponent(NotificationActivityView view, Activity activity) {
//        return DaggerNotificationActivityComponent
//                .builder()
//                .gonzalezDanielaAdmAppModule(gonzalezDanielaAdmAppModule)
//                .libsModule(new LibsModule(activity))
//                .notificationActivityModule(new NotificationActivityModule(view))
//                .build();
//    }
//
//    public SplashActivityComponent getSplashActivityComponent(SplashActivityView view, Activity activity) {
//        return DaggerSplashActivityComponent
//                .builder()
//                .gonzalezDanielaAdmAppModule(gonzalezDanielaAdmAppModule)
//                .libsModule(new LibsModule(activity))
//                .splashActivityModule(new SplashActivityModule(view))
//                .build();
//    }
}
