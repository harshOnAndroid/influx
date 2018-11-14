package com.harsh.influx.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.harsh.influx.data.FoodCategory;
import com.harsh.influx.data.FoodItem;
import com.harsh.influx.data.HomeViewModel;
import com.harsh.influx.R;
import com.harsh.influx.data.SelectedOrderLiveData;
import com.harsh.influx.data.SubItem;
import com.harsh.influx.utils.AnimUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.orderOverviewLayout)
    LinearLayout orderOverviewLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.progressLayout)
    FrameLayout progressLayout;

    @BindView(R.id.rvOrderOverview)
    RecyclerView rvOrderOverview;

    @BindView(R.id.total)
    TextView total;

    private HomeViewModel viewModel;
    private List<FoodCategory> foodItems;
    private OverviewItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        rvOrderOverview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OverviewItemsAdapter();
        rvOrderOverview.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.fetchFood().observe(this, new Observer<List<FoodCategory>>() {
            @Override
            public void onChanged(@Nullable List<FoodCategory> foodCategories) {
                if (foodCategories != null) {

                    foodItems = new ArrayList<>();
                    foodItems.addAll(foodCategories);

                    createViewPager();
                    setUpTabs();

                    progressLayout.setVisibility(View.GONE);
                } else
                    Toast.makeText(HomeActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });

        SelectedOrderLiveData.getInstance().observe(this, new Observer<List<FoodItem>>() {
            @Override
            public void onChanged(@Nullable List<FoodItem> items) {
                if (items != null) {
                    adapter.updateList(items);
                    total.setText("AED "+getTotal(items));
                }
            }
        });

    }

     Double getTotal(List<FoodItem> items) {

            double price = 0.0;

            for (int j=0;j<items.size();j++) {
             FoodItem bean = items.get(j);
                if (bean.Subitems().size() <= 0)
                    price += Double.valueOf(bean.ItemPrice()) * bean.Quantity();
                for (int i = 0; i < bean.Subitems().size(); i++) {
                    final SubItem subItem = bean.Subitems().get(i);

                    price += Double.valueOf(subItem.subitemPrice());
                }
            }
            return price;
    }


    private void setUpTabs() {

        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.removeAllTabs();

        for (int i = 0; i < foodItems.size(); i++) {
            FoodCategory foodCategory = foodItems.get(i);
            TextView tabView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabView.setText(foodCategory.TabName());

            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(tabView);
            tabLayout.addTab(tab);
        }

    }

    private void createViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), foodItems);
        viewPager.setAdapter(adapter);
    }

    @OnClick(R.id.checkoutLayout)
    void toggleSlider() {
        orderOverviewLayout.setTag(orderOverviewLayout.getTag() == null ? false : orderOverviewLayout.getTag());
        AnimUtil.toggleSlide(orderOverviewLayout);
    }
}
