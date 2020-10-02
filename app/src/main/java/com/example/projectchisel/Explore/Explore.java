package com.example.projectchisel.Explore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.projectchisel.Adapter.ExerciseAdapter;
import com.example.projectchisel.Adapter.UserAdapter;
import com.example.projectchisel.AddPost.AddWorkout;
import com.example.projectchisel.Homepage.Homepage;
import com.example.projectchisel.Model.User;
import com.example.projectchisel.Model.UserInfo;
import com.example.projectchisel.R;
import com.example.projectchisel.Utils.BottomNavigationViewHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.Objects;

public class Explore extends AppCompatActivity implements UserAdapter.OnItemClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = firebaseDatabase.getReference();
    public static final String TAG = "ExploreActivity" ;
    public static final int ACTIVITY_NUM = 1 ;
    private ArrayList<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        Log.d(TAG, "onCreate starting") ;
        setupBottomNavigationView() ;
        overridePendingTransition(0,0) ;

        RecyclerView searchE = findViewById(R.id.users);
        EditText searchBar = findViewById(R.id.search);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.equals("")) {
                    list = searchForResult(s.toString());

                    searchE.setHasFixedSize(true);
                    searchE.setVisibility(View.VISIBLE);

                    // use a linear layout manager
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Explore.this);
                    searchE.setLayoutManager(layoutManager);

                    UserAdapter userAdapter;
                    userAdapter = new UserAdapter(list);
                    searchE.setAdapter(userAdapter);
                    userAdapter.setOnItemClickListner(Explore.this);
                }else{
                    //Todo: Set Default View
                }
            }
        });

        searchBar.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    searchE.setVisibility(View.GONE);
                    return true;
                }
            }
            return false;
        });
    }

    private ArrayList<User> searchForResult(String username) {
        ArrayList<User>  userarray = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.child("user_info").getChildren()){
                    UserInfo info = ds.getValue(UserInfo.class);
                    ds.getKey();
                    assert info != null;
                    if(info.getUsername().contains(username)){
                        UserInfo userInfo = ds.getValue(UserInfo.class);
                        User user = new User(userInfo, ds.getKey());
                        userarray.add(user);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });

        return userarray;
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting Up Helper");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomnavviewbar);
        BottomNavigationViewHelper.setupBottomNavigationViewHelper(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNav(Explore.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem =  menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void onItemClick(int postion) {
        Log.d(TAG, "User Clicked: " + list.get(postion).toString());
        String userid = list.get(postion).getUid();
        Log.d(TAG, "User Id: " + userid);
        Intent viewprofile = new Intent(Explore.this, ViewProfile.class);
        viewprofile.putExtra("username", list.get(postion).getUserInfo().getUsername());
        viewprofile.putExtra("desc", list.get(postion).getUserInfo().getDescription());
        viewprofile.putExtra("dp", list.get(postion).getUserInfo().getProfile_pic());
//        viewprofile.putExtra("userid", userid);
        viewprofile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(viewprofile);
    }

    @Override
    public void onDeleteClick(int position) {
        Log.d(TAG, "Delete button click for " + list.get(position).getUserInfo().getUsername());
    }
}
