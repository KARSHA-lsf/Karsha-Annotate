/*
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
 */
package org.karsha.controler;

/**
 *
 * @author lsf
 */
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author me
 */
public class LoginFilter implements Filter {

    /**
     * Creates a new instance of LoginFilter
     */
    public LoginFilter() {
    }

    public void destroy() {
    }

    public void doFilter(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse, javax.servlet.FilterChain filterChain) throws java.io.IOException,
            javax.servlet.ServletException {
        HttpServletRequest hreq = (HttpServletRequest) servletRequest;
        HttpServletResponse hres = (HttpServletResponse) servletResponse;
        HttpSession session = hreq.getSession();
        String url = hreq.getServletPath();

        // boolean loginPage = url.endsWith("Login.jsp"); // boolean isJsp = url.endsWith(".jsp");

        /**
         * Dont filter login.jsp because otherwise an endless loop. & only
         * filter .jsp otherwise it will filter all images etc as well.
         *         
*/
        if (url.equals("/getdocuments")) {

            if (session.getAttribute("username") == null) {

                session.setAttribute("timeout_message", "Please signon on this page because you did not signon or your session is timed out.");
                hres.sendRedirect("login");
                return;
            }
        } 
        
       
        else if (url.equals("/deletemarkedupdata")) {
            String username = (String) session.getAttribute("username"); 
            
            if (!username.equals("admin")) {
                session.setAttribute("admin_privilage_message", "You need Admin privilages to clear MarkedUp Data, Signout & Login as Admin user to Clear Data");
                hres.sendRedirect("noadminprivilege");
                return;
            }
        }
        else if (url.equals("/uploaddocuments")) {
            String username = (String) session.getAttribute("username");

            if (username == null) {

                session.setAttribute("timeout_message", "Please signon on this page because you did not signon or your session is timed out.");
                hres.sendRedirect("login");
                return;
            } else if (!username.equals("admin")) {
                session.setAttribute("admin_privilage_message", "You need Admin privilages to upload documents, Signout & Login as Admin user to upload Documents");
                hres.sendRedirect("noadminprivilege");
                return;
            }
        } else if (url.equals("/newuser")) {

            String username = (String) session.getAttribute("username");
            if (username == null) {

                session.setAttribute("timeout_message", "Please signon on this page because you did not signon or your session is timed out.");
                hres.sendRedirect("login");
                return;
            } else if (!username.equals("admin")) {
                session.setAttribute("admin_privilage_message", "You need Admin privilages for User Management, Signout & Login as Admin user to Manage Users");
                hres.sendRedirect("noadminprivilege");
                return;
            }
        } else if (url.equals("/selectphrases")) {

            if (session.getAttribute("username") == null) {

                session.setAttribute("timeout_message", "Please signon on this page your session is timed out.");
                hres.sendRedirect("login");
                return;
            }
        } else if (url.equals("/markwithfibo")) {

            if (session.getAttribute("username") == null) {

                session.setAttribute("timeout_message", "Please signon on this page because you did not signon or your session is timed out.");
                hres.sendRedirect("login");
                return;
            }
        } else if (url.equals("/getcollection")) {


            if (session.getAttribute("username") == null) {

                session.setAttribute("timeout_message", "Please signon on this page because you did not signon or your session is timed out.");
                hres.sendRedirect("login");
                return;
            }
        } else if (url.equals("/savedata")) {

            if (session.getAttribute("username") == null) {

                session.setAttribute("timeout_message", "Please signon on this page because you did not signon or your session is timed out.");
                hres.sendRedirect("login");
                return;
            }
        }
        
        else if (url.equals("/createcollection")) {

            if (session.getAttribute("username") == null) {

                session.setAttribute("timeout_message", "Please signon on this page because you did not signon or your session is timed out.");
                hres.sendRedirect("login");
                return;
            }
        }
        
        else if (url.equals("/index.jsp")) {
 hres.sendRedirect("login");
         
        }
        /*
         * deliver request to next filter
         */
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void init(javax.servlet.FilterConfig filterConfig) throws javax.servlet.ServletException {
    }
}
