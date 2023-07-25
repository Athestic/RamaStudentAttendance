package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class UsersActivity extends AppCompatActivity {

    private ListView lv;
    private AttendanceDbHelper db;
    private SimpleCursorAdapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        lv=findViewById(R.id.lv_users);
        getSupportActionBar().setTitle("Users");
        db=new AttendanceDbHelper(this);
        final Cursor cc=db.allUsers();
        AlertDialog.Builder builder=new AlertDialog.Builder(UsersActivity.this);
        builder.setTitle("Information");
        builder.setMessage("Long Press User record to activate it.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
        adp=new SimpleCursorAdapter(this,
                R.layout.teacher_lv_item,
                cc,new String[]{"userid","role","status"},
                new int[]{R.id.tvsfname2,R.id.tvsemail2,R.id.tvslname2}, Adapter.IGNORE_ITEM_VIEW_TYPE);
        lv.setAdapter(adp);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cc.moveToPosition(position);
                final String tname=cc.getString(1);
                AlertDialog.Builder builder=new AlertDialog.Builder(UsersActivity.this);
                builder.setTitle("Activate User");
                builder.setMessage("Do you want to activate the user ?");
                builder.setPositiveButton("Activate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.updateStatus(tname);
                        Toast.makeText(UsersActivity.this, "User Activated..", Toast.LENGTH_SHORT).show();
                        Cursor c=db.allUsers();
                        adp.changeCursor(c);
                        onResume();
                    }
                });
                builder.show();
                return false;
            }
        });
    }


}
