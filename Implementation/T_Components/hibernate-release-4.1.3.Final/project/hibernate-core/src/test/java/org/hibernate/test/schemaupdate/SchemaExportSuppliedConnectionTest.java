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
package org.hibernate.test.schemaupdate;

import org.junit.After;
import org.junit.Before;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.testing.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * @author Gail Badner
 */
public class SchemaExportSuppliedConnectionTest extends SchemaExportTest {

	private ServiceRegistry serviceRegistry;

	@Before
	public void setUp() {
		serviceRegistry = ServiceRegistryBuilder.buildServiceRegistry( Environment.getProperties() );
	}

	@After
	public void tearDown() {
		ServiceRegistryBuilder.destroy( serviceRegistry );
		serviceRegistry = null;
	}

	@Override
	protected SchemaExport createSchemaExport(Configuration cfg) {
		return new SchemaExport( serviceRegistry, cfg );
	}
}