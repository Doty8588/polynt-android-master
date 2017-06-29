package com.Polynt.ui.viewbinders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.Polynt.R;
import com.Polynt.model.PDFFile;
import com.Polynt.ui.viewholders.BaseViewHolder;
import com.Polynt.ui.viewholders.PDFFileItemHolder;

/**
 * Created by Alex on 6/6/2015.
 */
public class PDFFileItemBinder extends ViewBinder<PDFFile>{

    private Context mContext;

    public PDFFileItemBinder(Context ctx){
        super(R.layout.pdf_item);
        mContext = ctx;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new PDFFileItemHolder(view);
    }

    @Override
    public void bindView(PDFFile entity, int position, int grpPosition, View view, Activity activity) {
        PDFFileItemHolder holder;
        if (view.getTag() != null)
            holder = (PDFFileItemHolder)view.getTag();
        else
            holder = new PDFFileItemHolder(view);

        holder.tvPDFFileTitle.setText(entity.getName());

    }
}
