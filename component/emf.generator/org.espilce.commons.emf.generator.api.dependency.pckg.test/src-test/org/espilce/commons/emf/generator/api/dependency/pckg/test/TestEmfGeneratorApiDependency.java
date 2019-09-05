package org.espilce.commons.emf.generator.api.dependency.pckg.test;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.espilce.commons.emf.generator.api.IGenerator2;
import org.espilce.commons.emf.generator.api.fsa.IFileSystemAccess2;
import org.espilce.commons.emf.generator.api.fsa.IFileSystemAccessExtension2;
import org.espilce.commons.generator.api.context.IGeneratorContext;
import org.espilce.commons.generator.api.fsa.RuntimeIOException;
import org.junit.Test;

public class TestEmfGeneratorApiDependency {
	@Test
	public void iGenerator2() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IGenerator2() {
			
			@Override
			public void doGenerate(
					final Resource input, final IFileSystemAccess2 fsa,
					final IGeneratorContext context
			) {
				b.set(true);
			}
			
			@Override
			public void beforeGenerate(
					final Resource input, final IFileSystemAccess2 fsa,
					final IGeneratorContext context
			) {
			}
			
			@Override
			public void afterGenerate(
					final Resource input, final IFileSystemAccess2 fsa,
					final IGeneratorContext context
			) {
			}
		}.doGenerate(null, null, null);
		
		assertTrue(b.get());
	}
	
	@Test
	public void iFileSystemAccess2() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IFileSystemAccess2() {
			
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
			}
			
			@Override
			public URI getURI(final String path) {
				return null;
			}
			
			@Override
			public URI getURI(final String path, final String outputConfiguration) {
				return null;
			}
			
			@Override
			public void deleteFile(final String fileName, final String outputConfigurationName) {
				
			}
			
			@Override
			public void generateFile(
					final String fileName, final String outputConfigurationName,
					final CharSequence contents
			) {
			}
			
			@Override
			public void generateFile(final String fileName, final CharSequence contents) {
			}
			
			@Override
			public void deleteFile(final String fileName) {
			}
			
			@Override
			public boolean isFile(final String path) throws RuntimeIOException {
				b.set(true);
				return false;
			}
			
			@Override
			public boolean isFile(final String path, final String outputConfigurationName)
					throws RuntimeIOException {
				return false;
			}
		}.isFile(null);
		
		assertTrue(b.get());
	}
	
	@Test
	public void iFileSystemAccessExtension2() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IFileSystemAccessExtension2() {
			
			@Override
			public URI getURI(final String path) {
				b.set(true);
				return null;
			}
			
			@Override
			public URI getURI(final String path, final String outputConfiguration) {
				return null;
			}
		}.getURI(null);
		
		assertTrue(b.get());
	}
	
}
