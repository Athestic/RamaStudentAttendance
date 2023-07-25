package com.rohit.studentattendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {

    private ListView lv;
    private AttendanceDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        getSupportActionBar().setTitle("Students ");
        lv=findViewById(R.id.lvstudents);
        db=new AttendanceDbHelper(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cc=db.allStudents();
        SimpleCursorAdapter adp=new SimpleCursorAdapter(this,
                R.layout.student_lv_item,
                cc,new String[]{"_id","fname","lname","cname","semno"},
                new int[]{R.id.tvsrno,R.id.tvsfname,R.id.tvslname,R.id.tvscname,R.id.tvssemno}, Adapter.IGNORE_ITEM_VIEW_TYPE);
        lv.setAdapter(adp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.m_course_add:
                Intent go=new Intent(StudentActivity.this,AddStudentActivity.class);
                startActivity(go);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
