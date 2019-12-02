package com.example.mygallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterRv adapterRv;

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissionForReadExternalStorage();

        recyclerView = findViewById(R.id.recycler_view);
        adapterRv = new AdapterRv();
        recyclerView.setAdapter(adapterRv);

        if(getResources().getConfiguration().orientation == getResources().getConfiguration().ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));
        }

        DividerItemDecoration decorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decorator.setDrawable(this.getResources().getDrawable(R.drawable.divider_line));
        recyclerView.addItemDecoration(decorator);

        getImages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getImages();
    }

    private void checkPermissionForReadExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == READ_STORAGE_PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                MainActivity.this.finish();
            }
        }
    }

    private void getImages() {
        ArrayList<MyImage> list = new ArrayList<>();
        int id = 1;
        String projection[] = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        int index = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
        if (cursor!=null){
            while (cursor.moveToNext()) {
                String absolutePathOfImage = cursor.getString(index);
                Uri uri = Uri.fromFile(new File(absolutePathOfImage));
                list.add(new MyImage(id, uri));
            }
        }
        cursor.close();
        adapterRv.setList(list);
    }
}
