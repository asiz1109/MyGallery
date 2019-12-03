package com.example.mygallery.RecyclerView;

import android.net.Uri;

public class MyImage {

    private int id;
    private Uri uri;

    public MyImage(int id, Uri uri) {
        this.id = id;
        this.uri = uri;
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
}

