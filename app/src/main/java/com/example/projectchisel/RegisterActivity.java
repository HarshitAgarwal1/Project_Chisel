package com.example.projectchisel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectchisel.Model.UserInfo;
import com.example.projectchisel.Model.User_Private;
import com.example.projectchisel.Utils.StringManipulation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String TAG = "RegisterActivity" ;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = mDatabase.getReference();
    private String append = "" ;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        TextView login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logina = new Intent(RegisterActivity.this, Login.class);
                startActivity(logina);
            }
        });

        EditText username = findViewById(R.id.nameregister);
        EditText email = findViewById(R.id.emailregister) ;
        EditText pass = findViewById(R.id.passwordregister) ;
        Button log = findViewById(R.id.signupbtn) ;

            log.setOnClickListener(v -> {
            if(email.getText().toString().matches("")){
                Toast.makeText(RegisterActivity.this, "Please Fill the Email", Toast.LENGTH_SHORT).show();
            }else if(pass.getText().toString().matches("")){
                Toast.makeText(RegisterActivity.this, "Please Fill the Password", Toast.LENGTH_SHORT).show();
            }else{
                loadingBar.setTitle("Loging In");
                loadingBar.setMessage("Please Wait While We are Checking Credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                LoginwithEmail(username.getText().toString(),email.getText().toString(), pass.getText().toString());
            }

        });



    }

    private void LoginwithEmail(final String username, String email, String password) {
        mRef = mDatabase.getReference();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        loadingBar.dismiss();
                        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String[] split = email.split("@");
                                String usern = split[0];
                                assert user != null;
                                addNewUser(email
                                        ,user.getUid()
                                        ,""
                                        , username
                                        , "R.drawable.ic_profile"
                                        , usern
                                        ,0
                                        ,0);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                loadingBar.dismiss();
                                Log.e(TAG, databaseError.getMessage());
                            }
                        });
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        updateUI(null);
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    void updateUI(FirebaseUser currentUser) {
        if (currentUser!= null){
            Intent intent = new Intent(RegisterActivity.this, Login.class);
            startActivity(intent);
        }
    }

    public void addNewUser(String email, String uid, String description, String name, String profile_pic, String username, int age, int height){
        User_Private user = new User_Private(email, uid, StringManipulation.condenseUsername(username));
        UserInfo userInfo = new UserInfo(description, name, profile_pic, StringManipulation.condenseUsername(username),age, height);

        mRef.child(String.valueOf(R.string.dbname_usersprivate))
                .child(uid)
                .setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "user registred to database", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "User Private Info: failed" + task.getException().getMessage());
                    }
                });


        mRef.child(String.valueOf(R.string.dbname_usersinfo))
                .child(uid)
                .setValue(userInfo)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "user registred to database", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "User Info: failed" + task.getException().getMessage());
                    }
                });

    }

}
