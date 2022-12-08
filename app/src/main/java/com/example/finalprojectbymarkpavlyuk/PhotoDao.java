package com.example.finalprojectbymarkpavlyuk;

import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PhotoDao {
    @Insert
    void insert(Photos... photos);

    @Insert
    void insertTag(Tags... tags);


    @Query("SELECT max(id) FROM Photos")
    int getLastPhotoID();


    @Query("SELECT Photo FROM Photos where id = :id")
    Bitmap getImageByImageId(int id);

    @Query("SELECT photoID FROM Tags WHERE Tag IN (:tag1)")
    int[] findPhotosByTags(String[] tag1);
}
