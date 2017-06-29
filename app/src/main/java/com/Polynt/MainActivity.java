package com.Polynt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            ProcessData();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
        }
    }

    public void ProcessData() {
        Polynt.getSharedApplication().strTempPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "temp"
                + File.separator;
        Polynt.getSharedApplication().strDownloadPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "downloadfolder"
                + File.separator;

        File folder = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "temp");
        if (!folder.exists())
            folder.mkdir();
        folder = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "downloadfolder");
        if (!folder.exists())
            folder.mkdir();

        Polynt.getSharedApplication().downloadList = new ArrayList();
        Polynt.getSharedApplication().downloadThread = null;
        Polynt.getSharedApplication().isUpdated = false;
        Polynt.getSharedApplication().CheckDownload(this);

//        if (Polynt.getSharedApplication().isUpdated){
//            Polynt.getSharedApplication().UpdatePlist();
//        }
//
//        Polynt.getSharedApplication().CheckDownload();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    ProcessData();
                } else {
                    Toast.makeText(this, "App needs write permission to external storage, please try again", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    public void onProducts(View v){
        Intent intent = new Intent(this, ProductsCategoryActivity.class);
        startActivity(intent);
    }

    public void onApplicationGuide(View v){
        Intent intent = new Intent(this, PDFFileActivity.class);
        intent.putExtra("type", 2);
        startActivity(intent);
    }

    public void onLiterature(View v){
        Intent intent = new Intent(this, LiteratureCategoryActivity.class);
        startActivity(intent);
    }

    public void onSearch(View v){
        Intent intent = new Intent(this, SearchPDFActivity.class);
        startActivity(intent);
    }

}
