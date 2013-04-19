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

    Document   : sentencemarkup
    Created on : Jan 23, 2013, 3:46:32 PM
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






<%@page import="java.util.Iterator"%>
<%@page import="org.karsha.entities.FiboTerm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="org.karsha.entities.DocSection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html>

<meta name="keywords" content="jQuery Tree, Tree Widget, Tree" /> 
<meta name="description" content="" />
<title id='Description'></title>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="scripts/gettheme.js"></script>
<script type="text/javascript" src="scripts/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
<script type="text/javascript" src="jqwidgets/jqxpanel.js"></script>
<script type="text/javascript" src="jqwidgets/jqxtree.js"></script>
<script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>
<script type="text/javascript" src="jqwidgets/jqxcombobox.js"></script>
<script type="text/javascript" src="jqwidgets/jqxmenu.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxlistbox.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdropdownlist.js"></script>
<script type="text/javascript" src="jqwidgets/jqxgrid.js"></script>
<script type="text/javascript" src="jqwidgets/jqxgrid.selection.js"></script> 
<script type="text/javascript" src="jqwidgets/jqxgrid.grouping.js"></script>
<script type="text/javascript" src="jqwidgets/jqxgrid.columnsresize.js"></script>
<script type="text/javascript" src="jqwidgets/jqxgrid.sort.js"></script> 
<script type="text/javascript" src="jqwidgets/jqxgrid.pager.js"></script> 
<script type="text/javascript" src="jqwidgets/jqxgrid.edit.js"></script> 



<script type="text/javascript" src="jqwidgets/jqxcombobox.js"></script>

<script type="text/javascript" src="jqwidgets/jqxdata.export.js"></script> 
<script type="text/javascript" src="jqwidgets/jqxgrid.export.js"></script> 

