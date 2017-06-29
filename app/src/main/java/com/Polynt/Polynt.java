package com.Polynt;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;

import com.Polynt.config.Constants;
import com.Polynt.helpers.NetworkHelper;
import com.Polynt.model.AppStructure;
import com.Polynt.model.Category;
import com.Polynt.model.PDFFile;
import com.Polynt.ui.adapters.ArrayListAdapter;
import com.Polynt.ui.viewbinders.PDFFileItemBinder;
import com.Polynt.utils.FileUtils;
import com.Polynt.utils.LogUtil;
import com.android.vending.expansion.zipfile.ZipResourceFile;
import com.crashlytics.android.Crashlytics;
import com.longevitysoft.android.xml.plist.PListXMLHandler;
import com.longevitysoft.android.xml.plist.PListXMLParser;
import com.longevitysoft.android.xml.plist.domain.Array;
import com.longevitysoft.android.xml.plist.domain.Dict;
import com.longevitysoft.android.xml.plist.domain.PList;
import com.longevitysoft.android.xml.plist.domain.PListObject;

import io.fabric.sdk.android.Fabric;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alex on 6/2/2015.
 */
public class Polynt extends Application{

    static Polynt _instance;

    private Dict literatureDict;
    private Dict productDict;
    public Map<String, String> mapHistory;
    Thread downloadThread;
    public boolean isUpdated;

    PListXMLParser parser = new PListXMLParser();
    PListXMLHandler handler = new PListXMLHandler();

    public ArrayList downloadList;
    ProgressDialog waitingDlg = null;

    public String strTempPath;
    public String strDownloadPath;

    public static Polynt getSharedApplication(){
        return _instance;
    }
    public Dict getProductDict(){
        return this.productDict;
    }
    public Dict getLiteratureDict(){
        return this.literatureDict;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        _instance = this;
        mapHistory = new HashMap();
    }

    public AssetFileDescriptor getAssetFileDescriptor(String filePath)
    {
        PackageManager manager = this.getPackageManager();
        PackageInfo info;

        try {
            info = manager.getPackageInfo( this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return( null );
        }

        try {
            String thePackageName = this.getPackageName();
            int thePackageVer = info.versionCode;
            String zipFilePath = "/mnt/sdcard/Android/obb/" + thePackageName + "/main." + thePackageVer + "." + thePackageName + ".obb";

            ZipResourceFile expansionFile = new ZipResourceFile(zipFilePath);
            AssetFileDescriptor afd = expansionFile.getAssetFileDescriptor(filePath);
            return afd;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void CheckDownload(final Activity activity) {

        if (downloadThread != null)
            return;

        downloadThread = new Thread(new Runnable() {
            @Override
            public void run() {

               activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        waitingDlg = new ProgressDialog(activity);
                        waitingDlg.setMessage("Syncing ...");
                        waitingDlg.setCancelable(false);
                        waitingDlg.show();
                    }
                });

                if (checkIfNeedDownloaded() && isOnline()) {

                    Log.e("Polynt", "Copy plist file");
                    FileUtils.copyFile(strTempPath + Constants.PRODUCTS_LIST, strDownloadPath + Constants.PRODUCTS_LIST);
                    FileUtils.copyFile(strTempPath + Constants.LITERATURE_LIST, strDownloadPath + Constants.LITERATURE_LIST);
                    if (waitingDlg != null) waitingDlg.dismiss();
                    for (int i = 0; i < downloadList.size(); i++) {
                        PDFFile pdfFile = (PDFFile) downloadList.get(i);
                        if (pdfFile.getPdf() != null && pdfFile.getPdf().length() > 0 &&
                                pdfFile.getPdf_url() != null && pdfFile.getPdf_url().length() > 0) {
                            Log.e("Polynt", "Download start : " + pdfFile.getPdf_url());
                            downloadFileFromServer(pdfFile.getPdf_url(), strDownloadPath + pdfFile.getPdf());
                            Log.e("Polynt", "Download end : " + strDownloadPath + pdfFile.getPdf());
                        }
                    }


                    isUpdated = true;
                }
                else {
                    waitingDlg.dismiss();
                    isUpdated = true;
                }



                downloadThread = null;
            }
        });
        downloadThread.start();
    }

