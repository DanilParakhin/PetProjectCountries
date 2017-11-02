package com.example.user.testrestproject.listener;

/**
 * Interface for listening database creation process.
 */

public interface OnDatabaseCreateListener {

	void onStartCreateDB();
	void onFinishCreateDB();
	void onFailureCreateDB();
}
