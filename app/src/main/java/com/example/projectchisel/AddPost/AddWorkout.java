package com.example.projectchisel.AddPost;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.projectchisel.Adapter.AddExerciseAdapter;
import com.example.projectchisel.Adapter.ExerciseAdapter;
import com.example.projectchisel.Homepage.Homepage;
import com.example.projectchisel.Model.Exercise;
import com.example.projectchisel.R;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddWorkout extends AppCompatActivity implements ExerciseAdapter.OnItemClickListener {

    /*TODO:
       Spotify PlayerAPI integration (REDIRECT URL Error)
     */


    public static final String TAG = "AddWorkoutActivity";
    private LinearLayoutManager layoutManager;
//    private static final String CLIENT_ID = "c68f0be424294d89a82fe63f329950df";
//    private static final String REDIRECT_URI = "https://developer.spotify.com";
//    private SpotifyAppRemote mSpotifyAppRemote;
//    private int PlayerState ;
//    private Connector.ConnectionListener mConnectionListener;

    private ArrayList<String> list ;
    private ArrayList<Exercise> exerciseList;
    private int countN;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workout);
        Log.d(TAG, "onCreate starting");
        overridePendingTransition(0, 0);

        final ImageView endWorkout = findViewById(R.id.endworkout);
        TextView date = findViewById(R.id.date);
        final ImageView playWorkout = findViewById(R.id.playworkout);
        final ImageView pauseWorkout = findViewById(R.id.pauseworkout);
        RecyclerView searchE = findViewById(R.id.searchlist);
        EditText searchBar = findViewById(R.id.exerciseSearch);


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.equals("")) {
                    list = searchForResult(s.toString());

                    searchE.setHasFixedSize(true);
                    searchE.setVisibility(View.VISIBLE);

                    // use a linear layout manager
                    layoutManager = new LinearLayoutManager(AddWorkout.this);
                    searchE.setLayoutManager(layoutManager);

                    ExerciseAdapter exerciseAdapter;
                    exerciseAdapter = new ExerciseAdapter(list);
                    searchE.setAdapter(exerciseAdapter);
                    exerciseAdapter.setOnItemClickListner(AddWorkout.this);
                }
            }
        });

        searchBar.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    searchE.setVisibility(View.GONE);
                    return true;
                }
            }
            return false;
        });
//Exercise List
//        RecyclerView exerciseView = findViewById(R.id.recyclerviewExercise);
//        exerciseView.setHasFixedSize(true);
//        exerciseView.setVisibility(View.VISIBLE);
//
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(AddWorkout.this);
//        exerciseView.setLayoutManager(layoutManager);
//        AddExerciseAdapter addexerciseAdapter;
//        addexerciseAdapter = new AddExerciseAdapter(exerciseList);
//        exerciseView.setAdapter(addexerciseAdapter);
//        addexerciseAdapter.setOnItemClickListner((AddExerciseAdapter.OnItemClickListener) AddWorkout.this);

        LocalDate todate = LocalDate.now();
        String formattedDate = todate.format(DateTimeFormatter.ofPattern("dd-MM-YY"));

        LocalTime today = LocalTime.now();
        String timeString = today.format(DateTimeFormatter.ofPattern("HH:mm"));

        date.setText(formattedDate + " - " + timeString);

        pauseWorkout.setOnClickListener(v -> {
            pauseWorkout.setVisibility(View.INVISIBLE);
            playWorkout.setVisibility(View.VISIBLE);
            endWorkout.setVisibility(View.VISIBLE);
//                PlayerState = 0 ;
//                connected(PlayerState);
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

        endWorkout.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    endWorkout.setImageResource(R.drawable.ic_crop_square_black_24dp);
                    YoYo.with(Techniques.RotateIn)
                            .duration(500)
                            .repeat(1)
                            .playOn(findViewById(R.id.endworkout));
                    break;
                case MotionEvent.ACTION_UP:
                    endWorkout.setImageResource(R.drawable.ic_stop_black_24dp);
                    break;
            }
            return false;
        });


    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("exercises.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void hideSoftKeyboard() {

        if (getCurrentFocus() != null) {
            Context context;
            InputMethodManager ipm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            ipm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<String> searchForResult(String keyword) {
        Log.d(TAG, "searchForMatch: searching for a match: " + keyword);
        ArrayList<String> exercisesList = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("exercises");
            ArrayList<HashMap<String, String>> exerciseList = new ArrayList<>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String exercise = jo_inside.getString("exercise");
                String muscle = jo_inside.getString("muscle");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<>();
                m_li.put("exercise", exercise);
                m_li.put("muscle", muscle);
                exerciseList.add(m_li);

            }

            for (int j = 0; j < exerciseList.size(); j++){
                String en = exerciseList.get(j).get("exercise");
                    if(en.contains(keyword)){
                       exercisesList.add(en);
                    }
            }
            return exercisesList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exercisesList ;
    }

    @Override
    public void onItemClick(int postion) {
        Log.d(TAG, list.get(postion));
        Dialog mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.addexercise_layout);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        TextView count = mDialog.findViewById(R.id.count);
        ImageView add = mDialog.findViewById(R.id.add);
        ImageView minus = mDialog.findViewById(R.id.minus);
        TextView stop = mDialog.findViewById(R.id.stop);
        TextView start = mDialog.findViewById(R.id.start);
        Chronometer time = mDialog.findViewById(R.id.time);

        if(count != null){
        countN = Integer.parseInt(count.getText().toString());

            start.setOnClickListener(v -> {
                time.start();
            });


            add.setOnClickListener(v -> {
                countN += 10 ;
                count.setText(String.valueOf(countN));
            });

            minus.setOnClickListener(v -> {
                countN -= 10 ;
                count.setText(String.valueOf(countN));
            });

            stop.setOnClickListener(v -> {
                mDialog.dismiss();
                TextView count1 = mDialog.findViewById(R.id.count);
                time.stop();
//                    Exercise exercise = new Exercise(list.get(postion), "","R.drawable.com_facebook_button_like_background",Integer.valueOf(count.getText().toString()),0 );
//                    exerciseList.add(exercise);
            });
        }
    }

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


