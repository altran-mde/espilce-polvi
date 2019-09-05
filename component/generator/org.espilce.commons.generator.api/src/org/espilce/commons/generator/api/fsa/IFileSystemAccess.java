/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.espilce.commons.generator.api.fsa;

import org.eclipse.jdt.annotation.NonNull;

/**
 * @author Sven Efftinge - Initial contribution and API
 */
public interface IFileSystemAccess {
	
	public final static String DEFAULT_OUTPUT = "DEFAULT_OUTPUT";
	
	/**
	 * @param fileName
	 *            using '/' as file separator
	 * @param contents
	 *            the to-be-written contents.
	 */
	public void generateFile(final @NonNull String fileName, final @NonNull CharSequence contents);
	
	/**
	 * @param fileName
	 *            using '/' as file separator
	 * @param outputConfigurationName
	 *            the name of the output configuration
	 * @param contents
	 *            the to-be-written contents.
	 */
	public void generateFile(
			final @NonNull String fileName,
			final @NonNull String outputConfigurationName,
			final @NonNull CharSequence contents
	);
	
	/**
	 * @param fileName
	 *            using '/' as file separator
	 */
	public void deleteFile(final @NonNull String fileName);
	
}
