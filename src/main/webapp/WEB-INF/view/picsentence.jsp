
<%-- 
    Document   : picsentence
    Created on : Jan 8, 2013, 10:07:25 AM
    Author     : lsf
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
            row["importance"]="Please Select";
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
                { name: 'importance', type: 'string' }
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
                
        var currentDocId;
                
        //addrows for the jqxgrid table
        function addrows(number){
            $.post("allsentences", { docSecId: number}, function(responce){
                //each data row is seprated by "##$$##"
                var parentStr=responce.split("$$##$$");
                $("#jqxgrid").jqxGrid('beginupdate');
                currentDocId=number;
                for(var i=0;i<parentStr.length;i++){
                    if(parentStr[i]!=null){
                        //sub items in each row is seperated by "$$**$$"
                        //                        var childStr=parentStr[i].split("$$**$$");
                        var row = {};
                        row["sentence"] =parentStr[i];
                        //                        row["fiboterm"] =childStr[0];
                        //                        row["sc row["fiboterm"] =childStr[0];ore"]=childStr[1];
                        //                        row["definition"] = childStr[2];
                        
            
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
                        row["importance"] =childStr[1];
                        //                        row["sc row["fiboterm"] =childStr[0];ore"]=childStr[1];
                        //                        row["definition"] = childStr[2];
                        
            
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
        // alert(nextItem.value);
        if (nextItem != null) {
            $("#jqxTree").jqxTree('selectItem', nextItem.element);
            $("#jqxTree").jqxTree('ensureVisible', nextItem.element);
            //                    alert("test");

            var valueelementdoc = $("<div></div>");
            valueelementdoc.html("Document Name: " + selectedItem.label);
            $("#docnamelog").children().remove();
            $("#docnamelog").append(valueelementdoc);
                
                
            var valueelementdocsec = $("<div></div>");
            valueelementdocsec.html("Doc Section Name: " + nextItem.label);
            $("#docsecnamelog").children().remove();
            $("#docsecnamelog").append(valueelementdocsec);
        }
      
      
        
        $('#jqxgrid').jqxGrid('clear');
        $('#jqxgrid_selected_phr').jqxGrid('clear');
        addrows(nextItem.value);
        
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
            
            $('#jqxgrid').jqxGrid('clear');
            $('#jqxgrid_selected_phr').jqxGrid('clear');        
     
            addrows(item.value);
            addrows_selected_sentences(item.value);

             
        });
        
        
        
        
            
            
        // initialize jqxGrid
        $("#jqxgrid").jqxGrid(
        {
            width: 500,
            height: 200,
            source: source,
            theme: theme,
            columns: [
                { text: 'Sentences', datafield: 'sentence', width: 3000 },
                //                { text: 'Okapi Score', datafield: 'score', width: 100 },
                //                { text: 'Definition', datafield: 'definition', width: 800 },
               
                //                { text: 'Product', datafield: 'productname', width: 180 },
                //                { text: 'Quantity', datafield: 'quantity', width: 80, cellsalign: 'right' },
                //                { text: 'Unit Price', datafield: 'price', width: 90, cellsalign: 'right', cellsformat: 'c2' },
                //                { text: 'Total', datafield: 'total', width: 100, cellsalign: 'right', cellsformat: 'c2' }
            ]
        });
            
            
                 
        // initialize selected sentences grid
        $("#jqxgrid_selected_phr").jqxGrid(
        {
            width: 500,
            height: 200,
            source: source,
            theme: theme,
            editable: true,
            // selectionmode: 'singlecell',
            columns: [
                {
                    text: 'Importance', datafield: 'importance', width: 150, columntype: 'dropdownlist',
                    createeditor: function (row, column, editor) {
                        // assign a new data source to the combobox.
                        var list = ['V. Importanr', 'Important', 'Average'];
                        editor.jqxDropDownList({autoOpen: true, source: list });
                    },
                    // update the editor's value before saving it.
                    cellvaluechanging: function (row, column, columntype, oldvalue, newvalue) {
                        
                        // alert("ldvalue");
                        // return the old value, if the new value is empty.
                        if (newvalue == "") return oldvalue;
                    }
                },

                { text: 'Selected Sentences', datafield: 'sentence', width: 3000 },
                
               
            ]
        });
                
        $("#addrowbutton").jqxButton({ theme: theme });
        //        $("#addmultiplerowsbutton").jqxButton({ theme: theme });
        $("#deleterowbutton").jqxButton({ theme: theme });
        $("#updaterowbutton").jqxButton({ theme: theme });
         
        $("#excelExport").jqxButton({ theme: theme });
        $("#xmlExport").jqxButton({ theme: theme });
        $("#csvExport").jqxButton({ theme: theme });
          
            
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
           
         
        // update row.
        $("#updaterowbutton").bind('click', function () {
            var datarow = generaterow();
            var selectedrowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
            var rowscount = $("#jqxgrid").jqxGrid('getdatainformation').rowscount;
            if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
                var id = $("#jqxgrid").jqxGrid('getrowid', selectedrowindex);
                $("#jqxgrid").jqxGrid('updaterow', id, datarow);
                $("#jqxgrid").jqxGrid('ensurerowvisible', selectedrowindex);
            }
        });
        // create new row.
        $("#addrowbutton").bind('click', function () {
            var datarow_selected ;
            //sending current docID to indentify in hte server
                    
            var selectedrowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
            var rowscount = $("#jqxgrid").jqxGrid('getdatainformation').rowscount;
            if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
                var id = $("#jqxgrid").jqxGrid('getrowid', selectedrowindex);
                var datarow= $("#jqxgrid").jqxGrid('getrowdata', selectedrowindex);
                // $("#jqxgrid").jqxGrid('deleterow', id, datarow, currentDocId );
                var sentenceToAdd=datarow['sentence'];
                //   alert("fiboterm to delete"+ fiboterm);
                        
                // $.post("deleterows", { docSecId: currentDocId, fiboTerm: ""+fiboterm }, function(responce){
                //  alert("response:"+responce);
                        
                   
                // });
   
                        
            }
            if ( sentenceToAdd) {
                
                $("#jqxgrid").jqxGrid('deleterow', id);
                datarow_selected  = generatenewrow(sentenceToAdd);
                $("#jqxgrid_selected_phr").jqxGrid('addrow', null, datarow_selected, currentDocId);
                //alert("Selected:" + item.label+"value:" + item.value);
                        
            
                //TO-DO add to the data structure
                $.post("addsentence", { docSecId: currentDocId, sentence: ""+sentenceToAdd }, function(responce){
                    // alert("response:"+responce);
                });
            
            }
        });
        // create new rows.
        $("#addmultiplerowsbutton").bind('click', function () {
            $("#jqxgrid").jqxGrid('beginupdate');
            for (var i = 0; i < 5; i++) {
                var datarow = generaterow(i);
                $("#jqxgrid").jqxGrid('addrow', null, datarow);
            }
            $("#jqxgrid").jqxGrid('endupdate');
        });
        // delete row.
        $("#deleterowbutton").bind('click', function () {
            var datarow_selected ;
            //sending current docID to indentify in hte server
                   
            var selectedrowindex = $("#jqxgrid_selected_phr").jqxGrid('getselectedrowindex');
            var rowscount = $("#jqxgrid_selected_phr").jqxGrid('getdatainformation').rowscount;
            //alert("selectedrowindex "+selectedrowindex +"rowscount "+rowscount );  
            if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
                var id = $("#jqxgrid_selected_phr").jqxGrid('getrowid', selectedrowindex);
                var datarow= $("#jqxgrid_selected_phr").jqxGrid('getrowdata', selectedrowindex);
                // $("#jqxgrid").jqxGrid('deleterow', id, datarow, currentDocId );
                var sentenceToRemove=datarow['sentence'];
                //  alert("fiboterm to delete");
                       
               
   
                        
            }
            if ( sentenceToRemove) {
                // alert("fiboterm to delete222222222222");
                $("#jqxgrid_selected_phr").jqxGrid('deleterow', id);
                
                datarow_selected  = generatenewrow(sentenceToRemove);
                $("#jqxgrid").jqxGrid('addrow', null, datarow_selected, currentDocId);
                //alert("Selected:" + item.label+"value:" + item.value);
                        
            
            
                $.post("removesentence", { docSecId: currentDocId, sentence: ""+sentenceToRemove}, function(responce){
                    //  alert("response:"+responce);
                        
                    //                    $("#jqxgrid_selected_phr").jqxGrid('deleterow', id);
                });
                
            }
            
            //TO-DO add to the data structure
            //            $.post("addrows", { docSecId: currentDocId, fiboTerm: ""+datarow['fiboterm'] ,score: ""+datarow['score'],fiboTermDef: ""+datarow['definition']}, function(responce){
            //                // alert("response:"+responce);
            //            });
        });
         
        

         
    });
    
    

