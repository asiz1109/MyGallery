package com.example.mygallery;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageViewActivity extends AppCompatActivity {

    private ImageView full_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        full_iv = findViewById(R.id.full_iv);
        String absolutePath = getIntent().getStringExtra("absolutePath");
        Uri uri = Uri.fromFile(new File(absolutePath));
        Picasso.get()
                .load(uri)
                .into(full_iv);
    }
}
