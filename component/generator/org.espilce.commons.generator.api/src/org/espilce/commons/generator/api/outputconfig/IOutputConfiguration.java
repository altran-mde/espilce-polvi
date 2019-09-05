/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.generator.api.outputconfig;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public interface IOutputConfiguration {
	
	public @NonNull String getName();
	
	public @Nullable String getDescription();
	
	/**
	 * a description to be shown in the UI.
	 */
	void setDescription(final @Nullable String description);
	
	String getOutputDirectory();
	
	/**
	 * the project relative path to the output directory
	 */
	void setOutputDirectory(final @Nullable String outputDirectory);
	
	boolean isCleanUpDerivedResources();
	
	/**
	 * whether derived resources should be deleted on clean.
	 */
	void setCleanUpDerivedResources(boolean cleanUpDerivedResources);
	
	boolean isOverrideExistingResources();
	
	/**
	 * whether existing resources should be overridden.
	 */
	void setOverrideExistingResources(boolean overrideExistingResources);
	
	boolean isSetDerivedProperty();
	
	/**
	 * whether the resources should be flagged as 'derived' <b>Only used if the
	 * underlying file system implementation supports such a property</b>
	 */
	void setSetDerivedProperty(boolean setDerivedProperty);
	
	boolean isCreateOutputDirectory();
	
	/**
	 * whether the output directory should be created if t doesn't already
	 * exist.
	 */
	void setCreateOutputDirectory(boolean createOutputDirectory);
	
	boolean isCanClearOutputDirectory();
	
	/**
	 * whether the whole outputDirectory can be cleared. This is usually used in
	 * a CLEAN build.
	 */
	void setCanClearOutputDirectory(boolean canClearOutputDirectory);
	
	/**
	 * @since 2.4
	 */
	boolean isInstallDslAsPrimarySource();
	
	/**
	 * whether the DSL files should be registered as primary source files for
	 * debugging in the generated Java-class-files. If false, the Java source is
	 * registered as primary source and the DSL files are registered as
	 * secondary source via JSR-045 (SMAP).
	 *
	 * @since 2.4
	 */
	void setInstallDslAsPrimarySource(boolean installDslAsPrimarySource);
	
	/**
	 * @since 2.4
	 */
	boolean isHideSyntheticLocalVariables();
	
	/**
	 * whether debug information should be removed from the class files for
	 * synthetic local variables. Synthetic variables are the ones that have not
	 * been declared in the DSL but have been introduced by the compiler. This
	 * flag is only used when {@link #isInstallDslAsPrimarySource()} is true.
	 *
	 * @since 2.4
	 */
	void setHideSyntheticLocalVariables(boolean hideSyntheticLocalVariables);
	
	/**
	 * @since 2.5
	 */
	boolean isKeepLocalHistory();
	
	/**
	 * whether local history should be kept for generated files.
	 *
	 * @since 2.5
	 */
	void setKeepLocalHistory(boolean keepLocalHistory);
	
	/**
	 * @since 2.6
	 */
	boolean isUseOutputPerSourceFolder();
	
	/**
	 * Whether to allow specifying output directories on a per source folder
	 * basis
	 *
	 * @since 2.6
	 */
	void setUseOutputPerSourceFolder(boolean useOutputPerSourceFolder);
	
	/**
	 * @since 2.6
	 */
	public @NonNull Set<@NonNull ? extends ISourceMapping> getSourceMappings();
	
	/**
	 * @param sourceFolder
	 *            the project relative source folder, e.g. "src" or
	 *            "src/main/java"
	 * @return the project relative output directory
	 * @since 2.6
	 */
	public @Nullable String getOutputDirectory(final @Nullable String sourceFolder);
	
	/**
	 * The source folders returned are project relative, e.g. "src" or
	 * "src/main/java"
	 * 
	 * @since 2.6
	 */
	public @NonNull Set<@NonNull String> getSourceFolders();
	
	/**
	 * The output directories returned are project relative, e.g. "build/gen" or
	 * "src-gen"
	 * 
	 * @since 2.6
	 */
	public @NonNull Set<@Nullable String> getOutputDirectories();
	
}
