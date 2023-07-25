package com.rohit.studentattendancesystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AttendanceDbHelper extends SQLiteOpenHelper {
    public AttendanceDbHelper(@Nullable Context context) {
        super(context, "attendance.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_class(_id integer primary key autoincrement," +
                "cname varchar(100))");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_subject(_id integer primary key autoincrement," +
                "cname varchar(100),semno int, subname varchar(100))");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_student(_id int primary key," +
                "fname varchar(50),lname varchar(50),city varchar(20),gender varchar(10)," +
                "cname varchar(100),semno int)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_teacher(_id integer primary key autoincrement," +
                "tname varchar(100),email varchar(100),gender varchar(20))");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_subject_allot(_id integer primary key autoincrement," +
                "tname varchar(100),subname varchar(100))");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_attendance(_id integer primary key autoincrement," +
                "rollno int,subname varchar(100),attdate date,pr_ab char(1))");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_users(_id integer primary key autoincrement," +
                "userid varchar(100),password varchar(50),role varchar(20)," +
                "status varchar(10))");
        db.execSQL("INSERT INTO tbl_users(userid,password,role,status) " +
                "VALUES('admin','rohit','admin','active')");
        db.execSQL("INSERT INTO tbl_users(userid,password,role,status) " +
                "VALUES('rtg00112@gmail.com','rohit','admin','active')");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public Cursor allUnallotedSubjects(){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("SELECT * FROM tbl_subject " +
                "WHERE subname not in (SELECT subname FROM tbl_subject_allot)",null);
    }
    public boolean validatepassword(String userid,String pwd){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM tbl_users WHERE userid=? and password=?",
                new String[]{userid,pwd});
        return c.moveToNext();
    }
    public void changePwd(String userid,String pwd){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("password",pwd);
        db.update("tbl_users",cv,"userid=?",new String[]{userid});
    }
    public Cursor getattendance(String subname)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM tbl_attendance WHERE subname=? and pr_ab='P'",
                new String[]{subname});
        return c;
    }
    public boolean checkattendance(String subname,String doa){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM tbl_attendance WHERE subname=? and attdate=?",
                new String[]{subname,doa});
        return c.moveToNext();
    }

    public int getPresentAttendance(String subname,String rollno){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT count(*) FROM tbl_attendance WHERE subname=? and rollno=? and pr_ab='P'",
                new String[]{subname,rollno});
        c.moveToFirst();
        return c.getInt(0);
    }
    public int getTotalAttendance(String subname){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT distinct(attdate) FROM tbl_attendance WHERE subname=?",
                new String[]{subname});
        //c.moveToFirst();
        return c.getCount();//c.getInt(0);
    }
    public void markattendance(String rollno,String subname,String doa,boolean pr_ab)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("rollno",rollno);
        cv.put("subname",subname);
        cv.put("attdate",doa);
        cv.put("pr_ab", pr_ab ? "P":"A");
        db.insert("tbl_attendance",null,cv);
    }
    public void addClass(String cname){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("cname",cname);
        db.insert("tbl_class",null,cv);
    }
    public Cursor allClasses(){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("SELECT * FROM tbl_class",null);
    }
    public void addSubject(String cname,String semno,String subname){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("cname",cname);
        cv.put("semno",semno);
        cv.put("subname",subname);
        db.insert("tbl_subject",null,cv);
    }
    public Cursor allSubjects(){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("SELECT * FROM tbl_subject",null);
    }
    public void deleteSubject(String subname){
        SQLiteDatabase db=getWritableDatabase();
        db.delete("tbl_subject","subname=?",new String[]{subname});
    }
    public void addStudent(String rollno,String fname,String lname,String city,String gender,String cname,String semno){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("_id",rollno);
        cv.put("fname",fname);
        cv.put("lname",lname);
        cv.put("city",city);
        cv.put("gender",gender);
        cv.put("cname",cname);
        cv.put("semno",semno);
        db.insert("tbl_student",null,cv);
    }
    public Cursor allStudents(){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("SELECT * FROM tbl_student",null);
    }
    public Cursor findCourse(String subname){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("SELECT cname,semno FROM tbl_subject where subname=?",new String[]{subname});
    }
    public Cursor allSubStudents(String subname){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cc=findCourse(subname);
        cc.moveToFirst();
        String cname=cc.getString(0);
        String semno=cc.getString(1);
        return db.rawQuery("SELECT * FROM tbl_student where cname=? and semno=?",new String[]{cname,semno});
    }
    public void addTeacher(String tname,String email,String gender){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("tname",tname);
        cv.put("email",email);
        cv.put("gender",gender);
        db.insert("tbl_teacher",null,cv);
    }
    public Cursor allTeachers(){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("SELECT * FROM tbl_teacher",null);
    }
    public void addUser(String userid,String pwd){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("userid",userid);
        cv.put("password",pwd);
        cv.put("role","teacher");
        cv.put("status","inactive");
        db.insert("tbl_users",null,cv);
    }
    public Cursor findteacher(String userid)
    {
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("SELECT * FROM tbl_teacher where email=?"
                ,new String[]{userid});
    }
    public Cursor allUsers(){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("SELECT * FROM tbl_users",null);
    }
    public void updateStatus(String userid)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("status","active");
        db.update("tbl_users",cv,"userid=?",new String[]{userid});
    }
    public Cursor validate(String userid,String pwd){
        SQLiteDatabase  db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM tbl_users WHERE userid=? and password=?",
                new String[]{userid,pwd});
        return c;
    }
    public Cursor allallotedsubs(String tname){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("SELECT * FROM tbl_subject_allot where tname=?",
                new String[]{tname});
    }
    public void allotsubs(String tname,String subname){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("tname",tname);
        cv.put("subname",subname);
        db.insert("tbl_subject_allot",null,cv);
    }
    public void unallotsubs(String subname){
        SQLiteDatabase db=getWritableDatabase();
        db.delete("tbl_subject_allot","subname=?",
                new String[]{subname});
    }
}