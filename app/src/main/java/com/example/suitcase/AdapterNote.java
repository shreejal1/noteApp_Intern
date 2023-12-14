package com.example.suitcase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.NoteViewHolder> {
    Context context;
    private ArrayList<Note> dataList;
    private ArrayList<Note> filterData;


    public AdapterNote(ArrayList<Note> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        filterData  = new ArrayList<>(dataList);

    }
//    public void setFilteredList(ArrayList<Note> filteredList){
//        this.dataList = filteredList;
//        notifyDataSetChanged();
//    }

//    @Override
//    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
//        holder.titlev.setText(note.title);
//        holder.prv.setText("Rs."+note.price);
//        holder.contv.setText(note.content);
//
//        if(note.purchased.equals("yes")){
//            holder.cart.setVisibility(View.GONE);
//            holder.tick.setVisibility(View.VISIBLE);
//        }else{
//            holder.cart.setVisibility(View.VISIBLE);
//            holder.tick.setVisibility(View.GONE);
//        }
//        //add to purchased
//        holder.cart.setOnClickListener((v)-> {
//            note.setPurchased("yes");
//            DocumentReference documentReference;
//            String docid = this.getSnapshots().getSnapshot(position).getId();
//            documentReference = Utility.getCollecRef().document(docid);
//            documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                    if (task.isSuccessful()) {
//                        Utility.showToast(context, "Added to purchased");
//
//                    } else {
//                        Utility.showToast(context, "Failed");
//                    }
//                }
//            });
//        });
//        //remove from purchased
//        holder.tick.setOnClickListener((v)-> {
//            note.setPurchased("no");
//            DocumentReference documentReference;
//            String docid = this.getSnapshots().getSnapshot(position).getId();
//            documentReference = Utility.getCollecRef().document(docid);
//            documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                    if (task.isSuccessful()) {
//                        Utility.showToast(context, "Removed from purchased");
//
//                    } else {
//                        Utility.showToast(context, "Failed");
//                    }
//                }
//            });
//        });
//
//        holder.send.setOnClickListener(v->{
//            Intent intent = new Intent(context, SendMessage.class);
//            intent.putExtra("title", note.title);
//            intent.putExtra("price", note.price);
//            intent.putExtra("content", note.content);
//
//            context.startActivity(intent);
//        });
//
//        //to edit the item
//        holder.edit.setOnClickListener((v)-> {
//            Intent intent = new Intent(context, NewNote.class);
//            intent.putExtra("title", note.title);
//            intent.putExtra("price", note.price);
//            intent.putExtra("content", note.content);
//            String docid = this.getSnapshots().getSnapshot(position).getId();
//            intent.putExtra("docid", docid);
//            context.startActivity(intent);
//        });
//    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recnote, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = dataList.get(position);
        holder.titlev.setText(note.title);
        holder.prv.setText("Rs. "+note.price);
        holder.contv.setText(note.content);

        holder.itemView.setOnClickListener((v)-> {
            Intent intent = new Intent(context, NewNote.class);
            intent.putExtra("title", note.title);
            intent.putExtra("price", note.price);
            intent.putExtra("content", note.content);
            String docid = String.valueOf(note.uid);
            intent.putExtra("docid", docid);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titlev, prv, contv;
        ImageButton cart, tick, send;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titlev = itemView.findViewById(R.id.rectitle);
            prv = itemView.findViewById(R.id.recpr);
            contv = itemView.findViewById(R.id.reccont);
            cart = itemView.findViewById(R.id.cart);
            tick = itemView.findViewById(R.id.tick);
            send = itemView.findViewById(R.id.send);

        }
    }
}
