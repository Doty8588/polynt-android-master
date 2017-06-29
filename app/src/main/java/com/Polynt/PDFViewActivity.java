package com.Polynt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.Polynt.utils.FileUtils;
import com.Polynt.utils.LogUtil;
import com.artifex.mupdfdemo.FilePicker;
import com.artifex.mupdfdemo.MuPDFCore;
import com.artifex.mupdfdemo.MuPDFPageAdapter;
import com.artifex.mupdfdemo.MuPDFReaderView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PDFViewActivity extends AppCompatActivity {
    /*
     *ButterKnife Controls
     */
    @Bind(R.id.content_view)
    RelativeLayout mContentView;

    String strPDFFileName;

    private MuPDFCore core;
    private MuPDFReaderView mDocView;
    private String mFilePath;

    private MuPDFCore openFile(String path) {
        int lastSlashPos = path.lastIndexOf('/');
        mFilePath = new String(lastSlashPos == -1
                ? path
                : path.substring(lastSlashPos + 1));
        try {
            core = new MuPDFCore(this, path);
            // New file: drop the old outline data
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            return null;
        }
        return core;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        Intent intent = getIntent();
        strPDFFileName = intent.getStringExtra("pdf");
        String structureFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "downloadfolder"
                + File.separator + strPDFFileName;
        if (!FileUtils.isFileExist(structureFilePath)) {
            structureFilePath = getFilesDir().getPath() + File.separator + strPDFFileName;

            if (!FileUtils.isFileExist(structureFilePath) &&
                    !FileUtils.copyFileFromObbToInternalStorage(this, strPDFFileName, strPDFFileName)) {
                LogUtil.e("Failed to copy AppStructure file to internal storage");
                return;
            }
        }

        final File tempFile = new File(structureFilePath);
        if (FileUtils.isFileExist(structureFilePath)) {
            ButterKnife.bind(this);

            Uri uri = Uri.parse(tempFile.getPath());
            core = openFile(Uri.decode(uri.getEncodedPath()));
            if (core != null && core.countPages() == 0) {
                core = null;
            }

            if (core == null || core.countPages() == 0 || core.countPages() == -1) {
                LogUtil.e("Document Not Opening");
            }

            if (core != null) {
                mDocView = new MuPDFReaderView(this) {
                    @Override
                    protected void onMoveToChild(int i) {
                        if (core == null)
                            return;
                        super.onMoveToChild(i);
                    }
                };

                mDocView.setAdapter(new MuPDFPageAdapter(this, new FilePicker.FilePickerSupport() {
                    @Override
                    public void performPickFor(FilePicker picker) {
                    }
                }, core));
                mContentView.addView(mDocView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (id == R.id.action_share){
            String structureFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "downloadfolder"
                    + File.separator + strPDFFileName;
            File file;
            Uri clipboardUri;
            if (FileUtils.isFileExist(structureFilePath)){
                file = new File(structureFilePath);
                clipboardUri = Uri.fromFile(file);
            } else {
                clipboardUri = Uri.fromFile(new File(getFilesDir().getPath() + File.separator + strPDFFileName));//getFileUri(this, strPDFFileName);
            }

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("application/pdf");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Check out this diagram I created on baseballblueprint.com");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, clipboardUri);
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static Uri getFileUri(Context context, String fileName) {
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "temp"
                + File.separator;
        File file = new File(path, fileName);
        if (!file.exists() || file.length() == 0) {
            OutputStream outputStream = null;
            InputStream inputStream = null;
            try {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                new File(path, ".nomedia").createNewFile();

                outputStream = new FileOutputStream(file);
                inputStream = context.getAssets().open(fileName);
                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    inputStream = null;
                    outputStream.flush();
                    outputStream.close();
                    outputStream = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Uri.fromFile(file);
    }
}
