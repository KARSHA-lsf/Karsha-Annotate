
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


    Document   : comparealldocs
    Created on : Nov 7, 2012, 1:56:01 PM
    Author     : Kasun Perera
--%>
<%@page import="org.karsha.entities.DocSection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html>
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





<meta name="keywords" content="jQuery Tree, Tree Widget, Tree" /> 
<meta name="description" content="" />
<title id='Description'></title>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="scripts/gettheme.js"></script>
<script type="text/javascript" src="scripts/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
<script type="text/javascript" src="jqwidgets/jqxpanel.js"></script>
<script type="text/javascript" src="jqwidgets/jqxtree.js"></script>
<script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>



<script type="text/javascript">
   
    $(document).ready(function () {
        // Create jqxTree 
        var theme = getTheme();
        // create jqxTree
        $('#jqxTree').jqxTree({ height: '400px', hasThreeStates: true, checkboxes: true, width: '230px', theme: theme });
        $('#jqxCheckBox').jqxCheckBox({ width: '200px', height: '25px', checked: true, theme: theme });
        $('#jqxCheckBox').bind('change', function (event) {
            var checked = event.args.checked;
            $('#jqxTree').jqxTree({ hasThreeStates: checked });
        });
        
        $('#jqxTree').jqxTree('selectItem', $("#jqxTree").find('li:first')[0]);
        var selectedItem = $("#jqxTree").jqxTree('selectedItem');
        var nextItem = $("#jqxTree").jqxTree('getNextItem', selectedItem.element);
        
        
        // var selectedItem = $("#jqxTree").jqxTree('selectedItem');
               // var nextItem = $("#jqxTree").jqxTree('getNextItem', selectedItem.element);
                if (nextItem != null) {
                    $("#jqxTree").jqxTree('selectItem', nextItem.element);
                    $("#jqxTree").jqxTree('ensureVisible', nextItem.element);
//                    alert("test");
                }
        // alert(nextItem.value);
       
       
       
        
        $.get("getDocSection", { docID: nextItem.value},function(data) {
            document.getElementById('td1').value = ""+data;
            // alert("Data Loaded: " + data);
            
             var valueelementdoc = $("<div></div>");
            valueelementdoc.html("Document Name: " + selectedItem.label);
            $("#docnamelog").children().remove();
            $("#docnamelog").append(valueelementdoc);
                
                
            var valueelementdocsec = $("<div></div>");
            valueelementdocsec.html("Doc Section Name: " + nextItem.label);
            $("#docsecnamelog").children().remove();
            $("#docsecnamelog").append(valueelementdocsec);
        });
            
            
        $('#jqxTree').bind('select', function (event) {
            

           
            var args = event.args;
            var item = $('#jqxTree').jqxTree('getItem', args.element);
            var parentItem = $("#jqxTree").jqxTree('getItem', item.parentElement);
           
           
            /*
             * to disply selected section_name and document_name
             */
            var valueelementdoc = $("<div></div>");
            valueelementdoc.html("Document Name: " + parentItem.label);
            $("#docnamelog").children().remove();
            $("#docnamelog").append(valueelementdoc);
                
                
            var valueelementdocsec = $("<div></div>");
            valueelementdocsec.html("Doc Section Name: " + item.label);
            $("#docsecnamelog").children().remove();
            $("#docsecnamelog").append(valueelementdocsec);
                
            //            alert();
            //            alert();

            $.get("getDocSection", { docID: item.value},function(data) {
                document.getElementById('td1').value = data;
                // alert("Data Loaded: " + data);
            });
            ////////////////////////////////////////////////
            //$.get('getDocSection' , {docID: item.value});
           
            //alert(val);
            
            // var docContentMap=document.getElementById(item.value).value;
            // document.getElementById('td1').value = docContentMap;
            // 
            // 
             
           
        });    
        
    });
    
    
 
            
    function getCheckedItems()
    {
        // get all items.
        var items = $("#jqxTree").jqxTree('getItems');
        // save all checked items in checkedItems array.
        var checkedItems = new Array();
        var selectedSectionID;
        //  $.children(expr)
        $.each(items, function () {
            if (this.checked) {
 
                     
                selectedSectionID=this.value;
                 
                if(document.getElementById('selected_check_boxes').value == "")
                {
                    document.getElementById('selected_check_boxes').value = selectedSectionID;
                     
                }
                   
                else
                {                   document.getElementById('selected_check_boxes').value += "\t" + selectedSectionID;
                    //  document.getElementById('selected_check_boxes').value += "***" + selectedSectionID;
                       
                }
                // }
                checkedItems[checkedItems.length] = this.label;
            }
        });
 
        return true;
    }
    
    
