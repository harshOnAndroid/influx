package com.harsh.influx.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harsh.influx.data.FoodItem;
import com.harsh.influx.R;
import com.harsh.influx.data.SelectedOrderLiveData;
import com.harsh.influx.data.SubItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class OverviewItemsAdapter extends RecyclerView.Adapter {


    private List<FoodItem> foodItems = new ArrayList<>();
    private SelectedOrderLiveData selectedOrder = SelectedOrderLiveData.getInstance();
    private Context context;
    private LayoutInflater inflater;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new FoodItemHolder(inflater.inflate(R.layout.overview_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        FoodItemHolder foodHolder = (FoodItemHolder) holder;

        FoodItem foodItem = foodItems.get(position);

        int quantity = foodItem.Quantity();
        foodHolder.foodName.setText(foodItem.Name() + "(" + String.valueOf(quantity) + ")");

        double price = calculatePrice(foodItem);
        foodHolder.price.setText(String.format("%.2f", price));
    }

    private Double calculatePrice(FoodItem bean) {

        double price = 0.0;

        if (bean.Subitems().size()<=0)
            return Double.valueOf(bean.ItemPrice()) * bean.Quantity();
        for (int i = 0; i < bean.Subitems().size(); i++) {
            final SubItem subItem = bean.Subitems().get(i);

            price += Double.valueOf(subItem.subitemPrice());
        }
        return price;
    }


    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    void updateList(List<FoodItem> items) {
        foodItems.clear();
        foodItems.addAll(items);
        notifyDataSetChanged();
    }


    class FoodItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView foodName;

        @BindView(R.id.price)
        TextView price;

        public FoodItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
