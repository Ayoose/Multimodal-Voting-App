package com.example.myvote;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateCandActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextID, editTextName, editTextVotes, editTextPost;
    private TextView headerText;
    private Button createButton, selectImg, captureImg;
    private ImageView mImageView;
    private ProgressDialog mProgressDialog;

    final int REQUEST_CODE_GALLERY = 999;
    final int CAMERA_REQUEST_CODE = 1;

    //public static SQLiteHelper sqLiteHelper;
    SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cand);

        init();

        //sqLiteHelper = new SQLiteHelper(this, "MyCandidateDB.sqlite", null, 1);

        //sqLiteHelper.queryData("create table if not exists MyCandidates (sn integer primary key autoincrement, id number(2), name varchar(50), votes number(4), image blob)");

    }

    private void init() {
        headerText = (TextView) findViewById(R.id.headerText1);
        editTextID = (EditText) findViewById(R.id.editTextID1);
        editTextName = (EditText) findViewById(R.id.editTextName1);
        editTextVotes = (EditText) findViewById(R.id.editTextVotes1);
        createButton = (Button) findViewById(R.id.createBtn1);
        selectImg = (Button) findViewById(R.id.selectImg1);
        captureImg = (Button) findViewById(R.id.captureImg1);
        mImageView = (ImageView) findViewById(R.id.imgView1);

        mProgressDialog = new ProgressDialog(this);

        headerText.setText("Create Candidate");

        createButton.setOnClickListener(this);
        selectImg.setOnClickListener(this);
        captureImg.setOnClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            mImageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        if (view == selectImg) {
            ActivityCompat.requestPermissions(
                    CreateCandActivity.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY
            );
        }

        if (view == captureImg) {

            Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }

        if (view == createButton) {

            try {
                sqLiteHelper.insertData(
                        editTextID.getText().toString().trim(),
                        editTextName.getText().toString().trim(),
                        editTextVotes.getText().toString().trim(),
                        imageViewToByte(mImageView)
                );
                Toast.makeText(getApplicationContext(), "Candidate created successfully!", Toast.LENGTH_LONG).show();

                editTextID.setText("");
                editTextName.setText("");
                mImageView.setImageResource(R.mipmap.ic_launcher);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
