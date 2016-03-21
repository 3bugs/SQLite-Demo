package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Database Helper Class (คลาสที่ช่วยในการสร้างและทำงานกับฐานข้อมูล)
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my.db";    // ชื่อฐานข้อมูล (จะถูกนำไปตั้งเป็นชื่อไฟล์)
    private static final int DATABASE_VERSION = 1;          // เวอร์ชั่นของฐานข้อมูล

    public static final String TABLE_NAME = "users";        // ชื่อเทเบิล
    public static final String COL_ID = "_id";              // ชื่อฟีลด์ _id
    public static final String COL_NAME = "name";           // ชื่อฟีลด์ name
    public static final String COL_AGE = "age";             // ชื่อฟีลด์ age

/*
    เทเบิล users
    +-------+--------+-------+
    |  _id  |  name  |  age  |
    +-------+--------+-------+
    |       |        |       |
*/

    /* คำสั่ง SQL ที่ใช้สร้างเทเบิล โดยคำสั่งเต็มๆคือ
     * CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER) */
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_NAME + " TEXT,"
            + COL_AGE + " INTEGER"
            + ")";

    // คอนสตรัคเตอร์
    public DatabaseHelper(Context context) {
        // เรียกต่อไปยังคอนสตรัคเตอร์ของ SQLiteOpenHelper โดยส่งพารามิเตอร์ไป 4 ตัว
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* เมธอดที่แอนดรอยด์จะเรียกกลับมา (callback) ถ้าหากยังไม่มีฐานข้อมูลตามชื่อที่ระบุในคอนสตรัคเตอร์
     * โดยแอนดรอยด์จะสร้างฐานข้อมูลว่างๆขึ้นมาให้ แล้วส่งฐานข้อมูลนั้นผ่านมาทางตัวแปรพารามิเตอร์ db ของเมธอดนี้
     * หน้าที่ของเราคือจะต้องสร้างเทเบิลต่างๆในฐานข้อมูลตามที่เราออกแบบ schema ไว้ (จะมีเทเบิลอะไรบ้าง
     * แต่ละเทเบิลมีฟีลด์อะไรบ้างและเป็นชนิดใด ฯลฯ) นอกจากนี้เราอาจเพิ่มข้อมูลตั้งต้นไว้ในฐานข้อมูลด้วย ก็ทำได้เช่นกัน
     * ดังเช่นตัวอย่างนี้ */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // สร้างเทเบิล users
        db.execSQL(SQL_CREATE_TABLE);

        // เพิ่มข้อมูล 1 แถวลงในเทเบิล users
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, "Promlert");
        cv.put(COL_AGE, 41);
        db.insert(TABLE_NAME, null, cv);

        // เพิ่มข้อมูลอีก 1 แถวลงในเทเบิล users
        cv = new ContentValues();
        cv.put(COL_NAME, "Abc");
        cv.put(COL_AGE, 123);
        db.insert(TABLE_NAME, null, cv);
    }

    /* เมธอดที่แอนดรอยด์จะเรียกกลับมา (callback) ถ้าหาก user ได้ติดตั้งแอพของเราไว้แล้ว แต่เวอร์ชั่นของฐานข้อมูล
     * ในแอพตัวเดิมเก่ากว่าฐานข้อมูลในแอพปัจจุบัน โดยแอนดรอยด์จะส่งฐานข้อมูลเดิม, เลขเวอร์ชั่นเดิม และเลขเวอร์ชั่นใหม่
     * ผ่านมาทางตัวแปรพารามิเตอร์ db, oldVersion และ newVersion ของเมธอดนี้ ตามลำดับ เพื่อให้เราแก้ไขโครงสร้าง
     * ของฐานข้อมูลได้ตามที่เราต้องการ (ทั้งนี้ เราจะปรับเลขเวอร์ชั่นฐานข้อมูล เมื่อมีการเปลี่ยนโครงสร้างฐานข้อมูลเท่านั้น
     * เช่นในแอพเวอร์ชั่นใหม่เราอาจต้องการเพิ่มฟีลด์ email ให้กับเทเบิล users เพื่อเอาไว้เก็บอีเมลแอดเดรสด้วย จากเดิม
     * ที่มีเฉพาะฟีลด์ name และ age */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
