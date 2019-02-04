// Soin Software 2019
package com.soinsoftware.datamanager.bll;

import java.io.Serializable;
import java.util.List;

/**
 * @author Carlos Rodriguez
 * @since 04/02/2019
 *
 */
public interface BusinessServiceable<T, P extends Serializable> {

	/**
	 * Returns a {@link List} with records loaded from database.
	 */
	List<T> findAll();

	/**
	 * Returns a {@link List} with records loaded from database filtered by its
	 * enabled column.
	 */
	List<T> findAll(boolean enabled);

	/**
	 * Returns the record if it was located using its primary key, or null
	 * otherwise.
	 */
	T getById(P pk);

	/**
	 * Saves the given entity in the database and commits the transaction.
	 */
	void save(final T entity);

	/**
	 * Saves the given entity in the database and commits the transaction only if it
	 * was indicated.
	 */
	void save(final T entity, final boolean mustCommit);

	/**
	 * Updates the given entity in the database and commits the transaction.
	 */
	void update(final T entity);

	/**
	 * Updates the given entity in the database and commits the transaction only if
	 * it was indicated.
	 */
	void update(final T entity, final boolean mustCommit);

	/**
	 * Executes the rollback process on the current transaction.
	 */
	void cancelSaveAction();

	/**
	 * Commits the current transaction.
	 */
	void commit();
}