package com.zeeshan_s.mygallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageClicked extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_clicked);

        Intent intent = getIntent();
        Uri imgUri = Uri.parse(intent.getStringExtra("img_name"));
        imageView = findViewById(R.id.selectedImg);

        imageView.setImageURI( imgUri );
    }
}