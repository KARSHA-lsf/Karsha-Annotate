<%-- 
   Copyright (C) 2013, Lanka Software Foundation and and University of Maryland.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License as
   published by the Free Software Foundation, either version 3 of the
   License, or (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>


    Document   : karshalogin
    Created on : Mar 26, 2012, 1:04:25 PM
    Author     : v-saba
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
    <head>


        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <meta name="description" content="Your description goes here" />
        <meta name="keywords" content="your,keywords,goes,here" />
        <meta name="author" content="Your Name" />


        <!--    <link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />-->

        <link rel="stylesheet" type="text/css" href="css/karshamarkup.css"  media="screen,projection" />

        <style type="text/css">
            /* This is only a demonstration of the included colors in the andreas09 template. Don't use this file as a starting point when you use the template, use the included index.html or 2col.html instead! */
            #container{background:#f0f0f0 url(images/bodybg-black.jpg) repeat-x;}
            #mainmenu a:hover{background:#f0f0f0 url(images/menuhover-black.jpg) top left repeat-x;}
            #mainmenu a.current{background:#f0f0f0 url(images/menuhover-black.jpg) top left repeat-x;}
        </style>
        <title>Karsha Annotation Tool </title>
    </head>

    <body>


        <div id="container">
            <div id="sitename">
                <h1>Karsha</h1>
                <h2>Annotation Tool</h2>

                <%
                    String userLogin = (String) session.getAttribute("username");
                    if (userLogin != null) {
                %>
                <label> Welcome <%=userLogin%> </label>&nbsp;&nbsp;&nbsp;
                <a href="userlogout"><font color="white"> Log Out </font>
                </a>
                <%
                    }
                %> 


            </div>
            <div id="mainmenu">
                <ul>
                    <li><a href="index.html">Karsha</a></li>

                </ul>
            </div>

            <div id="wrap">
<!--                <div id="leftside">

                    <p>

                                                <a class="nav" href="createcollection">Admin</a>
                                                <a class="nav sub" href="createcollection">Create Collection</a>
                        <a class="nav" href="uploaddocuments">Admin</a>

                        <a class="nav sub" href="uploaddocuments">Upload Doc</a>
                        <a class="nav sub" href="newuser">User Management</a>

                    </p>

                    <p>
                        <a class="nav" href="createcollection">Annotate</a>
                                                <a class="nav" href="docSimilarity">Doc Similarity</a>
                    </p>


                </div>-->

            </div>

            <div id="content"  style="height:auto; min-height: 800px">

                <!--   <div id="content"  style="height:auto;">-->





                <%@page contentType="text/html" pageEncoding="UTF-8"%>
                <%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>

                <div id="indexRightColumn">

                    <%
               //Showing message that session is timeout

                        String timeout_msg = (String) session.getAttribute("timeout_message");
                        if (timeout_msg != null) {


                    %>

                    <h2 style="color:red;">Please signon on this page because you did not signon or your session is timed out.</h2>


                    <%
                            session.setAttribute("timeout_message", null);
                        }
                    %>


                    <%
                        String myname = (String) session.getAttribute("username");

                        if (myname != null) {
                            response.sendRedirect("createcollection");
                        } else {

                            if (request.getMethod().equals("POST")) {

                    %>
                    <h1>Login Form - Error</h1>
                    <p>You did not log in successfully.</p>
                    <p>Please check your username and password and try again.</p>

                    <%                }
                    %>

<!--                    <script>
                        var ele = document.getElementById("leftside");
                        ele.style.display = "none";
                    </script>-->

                    <form action="userlogin" method="post">
                        <table>
                            <tr>
                                <td> <label>Username</label> <input name="username" type="text" /> </td> 
                            </tr>
                            <tr></tr>
                            <tr></tr>

                            <tr>
                                <td> <label>Password</label> <input type="password" name="password" /> </td>
                            </tr>
                        </table>

                        <div>
                            <br/>
                            <input type="submit" value="login" name="Login" />
                        </div>           


                    </form>
                    <%
                        }
                        String msg = request.getParameter("msg");
                        if (msg != null) {
                    %>
                    <label><font color="red"><%=msg%></font></label> 
                    <%
                        }
                    %>


                </div>
            </div>

            <div class="clearingdiv">&nbsp;</div>            

            <div id="footer">
                <p>&copy; 2012 <a href="www.opensource.lk">Lanka Software Foundation</a></p>
            </div>

        </div>
    </body>
</html>
