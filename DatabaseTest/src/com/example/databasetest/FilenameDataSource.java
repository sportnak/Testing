package com.example.databasetest;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FilenameDataSource {

  // Database fields
  private SQLiteDatabase database;
  private Database dbHelper;
  private String[] allColumns = { Database.COLUMN_ID,
      Database.COLUMN_FILE };

  public FilenameDataSource(Context context) {
    dbHelper = new Database(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Filenames createFilenames(String file) {
    ContentValues values = new ContentValues();
    values.put(Database.COLUMN_FILE, file);
    long insertId = database.insert(Database.TABLE_FILES, null,
        values);
    Cursor cursor = database.query(Database.TABLE_FILES,
        allColumns, Database.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Filenames newFilenames = cursorToFilenames(cursor);
    cursor.close();
    return newFilenames;
  }

  public void deleteFilenames(Filenames file) {
    long id = file.getId();
    System.out.println("Filenames deleted with id: " + id);
    database.delete(Database.TABLE_FILES, Database.COLUMN_ID
        + " = " + id, null);
  }

  public List<Filenames> getAllFilenames() {
    List<Filenames> files = new ArrayList<Filenames>();

    Cursor cursor = database.query(Database.TABLE_FILES,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Filenames file = cursorToFilenames(cursor);
      files.add(file);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return files;
  }

  private Filenames cursorToFilenames(Cursor cursor) {
    Filenames file = new Filenames();
    file.setId(cursor.getLong(0));
    file.setFilename(cursor.getString(1));
    return file;
  }
} 