package com.mart.grocerygo_admin.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mart.grocerygo_admin.R;
import com.mart.grocerygo_admin.adapters.DealAdapter;
import com.mart.grocerygo_admin.databinding.FragmentDealsBinding;
import com.mart.grocerygo_admin.utils.BitmapEncodingHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DealsFragment extends Fragment {

    private FragmentDealsBinding binding;
    private DatabaseReference reference;
    private final List<String> deals = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDealsBinding.inflate(inflater,container,false);

        reference = FirebaseDatabase.getInstance().getReference("My_Deals");
        reference.keepSynced(true);

        //readData();


//        binding.fabAddDeal.setOnClickListener(view -> {
//            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            launcher.launch(intent);
//        });

        return binding.getRoot();
    }

    private void readData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                deals.clear();
                if (snapshot.hasChildren()) {

                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        String deal = dataSnapshot.getValue(String.class);
                        deals.add(deal);
                    }

                    DealAdapter adapter = new DealAdapter(deals);
                    binding.RVDeals.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    binding.RVDeals.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
                        reference.child(getDateTime()).setValue(BitmapEncodingHelper.EncodeBitmap(bitmap))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(requireActivity(), "Added", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(e -> Toast.makeText(requireActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());

                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    });

    private String getDateTime() {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
        Date myDate = new Date();
        return timeStampFormat.format(myDate);
    }

}