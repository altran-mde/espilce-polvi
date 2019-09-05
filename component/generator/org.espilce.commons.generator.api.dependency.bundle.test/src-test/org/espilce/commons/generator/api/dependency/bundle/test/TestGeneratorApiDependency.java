package org.espilce.commons.generator.api.dependency.bundle.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.espilce.commons.generator.api.IGenerator1;
import org.espilce.commons.generator.api.context.CancelIndicator;
import org.espilce.commons.generator.api.context.IGeneratorContext;
import org.espilce.commons.generator.api.fsa.IFileSystemAccess;
import org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension;
import org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension3;
import org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension4;
import org.espilce.commons.generator.api.fsa.RuntimeIOException;
import org.espilce.commons.generator.api.outputconfig.IOutputConfiguration;
import org.espilce.commons.generator.api.outputconfig.ISourceMapping;
import org.junit.Test;

public class TestGeneratorApiDependency {
	@SuppressWarnings("null")
	@Test
	public void iGenerator1() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		new IGenerator1() {
			
			@Override
			public void doGenerate(
					final Resource input, final IFileSystemAccess fsa,
					final IGeneratorContext context
			) {
				b.set(true);
			}
			
			@Override
			public void beforeGenerate(
					final Resource input, final IFileSystemAccess fsa,
					final IGeneratorContext context
			) {
			}
			
			@Override
			public void afterGenerate(
					final Resource input, final IFileSystemAccess fsa,
					final IGeneratorContext context
			) {
			}
		}.doGenerate(null, null, null);
		
		assertTrue(b.get());
	}
	
	@Test
	public void cancelIndicator() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new CancelIndicator() {
			
			@Override
			public boolean isCanceled() {
				b.set(true);
				return false;
			}
		}.isCanceled();
		
		assertTrue(b.get());
	}
	
	@SuppressWarnings("null")
	@Test
	public void iGeneratorContext() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IGeneratorContext() {
			
			@Override
			public CancelIndicator getCancelIndicator() {
				b.set(true);
				return null;
			}
		}.getCancelIndicator();
		
		assertTrue(b.get());
	}
	
	@SuppressWarnings("null")
	@Test
	public void iFileSystemAccess() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IFileSystemAccess() {
			
			@Override
			public void generateFile(
					final String fileName, final String outputConfigurationName,
					final CharSequence contents
			) {
			}
			
			@Override
			public void generateFile(final String fileName, final CharSequence contents) {
				b.set(true);
				
			}
			
			@Override
			public void deleteFile(final String fileName) {
			}
		}.generateFile(null, null);
		
		assertTrue(b.get());
	}
	
	@SuppressWarnings("null")
	@Test
	public void iFileSystemAccessExtension() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IFileSystemAccessExtension() {
			
			@Override
			public void deleteFile(final String fileName, final String outputConfigurationName) {
				b.set(true);
			}
		}.deleteFile(null, null);
		
		assertTrue(b.get());
	}
	
	@SuppressWarnings("null")
	@Test
	public void iFileSystemAccessExtension3() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		new IFileSystemAccessExtension3() {
			
			@Override
			public CharSequence readTextFile(final String fileName) throws RuntimeIOException {
				return null;
			}
			
			@Override
			public CharSequence readTextFile(
					final String fileName, final String outputCfgName
			)
					throws RuntimeIOException {
				return null;
			}
			
			@Override
			public InputStream readBinaryFile(final String fileName) throws RuntimeIOException {
				return null;
			}
			
			@Override
			public InputStream readBinaryFile(
					final String fileName, final String outputCfgName
			)
					throws RuntimeIOException {
				return null;
			}
			
			@Override
			public void generateFile(final String fileName, final InputStream content)
					throws RuntimeIOException {
			}
			
			@Override
			public void generateFile(
					final String fileName, final String outputCfgName,
					final InputStream content
			)
					throws RuntimeIOException {
				b.set(true);
			}
		}.generateFile(null, null, null);
		
		assertTrue(b.get());
	}
	
	@SuppressWarnings("null")
	@Test
	public void iFileSystemAccessExtension4() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IFileSystemAccessExtension4() {
			
			@Override
			public List<String> findMatchingFiles(
					final String parentPath, final String outputConfigurationName
			) {
				return null;
			}
			
			@Override
			public List<String> findMatchingFiles(final String parentPath) {
				b.set(true);
				return null;
			}
		}.findMatchingFiles(null);
		
		assertTrue(b.get());
	}
	
	@Test
	public void runtimeIOException() throws Exception {
		assertNotNull(new RuntimeIOException("hello").getMessage());
	}
	
	@SuppressWarnings("null")
	@Test
	public void iOutputConfiguration() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IOutputConfiguration() {
			
			@Override
			public void setUseOutputPerSourceFolder(final boolean useOutputPerSourceFolder) {
			}
			
			@Override
			public void setSetDerivedProperty(final boolean setDerivedProperty) {
			}
			
			@Override
			public void setOverrideExistingResources(final boolean overrideExistingResources) {
			}
			
			@Override
			public void setOutputDirectory(final String outputDirectory) {
			}
			
			@Override
			public void setKeepLocalHistory(final boolean keepLocalHistory) {
			}
			
			@Override
			public void setInstallDslAsPrimarySource(final boolean installDslAsPrimarySource) {
			}
			
			@Override
			public void setHideSyntheticLocalVariables(final boolean hideSyntheticLocalVariables) {
			}
			
			@Override
			public void setDescription(final String description) {
			}
			
			@Override
			public void setCreateOutputDirectory(final boolean createOutputDirectory) {
			}
			
			@Override
			public void setCleanUpDerivedResources(final boolean cleanUpDerivedResources) {
			}
			
			@Override
			public void setCanClearOutputDirectory(final boolean canClearOutputDirectory) {
			}
			
			@Override
			public boolean isUseOutputPerSourceFolder() { return false; }
			
			@Override
			public boolean isSetDerivedProperty() { return false; }
			
			@Override
			public boolean isOverrideExistingResources() { return false; }
			
			@Override
			public boolean isKeepLocalHistory() { return false; }
			
			@Override
			public boolean isInstallDslAsPrimarySource() { return false; }
			
			@Override
			public boolean isHideSyntheticLocalVariables() { return false; }
			
			@Override
			public boolean isCreateOutputDirectory() { return false; }
			
			@Override
			public boolean isCleanUpDerivedResources() { return false; }
			
			@Override
			public boolean isCanClearOutputDirectory() { return false; }
			
			@Override
			public Set<? extends ISourceMapping> getSourceMappings() { return null; }
			
			@Override
			public Set<String> getSourceFolders() { return null; }
			
			@Override
			public String getOutputDirectory(final String sourceFolder) {
				return null;
			}
			
			@Override
			public String getOutputDirectory() { return null; }
			
			@Override
			public Set<String> getOutputDirectories() { return null; }
			
			@Override
			public String getName() { return null; }
			
			@Override
			public String getDescription() {
				b.set(true);
				return null;
			}
		}.getDescription();
		
		assertTrue(b.get());
	}
	
	@SuppressWarnings("null")
	@Test
	public void iSourceMapping() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new ISourceMapping() {
			
			@Override
			public void setOutputDirectory(final String outputDirectory) {
			}
			
			@Override
			public void setIgnore(final boolean ignore) {
			}
			
			@Override
			public boolean isIgnore() { return false; }
			
			@Override
			public String getSourceFolder() { return null; }
			
			@Override
			public String getOutputDirectory() {
				b.set(true);
				return null;
			}
		}.getOutputDirectory();
		
		assertTrue(b.get());
	}
}
