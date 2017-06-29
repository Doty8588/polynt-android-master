package com.Polynt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.Polynt.db.DatabaseMaster;
import com.Polynt.model.Category;
import com.Polynt.model.PDFFile;
import com.Polynt.ui.adapters.ArrayListAdapter;
import com.Polynt.ui.viewbinders.PDFFileItemBinder;
import com.longevitysoft.android.xml.plist.domain.Array;
import com.longevitysoft.android.xml.plist.domain.Dict;
import com.longevitysoft.android.xml.plist.domain.PListObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SearchPDFActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    SearchView svSearch;
    ListView lstHistory;
    ArrayListAdapter<PDFFile> mAdapter;
    SearchPDFActivity activity;
    ArrayList arrayHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pdf);
        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        lstHistory = (ListView)findViewById(R.id.lstHistory);
        svSearch = (SearchView)findViewById(R.id.tv_search);
        svSearch.setQueryHint("Search");
        mAdapter = new ArrayListAdapter<PDFFile>(this, new PDFFileItemBinder(this));

        svSearch.setOnCloseListener(new SearchView.OnCloseListener(){

            @Override
            public boolean onClose() {

                arrayHistory = DatabaseMaster.getInstance(activity).GetHistorys();
                mAdapter.clearList();
                for (int i = 0; i < arrayHistory.size(); i++) {
                    PDFFile pdfFile = new PDFFile();
                    String strName = (String)arrayHistory.get(i);
                    String strFileName = Polynt.getSharedApplication().mapHistory.get(strName);
                    pdfFile.setName(strName);
                    pdfFile.setCategory("search");
                    pdfFile.setPdf(strFileName);
                    mAdapter.add(pdfFile);
                }
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                String newSearchText = searchText.toLowerCase();
                Log.e("DEBUG", newSearchText);
                mAdapter.clearList();
                if (newSearchText.length() > 0) {
                    Dict productDict = Polynt.getSharedApplication().getProductDict();
                    Dict literatureDict = Polynt.getSharedApplication().getLiteratureDict();
                    Map<String, PListObject> data = productDict.getConfigMap();
                    Set<String> keySet = data.keySet();
                    for (Iterator i = keySet.iterator(); i.hasNext(); ) {
                        String key = (String) i.next();
                        Array pdfArray = (Array) data.get(key);
                        for (int j = 0; j < pdfArray.size(); j++) {

                            Dict itemDict = (Dict) pdfArray.get(j);
                            if ((itemDict.getConfiguration("category") != null && itemDict.getConfiguration("category").toString().toLowerCase().contains(newSearchText)) ||
                                    (itemDict.getConfiguration("description") != null && itemDict.getConfiguration("description").toString().toLowerCase().contains(newSearchText)) ||
                                    (itemDict.getConfiguration("name") != null && itemDict.getConfiguration("name").toString().toLowerCase().contains(newSearchText)) ||
                                    (itemDict.getConfiguration("series") != null && itemDict.getConfiguration("series").toString().toLowerCase().contains(newSearchText)) ||
                                    (itemDict.getConfiguration("trade_name") != null && itemDict.getConfiguration("trade_name").toString().toLowerCase().contains(newSearchText))
                                    ) {
                                PDFFile pdfFile = new PDFFile();
                                pdfFile.setName(itemDict.getConfiguration("name").toString());
                                pdfFile.setCategory("Search");
                                pdfFile.setPdf(itemDict.getConfiguration("pdf").toString());
                                mAdapter.add(pdfFile);
                            }
                        }
                    }

                    data = literatureDict.getConfigMap();
                    keySet = data.keySet();
                    for (Iterator i = keySet.iterator(); i.hasNext(); ) {
                        String key = (String) i.next();
                        Array pdfArray = (Array) data.get(key);
                        for (int j = 0; j < pdfArray.size(); j++) {

                            Dict itemDict = (Dict) pdfArray.get(j);
                            if ((itemDict.getConfiguration("category") != null && itemDict.getConfiguration("category").toString().toLowerCase().contains(newSearchText)) ||
                                    (itemDict.getConfiguration("description") != null && itemDict.getConfiguration("description").toString().toLowerCase().contains(newSearchText)) ||
                                    (itemDict.getConfiguration("name") != null && itemDict.getConfiguration("name").toString().toLowerCase().contains(newSearchText)) ||
                                    (itemDict.getConfiguration("series") != null && itemDict.getConfiguration("series").toString().toLowerCase().contains(newSearchText)) ||
                                    (itemDict.getConfiguration("trade_name") != null && itemDict.getConfiguration("trade_name").toString().toLowerCase().contains(newSearchText))
                                    ) {
                                PDFFile pdfFile = new PDFFile();
                                pdfFile.setName(itemDict.getConfiguration("name").toString());
                                pdfFile.setCategory("Search");
                                pdfFile.setPdf(itemDict.getConfiguration("pdf").toString());
                                mAdapter.add(pdfFile);
                            }
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        svSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.tv_search && !hasFocus){

                }
            }
        });


        arrayHistory = DatabaseMaster.getInstance(this).GetHistorys();
        for (int i = 0; i < arrayHistory.size(); i++) {
            PDFFile pdfFile = new PDFFile();
            String strName = (String)arrayHistory.get(i);
            String strFileName = Polynt.getSharedApplication().mapHistory.get(strName);
            pdfFile.setName(strName);
            pdfFile.setCategory("search");
            pdfFile.setPdf(strFileName);
            mAdapter.add(pdfFile);
        }
        lstHistory.setAdapter(mAdapter);
        lstHistory.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
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
        if (id == R.id.action_delete){
            DatabaseMaster.getInstance(this).RemoveAllHistory();
            mAdapter.clearList();
            mAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PDFFile pdfFile = (PDFFile)mAdapter.getItem(position);
        if(!arrayHistory.contains(pdfFile.getName()))
            DatabaseMaster.getInstance(this).InsertHistory(pdfFile.getName(), pdfFile.getPdf());
        Intent intent = new Intent(this, PDFViewActivity.class);
        intent.putExtra("pdf", pdfFile.getPdf());
        startActivity(intent);
    }
}
