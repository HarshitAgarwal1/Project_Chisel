package com.example.projectchisel.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectchisel.Model.UserInfo;
import com.example.projectchisel.R;
import com.example.projectchisel.Utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    public static final String TAG = "ProfileActivity" ;
    public static final int ACTIVITY_NUM = 3 ;
    private FirebaseAuth mAuth;
    private String uid ;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate starting") ;
        mAuth = FirebaseAuth.getInstance() ;
        setupBottomNavigationView() ;
        overridePendingTransition(0,0) ;

        if(mAuth.getCurrentUser() != null){
            uid = mAuth.getCurrentUser().getUid();
        }

        TextView username = findViewById(R.id.profile_username);
        TextView desc = findViewById(R.id.desc);
        CircleImageView dp = findViewById(R.id.profile_pic);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfo info = RetrieveAccountUsername(dataSnapshot);
                username.setText(info.getUsername());
                desc.setText(info.getDescription());
                if (info.getProfile_pic() != null){
                    Picasso.get().load(info.getProfile_pic()).into(dp);
                }else{
                    dp.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(Profile.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private UserInfo RetrieveAccountUsername(DataSnapshot dataSnapshot) {
        Log.d(TAG, "Retrieving Account Details");
        UserInfo info = new UserInfo();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            if(ds.getKey().equals("user_info")){
                info = (Objects.requireNonNull(ds.child(uid).getValue(UserInfo.class)));
                return info;
            }
        }
        return info;
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting Up Helper");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomnavviewbar);
        BottomNavigationViewHelper.setupBottomNavigationViewHelper(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNav(Profile.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem =  menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


}