</script>

<!--<body class='default'>-->
<div id='jqxWidget'>
    <h2>Select Sentences</h2>
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
        
        
         <form name="DocSections" action="recomondtermsforselections">
<!-- <form name="DocSections" action="recomondtermsforselect">-->


            <div style='margin-left: 10px; float: left; position:absolute; left: 250px; top: 650px;'>
                <br/>
                <input style="width: 150px;" type="submit" value="Recom: FIBO Terms" />
            </div>

        </form>
        <form name="DocSections" action="saveselectedsentences">


            <div style='margin-left: 10px; float: left; position:absolute; left: 250px; top: 680px;'>
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

                <h3> Select Sentences from the Table </h3>
                <p style="color:red">* Click on the desired sentence of Top table and click on <br/>
                      "Select highlighted Sentence" button<br/>
                * Then use bottom table to mark the importance of the selected sentence
                </p>
          
                
                <div style='max-width: 500px; margin-top: 10px; color: black' id="docnamelog"></div>
                    <div style='max-width: 500px; margin-top: 10px; color: black' id="docsecnamelog"></div>
                    <br/>
                
                <div style="float: bottom;" id="jqxgrid">

                </div>

                <div style="margin-top: 10px; size: 400px;">
                    <input id="addrowbutton" type="button" value="Select highlighted Sentence" style="width: 200px;"/>

                </div>
            </div>
        </div>


    </div>


    <div style='margin-left: 10px; float: left; position:absolute; left: 510px; top: 610px;'>

        <h3> Selected Phrases </h3>
        <div style="float: bottom;" id="jqxgrid_selected_phr">

        </div>

        <div style="margin-top: 10px; size: 400px;">
            <input id="deleterowbutton" type="button" value="Remove Selected Row" style="width: 150px;"/>

            <!--                 <input type="button" value="Export to Excel" id='excelExport' />-->

            <input type="button" value="Export to CSV" id='csvExport' />

            <input type="button" value="Export to XML" id='xmlExport' />
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
