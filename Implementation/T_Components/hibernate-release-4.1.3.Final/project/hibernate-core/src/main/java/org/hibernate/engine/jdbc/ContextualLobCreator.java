/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2009 by Red Hat Inc and/or its affiliates or by
 * third-party contributors as indicated by either @author tags or express
 * copyright attribution statements applied by the authors.  All
 * third-party contributions are distributed under license by Red Hat Inc.
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
package org.hibernate.engine.jdbc;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

/**
 * {@link LobCreator} implementation using contextual creation against the JDBC {@link java.sql.Connection} class's LOB creation
 * methods.
 *
 * @author Steve Ebersole
 * @author Gail Badner
 */
public class ContextualLobCreator extends AbstractLobCreator implements LobCreator {
	private LobCreationContext lobCreationContext;

	public ContextualLobCreator(LobCreationContext lobCreationContext) {
		this.lobCreationContext = lobCreationContext;
	}

	/**
	 * Create the basic contextual BLOB reference.
	 *
	 * @return The created BLOB reference.
	 */
	public Blob createBlob() {
		return lobCreationContext.execute( CREATE_BLOB_CALLBACK );
	}

	/**
	 * {@inheritDoc}
	 */
	public Blob createBlob(byte[] bytes) {
		try {
			Blob blob = createBlob();
			blob.setBytes( 1, bytes );
			return blob;
		}
		catch ( SQLException e ) {
			throw new JDBCException( "Unable to set BLOB bytes after creation", e );
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Blob createBlob(InputStream inputStream, long length) {
		try {
			Blob blob = createBlob();
			OutputStream byteStream = blob.setBinaryStream( 1 );
			StreamUtils.copy( inputStream, byteStream );
			byteStream.flush();
			byteStream.close();
			// todo : validate length written versus length given?
			return blob;
		}
		catch ( SQLException e ) {
			throw new JDBCException( "Unable to prepare BLOB binary stream for writing",e );
		}
		catch ( IOException e ) {
			throw new HibernateException( "Unable to write stream contents to BLOB", e );
		}
	}

	/**
	 * Create the basic contextual CLOB reference.
	 *
	 * @return The created CLOB reference.
	 */
	public Clob createClob() {
		return lobCreationContext.execute( CREATE_CLOB_CALLBACK );
	}

	/**
	 * {@inheritDoc}
	 */
	public Clob createClob(String string) {
		try {
			Clob clob = createClob();
			clob.setString( 1, string );
			return clob;
		}
		catch ( SQLException e ) {
			throw new JDBCException( "Unable to set CLOB string after creation", e );
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Clob createClob(Reader reader, long length) {
		try {
			Clob clob = createClob();
			Writer writer = clob.setCharacterStream( 1 );
			StreamUtils.copy( reader, writer );
			writer.flush();
			writer.close();
			return clob;
		}
		catch ( SQLException e ) {
			throw new JDBCException( "Unable to prepare CLOB stream for writing", e );
		}
		catch ( IOException e ) {
			throw new HibernateException( "Unable to write CLOB stream content", e );
		}
	}

	/**
	 * Create the basic contextual NCLOB reference.
	 *
	 * @return The created NCLOB reference.
	 */
	public NClob createNClob() {
		return lobCreationContext.execute( CREATE_NCLOB_CALLBACK );
	}

	/**
	 * {@inheritDoc}
	 */
	public NClob createNClob(String string) {
		try {
			NClob nclob = createNClob();
			nclob.setString( 1, string );
			return nclob;
		}
		catch ( SQLException e ) {
			throw new JDBCException( "Unable to set NCLOB string after creation", e );
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public NClob createNClob(Reader reader, long length) {
		try {
			NClob nclob = createNClob();
			Writer writer = nclob.setCharacterStream( 1 );
			StreamUtils.copy( reader, writer );
			writer.flush();
			writer.close();
			return nclob;
		}
		catch ( SQLException e ) {
			throw new JDBCException( "Unable to prepare NCLOB stream for writing", e );
		}
		catch ( IOException e ) {
			throw new HibernateException( "Unable to write NCLOB stream content", e );
		}
	}

	public static final LobCreationContext.Callback<Blob> CREATE_BLOB_CALLBACK = new LobCreationContext.Callback<Blob>() {
		public Blob executeOnConnection(Connection connection) throws SQLException {
			return connection.createBlob();
		}
	};

	public static final LobCreationContext.Callback<Clob> CREATE_CLOB_CALLBACK = new LobCreationContext.Callback<Clob>() {
		public Clob executeOnConnection(Connection connection) throws SQLException {
			return connection.createClob();
		}
	};

	public static final LobCreationContext.Callback<NClob> CREATE_NCLOB_CALLBACK = new LobCreationContext.Callback<NClob>() {
		public NClob executeOnConnection(Connection connection) throws SQLException {
			return connection.createNClob();
		}
	};
}
