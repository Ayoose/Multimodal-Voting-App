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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtemaillogin;
    private EditText txtpwd;
    private Button Login;
    private TextView txtAdmin;
    private ProgressDialog progressDialog;
    MyDb mdb;
    SQLiteDatabase db;

    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MobileAds.initialize(this, "ca-app-pub-2797276862641376~4701320276");
        //Unit "2797276862641376/7223985999"
        //test id "3940256099942544~3347511713"
        //test unit "3940256099942544/6300978111"
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice("45ABC4583261F256EA55C30C7F69E644").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2797276862641376/5719332638");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener()
                                      {
                                          @Override
                                          public void onAdClosed() {
                                              startActivity(new Intent(MainActivity.this, AdminActivity.class));
                                              mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                          }
                                      }
        );

        txtemaillogin = (EditText) findViewById(R.id.emaillog);
        txtpwd = (EditText) findViewById(R.id.pwdlog);
        Login = (Button) findViewById(R.id.btnlog);
        txtAdmin = (TextView) findViewById(R.id.adminlog);

        progressDialog = new ProgressDialog(this);

        Login.setOnClickListener(this);
        txtAdmin.setOnClickListener(this);
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
        db = mdb.getWritableDatabase();
        Cursor cur = db.rawQuery("select * from users where username = ? and password = ?",
                new String[]{txtemaillogin.getText().toString(),txtpwd.getText().toString()});


        if (cur.getCount() == 0 ) {

            Toast.makeText(this, "Invalid User", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
        else {
            progressDialog.dismiss();
            MyDb.username = txtemaillogin.getText().toString();
            startActivity(new Intent(this, HomeActivity.class));
        }
        cur.close();
    }
    @Override

    public void onClick(View view) {
        if (view == Login) {
            loginUser();
        }
        if (view == txtAdmin) {
            if(mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                startActivity(new Intent(this, AdminActivity.class));
            }
        }
    }
}
