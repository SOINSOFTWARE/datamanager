// Soin Software, 2019
package com.soinsoftware.datamanager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * This class builds the {@link EntityManagerFactory} object using the provided
 * data passed as parameters to the constructor method.
 * 
 * @author Carlos Rodriguez
 * @since 13/08/2018
 */
public abstract class AbstractManagerFactory implements IEntityManagerFactory {

	private Logger log = Logger.getLogger(AbstractManagerFactory.class);
	protected EntityManagerFactory emf;

	/**
	 * Constructor that must be called for all classes that implements
	 * {@link AbstractManagerFactory} class.
	 * 
	 * @param packageToScan
	 *            Package name that contains the classes with table
	 *            mapping(model).
	 * @param persistenceName
	 *            Name of persistence that will be loaded, it's only used to
	 *            improve description in log.
	 * @param propFile
	 *            Property file name including path if it is not stored in
	 *            src/main/resources source folder.
	 * @param propFile
	 *            Property file name including path if it is not stored in
	 *            src/main/resources source folder.
	 * 
	 * @throws IOException
	 *             If {@link EntityManagerFactory} could not be created.
	 */
	public AbstractManagerFactory(final String packageToScan, final String persistenceName, final String propFile,
			final boolean loadAsResource) throws IOException {
		super();
		log.info("Before try to build entity manager factory");
		log.info("Package with database model: " + packageToScan);
		log.info("Property file: " + propFile);
		buildEntityManagerFactory(packageToScan, persistenceName, propFile, loadAsResource);
	}

	@Override
	public EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Builds {@link EntityManagerFactory} object using the configuration
	 * provided.<br>
	 * <br>
	 * Review {@link HikariConfig} if you need any help with property file
	 * attributes.<br>
	 * <br>
	 * Check package name if tables could not be loaded. *
	 * 
	 * @param packageToScan
	 *            Package name that contains the classes with table
	 *            mapping(model).
	 * @param persistenceName
	 *            Name of persistence that will be loaded, it's only used to
	 *            improve description in log.
	 * @param loadAsResource
	 *            Indicates if the property file path provided to this method
	 *            must be loaded as a resource located inside class path or it
	 *            is with file full path information.
	 * @param propFile
	 *            Property file name including path if it is not stored in
	 *            src/main/resources source folder.
	 * 
	 * @throws IOException
	 *             If {@link EntityManagerFactory} could not be created.
	 */
	private void buildEntityManagerFactory(final String packageToScan, final String persistenceName,
			final String propFile, final boolean loadAsResource) throws IOException {
		final HikariConfig config = new HikariConfig(loadPropertyFile(propFile, loadAsResource));
		config.setMaxLifetime(600000);
		final HikariDataSource dataSource = new HikariDataSource(config);
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(packageToScan);
		em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		em.setPersistenceUnitName(persistenceName);
	
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.afterPropertiesSet();
		emf = em.getObject();
		log.info("After building entity manager factory");
	}

	/**
	 * Loads {@link Properties} object using property file path provided as
	 * parameter.
	 * 
	 * @param propFile
	 *            Property file name including path if it is not stored in
	 *            src/main/resources source folder.
	 * 
	 * @param loadAsResource
	 *            Indicates if the property file path provided to this method
	 *            must be loaded as a resource located inside class path or it
	 *            is with file full path information.
	 * @return {@link Properties} object
	 * 
	 * @throws IOException
	 *             If property file could not located or there is an error in
	 *             its defined attributes.
	 */
	private Properties loadPropertyFile(final String propFile, final boolean loadAsResource) throws IOException {
		final Properties properties = new Properties();
		if (loadAsResource) {
			properties.load(AbstractManagerFactory.class.getResourceAsStream(propFile));
		} else {
			properties.load(new FileReader(propFile));
		}
		log.info("Property file loaded successfuly");
		return properties;
	}
}
