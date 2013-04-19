/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.karsha.controler;

//import com.sun.org.apache.xml.internal.security.Init;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;

/** 
 * Copyright (C) 2012, Lanka Software Foundation. 
 * 
 * Date             Author          Changes 
 * Dec 28, 2012     Kasun Perera    Created   
 * 
 */ 

/**
 * TODO- describe the  purpose  of  the  class
 * 
 */
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class KarshaServletContextListener extends HttpServlet
{

    public void init() throws ServletException
    {
          /// Automatically java script can run here
          System.out.println("************");
          System.out.println("*** Servlet Initialized successfully ***..");
          System.out.println("********hooooo***");

    }

    public void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {

    }
}
