package com.example.andrew.yalahwy.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.andrew.yalahwy.Activity.R;
import com.example.andrew.yalahwy.DataAccess.AuthDataAccess;
import com.example.andrew.yalahwy.DataAccess.UserDataAccess;
import com.example.andrew.yalahwy.Entity.Person;
import com.example.andrew.yalahwy.Entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.*;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostRecycle extends RecyclerView.Adapter<PostRecycle.ViewHolder>{

    public Context context;
    public List<Post> post_List_data;
    private Person person;
    private UserDataAccess userDataAccess;

    AuthDataAccess mAuth;
    public PostRecycle(List<Post> post_List_data){

        this.post_List_data = post_List_data;
        userDataAccess = new UserDataAccess();
        mAuth = new AuthDataAccess();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_post_layout,parent,false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(post_List_data.size() > 0){
        Post post = post_List_data.get(position);
        holder.setPost_desc(post.getPostDesc());
        holder.setPost_image(post.getPostImage(), post.getImageThumb());

        long milliseconds = post.getTimestamp().getTime();
        String dateString = DateFormat.format("MM/dd/yyyy", new Date(milliseconds)).toString();
        holder.setPost_date(dateString);
        userDataAccess.getUserInfo(post.getUserID(), holder);
        }
    }


    @Override
    public int getItemCount() {
        return post_List_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;



        private CircleImageView profile_image;
        private ImageView post_image;
        private TextView post_desc, profile_name,post_date;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setPost_image(String downloadUri, String thumbUri){

            post_image = mView.findViewById(R.id.post_image);

            RequestOptions requestOptions = new RequestOptions();
          //  requestOptions.placeholder(R.drawable.image_placeholder);

            Glide.with(context).load(downloadUri).into(post_image);
        }

        public void setPost_desc(String desc){
            post_desc = mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void setPost_date(String date){
            this.post_date = mView.findViewById(R.id.post_date);
            this.post_date.setText(date);
        }

        public void setProfile_name(String profileName){
            this.profile_name = mView.findViewById(R.id.profile_name);
            this.profile_name.setText(profileName);
        }

        public void setProfile_image(String downloadUri) {
            this.profile_image = mView.findViewById(R.id.profile_image);
            Glide.with(context).load(downloadUri).into(profile_image);
        }
    }

}
