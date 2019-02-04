// Soin Software 2019
package com.soinsoftware.datamanager.bll.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.soinsoftware.datamanager.bll.BusinessServiceable;
import com.soinsoftware.datamanager.dao.DataAccessibleObject;
import com.soinsoftware.datamanager.model.CommonData;

/**
 * @author Carlos Rodriguez
 * @since 04/02/2019
 */
public abstract class AbstractBll<T, P extends Serializable> implements BusinessServiceable<T, P> {

	private final Logger log = Logger.getLogger(AbstractBll.class);
	protected DataAccessibleObject<T, P> dao;

	public AbstractBll(DataAccessibleObject<T, P> dao) {
		super();
		this.dao = dao;
	}

	@Override
	public List<T> findAll() {
		return dao.selectAll();
	}

	@Override
	public List<T> findAll(boolean enabled) {
		return dao.selectAll(enabled);
	}

	@Override
	public T getById(P pk) {
		return (T) dao.selectById(pk);
	}

	@Override
	public void save(final T entity) {
		save(entity, true);
	}

	@Override
	public void save(final T entity, final boolean mustCommit) {
		((CommonData) entity).validate();
		if (((CommonData) entity).isNew()) {
			log.info("persisting entity");
			if (mustCommit) {
				dao.persist(entity);
			} else {
				dao.persist(getCurrentTransaction(), entity);
			}
		} else {
			log.info("updating entity");
			if (mustCommit) {
				dao.update(entity);
			} else {
				dao.update(getCurrentTransaction(), entity);
			}
		}
	}

	@Override
	public void update(final T entity) {
		update(entity, true);
	}

	@Override
	public void update(final T entity, final boolean mustCommit) {
		if (mustCommit) {
			dao.update(entity);
		} else {
			dao.update(getCurrentTransaction(), entity);
		}
	}

	@Override
	public void cancelSaveAction() {
		dao.rollbackTransaction();
	}

	@Override
	public void commit() {
		getCurrentTransaction().commit();
		dao.getSession().flush();
	}

	/**
	 * Returns the current transaction from the {@link Session}
	 */
	private Transaction getCurrentTransaction() {
		return dao.getSession().getTransaction();
	}
}