package com.example.sqlitedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDatabase;
    private TextView mTextView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);
        mListView = (ListView) findViewById(R.id.list_view);

        // สร้างออบเจ็คของ Database Helper Class
        mHelper = new DatabaseHelper(this);
        // ติดต่อฐานข้อมูล ซึ่งจังหวะนี้เองที่แอนดรอยด์จะ callback ไปยังเมธอด onCreate ใน Database Helper Class
        // ถ้าหากยังไม่มีฐานข้อมูล
        mDatabase = mHelper.getWritableDatabase();

        query1();
        query2();
    }

    /* คิวรีข้อมูลทั้งหมดจากเทเบิล users มาแสดงใน TextView */
    private void query1() {
        // คิวรีฐานขอ้มูล จะได้ผลลัพธ์เป็น Cursor กลับมา ซึ่งเป็นตัวแทนของ result set
        Cursor cursor = mDatabase.query(
                DatabaseHelper.TABLE_NAME,  // ชื่อเทเบิล
                null,                       // ฟีลด์ที่ต้องการ
                null,                       // เงื่อนไขของแถวที่ต้องการ
                null,                       // ค่าสำหรับแทนลงในเงื่อนไข
                null,                       // การจัดกลุ่ม
                null,                       // เงื่อนไขของกลุ่ม
                null                        // การเรียงลำดับ
        );

        // ลบข้อมูลเดิมใน TextView ทิ้งไปก่อน
        mTextView.setText(null);

        // เข้าถึงแถวข้อมูลใน result set
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
            int age = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_AGE));

            String text = String.format("Name: %s, Age: %s\n", name, age);
            mTextView.append(text);
        }
        cursor.close();
    }

    /* คิวรีข้อมูลทั้งหมดจากเทเบิล users มาแสดงใน ListView */
    private void query2() {
/*
        +--------------+      +-----------------------+      +--------------+
        |              |      |                       |      |              |
        |   ListView   |======|        Adapter        |======|    Cursor    |
        |              |      | (SimpleCursorAdapter) |      |              |
        +--------------+      +-----------------------+      +--------------+
*/
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM users", null);

        // Adapter ที่นำข้อมูลจาก Cursor มาใส่ลง ListView (เป็นตัวกลางระหว่าง Cursor กับ ListView)
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                // คอนเท็กซ์
                this,
                // layout ของแต่ละไอเท็มใน ListView (แต่ละแถวใน Cursor)
                android.R.layout.simple_list_item_2,
                // เคอร์เซอร์
                cursor,
                // ชื่อฟีลด์ที่จะแสดงข้อมูลออกมา
                new String[]{DatabaseHelper.COL_NAME, DatabaseHelper.COL_AGE},
                // ID ของวิวใน layout ที่จะรับข้อมูลจากฟีลด์มาแสดง
                new int[]{android.R.id.text1, android.R.id.text2},
                // flags (โดยทั่วไปให้กำหนดเป็น 0)
                0
        );

        // ผูก Adapter เข้ากับ ListView
        mListView.setAdapter(adapter);
    }
}
