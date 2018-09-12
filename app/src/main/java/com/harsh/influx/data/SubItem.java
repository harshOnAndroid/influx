package com.harsh.influx.data;

public class SubItem {

    private String Name;
    private String PriceInCents;
    private String SubitemPrice;
    private String VistaParentFoodItemId;
    private String VistaSubFoodItemId;


    public String name() {
        return Name;
    }

    public void name(String name) {
        Name = name;
    }

    public String priceInCents() {
        return PriceInCents;
    }

    public void priceInCents(String priceInCents) {
        PriceInCents = priceInCents;
    }

    public String subitemPrice() {
        return SubitemPrice;
    }

    public void subitemPrice(String subitemPrice) {
        SubitemPrice = subitemPrice;
    }

    public String vistaParentFoodItemId() {
        return VistaParentFoodItemId;
    }

    public void vistaParentFoodItemId(String vistaParentFoodItemId) {
        VistaParentFoodItemId = vistaParentFoodItemId;
    }

    public String vistaSubFoodItemId() {
        return VistaSubFoodItemId;
    }

    public void vistaSubFoodItemId(String vistaSubFoodItemId) {
        VistaSubFoodItemId = vistaSubFoodItemId;
    }
}
