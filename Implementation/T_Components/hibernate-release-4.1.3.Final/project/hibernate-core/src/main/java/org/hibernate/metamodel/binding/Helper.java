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
package org.hibernate.metamodel.binding;

import org.hibernate.AssertionFailure;
import org.hibernate.metamodel.domain.PluralAttribute;
import org.hibernate.metamodel.domain.PluralAttributeNature;

/**
 * Helper utilities specific to the binding package.
 *
 * @author Steve Ebersole
 */
public class Helper {
	public static void checkPluralAttributeNature(PluralAttribute attribute, PluralAttributeNature expected) {
		if ( attribute.getNature() != expected ) {
			throw new AssertionFailure(
					String.format(
							"Mismatched collection natures; expecting %s, but found %s",
							expected.getName(),
							attribute.getNature().getName()
					)
			);
		}
	}
}
