/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.lucene.jdbc;

/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes Dec 31, 2012 Kasun Perera Created
 *
 */
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
