package com.wmt.hardik;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wmt.hardik.model.UserList.User;

import java.util.ArrayList;
import java.util.List;



public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.ViewHolder>  {

    private Context context;
    private List<User> userList;


    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imageview;
        TextView username;
        TextView email;

        public ViewHolder(View view) {
            super(view);

            imageview = (RoundedImageView) view.findViewById(R.id.imageview);
            username = (TextView) view.findViewById(R.id.username);
            email = (TextView) view.findViewById(R.id.email);
        }

    }

    public UserAdapter(Activity context, ArrayList<User> arrayItemCart) {
        this.context = context;
        this.userList = arrayItemCart;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

       // cat_type=CartAdapter.cat_type;


        Glide.with(context)
                .load(userList.get(position).getProfilePic())
                .circleCrop()
                .into(holder.imageview);

        holder.username.setText(userList.get(position).getUsername());
        holder.email.setText(userList.get(position).getEmail());



    }



    @Override
    public int getItemCount() {
        return userList.size();
    }

}
