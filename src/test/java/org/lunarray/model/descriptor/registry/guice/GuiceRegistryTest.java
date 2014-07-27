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

import java.util.Set;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.converter.Converter;
import org.lunarray.model.descriptor.dictionary.Dictionary;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;
import org.lunarray.model.descriptor.registry.guice.model.Entity01;
import org.lunarray.model.descriptor.registry.guice.modules.ExtensionsModule;

/**
 * Tests the guice registry.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see GuiceRegistry
 */
public class GuiceRegistryTest {

	/** The model. */
	private Model<Object> model;
	/** The registry. */
	private Registry<Key<?>> registry;

	/** Setup the test. */
	@Before
	@SuppressWarnings("unchecked")
	public void setup() {
		final Injector injector = Guice.createInjector(new ExtensionsModule(), new ModelModule());
		this.model = injector.getInstance(Model.class);
		this.registry = injector.getInstance(Registry.class);
	}

	/**
	 * Do a lookup of a bound type but with an unbound key.
	 * 
	 * @see Registry#lookup(Class, Object)
	 */
	@Test(expected = RegistryException.class)
	public void testBoundTypeNonExistentKey() throws RegistryException {
		this.registry.lookup(Dictionary.class, Key.get(Dictionary.class, Names.named("nonexistant")));
		Assert.fail();
	}

	/**
	 * Test with an empty key.
	 * 
	 * @see Registry#lookup(Object)
	 */
	@Test(expected = RegistryException.class)
	public void testEmptyKey() throws RegistryException {
		this.registry.lookup((Key<?>) null);
		Assert.fail();
	}

	/**
	 * Test lookup with a key.
	 * 
	 * @see Registry#lookup(Object)
	 */
	@Test
	public void testLookupAfterLookupAll() throws RegistryException {
		final Set<Key<?>> beans = this.registry.lookupAll(Dictionary.class);
		Assert.assertNotNull(this.registry.lookup(beans.iterator().next()));
	}

	/**
	 * Test lookup of all.
	 * 
	 * @see Registry#lookupAll(Class)
	 */
	@Test
	public void testLookupAll() throws RegistryException {
		final Set<Key<?>> beans = this.registry.lookupAll(Dictionary.class);
		Assert.assertEquals(2, beans.size());
	}

	/**
	 * Test empty result set of not registered type.
	 * 
	 * @see Registry#lookupAll(Class)
	 */
	@Test
	public void testLookupAllEmpty() throws RegistryException {
		Assert.assertTrue(this.registry.lookupAll(Converter.class).isEmpty());
	}

	/**
	 * Test empty key and empty type.
	 * 
	 * @see Registry#lookup(Class, Object)
	 */
	@Test(expected = RegistryException.class)
	public void testLookupEmptyTypeEmptyKey() throws RegistryException {
		this.registry.lookup(null, null);
		Assert.fail();
	}

	/**
	 * Test with a non existent key.
	 * 
	 * @see Registry#lookup(Object)
	 */
	@Test(expected = RegistryException.class)
	public void testLookupExceptions() throws RegistryException {
		this.registry.lookup(Key.get(Dictionary.class, Names.named("nonexistant")));
		Assert.fail();
	}

	/**
	 * Test simple lookup.
	 * 
	 * @see Registry#lookup(Class)
	 */
	@Test
	public void testLookups() throws RegistryException {
		Assert.assertNotNull(this.registry.lookup(Dictionary.class));
	}

	/**
	 * Test empty type.
	 * 
	 * @see Registry#lookup(Class)
	 */
	@Test(expected = RegistryException.class)
	public void testLookupTypeEmpty() throws RegistryException {
		this.registry.lookup((Class<?>) null);
		Assert.fail();
	}

	/**
	 * Test a not registered type.
	 * 
	 * @see Registry#lookup(Class)
	 */
	@Test(expected = RegistryException.class)
	public void testLookupTypeUnfound() throws RegistryException {
		this.registry.lookup(Converter.class);
		Assert.fail();
	}

	/**
	 * Test not registered type with an empty key.
	 * 
	 * @see Registry#lookup(Class, Object)
	 */
	@Test(expected = RegistryException.class)
	public void testLookupTypeUnfoundEmptyKey() throws RegistryException {
		this.registry.lookup(Converter.class, null);
		Assert.fail();
	}

	/**
	 * Test the model base.
	 * 
	 * @see Model#getEntity(Class)
	 */
	@Test
	public void testModel() throws Exception {
		Assert.assertNotNull(this.model.getEntity(Entity01.class));
	}

	/**
	 * Test base registry lookup.
	 * 
	 * @see Model#getExtension(Class)
	 */
	@Test
	public void testSimpleLookup() throws Exception {
		Assert.assertNotNull(this.model.getExtension(ObjectFactory.class));
	}

	/**
	 * Test typed keyed lookup.
	 * 
	 * @see Registry#lookup(Class, Object)
	 */
	@Test
	public void testTypedLookupAfterLookupAll() throws RegistryException {
		final Set<Key<?>> beans = this.registry.lookupAll(Dictionary.class);
		Assert.assertEquals(2, beans.size());
		Assert.assertNotNull(this.registry.lookup(Dictionary.class, beans.iterator().next()));
	}
}
