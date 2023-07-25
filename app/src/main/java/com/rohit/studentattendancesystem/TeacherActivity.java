package com.rohit.studentattendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TeacherActivity extends AppCompatActivity {

    private ListView lv;
    private AttendanceDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        getSupportActionBar().setTitle("Teachers ");
        lv=findViewById(R.id.lvteachers);
        db=new AttendanceDbHelper(this);
        final Cursor cc=db.allTeachers();
        SimpleCursorAdapter adp=new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cc,new String[]{"tname","email"},
                new int[]{android.R.id.text1,android.R.id.text2}, Adapter.IGNORE_ITEM_VIEW_TYPE);
        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cc.moveToPosition(position);
                String tname=cc.getString(1);
                Intent allot=new Intent(TeacherActivity.this,SubjectAllotActivity.class);
                allot.putExtra("tname",tname);
                startActivity(allot);
            }
        });
    }
}
