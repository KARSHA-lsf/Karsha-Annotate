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


    Document   : selectsimilardoc
    Created on : Nov 29, 2013, 9:46:42 AM
    Author     : Randula Jayathilaka
--%>
<%@page import="org.karsha.entities.Document"%>
<%@page import="org.karsha.entities.CollectionType"%>
<%@page import="java.util.ArrayList"%>
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
            <a class="nav" href="uploaddocuments">Admin</a>
            <a class="nav sub" href="uploaddocuments">Upload Doc</a>
            <a class="nav sub" href="newuser">User Management</a>

        </p>

        <p>
            <a class="nav" href="createcollection">Annotate</a>
        </p>
        <p>
            <a class="nav" href="createdoccollection">Doc Section MarkUp</a>
        </p>
        <p>
            <a class="nav">Search</a>
            <a class="nav sub" href="docsearch">Document Similarity</a>
            <a class="nav sub" href="semanticSearch">Semantic Similarity</a>
        </p>

    </div>

</div>

<div id="content"  style="height:auto; min-height: 800px">

<!--   <div id="content"  style="height:auto;">-->





<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>





<%
    //Showing message that data is success full aster full markup cycle

    String savesucces = (String) session.getAttribute("savesucces");
    if (savesucces != null) {


%>

<h2 style="color:red;"><%=savesucces%></h2>


<%
        session.setAttribute("savesucces", null);
    }
%>


<script>

    function checkAll(field)
    {
        for (i = 0; i < field.length; i++){
            if(!field[i].checked){
                field[i].checked = true ;

            }

        }
    }

    function uncheckAll(field)
    {
        for (i = 0; i < field.length; i++){


            field[i].checked = false ;

        }

    }

    function validate(){
        var selectedckbox=0;
        var noofcheckboxes= document.getElementsByName("docCkBox");
        for(var i=0; i<noofcheckboxes.length; i++){
            if (eval("document.docForm.docCkBox[" + i + "].checked")==true) {
                selectedckbox++;
            }

        }

        if(selectedckbox>=1){ // have to select atleast 1 checkboxes
            return true;
        }
        else{

            alert ('Please select atleast 1 documents');
            return false;
        }

    }


</script>

<div id="indexRightColumn">

    <h2>Find Similar Documents (FIBO based)</h2>

    <table border="0" cellspacing="2">

        <tbody>
        <tr>
            <form method="post" action="getsimilardocuments">

                <td>Select A Document</td>
                <td>

                    <select name="document_name" size="1"  id="document_name" style="width:400px">
                        <%
                            ArrayList<Document> documentNameList = (ArrayList<Document>) session.getAttribute("documentList");
                            for (int i = 0; i < documentNameList.size(); i++) {
                        %>
                        <option  value="<%=documentNameList.get(i).getDocumentName()%>"><%=documentNameList.get(i).getDocumentName()%></option>

                        <%
                            }

                        %>
                    </select>

                    <script>
                        <%
                            String selectedItem;
                            if (request.getAttribute("selectedDocumentName") != null) {
                                selectedItem = request.getAttribute("selectedDocumentName").toString();
                        %>
                        document.getElementById('document_name').value = <%=selectedItem%>;
                        <%
                            }
                        %>
                    </script>


                </td>
                <td>
                    <input type="submit" value="Search" name="search" />
                </td>
            </form>
        </tr>

        <tr><td colspan="3">&nbsp;</td></tr>



            <tr>
                <td colspan="3">
                    <% if (request.getMethod().equals("POST")) {%>

                    <br/>
                    <table class="gridtable" width="100%">
                        <tbody>

                        <%
                            ArrayList<Document> documentList = (ArrayList<Document>) session.getAttribute("similarDocumentList");
                            for (int i = 0; i < documentList.size(); i++) {
                        %>
                        <tr>
                            <td> <%=documentList.get(i).getDocumentName()%> </td>

                        </tr>

                        <%
                            }

                        %>


                        </tbody>
                    </table>
                    <% }%>

                </td>
            </tr>

            <tr><td colspan="3">&nbsp;</td></tr>
            <tr><td colspan="3">&nbsp;</td></tr>


        </tbody>
    </table>
    <br/>
    <br/>
</div>
</div>

<div class="clearingdiv">&nbsp;</div>

<div id="footer">
    <p>&copy; 2012 <a href="www.opensource.lk">Lanka Software Foundation</a></p>
</div>

</div>
</body>
</html>
