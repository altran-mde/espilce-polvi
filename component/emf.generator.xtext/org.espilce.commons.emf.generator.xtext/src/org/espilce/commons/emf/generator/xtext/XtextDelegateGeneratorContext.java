/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.generator.xtext;

import org.eclipse.xtext.generator.IGeneratorContext;

public class XtextDelegateGeneratorContext implements org.espilce.commons.generator.api.context.IGeneratorContext {
	private final IGeneratorContext delegate;
	
	public XtextDelegateGeneratorContext(final org.eclipse.xtext.generator.IGeneratorContext delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public org.espilce.commons.generator.api.context.CancelIndicator getCancelIndicator() {
		return convert(this.delegate.getCancelIndicator());
	}
	
	protected org.espilce.commons.generator.api.context.CancelIndicator convert(
			final org.eclipse.xtext.util.CancelIndicator cancelIndicator
	) {
		return new XtextDelegateCancelIndicator(cancelIndicator);
	}
}
