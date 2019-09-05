package org.espilce.commons.emf.generator.fsa;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.annotation.NonNull;
import org.espilce.commons.generator.api.fsa.IFileSystemAccessExtension4;
import org.espilce.commons.generator.api.fsa.RuntimeIOException;
import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @author Jan Koehnlein
 * @author Moritz Eysholdt
 */
public class JavaIoFileSystemAccess extends AbstractFileSystemAccess2 implements IFileSystemAccessExtension4 {
	ILoadHelper loadHelper;
	
	/**
	 * @since 2.9
	 * @noextend
	 * @noimplement
	 */
	public static interface IFileCallback {
		void fileAdded(File file);
		
		void fileDeleted(File file);
	}
	
	private IFileCallback callBack;
	
	private final IEncodingProvider encodingProvider;
	
	private boolean writeTrace = true;
	
	public JavaIoFileSystemAccess() {
		this(new IEncodingProvider.Runtime(), new FilesystemClassloaderLoadHelper());
	}
	
	public JavaIoFileSystemAccess(final IEncodingProvider encodingProvider, final ILoadHelper loadHelper) {
		this.encodingProvider = encodingProvider;
		this.loadHelper = loadHelper;
	}
	
	/**
	 * @since 2.9
	 */
	public void setCallBack(final IFileCallback callBack) { this.callBack = callBack; }
	
	@Override
	public void generateFile(final String fileName, final String outputConfigName, final CharSequence contents)
			throws RuntimeIOException {
		final File file = getFile(fileName, outputConfigName);
		if (!getOutputConfig(outputConfigName).isOverrideExistingResources() && file.exists()) {
			return;
		}
		try {
			createFolder(file.getParentFile());
			final String encoding = getEncoding(getURI(fileName, outputConfigName));
			final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), encoding);
			try {
				writer.append(postProcess(fileName, outputConfigName, contents, encoding));
				if (this.callBack != null) {
					this.callBack.fileAdded(file);
				}
				if (this.writeTrace) {
					generateTrace(fileName, outputConfigName, contents);
				}
			} finally {
				writer.close();
			}
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}
	
	protected void generateTrace(
			final String generatedFile, final String outputConfigName, final CharSequence contents
	) {
		// not implemented
	}
	
	/**
	 * @since 2.4
	 */
	public boolean isWriteTrace() { return this.writeTrace; }
	
	/**
	 * @since 2.4
	 */
	public void setWriteTrace(final boolean writeTrace) { this.writeTrace = writeTrace; }
	
	/**
	 * @since 2.3
	 */
	protected String getEncoding(final URI fileURI) {
		return this.encodingProvider.getEncoding(fileURI);
	}
	
	/**
	 * @since 2.1
	 */
	protected File getFile(final String fileName, final String outputConfigName) {
		final String outlet = getPathes().get(outputConfigName);
		if (outlet == null) {
			throw new IllegalArgumentException("A slot with name '" + outputConfigName + "' has not been configured.");
		}
		final String pathName = toSystemFileName(outlet + "/" + fileName);
		final File file = new File(pathName).getAbsoluteFile();
		return file;
	}
	
	protected void createFolder(final File parent) {
		if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
			throw new RuntimeIOException("Could not create directory " + parent);
		}
	}
	
	@Override
	public void deleteFile(final String fileName, final String outputConfiguration) {
		final File file = getFile(fileName, outputConfiguration);
		if (file.exists()) {
			file.delete();
		}
	}
	
	protected String toSystemFileName(final String fileName) {
		return fileName.replace("/", File.separator);
	}
	
	/**
	 * @since 2.3
	 */
	@Override
	public URI getURI(final String fileName, final String outputConfiguration) {
		return URI.createURI(getFile(fileName, outputConfiguration).toURI().toString());
	}
	
	/**
	 * @since 2.4
	 */
	@Override
	public void generateFile(final String fileName, final String outputCfgName, final InputStream content)
			throws RuntimeIOException {
		final File file = getFile(fileName, outputCfgName);
		if (!getOutputConfig(outputCfgName).isOverrideExistingResources() && file.exists()) {
			return;
		}
		try {
			createFolder(file.getParentFile());
			final OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			try {
				IOUtils.copy(content, out);
			} finally {
				try {
					out.close();
					if (this.callBack != null) {
						this.callBack.fileAdded(file);
					}
				} finally {
					content.close();
				}
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
		final File file = getFile(fileName, outputCfgName);
		try {
			return new BufferedInputStream(new FileInputStream(file));
		} catch (final FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}
	
	/**
	 * @since 2.4
	 */
	@Override
	public CharSequence readTextFile(final String fileName, final String outputCfgName) throws RuntimeIOException {
		try {
			final File file = getFile(fileName, outputCfgName);
			final String encoding = getEncoding(getURI(fileName, outputCfgName));
			return new String(FileUtils.readFileToByteArray(file), encoding);
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeIOException(e);
		} catch (final IOException e) {
			throw new RuntimeIOException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFile(final String path, final String outputConfigurationName) throws RuntimeIOException {
		final File file = getFile(path, outputConfigurationName);
		return file != null && file.exists() && file.isFile();
	}
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(@NonNull final String parentPath) {
		return findMatchingFiles(parentPath, DEFAULT_OUTPUT);
	}
	
	@Override
	public @NonNull List<@NonNull String> findMatchingFiles(
			@NonNull final String parentPath, @NonNull final String outputConfigurationName
	) {
		final File file = getFile(parentPath, outputConfigurationName);
		final Path path = file.toPath();
		return this.loadHelper.findMatchingResources(getClass(), path.toString()).stream()
				.map(ConversionUtils::toJavaPath)
				.filter(Objects::nonNull)
				.map(p -> p.resolve(path).toString()).collect(Collectors.toList());
	}
}
