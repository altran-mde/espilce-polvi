package org.espilce.commons.emf.generator.fsa;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.jdt.annotation.NonNull;
import org.espilce.commons.emf.UriUtils;
import org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension4;
import org.espilce.commons.generator.api.fsa.RuntimeIOException;
import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;

/**
 * A file system access implementation that is based on EMF URIs and
 * URIConverter
 * 
 * @since 2.9
 */
public class URIBasedFileSystemAccess extends AbstractFileSystemAccess2 implements IFileSystemAccessExtension4 {
	
	static interface BeforeDelete {
		/**
		 * @return <code>true</code> if the file can be deleted, false otherwise
		 */
		boolean beforeDelete(URI changed);
	}
	
	static interface BeforeWrite {
		InputStream beforeWrite(URI changed, String outputCfgName, InputStream in);
	}
	
	static interface BeforeRead {
		InputStream beforeRead(URI changed, InputStream in);
	}
	
	private final URIConverter converter;
	private URI baseDir;
	ILoadHelper loadHelper;
	private IEncodingProvider encodingProvider = new IEncodingProvider.Runtime();
	private final BeforeWrite beforeWrite = (changed, outputCfgName, in) -> in;
	private final BeforeRead beforeRead = (changed, in) -> in;
	
	/*
	 * private BeforeDelete beforeDelete = changed -> true; private boolean
	 * generateTraces = false;
	 */
	
	public URIBasedFileSystemAccess() {
		this(new ExtensibleURIConverterImpl(), new IEncodingProvider.Runtime(), new FilesystemClassloaderLoadHelper());
	}
	
	public URIBasedFileSystemAccess(
			final URIConverter uriConverter, final IEncodingProvider encodingProvider, final ILoadHelper loadHelper
	) {
		this.converter = uriConverter;
		this.encodingProvider = encodingProvider;
		this.loadHelper = loadHelper;
	}
	
	@Override
	public void setPostProcessor(final IFilePostProcessor filePostProcessor) {
		super.setPostProcessor(filePostProcessor);
	}
	
	@Override
	public URI getURI(final String path, final String outputConfiguration) {
		final String outlet = getPathes().get(outputConfiguration);
		if (outlet == null) {
			throw new IllegalArgumentException(
					"A slot with name '" + outputConfiguration + "' has not been configured."
			);
		}
		final URI uri = URI.createFileURI(outlet + File.separator + path);
		if (this.baseDir != null) {
			final URI resolved = uri.resolve(this.baseDir);
			return resolved;
		} else {
			return uri;
		}
	}
	
	String getEncoding(final URI uri) {
		return this.encodingProvider.getEncoding(uri);
	}
	
	@Override
	public void generateFile(final String fileName, final String outputCfgName, final CharSequence contents) {
		final URI uri = getURI(fileName, outputCfgName);
		if (
			!getOutputConfig(outputCfgName).isOverrideExistingResources()
					&& this.converter.exists(uri, Collections.emptyMap())
		) {
			return;
		}
		final String encoding = getEncoding(uri);
		final CharSequence postProcessed = postProcess(fileName, outputCfgName, contents, encoding);
		generateTrace(fileName, outputCfgName, postProcessed);
		try (
				final ByteArrayInputStream inStream = new ByteArrayInputStream(
						postProcessed.toString().getBytes(encoding)
				)
		) {
			generateFile(fileName, outputCfgName, inStream);
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}
	
	protected void generateTrace(
			final String generatedFile, final String outputConfigName, final CharSequence contents
	) {
		// not implemented
	}
	
	@Override
	public void generateFile(final String fileName, final String outputCfgName, final InputStream content)
			throws RuntimeIOException {
		final URI uri = getURI(fileName, outputCfgName);
		try (final OutputStream out = this.converter.createOutputStream(uri)) {
			final InputStream processedContent = this.beforeWrite.beforeWrite(uri, outputCfgName, content);
			IOUtils.copy(processedContent, out);
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}
	
	@Override
	public InputStream readBinaryFile(final String fileName, final String outputCfgName) throws RuntimeIOException {
		try {
			final URI uri = getURI(fileName, outputCfgName);
			final InputStream input = this.converter.createInputStream(uri);
			return this.beforeRead.beforeRead(uri, input);
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}
	
	@Override
	public CharSequence readTextFile(final String fileName, final String outputCfgName) throws RuntimeIOException {
		final URI uri = getURI(fileName, outputCfgName);
		try (final InputStream inputstream = readBinaryFile(fileName, outputCfgName)) {
			return IOUtils.toString(new InputStreamReader(inputstream, getEncoding(uri)));
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(@NonNull final String parentPath) {
		return findMatchingFiles(parentPath, DEFAULT_OUTPUT);
	}
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(
			@NonNull final String parentPath, @NonNull final String outputConfigurationName
	) {
		final URI uri = getURI(parentPath, outputConfigurationName);
		final String path = uri.toString();
		
		return this.loadHelper.findMatchingResources(getClass(), path).stream()
				.map(UriUtils::asEmfUri).map(u -> u.resolve(uri).toString()).collect(Collectors.toList());
	}
}