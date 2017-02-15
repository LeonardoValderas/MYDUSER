package com.valdroide.gonzalezdanielauser.main.navigation.di;
import com.valdroide.gonzalezdanielauser.GonzalezDanielaUserAppModule;
import com.valdroide.gonzalezdanielauser.lib.di.LibsModule;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.NavigationActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NavigationActivityModule.class, LibsModule.class, GonzalezDanielaUserAppModule.class})
public interface NavigationActivityComponent {
    void inject(NavigationActivity activity);
}
