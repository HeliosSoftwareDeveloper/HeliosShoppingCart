package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.ProductImageFragment;

/**
 * Created by rngrajo on 02/02/2018.
 */

public class ProductPagerAdapter extends FragmentPagerAdapter {
    private Product product;

    public ProductPagerAdapter(FragmentManager fragmentManager, Product product) {
        super(fragmentManager);
        this.product = product;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return product.getImageArrayList().size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        return ProductImageFragment.newInstance(product, position);

    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}
