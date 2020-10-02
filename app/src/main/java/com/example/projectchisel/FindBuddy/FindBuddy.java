package com.example.projectchisel.FindBuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.projectchisel.R;
import com.example.projectchisel.Utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class FindBuddy extends AppCompatActivity {

    public static final int ACTIVITY_NUM = 3 ;
    public static final String TAG = "FindBuddyActivity" ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_buddy);
        Log.d(TAG, "onCreate starting") ;
        mAuth = FirebaseAuth.getInstance() ;
        setupBottomNavigationView() ;
        overridePendingTransition(0,0) ;

    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting Up Helper");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomnavviewbar);
        BottomNavigationViewHelper.setupBottomNavigationViewHelper(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNav(FindBuddy.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem =  menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
