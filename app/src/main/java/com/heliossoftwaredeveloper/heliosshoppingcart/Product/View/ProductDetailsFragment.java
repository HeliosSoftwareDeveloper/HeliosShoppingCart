package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View;

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
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Adapter.ProductPagerAdapter;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String KEY_PRODUCT = "product";

    // TODO: Rename and change types of parameters
    private Product product;

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
    public static ProductDetailsFragment newInstance(Product product) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable(KEY_PRODUCT);
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
        final TextView txtSize =(TextView)view.findViewById(R.id.txtSize);
        ImageButton imgBtnBefore =(ImageButton)view.findViewById(R.id.imgBtnBefore);
        ImageButton imgBtnNext =(ImageButton)view.findViewById(R.id.imgBtnNext);
        Button btnAddToCart =(Button)view.findViewById(R.id.btnAddToCart);

        FragmentPagerAdapter adapterViewPager = new ProductPagerAdapter(getChildFragmentManager(), product);
        productPager.setAdapter(adapterViewPager);

        txtProductName.setText(product.getItemName());
        txtProductCode.setText(product.getItemcode());
        txtProductDescription.setText(product.getItemDescription());

        txtReleaseDate.setText(product.getReleaseDate());


        txtSize.setText(Integer.toString(product.getSizes().get(0)));
        txtPrice.setText("$"+Integer.toString(product.getItemPrice()));

        imgBtnBefore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int index = product.getSizes().indexOf(Integer.valueOf(txtSize.getText().toString()));
                if(index > 0)
                    txtSize.setText(Integer.toString(product.getSizes().get(index-1)));
            }
        });

        imgBtnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int index = product.getSizes().indexOf(Integer.valueOf(txtSize.getText().toString()));
                if(index >= 0 && index < (product.getSizes().size() - 1))
                    txtSize.setText(Integer.toString(product.getSizes().get(index+1)));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
