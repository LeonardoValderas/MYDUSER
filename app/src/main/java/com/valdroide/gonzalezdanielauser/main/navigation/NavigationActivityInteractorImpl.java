package com.valdroide.gonzalezdanielauser.main.navigation;

/**
 * Created by LEO on 11/2/2017.
 */

public class NavigationActivityInteractorImpl implements NavigationActivityInteractor {
    private NavigationActivityRepository repository;

    public NavigationActivityInteractorImpl(NavigationActivityRepository repository) {
        this.repository = repository;
    }


    @Override
    public void getCategoriesAndSubCategories() {
        repository.getCategoriesAndSubCategories();
    }
}
