package com.example.myvote;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//import static com.example.myvote.CreateCandActivity.sqLiteHelper;

public class CastVoteActivity extends AppCompatActivity implements View.OnClickListener{

    ListView lv;
    EditText choice;
    Button castBtn;

    ArrayList<CustomCandidate> list;
    ChoiceListAdapter adapter = null;

    SQLiteHelper sqLiteHelper = new SQLiteHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);

        lv = (ListView) findViewById(R.id.choose_listCast);
        choice = (EditText) findViewById(R.id.choiceCast);
        castBtn = (Button) findViewById(R.id.castVoteCast);


        castBtn.setOnClickListener(this);
        castBtn.setEnabled(false);
        choice.addTextChangedListener(textWatcher);

        list = new ArrayList<>();
        adapter = new ChoiceListAdapter(this, R.layout.cand_row, list);

        lv.setAdapter(adapter);

        Cursor cursor = sqLiteHelper.getData("select * from MyCandidates ");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ids = cursor.getString(1);
            String names = cursor.getString(2);
            byte[] image = cursor.getBlob(4);

            list.add(new CustomCandidate(ids, names, image, id));
        }
        adapter.notifyDataSetChanged();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String choiceText = choice.getText().toString().trim();
            castBtn.setEnabled(!choiceText.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onClick(View view) {

        if (view == castBtn) {

            SQLiteHelper sqLiteHelper = new SQLiteHelper(this);

            //db.execSQL("insert into candidates " + choice + ",'" + names + "'" + cast);
            String myChoice = choice.getText().toString();
            sqLiteHelper.queryData("update MyCandidates set votes = votes + 1 where id = " + myChoice);
            //sqLiteDatabase.execSQL("update MyCandidates set votes = votes + 1 where id = " + myChoice);
            Toast.makeText(this, "You have voted successfully!", Toast.LENGTH_LONG).show();
            castBtn.setEnabled(false);
        }
    }
}
