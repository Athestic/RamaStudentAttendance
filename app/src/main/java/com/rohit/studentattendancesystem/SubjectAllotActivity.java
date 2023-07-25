package com.rohit.studentattendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SubjectAllotActivity extends AppCompatActivity {

    private AttendanceDbHelper db;
    private ListView lv;
    private SimpleCursorAdapter adp;
    private String tname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_allot);
        getSupportActionBar().setTitle("Subject Allot Module");

        db=new AttendanceDbHelper(this);
        lv=findViewById(R.id.lvmapsub);

        tname=getIntent().getStringExtra("tname");
        final Cursor c=db.allallotedsubs(tname);
        adp=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,c,
                new String[]{"tname","subname"},new int[]{android.R.id.text1,android.R.id.text2},
                Adapter.IGNORE_ITEM_VIEW_TYPE);
        lv.setAdapter(adp);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                c.moveToPosition(position);
                final String subname=c.getString(2);
                AlertDialog.Builder builder=new AlertDialog.Builder(SubjectAllotActivity.this);
                builder.setTitle("Revoke Subject");
                builder.setMessage("Do you want to revoke this subject ?");
                builder.setPositiveButton("Revoke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.unallotsubs(subname);
                        Toast.makeText(getApplicationContext(), "Subject revoked..", Toast.LENGTH_SHORT).show();
                        Cursor c=db.allallotedsubs(tname);
                        adp.changeCursor(c);
                        onResume();
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.m_course_add:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Allot Subject");
                final Spinner sp=new Spinner(this);
                final Cursor csp=db.allUnallotedSubjects();
                SimpleCursorAdapter spadp=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,csp,
                        new String[]{"subname"},new int[]{android.R.id.text1}, Adapter.IGNORE_ITEM_VIEW_TYPE);
                sp.setAdapter(spadp);
                builder.setView(sp);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        csp.moveToPosition(sp.getSelectedItemPosition());
                        String subname=csp.getString(3);
                        db.allotsubs(tname,subname);
                        Cursor c=db.allallotedsubs(tname);
                        adp.changeCursor(c);
                        onResume();
                    }
                });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
