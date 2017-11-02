package com.example.user.testrestproject.loader;

import android.content.Context;
import android.support.v4.content.Loader;

import com.example.user.testrestproject.TestRestApp;
import com.example.user.testrestproject.model.CityInfoModel;
import com.example.user.testrestproject.model.CityModelList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.user.testrestproject.Constants.API_USERNAME;

/**
 * Class used to async load data from API.
 */
public class CityInfoLoader extends Loader<List<CityInfoModel>> {
	private String cityName;

	public CityInfoLoader(Context context, String cityName) {
		super(context);
		this.cityName = cityName;
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		forceLoad();
	}

	@Override
	public void forceLoad() {
		super.forceLoad();
		TestRestApp.getMyRetrofitAPI()
				.getCity(cityName, 20, API_USERNAME)
				.enqueue(new Callback<CityModelList>() {
					@Override
					public void onResponse(Call<CityModelList> call, Response<CityModelList> response) {
						deliverResult(response.body().getGeonames());
					}

					@Override
					public void onFailure(Call<CityModelList> call, Throwable t) {
						deliverResult(new ArrayList<CityInfoModel>());
					}
				});
	}
}

