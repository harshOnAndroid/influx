package com.harsh.influx.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.harsh.influx.data.FoodItem;
import com.harsh.influx.R;
import com.harsh.influx.data.SelectedOrderLiveData;
import com.harsh.influx.data.SubItem;
import com.harsh.influx.utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

class FoodItemsAdapter extends RecyclerView.Adapter {


    private static final int RADIO_BTN_ID = 103900;
    private List<FoodItem> foodItems = new ArrayList<>();
    private SelectedOrderLiveData selectedOrder = SelectedOrderLiveData.getInstance();
    private Context context;
    private LayoutInflater inflater;

    public FoodItemsAdapter() {
        selectedOrder.observeForever(new Observer<List<FoodItem>>() {
            @Override
            public void onChanged(@Nullable List<FoodItem> items) {
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new FoodItemHolder(inflater.inflate(R.layout.food_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final FoodItemHolder foodHolder = (FoodItemHolder) holder;

        final FoodItem foodItem = foodItems.get(position);
        FoodItem foodItemInSelectedOrder = selectedOrder.getItem(foodItem);

        foodHolder.foodName.setText(foodItem.Name());
        foodHolder.quantity.setText(foodItemInSelectedOrder == null ? "0" : foodItemInSelectedOrder.Quantity() + "");
        fetchFoodImage(foodHolder.img, foodItem.ImageUrl());

        foodHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foodItem.SubItemCount() > 0)
                    selectedOrder.addFoodItem(addSubItemToOrder(foodItem, foodHolder.rgSubItems));
                else
                    selectedOrder.addFoodItem(foodItem);
            }
        });
        foodHolder.substract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foodItem.SubItemCount() > 0)
                    selectedOrder.removeFoodItem(addSubItemToOrder(foodItem, foodHolder.rgSubItems));
//                else
//                    selectedOrder.removeFoodItem(foodItem);
            }
        });

        if (foodItem.SubItemCount() > 0) {
            populateSubItems(foodHolder.rgSubItems, foodItem, foodHolder, foodItemInSelectedOrder);
        } else {
            foodHolder.price.setText("AED " + foodItem.ItemPrice());
            foodHolder.rgSubItems.setVisibility(View.GONE);
        }


    }

    private FoodItem addSubItemToOrder(FoodItem foodItem, RadioGroup rgSubItems) {
        FoodItem newFoodItem = foodItem.clone();
        int pos = rgSubItems.getCheckedRadioButtonId();
        pos = pos - RADIO_BTN_ID;
        SubItem subItem = foodItem.Subitems().get(pos);
        newFoodItem.Subitems().clear();
        newFoodItem.Subitems().add(subItem);
        return newFoodItem;
    }

    private void populateSubItems(RadioGroup radioGroup, FoodItem bean, final FoodItemHolder foodHolder, FoodItem foodItemInSelectedOrder) {

        boolean isSubItemMatched =false;
        radioGroup.removeAllViews();

        for (int i = 0; i < bean.SubItemCount(); i++) {
            final SubItem subItem = bean.Subitems().get(i);
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.subitem_layout, radioGroup, false);

            radioButton.setText(subItem.name());
            radioButton.setId(RADIO_BTN_ID + i);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        foodHolder.price.setText("AED " + subItem.subitemPrice());
                    }
                }
            });

            if (foodItemInSelectedOrder != null && foodItemInSelectedOrder.Subitems() != null && !foodItemInSelectedOrder.Subitems().isEmpty()) {
                if (subItem.vistaSubFoodItemId().equals(foodItemInSelectedOrder.Subitems().get(foodItemInSelectedOrder.Subitems().size() - 1).vistaSubFoodItemId())){
                    radioButton.setChecked(true);
                    isSubItemMatched = true;
                }

            }

            radioGroup.addView(radioButton);
        }

        int id = radioGroup.getCheckedRadioButtonId();
        int pos = id-RADIO_BTN_ID;
        if (!isSubItemMatched)
            ((RadioButton) radioGroup.getChildAt(id==-1?0:pos)).setChecked(true);
    }

    private void fetchFoodImage(final ImageView img, final String imgUrl) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    final Bitmap bitmap = Glide.with(context)
                            .asBitmap()
                            .load(imgUrl)
                            .into(320, 150)
                            .get();


                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(ImageHelper.getRoundedCornerBitmap(bitmap, 80));
                        }
                    });


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
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

        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.add)
        ImageView add;

        @BindView(R.id.substract)
        ImageView substract;

        @BindView(R.id.rgSize)
        RadioGroup rgSubItems;

        @BindView(R.id.quantity)
        TextView quantity;

        @BindView(R.id.price)
        TextView price;

        public FoodItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
