package org.espilce.commons.emf.generator.fsa;

import java.nio.charset.Charset;

import org.eclipse.emf.common.util.URI;

/**
 * Provides the file encoding for a language.
 * 
 * @author Jan Koehnlein - Initial contribution and API
 */
public interface IEncodingProvider {
	
	/**
	 * Returns the encoding that should be used to read a resource from the
	 * given uri. If the uri is <code>null</code>, the default encoding for the
	 * language is returned.
	 * 
	 * At runtime, the default encoding was either configured externally or it
	 * is obtained from the class {@link Charset#defaultCharset() Charset}. In
	 * the UI environment, the encoding should usually be read from the
	 * workspace metadata.
	 * 
	 * @param uri
	 *            the uri of the specific resource or <code>null</code> to
	 *            obtain the default encoding.
	 */
	String getEncoding(URI uri);
	
	class Runtime implements IEncodingProvider {
		
		private String defaultEncoding = null;
		
		@Override
		public String getEncoding(final URI uri) {
			if (defaultEncoding != null) {
				return defaultEncoding;
			}
			return Charset.defaultCharset().name();
		}
		
		/**
		 * @since 2.4
		 */
		public String getDefaultEncoding() { return defaultEncoding; }
		
		/**
		 * @since 2.4
		 */
		public void setDefaultEncoding(final String defaultEncoding) { this.defaultEncoding = defaultEncoding; }
		
	}
	
}
