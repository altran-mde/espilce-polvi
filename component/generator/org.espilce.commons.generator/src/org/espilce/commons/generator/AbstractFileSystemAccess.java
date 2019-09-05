package org.espilce.commons.generator;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.espilce.commons.generator.api.fsa.IFileSystemAccess;
import org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension;
import org.espilce.commons.generator.outputconfig.OutputConfiguration;

/**
 * @author Sven Efftinge - Initial contribution and API
 * 
 * @since 0.2
 */
public abstract class AbstractFileSystemAccess implements IFileSystemAccess, IFileSystemAccessExtension {

	private Map<String, OutputConfiguration> outputs = new LinkedHashMap<>();
	
	private String currentSource;
	
	/**
	 * @since 2.6
	 */
	public void setCurrentSource(final String currentSource) {
		this.currentSource = currentSource;
	}
	
	/**
	 * The current source folder, relative to the project location
	 * @since 2.6
	 */
	protected String getCurrentSource() {
		return this.currentSource;
	}

	/**
	 * @since 2.1
	 */
	public void setOutputConfigurations(final Map<String, OutputConfiguration> outputs) {
		this.outputs = outputs;
	}

	/**
	 * @since 2.1
	 */
	public Map<String, OutputConfiguration> getOutputConfigurations() {
		return this.outputs;
	}

	/**
	 * @since 2.1
	 */
	protected OutputConfiguration getOutputConfig(final String outputName) {
		if (!getOutputConfigurations().containsKey(outputName)) {
			throw new IllegalArgumentException("No output configuration with name '" + outputName + "' exists.");
		}
		return getOutputConfigurations().get(outputName);
	}

	protected Map<String, String> getPathes() {
		return getOutputConfigurations().entrySet().stream().map(e -> {
			final OutputConfiguration from = e.getValue();
			String to;
			if (AbstractFileSystemAccess.this.currentSource == null) {
				to = from.getOutputDirectory();
			} else {
				to = from.getOutputDirectory(AbstractFileSystemAccess.this.currentSource);
			}
			return new SimpleImmutableEntry<>(e.getKey(), to);
		}).collect(Collectors.toMap(Entry::getKey, Entry::getKey));
	}

	public void setOutputPath(final String outputName, final String path) {
		OutputConfiguration configuration = this.outputs.get(outputName);
		if (configuration == null) {
			configuration = new OutputConfiguration(outputName);
			this.outputs.put(outputName, configuration);
		}
		configuration.setOutputDirectory(path);
	}

	public void setOutputPath(final String path) {
		setOutputPath(DEFAULT_OUTPUT, path);
	}

	@Override
	public void generateFile(final String fileName, final CharSequence contents) {
		generateFile(fileName, DEFAULT_OUTPUT, contents);
	}

	/**
	 * @since 2.1
	 */
	@Override
	public void deleteFile(final String fileName) {
		deleteFile(fileName, DEFAULT_OUTPUT);
	}

	/**
	 * @since 2.1
	 */
	@Override
	public void deleteFile(final String fileName, final String outputConfigurationName) {
		throw new UnsupportedOperationException();
	}
}