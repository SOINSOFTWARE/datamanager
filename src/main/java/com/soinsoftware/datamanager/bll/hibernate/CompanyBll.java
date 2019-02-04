package com.soinsoftware.datamanager.bll.hibernate;

import java.io.IOException;
import java.math.BigInteger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.soinsoftware.datamanager.dao.hibernate.CompanyDao;
import com.soinsoftware.datamanager.model.Company;

/**
 * @author Carlos Rodriguez
 * @since 13/08/2018
 */
@Singleton
public class CompanyBll extends AbstractBll<Company, BigInteger> {
	
	@Inject
	public CompanyBll(CompanyDao dao) throws IOException {
		super(dao);
	}

	public Company find(final String document) {
		return ((CompanyDao) dao).select(document);
	}
}