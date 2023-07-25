package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText et1,et2;
    private Button b1;
    private AttendanceDbHelper db;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    public String PREFS_NAME="myprefname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login Screen");

        et1=findViewById(R.id.etmuserid);
        et2=findViewById(R.id.etmpwd);
        b1=findViewById(R.id.blogin);
        db=new AttendanceDbHelper(this);
        sp=getSharedPreferences("users",MODE_PRIVATE);
        edit=sp.edit();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("User_Id",et1.getText().toString());
                editor.putString("Password",et2.getText().toString());
                editor.commit();
                String userid=et1.getText().toString();
                String pwd=et2.getText().toString();
                Cursor c=db.validate(userid,pwd);
                if(c.moveToNext()){
                    String status=c.getString(4);
                    if(status.equals("active")) {
                        String role = c.getString(3);
                        if (role.equals("admin")) {
                            Intent admin = new Intent(LoginActivity.this, MainActivity.class);
                            finish();
                            admin.putExtra("userid",c.getString(1));
                            startActivity(admin);
                        } else {
                            Intent teacher = new Intent(LoginActivity.this, TeacherDashboard.class);
                            edit.putString("userid",c.getString(1));
                            edit.commit();
                            finish();
                            startActivity(teacher);
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "User not active. Contact Administrator", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid username or password..!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void registerme(View v)
    {
        Intent go=new Intent(this,AddTeacherActivity.class);
        startActivity(go);
    }
}
