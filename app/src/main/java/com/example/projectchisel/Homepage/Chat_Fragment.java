package com.example.projectchisel.Homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectchisel.Adapter.UserAdapter;
import com.example.projectchisel.Explore.ViewProfile;
import com.example.projectchisel.Model.Chat;
import com.example.projectchisel.Model.User;
import com.example.projectchisel.Model.UserInfo;
import com.example.projectchisel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chat_Fragment extends Fragment implements UserAdapter.OnItemClickListener{
    public static final String TAG = "Chat_Fragement" ;
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    private FirebaseUser user;
    private DatabaseReference mRef;
    private List<String> usersList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage_sendchat,container, false);

        recyclerView = view.findViewById(R.id.chats);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        user = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("chats");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Chat chat = ds.getValue(Chat.class);

                    if (chat != null) {
                            if(chat.getSender().equals(user.getUid())){
                                usersList.add(chat.getReciever());
                            }

                            if(chat.getReciever().equals(user.getUid())) {
                                usersList.add(chat.getSender());
                            }
                        }
                    }

                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void readChats() {
        Context context = getContext();
        mUsers = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("user_info");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    UserInfo userInfo = ds.getValue(UserInfo.class);
                    User user = new User(userInfo, ds.getKey());

                    for(String id: usersList){
                        if(user.getUid().equals(id)){
                            if (mUsers.size()!= 0){
                                try {
                                    for (User user1 : mUsers) {
                                        if (!user.getUid().equals(user1.getUid())) {
                                            mUsers.add(user1);
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }else{
                                mUsers.add(user);
                            }
                        }
                    }
                }
                userAdapter = new UserAdapter((ArrayList<User>) mUsers);
                recyclerView.setAdapter(userAdapter);
                userAdapter.setOnItemClickListner(Chat_Fragment.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemClick(int postion) {
        String userid = mUsers.get(postion).getUid();
        Intent viewprofile = new Intent(getContext(), ViewProfile.class);
        viewprofile.putExtra("username", mUsers.get(postion).getUserInfo().getUsername());
        viewprofile.putExtra("desc", mUsers.get(postion).getUserInfo().getDescription());
        viewprofile.putExtra("dp", mUsers.get(postion).getUserInfo().getProfile_pic());
        viewprofile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(viewprofile);
    }

    @Override
    public void onDeleteClick(int position) {

    }
}
