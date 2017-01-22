package com.example.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private MyDatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbHelper = new MyDatabaseHelper(this, "Books", null, 2);
		Button createDatabase = (Button) findViewById(R.id.create_database);
		createDatabase.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dbHelper.getWritableDatabase();
			}
		});

		Button addData = (Button) findViewById(R.id.add_data);
		addData.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
						new String[] { "The Da Vinci Code", "Dan Brown", "454", "16.96" });
				db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
						new String[] { "The Lost Symbol", "Dan Brown", "510", "19.95" });
			}
		});

		Button updateData = (Button) findViewById(R.id.update_data);
		updateData.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.execSQL("update Book set price = ? where name = ?", new String[] { "10.99", "The Da Vinci Code" });
			}
		});

		Button deleteData = (Button) findViewById(R.id.delete_data);
		deleteData.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.execSQL("delete from Book where pages > ?", new String[] { "500" });
			}
		});

		Button queryData = (Button) findViewById(R.id.query_data);
		queryData.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				Cursor cursor = db.rawQuery("select * from Book", null);
				if (cursor.moveToFirst()) {
					do {
						String name = cursor.getString(cursor.getColumnIndex("name"));
						String author = cursor.getString(cursor.getColumnIndex("author"));
						int pages = cursor.getInt(cursor.getColumnIndex("pages"));
						double price = cursor.getDouble(cursor.getColumnIndex("price"));
						Log.d("MyActivity", "book name is " + name);
						Log.d("MyActivity", "book author is " + author);
						Log.d("MyActivity", "book pages is " + pages);
						Log.d("MainActivity", "book price is " + price);
					} while (cursor.moveToNext());
				}
				cursor.close();
			}
		});
	}
}