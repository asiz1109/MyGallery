package com.example.mygallery.RecyclerView;

import android.net.Uri;

public class MyImage {

    private Uri uri;

    public MyImage(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}

