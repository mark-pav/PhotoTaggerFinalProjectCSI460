package com.example.finalprojectbymarkpavlyuk;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Photos {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Photo")
    private Bitmap photo;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Photos(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

}

