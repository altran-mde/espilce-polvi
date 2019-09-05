package org.espilce.commons.emf.generator.dependency.pckg.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.generator.fsa.IEncodingProvider;
import org.espilce.commons.emf.generator.fsa.IFilePostProcessor;
import org.espilce.commons.emf.generator.fsa.IFilePostProcessorExtension;
import org.espilce.commons.emf.generator.fsa.InMemoryFileSystemAccess;
import org.espilce.commons.emf.generator.fsa.JavaIoFileSystemAccess;
import org.espilce.commons.emf.generator.fsa.URIBasedFileSystemAccess;
import org.junit.Test;

public class TestEmfGeneratorDependency {
	@Test
	public void iEncodingProvider() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IEncodingProvider() {
			
			@Override
			public String getEncoding(final URI uri) {
				b.set(true);
				return null;
			}
		}.getEncoding(null);
		
		assertTrue(b.get());
	}
	
	@Test
	public void iFilePostProcessor() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IFilePostProcessor() {
			
			@Override
			public CharSequence postProcess(final URI fileURI, final CharSequence content) {
				b.set(true);
				return null;
			}
		}.postProcess(null, null);
		
		assertTrue(b.get());
	}
	
	@Test
	public void iFilePostProcessorExtension() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		
		new IFilePostProcessorExtension() {
			
			@Override
			public CharSequence postProcess(
					final URI fileURI, final CharSequence content, final Charset targetCharset
			) {
				b.set(true);
				return null;
			}
		}.postProcess(null, null, null);
		
		assertTrue(b.get());
	}
	
	@Test
	public void inMemoryFileSystemAccess() throws Exception {
		assertNotNull(new InMemoryFileSystemAccess().getAllFiles());
	}
	
	@Test
	public void javaIoFileSystemAccess() throws Exception {
		assertNotNull(new JavaIoFileSystemAccess().getOutputConfigurations());
	}
	
	@Test
	public void uRIBasedFileSystemAccess() throws Exception {
		assertNotNull(new URIBasedFileSystemAccess().getOutputConfigurations());
	}
	
}
