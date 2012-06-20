/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
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
package org.hibernate.metamodel.source.binder;

import org.hibernate.mapping.PropertyGeneration;

/**
 * Source-agnostic description of information needed to bind a singular attribute.
 *
 * @author Steve Ebersole
 */
public interface SingularAttributeSource extends AttributeSource, RelationalValueSourceContainer {
	/**
	 * Determine whether this is a virtual attribute or whether it physically exists on the users domain model.
	 *
	 * @return {@code true} indicates the attribute is virtual, meaning it does NOT exist on the domain model;
	 *         {@code false} indicates the attribute physically exists.
	 */
	public boolean isVirtualAttribute();

	/**
	 * Obtain the nature of this attribute type.
	 *
	 * @return The attribute type nature
	 */
	public SingularAttributeNature getNature();

	/**
	 * Determine whether this attribute is insertable.
	 *
	 * @return {@code true} indicates the attribute value should be used in the {@code SQL INSERT}; {@code false}
	 *         indicates it should not.
	 */
	public boolean isInsertable();

	/**
	 * Determine whether this attribute is updateable.
	 *
	 * @return {@code true} indicates the attribute value should be used in the {@code SQL UPDATE}; {@code false}
	 *         indicates it should not.
	 */
	public boolean isUpdatable();

	/**
	 * Obtain a description of if/when the attribute value is generated by the database.
	 *
	 * @return The attribute value generation information
	 */
	public PropertyGeneration getGeneration();

	/**
	 * Should the attribute be (bytecode enhancement) lazily loaded?
	 *
	 * @return {@code true} to indicate the attribute should be lazily loaded.
	 */
	public boolean isLazy();
}
