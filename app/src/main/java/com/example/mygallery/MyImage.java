package com.example.mygallery;

import android.net.Uri;

public class MyImage {

    private int id;
    private Uri uri;
    private String absolutePath;

    public MyImage(int id, Uri uri, String absolutePath) {
        this.id = id;
        this.uri = uri;
        this.absolutePath = absolutePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
