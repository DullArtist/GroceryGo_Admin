package com.mart.grocerygo_admin.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.mart.grocerygo_admin.R;
import com.mart.grocerygo_admin.model.GroceryModel;
import com.mart.grocerygo_admin.utils.BitmapEncodingHelper;

import java.util.ArrayList;
import java.util.List;

public class GroceryHomeAdapter extends RecyclerView.Adapter<GroceryHomeAdapter.GroceryHomeViewHolder> {

    private final Context context;
    private final List<GroceryModel> groceryModelList;
    private final List<GroceryModel> twoItemList;

    private final boolean showFullItems;


    public GroceryHomeAdapter(Context context, List<GroceryModel> groceryModelList, boolean showFullItems) {
        this.context = context;
        this.groceryModelList = groceryModelList;
        this.showFullItems = showFullItems;

        twoItemList = new ArrayList<>(getTwoItemList(groceryModelList));
    }

    @NonNull
    @Override
    public GroceryHomeAdapter.GroceryHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grocery_layout,parent,false);
        return new GroceryHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryHomeAdapter.GroceryHomeViewHolder holder, int position) {

        bindData(holder, showFullItems ? groceryModelList.get(position) : twoItemList.get(position));

    }

    private void bindData(GroceryHomeViewHolder holder, GroceryModel groceryModel) {

        holder.groceryName.setText(groceryModel.getGrocery_Name());
        holder.groceryPrice.setText(String.valueOf(groceryModel.getGrocery_Price()));
        holder.groceryQuantity.setText(String.valueOf(groceryModel.getGrocery_Quantity()));
        holder.groceryQuantityUnit.setText(groceryModel.getQuantity_Unit());
        holder.groceryPriceCurrency.setText(groceryModel.getPrice_Currency());


        holder.groceryImage.setImageBitmap(BitmapEncodingHelper.DecodeImage(groceryModel.getGrocery_Image()));


        holder.itemView.setOnClickListener(view -> {
            // get a reference to the Navigation Controller from the Activity or Fragment that holds the NavHostFragment
            NavController navController = Navigation.findNavController((Activity)context, R.id.nav_host_fragment);
            // navigate to the desired fragment with the ID and any arguments that you want to pass to it
            Bundle arguments = new Bundle();
            arguments.putSerializable("grocery", groceryModel);
            navController.navigate(R.id.updateFoodFragment, arguments);
        });




    }

    @Override
    public int getItemCount() {
        if (showFullItems) {
            return groceryModelList.size();
        }else {
            return twoItemList.size();
        }

    }

    public static class GroceryHomeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView groceryImage;
        private final TextView groceryName;
        private final TextView groceryPrice;
        private final TextView groceryQuantity;
        private final TextView groceryQuantityUnit;
        private final TextView groceryPriceCurrency;

        public GroceryHomeViewHolder(@NonNull View itemView) {
            super(itemView);

            groceryImage = itemView.findViewById(R.id.iv_grocery_food);
            groceryName = itemView.findViewById(R.id.tv_grocery_food_name);
            groceryPrice = itemView.findViewById(R.id.tv_grocery_food_price);
            groceryQuantity = itemView.findViewById(R.id.tv_grocery_food_quantity);
            groceryQuantityUnit = itemView.findViewById(R.id.tv_grocery_food_quantity_unit);
            groceryPriceCurrency = itemView.findViewById(R.id.tv_grocery_food_currency);


        }


    }

    private List<GroceryModel> getTwoItemList(List<GroceryModel> fullList) {

        List<GroceryModel> tempList = new ArrayList<>();

        if (fullList != null) {
            if (fullList.size() >=2) {
                tempList = fullList.subList(0,2);
            }else {
                tempList = fullList;
            }
        }

        return tempList;



    }


}
