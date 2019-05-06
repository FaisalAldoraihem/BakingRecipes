package com.faisal.bakingrecipes.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {


    private Step(Parcel parcel) {
        String[] data = new String[4];
        parcel.readStringArray(data);

        this.shortDescription = data[0];
        this.description = data[1];
        this.videoURL = data[2];
        this.thumbnailURL = data[3];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                this.shortDescription,
                this.description,
                this.videoURL,
                this.thumbnailURL
        });
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {

        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);  //using parcelable constructor
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shortDescription")
    @Expose
    private final String shortDescription;
    @SerializedName("description")
    @Expose
    private final String description;
    @SerializedName("videoURL")
    @Expose
    private final String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private final String thumbnailURL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

}