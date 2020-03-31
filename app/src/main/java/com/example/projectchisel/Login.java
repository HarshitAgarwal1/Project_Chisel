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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectchisel.Homepage.Homepage;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


//TODO: Login Activity

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity" ;
    private FirebaseAuth mAuth;
    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    private ProgressDialog loadingBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialise Facebook Auth
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_login);

        loadingBar = new ProgressDialog(this) ;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        EditText email = findViewById(R.id.emailinput) ;
        EditText pass = findViewById(R.id.passwordinput) ;
        Button log = findViewById(R.id.loginbtn) ;
        log.setOnClickListener(v -> {
            if(email.getText().toString().matches("")){
                Toast.makeText(Login.this, "Please Fill the Email", Toast.LENGTH_SHORT).show();
            }else if(pass.getText().toString().matches("")){
                Toast.makeText(Login.this, "Please Fill the Password", Toast.LENGTH_SHORT).show();
            }else{
                loadingBar.setTitle("Loging In");
                loadingBar.setMessage("Please Wait While We are Checking Credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                LoginwithEmail(email.getText().toString(), pass.getText().toString());
            }

        });


        //Register Button
        TextView register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            Intent register1 = new Intent(Login.this, RegisterActivity.class);
            startActivity(register1);
        });

        callbackManager = CallbackManager.Factory.create();
        Button loginButton = findViewById(R.id.fbloginbtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setTitle("Logging In Via Facebook");
                loadingBar.setMessage("Please Wait While We are Checking Credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList(EMAIL));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                                handleFacebookAccessToken(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel() {
                                Log.d(TAG, "facebook:onCancel");
                                // ...
                            }

                            @Override
                            public void onError(FacebookException error) {
                                Log.d(TAG, "facebook:onError", error);
                                // ...
                            }
                        });
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        // ...
                    }
                });

    }


    public void LoginwithEmail(String email, String pass){
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            loadingBar.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            loadingBar.dismiss();
                        }

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
            Intent intent = new Intent(Login.this, Homepage.class);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,
                resultCode, data);
    }

}
