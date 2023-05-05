package com.mart.grocerygo_admin.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mart.grocerygo_admin.R;
import com.mart.grocerygo_admin.databinding.FragmentAddfoodBinding;
import com.mart.grocerygo_admin.model.GroceryModel;
import com.mart.grocerygo_admin.utils.BitmapEncodingHelper;
import com.mart.grocerygo_admin.utils.ProgressDialogHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

public class AddfoodFragment extends Fragment implements View.OnClickListener {

    private FragmentAddfoodBinding binding;
    private String FoodImage;
    private ProgressDialogHelper progressDialogHelper;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddfoodBinding.inflate(inflater, container, false);

        binding.llFoodImg.setElevation(20f);

        progressDialogHelper = new ProgressDialogHelper(requireActivity());
        databaseReference = FirebaseDatabase.getInstance().getReference("My_Grocery_Shop");

        //get Image
        binding.ivFoodItem.setOnClickListener(this);
        //add item
        binding.btnAddFoodItem.setOnClickListener(this);


        return binding.getRoot();

    }

    private void AddFoodItem() {

        String Food_Title = binding.itemTitleEditText.getText().toString().trim();
        long Food_Price = Long.parseLong(binding.itemPriceEditText.getText().toString().trim());
        float Food_Quantity = Long.parseLong(binding.itemQuantityEditText.getText().toString().trim());

        String Description =  binding.itemDescriptionEditText.getText().toString().trim();

        String Food_Currency = binding.currencySpinner.getSelectedItem().toString();
        String Food_Quantity_Unit = binding.quantitySpinner.getSelectedItem().toString();
        String Food_Category = binding.categorySpinner.getSelectedItem().toString();

        progressDialogHelper.ShowProgressDialog("Please Wait","Food item is being uploaded",false);

        String Grocery_ID = getDateTime(); 
        GroceryModel groceryModel = new GroceryModel(Food_Title,FoodImage,Food_Category,Food_Currency,Food_Quantity_Unit,Food_Price,Food_Quantity,Description,Grocery_ID);

        //save in DB Firebase
        databaseReference.child(Grocery_ID).setValue(groceryModel)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(requireActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                            progressDialogHelper.CancelProgressDialog();
                            clearAllFields();
                        })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    progressDialogHelper.CancelProgressDialog();
                });


    }

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    Uri ImageUri = result.getData().getData();

                    try {
                        InputStream inputStream = requireActivity().getContentResolver().openInputStream(ImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        FoodImage = BitmapEncodingHelper.EncodeBitmap(bitmap);
                        binding.ivFoodItem.setImageBitmap(bitmap);


                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    });


    private boolean checkAllFields() {

        if (binding.itemTitleEditText.length() == 0 ) {
            binding.itemTitleEditText.setError("Please Enter Food Name");
            return false;
        }
        if (binding.itemPriceEditText.length() == 0) {
            binding.itemPriceEditText.setError("Please Enter Food Price");
            return false;
        }
        if (binding.itemQuantityEditText.length() == 0) {
            binding.itemQuantityEditText.setError("Please Enter Quantity of Food");
            return false;
        }
        if (binding.itemDescriptionEditText.length() == 0) {
            binding.itemDescriptionEditText.setError("Enter description");
            return false;
        }
        if (binding.ivFoodItem.getDrawable() == null) {
            Toast.makeText(requireActivity(), "Please Provide Food Image", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void clearAllFields() {
        ViewGroup group = binding.constraintLayout;
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof TextInputLayout) {
                Objects.requireNonNull(((TextInputLayout) view).getEditText()).setText("");
            }
            if (view instanceof ImageView && view.getId() == R.id.iv_food_item) {
                ((ImageView) view).setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.placeholder));
            }
        }
    }

    private String getDateTime() {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
        Date myDate = new Date();
        return timeStampFormat.format(myDate);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_food_item) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            launcher.launch(intent);
        } else if (id == R.id.btn_add_food_item) {
            boolean isAllFieldsOk = checkAllFields();
            if (isAllFieldsOk) {
                AddFoodItem();
            }
        }
    }


}