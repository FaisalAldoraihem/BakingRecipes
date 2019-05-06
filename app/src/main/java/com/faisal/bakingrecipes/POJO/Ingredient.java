package com.faisal.bakingrecipes.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    private Ingredient(Parcel parcel) {
        String[] data = new String[3];
        parcel.readStringArray(data);

        this.quantity = Double.parseDouble(data[0]);
        this.measure = data[1];
        this.ingredient = data[2];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                String.valueOf(this.quantity),
                this.measure,
                this.ingredient
        });
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);  //using parcelable constructor
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @SerializedName("quantity")
    @Expose
    private final double quantity;
    @SerializedName("measure")
    @Expose
    private final String measure;
    @SerializedName("ingredient")
    @Expose
    private final String ingredient;

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }


    public String getIngredient() {
        return ingredient;
    }

}