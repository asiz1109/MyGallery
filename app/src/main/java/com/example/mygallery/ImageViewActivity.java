package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageViewActivity extends AppCompatActivity {

    private ImageView full_iv;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        full_iv = findViewById(R.id.full_iv);
        uri = getIntent().getParcelableExtra("uri");
        Picasso.get()
                .load(uri)
                .into(full_iv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu_iva, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            Intent intent = new Intent()
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_STREAM, uri)
                    .setType("image/jpeg");
            startActivity(Intent.createChooser(intent, getResources().getString(R.string.share)));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
