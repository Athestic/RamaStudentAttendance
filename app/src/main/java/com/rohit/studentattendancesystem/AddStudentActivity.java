package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AddStudentActivity extends AppCompatActivity {

    private EditText et1,et2,et3,et4,et5,et6;
    private Spinner spcourse;
    private Button b1;
    private RadioGroup rg;

    private AttendanceDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        getSupportActionBar().setTitle("Add New Module");
        db=new AttendanceDbHelper(this);

        et1=findViewById(R.id.etrollno);
        et2=findViewById(R.id.etfname);
        et3=findViewById(R.id.etlname);
        et4=findViewById(R.id.etcity);
        et5=findViewById(R.id.etssemno);
        spcourse=findViewById(R.id.spccname);
        b1=findViewById(R.id.bstsave);
        rg=findViewById(R.id.rbtgender);

        final Cursor c=db.allClasses();
        SimpleCursorAdapter cadp=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,c,
                new String[]{"cname"},new int[]{android.R.id.text1}, Adapter.IGNORE_ITEM_VIEW_TYPE);
        spcourse.setAdapter(cadp);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rollno=et1.getText().toString();
                String fname=et2.getText().toString();
                String lname=et3.getText().toString();
                String city=et4.getText().toString();
                String semno=et5.getText().toString();
                RadioButton rb=findViewById(rg.getCheckedRadioButtonId());
                String gender=rb.getText().toString();
                c.moveToPosition(spcourse.getSelectedItemPosition());
                String course=c.getString(1);
                db.addStudent(rollno,fname,lname,city,gender,course,semno);
                Toast.makeText(AddStudentActivity.this, "Student Added", Toast.LENGTH_SHORT).show();
                et1.setText("");et2.setText("");et3.setText("");et4.setText("");
                et5.setText("");et1.requestFocus();
            }
        });

    }
}
