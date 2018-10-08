package com.codepeaker.marsview.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class AppUtils {
    private static AppUtils appUtils;
    private ProgressDialog progressDialog;

    private AppUtils() {
    }

    public static synchronized AppUtils getInstance() {
        if (appUtils == null) {
            appUtils = new AppUtils();
        }
        return appUtils;
    }

    public void showPDialog(Context context, String msg) {
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(msg);
            progressDialog.show();
        }
    }

    public void hidePDialog(Context context) {
        if (context != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showPTALToast(Context context) {
        Toast.makeText(context, "Faced some error, please try again later"
                , Toast.LENGTH_LONG).show();
    }
}
