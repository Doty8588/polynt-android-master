package com.Polynt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.Polynt.HomeActivity;
import com.Polynt.PDFFileWithCategoryActivity;
import com.Polynt.Polynt;
import com.Polynt.model.Category;
import com.Polynt.R;
import com.Polynt.ui.adapters.ArrayListAdapter;
import com.Polynt.ui.viewbinders.CategoryItemBinder;
import com.longevitysoft.android.xml.plist.domain.Array;
import com.longevitysoft.android.xml.plist.domain.Dict;
import com.longevitysoft.android.xml.plist.domain.PListObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Alex on 6/3/2015.
 */
public class SeriesFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    ListView lvCategory;
    ArrayListAdapter<Category> mAdapter;
    HomeActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_series, container, false);
        lvCategory = (ListView) rootView.findViewById(R.id.lvCategory);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (HomeActivity)getActivity();
        setTitle(activity.strCategory);

        mAdapter = new ArrayListAdapter<Category>(getActivity(), new CategoryItemBinder(getActivity()));
        Dict dict;
        dict = Polynt.getSharedApplication().getProductDict();

        Map<String, PListObject> data = dict.getConfigMap();
        Array array = (Array)data.get(activity.strCategory);
        ArrayList arrFilteredCategory = new ArrayList();
        int nID = 0;
        for (int i = 0; i < array.size(); i ++) {
            Dict itemDict = (Dict)array.get(i);
            String strName = itemDict.getConfiguration("series").toString();
            if (!arrFilteredCategory.contains(strName))
                arrFilteredCategory.add(strName);
        }

        Collections.sort(arrFilteredCategory);
        for (int i = 0; i < arrFilteredCategory.size(); i ++){
            Category category = new Category();
            category.setCategoryID(i);
            category.setCategorytitle((String) arrFilteredCategory.get(i));
            mAdapter.add(category);
        }
        //mAdapter.addAll(arrFilteredCategory);
        lvCategory.setAdapter(mAdapter);
        lvCategory.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tvTitle = (TextView)view.findViewById(R.id.tvCategoryName);
        String strTitle = tvTitle.getText().toString();
        Intent intent = new Intent(getActivity(), PDFFileWithCategoryActivity.class);
        intent.putExtra("category", activity.strCategory);
        intent.putExtra("type", 2);
        intent.putExtra("filter", strTitle);
        getActivity().startActivity(intent);
    }
}
