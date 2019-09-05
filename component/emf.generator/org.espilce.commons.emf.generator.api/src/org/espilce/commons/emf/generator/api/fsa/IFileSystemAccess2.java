/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.espilce.commons.emf.generator.api.fsa;

import org.eclipse.jdt.annotation.NonNull;
import org.espilce.commons.generator.api.fsa.IFileSystemAccess;
import org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension;
import org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension3;
import org.espilce.commons.generator.api.fsa.RuntimeIOException;

/**
 * Abstraction for file system operations with the added value of a logical path
 * mapping (outlet).
 *
 * This interface composes all the extension interfaces to
 * {@link IFileSystemAccess}. Further enhancements will be implemented directly
 * in this interface.
 *
 * @author Sebastian Zarnekow - Initial contribution and API
 * @noimplement This interface is not intended to be implemented by clients.
 * @since 2.9
 */
public interface IFileSystemAccess2 extends IFileSystemAccess, IFileSystemAccessExtension, IFileSystemAccessExtension2,
		IFileSystemAccessExtension3
{
	
	/**
	 * Tests whether the file exists at the location denoted by the output
	 * configuration. Returns {@code true} if the file at the described location
	 * exists and is a normal file (not a directory). Otherwise {@code false}.
	 *
	 * @param path
	 *            using '/' as path separator
	 * @param outputConfigurationName
	 *            the name of the output configuration
	 * @return <code>true</code> when the file at the given path exists and is a
	 *         normal file. Will return <code>false</code> when the path belongs
	 *         to a directory.
	 */
	boolean isFile(final @NonNull String path, final @NonNull String outputConfigurationName) throws RuntimeIOException;
	
	/**
	 * Tests whether the file exists at the location in the default output
	 * configuration. Returns {@code true} if the file at the described location
	 * exists and is a normal file (not a directory). Otherwise {@code false}.
	 *
	 * @param path
	 *            using '/' as path separator
	 * @return <code>true</code> when the file at the given path exists and is a
	 *         normal file. Will return <code>false</code> when the path belongs
	 *         to a directory.
	 */
	boolean isFile(final @NonNull String path) throws RuntimeIOException;
	
}
