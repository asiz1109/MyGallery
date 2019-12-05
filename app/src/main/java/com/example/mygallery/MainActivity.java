package com.example.mygallery;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygallery.RecyclerView.AdapterRv;
import com.example.mygallery.RecyclerView.ListenerRV;
import com.example.mygallery.RecyclerView.MyImage;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ListenerRV {

    private AdapterRv adapterRv;
    private Handler handler;
    private ArrayList<MyImage> list = new ArrayList<>();

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1001;
    private static final int COLUMNS_3 = 3;
    private static final int COLUMNS_4 = 4;
    private static final int HANDLER_MESSAGE = 65656;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissionForReadExternalStorage();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapterRv = new AdapterRv();
        adapterRv.setListener(this);
        recyclerView.setAdapter(adapterRv);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNS_3));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNS_4));
        }

        DividerItemDecoration decorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decorator.setDrawable(this.getResources().getDrawable(R.drawable.divider_line));
        recyclerView.addItemDecoration(decorator);

        handler = new MyHandler(this);
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
        list.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int id = 1;
                String[] projection = {MediaStore.MediaColumns._ID};
                Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
                int index = Objects.requireNonNull(cursor).getColumnIndex(MediaStore.MediaColumns._ID);
                while (cursor.moveToNext()) {
                    long store_id = cursor.getLong(index);
                    Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, store_id);
                    list.add(new MyImage(uri));
                }
                cursor.close();
                handler.sendEmptyMessage(HANDLER_MESSAGE);
            }
        }).start();
    }

    @Override
    public void onItemClick(MyImage myImage) {
        startActivity(new Intent(MainActivity.this, ImageViewActivity.class).putExtra("uri", myImage.getUri()).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    private void setList(){
        adapterRv.setList(list);
    }



    static class MyHandler extends Handler {

        WeakReference<MainActivity> wrActivity;

        MyHandler(MainActivity activity) {
            wrActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MainActivity activity = wrActivity.get();
            if (activity != null) {
                if (msg.what == HANDLER_MESSAGE) {
                    activity.setList();
                }
            }
        }
    }
}
