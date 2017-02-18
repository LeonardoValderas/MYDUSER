package com.valdroide.gonzalezdanielauser.main.splash;

import android.content.Context;

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
    public void getAllData(Context context) {
        repository.getAllData(context);
    }

    @Override
    public void validateToken(Context context) {
        repository.validateToken(context);
    }

    @Override
    public void setDateTable(Context context, String date, String category, String subcategory, String clothes, String contact) {
        repository.setDateTable(context, date, category, subcategory, clothes, contact);
    }
}
