/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.espilce.commons.generator.outputconfig;

import java.util.Objects;

import org.espilce.commons.generator.api.outputconfig.ISourceMapping;

/**
 * Specifies the output folder for a source folder (may be null, in which case
 * the output folder of the enclosing {@link OutputConfiguration} is used). A
 * source folder may also be set to be ignored in the UI. This makes it clear to
 * the user that he does not have to specify an output directory for that source
 * folder.
 *
 * Both source and output folders are project relative, e.g. "src/main/java",
 * "build/gen" etc.
 *
 * @since 2.6
 */
public class SourceMapping implements ISourceMapping {
	private final String sourceFolder;
	private String outputDirectory;
	private boolean ignore;
	
	public SourceMapping(final String sourceFolder) {
		this.sourceFolder = Objects.requireNonNull(sourceFolder);
	}
	
	@Override
	public String getSourceFolder() { return this.sourceFolder; }
	
	@Override
	public String getOutputDirectory() { return this.outputDirectory; }
	
	@Override
	public void setOutputDirectory(final String outputDirectory) { this.outputDirectory = outputDirectory; }
	
	@Override
	public boolean isIgnore() { return this.ignore; }
	
	@Override
	public void setIgnore(final boolean ignore) { this.ignore = ignore; }
	
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof SourceMapping) {
			final SourceMapping other = (SourceMapping) obj;
			return Objects.equals(this.sourceFolder, other.sourceFolder);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.sourceFolder);
	}
}
