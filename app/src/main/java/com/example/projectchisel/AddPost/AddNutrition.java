package com.example.projectchisel.AddPost;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectchisel.R;

public class AddNutrition extends AppCompatActivity {

    public static final String TAG = "AddRecipeActivity" ;

    @Override  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_nutrition);
        Log.d(TAG, "onCreate starting") ;
        overridePendingTransition(0,0) ;
    }
}


