package com.example.projectchisel.Homepage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectchisel.Adapter.MessageAdapter;
import com.example.projectchisel.Model.Chat;
import com.example.projectchisel.Model.UserInfo;
import com.example.projectchisel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements MessageAdapter.OnItemClickListener {

    private String TAG = "ChatActivity";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = firebaseDatabase.getReference();
    private String userId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    MessageAdapter msgadap ;
    List<Chat> mChat ;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        Intent viewprofile = getIntent();
        String username = viewprofile.getStringExtra("username");
        String dp = viewprofile.getStringExtra("dp");
        String myId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        recyclerView = findViewById(R.id.chats) ;
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.child("user_info").getChildren()){
                    UserInfo info = ds.getValue(UserInfo.class);
                    ds.getKey();
                    assert info != null;
                    if(info.getUsername().contains(username)){
                        userId = ds.getKey();
                    }
                }
                readMessage(userId,myId,dp);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());

        CircleImageView userdp = findViewById(R.id.dp);
        Picasso.get().load(dp).into(userdp);

        TextView user = findViewById(R.id.profile_username);
        user.setText(username);

        EditText msg = findViewById(R.id.sendmsg);
        ImageView send = findViewById(R.id.sendbtn);

        send.setOnClickListener(v -> {
            String messagetxt = msg.getText().toString() ;
            if(!messagetxt.equals("")) {
                sendMessage(userId, messagetxt, myId);
                msg.setText("");
            }
        });


    }

    private void sendMessage(String userId, String messagetxt, String myId) {
        mRef = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", userId);
        hashMap.put("reciever", myId);
        hashMap.put("msg", messagetxt);
        mRef.child("chats").push().setValue(hashMap);
    }

    private void readMessage(String myid, String userId, String imageUrl){
        mChat = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for(DataSnapshot ds: dataSnapshot.child("chats").getChildren()){
                    Chat chat = ds.getValue(Chat.class);
                    if (chat != null) {
                        if(chat.getReciever().equals(myid) && chat.getSender().equals(userId)
                                ||chat.getReciever().equals(userId) && chat.getSender().equals(myid)){
                            mChat.add(chat);
                        }
                    }
                    msgadap = new MessageAdapter(mChat,imageUrl);
                    recyclerView.setAdapter(msgadap);
                    msgadap.setOnItemClickListner(ChatActivity.this);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(int postion) {
    }

    @Override
    public void onLongClick(int position) {
        Chat chat = mChat.get(position);

        Dialog mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.deletemsg);
        mDialog.show();

        TextView delete = mDialog.findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            Log.d(TAG, "Clicked");
            mRef = firebaseDatabase.getReference("chats");
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        Chat dschat = ds.getValue(Chat.class);
                        if (dschat != null) {
                            Log.d(TAG, "searching chats: " + dschat.toString() + "  " + chat.toString());

                            if (chat.equals(dschat)) {
                                Log.d(TAG, "found chat: " + ds.getValue() + "  " + chat.toString());
                                ds.getRef().removeValue();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    databaseError.toException().printStackTrace();
                }
            });
        });
    }
}
