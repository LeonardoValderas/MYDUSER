package com.valdroide.gonzalezdanielauser.main.splash.di;

import com.valdroide.gonzalezdanielauser.GonzalezDanielaUserAppModule;
import com.valdroide.gonzalezdanielauser.lib.di.LibsModule;
import com.valdroide.gonzalezdanielauser.main.splash.ui.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SplashActivityModule.class, LibsModule.class, GonzalezDanielaUserAppModule.class})
public interface SplashActivityComponent {
    void inject(SplashActivity activity);
}
