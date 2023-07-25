package com.rohit.studentattendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class SubjectActivity extends AppCompatActivity {

    private ListView lv;
    private AttendanceDbHelper db;
    private SimpleCursorAdapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        lv=findViewById(R.id.lv2);
        getSupportActionBar().setTitle("Subjects");
        db=new AttendanceDbHelper(this);
        final Cursor cc=db.allSubjects();
        adp=new SimpleCursorAdapter(this,R.layout.lv_course_item,cc,
                new String[]{"cname","semno","subname"},
                new int[]{R.id.tvcname,R.id.tvsemno,R.id.tvsubname},Adapter.IGNORE_ITEM_VIEW_TYPE);
        lv.setAdapter(adp);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cc.moveToPosition(position);
                String sname=cc.getString(3);
                db.deleteSubject(sname);
                Cursor c2=db.allSubjects();
                adp.changeCursor(c2);
                adp.notifyDataSetChanged();
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
                builder.setTitle("Add Subject");
                final View vv=getLayoutInflater().inflate(R.layout.add_course,null,false);
                final Spinner sp=vv.findViewById(R.id.spclass);
                final Cursor c=db.allClasses();
                SimpleCursorAdapter cadp=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,c,
                        new String[]{"cname"},new int[]{android.R.id.text1}, Adapter.IGNORE_ITEM_VIEW_TYPE);
                sp.setAdapter(cadp);
                builder.setView(vv);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText semno=vv.findViewById(R.id.etsemno);
                        EditText subname=vv.findViewById(R.id.etsubname);
                        c.moveToPosition(sp.getSelectedItemPosition());
                        String cname=c.getString(1);
                        db.addSubject(cname,semno.getText().toString(),subname.getText().toString());
                        Toast.makeText(SubjectActivity.this, "Subject "+cname, Toast.LENGTH_SHORT).show();
                        Cursor c2=db.allSubjects();
                        adp.changeCursor(c2);
                        onResume();
                    }
                });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
