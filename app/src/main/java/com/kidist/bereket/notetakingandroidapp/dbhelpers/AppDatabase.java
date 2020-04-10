package com.kidist.bereket.notetakingandroidapp.dbhelpers;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kidist.bereket.notetakingandroidapp.entities.NoteEntity;

@Database(entities = {NoteEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDAO noteDAO();

}