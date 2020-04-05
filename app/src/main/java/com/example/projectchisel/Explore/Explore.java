package com.example.projectchisel.Explore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.projectchisel.Adapter.ExerciseAdapter;
import com.example.projectchisel.AddPost.AddWorkout;
import com.example.projectchisel.Homepage.Homepage;
import com.example.projectchisel.R;
import com.example.projectchisel.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class Explore extends AppCompatActivity {

    public static final String TAG = "ExploreActivity" ;
    public static final int ACTIVITY_NUM = 1 ;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        Log.d(TAG, "onCreate starting") ;
        setupBottomNavigationView() ;
        overridePendingTransition(0,0) ;

        RecyclerView searchE = findViewById(R.id.posts);
        EditText searchBar = findViewById(R.id.search);

//        searchBar.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                if(!s.equals("")) {
//                    list = searchForResult(s.toString());
//
//                    searchE.setHasFixedSize(true);
//                    searchE.setVisibility(View.VISIBLE);
//
//                    // use a linear layout manager
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(Explore.this);
//                    searchE.setLayoutManager(layoutManager);
//
//                    ExerciseAdapter exerciseAdapter;
//                    exerciseAdapter = new ExerciseAdapter(list);
//                    searchE.setAdapter(exerciseAdapter);
//                    userAdapter.setOnItemClickListner(Explore.this);
//                }
//            }
//        });
//
//        searchBar.setOnKeyListener((v, keyCode, event) -> {
//            if (event.getAction() == KeyEvent.ACTION_DOWN)
//            {
//                if (keyCode == KeyEvent.KEYCODE_BACK){
//                    searchE.setVisibility(View.GONE);
//                    return true;
//                }
//            }
//            return false;
//        });
//
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting Up Helper");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomnavviewbar);
        BottomNavigationViewHelper.setupBottomNavigationViewHelper(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNav(Explore.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem =  menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}


