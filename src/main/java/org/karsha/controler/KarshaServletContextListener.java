/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.karsha.controler;

//import com.sun.org.apache.xml.internal.security.Init;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;

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
