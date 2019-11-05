package com.thedancercodes.flickrfindr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainFlickrModel {

    @SerializedName("photos")
    @Expose
    private FlickrPhotosResponse photos;
    @SerializedName("stat")
    @Expose
    private String stat;

    public FlickrPhotosResponse getPhotos() {
        return photos;
    }

    public void setPhotos(FlickrPhotosResponse photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
