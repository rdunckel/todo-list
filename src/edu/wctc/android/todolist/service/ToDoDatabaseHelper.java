package edu.wctc.android.todolist.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "ToDoList";
	public static final String TODO_TABLE_NAME = "todo";
	public static final String TODO_ID_KEY = "id";
	public static final String TODO_DESCRIPTION_KEY = "description";
	public static final String TODO_OWNER_KEY = "owner";
	private static final String TODO_TABLE_CREATE = "CREATE TABLE "
			+ TODO_TABLE_NAME + " (" + TODO_ID_KEY + " INTEGER, "
			+ TODO_DESCRIPTION_KEY + " TEXT, " + TODO_OWNER_KEY + " TEXT);";

	public ToDoDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TODO_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
