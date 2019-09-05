/*******************************************************************************
 * Copyright (c) 2013 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.espilce.commons.generator.api.fsa;

import java.io.InputStream;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This class extends {@link IFileSystemAccess} with the capability to write
 * binary files.
 *
 * @author Moritz Eysholdt - Initial contribution and API
 * @since 2.4
 */
public interface IFileSystemAccessExtension3 {
	
	/**
	 * Writes binary data to disk. For writing text, it is recommended to use
	 * {@link IFileSystemAccess#generateFile(String, String, CharSequence)}
	 */
	void generateFile(
			final @NonNull String fileName,
			final @NonNull String outputCfgName,
			final @NonNull InputStream content
	) throws RuntimeIOException;
	
	/**
	 * Writes binary data to disk. For writing text, it is recommended to use
	 * {@link IFileSystemAccess#generateFile(String, CharSequence)}
	 */
	void generateFile(final @NonNull String fileName, final @NonNull InputStream content) throws RuntimeIOException;
	
	/**
	 * Creates an InputStream to read a binary file from disk. For text files,
	 * use {@link #readTextFile(String, String)} .
	 */
	public @NonNull InputStream readBinaryFile(final @NonNull String fileName, final @NonNull String outputCfgName)
			throws RuntimeIOException;
	
	/**
	 * Creates an InputStream to read a binary file from disk. For text files,
	 * use {@link #readTextFile(String)}.
	 */
	public @NonNull InputStream readBinaryFile(final @NonNull String fileName) throws RuntimeIOException;
	
	/**
	 * Reads a text file from disk. To read a binary file, use
	 * {@link #readBinaryFile(String, String)}.
	 */
	public @NonNull CharSequence readTextFile(final @NonNull String fileName, final @NonNull String outputCfgName)
			throws RuntimeIOException;
	
	/**
	 * Reads a text file from disk. To read a binary file, use
	 * {@link #readBinaryFile(String)}.
	 */
	public @NonNull CharSequence readTextFile(final @NonNull String fileName) throws RuntimeIOException;
}