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

import com.google.inject.AbstractModule;

import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.registry.Registry;

/**
 * The model module. Provides the registry and the model.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ModelModule
		extends AbstractModule {

	/**
	 * Default constructor.
	 */
	public ModelModule() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	protected void configure() {
		this.bind(Registry.class).to(GuiceRegistry.class);
		this.bind(Model.class).toProvider(ModelProvider.class);
	}
}
