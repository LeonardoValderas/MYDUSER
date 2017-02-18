package com.valdroide.gonzalezdanielauser.main.splash.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.valdroide.gonzalezdanielauser.GonzalezDanielaUserApp;
import com.valdroide.gonzalezdanielauser.R;
import com.valdroide.gonzalezdanielauser.entities.DateTable;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.NavigationActivity;
import com.valdroide.gonzalezdanielauser.main.splash.SplashActivityPresenter;
import com.valdroide.gonzalezdanielauser.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements SplashActivityView {

    @Bind(R.id.imageViewLogo)
    ImageView imageViewLogo;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.activity_splash)
    LinearLayout conteiner;
    @Inject
    SplashActivityPresenter presenter;
    GonzalezDanielaUserApp app;
    @Bind(R.id.textViewDownload)
    TextView textViewDownload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setupInjection();
        presenter.onCreate();
        presenter.validateToken(this);
        getDateTable();
    }

    private void setupInjection() {
        app = (GonzalezDanielaUserApp) getApplication();
        app.getSplashActivityComponent(this, this).inject(this);
    }

    @Override
    public void gotToNavigation() {
        progressBar.setVisibility(View.INVISIBLE);
        startActivity(new Intent(this, NavigationActivity.class));
    }

    @Override
    public void getDateTableEmpty() {
        presenter.getAllData(this);
    }

    @Override
    public void setDateTable(List<DateTable> dateTables) {
        String date = "",cat = "", sub = "", clo = "", contact = "";
        for (int i = 0; i <dateTables.size() ; i++) {
            switch (dateTables.get(i).getTABLENAME()){
                case Utils.TABLE:
                    date = dateTables.get(i).getDATE();
                    break;
                case Utils.CATEGORY:
                    cat = dateTables.get(i).getDATE();
                    break;
                case Utils.SUBCATEGORY:
                    sub = dateTables.get(i).getDATE();
                    break;
                case Utils.CLOTHES:
                    clo = dateTables.get(i).getDATE();
                    break;
                case Utils.CONTACT:
                    contact = dateTables.get(i).getDATE();
                    break;
            }
        }
        presenter.setDateTable(this, date, cat, sub, clo, contact);
    }

    public void getDateTable() {
        progressBar.setVisibility(View.VISIBLE);
        presenter.getDateTable();
    }

    @Override
    public void setError(String msg) {
        progressBar.setVisibility(View.INVISIBLE);
        textViewDownload.setText(msg);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
