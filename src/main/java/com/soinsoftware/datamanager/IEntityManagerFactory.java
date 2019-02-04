// Soin Software, 2018
package com.soinsoftware.datamanager;

import javax.persistence.EntityManager;

/**
 * Interface for creating entity manager factory that will help to create DAO
 * objects.
 * 
 * @author Carlos Rodriguez
 * @since 13/08/2018
 */
public interface IEntityManagerFactory {

	/**
	 * Called when entity manager factory will help to create DAO objects.
	 * 
	 * @return
	 */
	EntityManager createEntityManager();
}