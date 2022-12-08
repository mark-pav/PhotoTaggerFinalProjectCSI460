package com.example.finalprojectbymarkpavlyuk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView cameraView;
    Button saveBtn, loadBtn, captureBtn;
    EditText editText;

    Bitmap currentBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing all the elements
        cameraView = findViewById(R.id.cameraImageView);
        loadBtn = findViewById(R.id.loadButton);
        captureBtn = findViewById(R.id.captureButton);
        saveBtn = findViewById(R.id.saveButton);
        editText = findViewById(R.id.tagEditText);

        //request camera runtime permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        //disabled save button
        saveBtn.setEnabled(false);


        //Capture button action
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        //save button action
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //connecting to db with dao
                PhotoDao photo_dao = AppDatabase.getInstance(getApplicationContext()).photoDao();

                //getting input from edit text
                String tagInput = editText.getText().toString();

                //inserting image into Photos table
                Photos image = new Photos(currentBitmap);
                photo_dao.insert(image);

                //if tags are entered:
                if(tagInput.isEmpty()!=true) {
                    //create array splitting by ";"
                    String[] tags = tagInput.split(";");

                    //saving each tag into Tags table while removing spaces
                    for (int i = 0; i < tags.length; i++) {
                        Tags tag = new Tags();
                        tag.setTagName(removeSpace(tags[i]));
                        tag.setPhotoID(photo_dao.getLastPhotoID());
                        photo_dao.insertTag(tag);
                    }
                    Toast.makeText(getApplicationContext(), "Saved " + String.valueOf(tags.length) + "tags to DB", Toast.LENGTH_LONG).show();
                    editText.setText("");
                }


            }
        });

        //action for load button
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //connecting with db through dao
                PhotoDao photo_dao = AppDatabase.getInstance(getApplicationContext()).photoDao();

                //getting tags that are in the edit text
                String rawResultInput = editText.getText().toString();
                if(rawResultInput.isEmpty()!=true) {
                    //putting each tag into array while removing spaces
                    String[] tagsForLoad = rawResultInput.split(";");
                    for(int i = 0; i< tagsForLoad.length; i++){
                        tagsForLoad[i]=removeSpace(tagsForLoad[i]);
                    }

                    //getting image IDs for all tags that are matched with the tags (removal of duplicate image IDs will happen in LoadPhotoDisplay.class)
                    int[] imagesIDs = photo_dao.findPhotosByTags(tagsForLoad);

                    //starting new intent
                    Intent intent = new Intent(getApplicationContext(), LoadPhotosDisplay.class);
                    intent.putExtra("imagesArray", imagesIDs);
                    startActivity(intent);

                }

            }
        });




    }

    //action for successful capture button click
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            currentBitmap = bitmap;
            saveBtn.setEnabled(true);
            cameraView.setImageBitmap(bitmap);
        }
    }

    //method to remove spaces (for tags)
    public static String removeSpace(String str){
        str = str.replaceAll("\\s","");
        return str;
    }
}