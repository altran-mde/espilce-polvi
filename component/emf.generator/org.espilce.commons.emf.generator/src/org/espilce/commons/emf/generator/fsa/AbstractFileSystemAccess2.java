package org.espilce.commons.emf.generator.fsa;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.ecore.resource.Resource;
import org.espilce.commons.emf.generator.api.fsa.IFileSystemAccess2;
import org.espilce.commons.generator.api.fsa.RuntimeIOException;

/**
 * Abstract base class for file system access supporting
 * {@link IFileSystemAccess2}.
 *
 * @author Sven Efftinge - Initial contribution and API
 * @since 2.4
 */
public abstract class AbstractFileSystemAccess2 extends AbstractEmfFileSystemAccess implements IFileSystemAccess2 {
	
	/**
	 * @since 2.4
	 */
	@Override
	public void generateFile(final String fileName, final InputStream content) {
		generateFile(fileName, DEFAULT_OUTPUT, content);
	}
	
	/**
	 * @since 2.4
	 */
	@Override
	public InputStream readBinaryFile(final String fileName) {
		return readBinaryFile(fileName, DEFAULT_OUTPUT);
	}
	
	/**
	 * @since 2.4
	 */
	@Override
	public CharSequence readTextFile(final String fileName) {
		return readTextFile(fileName, DEFAULT_OUTPUT);
	}
	
	
	/**
	 * Sets the context to further configure this file system access instance.
	 *
	 * @param context
	 *            a context from which project configuration can be obtained.
	 *            Supported context types depend on the concrete implementation,
	 *            but {@link Resource} is usually a good fit.
	 *
	 * @since 2.8
	 */
	public void setContext(final Object context) {
		// do nothing
	}
	
	/**
	 * @since 2.9
	 */
	@Override
	public boolean isFile(final String path) throws RuntimeIOException {
		return isFile(path, DEFAULT_OUTPUT);
	}
	
	/**
	 * @since 2.9
	 */
	@SuppressWarnings("null")
	@Override
	public boolean isFile(final String path, final String outputConfigurationName) throws RuntimeIOException {
		InputStream is = null;
		try {
			is = readBinaryFile(path, outputConfigurationName);
			if (is != null) {
				try {
					is.close();
				} catch (final IOException e) {
					throw new RuntimeIOException(e);
				}
			}
			return is != null; // no exception => file exists
		} catch (final RuntimeIOException e) {
			return false;
		}
	}
	
}
