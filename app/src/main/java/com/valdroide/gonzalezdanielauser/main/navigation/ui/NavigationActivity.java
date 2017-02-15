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

import com.valdroide.gonzalezdanielauser.GonzalezDanielaUserApp;
import com.valdroide.gonzalezdanielauser.R;
import com.valdroide.gonzalezdanielauser.entities.Category;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.main.navigation.NavigationActivityPresenter;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.adapters.CustomExpandableListAdapter;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.adapters.ExpandableListDataSource;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.adapters.FragmentNavigationManager;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.adapters.NavigationManager;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("M & D");
    }

    private void setupInjection() {
        app = (GonzalezDanielaUserApp) getApplication();
        app.getNavigationActivityComponent(this, this, this).inject(this);
    }

    private void selectFirstItemAsDefault() {
        if (mNavigationManager != null) {
            //       String firstActionMovie = getResources().getStringArray(R.array.actionFilms)[0];
            mNavigationManager.showFragmentAction(new SubCategory());
            //  getSupportActionBar().setTitle(firstActionMovie);
        }
    }

    @Override
    public void setListCategoriesAndSubCategories(List<Category> categories, List<SubCategory> subCategories) {
        mExpandableListData = ExpandableListDataSource.getData(categories, subCategories);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        mExpandableListAdapter.setList(mExpandableListTitle, mExpandableListData);
    }

    private void addDrawerItems() {
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
             //   getSupportActionBar().setTitle("LISTA DE ROPA");
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //getSupportActionBar().setTitle("LISTA DE ROPA");
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                value = mExpandableListData.get(mExpandableListTitle.get(groupPosition));
                mNavigationManager.showFragmentAction(value.get(0));
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
                if(value != null)
                if(value.get(0) != null)
                getSupportActionBar().setTitle(value.get(0).getSUBCATEGORY());
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

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
