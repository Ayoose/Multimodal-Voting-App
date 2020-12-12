package com.example.myvote;


import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LiveVoteActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<CustomCandidate> list;
    CandListAdapter adapter = null;
    SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_vote);

        lv = (ListView) findViewById(R.id.vote_list1);
        list = new ArrayList<>();
        adapter = new CandListAdapter(this, R.layout.my_row, list);

        lv.setAdapter(adapter);

        //get all data from sqlite
        Cursor cursor = sqLiteHelper.getData("select * from MyCandidates ");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ids = cursor.getString(1);
            String names = cursor.getString(2);
            String votes = cursor.getString(3);
            byte[] image = cursor.getBlob(4);

            list.add(new CustomCandidate(ids, names, votes, image, id));
        }
        adapter.notifyDataSetChanged();
    }
}
