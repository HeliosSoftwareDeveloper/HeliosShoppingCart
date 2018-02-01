package com.heliossoftwaredeveloper.heliosshoppingcart;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.ProductListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ProductListFragment.ProductListFragmentCallback {

    CoordinatorLayout mainContent;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fab;

    public Fragment productListFragment;
    public FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mainContent = (CoordinatorLayout)findViewById(R.id.mainContent);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        productListFragment = new ProductListFragment();
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, productListFragment,productListFragment.getClass().getName());
        fragmentTransaction.show(productListFragment);
        fragmentTransaction.commit();

        ((ProductListFragment) productListFragment).setCallback(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationView.setCheckedItem(R.id.nav_cart);
                toolbar.setTitle(navigationView.getMenu().getItem(2).getTitle());
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
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
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        toolbar.setTitle(item.getTitle());
        if (id == R.id.nav_list) {
            // Handle the camera action
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_checkout) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showMessage(){
         Snackbar.make(mainContent, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
    }

    @Override
    public void onProductItemClicked(Product product) {

    }

    @Override
    public void onProductAddToCartClicked(Product product, int sizeSelected) {

    }
}
