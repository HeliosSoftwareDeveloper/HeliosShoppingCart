package com.heliossoftwaredeveloper.heliosshoppingcart.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.Cart.CartManager;
import com.heliossoftwaredeveloper.heliosshoppingcart.Cart.View.CartFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.Cart.View.CheckOutFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.ProductDetailsFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.ProductListFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.ImageLazyLoaderManager;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,ProductListFragment.ProductListFragmentCallback,CartFragment.CartFragmentCallback,CheckOutFragment.CheckOutFragmentCallback {

    public Fragment productListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        productListFragment = new ProductListFragment();

        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyCartFragment();
            }
        });
        showProductListFragment();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() == 1){
                setToolbarTitle(getString(R.string.app_name));
                navigationView.setCheckedItem(R.id.nav_list);
                showBurgerMenuButton();
                fab.setVisibility(View.VISIBLE);
            }
            else{
                if(getToolbarTitle().equals("Product Details")){
                    if(checkFragmentFromBackStack(CartFragment.class.getName())){
                        fab.setVisibility(View.INVISIBLE);
                        showBackButton();
                        navigationView.setCheckedItem(R.id.nav_cart);
                        setToolbarTitle(navigationView.getMenu().getItem(2).getTitle().toString());
                    }

                }
                else if(getToolbarTitle().equals(navigationView.getMenu().getItem(2).getTitle().toString())){
                    if(checkFragmentFromBackStack(ProductDetailsFragment.class.getName())){
                        showBackButton();
                        setToolbarTitle("Product Details");
                        fab.setVisibility(View.VISIBLE);
                    }
                }
                else if(getToolbarTitle().equals(navigationView.getMenu().getItem(3).getTitle().toString())){
                    if(checkFragmentFromBackStack(CartFragment.class.getName())){
                        fab.setVisibility(View.INVISIBLE);
                        showBackButton();
                        navigationView.setCheckedItem(R.id.nav_cart);
                        setToolbarTitle(navigationView.getMenu().getItem(2).getTitle().toString());
                    }
                    else
                        getSupportFragmentManager().popBackStack(ProductListFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_list) {
            setToolbarTitle(getString(R.string.app_name));
        } else if (id == R.id.nav_history) {
            setToolbarTitle(item.getTitle().toString());
        } else if (id == R.id.nav_cart) {
            showMyCartFragment();
        } else if (id == R.id.nav_checkout) {
            showCheckOutFragment();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLazyLoaderManager.getInstance().cancelAllDownloadRequest();
        CartManager.getInstance().clearCart();
    }

    @Override
    public void onProductItemClicked(Product product) {
        showProductDetailsFragment(product);
    }

    @Override
    public void onProductAddToCartClicked(Product product, int sizeSelected) {
        boolean isNewlyAdded = CartManager.getInstance().addItem(product, sizeSelected);
        showMessage(product.getItemName()+ (isNewlyAdded ? " added.": " quantity added."));
    }

    @Override
    public void onAddProductQuantityClicked(Product product, int size) {
        CartManager.getInstance().addItem(product, size);
        showMessage(product.getItemName()+ " quantity added.");
    }

    @Override
    public void onRemoveFromCartClicked(Product product, int size) {
        CartManager.getInstance().removeItem(product, size);
        showMessage(product.getItemName()+ " product removed.");
    }

    @Override
    public void onReduceProductQuantityClicked(Product product, int size) {
        CartManager.getInstance().reduceQuantity(product, size);
        showMessage(product.getItemName()+ " quantity reduced.");
    }

    @Override
    public void onShowCheckOutScreenClicked() {
        showCheckOutFragment();
    }

    @Override
    public void onShowProductDetailsClicked(Product product) {
        onProductItemClicked(product);
    }

    @Override
    public void onProceedToCheckOutClicked() {
        onBackPressed();
    }

    public void showProductListFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, productListFragment,productListFragment.getClass().getName());
        fragmentTransaction.show(productListFragment);
        fragmentTransaction.commit();
    }

    public void showProductDetailsFragment(Product product){
        showBackButton();
        setToolbarTitle("Product Details");
        fab.setVisibility(View.VISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(checkFragmentFromBackStack(ProductDetailsFragment.class.getName())){
            fragmentManager.popBackStack(ProductDetailsFragment.class.getName(), 0);
        }
        else{
            Fragment productDetailsFragment = ProductDetailsFragment.newInstance(product);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, productDetailsFragment,productDetailsFragment.getClass().getName());
            fragmentTransaction.hide(productListFragment);
            fragmentTransaction.show(productDetailsFragment);
            fragmentTransaction.addToBackStack(productDetailsFragment.getClass().getName());
            fragmentTransaction.commit();
        }
    }

    public void showMyCartFragment(){
        fab.setVisibility(View.INVISIBLE);
        showBackButton();
        navigationView.setCheckedItem(R.id.nav_cart);
        setToolbarTitle(navigationView.getMenu().getItem(2).getTitle().toString());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(checkFragmentFromBackStack(CartFragment.class.getName())){
            fragmentManager.popBackStack(CartFragment.class.getName(), 0);
        }
        else{
            CartFragment cartFragment = CartFragment.newInstance(CartManager.getInstance().getCart());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, cartFragment,CartFragment.class.getName());
            fragmentTransaction.hide(productListFragment);
            fragmentTransaction.show(cartFragment);
            fragmentTransaction.addToBackStack(cartFragment.getClass().getName());
            fragmentTransaction.commit();
        }
    }

    public void showCheckOutFragment(){
        fab.setVisibility(View.INVISIBLE);
        showBackButton();
        navigationView.setCheckedItem(R.id.nav_checkout);
        setToolbarTitle(navigationView.getMenu().getItem(3).getTitle().toString());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(checkFragmentFromBackStack(CheckOutFragment.class.getName())){
            fragmentManager.popBackStack(CheckOutFragment.class.getName(), 0);
        }
        else{
            CheckOutFragment checkOutFragment = CheckOutFragment.newInstance(CartManager.getInstance().getCart());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, checkOutFragment,CheckOutFragment.class.getName());
            fragmentTransaction.hide(productListFragment);
            fragmentTransaction.show(checkOutFragment);
            fragmentTransaction.addToBackStack(checkOutFragment.getClass().getName());
            fragmentTransaction.commit();
        }
    }
}
