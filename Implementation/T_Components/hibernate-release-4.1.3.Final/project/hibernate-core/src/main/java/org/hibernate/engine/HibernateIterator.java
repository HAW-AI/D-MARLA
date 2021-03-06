/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008-2011, Red Hat Inc. or third-party contributors as
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
package org.hibernate.engine;

import java.util.Iterator;

import org.hibernate.JDBCException;

/**
 * Hibernate-specific iterator that may be closed
 *
 * @see org.hibernate.Query#iterate()
 * @see org.hibernate.Hibernate#close(java.util.Iterator)
 *
 * @author Gavin King
 */
public interface HibernateIterator extends Iterator {
	/**
	 * Close the Hibernate query result iterator
	 *
	 * @throws JDBCException Indicates a problem releasing the underlying JDBC resources.
	 */
	public void close() throws JDBCException;
}
