package org.espilce.commons.emf.generator.fsa;

import java.nio.charset.Charset;

import org.eclipse.emf.common.util.URI;

/**
 * Extension interface to {@link IFilePostProcessor}. It allows to process the
 * content with respect to the target charset. Implementors should handle a
 * {@link #postProcess(URI, CharSequence, Charset) postProcess} request without
 * an explicit target charset in the same way as a plain
 * {@link IFilePostProcessor#postProcess(URI, CharSequence)} request.
 * 
 * @see IFilePostProcessor
 * 
 * @author Sebastian Zarnekow - Initial contribution and API
 * @since 2.4
 */
public interface IFilePostProcessorExtension {
	
	CharSequence postProcess(URI fileURI, CharSequence content, /* @Nullable */ Charset targetCharset);
	
}
