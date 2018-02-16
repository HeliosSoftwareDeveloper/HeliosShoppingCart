package com.heliossoftwaredeveloper.heliosshoppingcart.Commons;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.CustomViews.ImageCarousel;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;

/**
 * RecyclerView.ViewHolder Class for single label view
 *
 * Created by rngrajo on 16/02/2018.
 */

public class CarouselViewHolder extends RecyclerView.ViewHolder {
    public ImageCarousel imageCarousel;

    public CarouselViewHolder(View view) {
        super(view);
        imageCarousel = (ImageCarousel)view.findViewById(R.id.imageCarousel);
    }
}