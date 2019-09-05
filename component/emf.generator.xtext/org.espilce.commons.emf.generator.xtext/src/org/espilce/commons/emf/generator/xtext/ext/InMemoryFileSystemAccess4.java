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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;

public class InMemoryFileSystemAccess4 extends InMemoryFileSystemAccess
		implements org.espilce.commons.emf.generator.api.fsa.IFileSystemAccess2,
		org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension4
{
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(@NonNull final String parentPath) {
		return findMatchingFiles(parentPath, org.eclipse.xtext.generator.IFileSystemAccess.DEFAULT_OUTPUT);
	}
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(
			@NonNull final String parentPath,
			@NonNull final String outputConfigurationName
	) {
		final String fileName = getFileName(parentPath, outputConfigurationName);
		
		return getAllFiles().keySet().stream().filter(Objects::nonNull).filter(name -> name.startsWith(fileName))
				.collect(Collectors.toList());
	}
	
}