<script type="text/javascript" src="jqwidgets/jqxtabs.js"></script>
<script type="text/javascript" src="scripts/generatedata.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        // Create jqxTree 
        var theme = getTheme();
        
        var data = {};

        
        var generatenewrow=function(sentence){
            var row = {};
            row["sentence"] = sentence;
            row["markup"]="Please Select";
            //            row["definition"] = fiboDef;

            return row;
        }
           
 
        //   var fiboTerm =new Array();
        //   var fiboDefinition= new Array();
        
         
       
        var source =
            {
            localdata: data,
            datatype: "local",
            datafields: [
                { name: 'sentence', type: 'string' },
                { name: 'markup', type: 'string' }
                //                { name: 'definition', type: 'string' }
            ] ,
            
            
            //getting row id, rowdata and number-section number 
            addrow: function (rowid, rowdata, number) {
                // alert(rowid + number );
                         
                // alert(rowid + number);
                   
                // synchronize with the server - send insert command
            },
                    
            //getting row id,and number-section number 
            deleterow: function (rowid) {
                //  alert("deleteing row Id "+rowid);
                // $.post("deleterows", { docSecId: docID, fiboTerm: rowdata['fiboterm'],fiboDef:rowdata['definition'] });
                // synchronize with the server - send delete command
            },
                    
            //                        //getting row id, rowdata and number-section number 
            //                        updaterow: function (rowid, newdata, number) {
            //                            // synchronize with the server - send update command
            //                            
            //                        },
            
            pager: function (pagenum, pagesize, oldpagenum) {
                // callback called when a page or page size is changed.
            },
            
            
            updaterow: function (rowid, rowdata, result) {
                 
                //Send update of Importance to the servelet
                $.post("updatesentence", { docSecId: currentDocId, sentence: ""+rowdata["sentence"], importance:""+rowdata["importance"] }, function(responce){
                    // alert("response:"+responce);
                });
                // alert(rowid+" "+rowdata["sentence"]+" "+rowdata["importance"]+" "+result);
                // synchronize with the server - send update command
                // call result with parameter true if the synchronization with the server is successful 
                // and with parameter false if the synchronization failder.
                result(true);
            }
    
    
        };
        
        //grid data for FIBO terms grid
        
        var dataFibo = {};

        
        var generatenewrowFibo=function(fiboterm, definition,fiboID){
            var row = {};
            
            row["fiboterm"] = fiboterm;
            row["definition"]=definition;
             row["fiboID"] = fiboID;

            return row;
        }
           
 
        //   var fiboTerm =new Array();
        //   var fiboDefinition= new Array();
        
         
       
        var sourceFibo =
            {
            localdata: dataFibo,
            datatype: "local",
            datafields: [
                { name: 'fiboterm', type: 'string' },
                { name: 'definition', type: 'string' },
                { name: 'fiboID', type: 'string' }
            ] ,
            
            
            //getting row id, rowdata and number-section number 
            addrow: function (rowid, rowdata, number) {
                // alert(rowid + number );
                         
                // alert(rowid + number);
                   
                // synchronize with the server - send insert command
            },
                    
            //getting row id,and number-section number 
            deleterow: function (rowid) {
                //  alert("deleteing row Id "+rowid);
                // $.post("deleterows", { docSecId: docID, fiboTerm: rowdata['fiboterm'],fiboDef:rowdata['definition'] });
                // synchronize with the server - send delete command
            },
                    
            //                        //getting row id, rowdata and number-section number 
            //                        updaterow: function (rowid, newdata, number) {
            //                            // synchronize with the server - send update command
            //                            
            //                        },
            
            pager: function (pagenum, pagesize, oldpagenum) {
                // callback called when a page or page size is changed.
            },
            
            
            updaterow: function (rowid, rowdata, result) {
                 
                //Send update of Importance to the servelet
              //  $.post("updatesentence", { docSecId: currentDocId, sentence: ""+rowdata["sentence"], importance:""+rowdata["importance"] }, function(responce){
                    // alert("response:"+responce);
            //    });
                // alert(rowid+" "+rowdata["sentence"]+" "+rowdata["importance"]+" "+result);
                // synchronize with the server - send update command
                // call result with parameter true if the synchronization with the server is successful 
                // and with parameter false if the synchronization failder.
            //    result(true);
            }
    
    
        };
        //  var dataAdapter = new $.jqx.dataAdapter(source);
           
        var dataList = [
           
    <%
        ArrayList<FiboTerm> fiboTermList = (ArrayList<FiboTerm>) session.getAttribute("fiboTermList");
        Iterator<FiboTerm> iterator = fiboTermList.iterator();
        FiboTerm fiboterm;
        while (iterator.hasNext()) {
            fiboterm = iterator.next();


    %>
                "<%=fiboterm.getFiboTerm()%>",
                               
    <%

        }

    %> 
        
           
            ];
                
            var currentDocId;
            var currentSelectedsent;
                
            //addrows for the jqxgrid table FIBO terms for sentence
            function addfiboterms(number, sentence){
                //To_Do write server method
                $.post("gettermsforsent", { docSecId: number, sentence:""+sentence}, function(responce){
                    //each data row is seprated by "##$$##"
                    var parentStr=responce.split("$$##$$");
                    $("#jqxgrid").jqxGrid('beginupdate');
                    currentDocId=number;
                    for(var i=0;i<parentStr.length;i++){
                        if(parentStr[i]!=null){
                            //sub items in each row is seperated by "$$**$$"
                            var childStr=parentStr[i].split("$$**$$");
                            var row = {};
                            row["fiboterm"] =childStr[0];
                            row["definition"] = childStr[1];
                        
                            //                        row["fiboterm"] =childStr[0];
                            //                        row["sc row["fiboterm"] =childStr[0];ore"]=childStr[1];
                            //                       
            
                            var datarow = row;
                            $("#jqxgrid").jqxGrid('addrow', null, datarow, number);
                        }
                    } 
                    $("#jqxgrid").jqxGrid('endupdate');
                    // alert("response:"+responce);
                });
                  
            }
          
          
            //addrows for the selected sentences table jqxgrid table-jqxgrid_selected_phr
            function addrows_selected_sentences(number){
                $.post("selectedsentences", { docSecId: number}, function(responce){
                    //each data row is seprated by "##$$##"
                    var parentStr=responce.split("$$##$$");
                    $("#jqxgrid_selected_phr").jqxGrid('beginupdate');
                    currentDocId=number;
                    for(var i=0;i<parentStr.length;i++){
                        if(parentStr[i]!=null){
                            //sub items in each row is seperated by "$$**$$"
                            var childStr=parentStr[i].split("$$**$$");
                            var row = {};
                            row["sentence"] =childStr[0];
                            row["markup"] ="";
                        
            
                            var datarow = row;
                            $("#jqxgrid_selected_phr").jqxGrid('addrow', null, datarow, number);
                        }
                    } 
                    $("#jqxgrid_selected_phr").jqxGrid('endupdate');
                    // alert("response:"+responce);
                });
                  
            }

  
            // create jqxTree
            $('#jqxTree').jqxTree({ height: '400px', hasThreeStates: true, checkboxes: false, width: '230px', theme: theme });
            $('#jqxCheckBox').jqxCheckBox({ width: '200px', height: '25px', checked: true, theme: theme });
            $('#jqxCheckBox').bind('change', function (event) {
                var checked = event.args.checked;
                $('#jqxTree').jqxTree({ hasThreeStates: checked });
            });
        
        
            /*
             *initial display of content
             */
            $('#jqxTree').jqxTree('selectItem', $("#jqxTree").find('li:first')[0]);
            var selectedItem = $("#jqxTree").jqxTree('selectedItem');
            var nextItem = $("#jqxTree").jqxTree('getNextItem', selectedItem.element);
            
             // var nextItem = $("#jqxTree").jqxTree('getNextItem', selectedItem.element);
                if (nextItem != null) {
                    $("#jqxTree").jqxTree('selectItem', nextItem.element);
                    $("#jqxTree").jqxTree('ensureVisible', nextItem.element);
                    
                    
             var valueelementdoc = $("<div></div>");
            valueelementdoc.html("Document Name: " + selectedItem.label);
            $("#docnamelog").children().remove();
            $("#docnamelog").append(valueelementdoc);
                
                
            var valueelementdocsec = $("<div></div>");
            valueelementdocsec.html("Doc Section Name: " + nextItem.label);
            $("#docsecnamelog").children().remove();
            $("#docsecnamelog").append(valueelementdocsec);
//                    alert("test");
                }
            // alert(nextItem.value);
       
      
            $('#jqxgrid_selected_phr').jqxGrid('clear');
        
            addrows_selected_sentences(nextItem.value);
        
            //jqxTree node select event
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
            
                //delete existing rows
            
           
                $('#jqxgrid_selected_phr').jqxGrid('clearselection');
                $('#jqxgrid_selected_phr').jqxGrid('clear');        
                // $('#jqxgrid_selected_phr').jqxGrid('refresh');
                //call add raw function
                addrows_selected_sentences(item.value);
                
                //delete rows of jqxgrid
                $('#jqxgrid').jqxGrid('clear'); 
                //clear selected sentence display
                $("#selectionlog").children().remove();
             
            });
        
     
                 
            // initialize selected sentences grid
            $("#jqxgrid_selected_phr").jqxGrid(
            {
                width: 500,
                height: 200,
                source: source,
                theme: theme,
                // editable: true,
                // selectionmode: 'singlecell',
                columns: [
                    /*
                    {
                        text: 'Importance', datafield: 'importance', width: 150, columntype: 'dropdownlist', 
                        createeditor: function (row, column, editor) {
                            // assign a new data source to the combobox.
                        
                            editor.jqxDropDownList({autoOpen: true, checkboxes: true, source: dataList ,theme: theme });
                        },
                        // update the editor's value before saving it.
                        cellvaluechanging: function (row, column, columntype, oldvalue, newvalue) {
                        
                            // alert("ldvalue");
                            // return the old value, if the new value is empty.
                            if (newvalue == "") return oldvalue;
                        }
                    },
                     */
                    { text: 'Selected Sentences', datafield: 'sentence', width: 2000 },
                
               
                ]
            });
            //raw select event
              
            $('#jqxgrid_selected_phr').bind('rowselect', function (event) 
            {
                var args = event.args;
                var row = args.rowindex;
               
                var datarow= $("#jqxgrid_selected_phr").jqxGrid('getrowdata', row);
                // alert(row +" "+datarow["sentence"]);
                currentSelectedsent=datarow["sentence"];
                var valueelement = $("<div></div>");
                valueelement.html("Selected Sentence: " + datarow["sentence"]);
                $("#selectionlog").children().remove();
                $("#selectionlog").append(valueelement);
                
                $('#jqxgrid').jqxGrid('clear');        
     
                //call add raw function
                addfiboterms(currentDocId,currentSelectedsent);

            });
            // initialize jqxGrid
            $("#jqxgrid").jqxGrid(
            {
                width: 500,
                height: 200,
                source: sourceFibo,
                theme: theme,
                columns: [
                    { text: 'FIBO Term', datafield: 'fiboterm', width: 150 },
                    { text: 'Definition', datafield: 'definition', width: 2500 },
                    //                { text: 'Definition', datafield: 'definition', width: 800 },
               
                    //                { text: 'Product', datafield: 'productname', width: 180 },
                    //                { text: 'Quantity', datafield: 'quantity', width: 80, cellsalign: 'right' },
                    //                { text: 'Unit Price', datafield: 'price', width: 90, cellsalign: 'right', cellsformat: 'c2' },
                    //                { text: 'Total', datafield: 'total', width: 100, cellsalign: 'right', cellsformat: 'c2' }
                ]
            });
            
            
         
            $("#excelExport").jqxButton({ theme: theme });
            $("#xmlExport").jqxButton({ theme: theme });
            $("#csvExport").jqxButton({ theme: theme });
          
            $("#addrowbutton").jqxButton({ theme: theme });
            $("#deleterowbutton").jqxButton({ theme: theme });
            $("#updaterowbutton").jqxButton({ theme: theme });
            
            
            // create new row.
            $("#addrowbutton").bind('click', function () {
                var datarow ;
                //sending current docID to indentify in hte server
                    
                var selectedrowindex = $("#jqxgrid_selected_phr").jqxGrid('getselectedrowindex');
                if(selectedrowindex >= 0){
                    var item = $('#jqxComboBox').jqxComboBox('getItem', args.index);
                    if (item != null) {
                        
                var valueArry=(item.value).split("&&&&")
                        datarow  = generatenewrowFibo(item.label,valueArry[0],valueArry[1]);
                   
                        //  alert(currentSelectedsent);
                        
                    }
                    //TO-Do write the server function add
                    $.post("addfibotermsent", { docSecId: currentDocId,curSentence:""+currentSelectedsent, fiboTerm: ""+datarow['fiboterm'] ,fiboTermDef: ""+datarow['definition'],fiboID: ""+datarow['fiboID']}, function(responce){
                    
                        $("#jqxgrid").jqxGrid('addrow', null, datarow, currentDocId);
                        // alert("response:"+responce);
                    });
                
                }
            });
        
        
            $("#deleterowbutton").bind('click', function () {
                var selectedrowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
                var rowscount = $("#jqxgrid").jqxGrid('getdatainformation').rowscount;
                if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
                    var id = $("#jqxgrid").jqxGrid('getrowid', selectedrowindex);
                    var datarow= $("#jqxgrid").jqxGrid('getrowdata', selectedrowindex);
                    // $("#jqxgrid").jqxGrid('deleterow', id, datarow, currentDocId );
                    var fiboterm=datarow['fiboterm'];
                    //  alert("fiboterm to delete"+ fiboterm+" "+currentDocId+" "+currentSelectedsent );
                    //To-DO write server function to delete  
                   
                    $.post("delfibotermsent", { docSecId: currentDocId, curSentence:""+currentSelectedsent  ,fiboTerm: ""+fiboterm }, function(responce){
                        //  alert("response:"+responce);
                        $("#jqxgrid").jqxGrid('deleterow', id);
             
                    });
   
                        
                }
            });
            
            $("#excelExport").click(function () {
                // alert("test");
                $("#jqxgrid_selected_phr").jqxGrid('exportdata', 'xls','termsrecommonded');  
              
            });
            
            $("#xmlExport").click(function () {
                //alert("test");
                $("#jqxgrid_selected_phr").jqxGrid('exportdata', 'xml','termsrecommonded');  
              
            });
          
            $("#csvExport").click(function () {
                $("#jqxgrid_selected_phr").jqxGrid('exportdata', 'csv','termsrecommonded');
            });
          
          
                         
            /////////combobox
            var comboSource = [
              
    <%
        ArrayList<FiboTerm> fiboTermList2 = (ArrayList<FiboTerm>) session.getAttribute("fiboTermList");
        Iterator<FiboTerm> iterator2 = fiboTermList2.iterator();
        FiboTerm fiboterm2;
        while (iterator2.hasNext()) {
            fiboterm2 = iterator2.next();


    %>
                {html: "<div tabIndex=0 style='padding: 1px;'><div>Term:<%=fiboterm2.getFiboTerm()%></div><div>Def:<%=fiboterm2.getFiboDefinition()%>  </div></div>", label: "<%=fiboterm2.getFiboTerm()%>",value: "<%=fiboterm2.getFiboDefinition()%>&&&&<%=fiboterm2.getFiboId()%>", group: "FIBO Debt Terms" },
                               
    <%

        }

    %> 
                    
 
            ];
            // Create a jqxComboBox
            $("#jqxComboBox").jqxComboBox({ source: comboSource, width: '300', height: '25', selectedIndex: 0, theme: theme });
         
        });
    
    
    
