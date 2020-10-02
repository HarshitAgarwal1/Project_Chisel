package com.example.projectchisel.Adapter;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectchisel.Model.Chat;
import com.example.projectchisel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    public static final int MSG_TYPE_LEFT = 0 ;
    public static final int MSG_TYPE_RIGHT = 1 ;
    private List<Chat> chat ;
    private OnItemClickListener mListener ;
    private FirebaseUser mUser;
    private String imageurl;

    public interface OnItemClickListener{
        void onItemClick(int postion);
        void onLongClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListener listener){
        mListener = listener;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView dp;
        public TextView msgtext;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            dp = itemView.findViewById(R.id.dpmsg);
            msgtext = itemView.findViewById(R.id.msgtext);

            itemView.setOnClickListener(v -> {
                if(mListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        mListener.onItemClick(position);
                    }
                }
            });

            itemView.setOnLongClickListener(v -> {
                if(mListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        mListener.onLongClick(position);
                    }
                }
                return false;
            });


        }
    }

    public MessageAdapter(List<Chat> chat, String imageurl) {
        this.chat = chat;
        this.imageurl = imageurl;
    }

    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == MSG_TYPE_LEFT){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_msg_layout, parent, false);
            return new MessageAdapter.MessageViewHolder(v);
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_msg_layout, parent, false);
            return new MessageAdapter.MessageViewHolder(v);
        }

    }
    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        Chat chat1 = chat.get(position);

        holder.msgtext.setText(chat1.getMsg());
        Picasso.get().load(imageurl).into(holder.dp);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return chat.size();
    }

    @Override
    public int getItemViewType(int position) {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chat.get(position).getSender().equals(mUser.getUid())){
            return MSG_TYPE_LEFT;
        }else{
            return MSG_TYPE_RIGHT;
        }
    }
}