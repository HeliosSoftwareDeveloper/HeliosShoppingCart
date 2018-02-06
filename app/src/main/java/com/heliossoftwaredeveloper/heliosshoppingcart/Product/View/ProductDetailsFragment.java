package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.HorizontalDataSetPicker;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Adapter.ProductPagerAdapter;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailsFragment.ProductDetailsFragmentCallback} interface
 * to handle interaction events.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String KEY_PRODUCT = "product";
    public static final String KEY_IS_FAVORITE = "isFavorite";

    // TODO: Rename and change types of parameters
    private Product product;
    private boolean isFavorite;

    private ProductDetailsFragmentCallback callback;

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
            isFavorite = getArguments().getBoolean(KEY_IS_FAVORITE);
            Log.e("productDetails",product.getItemcode());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
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
                callback.onProductDetailsAddToCartClickListener(product,horizontalPickerSize.getValueInt() );
            }
        });

        imgbtnFavorite.setSelected(isFavorite);

        imgbtnFavorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                imgbtnFavorite.setSelected(!imgbtnFavorite.isSelected());
                callback.onProductDetailsFavoriteClickListener(product);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProductDetailsFragment.ProductDetailsFragmentCallback) {
            callback = (ProductDetailsFragment.ProductDetailsFragmentCallback) context;
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface ProductDetailsFragmentCallback {
        // TODO: Update argument type and name
        void onProductDetailsAddToCartClickListener(Product product, int size);
        void onProductDetailsFavoriteClickListener(Product product);
    }
}
