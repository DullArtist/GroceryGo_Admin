package com.mart.grocerygo_admin.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.mart.grocerygo_admin.R;

public class ProgressDialogHelper {

    private ProgressDialog progressDialog;
    private final Context context;

    public ProgressDialogHelper(Context context) {
        this.context = context;
    }

    public void ShowProgressDialog(String title, String message, boolean Cancelable) {

        progressDialog = new ProgressDialog(context, R.style.CustomProgressDialog);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(Cancelable);

        progressDialog.show();

    }

    public void CancelProgressDialog() {
        progressDialog.dismiss();
    }

}
