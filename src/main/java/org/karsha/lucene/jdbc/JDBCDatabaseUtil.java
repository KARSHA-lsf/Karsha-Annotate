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
 * Date             Author          Changes 
 * Jan 1, 2013     Kasun Perera    Created   
 * 
 */

package org.karsha.lucene.jdbc;

 

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
