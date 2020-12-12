package com.example.myvote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button LiveButton;
    private Button ProceedButton;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ProceedButton = (Button) findViewById(R.id.proceedBtn);
        LiveButton = (Button) findViewById(R.id.liveBtn);
        tv = (TextView) findViewById(R.id.dtext);
        tv.setText(MyDb.username);

        ProceedButton.setOnClickListener(this);
        LiveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == LiveButton) {
            startActivity(new Intent(this, LiveVoteActivity.class));
        }

        if (view == ProceedButton) {
            //startActivity(new Intent(this, VotingActivity.class));
            startActivity(new Intent(this, CastVoteActivity.class));
        }
    }
}
