package com.example.user.testrestproject.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import com.example.user.testrestproject.database.LocalDatabase;


/**
 * Class used to async load of data cursor from local database.
 */

public class CountryCursorLoader extends CursorLoader {
	private LocalDatabase localDatabase;

	public CountryCursorLoader(Context context, LocalDatabase localDatabase) {
		super(context);
		this.localDatabase = localDatabase;
	}

	@Override
	protected Cursor onLoadInBackground() {
		return localDatabase.getAllCountries();
	}
}
