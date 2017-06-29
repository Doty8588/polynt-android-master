package com.Polynt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.Polynt.config.Constants;
import com.Polynt.model.Category;
import com.Polynt.ui.adapters.ArrayListAdapter;
import com.Polynt.ui.viewbinders.CategoryItemBinder;
import com.longevitysoft.android.xml.plist.domain.Dict;
import com.longevitysoft.android.xml.plist.domain.PListObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ProductsCategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mLstCategory;
    ArrayListAdapter<Category> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Products");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Dict dict;
        dict = Polynt.getSharedApplication().getProductDict();

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
        Intent intent = new Intent(this, HomeActivity.class);
        TextView tv = (TextView)view.findViewById(R.id.tvCategoryName);
        String categoryName = tv.getText().toString();
        intent.putExtra("category", categoryName);
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