</script>

<!--<body class='default'>-->
<div id='jqxWidget'>
    <h2>Markup Sentences</h2>
    <!--   <div style="float: left;" id="jqxgrid">
        </div>-->
    <div style='float: left;'>

        <%if (request.getMethod().equals("POST")) {%>
        <form name="DocSections" action="showtermsgrid">


            <div style='margin-left: 10px; float: left; position:absolute; left: 320px; top: 650px;'>
                <br/>
                <input style="width: 90px;" type="submit" value="Next" />
            </div>


        </form>

        <form name="DocSections2" method="post" action="selectSections">
            <div style='margin-left: 60px; float: left; position:absolute; left: 160px; top: 664px;'> 
                <input style="width: 90px;" type="submit" value="Back" name="backButton" />
            </div>
        </form>
        <%      } else {%>
        <form name="DocSections" action="savemarkedupsentences">


            <div style='margin-left: 10px; float: left; position:absolute; left: 250px; top: 650px;'>
                <br/>
                <input style="width: 150px;" type="submit" value="Review Onother Doc" />
            </div>


        </form>

        <% }%>

        <strong> Documents-DocSections</strong>

        <br/>
        <br/>
        <div id='jqxTree' style='float: left; margin-left: 20px;'>
            <!--            <h4>Selected Documents/DocSec:</h4>-->
            <ul>
                <%

                    HashMap<String, ArrayList<DocSection>> map = (HashMap<String, ArrayList<DocSection>>) session.getAttribute("SelectedDocSectionMap");
                    for (Map.Entry entry : map.entrySet()) {
                        ArrayList<DocSection> docsectionList = (ArrayList<DocSection>) entry.getValue();



                %>
                <li item-checked='true' item-expanded='true' item-value='<%=entry.getKey()%>'><%=entry.getKey()%>

                    <ul>
                        <% for (int i = 0; i < docsectionList.size(); i++) {
                        %>
                        <li item-value='<%=docsectionList.get(i).getSectionId()%>'>  <%=docsectionList.get(i).getSectionName()%></li>

                        <%}%>
                    </ul>
                </li>
                <%}%>
            </ul>



        </div>

        <input type="hidden" name="selected_check_boxes" id="selected_check_boxes"/>



        <div style='margin-left: 60px; float: left; '> 

            <div style='margin-top: 10px;position:absolute; left: 520px; top: 185px;'>
 <div style='max-width: 500px; margin-top: 10px; color: black' id="docnamelog"></div>
                    <div style='max-width: 500px; margin-top: 10px; color: black' id="docsecnamelog"></div>
                    <br/>
                    <p style="color:red">* Select a sentence from Top table<br/>
                      * Use bottom "dropdownlist" to recommend  FIBO terms for the sentence </p>
                <h3> Markup the Sentences with FIBO Terms </h3>
                <div style="float: bottom;" id="jqxgrid_selected_phr">

                </div>
                <!--                <div style="float: left; margin-left: 20px; font-size: 13px; font-family: Verdana;">
                                                    <div id="selectionlog"></div>
                                    <div style='max-width: 500px; margin-top: 20px; color: blue' id="selectionlog"></div>
                                </div>-->

            </div>


            <div style='margin-left: 10px; float: left; position:absolute; left: 510px; top: 540px;'>

                <div style="float: left; margin-left: 0px; font-size: 13px; font-family: Verdana;">
                    <!--                <div id="selectionlog"></div>-->
                    <div style='max-width: 500px; margin-top: 20px; color: blue' id="selectionlog"></div>
                </div>

                <br/>
                <br/>
                <br/>
                <h3> FIBO Terms for the Selected Sentence </h3>
                <div style="float: bottom;" id="jqxgrid">

                </div>
                <div style="margin-top: 10px; size: 400px;">


                    <input id="deleterowbutton" type="button" value="Delete Selected Row" style="width: 150px;"/>

                </div>
                <div style="margin-top: 10px; size: 400px;">

                    <div style='float: left;' id='jqxComboBox'></div>
                    <input id="addrowbutton" type="button" value="Add New Row" />


                </div>

            </div>
        </div>


    </div>




</div>
</div>
        
            <div class="clearingdiv">&nbsp;</div>            

            <div id="footer">
                    <p>&copy; 2012 <a href="www.opensource.lk">Lanka Software Foundation</a></p>
            </div>

        </div>
    </body>
</html>
