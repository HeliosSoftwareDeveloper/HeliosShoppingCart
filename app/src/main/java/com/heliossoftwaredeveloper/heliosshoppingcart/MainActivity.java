package com.heliossoftwaredeveloper.heliosshoppingcart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Interface.ProductViewListener;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartContainer;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.View.Fragment.CartFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.Fragment.CheckOutFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Model.FavoritesContainer;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.View.Fragment.FavoritesFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Fragment.ProductDetailsFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Fragment.ProductListFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.Fragment.TransactionHistoryDetailsFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.Fragment.TransactionHistoryListFragment;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.ImageLazyLoaderManager;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,ProductViewListener,
                            CartFragment.CartFragmentCallback,CheckOutFragment.CheckOutFragmentCallback, TransactionHistoryListFragment.TransactionHistoryListFragmentCallback {

    public Fragment productListFragment, favoritesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
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
                updateMainViewUI(View.VISIBLE, false, getString(R.string.app_name),R.id.nav_list);
            }
            else{
                if(getToolbarTitle().equals(getString(R.string.title_product_details))){
                    if(checkFragmentFromBackStack(CartFragment.class.getName())){
                        updateMainViewUI(View.INVISIBLE, true, getString(R.string.title_my_cart),R.id.nav_cart);
                    }
                    else if(checkFragmentFromBackStack(FavoritesFragment.class.getName())){
                        updateMainViewUI(View.INVISIBLE, true, getString(R.string.title_favorites),R.id.nav_favorites);
                        if(FavoritesContainer.getInstance().hasDataChanges())
                            ((FavoritesFragment)favoritesFragment).triggerUpdateList();
                    }
                }
                else if(getToolbarTitle().equals(getString(R.string.title_my_cart))){
                    if(checkFragmentFromBackStack(ProductDetailsFragment.class.getName())){
                        updateMainViewUI(View.VISIBLE, true, getString(R.string.title_product_details),0);
                    }
                }
                else if(getToolbarTitle().equals(getString(R.string.title_check_out))){
                    if(checkFragmentFromBackStack(CartFragment.class.getName())){
                        updateMainViewUI(View.INVISIBLE, true, getString(R.string.title_my_cart),R.id.nav_cart);
                    }
                }
                else if(getToolbarTitle().equals(getString(R.string.title_transaction_details))){
                    if(checkFragmentFromBackStack(TransactionHistoryDetailsFragment.class.getName())){
                        updateMainViewUI(View.VISIBLE, true, getString(R.string.title_transaction_history),0);
                    }
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
            showTransactionHistoryFragment();
        } else if (id == R.id.nav_cart) {
            showMyCartFragment();
        } else if (id == R.id.nav_favorites) {
            showFavoritesFragment();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLazyLoaderManager.getInstance().cancelAllDownloadRequest();
        CartContainer.getInstance().clearCart();
    }

    @Override
    public void onShowCheckOutScreenClicked() {
        showCheckOutFragment();
    }

    @Override
    public void onProceedToCheckOutClicked() {
        updateMainViewUI(View.VISIBLE, false, getString(R.string.app_name),R.id.nav_list);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onProductClickedListener(Product product) {
        showProductDetailsFragment(product);
    }

    @Override
    public void onTransactionHistoryListItemClicked(UserTransaction userTransaction) {
        showTransactionHistoryDetailsFragment(userTransaction);
    }

    public void showProductListFragment(){
        productListFragment = new ProductListFragment();
        addShowFragment(getSupportFragmentManager(), productListFragment, null, false);
    }

    public void showProductDetailsFragment(Product product){
        updateMainViewUI(View.VISIBLE, true, getString(R.string.title_product_details),0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(checkFragmentFromBackStack(ProductDetailsFragment.class.getName())){
            fragmentManager.popBackStack(ProductDetailsFragment.class.getName(), 0);
        }
        else{
            addShowFragment(fragmentManager,ProductDetailsFragment.newInstance(product, FavoritesContainer.getInstance().isProductFavorite(product)),productListFragment,true);
        }
    }

    public void showMyCartFragment(){
        updateMainViewUI(View.INVISIBLE, true, getString(R.string.title_my_cart),R.id.nav_cart);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(checkFragmentFromBackStack(CartFragment.class.getName())){
            fragmentManager.popBackStack(CartFragment.class.getName(), 0);
        }
        else{
            addShowFragment(fragmentManager,new CartFragment(),productListFragment,true);
        }
    }

    public void showCheckOutFragment(){
        if(CartContainer.getInstance().isCartEmpty())
            showMessage(getString(R.string.msg_cart_empty));
        else{
            updateMainViewUI(View.INVISIBLE, true, getString(R.string.title_check_out),0);
            FragmentManager fragmentManager = getSupportFragmentManager();
            if(checkFragmentFromBackStack(CheckOutFragment.class.getName())){
                fragmentManager.popBackStack(CheckOutFragment.class.getName(), 0);
            }
            else{
                addShowFragment(fragmentManager, new CheckOutFragment(), productListFragment,true);
            }
        }
    }

    public void showFavoritesFragment(){
        updateMainViewUI(View.INVISIBLE, true, getString(R.string.title_favorites),R.id.nav_favorites);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(checkFragmentFromBackStack(FavoritesFragment.class.getName())){
            fragmentManager.popBackStack(FavoritesFragment.class.getName(), 0);
        }
        else{
            favoritesFragment = new FavoritesFragment();
            addShowFragment(fragmentManager,favoritesFragment,productListFragment,true);
        }
    }

    public void showTransactionHistoryFragment(){
        updateMainViewUI(View.INVISIBLE, true, getString(R.string.title_transaction_history),R.id.nav_history);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(checkFragmentFromBackStack(TransactionHistoryListFragment.class.getName())){
            fragmentManager.popBackStack(TransactionHistoryListFragment.class.getName(), 0);
        }
        else{
            addShowFragment(fragmentManager,new TransactionHistoryListFragment(),productListFragment,true);
        }
    }

    public void showTransactionHistoryDetailsFragment(UserTransaction userTransaction){
        updateMainViewUI(View.INVISIBLE, true, getString(R.string.title_transaction_details),0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(checkFragmentFromBackStack(TransactionHistoryDetailsFragment.class.getName())){
            fragmentManager.popBackStack(TransactionHistoryDetailsFragment.class.getName(), 0);
        }
        else{
            addShowFragment(fragmentManager,TransactionHistoryDetailsFragment.newInstance(userTransaction),productListFragment,true);
        }
    }


}
