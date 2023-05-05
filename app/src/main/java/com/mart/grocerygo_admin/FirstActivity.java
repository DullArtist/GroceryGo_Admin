package com.mart.grocerygo_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.mart.grocerygo_admin.databinding.ActivityFirstBinding;
public class FirstActivity extends AppCompatActivity {

    private ActivityFirstBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        setContentView(R.layout.activity_first);


        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(() -> {
            if (mAuth.getCurrentUser() == null) {
                //go to login
                startActivity(new Intent(this, LoginActivity.class));
            }else {
                startActivity(new Intent(this, MainActivity.class));            }
        },3000);

    }
}