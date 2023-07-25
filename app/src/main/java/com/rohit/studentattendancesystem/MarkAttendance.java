package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MarkAttendance extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private AttendanceDbHelper db;
    private Spinner sp;
    private TextView tv;
    private Button b;
    private String doa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        getSupportActionBar().setTitle("Mark Attendance");
        db=new AttendanceDbHelper(this);
        sp=findViewById(R.id.spinner);
        tv=findViewById(R.id.tvdoattend);
        b=findViewById(R.id.bstartattend);
        String tname=getIntent().getStringExtra("tname");
        final SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
        doa=sdf.format(new Date());
        tv.setText(doa);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                DatePickerDialog dp = new DatePickerDialog(MarkAttendance.this,
                        android.R.style.Theme_Holo_Dialog,
                        MarkAttendance.this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });
        final Cursor c=db.allallotedsubs(tname);
        SimpleCursorAdapter adp=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,
                c,new String[]{"subname"},
                new int[]{android.R.id.text1}, Adapter.IGNORE_ITEM_VIEW_TYPE);
        sp.setAdapter(adp);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.moveToPosition(sp.getSelectedItemPosition());
                String subname=c.getString(2);
                try {
                    Date dd = sdf.parse(tv.getText().toString());
                    doa=new SimpleDateFormat("yyyy-MM-dd").format(dd);
                }catch(Exception ex){
                }
                //Toast.makeText(MarkAttendance.this, "subject "+subname+" date "+doa, Toast.LENGTH_SHORT).show();
                if(db.checkattendance(subname,doa)){
                    Toast.makeText(MarkAttendance.this, "Already marked..", Toast.LENGTH_SHORT).show();
                }else {
                    Intent mark = new Intent(MarkAttendance.this, StudentAttend.class);
                    mark.putExtra("doa", doa);
                    mark.putExtra("subname", subname);
                    finish();
                    startActivity(mark);
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
        Calendar cal=Calendar.getInstance();
        cal.set(year,month,dayOfMonth);
        tv.setText(sdf.format(cal.getTime()));
    }
}
