package com.Polynt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.Polynt.config.Constants;
import com.Polynt.model.Category;
import com.Polynt.model.PDFFile;
import com.Polynt.ui.adapters.ArrayListAdapter;
import com.Polynt.ui.viewbinders.PDFFileItemBinder;
import com.Polynt.ui.viewbinders.PDFFileItemWithCategoryBinder;
import com.longevitysoft.android.xml.plist.domain.Array;
import com.longevitysoft.android.xml.plist.domain.Dict;
import com.longevitysoft.android.xml.plist.domain.PListObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PDFFileWithCategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mLstPDFFile;
    ArrayListAdapter<PDFFile> mAdapter;
    int nType;
    String strFilter;
    String categoryName;
    HashMap<String,String> pdfFileMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdffile_with_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        mLstPDFFile = (ListView)findViewById(R.id.lstFileItems);

        pdfFileMap = new HashMap<String, String>();
        Intent intent = getIntent();
        nType = intent.getIntExtra("type", 1);
        strFilter = intent.getStringExtra("filter");
        categoryName = intent.getStringExtra("category");

        String strFilterKey = "";
        switch (nType) {
            case 1:
                strFilterKey = "category";
                break;
            case 2:
                strFilterKey = "series";
                break;
            case 3:
                strFilterKey = "trade_name";
                break;
        }

        Dict dict = Polynt.getSharedApplication().getProductDict();
        Map<String, PListObject> data = dict.getConfigMap();
        Array array = (Array)data.get(categoryName);
        ArrayList arrFilteredName = new ArrayList();

        int nID = 0;
        for (int i = 0; i < array.size(); i ++) {
            Dict itemDict = (Dict)array.get(i);
            String strFilterField = itemDict.getConfiguration(strFilterKey).toString();
            String strPDFName = itemDict.getConfiguration("name").toString();
            String strPDFFileName = itemDict.getConfiguration("pdf").toString();
            if (strFilterField.equals(strFilter)){
                arrFilteredName.add(strPDFName);
                pdfFileMap.put(strPDFName, strPDFFileName);
            }
        }

        Collections.sort(arrFilteredName);
        mAdapter = new ArrayListAdapter<PDFFile>(this, new PDFFileItemWithCategoryBinder(this));
        for (int i = 0; i < arrFilteredName.size(); i ++){
            PDFFile pdfFile = new PDFFile();

            String strName = (String)arrFilteredName.get(i);
            pdfFile.setName(strName);
            pdfFile.setCategory(categoryName);
            mAdapter.add(pdfFile);
        }

        mLstPDFFile.setAdapter(mAdapter);
        mLstPDFFile.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tvName = (TextView)view.findViewById(R.id.tvFileName);
        String strName = tvName.getText().toString();
        String strPDFFileName = pdfFileMap.get(strName);
        if (strPDFFileName != null && strPDFFileName.length() > 0) {
            Intent intent = new Intent(this, PDFViewActivity.class);
            intent.putExtra("pdf", strPDFFileName);
            startActivity(intent);
        }
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
