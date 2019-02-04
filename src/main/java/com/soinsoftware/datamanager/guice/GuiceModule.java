package com.soinsoftware.datamanager.guice;

import com.google.inject.AbstractModule;
import com.soinsoftware.datamanager.AbstractManagerFactory;
import com.soinsoftware.datamanager.PetCityManagerFactory;
import com.soinsoftware.datamanager.bll.hibernate.AbstractBll;
import com.soinsoftware.datamanager.bll.hibernate.CompanyBll;
import com.soinsoftware.datamanager.dao.DataAccessibleObject;
import com.soinsoftware.datamanager.dao.hibernate.CompanyDao;

public class GuiceModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(AbstractManagerFactory.class).to(PetCityManagerFactory.class);
		bind(DataAccessibleObject.class).to(CompanyDao.class);
		bind(AbstractBll.class).to(CompanyBll.class);
	}
}