// Soin Software 2019
package com.soinsoftware.datamanager.bll;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.soinsoftware.datamanager.bll.hibernate.CompanyBll;
import com.soinsoftware.datamanager.guice.GuiceModule;
import com.soinsoftware.datamanager.guice.InjectorHelper;
import com.soinsoftware.datamanager.model.Company;

/**
 * @author Carlos Rodriguez
 * @since 13/08/2018
 */
public class CompanyBllTest {

	private CompanyBll bll;

	@Before
	public void setUp() throws Exception {
		InjectorHelper.createInjector(new GuiceModule());
		bll = InjectorHelper.getInstance(CompanyBll.class);
	}

	@Test
	public void selectAll() {
		final List<Company> entities = bll.findAll();
		assertNotNull(entities);
		assertNotSame(entities.size(), 0);
	}

	@Test
	public void selectEnabled() {
		final List<Company> entities = bll.findAll(true);
		assertNotNull(entities);
		assertNotSame(entities.size(), 0);
	}

	@Test
	public void selectByDocumentNotExists() {
		final Company entity = bll.find("900957626-1");
		assertNull(entity);
	}

	@Test
	public void selectByDocumentExists() {
		final Company entity = bll.find("900957626-2");
		assertNotNull(entity);
	}
}