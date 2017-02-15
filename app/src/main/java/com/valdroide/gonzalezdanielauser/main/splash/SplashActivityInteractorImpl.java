package com.valdroide.gonzalezdanielauser.main.splash;

public class SplashActivityInteractorImpl implements SplashActivityInteractor {

    private SplashActivityRepository repository;

    public SplashActivityInteractorImpl(SplashActivityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getDateTable() {
        repository.getDateTable();
    }

    @Override
    public void getAllData() {
        repository.getAllData();
    }

    @Override
    public void setDateTable(String date, String category, String subcategory, String clothes) {
        repository.setDateTable(date, category, subcategory, clothes);
    }
}
