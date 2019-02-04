// Soin Software 2019
package com.soinsoftware.datamanager.guice;

import com.google.inject.ConfigurationException;
import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.ProvisionException;
import com.google.inject.Stage;

/**
 * @author Carlos Rodriguez
 * @since 04/02/2019
 *
 */
public class InjectorHelper {

	private static Injector injector;

	/**
	 * Creates an injector for the given set of modules. This is equivalent to
	 * calling {@link #createInjector(Stage, Module...)} with Stage.DEVELOPMENT.
	 *
	 * @throws CreationException
	 *             if one or more errors occur during injector construction
	 */
	public static void createInjector(Module... modules) {
		if (injector == null) {
			injector = Guice.createInjector(modules);
		}
	}

	/**
	 * Returns current injector object or throws
	 * {@link UnsupportedOperationException} if no instance was created before
	 */
	public static Injector getInstance() {
		if (injector == null) {
			throw new UnsupportedOperationException("Injector must be created before try to get an instance of it");
		}
		return injector;
	}

	/**
	 * Returns the appropriate instance for the given injection type; equivalent to
	 * {@code
	 * getProvider(type).get()}. When feasible, avoid using this method, in favor of
	 * having Guice inject your dependencies ahead of time.
	 *
	 * @throws ConfigurationException
	 *             if this injector cannot find or create the provider.
	 * @throws ProvisionException
	 *             if there was a runtime failure while providing an instance.
	 */
	public static <T> T getInstance(Class<T> type) {
		return getInstance().getInstance(type);
	}
}