package com.example.myvote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtemaillogin;
    private EditText txtpwd;
    private Button Login;
    private ProgressDialog progressDialog;
    MyDb mdb;
    SQLiteDatabase db;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        txtemaillogin = (EditText) findViewById(R.id.emaillog);
        txtpwd = (EditText) findViewById(R.id.pwdlog);
        Login = (Button) findViewById(R.id.btnlog);

        progressDialog = new ProgressDialog(this);

        Login.setOnClickListener(this);

    }

    private void loginUser() {
        String authEmail = txtemaillogin.getText().toString().trim();
        String authPassword = txtpwd.getText().toString().trim();

        if (TextUtils.isEmpty(authEmail)) {
            // username is empty
            Toast.makeText(this, "Email Address field is empty", Toast.LENGTH_SHORT).show();
            //stopping the execution
            return;
        }

        if (TextUtils.isEmpty(authPassword)) {
            // password is empty
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
            // stopping the execution
            return;
        }

        // if validations are okay
        // show a progress bar
        progressDialog.setMessage("Signing the user...");
        progressDialog.show();

        mdb = new MyDb(this);
        db = mdb.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from administrator where username = ? and password = ?",
                new String[]{txtemaillogin.getText().toString(),txtpwd.getText().toString()});

        if (cur.getCount() == 0 ) {

            Toast.makeText(this, "Invalid User", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
        else {
            progressDialog.dismiss();
            MyDb.username = txtemaillogin.getText().toString();
            startActivity(new Intent(this, AdminHomeActivity.class));
        }

    }

    @Override
    public void onClick(View view) {
        if (view == Login) {
            loginUser();
        }
    }
}
