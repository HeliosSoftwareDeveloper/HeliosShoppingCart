package com.heliossoftwaredeveloper.heliosshoppingcart;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.heliossoftwaredeveloper.heliosshoppingcart.R;

/**
 * Created by rngrajo on 02/02/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public CoordinatorLayout mainContent;
    public DrawerLayout drawer;
    public NavigationView navigationView;
    public Toolbar toolbar;
    public FloatingActionButton fab;
    public ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mainContent = (CoordinatorLayout)findViewById(R.id.mainContent);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        setUpToggle();
    }

    public void setUpToggle(){

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0);
                mainContent.setTranslationX(slideOffset * drawerView.getWidth());
                drawer.bringChildToFront(drawerView);
                drawer.requestLayout();
            }
        };

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Doesn't have to be onBackPressed
                onBackPressed();
            }
        });

        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    public void showMessage(String message){
        Snackbar.make(mainContent, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    public void setToolbarTitle(String title){
        toolbar.setTitle(title);
    }

    public String getToolbarTitle(){
        return toolbar.getTitle().toString();
    }

    public void showBackButton(){
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();
    }

    public void showBurgerMenuButton(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Show hamburger
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    public boolean checkFragmentFromBackStack(String fragmentName){
        FragmentManager fragmentManager = getSupportFragmentManager();
        for(int i = 0; i< fragmentManager.getBackStackEntryCount(); i++){
            if(fragmentName.equals(fragmentManager.getBackStackEntryAt(i).getName()))
                return true;
        }
        return false;
    }

    public void addShowFragment(FragmentManager fragmentManager, Fragment fragmentToAdd, Fragment fragmentToHide, boolean isAddToBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);

        fragmentTransaction.add(R.id.fragment_container, fragmentToAdd,fragmentToAdd.getClass().getName());
        if (fragmentToHide!=null)
            fragmentTransaction.hide(fragmentToHide);
        fragmentTransaction.show(fragmentToAdd);
        if(isAddToBackStack)
            fragmentTransaction.addToBackStack(fragmentToAdd.getClass().getName());
        fragmentTransaction.commit();
    }

    public void updateMainViewUI(int fabVibility, boolean isShowBackButton, String toolbarTile, int drawerItemToCheck){
        fab.setVisibility(fabVibility);
        if(isShowBackButton)
            showBackButton();
        else{
            showBurgerMenuButton();
        }
        if(!toolbarTile.isEmpty())
            setToolbarTitle(toolbarTile);
        if(drawerItemToCheck != 0)
            navigationView.setCheckedItem(drawerItemToCheck);
    }
}
