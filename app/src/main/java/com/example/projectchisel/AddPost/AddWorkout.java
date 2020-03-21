package com.example.projectchisel.AddPost;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.projectchisel.Homepage.Homepage;
import com.example.projectchisel.R;
import com.example.projectchisel.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddWorkout extends AppCompatActivity {

    public static final String TAG = "AddWorkoutActivity" ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workout);
        Log.d(TAG, "onCreate starting") ;
        overridePendingTransition(0,0) ;

        final ImageView endWorkout = findViewById(R.id.endworkout);
        TextView date = findViewById(R.id.date) ;
        final ImageView playWorkout = findViewById(R.id.playworkout);
        final ImageView pauseWorkout = findViewById(R.id.pauseworkout);


        LocalDate todate = LocalDate.now();
        String formattedDate = todate.format(DateTimeFormatter.ofPattern("dd-MM-YY"));

        LocalTime today = LocalTime.now();
        String timeString = today.format(DateTimeFormatter.ofPattern("HH:mm"));

        date.setText(formattedDate + " - " + timeString);


        pauseWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseWorkout.setVisibility(View.INVISIBLE);
                playWorkout.setVisibility(View.VISIBLE);
                endWorkout.setVisibility(View.VISIBLE);
            }
        });

        playWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseWorkout.setVisibility(View.VISIBLE);
                endWorkout.setVisibility(View.INVISIBLE);
                playWorkout.setVisibility(View.INVISIBLE);
            }
        });

        endWorkout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(AddWorkout.this, Homepage.class);
                startActivity(intent);
                return true;
            }
        });

        endWorkout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    endWorkout.setImageResource(R.drawable.ic_crop_square_black_24dp);
                    YoYo.with(Techniques.RotateIn)
                                .duration(500)
                                .repeat(1)
                                .playOn(findViewById(R.id.endworkout));
                        break;
                    case MotionEvent.ACTION_UP:
                    endWorkout.setImageResource(R.drawable.ic_stop_black_24dp);
                        break ;
                }
                return false;
            }
        });
    }
}


