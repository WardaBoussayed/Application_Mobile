package com.example.mycalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    private final static String CREATE_EVENT_TABLE = "create table " + DBStructure.EVENT_TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            +DBStructure.EVENT+" TEXT, " +DBStructure.TIME+" TEXT, "+DBStructure.DATE+" TEXT, "+ DBStructure.MONTH +" TEXT, "
            +DBStructure.YEAR+" TEXT, "+DBStructure.Notify+" TEXT)";
    private static final String DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " +DBStructure.EVENT_TABLE_NAME;





    public DBOpenHelper(@Nullable Context context) {
        super(context, DBStructure.DB_NAME,null,DBStructure.DB_VERSION);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENT_TABLE);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);
    }



    public void SaveEvent(String event, String time, String date, String month, String year, String notify, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.EVENT, event);
        contentValues.put(DBStructure.TIME, time);
        contentValues.put(DBStructure.DATE, date);
        contentValues.put(DBStructure.MONTH, month);
        contentValues.put(DBStructure.YEAR, year);
        contentValues.put(DBStructure.Notify,notify);
    database.insert(DBStructure.EVENT_TABLE_NAME, null, contentValues);

    }



    public Cursor ReadEvent(String date, SQLiteDatabase database){
        String [] projections = {DBStructure.EVENT, DBStructure.TIME, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String Selection = DBStructure.DATE + "=?";
        String [] selectionArgs = {date};
        return database.query(DBStructure.EVENT_TABLE_NAME,projections, Selection,selectionArgs,null,null,null);

    }


    public Cursor ReadIDEvent(String date,String event,String time, SQLiteDatabase database){
        String [] projections = {DBStructure.ID, DBStructure.Notify};
        String Selection = DBStructure.DATE + "=? and "+DBStructure.EVENT+"=? and "+DBStructure.TIME+"=?";
        String [] selectionArgs = {date, event,time};


        return database.query(DBStructure.EVENT_TABLE_NAME,projections, Selection,selectionArgs,null,null,null);

    }

    public void updateEvent(String date,String event,String time,String notify, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.Notify, notify);
        String Selection = DBStructure.EVENT + "=? and "+DBStructure.DATE+"=? and "+DBStructure.TIME+"=?";
        String [] selectionArgs = {event,date,time};
        database.update(DBStructure.EVENT_TABLE_NAME,contentValues, Selection,selectionArgs);

    }




    public Cursor ReadEventperMonth(String month,String year, SQLiteDatabase database){
        String [] projections = {DBStructure.EVENT, DBStructure.TIME, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String Selection = DBStructure.MONTH + "=? and " + DBStructure.YEAR+"=?" ;
        String [] selectionArgs = {month, year};
        return database.query(DBStructure.EVENT_TABLE_NAME,projections, Selection,selectionArgs,null,null,null);

    }



    public void deleteEvent(String event, String date, String time, SQLiteDatabase database){

        String selection = DBStructure.EVENT + "=? and "+DBStructure.DATE+"=? and "+DBStructure.TIME+"=?";
        String [] selectionArg = {event,date,time};
        database.delete(DBStructure.EVENT_TABLE_NAME, selection, selectionArg)
;    }

}
