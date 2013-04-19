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
 *
 */
package org.karsha.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.karsha.entities.User;


public class UserDB {

    public static int insert(User user) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method adds a new record to the Users table in the database
        String query =
                "INSERT INTO user (UserName, Email, Password, Date) "
                + "VALUES (?, ?, ?, CURDATE())";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmailAddress());
            ps.setString(3, user.getPassword());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    /*
    
    public static User getUserByUserName(String userName) {
   String url = "jdbc:mysql://rss1.stratoslive.wso2.com/karshaannotate_2_karsha_opensource_lk";
            String username = "admin_2_lQrhzZUK";
           String password = "admin@lsf"; 
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setValidationQuery("SELECT 1");

        Connection dbConnection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "Select * from user where UserName = ?";
        User karshaUser = null;
        try {
            dbConnection = ds.getConnection();
            ps = dbConnection.prepareStatement(query);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            if (rs.next()) {

                karshaUser = new User();
                karshaUser.setUserId(Integer.parseInt(rs.getString("UserID")));
                karshaUser.setUserName(userName);
                karshaUser.setPassword(rs.getString("Password"));
                karshaUser.setEmailAddress(rs.getString("Email"));
            }

           


            dbConnection.close();
            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
 return karshaUser;

       
    }
*/
    
      public static User getUserByUserName(String userName){
     
      ConnectionPool pool = ConnectionPool.getInstance();
      Connection connection    = pool.getConnection(); 
      
      PreparedStatement ps = null; ResultSet rs = null;
     
      String query = "Select * from user where UserName = ?"; 
      User karshaUser =   null;
     
      try { ps = connection.prepareStatement(query); ps.setString(1, userName);
      rs = ps.executeQuery(); 
      
      if(rs.next()){
     
      karshaUser = new User();
      karshaUser.setUserId(Integer.parseInt(rs.getString("UserID")));
      karshaUser.setUserName(userName);
      karshaUser.setPassword(rs.getString("Password"));
      karshaUser.setEmailAddress(rs.getString("Email")); }
     
      return karshaUser;
     
      }
      catch(SQLException e) { e.printStackTrace(); return null; } 
      
      finally {
      DBUtil.closeResultSet(rs); DBUtil.closePreparedStatement(ps);
      pool.freeConnection(connection); }
     
      }
     
    public static boolean userNameExist(String userName) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT UserName FROM user WHERE UserName = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<User> getAllUsers() {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user";

        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<User> usersList = new ArrayList<User>();
            while (rs.next()) {
                User u = new User();
                u.setUserName(rs.getString("UserName"));
                u.setEmailAddress(rs.getString("Email"));
                u.setUserId(rs.getInt("UserID"));
                usersList.add(u);
            }
            return usersList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    public static boolean deleteUserByUserId(int userId) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int updateStatus;

        String query = "delete from user where UserID =? ";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            updateStatus = ps.executeUpdate();

            if (updateStatus < 1) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void updateUser(User user) {

        int updateQuery = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //Document d= new Document();

        try {


            String query = "UPDATE document SET UserName=?, Email=?, Password=? WHERE DocID = ?";


            ps = connection.prepareStatement(query);
            ps.setString(0, user.getUserName());
            ps.setString(1, user.getEmailAddress());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getUserId());

            updateQuery = ps.executeUpdate();
            if (updateQuery != 0) {
            }




        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }




    }
}
