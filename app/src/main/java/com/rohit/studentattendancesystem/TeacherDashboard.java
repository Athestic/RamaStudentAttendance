package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class TeacherDashboard extends AppCompatActivity {

    private TextView tv,tv2;
    private GridView gv;
    private String modules[];
    private int pics[];
    private AttendanceDbHelper db;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        db=new AttendanceDbHelper(this);
        sp=getSharedPreferences("users",MODE_PRIVATE);
        tv=findViewById(R.id.tvwelcome);
        tv2=findViewById(R.id.tvttuserid);
        gv=findViewById(R.id.gvteacher);

        modules=new String[]{"Subjects","Mark Attendance","Attendence Report","Change Password","Logout"};
        pics=new int[]{R.drawable.subs,
                R.drawable.attendence,
                R.drawable.attendancemarked,
                R.drawable.changepass,
                R.drawable.check_out};
        final String userid=sp.getString("userid","guest");
        tv2.setText(userid);
        Cursor teacher=db.findteacher(userid);
        teacher.moveToNext();
        final String tname=teacher.getString(1);
        tv.setText("Welcome ! "+tname);

        CustomAdapter adp=new CustomAdapter();
        gv.setAdapter(adp);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Intent alloted=new Intent(TeacherDashboard.this,AllotedSubjects.class);
                        alloted.putExtra("tname",tname);
                        startActivity(alloted);
                        break;
                    case 1:
                        Intent attend=new Intent(TeacherDashboard.this,MarkAttendance.class);
                        attend.putExtra("tname",tname);
                        attend.putExtra("userid",userid);
                        startActivity(attend);
                        break;
                    case 2:
                        Intent mark=new Intent(TeacherDashboard.this,AttendanceReport.class);
                        startActivity(mark);
                        break;
                    case 3:
                        Intent change=new Intent(TeacherDashboard.this,ChangePwd.class);
                        change.putExtra("userid",userid);
                        startActivity(change);
                        break;
                    case 4:
                        startActivity(new Intent(TeacherDashboard.this,LoginActivity.class));
                        SharedPreferences.Editor edit=sp.edit();
                        edit.remove("userid");
                        edit.commit();
                        finish();
                        break;
                }
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
