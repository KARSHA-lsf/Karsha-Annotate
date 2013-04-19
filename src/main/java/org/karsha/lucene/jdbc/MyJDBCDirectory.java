/**
 *   Copyright (C) 2013, Lanka Software Foundation and University of Maryland.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 * Date Author Changes Dec 31, 2012 Kasun Perera Created
 *
 */
package org.karsha.lucene.jdbc;


/**
 * TODO- describe the purpose of the class
 *
 */
import java.io.IOException;

import javax.sql.DataSource;

import org.apache.lucene.store.jdbc.JdbcDirectory;
import org.apache.lucene.store.jdbc.JdbcDirectorySettings;
import org.apache.lucene.store.jdbc.dialect.Dialect;

/**
 * The Class MyJDBCDirectory.
 *
 * @author prabhat.jha
 */
public class MyJDBCDirectory extends JdbcDirectory {
//public static final String INDEX_OUTPUT_THRESHOLD_SETTING;
    /**
     * Instantiates a new my jdbc directory.
     *
     * @param dataSource the data source
     * @param dialect the dialect
     * @param settings the settings
     * @param tableName the table name
     */
   
    public MyJDBCDirectory(DataSource dataSource, Dialect dialect, JdbcDirectorySettings settings, String tableName) {
   
        super(dataSource, dialect, settings, tableName);
    }

    public MyJDBCDirectory(DataSource dataSource, Dialect dialect, String tableName) {
        super(dataSource, dialect, tableName);
    }
   
    /**
     * (non-Javadoc)
     *
     * @see org.apache.lucene.store.Directory#listAll()
     */
    @Override
    public String[] listAll() throws IOException {
        return super.list();
    }
}
