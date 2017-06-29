package com.Polynt.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Polynt.R;

/**
 * Created by Alex on 6/6/2015.
 */
public class CategoryItemHolder extends BaseViewHolder{

    public TextView tvCategoryTitle;

    public CategoryItemHolder(View rootView){
        tvCategoryTitle = (TextView) rootView.findViewById(R.id.tvCategoryName);
    }

}
