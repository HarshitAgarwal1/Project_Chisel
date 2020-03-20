package com.example.projectchisel.AddPost;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectchisel.R;
import com.example.projectchisel.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class AddWorkout extends AppCompatActivity {

    public static final String TAG = "AddWorkoutActivity" ;

    @Override  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workout);
        Log.d(TAG, "onCreate starting") ;
        overridePendingTransition(0,0) ;
    }
}


