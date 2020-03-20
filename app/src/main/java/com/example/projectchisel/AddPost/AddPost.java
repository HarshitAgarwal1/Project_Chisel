package com.example.projectchisel.AddPost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.projectchisel.R;
import com.example.projectchisel.Utils.BottomNavigationViewHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class AddPost extends AppCompatActivity {

    public static final String TAG = "AddPostActivity" ;
    public static final int ACTIVITY_NUM = 2 ;

    @Override  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_addpost);
        Log.d(TAG, "onCreate starting") ;
//        setupBottomNavigationView() ;
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AddPost.this) ;
//        bottomSheetDialog.setContentView(R.layout.activity_addpost);
//        bottomSheetDialog.show();
        overridePendingTransition(0,0) ;
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting Up Helper");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomnavviewbar);
        BottomNavigationViewHelper.setupBottomNavigationViewHelper(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNav(AddPost.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem =  menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}


