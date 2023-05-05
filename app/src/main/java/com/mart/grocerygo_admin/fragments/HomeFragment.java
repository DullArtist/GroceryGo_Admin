package com.mart.grocerygo_admin.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.transition.MaterialContainerTransform;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mart.grocerygo_admin.R;
import com.mart.grocerygo_admin.adapters.GroceryHomeParentAdapter;
import com.mart.grocerygo_admin.databinding.FragmentHomeBinding;
import com.mart.grocerygo_admin.model.GroceryModel;
import com.mart.grocerygo_admin.model.GroceryParentModel;
import com.mart.grocerygo_admin.utils.ProgressDialogHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseReference databaseReference;

    private final List<GroceryModel> FruitList = new ArrayList<>();
    private final List<GroceryModel> VegetableList = new ArrayList<>();
    private final List<GroceryModel> MeatList = new ArrayList<>();
    private final List<GroceryModel> BakeryList = new ArrayList<>();
    private final List<GroceryModel> BeveragesList = new ArrayList<>();
    private final List<GroceryModel> LentilsList = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        databaseReference = FirebaseDatabase.getInstance().getReference("My_Grocery_Shop");

        readData();

        binding.fabAddGrocery.setOnClickListener(view -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_addfoodFragment);
        });

        return binding.getRoot();
    }

    private void readData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                FruitList.clear();
                VegetableList.clear();
                BakeryList.clear();
                MeatList.clear();
                LentilsList.clear();
                BeveragesList.clear();

                if (snapshot.hasChildren()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        GroceryModel grocery_item = dataSnapshot.getValue(GroceryModel.class);

                        switch (Objects.requireNonNull(grocery_item).getGrocery_Category()) {
                            case "Fruits":
                                FruitList.add(grocery_item);
                                break;
                            case "Vegetables":
                                VegetableList.add(grocery_item);
                                break;
                            case "Bakery":
                                BakeryList.add(grocery_item);
                                break;
                            case "Beverages":
                                BeveragesList.add(grocery_item);
                                break;
                            case "Meat":
                                MeatList.add(grocery_item);
                                break;
                            case "Lentils":
                                LentilsList.add(grocery_item);
                                break;
                        }

                    }

                    List<GroceryParentModel> groceryParentModels = new ArrayList<>();
                    if (FruitList.size()>0)
                        groceryParentModels.add(new GroceryParentModel("Fruits",FruitList));
                    if (VegetableList.size()>0)
                        groceryParentModels.add(new GroceryParentModel("Vegetables",VegetableList));
                    if (BakeryList.size()>0)
                        groceryParentModels.add(new GroceryParentModel("Bakery",BakeryList));
                    if (LentilsList.size()>0)
                        groceryParentModels.add(new GroceryParentModel("Lentils",LentilsList));
                    if (MeatList.size()>0)
                        groceryParentModels.add(new GroceryParentModel("Meat",MeatList));
                    if (BeveragesList.size()>0)
                        groceryParentModels.add(new GroceryParentModel("Beverages",BeveragesList));

                    GroceryHomeParentAdapter adapter = new GroceryHomeParentAdapter(requireActivity(),groceryParentModels);
                    binding.parentRVHome.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    binding.parentRVHome.setAdapter(adapter);





                }
                binding.progressCircularHome.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                binding.progressCircularHome.setVisibility(View.GONE);
            }
        });

        databaseReference.keepSynced(true);


    }

}