package com.harsh.influx.data;

import java.util.List;

public class FoodCategory {

    private String TabName;
    private List<FoodItem> fnblist;

    public String TabName() {
        return TabName;
    }

    public void TabName(String tabName) {
        TabName = tabName;
    }

    public List<FoodItem> Fnblist() {
        return fnblist;
    }

    public void Fnblist(List<FoodItem> fnblist) {
        this.fnblist = fnblist;
    }
}
