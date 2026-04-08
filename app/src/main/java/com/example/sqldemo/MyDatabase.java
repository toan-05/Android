package com.example.sqldemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(Context context) {
        super(context, "persons.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table persons(id integer primary key autoincrement, name text, age integer, address text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void executeSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public Cursor selectSQL(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public void insertPerson(String name, int age, String address) {
        String sql = "insert into persons(name, age, address) values('" + name + "', " + age + ", '" + address + "')";
        executeSQL(sql);
    }

    public void updatePerson(int id, String name, int age, String address) {
        String sql = "update persons set name='" + name + "', age=" + age + ", address='" + address + "' where id=" + id;
        executeSQL(sql);
    }

    public void deletePerson(int id) {
        String sql = "delete from persons where id=" + id;
        executeSQL(sql);
    }

    public Cursor getAllPersons() {
        return selectSQL("select * from persons");
    }
}