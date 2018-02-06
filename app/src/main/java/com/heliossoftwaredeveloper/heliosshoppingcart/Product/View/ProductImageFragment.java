package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.Constant;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.ImageLazyLoaderManager;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.Model.ImageRequestData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ProductImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductImageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Product mParam1;
    private int mParam2;

    public ProductImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProductImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductImageFragment newInstance(Product param1, int mParam2) {
        ProductImageFragment fragment = new ProductImageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, mParam2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Product) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);

            Log.e("mParam1",mParam1.getItemcode());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_image, container, false);
        ImageView imgPhoto = (ImageView)view.findViewById(R.id.imgPhoto);

        ImageLazyLoaderManager.getInstance().setCallback(new ImageLazyLoaderManager.ImageLazyLoaderManagerCallback() {
            @Override
            public void onImageDownloaded(ImageRequestData imageRequestData) {
                imageRequestData.getImageView().setImageDrawable(imageRequestData.getDownloadedDrawable());
            }

            @Override
            public void onImageDownloadError(String error) {

            }
        });
        ImageLazyLoaderManager.getInstance().loadImage(new ImageRequestData(imgPhoto, Constant.URL_IMAGE_LINK.replace("[SLUG]",mParam1.getImageArrayList().get(mParam2)), ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher)));


        return view;
    }
}
