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
package org.hibernate.dialect;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.cfg.Environment;

/**
 * A SQL dialect for Ingres 10 and later versions.
 * <p/>
 * Changes:
 * <ul>
 * <li>Add native BOOLEAN type support</li>
 * <li>Add identity column support</li>
 * </ul>
 *
 * @author Raymond Fan
 */
public class Ingres10Dialect extends Ingres9Dialect {
    public Ingres10Dialect() {
        super();
        registerBooleanSupport();
    }

    // Boolean column type support

    /**
     * The SQL literal value to which this database maps boolean values.
     *
     * @param bool The boolean value
     * @return The appropriate SQL literal.
     */
    public String toBooleanValueString(boolean bool) {
        return bool ? "true" : "false";
    }

    protected void registerBooleanSupport() {
        // Column type

        // Boolean type (mapping/BooleanType) mapping maps SQL BIT to Java
        // Boolean. In order to create a boolean column, BIT needs to be mapped
        // to boolean as well, similar to H2Dialect.
        registerColumnType( Types.BIT, "boolean" );
        registerColumnType( Types.BOOLEAN, "boolean" );

        // Functions

        // true, false and unknown are now valid values
        // Remove the query substitutions previously added in IngresDialect.
        Properties properties = getDefaultProperties();
        String querySubst = properties.getProperty(Environment.QUERY_SUBSTITUTIONS);
        if (querySubst != null) {
            String newQuerySubst = querySubst.replace("true=1,false=0","");
            properties.setProperty(Environment.QUERY_SUBSTITUTIONS, newQuerySubst);
        }
    }

	// IDENTITY support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public boolean supportsIdentityColumns() {
		return true;
	}

	public boolean hasDataTypeInIdentityColumn() {
		return true;
	}

	public String getIdentitySelectString() {
		return "select last_identity()";
	}

	public String getIdentityColumnString() {
		return "not null generated by default as identity";
	}

	public String getIdentityInsertString() {
		return "default";
	}
}
