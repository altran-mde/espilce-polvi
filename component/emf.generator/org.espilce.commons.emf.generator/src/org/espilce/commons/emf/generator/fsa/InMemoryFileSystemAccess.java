package org.espilce.commons.emf.generator.fsa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.annotation.NonNull;
import org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension4;
import org.espilce.commons.generator.api.fsa.RuntimeIOException;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @author Moritz Eysholdt
 * @since 2.0
 */
public class InMemoryFileSystemAccess extends AbstractFileSystemAccess2 implements IFileSystemAccessExtension4 {
	
	private final Map<String, Object> files = new TreeMap<>(); // the TreeMap
																// sorts all
																// files by name
	
	private String textFileEnconding;
	
	@Override
	public void generateFile(final String fileName, final String outputConfigName, CharSequence contents) {
		final String encoding = getTextFileEncoding();
		if (encoding != null) {
			contents = postProcess(fileName, outputConfigName, contents, encoding);
		} else {
			contents = postProcess(fileName, outputConfigName, contents);
		}
		this.files.put(getFileName(fileName, outputConfigName), contents);
	}
	
	/**
	 * @since 2.4
	 */
	public String getTextFileEncoding() { return this.textFileEnconding; }
	
	/**
	 * @since 2.4
	 */
	public void setTextFileEnconding(final String textFileEnconding) { this.textFileEnconding = textFileEnconding; }
	
	/**
	 * @since 2.4
	 */
	@Override
	public void setPostProcessor(final IFilePostProcessor postProcessor) {
		super.setPostProcessor(postProcessor);
	}
	
	/**
	 * @since 2.4
	 */
	protected String getFileName(final String fileName, final String outputConfigName) {
		return outputConfigName + fileName;
	}
	
	@Override
	public void deleteFile(final String fileName, final String outputConfigName) {
		this.files.remove(getFileName(fileName, outputConfigName));
	}
	
	/**
	 * @since 2.4
	 */
	public Map<String, CharSequence> getTextFiles() {
		final Map<String, CharSequence> result = new LinkedHashMap<>();
		for (final Map.Entry<String, Object> e : this.files.entrySet()) {
			if (e.getValue() instanceof CharSequence) {
				result.put(e.getKey(), ((CharSequence) e.getValue()));
			}
		}
		return result;
	}
	
	/**
	 * @since 2.4
	 */
	public Map<String, byte[]> getBinaryFiles() {
		final Map<String, byte[]> result = new LinkedHashMap<>();
		for (final Map.Entry<String, Object> e : this.files.entrySet()) {
			if (e.getValue() instanceof byte[]) {
				result.put(e.getKey(), ((byte[]) e.getValue()));
			}
		}
		return result;
	}
	
	/**
	 * @since 2.4
	 */
	public Map<String, Object> getAllFiles() { return this.files; }
	
	/**
	 * use {@link #getTextFiles()} or {@link #getAllFiles()}.
	 */
	@Deprecated
	public Map<String, CharSequence> getFiles() { return getTextFiles(); }
	
	/**
	 * @since 2.3
	 */
	@Override
	public URI getURI(final String fileName, final String outputConfiguration) {
		return URI.createURI("memory:/" + outputConfiguration + "/" + fileName);
	}
	
	/**
	 * @since 2.4
	 */
	@Override
	public void generateFile(final String fileName, final String outputCfgName, final InputStream content) {
		try {
			try {
				final byte[] byteArray = IOUtils.toByteArray(content);
				this.files.put(getFileName(fileName, outputCfgName), byteArray);
			} finally {
				content.close();
			}
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}
	
	/**
	 * @since 2.4
	 */
	@Override
	public InputStream readBinaryFile(final String fileName, final String outputCfgName) throws RuntimeIOException {
		final String name = getFileName(fileName, outputCfgName);
		final Object contents = this.files.get(name);
		if (contents == null) {
			throw new RuntimeIOException("File not found: " + name);
		}
		if (contents instanceof byte[]) {
			return new ByteArrayInputStream((byte[]) contents);
		}
		if (contents instanceof CharSequence) {
			return new CharSequenceInputStream(contents.toString(), Charset.defaultCharset());
		}
		throw new RuntimeIOException("Unknown File Data Type: " + contents.getClass() + " File: " + name);
	}
	
	/**
	 * @since 2.4
	 */
	@Override
	public CharSequence readTextFile(final String fileName, final String outputCfgName) throws RuntimeIOException {
		final String name = getFileName(fileName, outputCfgName);
		final Object contents = this.files.get(name);
		if (contents == null) {
			throw new RuntimeIOException("File not found: " + name);
		}
		if (contents instanceof CharSequence) {
			return (CharSequence) contents;
		}
		if (contents instanceof byte[]) {
			throw new RuntimeIOException("Can not read a binary file using readTextFile(). File: " + name);
		}
		throw new RuntimeIOException("Unknown File Data Type: " + contents.getClass() + " File: " + name);
	}
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(@NonNull final String parentPath) {
		return findMatchingFiles(parentPath, DEFAULT_OUTPUT);
	}
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(
			@NonNull final String parentPath, @NonNull final String outputConfigurationName
	) {
		final String fileName = getFileName(parentPath, outputConfigurationName);
		
		return getAllFiles().keySet().stream().filter(Objects::nonNull).filter(name -> name.startsWith(fileName))
				.collect(Collectors.toList());
	}
	
}
