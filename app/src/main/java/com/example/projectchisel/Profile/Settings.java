package com.example.projectchisel.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectchisel.Login;
import com.example.projectchisel.Model.UserInfo;
import com.example.projectchisel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {

    /*
    TODO: Edit Profile
        Convert everything into Recycler View Items
     */
    public static final String TAG = "SettingsActivity" ;

    private String uid ;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = firebaseDatabase.getReference();
    private int age;
    private Uri mImageUri ;
    private int PICK_IMAGE_REQUEST = 1;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    public Settings() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate starting") ;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

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
//            EditText agex = findViewById(R.id.agex);
//
//            try{
//                if (agex != null) {
//
//                    age = Integer.parseInt(agex.getText().toString());
//                }
//            }catch (NumberFormatException e){
//                e.printStackTrace();
//            }
//            EditText height = findViewById(R.id.heightinput);
            CircleImageView dp = findViewById(R.id.profile_pic);
            Button save = findViewById(R.id.savebtn);

            TextView changedp = findViewById(R.id.profile_picchange);
            changedp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFileChooser();
                }
            });


            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    UserInfo info = RetrieveAccountUsername(dataSnapshot);

//                  Log.d(TAG, String.valueOf(info.getAge())+ info.getHeight() + info.getDescription());
                    fullname.setText(info.getName());
                    desc.setText(info.getDescription());
//                    age = info.getAge();
//                    agex.setText(age);
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
                if(mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(this, "Task In Progress", Toast.LENGTH_SHORT).show();
                }else {

                    mRef.child("user_info")
                            .child(uid)
                            .child("description")
                            .setValue(desc.getText().toString());

                    mRef.child("user_info")
                            .child(uid)
                            .child("name")
                            .setValue(fullname.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Settings.this, "Database Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Settings.this, "Failure", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, Objects.requireNonNull(task.getException()).toString());
                                }
                            });

                    uploadFile();
                }
//                mRef.child("user_info")
//                        .child(uid)
//                        .child("age")
//                        .setValue(agex.getText().toString());
            });
        });

//  Logout
        TextView logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            mAuth.signOut();
            finish();
            Intent logout1 = new Intent(this, Login.class);
            logout1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            logout1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logout1);
        });

    }

    private void uploadFile() {
        if(mImageUri != null){
            ProgressBar progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            mStorageRef.child(uid+"."+getFileExtension(mImageUri)).delete();
            StorageReference file = mStorageRef.child(uid+"."+getFileExtension(mImageUri));
            mUploadTask = file.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> progressBar.setProgress(0), 2000);

                        Log.d(TAG, "Image Uploaded to firebase");
                        // Get a URL to the uploaded content
                        file.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful()){
                                    String downloadUrl  = task.getResult().toString();
                                    mRef.child("user_info")
                                            .child(uid)
                                            .child("profile_pic")
                                            .setValue(downloadUrl);
                                }else{
                                    Log.e(TAG, task.getException().getMessage());
                                }
                            }
                        });

                        progressBar.setProgress(0);
                        progressBar.setVisibility(View.GONE);
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);
                        }
                    });
        }else{
            Toast.makeText(this, "No profile pic selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gallery, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){
            mImageUri = data.getData();
            CircleImageView dp = findViewById(R.id.profile_pic);
            Picasso.get().load(mImageUri).into(dp);
        }
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


