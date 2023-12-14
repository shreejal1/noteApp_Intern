package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ImageButton menu;
    SearchView search;
    AdapterNote adapterNote;
    ArrayList<Note> filteredList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recview);
        menu = findViewById(R.id.menu);
        fab = findViewById(R.id.newnote);
        search = findViewById(R.id.search);
        search.clearFocus();
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Note> arrNote = (ArrayList<Note>) databaseHelper.noteDao().getAllNotes();
                filteredList = new ArrayList<>();
                if(query.length()>0){
                    for(int i = 0; i < arrNote.size(); i++){
                        if(arrNote.get(i).getTitle().toLowerCase().contains(query.toLowerCase())){
                            Note note = new Note();
                            note.setTitle(arrNote.get(i).title);
                            note.setContent(arrNote.get(i).price);
                            note.setContent(arrNote.get(i).content);
                            note.setPurchased(arrNote.get(i).purchased);
                            note.setUid(arrNote.get(i).uid);
                            filteredList.add(note);
                        }
                    }
                    AdapterNote adapterNote = new AdapterNote(filteredList, HomePage.this);
                    recyclerView.setAdapter(adapterNote);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));
                }else{
                    AdapterNote adapterNote = new AdapterNote(arrNote, HomePage.this);
                    recyclerView.setAdapter(adapterNote);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Note> arrNote = (ArrayList<Note>) databaseHelper.noteDao().getAllNotes();
                filteredList = new ArrayList<>();
                if(newText.length()>0){
                    for(int i = 0; i < arrNote.size(); i++){
                        if(arrNote.get(i).getTitle().toLowerCase().contains(newText.toLowerCase())){
                            Note note = new Note();
                            note.setTitle(arrNote.get(i).title);
                            note.setContent(arrNote.get(i).price);
                            note.setContent(arrNote.get(i).content);
                            note.setPurchased(arrNote.get(i).purchased);
                            note.setUid(arrNote.get(i).uid);
                            filteredList.add(note);
                        }
                    }
                    AdapterNote adapterNote = new AdapterNote(filteredList, HomePage.this);
                    recyclerView.setAdapter(adapterNote);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));
                }else{
                    AdapterNote adapterNote = new AdapterNote(arrNote, HomePage.this);
                    recyclerView.setAdapter(adapterNote);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));
                }
                return false;
            }


        });
        fab.setOnClickListener(v-> startActivity(new Intent(HomePage.this, NewNote.class)));
        menu.setOnClickListener(v-> showMenu());
        recviewsetup();

    }

    private void filterList(String newText) {
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
        ArrayList<Note> arrNote = (ArrayList<Note>) databaseHelper.noteDao().getAllNotes();
        ArrayList<Note> filteredList = new ArrayList<>();
        for(Note note: arrNote){
            if(note.getTitle().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(note);
            }
        }
        if(!filteredList.isEmpty()){
//            adapterNote.(filteredList);
        }else{
            Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
        }
    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(HomePage.this, menu);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(HomePage.this, MainActivity.class));
                    finish();
                    return true;
                }
                return false;

            }
        });
    }

    void recviewsetup(){

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
        ArrayList<Note> arrNote = (ArrayList<Note>) databaseHelper.noteDao().getAllNotes();
//        for (int i = 0; i< arrNote.size(); i++){
//            Log.d("Data", "Title: " + arrNote.get(i).getTitle());
//        }
        AdapterNote adapterNote = new AdapterNote(arrNote, this);
        recyclerView.setAdapter(adapterNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
//    private void filterList(String newText) {
//        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
//        ArrayList<Note> arrNote = (ArrayList<Note>) databaseHelper.noteDao().getAllNotes();
//        ArrayList<Note> filteredList = new ArrayList<>();
//        for(Note note: arrNote){
//            if(note.getTitle().toLowerCase().contains(newText.toLowerCase())){
//                filteredList.add(note);
//            }
//        }
//        if(!filteredList.isEmpty()){
//            adapterNote.setFilteredList(filteredList);
//        }else{
//            Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
//        }
//    }

}