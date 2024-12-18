package org.espilce.polvi.generator.dependency.bundle.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.espilce.polvi.generator.fsa.AbstractFileSystemAccess;
import org.espilce.polvi.generator.outputconfig.OutputConfiguration;
import org.espilce.polvi.generator.outputconfig.SourceMapping;
import org.junit.Test;

public class TestGeneratorDependency {
	@Test
	public void abstractFileSystemAccess() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		new AbstractFileSystemAccess() {
			
			@Override
			public void generateFile(
					final String fileName, final String outputConfigurationName,
					final CharSequence contents
			) {
				b.set(true);
			}
		}.generateFile("a", "b", "c");
		assertTrue(b.get());
	}
	
	@Test
	public void outputConfiguration() throws Exception {
		assertNotNull(new OutputConfiguration("hello").getName());
		
	}
	
	@Test
	public void sourceMapping() throws Exception {
		assertNotNull(new SourceMapping("asdf").getSourceFolder());
	}
}
