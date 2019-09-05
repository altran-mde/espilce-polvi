package org.espilce.commons.emf.generator.fsa;

import java.nio.charset.Charset;

import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.generator.api.fsa.IFileSystemAccessExtension2;
import org.espilce.commons.generator.AbstractFileSystemAccess;

/**
 * @author Sven Efftinge - Initial contribution and API
 */
public abstract class AbstractEmfFileSystemAccess extends AbstractFileSystemAccess implements
		IFileSystemAccessExtension2
{
	
	private IFilePostProcessor postProcessor;
	
	/**
	 * @since 2.3
	 */
	protected CharSequence postProcess(
			final String fileName, final String outputConfiguration, final CharSequence content
	) {
		if (this.postProcessor != null) {
			return this.postProcessor.postProcess(getURI(fileName, outputConfiguration), content);
		} else {
			return content;
		}
	}
	
	/**
	 * @since 2.4
	 */
	protected CharSequence postProcess(
			final String fileName, final String outputConfiguration, final CharSequence content, final String charset
	) {
		if (this.postProcessor instanceof IFilePostProcessorExtension) {
			return ((IFilePostProcessorExtension) this.postProcessor)
					.postProcess(getURI(fileName, outputConfiguration), content, Charset.forName(charset));
		} else {
			return postProcess(fileName, outputConfiguration, content);
		}
	}
	
	/**
	 * @since 2.3
	 */
	@Override
	public URI getURI(final String path) {
		return getURI(path, DEFAULT_OUTPUT);
	}
	
	void setPostProcessor(final IFilePostProcessor postProcessor) { this.postProcessor = postProcessor; }
}