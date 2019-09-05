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

public class XtextDelegateCancelIndicator implements org.espilce.commons.generator.api.context.CancelIndicator {
	
	private final org.eclipse.xtext.util.CancelIndicator delegate;
	
	public XtextDelegateCancelIndicator(final org.eclipse.xtext.util.CancelIndicator delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public boolean isCanceled() { return this.delegate.isCanceled(); }
}
