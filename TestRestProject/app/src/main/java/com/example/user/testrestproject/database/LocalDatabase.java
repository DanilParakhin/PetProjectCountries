package com.example.user.testrestproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.user.testrestproject.database.DBHelper.KEY_CITY;
import static com.example.user.testrestproject.database.DBHelper.KEY_COUNTRY;
import static com.example.user.testrestproject.database.DBHelper.KEY_COUNTRY_ID;
import static com.example.user.testrestproject.database.DBHelper.TABLE_CITIES;
import static com.example.user.testrestproject.database.DBHelper.TABLE_COUNTRIES;


/**
 * Class used work with local database.
 */

public class LocalDatabase {

	private DBHelper dbHelper;
	private SQLiteDatabase db;

	public LocalDatabase(Context context) {
		dbHelper = new DBHelper(context);
	}

	/**
	 * Method used to open connection with database.
	 */
	public void openDb(){
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * Method used to close connection with database.
	 */
	public void closeDb(){
		if (dbHelper != null){
			dbHelper.close();
		}
	}

	/**
	 * Method fills database tables with data received from Retrofit API.
	 */
	public void fillDbTables(){
		dbHelper.fillTables(db);
	}

	public Cursor getAllCountries(){

		return db.query(TABLE_COUNTRIES, null, null, null, null, null, KEY_COUNTRY);
//		return db.rawQuery("select * from " + TABLE_COUNTRIES, null);
	}

	public Cursor getCities(long _id) {
		return db.query(TABLE_CITIES, null, KEY_COUNTRY_ID + "= ?", new String[]{String.valueOf(_id)}, null, null, KEY_CITY);
	}

	/**
	 * Method returns cursor on list of cities by specified city name pattern and country ID.
	 * @param _id Country ID
	 * @param searchCriteria City name pattern
	 * @return Cursor on list of cities on spe
	 */
	public Cursor getCitiesBySearchCriteria(long _id, String searchCriteria) {
		String query = "SELECT * FROM " + TABLE_CITIES +
				" WHERE "  + KEY_COUNTRY_ID + "=?" +
				" AND " + KEY_CITY + " LIKE '" + searchCriteria + "%';";
		return db.rawQuery(query, new String[]{String.valueOf(_id)});
	}

	public DBHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}


}
