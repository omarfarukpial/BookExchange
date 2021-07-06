package com.example.bookexchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<bookInfo> bookInfoList;

    private FirebaseAuth mAuth;
    private FirebaseDatabase dbase;
    private DatabaseReference dbRef;
    private FirebaseUser user;


    public MyAdapter(Context context, List<bookInfo> bookInfoList) {
        this.context = context;
        this.bookInfoList = bookInfoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.book_layout,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        bookInfo book = bookInfoList.get(position);
        holder.bookNameHome.setText(book.getBookName());
        holder.authorNameHome.setText(book.getAuthorName());
        holder.genreNameHome.setText(book.getGenreName());
        holder.isbnHome.setText(book.getIsbn());
        holder.userNameHome.setText(book.getUserNameHome());




        Picasso.get()
                .load(book.getImageURL())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerCrop()
                .into(holder.bookImageHome);


    }

    @Override
    public int getItemCount() {
        return bookInfoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  bookNameHome, authorNameHome, genreNameHome, isbnHome, userNameHome;
        ImageView bookImageHome;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bookNameHome = itemView.findViewById(R.id.bookNameHome);
            authorNameHome = itemView.findViewById(R.id.authorNameHome);
            genreNameHome = itemView.findViewById(R.id.genreNameHome);
            isbnHome = itemView.findViewById(R.id.isbnHome);
            bookImageHome = itemView.findViewById(R.id.bookImageHome);
            userNameHome = itemView.findViewById(R.id.userNameBook);


        }
    }
}
