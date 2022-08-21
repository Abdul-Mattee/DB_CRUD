package com.example.databasecrud_39;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String STUDENT_ID = "StudentID";
    public static final String STUDENT_NAME = "StudentName";
    public static final String STUDENT_ROLL = "StudentRollNumber";
    public static final String STUDENT_ENROLL = "IsEnrolled";
    public static final String STUDENT_TABLE = "StudentTable";
    Context c;


    public DBHelper(@Nullable Context context) {
        super(context, "MyDB.db", null, 1);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTableSTatementOne = "CREATE TABLE CustTable(CustomerID Integer PRIMARY KEY AUTOINCREMENT, " + CUSTOMER_NAME_FIRST + " Text, CustomerAge Int, ActiveCustomer BOOL) ";
        String createTableSTatement = "CREATE TABLE " + STUDENT_TABLE + "(" +
                STUDENT_ID + " Integer PRIMARY KEY AUTOINCREMENT, " + STUDENT_NAME + " Text, "
                + STUDENT_ROLL + " Int, " + STUDENT_ENROLL + " BOOL) ";
        db.execSQL(createTableSTatement);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        onCreate(db);
    }
    public void CheckIfStudentExists(int rollNumber) {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + " Where " + STUDENT_ROLL + "=" + rollNumber, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            StudentModel studentFound = new StudentModel(cursor.getString(0),cursor.getInt(1), cursor.getInt(3) == 1 ? true : false);
        }catch (Exception e){
            throw new RuntimeException("No Student found with this Roll Number");
        }
    }
    public void CheckIfTableHasValues() {
        // if database is empty
        List<StudentModel> students = getAllStudents();
        if(students.isEmpty()){
            throw new RuntimeException("Table is empty please add students first");
        }
    }
    public ArrayList<StudentModel> getAllStudents() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + STUDENT_TABLE, null);

        ArrayList<StudentModel> studentArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {

                studentArrayList.add(new StudentModel(cursorCourses.getString(1),
                        cursorCourses.getInt(2),
                        cursorCourses.getInt(3) == 1 ? true : false));
            } while (cursorCourses.moveToNext());

        }

        cursorCourses.close();
        return studentArrayList;
    }
    public void  addStudent(StudentModel StdModel){
        SQLiteDatabase db = this.getWritableDatabase();
        //Hash map, as we did in bundles
        ContentValues cv = new ContentValues();

        cv.put(STUDENT_NAME, StdModel.getName());
        cv.put(STUDENT_ROLL, StdModel.getRollNmber());
        cv.put(STUDENT_ENROLL, StdModel.isEnroll());
        db.insert(STUDENT_TABLE, null, cv);
        db.close();
    }
    public void updateStudent(StudentModel StdModel) {
        //checks
        CheckIfTableHasValues();
        CheckIfStudentExists(StdModel.getRollNmber());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
            cv.put(STUDENT_NAME, StdModel.getName());
            cv.put(STUDENT_ROLL, StdModel.getRollNmber());
            cv.put(STUDENT_ENROLL, StdModel.isEnroll());
            db.update(STUDENT_TABLE,cv,STUDENT_ROLL + "=?", new String[]{String.valueOf(StdModel.getRollNmber())});
            db.close();
    }
    public void deleteStudent(int rollNumber){
        //checks
        CheckIfTableHasValues();
        CheckIfStudentExists(rollNumber);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(STUDENT_TABLE, STUDENT_ROLL + " = ?",
                new String[] { String.valueOf(rollNumber) });
        db.close();
    }
}