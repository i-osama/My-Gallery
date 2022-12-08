package com.zeeshan_s.mygallery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Gallery> galleryList;
    RecyclerView recyclerView;
//    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        viewPager2 = findViewById(R.id.viewPager);
        recyclerView= findViewById(R.id.imageRecycler);

        galleryList = new ArrayList<>();
        requestPermissions();
    }

    private void requestPermissions() {
        // below line is use to request permission in the current activity.
        // this method is use to handle error in runtime permissions
        Dexter.withContext(this)
                // below line is use to request the number of permissions which are required in our app.
                .withPermissions(Manifest.permission.CAMERA,
                        // below is the list of permissions
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.READ_CONTACTS
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                // after adding permissions we are calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now
                            Toast.makeText(MainActivity.this, "All the permissions are granted..", Toast.LENGTH_SHORT).show();

                            getAllImages(); //------- this function will show the images
                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently, we will show user a dialog message.
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        // this method is called when user grants some permission and denies some of them.
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(error -> {
            // we are displaying a toast message for error message.
            Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
        })
                // below line is use to run the permissions on same thread and to check the permissions
                .onSameThread().check();
    }

    private void getAllImages() {
//        ----------------------------- This method will show the images ----------------------
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_MODIFIED
        };

        Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = getContentResolver().query(contentUri,projection, null, null, null);

        Log.i("tag", "Img test _____________----- " + cursor );
        if (cursor != null && cursor.getCount()>0){
            Log.i("tag", "getAllImages: "+ cursor.getCount());
            cursor.moveToPosition(0);
            while (true){
                Log.i("tag", "getAllImages: Entered----");

                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                Log.i("tag", "getAllImages: Entered----");

                Uri imageUri = ContentUris.withAppendedId(contentUri, id);

                galleryList.add(new Gallery(name, id, imageUri));

                Log.i("tag", "getAllImages: "+ name+" "+ imageUri);

//                GalleryAdapter adapter = new GalleryAdapter(MainActivity.this, galleryList);
//                recyclerView.setAdapter(adapter);

                if (!cursor.isLast()){cursor.moveToNext();}
                else {break;}

            }


            Log.i("tag", " ------------------------");
            GalleryAdapter adapter = new GalleryAdapter(MainActivity.this, galleryList);
            recyclerView.setAdapter(adapter);
//            viewPager2.setAdapter(adapter);
        }
    }   // below is the shoe setting dialog method which is use to display a dialogue message.
    private void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions");

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel();
            // below is the intent from which we are redirecting our user.
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // this method is called when user click on negative button.
            dialog.cancel();
        });
        // below line is used to display our dialog
        builder.show();
    }



}