package com.heliossoftwaredeveloper.heliosshoppingcart.Commons;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.R;

/**
 * RecyclerView.ViewHolder Class for single label view
 *
 * Created by rngrajo on 16/02/2018.
 */

public class SingleTextViewViewHolder extends RecyclerView.ViewHolder {
    public TextView txtLabel;

    public SingleTextViewViewHolder(View view) {
        super(view);
        txtLabel = (TextView)view.findViewById(R.id.txtLabel);
    }
}