package com.soinsoftware.datamanager.dao.hibernate;

import java.io.IOException;
import java.math.BigInteger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.soinsoftware.datamanager.PetCityManagerFactory;
import com.soinsoftware.datamanager.model.Company;

/**
 * @author Carlos Rodriguez
 * @since 13/08/2018
 */
@Singleton
public class CompanyDao extends AbstractHibernateDAO<Company, BigInteger> {

	@Inject
	public CompanyDao(PetCityManagerFactory factory) throws IOException {
		super(factory, Company.class);
	}

	public Company select(final String document) {
		return bySimpleNaturalId(document);
	}
}