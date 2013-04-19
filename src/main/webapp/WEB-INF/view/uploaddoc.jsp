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

    Document   : uploaddoc
    Created on : Mar 9, 2012, 11:03:59 AM
    Author     : Kasun Perera
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
    <head>


        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <meta name="description" content="Your description goes here" />
        <meta name="keywords" content="your,keywords,goes,here" />
        <meta name="author" content="Your Name" />

        <script type="text/javascript" src="scripts/jquery-1.8.1.js"></script>	
        <script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
        <script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
        <script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
        <script type="text/javascript" src="jqwidgets/jqxpanel.js"></script>
        <script type="text/javascript" src="jqwidgets/jqxtree.js"></script>
        <script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>

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
                <div id="leftside">

                    <p>

                        <!--                        <a class="nav" href="createcollection">Admin</a>
                                                <a class="nav sub" href="createcollection">Create Collection</a>-->
                        <a class="nav" href="uploaddocuments">Admin</a>

                        <a class="nav sub" href="uploaddocuments">Upload Doc</a>
                        <a class="nav sub" href="newuser">User Management</a>

                    </p>

                    <p>
                        <a class="nav" href="createcollection">Annotate</a>
                        <!--                        <a class="nav" href="docSimilarity">Doc Similarity</a>-->
                    </p>


                </div>

            </div>

            <div id="content"  style="height:auto; min-height: 800px">

                <!--   <div id="content"  style="height:auto;">-->





                <%@page import="java.util.HashMap"%>
                <%@page import="org.karsha.entities.CollectionType"%>
                <%@page import="org.karsha.data.CollectionTypDB"%>
                <%@page import="java.util.ArrayList"%>


                <div id="indexRightColumn">

                    <h2>Upload to Document Collection</h2> 

                    <%
                        String error_message = request.getParameter("error");
                        String succuss_message = request.getParameter("succuss");
                        HashMap requestParameters = null;
                        String docName = "";
                        String fileName = "";

                        if (error_message != null) {
                            // requestParameters = (HashMap )request.getAttribute("requestParameters");
                            // docName = (requestParameters.get("docName")).toString();
                            // fileName = (requestParameters.get("fileName")).toString();
%>
                    <h2 style="color:red;"> <%=error_message%> </h2>
                    <% }

                        if (succuss_message != null) {
                            //requestParameters = (HashMap )request.getAttribute("requestParameters");
                            // docName = (requestParameters.get("docName")).toString();
                            // fileName = (requestParameters.get("fileName")).toString();
%>
                    <h2 style="color:red;"> <%=succuss_message%> </h2>
                    <% }



                    %>


                    <script language="javascript" type="text/javascript">

                        function popitup(url) {
                            newwindow=window.open(url,'name','height=600,width=950');
                            if (window.focus) {newwindow.focus()}
                            return false;
                        }


                    </script>
                    <div>
                        <h3>Before Upload Documents</h3>
                        <h4>Step 1- Get Bond documents from EMMA Repository</h4>
                       
<iframe width="825" height="800" src="http://emma.msrb.org/Search/Search.aspx"></iframe>
<!--                        <input type="submit" value="Search EMMA" name="emmasearch" onclick="return popitup('http://emma.msrb.org/Search/Search.aspx')"/> -->
                        <!--    <a href="Emma" onclick="return popitup('http://emma.msrb.org/Search/Search.aspx')"
                                >Link to EMMA</a>-->
                        <h4>Step 2- Bookmark the sections of the Downloaded Documents(Use Your PDF Reader to Bookmark)</h4>
                        <h4>Step 3- Upload Bookmarked Document to the Karhsa Database  </h4>
                    </div>

                    <form action="uploaddocuments"  method="post" enctype="multipart/form-data">
                        <table border="0" cellspacing="5" cellpadding="5">

                            <tr>
                                <td>Document Name</td>
                                <td><input type="text" name="docName" value ="<%=docName%>" /></td>
                            </tr>
                            <tr>
                                <td>Select Collection</td>
                                <td>
                                    <%
                                        ArrayList<CollectionType> collectionTypeList = CollectionTypDB.getAllCollectionType();
                                        session.setAttribute("collectionTypeList", collectionTypeList);
                                    %>
                                    <select name="collection_type_new" size="1" >
                                        <%
                                            for (int i = 0; i < collectionTypeList.size(); i++) {
                                        %>
                                        <option  value="<%=collectionTypeList.get(i).getCollectionId()%>"><%=collectionTypeList.get(i).getCollectionType()%></option>

                                        <%
                                            }

                                        %>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Select Document</td>
                                <td><input type="file" name="docfile" value ="<%=fileName%>" accept="MIME_type"/> </td>
                            </tr>

                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr><td colspan="2">&nbsp;</td></tr>

                            <tr>
                                <td colspan="2">
                                    <input type="submit" value="Upload" name="upload"/> 
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>

            </div>

            <div class="clearingdiv">&nbsp;</div>            

            <div id="footer">
                <p>&copy; 2012 <a href="www.opensource.lk">Lanka Software Foundation</a></p>
            </div>

        </div>
    </body>
</html>
