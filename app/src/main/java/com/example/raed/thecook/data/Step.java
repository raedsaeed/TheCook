package com.example.raed.thecook.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raed on 4/3/18.
 */

public class Step implements Parcelable {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
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

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    private Step (Parcel parcel) {
        id = parcel.readInt();
        shortDescription = parcel.readString();
        description = parcel.readString();
        videoURL = parcel.readString();
        thumbnailURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step> () {
        @Override
        public Step createFromParcel(Parcel parcel) {
            return new Step(parcel);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Step)) return false;

        Step step = (Step) o;

        if (getId() != step.getId()) return false;
        if (getShortDescription() != null ? !getShortDescription().equals(step.getShortDescription()) : step.getShortDescription() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(step.getDescription()) : step.getDescription() != null)
            return false;
        if (getVideoURL() != null ? !getVideoURL().equals(step.getVideoURL()) : step.getVideoURL() != null)
            return false;
        return getThumbnailURL() != null ? getThumbnailURL().equals(step.getThumbnailURL()) : step.getThumbnailURL() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getShortDescription() != null ? getShortDescription().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getVideoURL() != null ? getVideoURL().hashCode() : 0);
        result = 31 * result + (getThumbnailURL() != null ? getThumbnailURL().hashCode() : 0);
        return result;
    }
}
