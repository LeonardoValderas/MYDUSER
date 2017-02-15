package com.valdroide.gonzalezdanielauser.main.FragmentMain.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.valdroide.gonzalezdanielauser.GonzalezDanielaUserApp;
import com.valdroide.gonzalezdanielauser.R;
import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.FragmentMainPresenter;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.dialogs.DialogClickClothes;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.adapters.FragmentMainAdapter;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.adapters.OnItemClickListener;
import com.valdroide.gonzalezdanielauser.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.internal.Util;

public class FragmentMain extends Fragment implements FragmentMainView, OnItemClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.conteiner)
    FrameLayout conteiner;
    @Inject
    FragmentMainAdapter adapter;
    @Inject
    FragmentMainPresenter presenter;

    GonzalezDanielaUserApp app;
    private List<Clothes> clothesList;
    private static String title = "";
    private static SubCategory subCategoryExtra;

    public FragmentMain() {
    }

    public static FragmentMain newInstance(SubCategory subCategory) {
        FragmentMain fragmentAction = new FragmentMain();
//        Bundle args = new Bundle();
//        args.putString(KEY_MOVIE_TITLE, subCategory.getSUBCATEGORY());
//        fragmentAction.setArguments(args);

        subCategoryExtra = subCategory;
        title = subCategoryExtra.getSUBCATEGORY();
        return fragmentAction;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        setupInjection();
        presenter.onCreate();
        if (subCategoryExtra != null)
            presenter.getListClothes(subCategoryExtra);
        //initAdapterRecycler();
        initRecyclerViewAdapter();
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

    @Override
    public void setListClothes(List<Clothes> clothes) {
        clothesList = clothes;
        adapter.setClothes(clothes);
    }

    @Override
    public void setError(String mgs) {
        Utils.showSnackBar(conteiner, mgs);
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
}
