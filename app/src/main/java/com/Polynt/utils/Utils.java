package com.Polynt.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import com.google.common.base.Strings;
import com.Polynt.helpers.OSHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Alex on 2/8/2015.
 */
public class Utils {
    public static File createDirOnSDCard(String dirName) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/"
                + dirName);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        return folder;
    }

    public static String getFileName(URL extUrl) {
        String filename = "";
        String path = extUrl.getPath();
        String[] pathContents = path.split("[\\\\/]");
        if (pathContents != null) {
            int pathContentsLength = pathContents.length;
            System.out.println("Path Contents Length: " + pathContentsLength);
            for (int i = 0; i < pathContents.length; i++) {
                System.out.println("Path " + i + ": " + pathContents[i]);
            }
            String lastPart = pathContents[pathContentsLength - 1];
            String[] lastPartContents = lastPart.split("\\.");
            if (lastPartContents != null && lastPartContents.length > 1) {
                int lastPartContentLength = lastPartContents.length;
                System.out
                        .println("Last Part Length: " + lastPartContentLength);
                String name = "";
                for (int i = 0; i < lastPartContentLength; i++) {
                    System.out.println("Last Part " + i + ": "
                            + lastPartContents[i]);
                    if (i < (lastPartContents.length - 1)) {
                        name += lastPartContents[i];
                        if (i < (lastPartContentLength - 2)) {
                            name += ".";
                        }
                    }
                }
                String extension = lastPartContents[lastPartContentLength - 1];
                filename = name + "." + extension;
                System.out.println("Name: " + name);
                System.out.println("Extension: " + extension);
                System.out.println("Filename: " + filename);
            }
        }
        return filename;
    }

    public static Bitmap getRoundedShape(Bitmap bitmap, int width, int height) {
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        paint.setDither(true);
        paint.setShader(shader);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        return circleBitmap;
    }

   final protected static char[] hexArray = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static String bytesToHex( byte[] bytes ) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String( hexChars );
    }

    public static void logTokens(Context context) {

        // Add code to print out the key hash
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(signature.toByteArray());
                LogUtil.i("SHA1-KeyHash:::", Base64.encodeToString(md.digest(), Base64.DEFAULT));

                md = MessageDigest.getInstance("MD5");
                md.update(signature.toByteArray());
                LogUtil.i("MD5-KeyHash:::", Base64.encodeToString(md.digest(), Base64.DEFAULT));

                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                LogUtil.i("SHA-Hex-From-KeyHash:::", bytesToHex(md.digest()));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static void openBrowser(Context context, String url){
        Uri uri = Uri.parse(url);
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public static int getResourceId(Context context, String resourceName){
        try{
            resourceName = resourceName.toLowerCase();
            int resID = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
            return resID;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static JSONArray parseJSONArray(String jsonString){
        if (Strings.isNullOrEmpty(jsonString)){
            return new JSONArray();
        }
        JSONArray array = null;
        try{
            array = new JSONArray(jsonString);
        }catch (JSONException e){
            array = new JSONArray();
        }
        return array;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(21)
    public static void playSoundFile(Context context, int soundRawId){

        final SoundPool sp;
        if (OSHelper.hasLollipop()){
            sp = new SoundPool.Builder()
                    .setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA).build())
                    .build();
        } else {
            sp = new SoundPool(5, AudioManager.STREAM_MUSIC | AudioManager.STREAM_RING, 0);
        }

        final int soundId = sp.load(context, soundRawId, 1);
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                sp.play(soundId, 1, 1, 0, 0, 1);
            }
        });

        /*

        try {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, soundRawId);

            if (OSHelper.hasKitKat()){
                try {
                    Class<?> cMediaTimeProvider = Class.forName( "android.media.MediaTimeProvider" );
                    Class<?> cSubtitleController = Class.forName( "android.media.SubtitleController" );
                    Class<?> iSubtitleControllerAnchor = Class.forName( "android.media.SubtitleController$Anchor" );
                    Class<?> iSubtitleControllerListener = Class.forName( "android.media.SubtitleController$Listener" );

                    Constructor constructor = cSubtitleController.getConstructor(new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});

                    Object subtitleInstance = constructor.newInstance(context, null, null);

                    Field f = cSubtitleController.getDeclaredField("mHandler");

                    f.setAccessible(true);
                    try {
                        f.set(subtitleInstance, new Handler());
                    }
                    catch (IllegalAccessException e) {}
                    finally {
                        f.setAccessible(false);
                    }

                    Method setsubtitleanchor = mediaPlayer.getClass().getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor);

                    setsubtitleanchor.invoke(mediaPlayer, subtitleInstance, null);
                    //Log.e("", "subtitle is setted :p");
                } catch (Exception e) {}
            }

            //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.start();
        } catch (IllegalStateException e1){
            e1.printStackTrace();
        }*/
    }

}
