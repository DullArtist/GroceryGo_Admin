package com.mart.grocerygo_admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mart.grocerygo_admin.databinding.ActivitySignUpBinding;
import com.mart.grocerygo_admin.model.User;
import com.mart.grocerygo_admin.utils.ProgressDialogHelper;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    private boolean isAllFieldsChecked = false;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ProgressDialogHelper progressDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        progressDialogHelper = new ProgressDialogHelper(this);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("All_Users");

        binding.btnCreateAccount.setOnClickListener(view -> {

            isAllFieldsChecked = isAllFieldsOk();
            if (isAllFieldsChecked) {
                String Email = binding.etEmail.getEditText().getText().toString();
                String Username = binding.etUsername.getEditText().getText().toString();
                String pass = binding.etPassword.getEditText().getText().toString();
                String c_pass = binding.etPasswordConfirm.getEditText().getText().toString();
                if (doPasswordsMatch(pass,c_pass)) {

                    progressDialogHelper.ShowProgressDialog(" Please Wait","We are making your account",false);

                    mAuth.createUserWithEmailAndPassword(Email,pass).addOnSuccessListener(authResult ->
                            databaseReference.child("Admin").child(authResult.getUser().getUid()).setValue(new User(Username,Email,authResult.getUser().getUid())).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    progressDialogHelper.CancelProgressDialog();
                                    clearAllFields();
                                    startActivity(new Intent(this, LoginActivity.class));
                                    Toast.makeText(this, "SignUp Complete", Toast.LENGTH_SHORT).show();
                                }else {
                                    progressDialogHelper.CancelProgressDialog();
                                    Toast.makeText(this, "SignUp Not Completed", Toast.LENGTH_SHORT).show();
                                }
                            })).addOnFailureListener(e -> {
                        progressDialogHelper.CancelProgressDialog();
                        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    });

                }

            }
        });

    }

    private void clearAllFields() {
        binding.etPassword.getEditText().getText().clear();
        binding.etPasswordConfirm.getEditText().getText().clear();
        binding.etEmail.getEditText().getText().clear();
        binding.etUsername.getEditText().getText().clear();
    }
    private boolean isAllFieldsOk() {
        if (binding.etUsername.getEditText().getText().length() == 0) {
            binding.etUsername.getEditText().setError("Enter Username");
            return false;
        }
        if (binding.etEmail.getEditText().getText().length() == 0) {
            binding.etEmail.getEditText().setError("Enter Email");
            return false;
        }
        if (binding.etPassword.getEditText().getText().length() == 0) {
            binding.etPassword.getEditText().setError("Enter Password");
            return false;
        }
        if (binding.etPasswordConfirm.getEditText().getText().length() == 0) {
            binding.etPasswordConfirm.getEditText().setError("Enter Confirm password");
            return false;
        }


        return true;
    }

    private boolean doPasswordsMatch(String pass, String c_pass) {
        return pass.equals(c_pass);
    }

}