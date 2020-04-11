package com.kidist.bereket.notetakingandroidapp.dbhelpers;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kidist.bereket.notetakingandroidapp.entities.NoteEntity;

import java.util.List;

@Dao
public interface NoteDAO {
    @Query("SELECT Id, CreatedDate, Content FROM NoteEntity ORDER BY CreatedDate")
    List<NoteEntity> GetAll();

    @Query("SELECT Id, CreatedDate, Content FROM NoteEntity WHERE Id = :paramId")
    NoteEntity GetSingleNote(int paramId);

    @Insert
    void InsertNote(NoteEntity note);

    @Update
    void UpdateNote(NoteEntity note);

    @Delete
    void DeleteNote(NoteEntity note);
}