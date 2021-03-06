package com.valdroide.gonzalezdanielauser.main.navigation.ui.adapters;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.valdroide.gonzalezdanielauser.BuildConfig;
import com.valdroide.gonzalezdanielauser.entities.SubCategory;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.FragmentMain;
import com.valdroide.gonzalezdanielauser.main.navigation.ui.NavigationActivity;
import com.valdroide.gonzalezdanielauser.R;

/**
 * Created by LEO on 11/2/2017.
 */

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager sInstance;

    private FragmentManager mFragmentManager;
    private NavigationActivity mActivity;

    public static FragmentNavigationManager obtain(NavigationActivity activity) {
        if (sInstance == null) {
            sInstance = new FragmentNavigationManager();
        }
        sInstance.configure(activity);
        return sInstance;
    }

    private void configure(NavigationActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragmentAction(SubCategory subCategory) {
        showFragment(FragmentMain.newInstance(subCategory), false);
    }

    private void showFragment(Fragment fragment, boolean allowStateLoss) {
        FragmentManager fm = mFragmentManager;

        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = fm.beginTransaction()
                .replace(R.id.container, fragment);

        ft.addToBackStack(null);

        if (allowStateLoss || !BuildConfig.DEBUG) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }

        fm.executePendingTransactions();
    }
}