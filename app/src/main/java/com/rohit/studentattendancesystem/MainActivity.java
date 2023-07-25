package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GridView gv;
    private int pics[];
    private String modules[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Admin Dashboard");
        gv=findViewById(R.id.gvmain);
     modules =new String[]{"Classes","Subjects","Students","Teachers",
                "Attendance\nReport","Users","Change\nPassword","Logout"};
        pics =new int[]{R.drawable.classes,
        R.drawable.subs,
        R.drawable.students,
        R.drawable.teachers,
        R.drawable.attendancemarked,
        R.drawable.users,
        R.drawable.changepass,
        R.drawable.check_out};
        CustomAdapter adp =new  CustomAdapter();
     // ArrayAdapter<String> adp=new ArrayAdapter<>(this,
        gv.setAdapter(adp);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent go=null;
                switch(position){
                    case 0:
                        go=new Intent(MainActivity.this,CourseActivity.class);
                        break;
                    case 1:
                        go=new Intent(MainActivity.this,SubjectActivity.class);
                        break;
                    case 2:
                        go=new Intent(MainActivity.this,StudentActivity.class);
                        break;
                    case 3:
                        go=new Intent(MainActivity.this,TeacherActivity.class);
                        break;
                    case 4:
                        go=new Intent(MainActivity.this,AttendanceReport.class);
                        break;
                    case 5:
                        go=new Intent(MainActivity.this,UsersActivity.class);
                        break;
                    case 6:
                        //change password
                        go=new Intent(MainActivity.this,ChangePwd.class);
                        go.putExtra("userid",getIntent().getStringExtra("userid"));
                        break;
                    case 7:
                        go=new Intent(MainActivity.this,LoginActivity.class);
                        finish();
                        break;
                }
                startActivity(go);
            }
        });
    }
    class CustomAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return modules.length;
        }

        @Override
        public Object getItem(int position) {
            return modules[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vv=getLayoutInflater().inflate(R.layout.main_gv_item,parent,false);
            TextView tv=vv.findViewById(R.id.tvtitle);
            ImageView iv=vv.findViewById(R.id.ivtitle);
            tv.setText(modules[position]);
            iv.setImageResource(pics[position]);
            return vv;
        }
    }
}
