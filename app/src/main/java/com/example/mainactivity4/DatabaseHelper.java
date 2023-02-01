package com.example.mainactivity4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //database name
    private static final String dbName = "users.db";

    // below int is our database version
    private static final int dbVersion = 1;

    // table names
    static final String userTable = "userDetails";

    // below variable is for our id column.
    private static final String userFullName = "name";

    // below variable is for our course name column
    private static final String userRegNo = "regnumber";

    // below variable id for our course duration column.
    private static final String userDob = "dateofbirth";

    // below variable for our course description column.
    private static final String userSchool = "school";

    // below variable is for our course tracks column.
    private static final String userCourse = "course";

    private static final String userGender = "gender";

    private static final String userStatus = "status";

    private static final String userCounty = "county";

    public static final String userName = "username";

    public static final String userPassword = "password";

    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + userTable + " ("
                + userFullName + " TEXT, "
                + userRegNo + " TEXT, "
                + userDob + " TEXT, "
                + userSchool + " TEXT, "
                + userCourse + " TEXT, "
                + userGender + " TEXT, "
                + userStatus + " TEXT, "
                + userCounty + " TEXT, "
                + userName + " TEXT primary key, "
                +userPassword + " TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    public void addUserDetails(String userfullName, String regNumber, String dateOfBirth, String school, String course, String gender,
                               String status, String county, String username, String userpassword){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(userFullName, userfullName);
        values.put(userRegNo, regNumber);
        values.put(userDob, dateOfBirth);
        values.put(userSchool, school);
        values.put(userCourse, course);
        values.put(userGender , gender);
        values.put(userStatus, status);
        values.put(userCounty,county);
        values.put(userName, username);
        values.put(userPassword, userpassword);

        database.insert(userTable, null, values);
        database.close();
    }

    public Cursor getUserDetails(String username)
    {
        SQLiteDatabase getUserData = this.getReadableDatabase();

        //fetch details of the user where username is from username
        Cursor cursor = getUserData.rawQuery("SELECT * FROM userDetails WHERE username = ?", new String[]{username});
        return cursor;
    }

    public void UpdateDetails(String userfullName, String regNumber, String dateOfBirtth, String school, String course, String gender,
                              String status, String county, String username, String userpassword)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(userFullName, userfullName);
        values.put(userRegNo, regNumber);
        values.put(userDob, dateOfBirtth);
        values.put(userSchool, school);
        values.put(userCourse, course);
        values.put(userGender , gender);
        values.put(userStatus, status);
        values.put(userCounty, county);
        values.put(userName, username);
        values.put(userPassword, userpassword);

        database.update(userTable, values, "username = ?" ,new String[] {username});
        database.close();
    }
    public void DeleteDetails(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(userTable, userName + " = ?", new String[]{username});
        db.close();
    }

    public Cursor getData(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + userTable + " where " + DatabaseHelper.userName + "=?" + " and " + DatabaseHelper.userPassword + "=?", new String[] {username, password});
        return res;
    }

    public Boolean CheckLoginDetails(String username, String userpassword){
        //columns to check data
        String[] columns = {userName, userPassword};
        String selection = userName + " = ? AND " + userPassword + " = ?";
        String[] selectionArgs = {username, userpassword};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(userTable,
                columns,
                selection,
                selectionArgs,
                null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0)
            return true;
        else
            return false;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + userTable);
        onCreate(sqLiteDatabase);
    }
}
