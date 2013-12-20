package com.example.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper{
	//private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + "filenames";
	public static final String COLUMN_ID = "_id";
	public static final String TABLE_FILES = "filenames";
	public static final String COLUMN_FILE = "file";
	private final static String CREATE_CMD = "CREATE TABLE filenames("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_FILE + " TEXT NOT NULL)";
	
	public Database(Context context) {
		super(context, "filenames", null, 1);
	}
	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CMD);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		 Log.w(Database.class.getName(),
			        "Upgrading database from version " + oldVersion + " to "
			            + newVersion + ", which will destroy all old data");
			    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILES);
			    onCreate(db);
	}

}
