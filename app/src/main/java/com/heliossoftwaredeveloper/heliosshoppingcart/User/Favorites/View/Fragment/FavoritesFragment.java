package com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Interface.ProductViewListener;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Presenter.Impl.FavoritesPresenterImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Presenter.Interface.FavoritesPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.View.Adapter.FavoritesAdapter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.View.FavoritesView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class FavoritesFragment extends Fragment implements FavoritesView, FavoritesAdapter.ProductListAdapterCallback {

    // TODO: Rename and change types of parameters
    private ArrayList<Product> productArrayList = new ArrayList<>();
    private RecyclerView recyclerViewProducts;

    private ProductViewListener callback;
    FavoritesPresenter presenter;
    private RelativeLayout layoutEmptyFavorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorites, container, false);
        presenter = new FavoritesPresenterImpl(this);
        recyclerViewProducts = (RecyclerView)view.findViewById(R.id.recyclerViewProducts);
        layoutEmptyFavorites = (RelativeLayout)view.findViewById(R.id.layoutEmptyFavorites);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        recyclerViewProducts.setLayoutManager(manager);

        recyclerViewProducts.setAdapter(new FavoritesAdapter(productArrayList, getActivity(), this));
        presenter.getFavoritesList();
        return view;
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

    public void triggerUpdateList(){
        presenter.getFavoritesList();
    }

    @Override
    public void updateProductViewList(ArrayList<Product> productArrayList) {
        this.productArrayList.clear();
        this.productArrayList.addAll(productArrayList);
        recyclerViewProducts.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showEmptyFavoritesView() {
        layoutEmptyFavorites.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyFavoritesView() {
        layoutEmptyFavorites.setVisibility(View.GONE);
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
}
