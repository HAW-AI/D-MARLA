/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2012, Red Hat Inc. or third-party contributors as
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
package org.hibernate.test.jpa.naturalid;

import org.junit.Test;

import org.hibernate.Session;
import org.hibernate.test.jpa.AbstractJPATest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Steve Ebersole
 */
public class MutableNaturalIdTest extends AbstractJPATest {
	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class[] { Group.class };
	}

	@Test
	public void testSimpleNaturalIdLoadAccessCacheWithUpdate() {
		Session s = openSession();
		s.beginTransaction();
		Group g = new Group( 1, "admin" );
		s.persist( g );
		s.getTransaction().commit();
		s.close();

		s = openSession();
		s.beginTransaction();
		g = (Group) s.bySimpleNaturalId( Group.class ).load( "admin" );
		assertNotNull( g );
		Group g2 = (Group) s.bySimpleNaturalId( Group.class ).getReference( "admin" );
		assertTrue( g == g2 );
		g.setName( "admins" );
		s.flush();
		g2 = (Group) s.bySimpleNaturalId( Group.class ).getReference( "admins" );
		assertTrue( g == g2 );
		s.getTransaction().commit();
		s.close();

		s = openSession();
		s.beginTransaction();
		s.createQuery( "delete Group" ).executeUpdate();
		s.getTransaction().commit();
		s.close();
	}
}
