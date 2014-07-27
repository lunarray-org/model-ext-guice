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
package org.lunarray.model.descriptor.registry.guice.modules;

import com.google.inject.AbstractModule;

import org.lunarray.model.descriptor.dictionary.Dictionary;
import org.lunarray.model.descriptor.dictionary.composite.registry.CompositeRegistryDictionary;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.lunarray.model.descriptor.objectfactory.simple.SimpleObjectFactory;
import org.lunarray.model.descriptor.registry.guice.model.Entity01;
import org.lunarray.model.descriptor.registry.guice.model.Entity02;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;

/**
 * Test model with base extensions.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class ExtensionsModule
		extends AbstractModule {

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected void configure() {
		this.bind(Resource.class).toInstance(new SimpleClazzResource<Object>(Entity01.class, Entity02.class));
		this.bind(ObjectFactory.class).to(SimpleObjectFactory.class);
		this.bind(Dictionary.class).to(CompositeRegistryDictionary.class);
	}
}
