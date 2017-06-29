package com.Polynt.ui.viewbinders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.Polynt.R;
import com.Polynt.model.PDFFile;
import com.Polynt.ui.viewholders.BaseViewHolder;
import com.Polynt.ui.viewholders.PDFFileItemHolder;
import com.Polynt.ui.viewholders.PDFFileItemWithCategoryHolder;

/**
 * Created by Alex on 6/6/2015.
 */
public class PDFFileItemWithCategoryBinder extends ViewBinder<PDFFile>{

    private Context mContext;

    public PDFFileItemWithCategoryBinder(Context ctx){
        super(R.layout.pdf_item_with_category);
        mContext = ctx;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new PDFFileItemWithCategoryHolder(view);
    }

    @Override
    public void bindView(PDFFile entity, int position, int grpPosition, View view, Activity activity) {
        PDFFileItemWithCategoryHolder holder;
        if (view.getTag() != null)
            holder = (PDFFileItemWithCategoryHolder)view.getTag();
        else
            holder = new PDFFileItemWithCategoryHolder(view);

        holder.tvPDFFileTitle.setText(entity.getName());
        holder.tvCategoryName.setText(entity.getCategory());
    }
}
