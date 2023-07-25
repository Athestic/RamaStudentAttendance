package com.rohit.studentattendancesystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentAttend extends AppCompatActivity {
    private ListView lv;
    private AttendanceDbHelper db;
    private List<AttendModel> list;
    private String subname;
    private String doa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attend);
        db=new AttendanceDbHelper(this);
        lv=findViewById(R.id.lvstds);
        subname=getIntent().getStringExtra("subname");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
        doa=getIntent().getStringExtra("doa");
        try {
            Date dd = sdf.parse(doa);
            doa=new SimpleDateFormat("yyyy-MM-dd").format(dd);
        }catch(Exception ex){
        }
        list=getList(subname);
        CustomAdapter adp=new CustomAdapter(list);
        lv.setAdapter(adp);
    }

    private class CustomAdapter extends BaseAdapter
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vv=getLayoutInflater().inflate(R.layout.attend_layout,parent,false);
            TextView tv1=vv.findViewById(R.id.textView5);
            TextView tv2=vv.findViewById(R.id.textView6);
            TextView tv3=vv.findViewById(R.id.textView7);
            CheckBox cb=vv.findViewById(R.id.checkBox);
            tv1.setText(list.get(position).getRollno());
            tv2.setText(list.get(position).getFname());
            tv3.setText(list.get(position).getLname());
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(position).setPresent(isChecked);
                }
            });
            return vv;
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.attend_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.msave)
        {
            for(AttendModel sa : list)
            {
                Log.d("anand", "onOptionsItemSelected: "+sa.getRollno()+" "+sa.isPresent());
                db.markattendance(sa.getRollno(),subname,doa,sa.isPresent());
            }
            Toast.makeText(this, "Attendance Marked", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
