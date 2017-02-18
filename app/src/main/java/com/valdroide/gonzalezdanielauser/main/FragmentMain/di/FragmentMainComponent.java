package com.valdroide.gonzalezdanielauser.main.FragmentMain.di;
import com.valdroide.gonzalezdanielauser.GonzalezDanielaUserAppModule;
import com.valdroide.gonzalezdanielauser.lib.di.LibsModule;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.FragmentMainPresenter;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.FragmentMain;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FragmentMainModule.class, LibsModule.class, GonzalezDanielaUserAppModule.class})
public interface FragmentMainComponent {
      void inject(FragmentMain fragment);
}
