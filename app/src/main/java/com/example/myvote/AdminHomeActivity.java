package com.example.myvote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener{

    Button changeButton;
    private Button LiveButton;
    private Button CandidateButton;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        changeButton = (Button) findViewById(R.id.changePasswordBtn);
        CandidateButton = (Button) findViewById(R.id.candidateBtn);
        LiveButton = (Button) findViewById(R.id.liveBtn);
        tv = (TextView) findViewById(R.id.dtext);
        tv.setText(MyDb.username);

        CandidateButton.setOnClickListener(this);
        LiveButton.setOnClickListener(this);
        changeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == changeButton) {
            startActivity(new Intent(this, ChangePasswordActivity.class));
        }
        if (view == LiveButton) {
            //startActivity(new Intent(this, LiveActivity.class));
            startActivity(new Intent(this, LiveVoteActivity.class));
        }
        if (view == CandidateButton) {
            //startActivity(new Intent(this, CreateActivity.class));
            startActivity(new Intent(this, CreateCandActivity.class));
        }
    }
}
