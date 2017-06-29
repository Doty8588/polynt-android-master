package com.Polynt.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.Polynt.Polynt;

import java.util.ArrayList;

public class DatabaseMaster {
	public static final String TABLE_HISTORY = "tblHistory";
	// ///////////////////////////////////////////////////////////

	private SQLiteDatabase database;
	private Context mContext;

	private static DatabaseMaster _instance = null;

	static public DatabaseMaster getInstance(Context context) {
		if (_instance == null) {
			_instance = new DatabaseMaster(context);
		}
		return _instance;
	}

	public DatabaseMaster(Context context) {
		DatabaseHelper helper = new DatabaseHelper(context);
		mContext = context;
		database = helper.getWritableDatabase();
	}

	public void DeleteAll(String table) {
		database.beginTransaction();
		try {
			database.delete(table, null, null);
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	public ArrayList GetHistorys() {
		ArrayList arrList = new ArrayList();
		try {
			String sql = String.format("SELECT * FROM %s", TABLE_HISTORY);

			Cursor cursor = database.rawQuery(sql, null);
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				String strName = cursor.getString(1);
				String strFileName = cursor.getString(2);
				arrList.add(strName);
				Polynt.getSharedApplication().mapHistory.put(strName, strFileName);
			}
			cursor.close();
		} finally {
		}
		return arrList;
	}

	public void InsertHistory(String strName, String strFileName) {
		database.beginTransaction();
		try {
			String sql = "INSERT INTO " + TABLE_HISTORY
					+ " (name, pdf)" + " values(?,?)";

			SQLiteStatement insert = database.compileStatement(sql);

			insert.bindString(1, strName);
			insert.bindString(2, strFileName);
			insert.execute();

			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	public Boolean RemoveAllHistory(){
		database.beginTransaction();
		try {
			String sql = "DELETE FROM " + TABLE_HISTORY;

			SQLiteStatement insert = database.compileStatement(sql);

			insert.execute();

			database.setTransactionSuccessful();
			return true;
		} finally {
			database.endTransaction();
		}
	}
}
