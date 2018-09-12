package com.harsh.influx.data;

import java.util.ArrayList;
import java.util.List;

public class FoodItem {

    private String Cinemaid;
    private String TabName;
    private String VistaFoodItemId;
    private String Name;
    private String PriceInCents;
    private String ItemPrice;
    private String SevenStarExperience;
    private String OtherExperience;
    private String ImageUrl;
    private String ImgUrl;
    private String VegClass;
    private Integer SubItemCount;
    private Integer Quantity;
    private List<SubItem> subitems;

    public FoodItem(){
        subitems = new ArrayList<>();
    }

    public Integer Quantity() {
        return Quantity;
    }

    public void Quantity(Integer quantity) {
        Quantity = quantity;
    }

    public String Cinemaid() {
        return Cinemaid;
    }

    public void Cinemaid(String cinemaid) {
        Cinemaid = cinemaid;
    }

    public String TabName() {
        return TabName;
    }

    public void TabName(String tabName) {
        TabName = tabName;
    }

    public String VistaFoodItemId() {
        return VistaFoodItemId;
    }

    public void VistaFoodItemId(String vistaFoodItemId) {
        VistaFoodItemId = vistaFoodItemId;
    }

    public String Name() {
        return Name;
    }

    public void Name(String name) {
        Name = name;
    }

    public String PriceInCents() {
        return PriceInCents;
    }

    public void PriceInCents(String priceInCents) {
        PriceInCents = priceInCents;
    }

    public String ItemPrice() {
        return ItemPrice;
    }

    public void ItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String SevenStarExperience() {
        return SevenStarExperience;
    }

    public void SevenStarExperience(String sevenStarExperience) {
        SevenStarExperience = sevenStarExperience;
    }

    public String OtherExperience() {
        return OtherExperience;
    }

    public void OtherExperience(String otherExperience) {
        OtherExperience = otherExperience;
    }

    public String ImageUrl() {
        return ImageUrl;
    }

    public void ImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String ImgUrl() {
        return ImgUrl;
    }

    public void ImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String VegClass() {
        return VegClass;
    }

    public void VegClass(String vegClass) {
        VegClass = vegClass;
    }

    public Integer SubItemCount() {
        return SubItemCount;
    }

    public void SubItemCount(Integer subItemCount) {
        SubItemCount = subItemCount;
    }

    public List<SubItem> Subitems() {
        return subitems;
    }

    public void Subitems(List<SubItem> subitems) {
        this.subitems = subitems;
    }

    public FoodItem clone() {
        FoodItem newFoodItem = new FoodItem();
        newFoodItem.Cinemaid = Cinemaid ;
        newFoodItem.TabName = TabName ;
        newFoodItem.VistaFoodItemId = VistaFoodItemId ;
        newFoodItem.Name = Name ;
        newFoodItem.PriceInCents = PriceInCents ;
        newFoodItem.ItemPrice = ItemPrice ;
        newFoodItem.SevenStarExperience = SevenStarExperience ;
        newFoodItem.OtherExperience = OtherExperience ;
        newFoodItem.ImageUrl = ImageUrl ;
        newFoodItem.ImgUrl = ImgUrl ;
        newFoodItem.VegClass = VegClass ;
        newFoodItem.SubItemCount = SubItemCount ;
        newFoodItem.Quantity = Quantity ;
        newFoodItem.subitems.addAll(this.subitems);
        return newFoodItem;
    }
}
