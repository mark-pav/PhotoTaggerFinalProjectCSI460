package com.example.finalprojectbymarkpavlyuk;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tags {

    @PrimaryKey(autoGenerate = true)
    private int tagId;

    @ColumnInfo(name = "Tag")
    private String tagName;

    @ColumnInfo(name = "PhotoID")
    private int photoID;

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public Tags() {
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
