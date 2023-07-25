package com.rohit.studentattendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class CourseActivity extends AppCompatActivity {

    private ListView lv;
    private AttendanceDbHelper db;
    private SimpleCursorAdapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setTitle("Classes");
        lv=findViewById(R.id.lv);
        db=new AttendanceDbHelper(this);

        Cursor c=db.allClasses();
        adp=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,c,
                new String[]{"cname"},new int[]{android.R.id.text1}, Adapter.IGNORE_ITEM_VIEW_TYPE);

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
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Add Class");
                final EditText et=new EditText(this);
                et.setHint("Enter Class Name");
                builder.setView(et);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.addClass(et.getText().toString());
                        Toast.makeText(CourseActivity.this, "Course Added..", Toast.LENGTH_SHORT).show();
                        Cursor c=db.allClasses();
                        adp.changeCursor(c);
                        onResume();
                    }
                });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
