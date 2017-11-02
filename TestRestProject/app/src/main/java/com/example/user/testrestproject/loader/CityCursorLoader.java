package com.example.user.testrestproject.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.example.user.testrestproject.database.LocalDatabase;


/**
 * Class used to async load of data cursor from local database.
 */

public class CityCursorLoader extends CursorLoader {

	private LocalDatabase localDatabase;
	private long _id;
	private String searchCriteria;

	public CityCursorLoader(Context context, LocalDatabase localDatabase, long _id, String searchCriteria) {
		super(context);
		this.localDatabase = localDatabase;
		this._id = _id;
		this.searchCriteria = searchCriteria;
	}

	@Override
	protected Cursor onLoadInBackground() {
		if (searchCriteria == null || searchCriteria.length() == 0) {
			return localDatabase.getCities(_id);
		} else {
			return localDatabase.getCitiesBySearchCriteria(_id, searchCriteria);
		}
	}

}
