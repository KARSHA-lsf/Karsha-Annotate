/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.data;

import org.karsha.controler.ControlerServelet;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.karsha.entities.Document;
import org.karsha.entities.Phrase;

/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes 
 * April 14, 2012 Kasun Perera Created
 * June 20, 2012 Kasun Perera   Made all the methods Static with modification to their references
 *                              Changed the class name   
 *
 */

public class PhraseDB {
    
    public static ArrayList<Phrase> getAllPhrasesByDocId(int docId) {
    
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT PhraseId, PhraseContent,ScoreInDoc from phrase where SectionID = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, docId);
            rs = ps.executeQuery();
            ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
            while (rs.next())
            {
                Phrase d = new Phrase();
                d.setPhraseContent(rs.getString("PhraseContent"));
                d.setScore(Integer.parseInt(rs.getString("ScoreInDoc")));
                d.setPhraseId(Integer.parseInt(rs.getString("PhraseId")));
              
                
                phraseList.add(d);
            }
            return phraseList;
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
        
    
    public static int getPhrasesIDByContent(String phraseContent) {
    ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT PhraseId from phrase where PhraseContent = ? ORDER BY Date DESC LIMIT 1";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, phraseContent);
            rs = ps.executeQuery();
            int phraseID = 0;
            //ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
            while (rs.next())
            {
                phraseID=Integer.parseInt(rs.getString("PhraseId"));
//                Phrase d = new Phrase();
//                d.setPhraseContent(rs.getString("PhraseContent"));
//                d.setScore(Integer.parseInt(rs.getString("ScoreInDoc")));
//                d.setPhraseId(Integer.parseInt(rs.getString("PhraseId")));
//              
//                
//                phraseList.add(d);
            }
            return  phraseID;
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    //public void saveTopKPhrses(ArrayList<Phrase> phrselist){
         public static void saveTopKPhrses(HashMap<Integer,Phrase> map){
         int updateQuery = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        
        Phrase p =new Phrase();
        try
        {
        for (Map.Entry entry : map.entrySet()) {
                              
                     
                    p = (Phrase)entry.getValue();
                    
                    
                    int docID=p.getDocId();
        int userId=p.getUserId();
        String phrseContent=p.getPhraseContent();
        int score=p.getScore();
    
           
            
        String query = "INSERT INTO phrase(SectionID, PhraseContent, ScoreInDoc, UserID) VALUES (?, ?, ? , ?)";

        
            ps = connection.prepareStatement(query);
             ps.setInt(1, docID);
                          ps.setString(2,phrseContent);
                          ps.setInt(3,score);
                           ps.setInt(4, userId);
              updateQuery = ps.executeUpdate();
                            if (updateQuery != 0) { 
              }
           
         
            
        }
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
      
          
        
    }
    
         public static boolean deletePhrasesByDocId(int docId) {
    
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int updateStatus;
        
        String query = "delete from phrase where SectionID = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, docId);
            updateStatus = ps.executeUpdate();
            
         if(updateStatus<1){
               return false; 
            }
            else {
                return true;
            }
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void saveTopKPhrses(ArrayList<Phrase> listOfPhrase) {
         int updateQuery = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        
        Phrase p =new Phrase();
        try
        {
        for (int i=0;i<listOfPhrase.size();i++) {
                              
                     
                    p = (Phrase)listOfPhrase.get(i);
                    
                    
                    int docID=listOfPhrase.get(i).getDocId();
        int userId=listOfPhrase.get(i).getUserId();
        String phrseContent=listOfPhrase.get(i).getPhraseContent();
        int score=listOfPhrase.get(i).getScore();
    
           
            
        String query = "INSERT INTO phrase(SectionID, PhraseContent, ScoreInDoc, UserID) VALUES (?, ?, ? , ?)";

        
            ps = connection.prepareStatement(query);
             ps.setInt(1, docID);
                          ps.setString(2,phrseContent);
                          ps.setInt(3,score);
                           ps.setInt(4, userId);
              updateQuery = ps.executeUpdate();
                            if (updateQuery != 0) { 
              }
           
         
            
        }
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
      
          
    }
         
       public static void saveTopKPhrses(Phrase phrase) {
         int updateQuery = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        
     
        try
        {
                     
                    int docID=phrase.getDocId();
        int userId=phrase.getUserId();
        String phrseContent=phrase.getPhraseContent();
        int score=phrase.getScore();
    
           
            
        String query = "INSERT INTO phrase(SectionID, PhraseContent, ScoreInDoc, UserID) VALUES (?, ?, ? , ?)";

        
            ps = connection.prepareStatement(query);
             ps.setInt(1, docID);
                          ps.setString(2,phrseContent);
                          ps.setInt(3,score);
                           ps.setInt(4, userId);
              updateQuery = ps.executeUpdate();
                            if (updateQuery != 0) { 
              }
           
         
            
    
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
      
          
    }  
}   

