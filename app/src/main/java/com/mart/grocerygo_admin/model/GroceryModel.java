package com.mart.grocerygo_admin.model;

import java.io.Serializable;

public class GroceryModel implements Serializable {

    private String Grocery_Name;
    private String Grocery_Image;
    private String Grocery_Category;
    private String Price_Currency;
    private String Quantity_Unit;
    private long Grocery_Price;
    private float Grocery_Quantity;

    private String Grocery_Description;

    private String ID;



    public GroceryModel() {
    }

    public GroceryModel(String grocery_Name, String grocery_Image, String grocery_Category, String price_Currency, String quantity_Unit, long grocery_Price, float grocery_Quantity,String grocery_Description,String ID) {
        Grocery_Name = grocery_Name;
        Grocery_Image = grocery_Image;
        Grocery_Category = grocery_Category;
        Price_Currency = price_Currency;
        Quantity_Unit = quantity_Unit;
        Grocery_Price = grocery_Price;
        Grocery_Quantity = grocery_Quantity;
        Grocery_Description = grocery_Description;
        this.ID = ID;
    }

    public String getGrocery_Name() {
        return Grocery_Name;
    }

    public void setGrocery_Name(String grocery_Name) {
        Grocery_Name = grocery_Name;
    }

    public String getGrocery_Image() {
        return Grocery_Image;
    }

    public void setGrocery_Image(String grocery_Image) {
        Grocery_Image = grocery_Image;
    }

    public String getGrocery_Category() {
        return Grocery_Category;
    }

    public void setGrocery_Category(String grocery_Category) {
        Grocery_Category = grocery_Category;
    }

    public String getPrice_Currency() {
        return Price_Currency;
    }

    public void setPrice_Currency(String price_Currency) {
        Price_Currency = price_Currency;
    }

    public String getQuantity_Unit() {
        return Quantity_Unit;
    }

    public void setQuantity_Unit(String quantity_Unit) {
        Quantity_Unit = quantity_Unit;
    }

    public long getGrocery_Price() {
        return Grocery_Price;
    }

    public void setGrocery_Price(long grocery_Price) {
        Grocery_Price = grocery_Price;
    }

    public float getGrocery_Quantity() {
        return Grocery_Quantity;
    }

    public void setGrocery_Quantity(float grocery_Quantity) {
        Grocery_Quantity = grocery_Quantity;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getGrocery_Description() {
        return Grocery_Description;
    }

    public void setGrocery_Description(String grocery_Description) {
        Grocery_Description = grocery_Description;
    }
}
