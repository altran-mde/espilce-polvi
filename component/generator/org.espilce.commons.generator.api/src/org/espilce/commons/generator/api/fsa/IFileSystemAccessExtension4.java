/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.generator.api.fsa;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public interface IFileSystemAccessExtension4 {
	public @NonNull List<@NonNull String> findMatchingFiles(final @NonNull String parentPath);
	
	public @NonNull List<@NonNull String> findMatchingFiles(
			final @NonNull String parentPath,
			final @NonNull String outputConfigurationName
	);
}
