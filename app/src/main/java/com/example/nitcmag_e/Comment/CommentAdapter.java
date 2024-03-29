package com.example.nitcmag_e.Comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitcmag_e.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>  {

    List<CommentModelClass> commentList;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    String articleId;

    public CommentAdapter(List<CommentModelClass> commentList, String articleId) {
        this.commentList = commentList;
        this.articleId = articleId;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        String id = commentList.get(position).getUserId();


        holder.commntText.setText(commentList.get(position).getCommnet());
        reference.child("User").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.commentUserName.setText(snapshot.child("name").getValue().toString());
                String img = snapshot.child("profilePictures").getValue().toString();

                if (!img.equalsIgnoreCase("null")) {
                    Picasso.get().load(img).into(holder.commentUserImage);
                }
                else
                {
                    Picasso.get().load(R.drawable.baseline_add_24).into(holder.commentUserImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView commntText,commentUserName;
        CircleImageView commentUserImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commntText = itemView.findViewById(R.id.commentText);
            commentUserImage = itemView.findViewById(R.id.circleImageViewCommentUser);
            commentUserName = itemView.findViewById(R.id.commentUserName);


        }
    }
}
