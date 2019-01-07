package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

// Parcelable so that a ParcelableArrayList of Recipe super objects can cross Activities and Fragment boundaries,
// initially from RecipeActivity to RecipeDetailActivity and
// RecipeDetailFragment so that I can extract both the list of Ingredients and Steps to fill their associated views.

public class Ingredient implements Parcelable {

    /*
     * Fields
     */

    private String mName;
    private String mQuantity;
    private String mUnit;


    /*
     * Constructors
     */


    public Ingredient(String name, String quantity, String unit) {
        mName = name;
        mQuantity = quantity;
        mUnit = unit;
    }

    private Ingredient(Parcel in) {
        mName = in.readString();
        mQuantity = in.readString();
        mUnit = in.readString();
    }


    /*
     * Implementing Parcelable
     */


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mQuantity);
        parcel.writeString(mUnit);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int i) {
            return new Ingredient[i];
        }
    };


    /*
     * Getters
     */


    public String getIngredientName() { return mName; }
    public String getIngredientQuantity() { return mQuantity; }
    public String getIngredientUnit() { return mUnit; }


    /*
     * Setters
     */


    public void setIngredientName(String name) { mName = name; }
    public void setIngredientQuantity(String quantity) { mQuantity = quantity; }
    public void setIngredientUnit(String unit) { mUnit = unit; }
}