</script>

<!--<body class='default'>-->

<div id='jqxWidget'>
    <h2>Select Document Sections</h2>
    <div style='float: left;'>
        <form name="DocSections" action="selectedsections" method="POST" onsubmit="javascript:return getCheckedItems();">
           


            <strong> Documents-DocSections</strong>
            <br/>
            <p style="color:red">* Use Checkboxes to select-deselect<br/>
                Required document sections</p>
           
         
            <div id='jqxTree' style='float: left; margin-left: 20px;'>
                <!--                <strong> Documents-DocSections</strong>-->
                <!--                <em>(Use Checkboxes to select-deselect)</em>-->

                <ul>
                    <%
                        HashMap<String, ArrayList<DocSection>> map = (HashMap<String, ArrayList<DocSection>>) session.getAttribute("selectedDocsMap");
                        for (Map.Entry entry : map.entrySet()) {
                            ArrayList<DocSection> docsectionList = (ArrayList<DocSection>) entry.getValue();



                    %>
                    <li item-checked='true' item-expanded='true' item-value='<%=entry.getKey()%>' item-label='<%=entry.getKey()%>' ><%=entry.getKey()%>
<!--                        <input type="hidden" name="section_<%=entry.getKey()%>" id="section_<%=entry.getKey()%>" value=""/>-->
                        <ul>
                            <% for (int i = 0; i < docsectionList.size(); i++) {


                            %>

                            <li  item-value='<%=docsectionList.get(i).getSectionId()%>' item-label="<%=docsectionList.get(i).getSectionName()%>" >  <%=docsectionList.get(i).getSectionName()%></li>
<!--                            <input type="hidden" name="section_<%=docsectionList.get(i).getSectionName()%>" id="section_<%=docsectionList.get(i).getSectionName()%>" value="<%=docsectionList.get(i).getSectionId()%>"/>-->
                            <%}%>
                        </ul>
                    </li>
                    <%}%>
                </ul>

            </div>
            <input type="hidden" name="selected_check_boxes" id="selected_check_boxes"/>

            <div style='margin-left: 60px; float: left; position:absolute; left: 300px; top: 670px;'> 
                <div class="buttonPos">
                    <br/>
                    <input type="submit" value="Submit Selected" />
                </div>
            </div>

            <div style='margin-left: 60px; float: left; position:absolute; left: 450px; top: 210px;'> 
                <h3> Document Section Content </h3>
                <div style='margin-top: 10px; '>
                    <div style='max-width: 500px; margin-top: 10px; color: black' id="docnamelog"></div>
                    <div style='max-width: 500px; margin-top: 10px; color: black' id="docsecnamelog"></div>
                    <br/>

                    <!--            <textarea id="td1" name="docSectionContenet" rows="18" cols="70" wrap='off' style="font-family:comic sans ms" >-->
                    <!--                    <textarea id="td1" name="docSectionContenet" rows="28" cols="70" wrap='off' style="font: 100%/120% Verdana, Arial, Helvetica, sans-serif" >-->
                    <textarea id="td1" name="docSectionContenet"  wrap='off' style="font: 100%/120% Verdana, Arial, Helvetica, sans-serif; width: 500px; height: 400px;" >



                    </textarea>
                </div>
            </div>



        </form>
        <form name="DocSections" action="createcollection">
            <div style='margin-left: 60px; float: left; position:absolute; left: 175px; top: 684px;'> 
                <input type="submit" value="Back" name="backButton" />
            </div>
        </form>
    </div>



</div>

<!--</body>-->
</div>
        
            <div class="clearingdiv">&nbsp;</div>            

            <div id="footer">
                    <p>&copy; 2012 <a href="www.opensource.lk">Lanka Software Foundation</a></p>
            </div>

        </div>
    </body>
</html>
