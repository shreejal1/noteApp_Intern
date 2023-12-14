package com.example.suitcase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note WHERE uid = :id")
            Note getNoteById(String id);

    @Insert
    void addNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("DELETE FROM note WHERE uid = :id")
    void deleteNote(String id);
}
