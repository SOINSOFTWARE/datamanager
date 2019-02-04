package com.soinsoftware.datamanager;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class provides the {@link EntityManagerFactory} to connect to database.
 * 
 * @author Carlos Rodriguez
 * @since 13/08/2018
 */
@Singleton
public class PetCityManagerFactory extends AbstractManagerFactory {

	private static final String PACKAGE_INFO = "com.soinsoftware.datamanager.model";

	private static final String PERSISTENCE_UNIT_NAME = "PetCity";

	private static final String PROPERTY_FILE = "/connection.properties";

	@Inject
	public PetCityManagerFactory() throws IOException {
		super(PACKAGE_INFO, PERSISTENCE_UNIT_NAME, PROPERTY_FILE, true);
	}

	public PetCityManagerFactory(final String propertyFilePath) throws IOException {
		super(PACKAGE_INFO, PERSISTENCE_UNIT_NAME, propertyFilePath, false);
	}
}