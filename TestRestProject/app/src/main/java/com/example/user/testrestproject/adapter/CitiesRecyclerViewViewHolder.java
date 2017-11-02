package com.example.user.testrestproject.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.testrestproject.R;
import com.example.user.testrestproject.listener.OnRecyclerViewItemClickListener;

/**
 * View holder for RecyclerView.
 */
class CitiesRecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
	CardView cardView;
	ImageView imageView;
	TextView textViewTitle;
	TextView textViewSubTitle;
	TextView textViewSummary;
	OnRecyclerViewItemClickListener listener;


	CitiesRecyclerViewViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
		super(itemView);
		this.listener = listener;

		cardView = (CardView) itemView.findViewById(R.id.recycler_card_view);
		imageView = (ImageView) itemView.findViewById(R.id.holder_image_view);
		textViewTitle = (TextView) itemView.findViewById(R.id.holder_text_view_title);
		textViewSubTitle = (TextView) itemView.findViewById(R.id.holder_text_view_subtitle);
		textViewSummary = (TextView) itemView.findViewById(R.id.holder_text_view_summary);
		cardView.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int position = getAdapterPosition();
		if (position != RecyclerView.NO_POSITION && listener != null) {
			listener.onItemClick(v.getId(), position);
		}
	}

}
