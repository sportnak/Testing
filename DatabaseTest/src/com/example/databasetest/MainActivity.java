package com.example.databasetest;

import java.util.List;
import java.util.Random;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	Database dbHelper;
	private FilenameDataSource datasource;
	private static final int REQUEST_BARCODE = 0;
    private static String contents = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new Database(getApplicationContext());
		
		datasource = new FilenameDataSource(this);
		datasource.open();
		
		List<Filenames> values = datasource.getAllFilenames();
		ArrayAdapter<Filenames> adapter = new ArrayAdapter<Filenames>(this,
		        android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onClick(View view) {
	    @SuppressWarnings("unchecked")
	    ArrayAdapter<Filenames> adapter = (ArrayAdapter<Filenames>) getListAdapter();
	    Filenames filename = null;
	    switch (view.getId()) {
	    case R.id.add:
	      scanBarCode(view);;
	      // save the new comment to the database
	      filename = datasource.createFilenames(contents);
	      adapter.add(filename);
	      break;
	    case R.id.delete:
	      if (getListAdapter().getCount() > 0) {
	        filename = (Filenames) getListAdapter().getItem(0);
	        datasource.deleteFilenames(filename);
	        adapter.remove(filename);
	      }
	      break;
	    }
	    adapter.notifyDataSetChanged();
	  }
	
	public void scanBarCode(View view) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");  

        startActivityForResult(intent, REQUEST_BARCODE);

        Toast toast = Toast.makeText(this, "Start scanning Barcode", Toast.LENGTH_SHORT);
        toast.show();
    }
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	            contents = intent.getStringExtra("SCAN_RESULT");
	        } else if (resultCode == RESULT_CANCELED) {
	        	Toast toast = Toast.makeText(this, "Failed Scan" , Toast.LENGTH_SHORT);
	            toast.show();
	        }
	    }
	}
	/*@SuppressWarnings("deprecation")
	public void createDB(View V) {
		SQLiteDatabase db =  dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("files", "michael");
		db.insert("filenames", null, values);
		values.clear();
		values.put("files", "leo");
		db.insert("filenames", null, values);
		final Cursor c = db.query("filenames", new String[] {"_id", "files"}, null, null, null, null, null);
		
		setListAdapter(new SimpleCursorAdapter(this, R.layout.activity_main, c, 
				new String[] {"_id", "files"}, new int[] {1,0}));
	}*/
	
	protected void onResume() {
		    datasource.open();
		    super.onResume();
	}

	 @Override
	protected void onPause() {
	    datasource.close();
	    super.onPause();
	 }

}
