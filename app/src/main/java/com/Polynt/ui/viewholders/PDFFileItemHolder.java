package com.Polynt.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import com.Polynt.R;

/**
 * Created by Alex on 6/6/2015.
 */
public class PDFFileItemHolder extends BaseViewHolder{

    public TextView tvPDFFileTitle;
    public PDFFileItemHolder(View rootView){
        tvPDFFileTitle = (TextView) rootView.findViewById(R.id.tvFileName);
    }

}
