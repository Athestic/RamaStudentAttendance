package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentSubjectReport extends AppCompatActivity {

    private ListView lv;
    private AttendanceDbHelper db;
    private List<AttendModel> list;
    private static String subname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);
        lv=findViewById(R.id.lv_report);
        db=new AttendanceDbHelper(this);
        subname=getIntent().getStringExtra("subname");
        list=getList(subname);
        CustomAdapter adp=new CustomAdapter(list);
        lv.setAdapter(adp);
    }

    private List<AttendModel> getList(String subname){
        List<AttendModel> list=new ArrayList<>();
        Cursor cc=db.allSubStudents(subname);
        while(cc.moveToNext())
        {
            String rollno=cc.getString(0);
            String fname=cc.getString(1);
            String lname=cc.getString(2);
            AttendModel sa=new AttendModel(rollno,fname,lname);
            list.add(sa);
        }
        return list;
    }



    class CustomAdapter extends BaseAdapter
    {
        private List<AttendModel> list;

        public CustomAdapter(List<AttendModel> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vv=getLayoutInflater().inflate(R.layout.attend_report_layout,parent,false);
            TextView tv1=vv.findViewById(R.id.textView5);
            TextView tv2=vv.findViewById(R.id.textView6);
            TextView tv3=vv.findViewById(R.id.textView7);
            TextView tv4=vv.findViewById(R.id.textView8);
            String rollno=list.get(position).getRollno();
            tv1.setText(rollno);
            tv2.setText(list.get(position).getFname());
            tv3.setText(list.get(position).getLname());
            int count= db.getPresentAttendance(subname,rollno);
            int total=db.getTotalAttendance(subname);
            tv4.setText(count+"/"+total);
            return vv;
        }
    }
}
