package com.example.projectchisel.Explore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectchisel.Homepage.ChatActivity;
import com.example.projectchisel.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView username = findViewById(R.id.profile_username);
        TextView desc = findViewById(R.id.desc);
        CircleImageView dp = findViewById(R.id.profile_pic);

        Button msg = findViewById(R.id.send_message);
        msg.setVisibility(View.VISIBLE);

        Intent viewprofile = getIntent();
        String user = viewprofile.getStringExtra("username");
        String userdp = viewprofile.getStringExtra("dp");

        username.setText(user);
        desc.setText(viewprofile.getStringExtra("desc"));
        Picasso.get().load(userdp).into(dp);

        View bottomnav = findViewById(R.id.bottomnav);
        bottomnav.setVisibility(View.GONE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendmsg = new Intent(ViewProfile.this, ChatActivity.class);
                sendmsg.putExtra("username", user);
                sendmsg.putExtra("dp", userdp);
                startActivity(sendmsg);
            }
        });

    }


}
