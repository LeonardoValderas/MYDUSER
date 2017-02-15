package com.valdroide.gonzalezdanielauser.main.splash.di;


import com.valdroide.gonzalezdanielauser.api.APIService;
import com.valdroide.gonzalezdanielauser.api.ClothesClient;
import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.splash.SplashActivityInteractor;
import com.valdroide.gonzalezdanielauser.main.splash.SplashActivityInteractorImpl;
import com.valdroide.gonzalezdanielauser.main.splash.SplashActivityPresenter;
import com.valdroide.gonzalezdanielauser.main.splash.SplashActivityPresenterImpl;
import com.valdroide.gonzalezdanielauser.main.splash.SplashActivityRepository;
import com.valdroide.gonzalezdanielauser.main.splash.SplashActivityRepositoryImpl;
import com.valdroide.gonzalezdanielauser.main.splash.ui.SplashActivityView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashActivityModule {
     SplashActivityView view;
     public SplashActivityModule(SplashActivityView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    SplashActivityView providesSplashActivityView() {
        return this.view;
    }

    @Provides
    @Singleton
    SplashActivityPresenter providesSplashActivityPresenter(SplashActivityView view, EventBus eventBus, SplashActivityInteractor listInteractor) {
        return new SplashActivityPresenterImpl(view, eventBus, listInteractor);
    }

    @Provides
    @Singleton
    SplashActivityInteractor providesSplashActivityInteractor(SplashActivityRepository repository) {
        return new SplashActivityInteractorImpl(repository);
    }

    @Provides
    @Singleton
    SplashActivityRepository providesSplashActivityRepository(EventBus eventBus, APIService service) {
        return new SplashActivityRepositoryImpl(eventBus, service);
    }

    @Provides
    @Singleton
    APIService provideAPIService () {
        ClothesClient client = new ClothesClient();
        return client.getAPIService();
    }
}
