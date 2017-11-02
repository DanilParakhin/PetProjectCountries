package com.example.user.testrestproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.util.ArrayMap;


import com.example.user.testrestproject.TestRestApp;
import com.example.user.testrestproject.listener.OnDatabaseCreateListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Countries database helper.
 * Contains methods to create and update database, and fill tables with data from Retrofit API.
 */

public class DBHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "TestProjectDB";
	public static final String TABLE_COUNTRIES = "countries";
	public static final String TABLE_CITIES = "cities";
	public static final String KEY_ID = "_id";
	public static final String KEY_COUNTRY = "country";
	public static final String KEY_CITY = "city";
	public static final String KEY_COUNTRY_ID = "country_id";


	final String CREATE_COUNTRY_TABLE = "CREATE TABLE " + TABLE_COUNTRIES + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
			KEY_COUNTRY + " TEXT" + ")";
	final String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_CITIES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			KEY_COUNTRY_ID + " INTEGER," + KEY_CITY + " TEXT" + ")";

	private ArrayMap<String, ArrayList<String>> map;
	private OnDatabaseCreateListener listener;


	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.listener = (OnDatabaseCreateListener) context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_COUNTRY_TABLE);
		db.execSQL(CREATE_CITY_TABLE);
		fillTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
		onCreate(db);
	}

	/**
	 * Method fills database tables with data received from Retrofit.
	 * @param db Database
	 */
	void fillTables(SQLiteDatabase db) {
		listener.onStartCreateDB();
		TestRestApp.getMyRetrofitAPI().getCountries()
				.enqueue(new CountriesRetrofitCallback(db));
	}


	/**
	 * Class used by Retrofit API for processing API response.
	 */
	private class CountriesRetrofitCallback implements Callback<ArrayMap<String, ArrayList<String>>> {
		private SQLiteDatabase db;

		CountriesRetrofitCallback(SQLiteDatabase db) {
			this.db = db;
		}

		@Override
		public void onResponse(Call<ArrayMap<String, ArrayList<String>>> call, Response<ArrayMap<String, ArrayList<String>>> response) {
			if (response.isSuccessful()) {
				response.body();
				ContentValues cv = new ContentValues();
				db.beginTransaction();
				try {
					for (int i = 0; i < response.body().size(); i++) {
						if (!response.body().keyAt(i).equals("")){
							cv.put(KEY_ID, i + 1);
							cv.put(KEY_COUNTRY, response.body().keyAt(i));
							db.insert(TABLE_COUNTRIES, null, cv);
							cv.clear();
							for (String each : response.body().get(response.body().keyAt(i))) {
								cv.put(KEY_COUNTRY_ID, i + 1);
								cv.put(KEY_CITY, each);
								db.insert(TABLE_CITIES, null, cv);
								cv.clear();
							}
						}
					}
					db.setTransactionSuccessful();
				} finally {
					db.endTransaction();
					listener.onFinishCreateDB();
				}
			}
		}
		@Override
		public void onFailure(Call<ArrayMap<String, ArrayList<String>>> call, Throwable t) {
			listener.onFailureCreateDB();
		}

	}
}


