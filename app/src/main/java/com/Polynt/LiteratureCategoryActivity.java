package com.Polynt;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.Polynt.config.Constants;
import com.Polynt.model.Category;
import com.Polynt.ui.adapters.ArrayListAdapter;
import com.Polynt.ui.viewbinders.CategoryItemBinder;
import com.longevitysoft.android.xml.plist.domain.Dict;
import com.longevitysoft.android.xml.plist.domain.PListObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class LiteratureCategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mLstCategory;
    ArrayListAdapter<Category> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Literature");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Dict dict;
        dict = Polynt.getSharedApplication().getLiteratureDict();

        mLstCategory = (ListView)findViewById(R.id.lstCategory);
        mAdapter = new ArrayListAdapter<Category>(this, new CategoryItemBinder(this));
        Map<String, PListObject> data = dict.getConfigMap();
        Set<String> keySet = data.keySet();
        int nID = 0;
        for (Iterator i = keySet.iterator(); i.hasNext();) {
            String key = (String) i.next();
            Category category = new Category();
            category.setCategoryID(nID);
            category.setCategorytitle(key);
            mAdapter.add(category);
            nID ++;
        }
        mLstCategory.setAdapter(mAdapter);
        mLstCategory.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PDFFileActivity.class);
        Category category = (Category)mAdapter.getItem(position);
        TextView tv = (TextView)view.findViewById(R.id.tvCategoryName);
        String categoryName = tv.getText().toString();
        intent.putExtra("category", category.getCategorytitle());
        intent.putExtra("type", 1);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
