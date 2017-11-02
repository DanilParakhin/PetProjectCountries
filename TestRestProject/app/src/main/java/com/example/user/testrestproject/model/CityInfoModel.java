package com.example.user.testrestproject.model;

/**
 * POJO model for City object.
 */

public class CityInfoModel {
	private String summary;
	private int elevation;
	private int geoNameId;
	private double lng;
	private String countryCode;
	private int rank;
	private String thumbnailImg;
	private String lang;
	private String title;
	private double lat;
	private String wikipediaUrl;

	public CityInfoModel(String summary, int elevation, int geoNameId, double lng, String countryCode, int rank, String thumbnailImg, String lang, String title, double lat, String wikipediaUrl) {
		this.summary = summary;
		this.elevation = elevation;
		this.geoNameId = geoNameId;
		this.lng = lng;
		this.countryCode = countryCode;
		this.rank = rank;
		this.thumbnailImg = thumbnailImg;
		this.lang = lang;
		this.title = title;
		this.lat = lat;
		this.wikipediaUrl = wikipediaUrl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getElevation() {
		return elevation;
	}

	public void setElevation(int elevation) {
		this.elevation = elevation;
	}

	public int getGeoNameId() {
		return geoNameId;
	}

	public void setGeoNameId(int geoNameId) {
		this.geoNameId = geoNameId;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getThumbnailImg() {
		return thumbnailImg;
	}

	public void setThumbnailImg(String thumbnailImg) {
		this.thumbnailImg = thumbnailImg;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getWikipediaUrl() {
		return wikipediaUrl;
	}

	public void setWikipediaUrl(String wikipediaUrl) {
		this.wikipediaUrl = wikipediaUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CityInfoModel cityInfoModel = (CityInfoModel) o;

		if (elevation != cityInfoModel.elevation) return false;
		if (geoNameId != cityInfoModel.geoNameId) return false;
		if (Double.compare(cityInfoModel.lng, lng) != 0) return false;
		if (rank != cityInfoModel.rank) return false;
		if (Double.compare(cityInfoModel.lat, lat) != 0) return false;
		if (summary != null ? !summary.equals(cityInfoModel.summary) : cityInfoModel.summary != null)
			return false;
		if (countryCode != null ? !countryCode.equals(cityInfoModel.countryCode) : cityInfoModel.countryCode != null)
			return false;
		if (thumbnailImg != null ? !thumbnailImg.equals(cityInfoModel.thumbnailImg) : cityInfoModel.thumbnailImg != null)
			return false;
		if (lang != null ? !lang.equals(cityInfoModel.lang) : cityInfoModel.lang != null) return false;
		if (title != null ? !title.equals(cityInfoModel.title) : cityInfoModel.title != null) return false;
		return wikipediaUrl != null ? wikipediaUrl.equals(cityInfoModel.wikipediaUrl) : cityInfoModel.wikipediaUrl == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = summary != null ? summary.hashCode() : 0;
		result = 31 * result + elevation;
		result = 31 * result + geoNameId;
		temp = Double.doubleToLongBits(lng);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
		result = 31 * result + rank;
		result = 31 * result + (thumbnailImg != null ? thumbnailImg.hashCode() : 0);
		result = 31 * result + (lang != null ? lang.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		temp = Double.doubleToLongBits(lat);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (wikipediaUrl != null ? wikipediaUrl.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "CityInfoModel{" +
				"summary='" + summary + '\'' +
				", elevation=" + elevation +
				", geoNameId=" + geoNameId +
				", lng=" + lng +
				", countryCode='" + countryCode + '\'' +
				", rank=" + rank +
				", thumbnailImg='" + thumbnailImg + '\'' +
				", lang='" + lang + '\'' +
				", title='" + title + '\'' +
				", lat=" + lat +
				", wikipediaUrl='" + wikipediaUrl + '\'' +
				'}';
	}
}