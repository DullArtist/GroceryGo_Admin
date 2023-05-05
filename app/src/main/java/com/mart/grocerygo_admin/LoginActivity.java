package com.mart.grocerygo_admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.mart.grocerygo_admin.databinding.ActivityLoginBinding;
import com.mart.grocerygo_admin.utils.ProgressDialogHelper;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private FirebaseAuth mAuth;
    private boolean iSAllFieldsChecked = false;
    private ProgressDialogHelper progressDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHelper = new ProgressDialogHelper(this);

        mAuth = FirebaseAuth.getInstance();

        binding.btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(this,SignUpActivity.class));
        });

        binding.btnLogin.setOnClickListener(view -> {

            iSAllFieldsChecked = isAllFieldsOk();
            if (iSAllFieldsChecked) {

                progressDialogHelper.ShowProgressDialog("Please Wait","We are logging you in",false);

                String Email = binding.etEmail.getEditText().getText().toString();
                String Password = binding.etPassword.getEditText().getText().toString();
                mAuth.signInWithEmailAndPassword(Email,Password).addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                    progressDialogHelper.CancelProgressDialog();
                    clearAllFields();
                    startActivity(new Intent(this, MainActivity.class));
                }).addOnFailureListener(e -> {
                    progressDialogHelper.CancelProgressDialog();
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
            }

        });

    }

    private void clearAllFields() {
        binding.etEmail.getEditText().getText().clear();
        binding.etPassword.getEditText().getText().clear();
    }

    private boolean isAllFieldsOk() {

        if (binding.etEmail.getEditText().getText().length() == 0) {
            binding.etEmail.getEditText().setError("Enter Email");
            return false;
        }
        if (binding.etPassword.getEditText().getText().length() == 0) {
            binding.etEmail.getEditText().setError("Enter Password");
            return false;
        }


        return true;
    }
}