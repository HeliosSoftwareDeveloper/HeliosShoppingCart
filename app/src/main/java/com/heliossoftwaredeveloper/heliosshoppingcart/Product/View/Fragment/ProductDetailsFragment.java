package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Impl.ProductDetailsPresenterImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Interface.ProductDetailsPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Interface.ProductDetailsView;
import com.heliossoftwaredeveloper.heliosshoppingcart.CustomViews.HorizontalDataSetPicker;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Adapter.ProductPagerAdapter;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment implements ProductDetailsView{
    public static final String KEY_PRODUCT = "product";
    public static final String KEY_IS_FAVORITE = "isFavorite";

    private Product product;
    private ProductDetailsPresenter presenter;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param product Parameter 1.
     * @return A new instance of fragment ProductDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailsFragment newInstance(Product product, boolean isFavorite) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_PRODUCT, product);
        args.putBoolean(KEY_IS_FAVORITE, isFavorite);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable(KEY_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        presenter = new ProductDetailsPresenterImpl(this);
        ViewPager productPager =(ViewPager)view.findViewById(R.id.productPager);
        TextView txtProductName =(TextView)view.findViewById(R.id.txtProductName);
        TextView txtProductDescription =(TextView)view.findViewById(R.id.txtProductDescription);
        TextView txtProductCode =(TextView)view.findViewById(R.id.txtProductCode);
        TextView txtReleaseDate =(TextView)view.findViewById(R.id.txtReleaseDate);
        TextView txtPrice =(TextView)view.findViewById(R.id.txtPrice);
        final ImageButton imgbtnFavorite = (ImageButton)view.findViewById(R.id.imgbtnFavorite);
        final HorizontalDataSetPicker horizontalPickerSize=(HorizontalDataSetPicker)view.findViewById(R.id.horizontalPickerSize);
        Button btnAddToCart =(Button)view.findViewById(R.id.btnAddToCart);

        FragmentPagerAdapter adapterViewPager = new ProductPagerAdapter(getChildFragmentManager(), product);
        productPager.setAdapter(adapterViewPager);

        txtProductName.setText(product.getItemName());
        txtProductCode.setText(product.getItemcode());
        txtProductDescription.setText(product.getItemDescription());

        txtReleaseDate.setText(product.getReleaseDate());

        horizontalPickerSize.setDataSetInt(product.getSizes());

        txtPrice.setText("$"+Integer.toString(product.getItemPrice()));

        btnAddToCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.addProductToCart(product,horizontalPickerSize.getValueInt(),  getString(R.string.msg_product_added),getString(R.string.msg_product_quantity_added));
            }
        });

        imgbtnFavorite.setSelected(presenter.isProductFavorite(product));

        imgbtnFavorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                imgbtnFavorite.setSelected(!imgbtnFavorite.isSelected());
                presenter.addRemoveProductTofavorite(product, getString(R.string.msg_product_favorite), getString(R.string.msg_product_unfavorite));
            }
        });

        return view;
    }

    @Override
    public void showSnackBarMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }
}
