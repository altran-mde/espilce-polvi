/*******************************************************************************
 * Copyright (C) 2022 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.polvi.emf.generator.context;


import org.eclipse.core.runtime.IProgressMonitor;
import org.espilce.polvi.generator.api.context.CancelIndicator;

/**
 * Adapts an {@link IProgressMonitor} to a {@link CancelIndicator}.
 */
public class ProgressMonitorAdapter implements CancelIndicator, IProgressMonitor {
	private final IProgressMonitor delegate;
	
	public ProgressMonitorAdapter(final IProgressMonitor delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void beginTask(final String name, final int totalWork) {
		this.delegate.beginTask(name, totalWork);
	}
	
	@Override
	public void done() {
		this.delegate.done();
	}
	
	@Override
	public void internalWorked(final double work) {
		this.delegate.internalWorked(work);
	}
	
	@Override
	public boolean isCanceled() { return this.delegate.isCanceled(); }
	
	@Override
	public void setCanceled(final boolean value) {
		this.delegate.setCanceled(value);
	}
	
	@Override
	public void setTaskName(final String name) {
		this.delegate.setTaskName(name);
	}
	
	@Override
	public void subTask(final String name) {
		this.delegate.subTask(name);
	}
	
	@Override
	public void worked(final int work) {
		this.delegate.worked(work);
	}
}
