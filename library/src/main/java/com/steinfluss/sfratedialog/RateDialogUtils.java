package com.steinfluss.sfratedialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RateDialogUtils {

    private static int countToRateGlobal;
    private static String THIS_APP_URL;

    public static void render(Activity activity, int countToRate, String appUrl, Drawable icon){
        countToRateGlobal = countToRate;
        THIS_APP_URL = appUrl;
        Context context = activity.getApplicationContext();
        int renderCounts = PreferenceUtils.getRenderCount(context);
        if (renderCounts == countToRate){
            getPleaseRateDialog(activity, icon).show();
        }else {
            PreferenceUtils.setRenderCount(context,renderCounts + 1);
        }
    }

    private static MaterialDialog.Builder getPleaseRateDialog(Activity activity, Drawable icon){
        final Context context = activity.getApplicationContext();
        return new MaterialDialog.Builder(activity)
                .title(context.getString(R.string.pls_review_app))
                //.customView(R.layout.layout_rate_dialog,true)
                .content(R.string.please_review)
                .icon(icon)
                .positiveText(R.string.rate_now)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dontShowRateAgain(context);
                        openURL(THIS_APP_URL,context);
                    }
                })
                .neutralText(R.string.rate_later)
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        PreferenceUtils.setRenderCount(context,countToRateGlobal-1);
                    }
                })
                .negativeText(R.string.dont_rate)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dontShowRateAgain(context);
                    }
                });
    }

    private static void dontShowRateAgain(Context context){
        PreferenceUtils.setRenderCount(context,countToRateGlobal+1); //dont show again
    }

    private static void openURL(String url, Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
