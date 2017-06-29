package com.Polynt.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import com.Polynt.R;

/**
 * Created by Alex on 6/6/2015.
 */
public class PDFFileItemWithCategoryHolder extends BaseViewHolder{

    public TextView tvPDFFileTitle;
    public TextView tvCategoryName;

    public PDFFileItemWithCategoryHolder(View rootView){
        tvPDFFileTitle = (TextView) rootView.findViewById(R.id.tvFileName);
        tvCategoryName = (TextView) rootView.findViewById(R.id.tvCategoryName);
    }

}
