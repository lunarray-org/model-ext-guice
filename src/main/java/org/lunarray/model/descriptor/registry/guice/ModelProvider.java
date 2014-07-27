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

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.ResourceException;

/**
 * The model provider. Provides a model based on the registry and a resource.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ModelProvider
		implements Provider<Model<?>> {

	/** The model. */
	private Model<?> model;

	/**
	 * Constructs the model provider.
	 * 
	 * @param resource
	 *            The model resource.
	 * @param registry
	 *            The registry.
	 * @throws ResourceException
	 *             Thrown if the provider could not be created.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Inject
	public ModelProvider(final Resource resource, final Registry registry) throws ResourceException {
		this.model = SimpleBuilder.createBuilder().resources(resource).extensions(registry).build();
	}

	/** {@inheritDoc} */
	@Override
	public Model<?> get() {
		return this.model;
	}

	/**
	 * Gets the value for the model field.
	 * 
	 * @return The value for the model field.
	 */
	public Model<?> getModel() {
		return this.model;
	}

	/**
	 * Sets a new value for the model field.
	 * 
	 * @param model
	 *            The new value for the model field.
	 */
	public void setModel(final Model<?> model) {
		this.model = model;
	}
}
