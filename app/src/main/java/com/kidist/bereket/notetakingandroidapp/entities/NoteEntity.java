package com.kidist.bereket.notetakingandroidapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.kidist.bereket.notetakingandroidapp.dbhelpers.DateConverter;

import java.util.Date;

@Entity
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    public int Id;

    @ColumnInfo(name = "CreatedDate")
    @TypeConverters(DateConverter.class)
    public Date CreatedDate;

    @ColumnInfo(name = "Content")
    public String Content;
}