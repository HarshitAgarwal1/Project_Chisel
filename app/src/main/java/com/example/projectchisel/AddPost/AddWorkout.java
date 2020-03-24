package com.example.projectchisel.AddPost;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

public class AddWorkout extends AppCompatActivity {

    /*TODO:
       Spotify PlayerAPI integration (REDIRECT URL Error)
       Search Feature + Workouts Json Array Called (JSoup Web Scraped)
       Add Exercise

     */


    public static final String TAG = "AddWorkoutActivity" ;
//    private static final String CLIENT_ID = "c68f0be424294d89a82fe63f329950df";
//    private static final String REDIRECT_URI = "https://developer.spotify.com";
//    private SpotifyAppRemote mSpotifyAppRemote;
//    private int PlayerState ;
//    private Connector.ConnectionListener mConnectionListener;

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
//                PlayerState = 0 ;
//                connected(PlayerState);
            }
        });

        playWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseWorkout.setVisibility(View.VISIBLE);
                endWorkout.setVisibility(View.INVISIBLE);
                playWorkout.setVisibility(View.INVISIBLE);
//                PlayerState = 1 ;
//                connected(PlayerState);
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


//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        ConnectionParams connectionParams =
//                new ConnectionParams.Builder(CLIENT_ID)
//                        .setRedirectUri(REDIRECT_URI)
//                        .showAuthView(true)
//                        .build();
//
//        SpotifyAppRemote.connect(this, connectionParams,
//                new Connector.ConnectionListener() {
//
//                    @Override
//                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
//                        mSpotifyAppRemote = spotifyAppRemote;
//                        Log.d(TAG, "Connected! Yay!");
//                        connected(1);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable throwable) {
//                        Log.d(TAG, "Spotify Error");
//                        Log.e(TAG, throwable.getMessage(), throwable);
//                        // Something went wrong when attempting to connect! Handle errors here
//
//                    }
//                });
//
//    }
//
//    private void connected(int playerState) {
//
//        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");
//
////        switch (playerState) {
////            case 0:
////                mSpotifyAppRemote.getPlayerApi().pause();
////                break;
////            case 1:
////
////                break;
////            case 2:
////                mSpotifyAppRemote.getPlayerApi().resume();
////                break;
////        }
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
//    }

}


