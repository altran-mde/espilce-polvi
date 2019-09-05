package org.espilce.commons.emf.generator.fsa;

import org.eclipse.emf.common.util.URI;

/**
 * The {@link IFilePostProcessor} allows to rewrite the contents that will be
 * written into a given file. A common use case is to fix-up the line delimiters
 * and make all of them homogeneous.
 * 
 * The extension interface {@link IFilePostProcessorExtension} provides
 * additional information for implementors which is the target encoding of the
 * file.
 * 
 * @see LineSeparatorHarmonizer
 * @see IFilePostProcessorExtension IFilePostProcessorExtension (since 2.4)
 * 
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.3
 */
public interface IFilePostProcessor {
	
	CharSequence postProcess(URI fileURI, CharSequence content);
	
}
