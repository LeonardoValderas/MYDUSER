package com.valdroide.gonzalezdanielauser.lib.di;

import com.valdroide.gonzalezdanielauser.GonzalezDanielaUserAppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LibsModule.class, GonzalezDanielaUserAppModule.class})
public interface LibsComponent {
}
