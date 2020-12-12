package com.example.myvote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText NewPassword;
    EditText ConfirmPassword;
    Button SaveButton;
    MyDb mdb;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        NewPassword = (EditText) findViewById(R.id.txtNewPassword);
        ConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        SaveButton = (Button) findViewById(R.id.saveBtn);

        SaveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == SaveButton) {
            if (NewPassword.getText().toString().equals(ConfirmPassword.getText().toString())) {
                mdb = new MyDb(this);
                db = mdb.getWritableDatabase();
                db.execSQL("Update administrator set password = ? where username = ?",
                        new String[]{NewPassword.getText().toString(), MyDb.username});
                startActivity(new Intent(this, AdminActivity.class));
            }
            else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            }
        }
    }
}
