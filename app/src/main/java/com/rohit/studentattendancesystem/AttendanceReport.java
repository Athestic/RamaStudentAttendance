package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AttendanceReport extends AppCompatActivity {

    private ListView lv;
    private AttendanceDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);
        lv=findViewById(R.id.lv_report);
        getSupportActionBar().setTitle("Attendence Report");
        db=new AttendanceDbHelper(this);
        final Cursor cc=db.allSubjects();
        SimpleCursorAdapter adp=new SimpleCursorAdapter(this,R.layout.lv_course_item,cc,
                new String[]{"cname","semno","subname"},
                new int[]{R.id.tvcname,R.id.tvsemno,R.id.tvsubname}, Adapter.IGNORE_ITEM_VIEW_TYPE);
        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cc.moveToPosition(position);
                String sname=cc.getString(3);
                Intent report=new Intent(getApplicationContext(),StudentSubjectReport.class);
                report.putExtra("subname",sname);
                startActivity(report);
            }
        });
    }
}
