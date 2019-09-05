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
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.xtext.generator.URIBasedFileSystemAccess;
import org.espilce.commons.emf.UriUtils;
import org.espilce.commons.lang.loadhelper.ILoadHelper;

import com.google.inject.Inject;

public class URIBasedFileSystemAccess4 extends URIBasedFileSystemAccess
		implements org.espilce.commons.emf.generator.api.fsa.IFileSystemAccess2,
		org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension4
{
	@Inject
	ILoadHelper loadHelper;
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(@NonNull final String parentPath) {
		return findMatchingFiles(parentPath, org.eclipse.xtext.generator.IFileSystemAccess.DEFAULT_OUTPUT);
	}
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(
			@NonNull final String parentPath,
			@NonNull final String outputConfigurationName
	) {
		final URI uri = getURI(parentPath, outputConfigurationName);
		final String path = uri.toString();
		
		return this.loadHelper.findMatchingResources(getClass(), path).stream()
				.map(UriUtils::asEmfUri).map(u -> u.resolve(uri).toString()).collect(Collectors.toList());
	}
}
