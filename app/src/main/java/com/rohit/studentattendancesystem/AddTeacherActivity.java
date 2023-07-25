package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Pattern;

public class AddTeacherActivity extends AppCompatActivity {

    private EditText et1,et2,et3,et4;
    private RadioGroup rg;
    private Button b1;
    String phone;
    private AttendanceDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        getSupportActionBar().setTitle("Teacher Registration");

        et1 = findViewById(R.id.ettname);
        et2 = findViewById(R.id.ettemail);
        rg = findViewById(R.id.rbtgender);
        et3 = findViewById(R.id.ettpwd);
        et4 = findViewById(R.id.etphone);
        b1 = findViewById(R.id.btnsaveteacher);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et1.getText().toString();
                String email = et2.getText().toString();
                RadioButton rb = findViewById(rg.getCheckedRadioButtonId());
                String gender = rb.getText().toString();
                String pwd = et3.getText().toString();
                db.addTeacher(name, email, gender);
                db.addUser(email, pwd);
                Toast.makeText(AddTeacherActivity.this, "Teacher Registered..!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
