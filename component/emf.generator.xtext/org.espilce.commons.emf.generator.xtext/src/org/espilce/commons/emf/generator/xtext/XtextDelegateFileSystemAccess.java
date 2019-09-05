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

import java.io.InputStream;

import org.eclipse.emf.common.util.URI;

public class XtextDelegateFileSystemAccess implements org.espilce.commons.emf.generator.api.fsa.IFileSystemAccess2 {
	private final org.eclipse.xtext.generator.IFileSystemAccess2 delegate;
	
	public XtextDelegateFileSystemAccess(final org.eclipse.xtext.generator.IFileSystemAccess2 delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void deleteFile(final String fileName, final String outputConfigurationName) {
		this.delegate.deleteFile(fileName, outputConfigurationName);
	}
	
	@Override
	public void generateFile(final String fileName, final CharSequence contents) {
		this.delegate.generateFile(fileName, contents);
	}
	
	@Override
	public URI getURI(final String path, final String outputConfiguration) {
		return this.delegate.getURI(path, outputConfiguration);
	}
	
	@Override
	public void generateFile(final String fileName, final String outputCfgName, final InputStream content)
			throws org.eclipse.xtext.util.RuntimeIOException {
		try {
			this.delegate.generateFile(fileName, outputCfgName, content);
		} catch (final org.espilce.commons.generator.api.fsa.RuntimeIOException e) {
			throw new org.eclipse.xtext.util.RuntimeIOException(e);
		}
	}
	
	@Override
	public void generateFile(final String fileName, final String outputConfigurationName, final CharSequence contents) {
		this.delegate.generateFile(fileName, outputConfigurationName, contents);
	}
	
	@Override
	public URI getURI(final String path) {
		return this.delegate.getURI(path);
	}
	
	@Override
	public void generateFile(final String fileName, final InputStream content)
			throws org.eclipse.xtext.util.RuntimeIOException {
		try {
			this.delegate.generateFile(fileName, content);
		} catch (final org.espilce.commons.generator.api.fsa.RuntimeIOException e) {
			throw new org.eclipse.xtext.util.RuntimeIOException(e);
		}
	}
	
	@Override
	public void deleteFile(final String fileName) {
		this.delegate.deleteFile(fileName);
	}
	
	@Override
	public boolean isFile(final String path, final String outputConfigurationName)
			throws org.eclipse.xtext.util.RuntimeIOException {
		try {
			return this.delegate.isFile(path, outputConfigurationName);
		} catch (final org.espilce.commons.generator.api.fsa.RuntimeIOException e) {
			throw new org.eclipse.xtext.util.RuntimeIOException(e);
		}
	}
	
	@Override
	public InputStream readBinaryFile(final String fileName, final String outputCfgName)
			throws org.eclipse.xtext.util.RuntimeIOException {
		try {
			return this.delegate.readBinaryFile(fileName, outputCfgName);
		} catch (final org.espilce.commons.generator.api.fsa.RuntimeIOException e) {
			throw new org.eclipse.xtext.util.RuntimeIOException(e);
		}
	}
	
	@Override
	public InputStream readBinaryFile(final String fileName) throws org.eclipse.xtext.util.RuntimeIOException {
		try {
			return this.delegate.readBinaryFile(fileName);
		} catch (final org.espilce.commons.generator.api.fsa.RuntimeIOException e) {
			throw new org.eclipse.xtext.util.RuntimeIOException(e);
		}
	}
	
	@Override
	public CharSequence readTextFile(final String fileName, final String outputCfgName)
			throws org.eclipse.xtext.util.RuntimeIOException {
		try {
			return this.delegate.readTextFile(fileName, outputCfgName);
		} catch (final org.espilce.commons.generator.api.fsa.RuntimeIOException e) {
			throw new org.eclipse.xtext.util.RuntimeIOException(e);
		}
	}
	
	@Override
	public boolean isFile(final String path) throws org.eclipse.xtext.util.RuntimeIOException {
		try {
			return this.delegate.isFile(path);
		} catch (final org.espilce.commons.generator.api.fsa.RuntimeIOException e) {
			throw new org.eclipse.xtext.util.RuntimeIOException(e);
		}
	}
	
	@Override
	public CharSequence readTextFile(final String fileName) throws org.eclipse.xtext.util.RuntimeIOException {
		try {
			return this.delegate.readTextFile(fileName);
		} catch (final org.espilce.commons.generator.api.fsa.RuntimeIOException e) {
			throw new org.eclipse.xtext.util.RuntimeIOException(e);
		}
	}
}
