package com.example.suitcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class NewNote extends AppCompatActivity {

    EditText notetitle, price, notecontent;
    ImageButton savenote, deletebtn;
    ProgressBar progress;
    TextView pagetitle;
    String title, pric, content, docid, url;
    boolean isedit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        notetitle = findViewById(R.id.notetitle);
        price = findViewById(R.id.price);
        notecontent = findViewById(R.id.notecontent);
        savenote = findViewById(R.id.savenote);
        progress = findViewById(R.id.progress);
        pagetitle = findViewById(R.id.pagetitle);
        deletebtn = findViewById(R.id.deletebtn);


        title = getIntent().getStringExtra("title");
        pric = getIntent().getStringExtra("price");
        content = getIntent().getStringExtra("content");
        docid = getIntent().getStringExtra("docid");


        if(docid!=null && !docid.isEmpty()){
            isedit = true;
        }

        notetitle.setText(title);
        price.setText(pric);
        notecontent.setText(content);

        if(url != null && !url.isEmpty()) {
            notecontent.setText("Available at: " + url + "\n");
        }

        if(isedit){
            pagetitle.setText("Edit your note");
            deletebtn.setVisibility(View.VISIBLE);
        }

        savenote.setOnClickListener(v-> saveNote());
        deletebtn.setOnClickListener(v-> deleteNote());
    }

    void saveNote(){

        String title = notetitle.getText().toString().trim();
        String pr = price.getText().toString().trim();
        String content = notecontent.getText().toString();


        if(title==null || title.isEmpty()){
            notetitle.setError("Specify the title");
            return;
        } if(pr==null || pr.isEmpty()){
            notetitle.setError("Specify the title");
            return;
        } if(content==null || content.isEmpty()){
            notetitle.setError("Specify the title");
            return;
        }

        if(!isedit){
            String purchased = "no";
            DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
            try {
                databaseHelper.noteDao().addNote(
                        new Note(title, pr, content, purchased)
                );
                startActivity(new Intent(NewNote.this, HomePage.class));
            } catch (Exception e) {
                Toast.makeText(this, "Error occured: " + e, Toast.LENGTH_SHORT).show();
            }
        }else {
            String purchased = getIntent().getStringExtra("purchased");
            DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
            try {
                Note old = databaseHelper.noteDao().getNoteById(docid);
                old.setContent(content);
                old.setPrice(pr);
                old.setTitle(title);
                old.setPurchased(purchased);
                databaseHelper.noteDao().updateNote(old);
                startActivity(new Intent(NewNote.this, HomePage.class));
            } catch (Exception e) {
                Toast.makeText(this, "Error occured: " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    void progress(boolean pro){
        if(pro){
            progress.setVisibility(View.VISIBLE);
            savenote.setVisibility(View.GONE);
        }else{
            progress.setVisibility(View.GONE);
            savenote.setVisibility(View.VISIBLE);
        }
    }
    void deleteNote(){
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.noteDao().deleteNote(docid);
                startActivity(new Intent(NewNote.this, HomePage.class));
                finish();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


}