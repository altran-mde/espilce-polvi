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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

public class CommonsDelegateGenerator implements org.eclipse.xtext.generator.IGenerator2 {
	private final org.espilce.commons.emf.generator.api.IGenerator2 delegate;
	
	public CommonsDelegateGenerator(final org.espilce.commons.emf.generator.api.IGenerator2 delegate) {
		this.delegate = delegate;
	}
	
	protected org.espilce.commons.emf.generator.api.fsa.IFileSystemAccess2 convert(
			final org.eclipse.xtext.generator.IFileSystemAccess2 fsa
	) {
		return new XtextDelegateFileSystemAccess(fsa);
	}
	
	protected org.espilce.commons.generator.api.context.IGeneratorContext convert(
			final org.eclipse.xtext.generator.IGeneratorContext context
	) {
		return new XtextDelegateGeneratorContext(context);
	}
	
	@Override
	public void doGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
		this.delegate.doGenerate(input, convert(fsa), convert(context));
	}
	
	@Override
	public void beforeGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
		this.delegate.beforeGenerate(input, convert(fsa), convert(context));
	}
	
	@Override
	public void afterGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
		this.delegate.afterGenerate(input, convert(fsa), convert(context));
	}
}
