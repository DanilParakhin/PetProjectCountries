package com.example.user.testrestproject.activity;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.user.testrestproject.R;
import com.example.user.testrestproject.adapter.CitiesRecyclerViewAdapter;
import com.example.user.testrestproject.loader.CityInfoLoader;
import com.example.user.testrestproject.model.CityInfoModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.testrestproject.Constants.CACHE_LIST;
import static com.example.user.testrestproject.Constants.CITY_INFO_LOADER_ID;
import static com.example.user.testrestproject.Constants.CITY_LIST;
import static com.example.user.testrestproject.database.DBHelper.KEY_CITY;

public class CityActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<CityInfoModel>> {
	private String cityName;

	private List<CityInfoModel> cacheList;


	private RecyclerView citiesRecyclerView;
	private CitiesRecyclerViewAdapter adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			cityName = extras.getString(KEY_CITY);
		}

		Toolbar toolbar = findViewById(R.id.toolbar);
		toolbar.setTitle(cityName);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.ic_action_back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});


		citiesRecyclerView = findViewById(R.id.city_activity_recycler_view);
		citiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new CitiesRecyclerViewAdapter(new ArrayList<CityInfoModel>(), this);
		citiesRecyclerView.setAdapter(adapter);
		setOnSwipeRemove();

		/*
		When activity is being created first time data is received from loader.
		When activity is being recreated after state changed data is received from retained data.
		 */
		if (getLastCustomNonConfigurationInstance()== null){
			getSupportLoaderManager().initLoader(CITY_INFO_LOADER_ID, null, this);
		} else {
			SparseArray<List<CityInfoModel>> lastInstances = (SparseArray<List<CityInfoModel>>)getLastCustomNonConfigurationInstance();
			adapter.dataSetChanged(lastInstances.get(CITY_LIST));
			cacheList = lastInstances.get(CACHE_LIST);
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_city, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_undo:
				if (cacheList != null && cacheList.size() > 0){
					adapter.add(cacheList.get(cacheList.size() - 1));
					cacheList.remove(cacheList.size() - 1);
				}
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public Loader<List<CityInfoModel>> onCreateLoader(int id, Bundle args) {
		return new CityInfoLoader(this, cityName);
	}

	@Override
	public void onLoadFinished(Loader<List<CityInfoModel>> loader, List<CityInfoModel> data) {
		adapter.dataSetChanged(data);
	}

	@Override
	public void onLoaderReset(Loader<List<CityInfoModel>> loader) {
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		SparseArray<List<CityInfoModel>> instances = new SparseArray<>();
		instances.put(CITY_LIST, adapter.getCitiesList());
		instances.put(CACHE_LIST, cacheList);
		return instances;
	}

	public void setOnSwipeRemove(){
		ItemTouchHelper.SimpleCallback touchSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

			@Override
			public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
				return false;
			}

			@Override
			public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
				if (cacheList == null){
					cacheList = new ArrayList<>();
				}
				cacheList.add(adapter.getItem(viewHolder.getAdapterPosition()));
				adapter.remove(viewHolder.getAdapterPosition());
			}
		};
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchSimpleCallback);
		itemTouchHelper.attachToRecyclerView(citiesRecyclerView);
	}

}



