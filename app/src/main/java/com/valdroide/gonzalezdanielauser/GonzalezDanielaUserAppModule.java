package com.valdroide.gonzalezdanielauser;
import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LEO on 29/1/2017.
 */
@Module
public class GonzalezDanielaUserAppModule {
    Application application;

    public GonzalezDanielaUserAppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }
}
