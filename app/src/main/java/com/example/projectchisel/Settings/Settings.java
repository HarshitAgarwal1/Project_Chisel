package com.example.projectchisel.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.projectchisel.R;
import com.example.projectchisel.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class Settings extends AppCompatActivity {

    public static final String TAG = "SettingsActivity" ;
    public static final int ACTIVITY_NUM = 4 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate starting") ;
        setupBottomNavigationView() ;
        overridePendingTransition(0,0) ;
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
}


