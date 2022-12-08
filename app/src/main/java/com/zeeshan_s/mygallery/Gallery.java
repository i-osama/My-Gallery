package com.zeeshan_s.mygallery;

import android.net.Uri;

public class Gallery {
    String imageName;
    long imageID;
    Uri imageUri;

    public Gallery(String imageName, long imageID, Uri imageUri) {
        this.imageName = imageName;
        this.imageID = imageID;
        this.imageUri = imageUri;
    }

    public String getImageName() {
        return imageName;
    }

    public long getImageID() {
        return imageID;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
