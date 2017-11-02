package com.example.user.testrestproject.listener;

/**
 * Interface for listening click on RecyclerView item.
 */

public interface OnRecyclerViewItemClickListener {

	/**
	 * Method used to handling click on RecyclerView item.
	 * @param viewId Clicked view ID
	 * @param position RecyclerView item position
	 */
	void onItemClick(int viewId, int position);
}
