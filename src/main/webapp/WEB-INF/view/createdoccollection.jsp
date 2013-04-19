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


    Document   : createdoccollection
    Created on : Apr 9, 2013, 3:44:51 PM
    Author     : Kasun Perera
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
                    <p>
                        <a class="nav" href="createdoccollection">Doc Section MarkUp</a>
                        <!--                        <a class="nav" href="docSimilarity">Doc Similarity</a>-->
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

                    <h2>Document Section Markup</h2> 

                    <table border="0" cellspacing="2">

                        <tbody>
                            <tr>
                                <form method="post" action="getsimdocuments">

                                    <td>Select  Type of Document Collection</td>
                                    <td>

                                        <select name="collection_type" size="1"  id="collection_type" style="width:150px">
                                            <%

                                                ArrayList<CollectionType> collectionTypeList = (ArrayList<CollectionType>) session.getAttribute("collectionTypeList");
                                                for (int i = 0; i < collectionTypeList.size(); i++) {
                                            %>
                                            <option  value="<%=collectionTypeList.get(i).getCollectionId()%>"><%=collectionTypeList.get(i).getCollectionType()%></option>

                                            <%
                                                }

                                            %>



                                        </select>

                                        <script>
                                            <%
                                                String selectedItem;
                                                if (request.getAttribute("selectedCollectionTypeId") != null) {
                                                    selectedItem = request.getAttribute("selectedCollectionTypeId").toString();
                                            %>
                                                document.getElementById('collection_type').value = <%=selectedItem%>;
                                            <%
                                                }
                                            %>
                                        </script>


                                    </td>
                                    <td>
                                        <input type="submit" value="Get Documents" name="getdocuments" />
                                    </td>
                                </form>
                            </tr>

                            <tr><td colspan="3">&nbsp;</td></tr>
                            <!--        <tr><td colspan="3">&nbsp;</td></tr>-->


                            <form name="docForm" method="post" action="docsecmarkup" onsubmit="javascript:return validate();">
                                <tr>
                                    <td colspan="3">
                                        <% if (request.getMethod().equals("POST")) {%>

                                        <br/>
                                        <table class="gridtable" width="100%">
                                            <strong>Select Documents for Annotate</strong>
                                            <br/>
                                            Option 1- Select One Document to Markup the sections
                                            <!--                                            <br/>
                                                                                        Option 2- Select Two or More Documents to Detailed Comparison-->
                                            <!--                                            <br/>
                                                                                        Option 3- Select More Than Two Documents to Compare(Document Wise Comparison)
                                            -->
                                            <Br/>
                                            <br/>
                                            <input type="button" name="CheckAll" value="Select All"
                                                   onClick="checkAll(document.docForm.docCkBox)">
                                                <input type="button" name="UnCheckAll" value="De-Select All"
                                                       onClick="uncheckAll(document.docForm.docCkBox)">

                                                    <br/>
                                                    <br/>
                                                    <thead>
                                                        <tr class="head">
                                                            <th width="5%">Select</th>
                                                            <th width="95%">Document Name</th>

                                                        </tr>
                                                    </thead>

                                                    <tbody>


                                                        <%

                                                            ArrayList<Document> documentList = (ArrayList<Document>) session.getAttribute("documentList");
                                                            for (int i = 0; i < documentList.size(); i++) {
                                                        %>


                                                        <tr>
                                                            <td><input type="checkbox" name="docCkBox" value="<%=documentList.get(i).getDocId()%>" /></td>
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


                                                    <tr>
                                                        <td colspan="3">
                                                            <input type="submit" value="Next" name="docSimilarity"  />
                                                        </td>
                                                    </tr>

                                                    </form> 

                                                    </tbody>
                                                    </table>
                                                    <br/>
                                                    <br/>    

                                                    <!--    <form name="docForm" action="emmasearch">
                                                            <input type="submit" value="Search Emma Repo" name="emmasearch" />
                                                            
                                                        </form>-->
                                                    </div>
                                                    </div>

                                                    <div class="clearingdiv">&nbsp;</div>            

                                                    <div id="footer">
                                                        <p>&copy; 2012 <a href="www.opensource.lk">Lanka Software Foundation</a></p>
                                                    </div>

                                                    </div>
                                                    </body>
                                                    </html>

