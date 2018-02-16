package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heliossoftwaredeveloper.heliosshoppingcart.CustomViews.ImageCarouseDataHolder;
import com.heliossoftwaredeveloper.heliosshoppingcart.CustomViews.ImageCarousel;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Interface.ProductListPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Impl.ProductListPresenterImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Adapter.ProductListAdapter;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Interface.ProductListView;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Interface.ProductViewListener;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.Constant;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.WrapContentLinearLayoutManager;
import java.util.ArrayList;


/**
 * Fragment class for Movie List
 *
 * Created by rngrajo on 30/01/2018.
 */

public class ProductListFragment extends Fragment implements ProductListView,ProductListAdapter.ProductListAdapterCallback,SwipeRefreshLayout.OnRefreshListener {

    private ProductListPresenter presenter;
    private RecyclerView recyclerViewProducts;
    private SwipeRefreshLayout swipeContainer;
    private ProductViewListener callback;
    private ImageCarousel imageCarousel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        presenter = new ProductListPresenterImpl(this);

        recyclerViewProducts = (RecyclerView)view.findViewById(R.id.recyclerViewProducts);
        swipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeContainer);
        imageCarousel = (ImageCarousel)view.findViewById(R.id.imageCarousel);

        swipeContainer.setOnRefreshListener(this);
        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
        recyclerViewProducts.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    public void onRefresh() {
        presenter.getProductList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProductViewListener) {
            callback = (ProductViewListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onItemClicked(Product product, int position) {
        callback.onProductClickedListener(product);
    }

    @Override
    public void onItemAddToCart(Product product, int sizeSelected) {
        presenter.addProductToCart(product, sizeSelected, getString(R.string.msg_product_added), getString(R.string.msg_product_quantity_added));
    }

    @Override
    public void updateProductViewList(ArrayList<Product> productArrayList) {
        recyclerViewProducts.setAdapter(new ProductListAdapter(productArrayList,getActivity(),this));

        ArrayList<ImageCarouseDataHolder> imageCarouseDataHolders = new ArrayList<>();


        for(Product product : productArrayList){
            ImageCarouseDataHolder imageCarouseDataHolder = new ImageCarouseDataHolder();
            imageCarouseDataHolder.setDescription("asdasd");
            imageCarouseDataHolder.setImageUrl( Constant.URL_IMAGE_LINK.replace("[SLUG]",product.getImageArrayList().get(0)));
            imageCarouseDataHolder.setLabel(product.getItemName());
            imageCarouseDataHolders.add(imageCarouseDataHolder);
        }
        imageCarousel.setData(imageCarouseDataHolders);
    }

    @Override
    public void showLoading() {
        swipeContainer.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void showUserPrompt(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void showSnackBarMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }
}
