package com.example.projectchisel.Homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.projectchisel.R;
import com.example.projectchisel.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class Homepage extends AppCompatActivity {

    public static final String TAG = "HomeActivity" ;
    public static final int ACTIVITY_NUM = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Log.d(TAG, "onCreate starting") ;
        setupBottomNavigationView() ;
        overridePendingTransition(0,0) ;
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting Up Helper");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomnavviewbar);
        BottomNavigationViewHelper.setupBottomNavigationViewHelper(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNav(Homepage.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem =  menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
    }


