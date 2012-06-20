/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.dialect.lock;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.OptimisticLockException;
import org.hibernate.action.internal.EntityVerifyVersionProcess;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.spi.EventSource;
import org.hibernate.persister.entity.Lockable;

/**
 * An optimistic locking strategy that verifies that the version hasn't changed (prior to transaction commit).
 * <p/>
 * This strategy is valid for LockMode.OPTIMISTIC
 *
 * @author Scott Marlow
 * @since 3.5
 */
public class OptimisticLockingStrategy implements LockingStrategy {

	private final Lockable lockable;
	private final LockMode lockMode;

	/**
	 * Construct locking strategy.
	 *
	 * @param lockable The metadata for the entity to be locked.
	 * @param lockMode Indicates the type of lock to be acquired.
	 */
	public OptimisticLockingStrategy(Lockable lockable, LockMode lockMode) {
		this.lockable = lockable;
		this.lockMode = lockMode;
		if ( lockMode.lessThan( LockMode.OPTIMISTIC ) ) {
			throw new HibernateException( "[" + lockMode + "] not valid for [" + lockable.getEntityName() + "]" );
		}
	}

	@Override
	public void lock(Serializable id, Object version, Object object, int timeout, SessionImplementor session) {
		if ( !lockable.isVersioned() ) {
			throw new OptimisticLockException( object, "[" + lockMode + "] not supported for non-versioned entities [" + lockable.getEntityName() + "]" );
		}
		EntityEntry entry = session.getPersistenceContext().getEntry(object);
		EventSource source = (EventSource)session;
		EntityVerifyVersionProcess verifyVersion = new EntityVerifyVersionProcess(object, entry);
		// Register the EntityVerifyVersionProcess action to run just prior to transaction commit.
		source.getActionQueue().registerProcess(verifyVersion);
	}

	protected LockMode getLockMode() {
		return lockMode;
	}
}