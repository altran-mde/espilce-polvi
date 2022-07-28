/*******************************************************************************
 * Copyright (C) 2022 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.polvi.generator.context;

import org.espilce.polvi.generator.api.context.CancelIndicator;
import org.espilce.polvi.generator.api.context.IGeneratorContext;

/**
 * @since 2.9
 */
public class GeneratorContext implements IGeneratorContext {
	private CancelIndicator cancelIndicator;
	
	public GeneratorContext() {
		// Empty
	}
	
	public GeneratorContext(final CancelIndicator cancelIndicator) {
		setCancelIndicator(cancelIndicator);
	}

	@Override
	public CancelIndicator getCancelIndicator() {
		return this.cancelIndicator;
	}

	public void setCancelIndicator(final CancelIndicator cancelIndicator) {
		this.cancelIndicator = cancelIndicator;
	}
}
