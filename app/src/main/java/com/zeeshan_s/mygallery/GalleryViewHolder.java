package com.zeeshan_s.mygallery;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
   public ImageView imageView;
//   public TextView imageName;
    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);

        Log.i("tag", "GalleryViewHolder: View holder e problem nai!!");
        imageView = itemView.findViewById(R.id.allImages);
//        imageName = itemView.findViewById(R.id.imageName);
    }
}
