
/**
 *   Copyright (C) 2013, Lanka Software Foundation and and University of Maryland.
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
 * Date Author Changes April 14, 2012 v-bala Created
 *
 */
package org.karsha.data;

import java.sql.Connection;
import java.sql.SQLException;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;
//import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSource;
//import org.apache.commons.pool.KeyedObjectPoolFactory;
//import org.apache.taglibs.standard.tag.rt.core.


/*
 * This class creates connection to the the database
 */
public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static BasicDataSource dataSource = null;

    public synchronized static ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    private ConnectionPool() {
        try {
//            String url = "jdbc:mysql://rss1.stratoslive.wso2.com/karsha_annotate_karsha_opensource_lk";
//            String username = "adm_ann_lQrhzZUK";
//           String password = "admin@lsf";
            
            
            /*the correct DB credentials for WSO@*/
    //      String url = "jdbc:mysql://rss1.stratoslive.wso2.com/karshaannotate_2_karsha_opensource_lk";
    //      String username = "admin_2_lQrhzZUK";
    //      String password = "admin@lsf";     
           
      String url = "jdbc:mysql://localhost:3306/karshaannotate";      
            String username = "karshaa";
          String password = "karshaa";
            
            /*Credentials for UMIACS server */ 
//         String url = "jdbc:mysql://localhost:3306/karshaannotate";      
//          String username = "karsha";
//          String password = "em$.N0w";
            
//                    
//                    String url = "jdbc:mysql://rss1.stratoslive.wso2.com/karshaannotate_kasunperera_com";
//           
//            String username = "admin2_i+APQovg";
//            String password = "admin2";

            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            
//            dataSource.setValidationQuery("SELECT 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
