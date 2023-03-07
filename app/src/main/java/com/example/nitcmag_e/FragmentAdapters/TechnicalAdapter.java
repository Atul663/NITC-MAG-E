package com.example.nitcmag_e.FragmentAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitcmag_e.ModelClass;
import com.example.nitcmag_e.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class TechnicalAdapter extends RecyclerView.Adapter<TechnicalAdapter.ViewHolder> {

    List<ModelClass> articleList;
    Context articleContext;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    public TechnicalAdapter(List<ModelClass> articleList) {
        this.articleList = articleList;
//        this.articleContext = articleContext;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_with_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String id = articleList.get(position).getId();
        String category = articleList.get(position).getCategory();

        if(category.equalsIgnoreCase("Technical")) {
            reference.child("Article").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String title = snapshot.child("title").getValue().toString();
                    String desc = snapshot.child("description").getValue().toString();

                    holder.articelTitle.setText(title);
                    holder.articleDesc.setText(desc);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (int i = 0; i < articleList.size(); i++)
        {
            if(articleList.get(i).getCategory().equalsIgnoreCase("Technical"))
            {
                count++;
            }
        }
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView articelTitle,articleDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            articelTitle = itemView.findViewById(R.id.textViewTitleCard);
            articleDesc = itemView.findViewById(R.id.textViewDescription);
        }
    }

}

