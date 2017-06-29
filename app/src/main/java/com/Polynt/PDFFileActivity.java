package com.Polynt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.Polynt.config.Constants;
import com.Polynt.model.Category;
import com.Polynt.model.PDFFile;
import com.Polynt.ui.adapters.ArrayListAdapter;
import com.Polynt.ui.viewbinders.CategoryItemBinder;
import com.Polynt.ui.viewbinders.PDFFileItemBinder;
import com.longevitysoft.android.xml.plist.domain.*;

import java.lang.String;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PDFFileActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mLstPDFFile;
    ArrayListAdapter<PDFFile> mAdapter;
    Array mPDFFileNameArray;
    int nType;
    String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdffile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        nType = intent.getIntExtra("type", 2);
        mLstPDFFile = (ListView) findViewById(R.id.lstFileItems);
        if (nType == 1) {
            categoryName = intent.getStringExtra("category");
            setTitle(categoryName);
            Dict dict = Polynt.getSharedApplication().getLiteratureDict();


            mAdapter = new ArrayListAdapter<PDFFile>(this, new PDFFileItemBinder(this));
            Map<String, PListObject> data = dict.getConfigMap();
            mPDFFileNameArray = (Array) data.get(categoryName);


            for (int i = 0; i < mPDFFileNameArray.size(); i++) {
                PDFFile pdfFile = new PDFFile();
                Dict itemDict = (Dict) mPDFFileNameArray.get(i);
                String strName = itemDict.getConfiguration("name").toString();
                String strFileName = itemDict.getConfiguration("pdf").toString();
                pdfFile.setName(strName);
                pdfFile.setCategory(categoryName);
                pdfFile.setPdf(strFileName);
                mAdapter.add(pdfFile);
            }
            mLstPDFFile.setAdapter(mAdapter);
            mLstPDFFile.setOnItemClickListener(this);
        }else {
            mAdapter = new ArrayListAdapter<PDFFile>(this, new PDFFileItemBinder(this));
            setTitle("Application Guide");
            PDFFile pdfFile = new PDFFile();
            pdfFile.setName("English");
            pdfFile.setCategory(categoryName);
            mAdapter.add(pdfFile);
            mLstPDFFile.setAdapter(mAdapter);
            mLstPDFFile.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (nType == 1) {
            PDFFile pdfFile = (PDFFile)mAdapter.getItem(position);
            if (pdfFile.getPdf() != null && pdfFile.getPdf().length() > 0) {
                Intent intent = new Intent(this, PDFViewActivity.class);
                intent.putExtra("pdf", pdfFile.getPdf());
                startActivity(intent);
            }
        }else {
            Intent intent = new Intent(this, PDFViewActivity.class);
            intent.putExtra("pdf", "Polynt Composites Applications Guide.pdf");
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
