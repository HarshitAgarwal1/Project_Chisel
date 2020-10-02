package com.example.projectchisel.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.projectchisel.AddPost.AddNutrition;
import com.example.projectchisel.AddPost.AddWorkout;
import com.example.projectchisel.Explore.Explore;
import com.example.projectchisel.FindBuddy.FindBuddy;
import com.example.projectchisel.Homepage.Homepage;
import com.example.projectchisel.Profile.Profile;
import com.example.projectchisel.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {

    public static final String TAG = "BottomNavigationViewHel" ;

    public static void setupBottomNavigationViewHelper(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationViewHelper ") ;
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNav(final Context context, BottomNavigationViewEx view){

        view.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_nav: //0
                    Intent intent = new Intent(context, Homepage.class);
                    context.startActivity(intent);

                    break;
                case R.id.explore_nav:  //1
                    Intent intent2 = new Intent(context, Explore.class);
                    context.startActivity(intent2);
                    break;
                case R.id.addpost_nav: //2
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context) ;
                    bottomSheetDialog.setContentView(R.layout.addpost_bottomsheet);
                    bottomSheetDialog.show();

                    final TextView addworkout = bottomSheetDialog.findViewById(R.id.addworkout) ;
                        addworkout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent addworkoutIntent = new Intent(context, AddWorkout.class);
                                context.startActivity(addworkoutIntent);
                            }
                        });

                    final TextView addrecipe = bottomSheetDialog.findViewById(R.id.addnutrition) ;
                    addrecipe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent addworkoutIntent = new Intent(context, AddNutrition.class);
                            context.startActivity(addworkoutIntent);
                        }
                    });
                    break;
                case R.id.profile_nav: //4
                    Intent intent4 = new Intent(context, Profile.class);
                    context.startActivity(intent4);
                    break;
                case R.id.findbuddy: //3
                    Intent intent5 = new Intent(context, FindBuddy.class);
                    context.startActivity(intent5);
                    break;

            }
            return false;
        });

    }


}
