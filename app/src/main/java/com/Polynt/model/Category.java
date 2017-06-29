package com.Polynt.model;

/**
 * Created by Alex on 6/2/2015.
 */
public class Category {
    String categoryTitle;
    int categoryID;
    public void setCategorytitle(String categoryTitle){
        this.categoryTitle = categoryTitle;
    }
    public String getCategorytitle(){
        return this.categoryTitle;
    }
    public void setCategoryID(int categoryID){
        this.categoryID = categoryID;
    }
    public int getCategoryID(){
        return this.categoryID;
    }
}
