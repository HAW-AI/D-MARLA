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
package org.hibernate.internal.util.jndi;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.hibernate.cfg.Environment;

/**
 * Helper for dealing with JNDI.
 *
 * @deprecated As JNDI access should get routed through {@link org.hibernate.service.jndi.spi.JndiService}
 */
@Deprecated
public final class JndiHelper {
	private JndiHelper() {
	}

	/**
	 * Given a hodgepodge of properties, extract out the ones relevant for JNDI interaction.
	 *
	 * @param configurationValues The map of config values
	 *
	 * @return The extracted JNDI specific properties.
	 */
	@SuppressWarnings({ "unchecked" })
	public static Properties extractJndiProperties(Map configurationValues) {
		final Properties jndiProperties = new Properties();

		for ( Map.Entry entry : (Set<Map.Entry>) configurationValues.entrySet() ) {
			if ( !String.class.isInstance( entry.getKey() ) ) {
				continue;
			}
			final String propertyName = (String) entry.getKey();
			final Object propertyValue = entry.getValue();
			if ( propertyName.startsWith( Environment.JNDI_PREFIX ) ) {
				// write the IntialContextFactory class and provider url to the result only if they are
				// non-null; this allows the environmental defaults (if any) to remain in effect
				if ( Environment.JNDI_CLASS.equals( propertyName ) ) {
					if ( propertyValue != null ) {
						jndiProperties.put( Context.INITIAL_CONTEXT_FACTORY, propertyValue );
					}
				}
				else if ( Environment.JNDI_URL.equals( propertyName ) ) {
					if ( propertyValue != null ) {
						jndiProperties.put( Context.PROVIDER_URL, propertyValue );
					}
				}
				else {
					final String passThruPropertyname = propertyName.substring( Environment.JNDI_PREFIX.length() + 1 );
					jndiProperties.put( passThruPropertyname, propertyValue );
				}
			}
		}

		return jndiProperties;
	}

	public static InitialContext getInitialContext(Properties props) throws NamingException {
		Hashtable hash = extractJndiProperties(props);
		return hash.size()==0 ?
				new InitialContext() :
				new InitialContext(hash);
	}

	/**
	 * Bind val to name in ctx, and make sure that all intermediate contexts exist.
	 *
	 * @param ctx the root context
	 * @param name the name as a string
	 * @param val the object to be bound
	 *
	 * @throws NamingException Indicates a problem performing the bind.
	 */
	public static void bind(Context ctx, String name, Object val) throws NamingException {
		try {
			ctx.rebind(name, val);
		}
		catch (Exception e) {
			Name n = ctx.getNameParser("").parse(name);
			while ( n.size() > 1 ) {
				String ctxName = n.get(0);

				Context subctx=null;
				try {
					subctx = (Context) ctx.lookup(ctxName);
				}
				catch (NameNotFoundException ignore) {
				}

				if (subctx!=null) {
					ctx = subctx;
				}
				else {
					ctx = ctx.createSubcontext(ctxName);
				}
				n = n.getSuffix(1);
			}
			ctx.rebind(n, val);
		}
	}
}

