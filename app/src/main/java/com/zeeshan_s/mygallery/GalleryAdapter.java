package com.zeeshan_s.mygallery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
    Context context;
    List<Gallery> galleryList;

    public GalleryAdapter(Context context, List<Gallery> galleryList) {
        this.context = context;
        this.galleryList = galleryList;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i("tag", "Adapter problem 1------------");
        View view = LayoutInflater.from(context).inflate(R.layout.image_shower, parent, false);
        GalleryViewHolder galleryViewHolder = new GalleryViewHolder(view);
        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Gallery gallery= galleryList.get(position);
//        String imgName = galleryList.get(position).getImageName();

        Log.i("tag", "Adapter problem 2--------------------");
        
        holder.imageView.setImageURI(gallery.getImageUri());

//        String imgName = galleryList.get(position).getImageName();
//        holder.imageView.setImageURI(galleryList.get(position).getImageUri());
//        holder.imageName.setText(gallery.getImageName());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ImageClicked.class);
            String imgUri= String.valueOf(gallery.getImageUri());
            intent.putExtra("img_name", imgUri);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }
}
