// Soin Software 2019
package com.soinsoftware.datamanager.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Interface for creating DAO objects.
 *
 * @param <T>
 *            Class that represents the table model.
 * @param <P>
 *            Class that represents the primary key.
 * 
 * @author Carlos Rodriguez
 * @since 04/02/2019
 */
public interface DataAccessibleObject<T, P extends Serializable> {

	/**
	 * Returns a {@link List} with records loaded from database.
	 */
	List<T> selectAll();

	/**
	 * Returns a {@link List} with records loaded from database filtered by its
	 * enabled column.
	 */
	List<T> selectAll(boolean enabled);

	/**
	 * Returns the record if it was located using its primary key, or null
	 * otherwise.
	 */
	T selectById(P pk);

	/**
	 * Persists the given record in the database.
	 */
	void persist(T record);

	/**
	 * Persists the given record in the database using the specified
	 * {@link Transaction}.
	 */
	void persist(Transaction transaction, T record);

	/**
	 * Updates the given record in the database.
	 */
	void update(T record);

	/**
	 * Updates the given record in the database using the specified
	 * {@link Transaction}.
	 */
	void update(Transaction transaction, T record);

	/**
	 * Deletes the given record from the database.
	 */
	void delete(final T record);

	/**
	 * When using transaction to store data, use this method to roll back
	 * transaction if anything goes wrong before finish it.
	 */
	void rollbackTransaction();

	/**
	 * Close an application-managed entity manager.
	 */
	void close();

	/**
	 * Builds an criteria object used to return data.
	 */
	CriteriaQuery<T> buildCriteria();

	/**
	 * Returns the current session or creates a new one.
	 */
	Session getSession();
}