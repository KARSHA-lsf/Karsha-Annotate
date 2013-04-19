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


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.jdbc.JdbcDirectory;
import org.apache.lucene.store.jdbc.JdbcDirectorySettings;
import org.apache.lucene.store.jdbc.JdbcFileEntrySettings;
import org.apache.lucene.store.jdbc.dialect.MySQLDialect;
import org.apache.lucene.store.jdbc.handler.ActualDeleteFileEntryHandler;
import org.apache.lucene.store.jdbc.index.FetchOnOpenJdbcIndexInput;
import org.apache.lucene.util.Version;
import org.karsha.data.ConnectionPool;
import org.apache.lucene.store.jdbc.index.RAMJdbcIndexOutput;
import org.apache.lucene.store.jdbc.index.RAMAndFileJdbcIndexOutput;
//import org.karsha.lucene.jdbc.MyJDBCDirectory;

/**
 * The Class JDBCIndexer.
 * 
 * @author prabhat.jha
 */
public class JDBCIndexer {

JDBCDirectory jdbcIn= new JDBCDirectory();


	/**
	 * Builds the index.
	 */
	public void buildIndex() {
		createAndBuildIndex();
	}
        
        /*
         * create index only
         */
public void createIndex() {
		createIndexTable();
	}
        
	/**
	 * Creates the and build index.
	 */
	private void createAndBuildIndex() {
		createIndexTable();
		index();
	}

	/**
	 * Index.
	 */
        
	private void index() {
		Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_35);
               
               
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		IndexWriter indexWriter = null;
		try {
			//indexWriter = new IndexWriter(getJdbcDirectory(), indexWriterConfig);
                        //addIndex(indexWriter);
                        IndexWriter writer = new IndexWriter(jdbcIn.getJdbcDirectory(), new IndexWriterConfig(Version.LUCENE_35,
                    new StandardAnalyzer(Version.LUCENE_35)));  
			addIndex(indexWriter);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					indexWriter = null;
				}
			}
		}
	}

	/**
	 * Adds some record in BOOKS table
	 * 
	 * @param indexWriter
	 *            the index writer
	 */
	private void addIndex(IndexWriter indexWriter) throws CorruptIndexException, IOException {
		try {
			Connection connection = JDBCDatabaseUtil.getConnection();
			String query = "SELECT BOOK_ID, BOOK_NAME, BOOK_AUTHOR, BOOK_PUBLISHER FROM books";
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				Document document = new Document();
				document.add(new Field("id", String.valueOf(resultSet.getInt(1)), Field.Store.YES, Field.Index.ANALYZED,Field.TermVector.YES));
				document.add(new Field("name", String.valueOf(resultSet.getString(2)), Field.Store.YES, Field.Index.ANALYZED,Field.TermVector.YES));
				document.add(new Field("author", String.valueOf(resultSet.getString(3)), Field.Store.YES, Field.Index.ANALYZED,Field.TermVector.YES));
				document.add(new Field("publisher", String.valueOf(resultSet.getString(4)), Field.Store.YES, Field.Index.ANALYZED,Field.TermVector.YES));
			
                                      indexWriter.addDocument(document);
            
                        }
                    indexWriter.close();    
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the index table.
	 */
	private void createIndexTable() {
           
          //  private byte val;
           
             JdbcFileEntrySettings segmentsSettings = new JdbcFileEntrySettings();
            long value = 3200000L;
            segmentsSettings.setLongSetting("INDEX_OUTPUT_THRESHOLD_SETTING", value);
            JdbcDirectorySettings settings = new JdbcDirectorySettings();

            segmentsSettings.setClassSetting(JdbcFileEntrySettings.FILE_ENTRY_HANDLER_TYPE, ActualDeleteFileEntryHandler.class);
            segmentsSettings.setClassSetting(JdbcFileEntrySettings.INDEX_INPUT_TYPE_SETTING, FetchOnOpenJdbcIndexInput.class);
            segmentsSettings.setClassSetting(JdbcFileEntrySettings.INDEX_OUTPUT_TYPE_SETTING, RAMJdbcIndexOutput.class);
            settings.registerFileEntrySettings("segments", segmentsSettings);
            settings.registerFileEntrySettings("segments.new", segmentsSettings);

            JdbcFileEntrySettings fnmSettings = new JdbcFileEntrySettings();
            fnmSettings.setLongSetting("INDEX_OUTPUT_THRESHOLD_SETTING", value);
            fnmSettings.setClassSetting(JdbcFileEntrySettings.INDEX_INPUT_TYPE_SETTING, FetchOnOpenJdbcIndexInput.class);
            fnmSettings.setClassSetting(JdbcFileEntrySettings.INDEX_OUTPUT_TYPE_SETTING, RAMJdbcIndexOutput.class);
            settings.registerFileEntrySettings("fnm", fnmSettings);
           
           
           
           // jdbcIn.setJdbcDirectory(new MyJDBCDirectory(JDBCDatabaseUtil.getDataSource(), new MySQLDialect(), "lucene_index_table"));
                
           
           
           
           
           
		jdbcIn.setJdbcDirectory(new MyJDBCDirectory(JDBCDatabaseUtil.getDataSource(), new MySQLDialect(),settings, "lucene_index_table"));
		//jdbcIn.setJdbcDirectory(new MyJDBCDirectory(ConnectionPool.getDataSource(), new MySQLDialect(), "lucene_index_table"));
                
                try {
			/**
			 * No need to manually create index table, create method will
			 * automatically create it.
			 */
                  MyJDBCDirectory d=(MyJDBCDirectory) jdbcIn.getJdbcDirectory();
                   // d.cr
		//((MyJDBCDirectory) jdbcIn.getJdbcDirectory()).create();
                  d.create();
                 // d.close();
                       
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
        
    
}

