package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePwd extends AppCompatActivity {

    private EditText et1,et2,et3;
    private Button b1;
    private TextView tv;
    private AttendanceDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        getSupportActionBar().setTitle("Change Password");
        db=new AttendanceDbHelper(this);
        et1=findViewById(R.id.editText);
        et2=findViewById(R.id.editText2);
        et3=findViewById(R.id.editText3);
        b1=findViewById(R.id.button);
        tv=findViewById(R.id.textView4);
        final String userid=getIntent().getStringExtra("userid");
        tv.setText("Welcome "+userid);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd=et1.getText().toString();
                if(db.validatepassword(userid,pwd)){
                    String npwd=et2.getText().toString();
                    String cpwd=et3.getText().toString();
                    if(npwd.equals(cpwd)) {
                        db.changePwd(userid, npwd);
                        Toast.makeText(ChangePwd.this, "Password Updated..", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(ChangePwd.this, "Confirm Password not match..", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ChangePwd.this, "Incorrect current password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
