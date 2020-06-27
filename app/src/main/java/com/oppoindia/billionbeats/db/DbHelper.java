package com.oppoindia.billionbeats.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.oppoindia.billionbeats.model.LoginVO;
import com.oppoindia.billionbeats.model.UserInfo;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "worldcup";
    private static final String TABLE_USER = "user_info";
    private static final String KEY_ID = "id";
    private static final String NAME="name";
    private static final String EMAIL="email";
    private static final String CITY="city";
    private static final String MOBILE="mobile";
    private static final String REFFERAL_CODE="refferal_code";
    private static final String REFFERAL_COUNT="refferal_count";
    private static final String REFFERY_CODE="reffery_code";
    private static final String PASSWORD="passcode";


    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "("  + NAME
            + " TEXT," + EMAIL + " TEXT ," + CITY + " TEXT," + REFFERAL_CODE + " TEXT," + MOBILE + " TEXT not null," + REFFERAL_COUNT
            + " TEXT, " +REFFERY_CODE+" TEXT," +PASSWORD+" TEXT,"+" PRIMARY KEY(email)" +")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public long createUser(UserInfo userInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        long i=0;
        ContentValues values = new ContentValues();
Log.e("count",userInfo.getEmail());
            values.put(NAME, userInfo.getName());
            values.put(EMAIL, userInfo.getEmail());
            values.put(PASSWORD, userInfo.getPassword());
            values.put(CITY, userInfo.getCity());
            values.put(MOBILE, userInfo.getMobile());
            values.put(REFFERAL_CODE, userInfo.getRefferal());
        values.put(REFFERAL_COUNT, "0");
        values.put(REFFERY_CODE, userInfo.getReffere());


            // insert row
        try{
      i  =   db.insert(TABLE_USER, null, values);}catch( Exception  e ){
            i=0;
        }
        finally {
            db.close();
        }
db.close();
        // insert tag_ids
return i;

    }

    public String getReferCount(String email,String mobile) {
        String num = "0";
        String query = "SELECT REFFERAL_COUNT FROM " + TABLE_USER + " WHERE EMAIL=email ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                num = c.getString(c.getColumnIndex(REFFERAL_COUNT));
            }
            while (c.moveToNext());

        }
        Log.e("vacant",String.valueOf(num));
        return num;
    }


    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    public int getLogin(LoginVO login) {
        int num = 0;
        String query = "SELECT * FROM " + TABLE_USER + " WHERE  email ='"+ login.getEmail() +"' AND passcode='"+login.getPassword()+"'";
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor c = db.rawQuery(query, null);
        num=c.getCount();
        c.close();
        Log.e("count",String.valueOf(num));
        return num;
    }
}