    private boolean checkIfNeedDownloaded(){

        downloadFileFromServer(Constants.PRODUCTS_LIST_URL, strTempPath + Constants.PRODUCTS_LIST);
        downloadFileFromServer(Constants.LITERATURE_LIST_URL, strTempPath + Constants.LITERATURE_LIST);

        ArrayList oldList = loadOldPlist();
        ArrayList newList = loadNewPlist();
        Log.e("Polynt", "Load plist file: " + oldList.size() + "-" + newList.size());

        downloadList.clear();
        for (int i = 0; i < newList.size(); i++){
            boolean bNeededDownload = true;
            PDFFile newFile = (PDFFile)newList.get(i);
            for (int j = 0; j < oldList.size(); j++){
                PDFFile oldFile = (PDFFile)oldList.get(j);
                if (newFile.isEqual(oldFile)) {
                    bNeededDownload = false;
                    oldList.remove(j);
                    break;
                }
            }

            if (bNeededDownload) {
                downloadList.add(newFile);
                Log.e("Polynt", "download add " + newFile.toString());
            } else if(!FileUtils.isFileExist(strDownloadPath + newFile.getPdf())){
                downloadList.add(newFile);
                Log.e("Polynt", "download add " + newFile.toString());
            }
        }

        if (downloadList.size() > 0)
            return true;

        return false;
    }

    ArrayList loadOldPlist() {
        Dict productDict = getPlistByType(strDownloadPath, Constants.PRODUCTS_LIST);
        if (productDict == null)
            productDict = new Dict();

        Dict literatureDict = getPlistByType(strDownloadPath, Constants.LITERATURE_LIST);
        if (literatureDict == null)
            literatureDict = new Dict();

        if(isOnline() == false)
            this.productDict = productDict;
            this.literatureDict = literatureDict;

        return loadPlist(productDict, literatureDict);
    }

    ArrayList loadNewPlist() {
        Dict productDict = getPlistByType(strTempPath, Constants.PRODUCTS_LIST);
        if (productDict == null)
            productDict = new Dict();

        Dict literatureDict = getPlistByType(strTempPath, Constants.LITERATURE_LIST);
        if (literatureDict == null)
            literatureDict = new Dict();

        if(isOnline() == true) {
            this.productDict = productDict;
            this.literatureDict = literatureDict;
        }


        return loadPlist(productDict, literatureDict);
    }


    ArrayList loadPlist(Dict productDict, Dict literatureDict){
        ArrayList list = new ArrayList();
        Map<String, PListObject> data = productDict.getConfigMap();
        Set<String> keySet = data.keySet();
        for (Iterator i = keySet.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            Array pdfArray = (Array) data.get(key);
            for (int j = 0; j < pdfArray.size(); j++) {

                Dict itemDict = (Dict) pdfArray.get(j);
                PDFFile pdffile = new PDFFile();

                if(itemDict.getConfiguration("category") != null)
                    pdffile.setCategory(itemDict.getConfiguration("category").toString());
                else
                    pdffile.setCategory("");
                if(itemDict.getConfiguration("description") != null)
                    pdffile.setDescription(itemDict.getConfiguration("description").toString());
                else
                    pdffile.setDescription("");
                if(itemDict.getConfiguration("name") != null)
                    pdffile.setName(itemDict.getConfiguration("name").toString());
                else
                    pdffile.setName("");
                if(itemDict.getConfiguration("pdf") != null)
                    pdffile.setPdf(itemDict.getConfiguration("pdf").toString());
                else
                    pdffile.setPdf("");
                if(itemDict.getConfiguration("series") != null)
                    pdffile.setSeries(itemDict.getConfiguration("series").toString());
                else
                    pdffile.setSeries("");
                if(itemDict.getConfiguration("trade_name") != null)
                    pdffile.setTradename(itemDict.getConfiguration("trade_name").toString());
                else
                    pdffile.setTradename("");
                if(itemDict.getConfiguration("pdf_url") != null)
                    pdffile.setPdf_url(itemDict.getConfiguration("pdf_url").toString());
                else
                    pdffile.setPdf_url("");
                if(itemDict.getConfiguration("modify_date") != null)
                    pdffile.setModify_date(itemDict.getConfiguration("modify_date").toString());
                else
                    pdffile.setModify_date("");
                list.add(pdffile);
            }
        }

        data = literatureDict.getConfigMap();
        keySet = data.keySet();
        for (Iterator i = keySet.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            Array pdfArray = (Array) data.get(key);
            for (int j = 0; j < pdfArray.size(); j++) {

                Dict itemDict = (Dict) pdfArray.get(j);
                PDFFile pdffile = new PDFFile();
                if(itemDict.getConfiguration("category") != null)
                    pdffile.setCategory(itemDict.getConfiguration("category").toString());
                else
                    pdffile.setCategory("");
                if(itemDict.getConfiguration("description") != null)
                    pdffile.setDescription(itemDict.getConfiguration("description").toString());
                else
                    pdffile.setDescription("");
                if(itemDict.getConfiguration("name") != null)
                    pdffile.setName(itemDict.getConfiguration("name").toString());
                else
                    pdffile.setName("");
                if(itemDict.getConfiguration("pdf") != null)
                    pdffile.setPdf(itemDict.getConfiguration("pdf").toString());
                else
                    pdffile.setPdf("");
                if(itemDict.getConfiguration("series") != null)
                    pdffile.setSeries(itemDict.getConfiguration("series").toString());
                else
                    pdffile.setSeries("");
                if(itemDict.getConfiguration("trade_name") != null)
                    pdffile.setTradename(itemDict.getConfiguration("trade_name").toString());
                else
                    pdffile.setTradename("");
                if(itemDict.getConfiguration("pdf_url") != null)
                    pdffile.setPdf_url(itemDict.getConfiguration("pdf_url").toString());
                else
                    pdffile.setPdf_url("");
                if(itemDict.getConfiguration("modify_date") != null)
                    pdffile.setModify_date(itemDict.getConfiguration("modify_date").toString());
                else
                    pdffile.setModify_date("");
                list.add(pdffile);
            }
        }
        return list;
    }

