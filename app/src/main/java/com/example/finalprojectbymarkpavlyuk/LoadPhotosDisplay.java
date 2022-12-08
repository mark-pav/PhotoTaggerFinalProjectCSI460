package com.example.finalprojectbymarkpavlyuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LoadPhotosDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_photos_display);

        //creating connection with db through dao
        PhotoDao photo_dao = AppDatabase.getInstance(getApplicationContext()).photoDao();

        //getting intent
        Bundle extras = getIntent().getExtras();
        int[] imageIDs = extras.getIntArray("imagesArray");

        //creating list that stores all the image IDs without duplicates (because List doesn't require specific number of elements to be entered upon init
        List<Integer> imagesIDsList = new ArrayList<>();
        for(int i = 0; i<imageIDs.length; i++){
           if(imagesIDsList.contains(imageIDs[i])!=true){
               imagesIDsList.add(imageIDs[i]);
           }
        }

        //Bitmap array that will hold all the images that matched tags that were entered by used for the load option
        Bitmap[] imagesBM = new Bitmap[imagesIDsList.size()];

        //populating Bitmap array with images from DB
        for(int i = 0; i<imagesIDsList.size(); i++){
            imagesBM[i] = photo_dao.getImageByImageId(imagesIDsList.get(i));
        }

        //setting up View pager for the images
        ViewPager viewPager = (ViewPager) findViewById(R.id.loadedPhotosImageSlider);
        ImageAdapter adapter = new ImageAdapter(getApplicationContext(), imagesBM);
        viewPager.setAdapter(adapter);

        //go back button
        Button goBackBtn = findViewById(R.id.goBackButtonFromLPD);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}