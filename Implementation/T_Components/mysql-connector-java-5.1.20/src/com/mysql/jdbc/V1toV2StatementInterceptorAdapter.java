/*
 Copyright (c) 2009, 2010, Oracle and/or its affiliates. All rights reserved., Inc. All rights reserved.
 U.S. Government Rights - Commercial software. Government users are subject
 to the Sun Microsystems, Inc. standard license agreement and applicable
 provisions of the FAR and its supplements. Use is subject to license terms.
 This distribution may include materials developed by third parties.Sun,
 Sun Microsystems, the Sun logo and MySQL Enterprise Monitor are
 trademarks or registered trademarks of Sun Microsystems, Inc. in the U.S.
 and other countries.

 Copyright (c) 2009, 2010, Oracle and/or its affiliates. All rights reserved., Inc. Tous droits réservés.
 L'utilisation est soumise aux termes du contrat de licence.Cette
 distribution peut comprendre des composants développés par des tierces
 parties.Sun, Sun Microsystems,  le logo Sun et  MySQL Enterprise Monitor sont
 des marques de fabrique ou des marques déposées de Sun Microsystems, Inc.
 aux Etats-Unis et dans du'autres pays.

 */

package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.Properties;

public class V1toV2StatementInterceptorAdapter implements StatementInterceptorV2 {
	private final StatementInterceptor toProxy;
	
	public V1toV2StatementInterceptorAdapter(StatementInterceptor toProxy) {
		this.toProxy = toProxy;
	}
	public ResultSetInternalMethods postProcess(String sql,
			Statement interceptedStatement,
			ResultSetInternalMethods originalResultSet, Connection connection,
			int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, 
			SQLException statementException) throws SQLException {
		return toProxy.postProcess(sql, interceptedStatement, originalResultSet, connection);
	}

	public void destroy() {
		toProxy.destroy();
	}

	public boolean executeTopLevelOnly() {
		return toProxy.executeTopLevelOnly();
	}

	public void init(Connection conn, Properties props) throws SQLException {
		toProxy.init(conn, props);
	}

	public ResultSetInternalMethods preProcess(String sql,
			Statement interceptedStatement, Connection connection)
			throws SQLException {
		return toProxy.preProcess(sql, interceptedStatement, connection);
	}

}
