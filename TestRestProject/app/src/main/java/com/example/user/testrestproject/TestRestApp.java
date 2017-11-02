package com.example.user.testrestproject;

import android.app.Application;

import com.example.user.testrestproject.database.LocalDatabase;
import com.example.user.testrestproject.listener.OnDatabaseCreateListener;
import com.example.user.testrestproject.observer.DBCreateObservable;
import com.example.user.testrestproject.observer.DBCreateObserver;
import com.example.user.testrestproject.retrofit.CountriesRetrofitAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.testrestproject.observer.DBCreateObserver.ON_FAILURE;
import static com.example.user.testrestproject.observer.DBCreateObserver.ON_FINISH;
import static com.example.user.testrestproject.observer.DBCreateObserver.ON_START;

/**
 * 		Main Application of the project.
 * 		Initialization of Retrofit API and local database
 */

public class TestRestApp extends Application implements OnDatabaseCreateListener, DBCreateObservable {
	private static CountriesRetrofitAPI myRetrofitAPI;
	private static LocalDatabase localDatabase;
	private Retrofit retrofit;
	private DBCreateObserver dbCreateObserver;

	@Override
	public void onCreate() {
		super.onCreate();
		retrofit = new Retrofit.Builder()
				.baseUrl(Constants.SERVER_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		myRetrofitAPI = retrofit.create(CountriesRetrofitAPI.class);

		localDatabase = new LocalDatabase(getApplicationContext());
	}

	public static CountriesRetrofitAPI getMyRetrofitAPI(){
		return myRetrofitAPI;
	}

	public static LocalDatabase getLocalDatabase() {
		return localDatabase;
	}


	@Override
	public void registerObserver(DBCreateObserver o) {
		this.dbCreateObserver = o;
	}

	@Override
	public void removeObserver(DBCreateObserver o) {
		this.dbCreateObserver = null;
	}


	@Override
	public void notifyObserver(int statusId) {
		dbCreateObserver.update(statusId);
	}


	@Override
	public void onStartCreateDB() {
		notifyObserver(ON_START);
	}

	@Override
	public void onFinishCreateDB() {
		notifyObserver(ON_FINISH);
	}

	@Override
	public void onFailureCreateDB() {
		notifyObserver(ON_FAILURE);
	}
}
