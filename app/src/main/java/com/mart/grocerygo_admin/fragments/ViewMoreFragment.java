package com.mart.grocerygo_admin.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mart.grocerygo_admin.R;
import com.mart.grocerygo_admin.adapters.GroceryHomeAdapter;
import com.mart.grocerygo_admin.databinding.FragmentViewMoreBinding;
import com.mart.grocerygo_admin.model.GroceryModel;
import com.mart.grocerygo_admin.model.GroceryParentModel;

import java.util.List;

public class ViewMoreFragment extends Fragment {

    private FragmentViewMoreBinding binding;
    private List<GroceryModel> myList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewMoreBinding.inflate(inflater,container,false);

        if (getArguments() != null) {
            myList = (List<GroceryModel>) getArguments().getSerializable("list");
        }

        GroceryHomeAdapter adapter = new GroceryHomeAdapter(requireActivity(),myList,true);
        binding.RVViewMore.setLayoutManager(new GridLayoutManager(requireActivity(),2));
        binding.RVViewMore.setAdapter(adapter);


        return binding.getRoot();
    }
}