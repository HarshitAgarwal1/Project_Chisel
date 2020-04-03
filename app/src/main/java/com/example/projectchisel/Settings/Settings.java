package com.example.projectchisel.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectchisel.Homepage.SectionsPagerAdapter;
import com.example.projectchisel.Model.UserInfo;
import com.example.projectchisel.Model.User_Private;
import com.example.projectchisel.Profile.Profile;
import com.example.projectchisel.R;
import com.example.projectchisel.Utils.BottomNavigationViewHelper;
import com.example.projectchisel.Utils.StringManipulation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {

    /*
    TODO: Edit Profile
        Convert everything into Recycler View Items
     */
    public static final String TAG = "SettingsActivity" ;
    public static final int ACTIVITY_NUM = 4 ;
    private FirebaseAuth mAuth;
    private String uid ;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = firebaseDatabase.getReference();

    public Settings() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate starting") ;
        mAuth = FirebaseAuth.getInstance() ;
        setupBottomNavigationView() ;
        overridePendingTransition(0,0) ;

        if(mAuth.getCurrentUser() != null){
            uid = mAuth.getCurrentUser().getUid();
        }

// Account Settings
        TextView ac = findViewById(R.id.account);
        ac.setOnClickListener(v -> {
            setContentView(R.layout.layout_edit_profile);
            EditText fullname = findViewById(R.id.fullnameinput);
            EditText desc = findViewById(R.id.descinput);
//            EditText height = findViewById(R.id.heightinput);
            CircleImageView dp = findViewById(R.id.profile_pic);
            Button save = findViewById(R.id.savebtn);

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    UserInfo info = RetrieveAccountUsername(dataSnapshot);

                    Log.d(TAG, String.valueOf(info.getAge())+ info.getHeight() + info.getDescription());
                    fullname.setText(info.getName());
                    desc.setText(info.getDescription());
//                    agevalue[0] = info.getAge();
//                    age.setText(info.getAge());
//                    height.setText(info.getHeight());
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
                    Toast.makeText(Settings.this, "Failed to load post.",
                            Toast.LENGTH_SHORT).show();
                }
            });

            save.setOnClickListener(v1 -> {

                mRef.child("user_info")
                        .child(uid)
                        .child("description")
                        .setValue(desc.getText().toString());
                mRef.child("user_info")
                        .child(uid)
                        .child("name")
                        .setValue(fullname.getText().toString())
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()) {
                            Toast.makeText(Settings.this, "Database Updated", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Settings.this, "Failure", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, Objects.requireNonNull(task.getException()).toString());
                        }
                        });

            });

        });
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting Up Helper");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomnavviewbar);
        BottomNavigationViewHelper.setupBottomNavigationViewHelper(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNav(Settings.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem =  menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private UserInfo RetrieveAccountUsername(DataSnapshot dataSnapshot) {
        Log.d(TAG, "Retrieving Account Details");
        UserInfo info = new UserInfo();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            if(ds.getKey().equals("user_info")){
                info = (Objects.requireNonNull(ds.child(uid).getValue(UserInfo.class)));
                Log.d(TAG, "User Info: " + info.toString());
                return info;
            }
        }
        return info;
    }



}


