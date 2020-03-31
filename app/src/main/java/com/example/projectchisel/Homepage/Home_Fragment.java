package com.example.projectchisel.Homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectchisel.Login;
import com.example.projectchisel.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Home_Fragment extends Fragment {
    public static final String TAG = "Home_Fragement" ;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_homepage_home,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        Button logout = Objects.requireNonNull(getView()).findViewById(R.id.logout);

        logout.setOnClickListener(v -> {
            mAuth.signOut();
            Objects.requireNonNull(getActivity()).finish();
            Intent logout1 = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), Login.class);
            startActivity(logout1);
        });
    }
}
