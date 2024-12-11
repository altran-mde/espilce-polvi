/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.espilce.polvi.emf.generator.api;

import org.eclipse.emf.ecore.resource.Resource;
import org.espilce.polvi.emf.generator.api.fsa.IFileSystemAccess2;
import org.espilce.polvi.generator.api.context.IGeneratorContext;

/**
 * @since 2.9
 */
public abstract class AbstractGenerator implements IGenerator2 {

	@Override
	public void beforeGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
	}

	@Override
	public void afterGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
	}

}
