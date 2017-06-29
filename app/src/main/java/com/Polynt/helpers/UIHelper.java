package com.Polynt.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.common.base.Strings;
import com.Polynt.R;

/**
 * Created by Wu Kai on 10/3/2014.
 */

public class UIHelper {

    public static void showToast(Context ctx, int messageId){
        Toast.makeText(ctx, messageId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context ctx, String message){
        message = Strings.nullToEmpty(message);
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static void showMessageDialog(Context ctx, int message){
        new MaterialDialog.Builder(ctx)
                .content(message)
                .positiveText("OK")
                .show();
    }

    public static void showMessageDialog(Context ctx, String message){
        new MaterialDialog.Builder(ctx)
                .content(message)
                .positiveText("OK")
                .show();
    }

    public static void showMessageDialogWithDismissListener(Context ctx, String message, DialogInterface.OnDismissListener listener){
        new MaterialDialog.Builder(ctx)
                .title(message)
                .titleGravity(GravityEnum.CENTER)
                .positiveText("OK")
                .dismissListener(listener)
                .show();
    }

    public static void showMessageDialogWithTitle(Context ctx, String title, String message){
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(message)
                .positiveText("OK")
                .show();
    }

    public static void showMessageDialogWithTitle(Context ctx, int titleResId, int messageResId){
        new MaterialDialog.Builder(ctx)
                .title(titleResId)
                .content(messageResId)
                .positiveText("OK")
                .show();
    }

    public static void showMessageDialogWithIcon(Context ctx, String title, String message, int iconResId){
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(message)
                .iconRes(iconResId)
                .positiveText("OK")
                .show();
    }

    public static void showWarningDialog(Context ctx, String title, String message){
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(message)
                .iconRes(R.drawable.ic_warning_amber)
                .positiveText("OK")
                .show();
    }

    public static void showWarningDialog(Context ctx, String title, int messageResId){
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(messageResId)
                .iconRes(R.drawable.ic_warning_amber)
                .positiveText("OK")
                .show();
    }

    public static void showErrorDialog(Context ctx, String title, String message){
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(message)
                .iconRes(R.drawable.ic_error_red)
                .positiveText("OK")
                .show();
    }

    public static void showErrorDialog(Context ctx, String title, int messageResId){
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(messageResId)
                .iconRes(R.drawable.ic_error_red)
                .positiveText("OK")
                .show();
    }


    public static void showMessageDialogWithIcon(Context ctx, int titleResId, int messageResId, int iconResId){
        new MaterialDialog.Builder(ctx)
                .title(titleResId)
                .content(messageResId)
                .iconRes(iconResId)
                .positiveText("OK")
                .show();
    }

    public static void showMessageDialogWithCallback(Context ctx, String message, String positiveText, MaterialDialog.ButtonCallback callback){
        new MaterialDialog.Builder(ctx)
                .content(message)
                .callback(callback)
                .positiveText(positiveText)
                .show();
    }

    public static void showMessageDialogWithCallback(Context ctx, String message, String positiveText, String negativeText, MaterialDialog.ButtonCallback callback){
        new MaterialDialog.Builder(ctx)
                .content(message)
                .callback(callback)
                .positiveText(positiveText)
                .negativeText(negativeText)
                .show();
    }

    public static void showMessageDialogWithCallback(Context ctx, String title, String message, String positiveText, String negativeText, MaterialDialog.ButtonCallback callback){
        new MaterialDialog.Builder(ctx)
                .title(title)
                .content(message)
                .callback(callback)
                .positiveText(positiveText)
                .negativeText(negativeText)
                .show();
    }

    public static void showDialog(Context ctx, String title, String message, int alertType, DialogInterface.OnDismissListener dismissListener){
        /*SweetAlertDialog dialog = new SweetAlertDialog(ctx, alertType);
        dialog.setTitleText(title);
        dialog.setContentText(message);
        dialog.setConfirmText("OK");
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        dialog.setOnDismissListener(dismissListener);
        dialog.show();*/
    }

    public static void showDialog(Context ctx, String title, String message, int alertType, String confirmText){
        /*SweetAlertDialog dialog = new SweetAlertDialog(ctx, alertType);
        dialog.setTitleText(title);
        dialog.setContentText(message);
        dialog.setConfirmText(confirmText);
        dialog.setConfirmClickListener(confirmListener);
        dialog.setCancelText(cancelText);
        dialog.setCancelClickListener(cancelListener);
        dialog.show();*/
    }

    public static void hideSoftKeyboard(Context ctx, View view){
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(view != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled){
        if(viewGroup == null)
            return;
        int childCount = viewGroup.getChildCount();
        for(int i = 0 ; i < childCount ; i++){
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);

            if( view instanceof ViewGroup){
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

    public static String getEditTextString(EditText edt){
        return edt.getText().toString();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void makeRectangleImageView(final ImageView imgView, final boolean isWidth){
        ViewTreeObserver.OnGlobalLayoutListener listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams param = imgView.getLayoutParams();
                if(isWidth){
                    param.width = imgView.getHeight();
                }else{
                    param.height = imgView.getWidth();
                }
                imgView.setLayoutParams(param);
                if(OSHelper.hasJellyBean())
                    imgView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    imgView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        };

        imgView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    public static void openBrowser(Context context, String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (!(url.startsWith("http") || url.startsWith("https"))){
            url = "http://" + url;
        }
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

}
