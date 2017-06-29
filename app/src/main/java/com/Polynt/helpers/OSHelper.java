package com.Polynt.helpers;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebView;

import com.Polynt.utils.LogUtil;

import java.io.File;

/**
 * Created by Alex on 12/19/2014.
 */
public class OSHelper {

    public static boolean isExerternalStorageAvailable() {
        return Environment.getExternalStorageState().contentEquals(
                Environment.MEDIA_MOUNTED ) ? true : false;
    }

    public static boolean isInternetAvailable( Context context ) {
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = conn.getActiveNetworkInfo();
        if ( activeNetworkInfo != null
                && activeNetworkInfo.isConnectedOrConnecting() ) {
            return true;
        }
        return false;
    }

    public static void deleteAppCache( Context context ) {
        deleteFiles( context.getCacheDir() );
    }

    /**
     * Deletes Directories after removing all files recursively
     *
     * @param dir
     *            Directory to clear
     */
    public static void deleteFiles( File dir ) {
        File files[] = dir.listFiles();
        for ( File file : files ) {
            if ( file.isDirectory() )
                deleteFiles( dir );
            else
                file.delete();
        }
        dir.delete();
    }

    @TargetApi(11)
    private void disableHardwareAcceleration( Application app, WebView webView ) {
        if ( (!webView.isHardwareAccelerated())
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            LogUtil.e("HardwareAcceleration", "disable HardwareAcceleration");
            webView.setLayerType( View.LAYER_TYPE_SOFTWARE, null );
        }
    }

    public static void placeCall( FragmentActivity fragment, String phoneNumber ) {
        Intent callIntent = new Intent( Intent.ACTION_CALL );
        callIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_NO_USER_ACTION );
        // tel://
        callIntent.setData( Uri.parse("tel:" + phoneNumber) );
        fragment.startActivity( callIntent );
    }

    public static String resolvedMediaPath( Uri mURI, FragmentActivity fragment ) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = fragment.managedQuery( mURI, projection, null, null,
                null );
        int column_index_data = cursor
                .getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
        cursor.moveToFirst();
        String capturedImageFilePath = cursor.getString( column_index_data );
        return capturedImageFilePath;
    }

    public static String getFileUri( Uri mURI, FragmentActivity fragment ) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = fragment.managedQuery( mURI, projection, null, null,
                null );
        int column_index_data = cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
        cursor.moveToFirst();
        String capturedImageFilePath = cursor.getString( column_index_data );
        return "file://" + capturedImageFilePath;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Gingerbread or later.
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Honeycomb or later.
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Honeycomb MR1 or later.
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Honeycomb MR1 or later.
     */
    public static boolean hasHoneycombMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * ICS or later.
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * JellyBean or later.
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * JellyBean MR1 or later.
     */
    public static boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * JellyBean MR2 or later.
     */
    public static boolean hasJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * KitKat or later.
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Lollipop or later.
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
