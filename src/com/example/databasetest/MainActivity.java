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
				ContentValues values = new ContentValues();

				values.put("name", "The Da Vinci Code");
				values.put("author", "Dan Brown");
				values.put("pages", 454);
				values.put("price", 16.96);
				db.insert("Book", null, values);
				values.clear();

				values.put("name", "The Lost Symbol");
				values.put("author", "Dan Brown");
				values.put("pages", 510);
				values.put("price", 19.95);
				db.insert("Book", null, values);
			}
		});

		Button updateData = (Button) findViewById(R.id.update_data);
		updateData.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("price", 10.99);
				db.update("Book", values, "name = ?", new String[] { "The Da Vinci Code" });
			}
		});

		Button deleteData = (Button) findViewById(R.id.delete_data);
		deleteData.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.delete("Book", "pages > ?", new String[] { "500" });
			}
		});

		Button queryData = (Button) findViewById(R.id.query_data);
		queryData.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				Cursor cursor = db.query("Book", null, null, null, null, null, null);
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
