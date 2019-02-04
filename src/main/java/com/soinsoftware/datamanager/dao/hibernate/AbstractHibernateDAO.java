// Soin Software 2019
package com.soinsoftware.datamanager.dao.hibernate;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.soinsoftware.datamanager.AbstractManagerFactory;
import com.soinsoftware.datamanager.dao.DataAccessibleObject;

/**
 * @author Carlos Rodriguez
 * @since 04/02/2019
 */
public abstract class AbstractHibernateDAO<T, P extends Serializable> implements DataAccessibleObject<T, P> {

	private final Logger log = Logger.getLogger(AbstractHibernateDAO.class);
	private final EntityManager manager;
	private final Class<T> clazz;
	private static Session session;

	/**
	 * Constructor that must be called for all DAO implementations.
	 * 
	 * @throws IOException
	 *             when the factory fails to create an {@link EntityManager}
	 */
	public AbstractHibernateDAO(AbstractManagerFactory factory, final Class<T> clazz) throws IOException {
		super();
		this.manager = factory.createEntityManager();
		this.clazz = clazz;
	}

	@Override
	public T selectById(P pk) {
		return manager.find(clazz, pk);
	}

	@Override
	public List<T> selectAll() {
		return getResultList(buildCriteria());
	}

	@Override
	public List<T> selectAll(final boolean enabled) {
		final CriteriaQuery<T> criteria = buildCriteria();
		criteria.where(buildEnabledPredicate(criteria, enabled));
		return getResultList(criteria);
	}

	@Override
	public void persist(T record) {
		try {
			persist(getSession().getTransaction(), record);
		} finally {
			getSession().getTransaction().commit();
			getSession().flush();
		}
	}

	@Override
	public void persist(final Transaction transaction, final T record) {
		log.info("Persisting object");
		if (!transaction.getStatus().equals(TransactionStatus.ACTIVE)) {
			transaction.begin();
		}
		getSession().persist(record);
		getSession().flush();
	}

	@Override
	public void update(T record) {
		try {
			update(getSession().getTransaction(), record);
		} finally {
			getSession().getTransaction().commit();
			getSession().flush();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(final Transaction transaction, final T record) {
		log.info("Updating object");
		if (!transaction.getStatus().equals(TransactionStatus.ACTIVE)) {
			transaction.begin();
		}
		T merged = (T) getSession().merge(record);
		persist(transaction, merged);
	}

	@Override
	public void delete(final T record) {
		log.info("Deleting object: " + record.toString());
		getSession().delete(record);
		getSession().getTransaction().commit();
		getSession().flush();
	}

	@Override
	public void rollbackTransaction() {
		Transaction transaction = getSession().getTransaction();
		if (transaction != null) {
			transaction.rollback();
		}
	}

	@Override
	public void close() {
		manager.close();
	}

	@Override
	public CriteriaQuery<T> buildCriteria() {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		return query;
	}

	@Override
	public Session getSession() {
		if (session == null || !session.isOpen()) {
			session = manager.unwrap(Session.class);
		}
		return session;
	}

	/**
	 * Execute a SELECT query and return the query results as a typed List.
	 */
	public List<T> getResultList(CriteriaQuery<T> criteria) {
		Query<T> query = getSession().createQuery(buildCriteria());
		return query.getResultList();
	}

	/**
	 * Convenience method to return a single instance that matches the query, or
	 * null if the query returns no results.
	 */
	public T uniqueResult(CriteriaQuery<T> criteria) {
		Query<T> query = getSession().createQuery(buildCriteria());
		return query.uniqueResult();
	}

	/**
	 * Return the persistent instance with the given natural id value, or null if
	 * there is no such persistent instance. If the instance is already associated
	 * with the session, return that instance, initializing it if needed. This
	 * method never returns an uninitialized instance.
	 */
	public T bySimpleNaturalId(String naturalId) {
		return getSession().bySimpleNaturalId(clazz).load(naturalId);
	}

	/**
	 * Create a predicate for testing the enabled column.
	 */
	public Predicate buildEnabledPredicate(CriteriaQuery<T> criteria, boolean enabled) {
		Root<T> root = (Root<T>) criteria.getSelection();
		return getSession().getCriteriaBuilder().equal(root.get("enabled"), enabled);
	}
}