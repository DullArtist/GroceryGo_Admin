package com.mart.grocerygo_admin;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class GroceryApplication extends Application {

   public GroceryApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
