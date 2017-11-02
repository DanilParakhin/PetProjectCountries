package com.example.user.testrestproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.testrestproject.R;
import com.example.user.testrestproject.activity.WebViewActivity;
import com.example.user.testrestproject.listener.OnRecyclerViewItemClickListener;
import com.example.user.testrestproject.model.CityInfoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.user.testrestproject.Constants.CITY_NAME;
import static com.example.user.testrestproject.Constants.WIKI_URL;

/**
 * Adapter for RecyclerView
 */

public class CitiesRecyclerViewAdapter extends RecyclerView.Adapter<CitiesRecyclerViewViewHolder> {
	private List<CityInfoModel> citiesList;
	private Context context;

	public CitiesRecyclerViewAdapter(List<CityInfoModel> citiesList, Context context) {
		this.citiesList = citiesList;
		this.context = context;
	}

	/**
	 * Method changes data representation when it is updated.
	 * @param citiesList
	 */
	public void dataSetChanged(List<CityInfoModel> citiesList) {
		this.citiesList = citiesList;
		notifyDataSetChanged();
	}

	/**
	 * Method adds city item to the current list and update data representation.
	 * @param city City item
	 */
	public void add(CityInfoModel city) {
		this.citiesList.add(0, city);
		notifyDataSetChanged();
	}

	/**
	 * Method removes city item by position and update data representation.
	 * @param position Removed city item position
	 */
	public void remove(int position) {
		this.citiesList.remove(position);
		notifyItemRemoved(position);
	}


	/**
	 * Method creates view holder and processing click on current item.
	 * @param parent Parent ViewGroup
	 * @param viewType View type
	 * @return ViewHolder
	 */
	@Override
	public CitiesRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.recycler_view_item, parent, false);
		OnRecyclerViewItemClickListener listener = new OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(int viewId, int position) {
				switch (viewId){
					case R.id.recycler_card_view:
						Intent intent = new Intent(context, WebViewActivity.class);
						intent.putExtra(CITY_NAME, citiesList.get(position).getTitle());
						intent.putExtra(WIKI_URL, citiesList.get(position).getWikipediaUrl());
						context.startActivity(intent);
						break;
				}
			}
		};
		return new CitiesRecyclerViewViewHolder(v, listener);
	}

	@Override
	public void onBindViewHolder(CitiesRecyclerViewViewHolder holder, int position) {
		CityInfoModel city = citiesList.get(position);
		String wikiUrl = city.getWikipediaUrl();
		String title = city.getTitle();
		if (title.length() > 16){
			title = title.substring(0, 14) + "...";
		}
		if (wikiUrl.length() > 33) {
			wikiUrl = wikiUrl.substring(0, 29) + "...";
		}
		Picasso.with(context).load(city.getThumbnailImg()).into(holder.imageView);
		holder.textViewTitle.setText(title);
		holder.textViewSubTitle.setText(wikiUrl);
		holder.textViewSummary.setText(city.getSummary());
	}

	@Override
	public int getItemCount() {
		return citiesList.size();
	}

	public CityInfoModel getItem(int position) {
		return citiesList.get(position);
	}

	public List<CityInfoModel> getCitiesList() {
		return citiesList;
	}



}
