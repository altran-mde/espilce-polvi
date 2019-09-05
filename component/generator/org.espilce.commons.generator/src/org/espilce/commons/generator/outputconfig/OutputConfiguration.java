/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.espilce.commons.generator.outputconfig;

import java.util.LinkedHashSet;
import java.util.Set;

import org.espilce.commons.generator.api.outputconfig.IOutputConfiguration;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @since 2.1
 */
public class OutputConfiguration implements IOutputConfiguration {
	
	private final String name;
	private String description;
	private String outputDirectory;
	private boolean createOutputDirectory;
	private boolean cleanUpDerivedResources = true;
	private boolean overrideExistingResources = true;
	private boolean setDerivedProperty = true;
	private boolean canClearOutputDirectory = false;
	private boolean installDslAsPrimarySource = false;
	private boolean hideSyntheticLocalVariables = true;
	private boolean keepLocalHistory = false;
	private boolean useOutputPerSourceFolder = false;
	private final Set<SourceMapping> sourceMappings = new LinkedHashSet<>();
	
	/**
	 * @param name
	 *            - a unique name identifying this outlet configuration.
	 */
	public OutputConfiguration(final String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String getName() { return this.name; }
	
	@Override
	public String getDescription() { return this.description; }
	
	@Override
	public void setDescription(final String description) { this.description = description; }
	
	@Override
	public String getOutputDirectory() { return this.outputDirectory; }
	
	@Override
	public void setOutputDirectory(final String outputDirectory) { this.outputDirectory = outputDirectory; }
	
	@Override
	public boolean isCleanUpDerivedResources() { return this.cleanUpDerivedResources; }
	
	@Override
	public void setCleanUpDerivedResources(final boolean cleanUpDerivedResources) {
		this.cleanUpDerivedResources = cleanUpDerivedResources;
	}
	
	@Override
	public boolean isOverrideExistingResources() { return this.overrideExistingResources; }
	
	@Override
	public void setOverrideExistingResources(final boolean overrideExistingResources) {
		this.overrideExistingResources = overrideExistingResources;
	}
	
	@Override
	public boolean isSetDerivedProperty() { return this.setDerivedProperty; }
	
	@Override
	public void setSetDerivedProperty(final boolean setDerivedProperty) {
		this.setDerivedProperty = setDerivedProperty;
	}
	
	@Override
	public boolean isCreateOutputDirectory() { return this.createOutputDirectory; }
	
	@Override
	public void setCreateOutputDirectory(final boolean createOutputDirectory) {
		this.createOutputDirectory = createOutputDirectory;
	}
	
	@Override
	public boolean isCanClearOutputDirectory() { return this.canClearOutputDirectory; }
	
	@Override
	public void setCanClearOutputDirectory(final boolean canClearOutputDirectory) {
		this.canClearOutputDirectory = canClearOutputDirectory;
	}
	
	@Override
	public boolean isInstallDslAsPrimarySource() { return this.installDslAsPrimarySource; }
	
	@Override
	public void setInstallDslAsPrimarySource(final boolean installDslAsPrimarySource) {
		this.installDslAsPrimarySource = installDslAsPrimarySource;
	}
	
	@Override
	public boolean isHideSyntheticLocalVariables() { return this.hideSyntheticLocalVariables; }
	
	@Override
	public void setHideSyntheticLocalVariables(final boolean hideSyntheticLocalVariables) {
		this.hideSyntheticLocalVariables = hideSyntheticLocalVariables;
	}
	
	@Override
	public boolean isKeepLocalHistory() { return this.keepLocalHistory; }
	
	@Override
	public void setKeepLocalHistory(final boolean keepLocalHistory) { this.keepLocalHistory = keepLocalHistory; }
	
	@Override
	public boolean isUseOutputPerSourceFolder() { return this.useOutputPerSourceFolder; }
	
	@Override
	public void setUseOutputPerSourceFolder(final boolean useOutputPerSourceFolder) {
		this.useOutputPerSourceFolder = useOutputPerSourceFolder;
	}
	
	@Override
	public Set<SourceMapping> getSourceMappings() { return this.sourceMappings; }
	
	@Override
	public String getOutputDirectory(final String sourceFolder) {
		if (this.useOutputPerSourceFolder) {
			for (final SourceMapping mapping : this.sourceMappings) {
				if (mapping.getSourceFolder().equals(sourceFolder) && mapping.getOutputDirectory() != null) {
					return mapping.getOutputDirectory();
				}
			}
		}
		return getOutputDirectory();
	}
	
	@Override
	public Set<String> getSourceFolders() {
		final Set<String> sourceFolders = new LinkedHashSet<>();
		for (final SourceMapping mapping : getSourceMappings()) {
			sourceFolders.add(mapping.getSourceFolder());
		}
		return sourceFolders;
	}
	
	@Override
	public Set<String> getOutputDirectories() {
		final Set<String> outputDirectories = new LinkedHashSet<>();
		if (isUseOutputPerSourceFolder()) {
			for (final SourceMapping mapping : getSourceMappings()) {
				outputDirectories.add(getOutputDirectory(mapping.getSourceFolder()));
			}
		} else {
			outputDirectories.add(getOutputDirectory());
		}
		return outputDirectories;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final OutputConfiguration other = (OutputConfiguration) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return getName() + ": " + getOutputDirectory() + " (" + getDescription() + ")";
	}
}
