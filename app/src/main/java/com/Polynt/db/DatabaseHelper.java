package com.Polynt.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
	public final Context mContext;
    public static final String DATABASE_NAME = "polynt.db";
    
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + DatabaseMaster.TABLE_HISTORY + " ([id] INTEGER PRIMARY KEY AUTOINCREMENT, [name] TEXT DEFAULT(''), [pdf] TEXT DEFAULT(''));");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropTable(db, DatabaseMaster.TABLE_HISTORY);
		onCreate(db);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropTable(db, DatabaseMaster.TABLE_HISTORY);
		onCreate(db);
	}
	
	public void dropTable(SQLiteDatabase db, String table ) {
		db.execSQL("DROP TABLE IF EXIST "+table+";");
	}
}
