package com.example.projectchisel.Homepage;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectchisel.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Home_Fragment extends Fragment {
    public static final String TAG = "Home_Fragement" ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_homepage_home,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        TextView showdata = view.findViewById(R.id.corona);

        Dialog mDialog = new Dialog(view.getContext());
        mDialog.setContentView(R.layout.coronavirus);
        mDialog.setCanceledOnTouchOutside(false);
        showdata.setOnClickListener(v -> {
            mDialog.show();
            loadData(mDialog);
        });

        ImageView close = mDialog.findViewById(R.id.close);
        close.setOnClickListener(v -> mDialog.dismiss());

//        Button logout = Objects.requireNonNull(getView()).findViewById(R.id.logout);
//
//        logout.setOnClickListener(v -> {
//            mAuth.signOut();
//            Objects.requireNonNull(getActivity()).finish();
//            Intent logout1 = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), Login.class);
//            logout1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            logout1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(logout1);
//        });



//
    }

    private void loadData(Dialog mDialog) {
        OkHttpClient client = new OkHttpClient();
        Request worldstat = new Request.Builder()
                .url("https://coronavirus-monitor.p.rapidapi.com/coronavirus/worldstat.php")
                .get()
                .addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "584921dfe4msh4b6d36372ae6d2ep1eddf5jsnb9a7409de887")
                .build();

        client.newCall(worldstat).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResp = response.body().string();

                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                        Log.d(TAG, "Response:" + myResp);
                        try {
                            JSONObject obj = new JSONObject(myResp);
                            String worldc = obj.getString("total_cases");
                            TextView world = mDialog.findViewById(R.id.casesworld);
                            world.setText(worldc);

                            world.setOnTouchListener((v, event) -> {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        try {
                                            String detailsworld ="Deaths: "+ obj.getString("total_deaths") +
                                                    "\n" + "Recovered: "+ obj.getString("total_recovered") ;
                                            world.setText(detailsworld);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        break;
                                    case MotionEvent.ACTION_UP:
                                        world.setText(worldc);
                                        break;
                                }
                                return true;
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });

        Request request = new Request.Builder()
                .url("https://coronavirus-monitor.p.rapidapi.com/coronavirus/latest_stat_by_country.php?country=India")
                .get()
                .addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "584921dfe4msh4b6d36372ae6d2ep1eddf5jsnb9a7409de887")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    String myResp = response.body().string();

                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                        Log.d(TAG, "Response:" + myResp);
                        try {
                            JSONObject obj = new JSONObject(myResp);
                            String indiac = obj.getJSONArray("latest_stat_by_country").getJSONObject(0).getString("total_cases");
                            TextView india = mDialog.findViewById(R.id.indiacases);
                            TextView indialabel = mDialog.findViewById(R.id.totalcasesindialabel);
                            india.setText(indiac);
                            india.setOnTouchListener((v, event) -> {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        try {
                                            String detailsindia ="Deaths: "+ obj.getJSONArray("latest_stat_by_country").getJSONObject(0).getString("total_deaths") +
                                                    "\n" + "Recovered: "+ obj.getJSONArray("latest_stat_by_country").getJSONObject(0).getString("total_recovered") ;
                                            india.setText(detailsindia);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        break;
                                    case MotionEvent.ACTION_UP:
                                        india.setText(indiac);
                                        break;
                                }
                                return true;
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });

    }
}
