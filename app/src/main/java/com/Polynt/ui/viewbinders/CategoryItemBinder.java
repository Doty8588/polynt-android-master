package com.Polynt.ui.viewbinders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.Polynt.R;
import com.Polynt.model.Category;
import com.Polynt.ui.viewholders.BaseViewHolder;
import com.Polynt.ui.viewholders.CategoryItemHolder;

/**
 * Created by Alex on 6/6/2015.
 */
public class CategoryItemBinder extends ViewBinder<Category>{

    private Context mContext;

    public CategoryItemBinder(Context ctx){
        super(R.layout.category_item);
        mContext = ctx;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new CategoryItemHolder(view);
    }

    @Override
    public void bindView(Category entity, int position, int grpPosition, View view, Activity activity) {
        CategoryItemHolder holder;
        if (view.getTag() != null)
            holder = (CategoryItemHolder)view.getTag();
        else
            holder = new CategoryItemHolder(view);

        holder.tvCategoryTitle.setText(entity.getCategorytitle());

    }
}
