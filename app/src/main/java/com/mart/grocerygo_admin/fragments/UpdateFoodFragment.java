package com.mart.grocerygo_admin.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mart.grocerygo_admin.R;
import com.mart.grocerygo_admin.databinding.FragmentUpdateFoodBinding;
import com.mart.grocerygo_admin.model.GroceryModel;
import com.mart.grocerygo_admin.utils.BitmapEncodingHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;


public class UpdateFoodFragment extends Fragment {

    private FragmentUpdateFoodBinding binding;
    private GroceryModel groceryModel;
    private ArrayAdapter<CharSequence> CatAdapter;
    private ArrayAdapter<CharSequence> QuantityAdapter;
    private ArrayAdapter<CharSequence> PriceAdapter;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpdateFoodBinding.inflate(inflater,container,false);

        binding.llFoodImg.setElevation(20f);

        databaseReference = FirebaseDatabase.getInstance().getReference("My_Grocery_Shop");


        CatAdapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.categories, android.R.layout.simple_spinner_item);
        QuantityAdapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.quantity, android.R.layout.simple_spinner_item);
        PriceAdapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.price, android.R.layout.simple_spinner_item);

        CatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        QuantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PriceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        if (getArguments() != null) {
            groceryModel = (GroceryModel) getArguments().getSerializable("grocery");
        }

        setValues(groceryModel);

        binding.ivFoodItem.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            launcher.launch(intent);
        });

        binding.btnDeleteFoodItem.setOnClickListener(view -> databaseReference.child(groceryModel.getID()).removeValue()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(requireActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(this).navigate(R.id.homeFragment);
                })
                .addOnFailureListener(e -> Toast.makeText(requireActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show()));

        binding.btnUpdateFoodItem.setOnClickListener(view -> {
            if (checkAllFields()) {
                String Food_Title = binding.itemTitleEditText.getText().toString().trim();
                long Food_Price = Long.parseLong(binding.itemPriceEditText.getText().toString().trim());
                float Food_Quantity = Float.parseFloat(binding.itemQuantityEditText.getText().toString().trim());

                String Food_Description = binding.itemDescriptionEditText.getText().toString().trim();


                String Food_Currency = binding.currencySpinner.getSelectedItem().toString();
                String Food_Quantity_Unit = binding.quantitySpinner.getSelectedItem().toString();
                String Food_Category = binding.categorySpinner.getSelectedItem().toString();

                groceryModel.setGrocery_Name(Food_Title);
                groceryModel.setGrocery_Price(Food_Price);
                groceryModel.setGrocery_Quantity(Food_Quantity);
                groceryModel.setPrice_Currency(Food_Currency);
                groceryModel.setQuantity_Unit(Food_Quantity_Unit);
                groceryModel.setGrocery_Category(Food_Category);
                groceryModel.setGrocery_Description(Food_Description);

                databaseReference.child(groceryModel.getID()).setValue(groceryModel)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(requireActivity(), "Updated", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(this).navigate(R.id.homeFragment);
                        }).addOnFailureListener(e -> Toast.makeText(requireActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());



            }
        });

        return binding.getRoot();
    }

    private void setValues(GroceryModel groceryItem) {

        binding.itemTitleEditText.setText(groceryItem.getGrocery_Name());
        binding.itemPriceEditText.setText(String.valueOf(groceryItem.getGrocery_Price()));
        binding.itemQuantityEditText.setText(String.valueOf(groceryItem.getGrocery_Quantity()));

        binding.itemDescriptionEditText.setText(groceryItem.getGrocery_Description());

        binding.categorySpinner.setSelection(CatAdapter.getPosition(groceryItem.getGrocery_Category()));
        binding.quantitySpinner.setSelection(QuantityAdapter.getPosition(String.valueOf(groceryItem.getGrocery_Quantity())));
        binding.currencySpinner.setSelection(PriceAdapter.getPosition(String.valueOf(groceryItem.getPrice_Currency())));

        binding.ivFoodItem.setImageBitmap(BitmapEncodingHelper.DecodeImage(groceryItem.getGrocery_Image()));


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
                        binding.ivFoodItem.setImageBitmap(bitmap);
                        groceryModel.setGrocery_Image(BitmapEncodingHelper.EncodeBitmap(bitmap));
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
        if (binding.itemDescriptionEditText.length() == 0) {
            binding.itemPriceEditText.setError("Please Enter Food description");
            return false;
        }
        if (binding.itemQuantityEditText.length() == 0) {
            binding.itemQuantityEditText.setError("Please Enter Quantity of Food");
            return false;
        }
        if (binding.ivFoodItem.getDrawable() == null) {
            Toast.makeText(requireActivity(), "Please Provide Food Image", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}