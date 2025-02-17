package com.example.ardeliachristina.events2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String DatabaseName = "Events.db";
    String Table1 = "events";
    String Col1 = "EventID";
    String Col2 = "EventName";
    String Col3 = "Description";
    String Col4 = "Location";
    String Col5 = "StartDate";
    String Col6 = "EndDate";
    String Col7 = "Rating";
    String Col8 = "Latitude";
    String Col9 = "Longitude";

    String Table2 = "eventTransactions";
    String C1= "TransactionID";
    String C2 = "UserID";
    String C3 = "EventID";
    String C4 = "TransactionDate";
    String C5 = "qty";

    String Table3 = "user";
    String Co1= "UserID";
    String Co2 = "Fullname";
    String Co3 = "Username";
    String Co4 = "Password";
    String Co5 = "PhoneNumber";
    String Co6 = "Address";
    String Co7 = "Gender";

    //Buat Database
    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, 1);
    }

    //Create Table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + Table1 + " (EventID TEXT PRIMARY KEY, EventName TEXT, Description TEXT, " +
                "Location TEXT, StartDate TEXT, EndDate TEXT, Rating INTEGER, Latitude TEXT, Longitude TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + Table2 + " (TransactionID TEXT PRIMARY KEY, UserID TEXT, EventID TEXT, " +
                "TransactionDate TEXT, qty INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE " + Table3 + " (UserID TEXT PRIMARY KEY, Fullname TEXT, Username TEXT, " +
                "Password TEXT, PhoneNumber TEXT, Address TEXT, Gender TEXT)");
    }

    //kosongin table nya
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // insert event
    public boolean insertEvent(String EventID, String EventName, String Description, String Location, String StartDate, String EndDate, int Rating, Double Latitude, Double Longitude) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col1, EventID);
        contentValues.put(Col2, EventName);
        contentValues.put(Col3, Description);
        contentValues.put(Col4, Location);
        contentValues.put(Col5, StartDate);
        contentValues.put(Col6, EndDate);
        contentValues.put(Col7, Rating);
        contentValues.put(Col8, Latitude);
        contentValues.put(Col9, Longitude);

        long result = sqLiteDatabase.insert(Table1, null, contentValues);

        if (result == -1) {
            return false;
        } else return true;

    }

    //get single event
    public Event getEvent(String EventID) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Table1 + " WHERE EventID == '"+ EventID +"'", null);
        if(cursor.moveToNext()) {
            Event selected = new Event(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getInt(6), cursor.getDouble(7), cursor.getDouble(8));

            return selected;
        }
        return null;
    }

    //get event list
    public ArrayList<Event> getEventList(){
        String selectQuery = "SELECT  * FROM " + Table1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Event> EventList = new ArrayList<Event>();
        if (cursor.moveToFirst()) {
            do {
                Event list = new Event(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getInt(6), cursor.getDouble(7), cursor.getDouble(8));
                EventList.add(list);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return EventList;
    }


    // insert transaction
    public boolean insertTransaction(String UserID, String EventID, String TransactionDate, String qty) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C1, "ET" + String.format("%03d", getTransactionList().size() + 1));
        contentValues.put(C2, UserID);
        contentValues.put(C3, EventID);
        contentValues.put(C4, TransactionDate);
        contentValues.put(C5, qty);

        long result = sqLiteDatabase.insert(Table2, null, contentValues);

        if (result == -1) {
            return false;
        } else return true;

    }

    //get transaction list
    public ArrayList<EventTransaction> getTransactionList(){
        String selectQuery = "SELECT * FROM " + Table2;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<EventTransaction> TransactionList = new ArrayList<EventTransaction>();
        if (cursor.moveToFirst()) {
            do {
                EventTransaction list = new EventTransaction(cursor.getString(cursor.getColumnIndex(C1)),
                        cursor.getString(cursor.getColumnIndex(C2)), cursor.getString(cursor.getColumnIndex(C3)),
                        cursor.getString(cursor.getColumnIndex(C4)), cursor.getString(cursor.getColumnIndex(C5)));
                TransactionList.add(list);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return TransactionList;
    }



    // insert user
    public boolean insertUser(String Fullname, String Username, String Password, String PhoneNumber, String Address, String Gender) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Co1, "US" + String.format("%03d", getUserList().size() + 1));
        contentValues.put(Co2, Fullname);
        contentValues.put(Co3, Username);
        contentValues.put(Co4, Password);
        contentValues.put(Co5, PhoneNumber);
        contentValues.put(Co6, Address);
        contentValues.put(Co7, Gender);

        long result = sqLiteDatabase.insert(Table3, null, contentValues);

        if (result == -1) {
            return false;
        } else return true;

    }

    //get user list
    public ArrayList<User> getUserList(){
        String selectQuery = "SELECT * FROM " + Table3;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<User> UserList = new ArrayList<User>();
        if (cursor.moveToFirst()) {
            do {
                User list = new User(
                        cursor.getString(cursor.getColumnIndex(Co2)), cursor.getString(cursor.getColumnIndex(Co3)),
                        cursor.getString(cursor.getColumnIndex(Co4)), cursor.getString(cursor.getColumnIndex(Co5)),
                        cursor.getString(cursor.getColumnIndex(Co6)), cursor.getString(cursor.getColumnIndex(Co7)),
                        cursor.getString(cursor.getColumnIndex(Co1)));
                UserList.add(list);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return UserList;
    }

}
