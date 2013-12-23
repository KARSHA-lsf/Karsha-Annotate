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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>


    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="description" content="Your description goes here"/>
    <meta name="keywords" content="your,keywords,goes,here"/>
    <meta name="author" content="Your Name"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <script type="text/javascript" src="scripts/jquery-1.8.1.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxpanel.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxtree.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>


    <script type="text/javascript" src="jqwidgets1/scripts/gettheme.js"></script>
    <script type="text/javascript" src="jqwidgets1/scripts/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxpanel.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxtree.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            // Create jqxTree
            var theme = getDemoTheme();
            // create jqxTree
            $('#jqxTree').jqxTree({ height: '400px', hasThreeStates: true, checkboxes: true, width: '330px', theme: theme });
            $('#jqxCheckBox').jqxCheckBox({ width: '200px', height: '25px', checked: true, theme: theme });
            $('#jqxCheckBox').on('change', function (event) {
                var checked = event.args.checked;
                $('#jqxTree').jqxTree({ hasThreeStates: checked });
            });
            $("#jqxTree").jqxTree('selectItem', $("#home")[0]);
        });
    </script>

    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen, projection">
    <script type="text/javascript" src="javaScript/jquery-1.4.2.min.js">
    </script>
    <script type="text/javascript" src="javaScript/scripts.js">
    </script>

    <style type="text/css">
        .row {
            vertical-align: top;
            height: auto !important;
        }

        .list {
            display: none;
        }

        .show {
            display: none;
        }

        .hide:target + .show {
            display: inline;
        }

        .hide:target {
            display: none;
        }

        .hide:target ~ .list {
            display: inline;
        }

        @media print {
            .hide, .show {
                display: none;
            }
        }
    </style>

    <!--    <link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />-->

    <link rel="stylesheet" type="text/css" href="css/karshamarkup.css" media="screen,projection"/>

    <style type="text/css">
            /* This is only a demonstration of the included colors in the andreas09 template. Don't use this file as a starting point when you use the template, use the included index.html or 2col.html instead! */
        #container {
            background: #f0f0f0 url(images/bodybg-black.jpg) repeat-x;
        }

        #mainmenu a:hover {
            background: #f0f0f0 url(images/menuhover-black.jpg) top left repeat-x;
        }

        #mainmenu a.current {
            background: #f0f0f0 url(images/menuhover-black.jpg) top left repeat-x;
        }
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

<div id="content" style="height:auto; min-height: 800px">

    <%@ page import="org.karsha.entities.FiboTerm" %>
    <%@ page import="java.util.*" %>

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
        <h3>FIBO Terms (Tick Terms To Search)</h3>

        <table border="0" cellspacing="2">
            <tbody>
            <tr>
                <form method="post" action="getsimilardocs">

                    <td>
                        <div id="listContainer">
                            <div class="listControl">
                                <a id="expandList">Expand All</a>
                                <a id="collapseList">Collapse All</a>
                                <input type="submit" value="Search" name="search" />
                            </div>
                            <ul>
                                <li>
                                    <%
                                        List<String> roots = (List<String>)session.getAttribute("roots");
                                    %>
                                    <a><%=roots.get(0)%></a>
                                    <ul>
                                        <li>
                                            <%
                                                List children = (List)session.getAttribute("children");
                                                for(int i=0;i<children.size();i++){
                                                    List  subChil= (List)session.getAttribute("childrenof"+i);
                                            %>
                                            <input id="checkBox" type="checkbox" value="<%=children.get(i)%>" name="fiboterms"><%=children.get(i)%></br>
                                            <ul>
                                                <li>
                                                    <%
                                                        int noofchildren = Integer.parseInt((String)session.getAttribute("numofchildren"));
                                                        for(int x=0;x<subChil.size();x++) {

                                                    %>
                                                    <input id="checkBox" type="checkbox" value="<%=subChil.get(x)%>" name="fiboterms"><%=subChil.get(x)%></br>
                                                    <ul>
                                                        <li>
                                                            <%

                                                                for(int y=0;y<children.size();y++) {
                                                                    for(int z=0;z<subChil.size();z++)   {
                                                                        List subChil2 = (List) session.getAttribute("subChiof00");

                                                            %>

                                                            <input id="checkBox" type="checkbox" value="<%=subChil2.get(y)%>" name="fiboterms"><%=subChil2.get(y)%></br>

                                                            <%
                                                                    }
                                                                }
                                                            %>
                                                        </li>
                                                    </ul>
                                                    <%
                                                        }
                                                    %>

                                                </li>
                                            </ul>
                                            <%

                                                }
                                            %>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        <script>
                            var $checks = $(".expList").change(function () { //for each checkbox
                                $checks.filter(':checked')
                            });
                        </script>
                    </td>
                </form>
            </tr>

            <tr><td colspan="3">&nbsp;</td></tr>


            <form name="docForm" method="post" action="getsimilardocs">
                <tr>
                    <td colspan="3">
                        <% if (request.getMethod().equals("POST")) {%>

                        <br/>
                        <table class="gridtable" width="100%">
                            <tbody>
                            <tr>
                                <td><b> DocID </b></td>
                                <td><b> SimScore </b></td>

                            </tr>
                            <%
                                HashMap<Integer,Double> topKDocs = (HashMap<Integer,Double>)session.getAttribute("topKDocs");

                                for (Map.Entry entryParent: topKDocs.entrySet()){
                                    if((Double) entryParent.getValue()!=0.0){
                                        String docID =entryParent.getKey().toString();
                                        double SimScore=(Double) entryParent.getValue();

                            %>
                            <tr>
                                <td> <%=docID%> </td>
                                <td> <%=SimScore%> </td>

                            </tr>

                            <%
                                    }
                                }

                            %>


                            </tbody>
                        </table>
                        <% }%>

                    </td>
                </tr>

                <tr><td colspan="3">&nbsp;</td></tr>
                <tr><td colspan="3">&nbsp;</td></tr>
            </form>

            </tbody>
        </table>
        <br/>
        <br/>
    </div>
</div>

<div class="clearingdiv">&nbsp;</div>

<div id="footer">
    <p>&copy; 2013 <a href="www.opensource.lk">Lanka Software Foundation</a></p>
</div>

</div>

</body>
</html>
