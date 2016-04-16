package com.example.umesh.countrylistview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlDbHelper extends SQLiteOpenHelper {
	public static final String DATABASE_TABLE = "COUNTRY";
	public static final String DATABASE_STATE_TABLE = "STATE";

	public static final String COLUMN1 = "id";
	public static final String COLUMN2 = "country_name";
	private static final String SCRIPT_CREATE_DATABASE = "create table "
			+ DATABASE_TABLE + " (" + COLUMN1
			+ " integer primary key autoincrement, " + COLUMN2
			+ " text not null);";
	private static final String CREATE_STATE = "create table "
			+ DATABASE_STATE_TABLE + " (" + COLUMN1
			+ " integer primary key autoincrement, "
			+"country_id integer,"
			+ "state_name text not null,"
			+ " FOREIGN KEY (country_id) REFERENCES "+DATABASE_TABLE+"("+COLUMN1+"));";

	public SqlDbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SCRIPT_CREATE_DATABASE);
		db.execSQL(CREATE_STATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_STATE_TABLE);
		onCreate(db);
	}

}
