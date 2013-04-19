/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.karsha.lucene.jdbc;

/** 
 * Copyright (C) 2012, Lanka Software Foundation. 
 * 
 * Date             Author          Changes 
 * Jan 1, 2013     Kasun Perera    Created   
 * 
 */ 

/**
 * TODO- describe the  purpose  of  the  class
 * 
 */



import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
//import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * The Class JDBCDatabaseUtil.
 * @author prabhat.jha
 */
public class JDBCDatabaseUtil {
	/**
	 * Gets the data source.
	 * 
	 * @return the data source
	 */
	public synchronized static DataSource getDataSource() {
            
             // String url = "jdbc:mysql://rss1.stratoslive.wso2.com/karsha_annotate_karsha_opensource_lk";
//            String username = "adm_ann_lQrhzZUK";
//           String password = "admin@lsf";
		//MysqlDataSource dataSource = new MysqlDataSource();
                BasicDataSource dataSource =new BasicDataSource();
//                dataSource.setUser("adm_ann_lQrhzZUK");             
//		dataSource.setPassword("admin@lsf");
//		dataSource.setUrl("jdbc:mysql://rss1.stratoslive.wso2.com/karsha_annotate_karsha_opensource_lk");


        
                 dataSource.setUsername("admin_i+APQovg");                
		dataSource.setPassword("admin");
                
                //emulateLocators=true&useUnicode=true&characterEncoding=UTF-8&useFastDateParsing‌​=false
                dataSource.setUrl("jdbc:mysql://rss1.stratoslive.wso2.com/karshaindex_kasunperera_com?emulateLocators=true&useUnicode=true&characterEncoding=UTF-8&useFastDateParsing‌​=false");

                
//                dataSource.setUsername("root");                
//		dataSource.setPassword("nbuser");
//                dataSource.setUrl("jdbc:mysql://localhost:3306/karshaindex?emulateLocators=true&useUnicode=true&characterEncoding=UTF-8&useFastDateParsing‌​=false");
              // dataSource.setEmulateLocators(true);
		return dataSource;
	}

        
	/**
	 * Gets the connection.
	 * 
	 * @return the connection
	 * @throws SQLException
	 *             the sQL exception
	 */
	public static Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}
}
