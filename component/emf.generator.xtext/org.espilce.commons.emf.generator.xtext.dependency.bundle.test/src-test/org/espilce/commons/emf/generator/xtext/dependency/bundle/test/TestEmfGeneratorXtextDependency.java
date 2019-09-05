package org.espilce.commons.emf.generator.xtext.dependency.bundle.test;

import static org.junit.Assert.assertNotNull;

import org.espilce.commons.emf.generator.xtext.CommonsDelegateGenerator;
import org.espilce.commons.emf.generator.xtext.XtextDelegateCancelIndicator;
import org.espilce.commons.emf.generator.xtext.XtextDelegateFileSystemAccess;
import org.espilce.commons.emf.generator.xtext.XtextDelegateGeneratorContext;
import org.espilce.commons.emf.generator.xtext.ext.InMemoryFileSystemAccess4;
import org.espilce.commons.emf.generator.xtext.ext.JavaIoFileSystemAccess4;
import org.espilce.commons.emf.generator.xtext.ext.URIBasedFileSystemAccess4;
import org.junit.Test;

public class TestEmfGeneratorXtextDependency {
	@Test
	public void commonsDelegateGenerator() throws Exception {
		assertNotNull(new CommonsDelegateGenerator(null));
	}
	
	@Test
	public void xtextDelegateCancelIndicator() throws Exception {
		assertNotNull(new XtextDelegateCancelIndicator(null));
	}
	
	@Test
	public void xtextDelegateFileSystemAccess() throws Exception {
		assertNotNull(new XtextDelegateFileSystemAccess(null));
	}
	
	@Test
	public void xtextDelegateGeneratorContext() throws Exception {
		assertNotNull(new XtextDelegateGeneratorContext(null));
	}
	
	@Test
	public void inMemoryFileSystemAccess4() throws Exception {
		assertNotNull(new InMemoryFileSystemAccess4());
	}
	
	@Test
	public void javaIoFileSystemAccess4() throws Exception {
		assertNotNull(new JavaIoFileSystemAccess4());
	}
	
	@Test
	public void uRIBasedFileSystemAccess4() throws Exception {
		assertNotNull(new URIBasedFileSystemAccess4());
	}
}
