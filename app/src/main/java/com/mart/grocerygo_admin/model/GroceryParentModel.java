package com.mart.grocerygo_admin.model;

import java.io.Serializable;
import java.util.List;

public class GroceryParentModel implements Serializable {

    private String grocery_cat;
    private List<GroceryModel> groceryList;

    public GroceryParentModel(String grocery_cat, List<GroceryModel> groceryList) {
        this.grocery_cat = grocery_cat;
        this.groceryList = groceryList;
    }

    public String getGrocery_cat() {
        return grocery_cat;
    }

    public void setGrocery_cat(String grocery_cat) {
        this.grocery_cat = grocery_cat;
    }

    public List<GroceryModel> getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(List<GroceryModel> groceryList) {
        this.groceryList = groceryList;
    }


}
