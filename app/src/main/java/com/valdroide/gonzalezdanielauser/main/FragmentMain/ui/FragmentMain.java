package com.valdroide.gonzalezdanielauser.main.FragmentMain.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.valdroide.gonzalezdanielauser.GonzalezDanielaUserApp;
import com.valdroide.gonzalezdanielauser.R;
import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.entities.DateTable;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.FragmentMainPresenter;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.dialogs.DialogClickClothes;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.adapters.FragmentMainAdapter;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.adapters.OnItemClickListener;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.NavigationActivity;
import com.valdroide.gonzalezdanielauser.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentMain extends Fragment implements FragmentMainView, OnItemClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.conteiner)
    FrameLayout conteiner;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.adView)
    AdView mAdView;
    @Inject
    FragmentMainAdapter adapter;
    @Inject
    FragmentMainPresenter presenter;

    GonzalezDanielaUserApp app;
    private List<Clothes> clothesList;
    private static String title = "";
    private static SubCategory subCategoryExtra;
    private ProgressDialog pDialog;
    private String cat = "", sub = "", date = "", clo = "", cont = "";
    private AdRequest adRequest;

    public FragmentMain() {
    }

    public static FragmentMain newInstance(SubCategory subCategory) {
        FragmentMain fragmentAction = new FragmentMain();
        subCategoryExtra = subCategory;
        title = subCategoryExtra.getSUBCATEGORY();
        return fragmentAction;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        setupInjection();
        presenter.onCreate();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Procesando...");
        pDialog.setCancelable(false);
        if (subCategoryExtra != null){
            pDialog.show();
            presenter.getListClothes(subCategoryExtra);
        }
        initRecyclerViewAdapter();
        initSwipeRefreshLayout();
        BannerAd();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void initRecyclerViewAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setupInjection() {
        app = (GonzalezDanielaUserApp) getActivity().getApplication();
        app.getFragmentMainComponent(this, this, this).inject(this);
    }

    public void verifySwipeRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getDateTable();
                presenter.refreshLayout(getActivity(), date, cat, sub, clo, cont);
            }
        });
    }
    public void BannerAd() {
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("B52960D9E6A2A5833E82FEA8ACD4B80C")
                .build();
        mAdView.loadAd(adRequest);
    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }
    @Override
    public void setListClothes(List<Clothes> clothes) {
        clothesList = clothes;
        pDialog.hide();
        adapter.setClothes(clothes);
        verifySwipeRefresh();
    }

    @Override
    public void setError(String mgs) {
        pDialog.hide();
        Utils.showSnackBar(conteiner, mgs);
        verifySwipeRefresh();
    }

    @Override
    public void withoutChange() {
        verifySwipeRefresh();
    }

    @Override
    public void callClothes() {
        if (subCategoryExtra != null)
            presenter.getListClothes(subCategoryExtra);
        else
            startActivity(new Intent(getActivity(), NavigationActivity.class));
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void setDateTable(List<DateTable> dateTable) {
         for (int i = 0; i <dateTable.size() ; i++) {
            switch (dateTable.get(i).getTABLENAME()){
                case Utils.TABLE:
                    date = dateTable.get(i).getDATE();
                    break;
                case Utils.CATEGORY:
                    cat = dateTable.get(i).getDATE();
                    break;
                case Utils.SUBCATEGORY:
                    sub = dateTable.get(i).getDATE();
                    break;
                case Utils.CLOTHES:
                    clo = dateTable.get(i).getDATE();
                    break;
                case Utils.CONTACT:
                    cont = dateTable.get(i).getDATE();
                    break;
            }
        }
    }

    @Override
    public void onClick(View view, int position) {
        new DialogClickClothes(getActivity(), clothesList.get(position), title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
