package com.faisal.bakingrecipes.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe implements Parcelable {


    private Recipe(Parcel parcel) {
        name = parcel.readString();
        ingredients = new ArrayList<>();
        parcel.readList(ingredients, Ingredient.class.getClassLoader());
        steps = new ArrayList<>();
        parcel.readList(steps, Step.class.getClassLoader());
        servings = parcel.readInt();
        image = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);  //using parcelable constructor
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private final String name;
    @SerializedName("ingredients")
    @Expose
    private final List<Ingredient> ingredients;
    @SerializedName("steps")
    @Expose
    private final List<Step> steps;
    @SerializedName("servings")
    @Expose
    private final Integer servings;
    @SerializedName("image")
    @Expose
    private final String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

}