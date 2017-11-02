package com.example.user.testrestproject.observer;

/**
 * Interface for monitoring database creation status.
 */

public interface DBCreateObserver {
	int ON_START = 0;
	int ON_FINISH = 1;
	int ON_FAILURE = 2;

	/**
	 * Method is used to update the status of database creation process.
	 *
	 * @param statusId status ID
	 */
	void update(int statusId);
}
