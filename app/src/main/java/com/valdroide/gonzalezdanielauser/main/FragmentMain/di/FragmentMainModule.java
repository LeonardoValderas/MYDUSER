package com.valdroide.gonzalezdanielauser.main.FragmentMain.di;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.valdroide.gonzalezdanielauser.api.APIService;
import com.valdroide.gonzalezdanielauser.api.ClothesClient;
import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.lib.base.EventBus;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.FragmentMainInteractor;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.FragmentMainInteractorImpl;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.FragmentMainPresenter;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.FragmentMainPresenterImpl;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.FragmentMainRepository;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.FragmentMainRepositoryImpl;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.FragmentMainView;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.adapters.FragmentMainAdapter;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.adapters.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LEO on 30/1/2017.
 */
@Module
public class FragmentMainModule {
    FragmentMainView view;
    Fragment fragment;
    OnItemClickListener onItemClickListener;

    public FragmentMainModule(FragmentMainView view, Fragment fragment, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.fragment = fragment;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides
    @Singleton
    FragmentMainView provideEditClothesView() {
        return this.view;
    }

    @Provides
    @Singleton
    FragmentMainPresenter provideFragmentMainPresenter(FragmentMainView view, EventBus eventBus, FragmentMainInteractor interactor) {
        return new FragmentMainPresenterImpl(view, eventBus, interactor);
    }

    @Provides
    @Singleton
    FragmentMainInteractor provideFragmentMainInteractor(FragmentMainRepository repository) {
        return new FragmentMainInteractorImpl(repository);
    }

    @Provides
    @Singleton
    FragmentMainRepository provideFragmentMainRepository(EventBus eventBus, APIService service) {
        return new FragmentMainRepositoryImpl(eventBus, service);
    }

    @Provides
    @Singleton
    List<Clothes> provideClothesList() {
        return new ArrayList<Clothes>();
    }

    @Provides
    @Singleton
    FragmentMainAdapter providesFragmentMainAdapter(List<Clothes> clothesList, OnItemClickListener onItemClickListener, Fragment fragment) {
        return new FragmentMainAdapter(clothesList, onItemClickListener, fragment);
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return this.onItemClickListener;
    }

    @Provides
    @Singleton
    APIService provideAPIService () {
        ClothesClient client = new ClothesClient();
        return client.getAPIService();
    }
}
