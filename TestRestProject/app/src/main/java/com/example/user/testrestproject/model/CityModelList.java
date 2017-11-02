package com.example.user.testrestproject.model;

import java.util.ArrayList;

/**
 * Class-container used to store cities list.
 */

public class CityModelList {
	private ArrayList<CityInfoModel> geonames;

	public CityModelList(ArrayList<CityInfoModel> geonames) {
		this.geonames = geonames;
	}

	public ArrayList<CityInfoModel> getGeonames() {
		return geonames;
	}

	public void setGeonames(ArrayList<CityInfoModel> geonames) {
		this.geonames = geonames;
	}
}
