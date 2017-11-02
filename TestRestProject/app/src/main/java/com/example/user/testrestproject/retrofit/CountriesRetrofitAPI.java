package com.example.user.testrestproject.retrofit;

import android.support.v4.util.ArrayMap;

import com.example.user.testrestproject.model.CityModelList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for downloading application data from different sources by Retrofit API.
 */

public interface CountriesRetrofitAPI {

	/**
	 * Method used to download list of cities by specified query from http://api.geonames.org/wikipediaSearchJSON.
	 *
	 * @param q Query
	 * @param maxRows Maximum number of returned rows
	 * @param username Login name for GeoNames API
	 * @return Response as list of cities
	 */
	@GET("http://api.geonames.org/wikipediaSearchJSON")
	Call<CityModelList> getCity(@Query("q") String q, @Query("maxRows") int maxRows, @Query("username") String username);

	/**
	 * Method used to download map of counties and cities from David-Haim/CountriesToCitiesJSON/master/countriesToCities.json.
	 * @return Response as map of countries and cities
	 */
	@GET("David-Haim/CountriesToCitiesJSON/master/countriesToCities.json")
	Call<ArrayMap<String, ArrayList<String>>> getCountries();

}
