package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AllotedSubjects extends AppCompatActivity {

    private ListView lv;
    private AttendanceDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alloted_subjects);
        getSupportActionBar().setTitle("Subjects Alloted");

        lv=findViewById(R.id.lvallotedsubs);
        db=new AttendanceDbHelper(this);
        String tname=getIntent().getStringExtra("tname");
        Cursor c=db.allallotedsubs(tname);
        SimpleCursorAdapter adp=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,
                c,new String[]{"subname"},
                new int[]{android.R.id.text1}, Adapter.IGNORE_ITEM_VIEW_TYPE);
        lv.setAdapter(adp);
    }
}
