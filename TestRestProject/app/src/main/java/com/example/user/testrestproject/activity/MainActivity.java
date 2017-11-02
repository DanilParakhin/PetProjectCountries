package com.example.user.testrestproject.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.testrestproject.R;
import com.example.user.testrestproject.TestRestApp;
import com.example.user.testrestproject.database.LocalDatabase;
import com.example.user.testrestproject.dialog.ProgressDialog;
import com.example.user.testrestproject.loader.CityCursorLoader;
import com.example.user.testrestproject.loader.CountryCursorLoader;
import com.example.user.testrestproject.observer.DBCreateObservable;
import com.example.user.testrestproject.observer.DBCreateObserver;

import static com.example.user.testrestproject.Constants.CITY_LOADER_ID;
import static com.example.user.testrestproject.Constants.COUNTRY_LOADER_ID;
import static com.example.user.testrestproject.Constants.SEARCH_CRITERIA;
import static com.example.user.testrestproject.database.DBHelper.KEY_CITY;
import static com.example.user.testrestproject.database.DBHelper.KEY_COUNTRY;
import static com.example.user.testrestproject.database.DBHelper.KEY_COUNTRY_ID;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, DBCreateObserver, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
	private LocalDatabase localDatabase;
	private Toolbar toolbar;
	private ProgressDialog progressDialog;
	private FloatingActionButton fab;

	private SearchView searchView;
	private Spinner spinnerCountries;
	private ListView listViewCities;
	private SimpleCursorAdapter spinnerAdapter;
	private SimpleCursorAdapter listViewAdapter;

	private DBCreateObservable dbCreateObservable;
	private Bundle cityLoaderBundle;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		dbCreateObservable = (DBCreateObservable) getApplication();
		dbCreateObservable.registerObserver(this);

		localDatabase = TestRestApp.getLocalDatabase();
		localDatabase.openDb();

		String[] fromCountries = new String[]{KEY_COUNTRY};
		String[] fromCities = new String[]{KEY_CITY};
		int[] toCountries = new int[]{R.id.spinner_text_view};
		int[] toCities = new int[]{R.id.list_view_text_view};

		spinnerCountries = (Spinner) findViewById(R.id.main_activity_spinner_countries);
		spinnerAdapter = new SimpleCursorAdapter(this, R.layout.spinner_item, null, fromCountries, toCountries, 0);
		spinnerCountries.setAdapter(spinnerAdapter);
		spinnerCountries.setPrompt("Set country");
		spinnerCountries.setOnItemSelectedListener(this);
		spinnerCountries.setSelection(0);


		listViewCities = (ListView) findViewById(R.id.main_activity_list_view_cities);
		listViewAdapter = new SimpleCursorAdapter(this, R.layout.list_view_item, null, fromCities, toCities, 0);
		listViewCities.setAdapter(listViewAdapter);
		listViewCities.setOnItemClickListener(this);


		cityLoaderBundle = new Bundle();
		cityLoaderBundle.putLong(KEY_COUNTRY_ID, spinnerCountries.getSelectedItemId());
		searchView = (SearchView) findViewById(R.id.main_activity_edit_text_search);
		searchView.setOnQueryTextListener(this);
		searchView.setIconified(false);
		searchView.setQueryHint(getResources().getString(R.string.start_enter_city_name));
		searchView.setVisibility(View.GONE);

		fab = (FloatingActionButton) findViewById(R.id.main_activity_fab);
		fab.setVisibility(View.GONE);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshDbCreation();
			}
		});

		getSupportLoaderManager().initLoader(COUNTRY_LOADER_ID, null, this);
		getSupportLoaderManager().initLoader(CITY_LOADER_ID, cityLoaderBundle, this);
	}

	@Override
	protected void onDestroy() {
		localDatabase.closeDb();
		dbCreateObservable.removeObserver(this);
		super.onDestroy();
	}

	@Override
	public void update(int statusId) {
		switch (statusId) {
			case ON_START:
				progressDialog = new ProgressDialog(MainActivity.this);
				progressDialog.show();
				break;
			case ON_FINISH:
				progressDialog.dismiss();
				fab.setVisibility(View.GONE);
				getSupportLoaderManager().getLoader(COUNTRY_LOADER_ID).forceLoad();
				break;
			case ON_FAILURE:
				progressDialog.dismiss();
				fab.setVisibility(View.VISIBLE);
				Toast.makeText(this, "Oops! We have network trouble. Please, push refresh button", Toast.LENGTH_LONG).show();
				break;
		}

	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Loader<Cursor> loader = null;
		switch (id) {
			case COUNTRY_LOADER_ID:
				loader = new CountryCursorLoader(this, localDatabase);
				break;
			case CITY_LOADER_ID:
				loader = new CityCursorLoader(this, localDatabase, args.getLong(KEY_COUNTRY_ID), args.getString(SEARCH_CRITERIA));
				break;
		}
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		switch (loader.getId()) {
			case COUNTRY_LOADER_ID:
				spinnerAdapter.swapCursor(data);
				break;
			case CITY_LOADER_ID:
				listViewAdapter.swapCursor(data);
				break;
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		searchView.setQuery("", false);
		searchView.clearFocus();
		cityLoaderBundle.clear();
		cityLoaderBundle.putLong(KEY_COUNTRY_ID, id);
		getSupportLoaderManager().restartLoader(CITY_LOADER_ID, cityLoaderBundle, this);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String countryName = ((Cursor) spinnerCountries.getSelectedItem()).getString(1);
		String cityName = ((Cursor) listViewAdapter.getItem(position)).getString(2);

		Intent intent = new Intent(this, CityActivity.class);
		intent.putExtra(KEY_COUNTRY, countryName);
		intent.putExtra(KEY_CITY, cityName);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_search:
				if (searchView.getVisibility() == View.VISIBLE) {
					searchView.setVisibility(View.GONE);
				} else if (searchView.getVisibility() == View.GONE) {
					searchView.setVisibility(View.VISIBLE);
				}
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Method reruns database creation when database creation failed.
	 */
	private void refreshDbCreation() {
		if (spinnerAdapter.getCount() == 0)
			localDatabase.fillDbTables();
		getSupportLoaderManager().restartLoader(COUNTRY_LOADER_ID, null, MainActivity.this);
	}


	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		cityLoaderBundle.clear();
		cityLoaderBundle.putLong(KEY_COUNTRY_ID, spinnerCountries.getSelectedItemId());
		cityLoaderBundle.putString(SEARCH_CRITERIA, newText);
		getSupportLoaderManager().restartLoader(CITY_LOADER_ID, cityLoaderBundle, this);
		return true;
	}
}
