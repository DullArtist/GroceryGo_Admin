package com.mart.grocerygo_admin.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mart.grocerygo_admin.R;
import com.mart.grocerygo_admin.model.GroceryParentModel;

import java.io.Serializable;
import java.util.List;

public class GroceryHomeParentAdapter extends RecyclerView.Adapter<GroceryHomeParentAdapter.ViewHolder> {

    private final Context context;
    private final List<GroceryParentModel> groceryParentModels;

    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();


    public GroceryHomeParentAdapter(Context context, List<GroceryParentModel> groceryParentModels) {
        this.context = context;
        this.groceryParentModels = groceryParentModels;
    }

    @NonNull
    @Override
    public GroceryHomeParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_parent_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryHomeParentAdapter.ViewHolder holder, int position) {

        GroceryParentModel groceryParentModel = groceryParentModels.get(position);

        //set grocery cat
        holder.grocery_cat.setText(groceryParentModel.getGrocery_cat());

        //set grocery rv child
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.grocery_RV.getContext(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManager.setInitialPrefetchItemCount(groceryParentModel.getGroceryList().size());

        GroceryHomeAdapter groceryHomeAdapter = new GroceryHomeAdapter(context,groceryParentModel.getGroceryList(),false);

        holder.grocery_RV.setLayoutManager(linearLayoutManager);
        holder.grocery_RV.setAdapter(groceryHomeAdapter);
        holder.grocery_RV.setRecycledViewPool(viewPool);

        holder.grocery_view_more.setOnClickListener(view -> {
            // get a reference to the Navigation Controller from the Activity or Fragment that holds the NavHostFragment
            NavController navController = Navigation.findNavController((Activity)context, R.id.nav_host_fragment);

            // navigate to the desired fragment with the ID and any arguments that you want to pass to it
            Bundle arguments = new Bundle();
            arguments.putSerializable("list", (Serializable) groceryParentModel.getGroceryList());
            navController.navigate(R.id.viewMoreFragment, arguments);
        });


    }

    @Override
    public int getItemCount() {
        return groceryParentModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView grocery_cat;
        private final TextView grocery_view_more;
        private final RecyclerView grocery_RV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            grocery_cat = itemView.findViewById(R.id.tv_food_category);
            grocery_RV = itemView.findViewById(R.id.RV_grocery_child);
            grocery_view_more = itemView.findViewById(R.id.tv_view_more);


        }

    }
}
