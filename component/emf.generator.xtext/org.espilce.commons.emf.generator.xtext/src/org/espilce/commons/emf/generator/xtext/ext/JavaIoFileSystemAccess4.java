/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.generator.xtext.ext;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;

import com.google.inject.Inject;

public class JavaIoFileSystemAccess4 extends JavaIoFileSystemAccess
		implements org.espilce.commons.emf.generator.api.fsa.IFileSystemAccess2,
		org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension4
{
	@Inject
	private FilesystemClassloaderLoadHelper loadHelper;
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(@NonNull final String parentPath) {
		return findMatchingFiles(parentPath, org.eclipse.xtext.generator.IFileSystemAccess.DEFAULT_OUTPUT);
	}
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(
			@NonNull final String parentPath,
			@NonNull final String outputConfigurationName
	) {
		final File file = getFile(parentPath, outputConfigurationName);
		final Path path = file.toPath();
		return this.loadHelper.findMatchingResources(getClass(), path.toString()).stream()
				.map(this::toPath)
				.filter(Objects::nonNull)
				.map(p -> p.resolve(path).toString()).collect(Collectors.toList());
	}
	
	protected Path toPath(final URL url) {
		try {
			return Paths.get(url.toURI());
		} catch (final URISyntaxException e) {
			return null;
		}
	}
}
