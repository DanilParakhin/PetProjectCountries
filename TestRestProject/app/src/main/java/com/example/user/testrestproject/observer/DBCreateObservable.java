package com.example.user.testrestproject.observer;

/**
 * Interface marks the subclass as observable object for database creation.
 * Contains methods to register, remove, notify observer.
 */

public interface DBCreateObservable {
	/**
	 * Method is used to register observer for database creation to current class.
	 * @param o Observer
	 */
	void registerObserver(DBCreateObserver o);

	/**
	 * Method is used to remove observer for database creation from current class.
	 * @param o Observer
	 */
	void removeObserver(DBCreateObserver o);

	/**
	 * Method is used to notify registered observer from current class.
	 * @param statusId Database creation status ID
	 */
	void notifyObserver(int statusId);
}
