/*******************************************************************************
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.espilce.commons.emf.generator.api.fsa;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.annotation.NonNull;;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.3
 */
public interface IFileSystemAccessExtension2 {
	
	/**
	 * Returns an URI for the specified path.
	 *
	 * For workspace resources a platform:/resource URI should be returned in
	 * order to pick up project specific preferences.
	 */
	public @NonNull URI getURI(final @NonNull String path, final @NonNull String outputConfiguration);
	
	/**
	 * Returns an URI for the specified path in the default output location.
	 *
	 * For workspace resources a platform:/resource URI should be returned in
	 * order to pick up project specific preferences.
	 */
	public @NonNull URI getURI(final @NonNull String path);
	
}
