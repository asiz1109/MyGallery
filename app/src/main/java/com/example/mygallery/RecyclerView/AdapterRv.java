package com.example.mygallery.RecyclerView;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygallery.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class AdapterRv extends RecyclerView.Adapter<AdapterRv.MyViewHolder> {

    private List<MyImage> list = Collections.emptyList();
    private ListenerRV listenerRV;

    public void setList(List<MyImage> image){
        list = image;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_rv, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyImage image = list.get(position);
        Uri uri = image.getUri();
        Picasso.get()
                .load(uri)
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.setListener(image, listenerRV);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setListener(ListenerRV listener) {
        listenerRV = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }

        void setListener(final MyImage myImage, final ListenerRV listenerRV) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerRV.onItemClick(myImage);
                }
            });
        }
    }
}
