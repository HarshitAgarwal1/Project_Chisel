package com.example.projectchisel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectchisel.Model.User;
import com.example.projectchisel.Model.UserInfo;
import com.example.projectchisel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<User> mDataset;
    private OnItemClickListener mListener ;

    public interface OnItemClickListener{
        void onItemClick(int postion);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListener listener){
        mListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class UserViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView username;
        public TextView name;
        public ImageView profilepic;
        public ImageView mDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.profile_username);
            name = itemView.findViewById(R.id.nameregister);
            profilepic = itemView.findViewById(R.id.profile_pic);
            mDelete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(v -> {
                if(mListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        mListener.onItemClick(position);
                    }
                }
            });

            mDelete.setOnClickListener(v -> {
                if(mListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        mListener.onDeleteClick(position);
                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserAdapter(ArrayList<User> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usersearch_item, parent, false);
        UserViewHolder vh = new UserViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        UserInfo info = mDataset.get(position).getUserInfo();

        Picasso.get().load(info.getProfile_pic()).into(holder.profilepic);
        holder.username.setText(info.getUsername());
        holder.name.setText(info.getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}