    public long getRemoteFilesize(String strUrl) {
        if (TextUtils.isEmpty(strUrl))
            return 0;
        long length = 0;
        try {
            URL url = new URL(strUrl);
            length = url.openConnection().getContentLength();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("Polynt", "Remote File : Url = " + strUrl + ", Size = " + length);
        return length;
    }

    boolean downloadFileFromServer(String serverFilePath, String localPath){
        File localFile = new File(localPath);
        Log.e("Polynt", localPath);
        localFile.delete();
        String url = serverFilePath;
        long total = 0;
        try {
            URL mUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            if (total > 0) {
                conn.setRequestProperty("Range", "bytes=" + total + "-");
            }

            InputStream is = conn.getInputStream();
            byte[] raster = new byte[1024 * 64];

            FileOutputStream fos = new FileOutputStream(localFile, true);

            while (true) {
                int Read = is.read(raster);
                if (Read < 0)
                    break;
                fos.write(raster, 0, Read);
                total += Read;
                Log.d("Polynt", "download " + total + " " + Read);
            }

            is.close();
            fos.close();
            conn.disconnect();
            if (raster != null)
                raster = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Dict getPlistByType(String dirPath, String listName){
        String structureFilePath = dirPath + listName;
        if (!FileUtils.isFileExist(structureFilePath)) {
            structureFilePath = getStructureFilePath(listName);
            if (structureFilePath == null)
                return null;
        }

        StringBuilder builder = FileUtils.readFile(structureFilePath, "utf-8");
        String plistData = builder.toString();
        if ( plistData.isEmpty() == true) {
            structureFilePath = getStructureFilePath(listName);

            builder = FileUtils.readFile(structureFilePath, "utf-8");
            plistData = builder.toString();
            if ( plistData.isEmpty() == true)
                return null;
        }

        parser.setHandler(handler);
        parser.parse(plistData);
        PList pList = ((PListXMLHandler)parser.getHandler()).getPlist();

        return (Dict)pList.getRootElement();
    }

    String getStructureFilePath(String listFileName) {
        String filePath = getFilesDir().getPath() + File.separator + listFileName;

        if (!FileUtils.isFileExist(filePath)) {
            if (!FileUtils.copyFileFromObbToInternalStorage(this, listFileName, listFileName)) {
                LogUtil.e("Failed to copy AppStructure file to internal storage");
                return null;
            }
        }

        return filePath;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        _instance = null;
    }
}
