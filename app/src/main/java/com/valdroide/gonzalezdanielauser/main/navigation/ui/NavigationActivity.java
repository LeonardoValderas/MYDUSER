package com.valdroide.gonzalezdanielauser.main.navigation.ui;

import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.valdroide.gonzalezdanielauser.GonzalezDanielaUserApp;
import com.valdroide.gonzalezdanielauser.R;
import com.valdroide.gonzalezdanielauser.entities.Category;
import com.valdroide.gonzalezdanielauser.entities.Contact;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.main.navigation.NavigationActivityPresenter;
import com.valdroide.gonzalezdanielauser.main.navigation.dialogs.DialogClickContact;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.adapters.CustomExpandableListAdapter;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.adapters.ExpandableListDataSource;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.adapters.FragmentNavigationManager;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.adapters.NavigationManager;
import com.valdroide.gonzalezdanielauser.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NavigationActivity extends AppCompatActivity implements NavigationActivityView {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.navList)
    ExpandableListView mExpandableListView;
    @Inject
    CustomExpandableListAdapter mExpandableListAdapter;
    @Inject
    List<String> mExpandableListTitle;
    @Inject
    Map<String, List<SubCategory>> mExpandableListData;
    GonzalezDanielaUserApp app;
    @Inject
    NavigationActivityPresenter presenter;

    private ActionBarDrawerToggle mDrawerToggle;

    private NavigationManager mNavigationManager;
    List<SubCategory> value;
    private Contact contact;
    private int positionChild = 0;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;
    private int conteo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);


        setupInjection();
        presenter.onCreate();
        mNavigationManager = FragmentNavigationManager.obtain(this);

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        mExpandableListView.addHeaderView(listHeaderView);

        addDrawerItems();
        presenter.getCategoriesAndSubCategories();
        setupDrawer();
        InterstitialAd();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("M & D");
    }

    private void setupInjection() {
        app = (GonzalezDanielaUserApp) getApplication();
        app.getNavigationActivityComponent(this, this, this).inject(this);
    }

    @Override
    public void setListCategoriesAndSubCategories(List<Category> categories, List<SubCategory> subCategories) {
        mExpandableListData = ExpandableListDataSource.getData(categories, subCategories);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        mExpandableListAdapter.setList(mExpandableListTitle, mExpandableListData);
    }

    public void InterstitialAd() {
        mInterstitialAd = new InterstitialAd(NavigationActivity.this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_navigation));

        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("B52960D9E6A2A5833E82FEA8ACD4B80C")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void conteoClick() {
        if (conteo == 10) {
            showInterstitial();
            conteo = 0;
        } else {
            conteo++;
        }
    }

    @Override
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void setError(String msg) {
        Utils.showSnackBar(mDrawerLayout, msg);
    }

    private void addDrawerItems() {
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                value = mExpandableListData.get(mExpandableListTitle.get(groupPosition));
                positionChild = childPosition;
                mNavigationManager.showFragmentAction(value.get(childPosition));
                conteoClick();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousItem)
                    mExpandableListView.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("M & D");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (value != null)
                    if (value.get(positionChild) != null)
                        getSupportActionBar().setTitle(value.get(positionChild).getSUBCATEGORY());
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.contact) {
            presenter.getContact();
            new DialogClickContact(this, contact);
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
