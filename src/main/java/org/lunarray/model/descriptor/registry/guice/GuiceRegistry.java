/* 
 * Model Tools.
 * Copyright (C) 2013 Pal Hargitai (pal@lunarray.org)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lunarray.model.descriptor.registry.guice;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import com.google.inject.Binding;
import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements a registry using guice.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class GuiceRegistry
		implements Registry<Key<?>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GuiceRegistry.class);
	/** The guice injector. */
	private final transient Injector injector;

	/**
	 * Default constructor.
	 * 
	 * @param injector
	 *            The injector to use for lookups.
	 */
	@Inject
	public GuiceRegistry(final Injector injector) {
		this.injector = injector;
	}

	/** {@inheritDoc} */
	@Override
	public <B> B lookup(final Class<B> clazz) throws RegistryException {
		GuiceRegistry.LOGGER.debug("Looking up bean with type {}.", clazz);
		if (CheckUtil.isNull(clazz)) {
			throw new RegistryException("Clazz may not be null.");
		}
		try {
			return this.injector.getInstance(clazz);
		} catch (final ConfigurationException e) {
			throw new RegistryException(String.format("Could not find bean with type '%s'.", clazz), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public <B> B lookup(final Class<B> clazz, final Key<?> name) throws RegistryException {
		GuiceRegistry.LOGGER.debug("Looking up bean with type {} with name '{}'.", clazz, name);
		if (CheckUtil.isNull(name) || CheckUtil.isNull(clazz)) {
			throw new RegistryException("Clazz and name may not be null.");
		}
		try {
			final Object instance = this.injector.getInstance(name);
			if (clazz.isInstance(instance)) {
				return clazz.cast(instance);
			} else {
				throw new RegistryException(String.format("Could not find bean with name '%s', found type '%s', expected '%s'.", name,
						instance.getClass(), clazz));
			}
		} catch (final ConfigurationException e) {
			throw new RegistryException(String.format("Could not find bean with name '%s' and type '%s'.", name, clazz), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Object lookup(final Key<?> name) throws RegistryException {
		GuiceRegistry.LOGGER.debug("Looking up bean with name '{}'.", name);
		if (CheckUtil.isNull(name)) {
			throw new RegistryException("Name may not be null.");
		}
		try {
			return this.injector.getInstance(name);
		} catch (final ConfigurationException e) {
			throw new RegistryException(String.format("Could not find bean with name '%s'.", name), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public <B> Set<Key<?>> lookupAll(final Class<B> clazz) throws RegistryException {
		GuiceRegistry.LOGGER.debug("Looking up all beans with type {}.", clazz);
		if (CheckUtil.isNull(clazz)) {
			throw new RegistryException("Bean type may not be null.");
		}
		final Map<Key<?>, Binding<?>> bindings = this.injector.getAllBindings();
		final Set<Key<?>> result = new HashSet<Key<?>>();
		for (final Map.Entry<Key<?>, Binding<?>> binding : bindings.entrySet()) {
			if (clazz.isAssignableFrom(binding.getKey().getTypeLiteral().getRawType())) {
				result.add(binding.getKey());
			}
		}
		return result;
	}
}
