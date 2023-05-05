package com.mart.grocerygo_admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mart.grocerygo_admin.R;
import com.mart.grocerygo_admin.utils.BitmapEncodingHelper;

import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    private final List<String> deals;

    public DealAdapter(List<String> deals) {
        this.deals = deals;
    }

    @NonNull
    @Override
    public DealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealAdapter.ViewHolder holder, int position) {

        String deal = deals.get(position);
        holder.iv_deal.setImageBitmap(BitmapEncodingHelper.DecodeImage(deal));

    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_deal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_deal = itemView.findViewById(R.id.iv_deal);
        }
    }
}
