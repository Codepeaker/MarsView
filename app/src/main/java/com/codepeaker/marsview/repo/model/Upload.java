package com.codepeaker.marsview.repo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Upload implements Parcelable {
    private String name;
    private String url;

    public Upload() {
    }

    public Upload(String url) {
        this.url = url;
    }

    public Upload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    protected Upload(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Upload> CREATOR = new Creator<Upload>() {
        @Override
        public Upload createFromParcel(Parcel in) {
            return new Upload(in);
        }

        @Override
        public Upload[] newArray(int size) {
            return new Upload[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
    }
}
