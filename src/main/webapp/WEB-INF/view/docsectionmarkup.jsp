<%-- 
    Document   : docsectionmarkup
    Created on : Apr 9, 2013, 10:49:09 AM
    Author     : lsf
--%>

<%-- 
    Document   : recommondterm
    Created on : Sep 18, 2012, 11:29:16 AM
    Author     : lsf
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
                    String userLogin = (String) session.getAttribute( "username" );
                    if ( userLogin != null )
                    {
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
                <script type="text/javascript" src="scripts/jquery-1.8.1.min.js"></script>
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

                <script type="text/javascript" src="jqwidgets/jqxdata.export.js"></script> 
                <script type="text/javascript" src="jqwidgets/jqxgrid.export.js"></script> 

                <script type="text/javascript" src="jqwidgets/jqxtabs.js"></script>
                <script type="text/javascript" src="scripts/generatedata.js"></script>
                <script type="text/javascript">
                    $(document).ready(function () {
                        // Create jqxTree 
                        var theme = getTheme();
        
                        var data = {};

        
                        var generatenewrow=function(fiboTerm,score,fiboDef){
                            var row = {};
                            row["fiboterm"] = fiboTerm;
                            row["score"]=score;
                            row["definition"] = fiboDef;

                            return row;
                        }
           
 
                        //   var fiboTerm =new Array();
                        //   var fiboDefinition= new Array();
        
         
       
                        var source =
                            {
                            localdata: data,
                            datatype: "local",
                            datafields: [
                                { name: 'fiboterm', type: 'string' },
                                { name: 'score', type: 'float' },
                                { name: 'definition', type: 'string' }
                            ] ,
                            //getting row id, rowdata and number-section number 
                            addrow: function (rowid, rowdata, number) {
                                //  alert(rowid + number + rowdata["fiboterm"]);
                         
                                // alert(rowid + number);
                   
                                // synchronize with the server - send insert command
                            },
                    
                            //getting row id,and number-section number 
                            deleterow: function (rowid) {
                                //  alert("deleteing row Id "+rowid);
                                // $.post("deleterows", { docSecId: docID, fiboTerm: rowdata['fiboterm'],fiboDef:rowdata['definition'] });
                                // synchronize with the server - send delete command
                            },
                    
                            //getting row id, rowdata and number-section number 
                            updaterow: function (rowid, newdata, number) {
                                // synchronize with the server - send update command
                            }
                        };
                
                
                
                   var data_1 = new Array();
              var i=0;
                      <%
                        ArrayList<FiboTerm> fiboTermList_1 = (ArrayList<FiboTerm>) session.getAttribute( "fiboTermList" );
                        Iterator<FiboTerm> iterator_1 = fiboTermList_1.iterator();
                        FiboTerm fiboterm_1;
                        while ( iterator_1.hasNext() )
                        {
                            fiboterm_1 = iterator_1.next();


                    %>
                          
                         var row_1 = {};
                        
                row_1["fiboterm"] =  "<%=fiboterm_1.getFiboTerm()%>";
                  row_1["definition"] = "<%=fiboterm_1.getFiboDefinition()%>";
              
                data_1[i] = row_1;  
                i++;
                    <%

                        }

                    %> 
                    
          
                
                        var source_1 =
                            {
                            localdata: data_1,
                            datatype: "array",
                           datafields: [
                                { name: 'fiboterm', type: 'string' },
                                { name: 'definition', type: 'string' }
                            ] 
                   
                        };
    
                        var dataAdapter_1 = new $.jqx.dataAdapter(source_1);
                
                        var currentDocId;
                
                        //addrows
                        function addrows(number){
                            $.post("getterms_doc", { docSecId: number}, function(responce){
                                //each data row is seprated by "##$$##"
                                var parentStr=responce.split("##$$##");
                                $("#jqxgrid").jqxGrid('beginupdate');
                                currentDocId=number;
                                for(var i=0;i<parentStr.length;i++){
                                    if(parentStr[i]!=null){
                                        //sub items in each row is seperated by "$$**$$"
                                        var childStr=parentStr[i].split("$$**$$");
                                        var row = {};
                                        row["fiboterm"] =childStr[0];
                                        row["score"]=childStr[1];
                                        row["definition"] = childStr[2];
                        
            
                                        var datarow = row;
                                        $("#jqxgrid").jqxGrid('addrow', null, datarow, number);
                                    }
                                } 
                                $("#jqxgrid").jqxGrid('endupdate');
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
            
                            var valueelementdoc = $("<div></div>");
                            valueelementdoc.html("Document Name: " + selectedItem.label);
                            $("#docnamelog").children().remove();
                            $("#docnamelog").append(valueelementdoc);
                
                
                            var valueelementdocsec = $("<div></div>");
                            valueelementdocsec.html("Doc Section Name: " + nextItem.label);
                            $("#docsecnamelog").children().remove();
                            $("#docsecnamelog").append(valueelementdocsec);
                            // alert("Data Loaded: " + data);
                        });
        
                        $('#jqxgrid').jqxGrid('clear');
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
                            /*
                             * call to the servelat using get method
                             */
                            $.get("getDocSection", { docID: item.value},function(data) {
                    
                                document.getElementById('td1').value = data;
                                // alert("Data Loaded: " + data);
                            });
            
                            //delete existing rows
            
                            $('#jqxgrid').jqxGrid('clear');
                     
     
                            addrows(item.value);

             
                        });
        
        
        
        
            
            
                        // initialize jqxGrid
                        $("#jqxgrid").jqxGrid(
                        {
                            width: 500,
                            height: 200,
                            source: source,
                            theme: theme,
                            columns: [
                                { text: 'Fibo Term', datafield: 'fiboterm', width: 150 },
                                { text: 'Definition', datafield: 'definition', width: 2500 },
               
                                //                { text: 'Product', datafield: 'productname', width: 180 },
                                //                { text: 'Quantity', datafield: 'quantity', width: 80, cellsalign: 'right' },
                                //                { text: 'Unit Price', datafield: 'price', width: 90, cellsalign: 'right', cellsformat: 'c2' },
                                //                { text: 'Total', datafield: 'total', width: 100, cellsalign: 'right', cellsformat: 'c2' }
                            ]
                        });
                
                       
                        // initialize jqxgrid_fiboterm
                        $("#jqxgrid_fiboterm").jqxGrid(
                        {
                            width: 500,
                            height: 200,
                          
                             source:dataAdapter_1,
                            theme: theme,
                            columns: [
                                { text: 'Fibo Term', datafield: 'fiboterm', width: 150 },
                                { text: 'Definition', datafield: 'definition', width: 2500 },
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
                            $("#jqxgrid").jqxGrid('exportdata', 'xls','termsrecommonded');  
              
                        });
            
                        $("#xmlExport").click(function () {
                            //alert("test");
                            $("#jqxgrid").jqxGrid('exportdata', 'xml','termsrecommonded');  
              
                        });
          
                        $("#csvExport").click(function () {
                            $("#jqxgrid").jqxGrid('exportdata', 'csv','termsrecommonded');
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
                        $("#addrowbutton_2").bind('click', function () {
                            var datarow ;
                            //sending current docID to indentify in hte server
                    
                    
                            var item = $('#jqxComboBox').jqxComboBox('getItem', args.index);
                            if (item != null) {
                                datarow  = generatenewrow(item.label,10.274,item.value);
                                $("#jqxgrid").jqxGrid('addrow', null, datarow, currentDocId);
                                //alert("Selected:" + item.label+"value:" + item.value);
                        
                            }
                            $.post("addrows_doc", { docSecId: currentDocId, fiboTerm: ""+datarow['fiboterm'] ,score: ""+datarow['score'],fiboTermDef: ""+datarow['definition']}, function(responce){
                                // alert("response:"+responce);
                            });
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
                            var selectedrowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
                            var rowscount = $("#jqxgrid").jqxGrid('getdatainformation').rowscount;
                            if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
                                var id = $("#jqxgrid").jqxGrid('getrowid', selectedrowindex);
                                var datarow= $("#jqxgrid").jqxGrid('getrowdata', selectedrowindex);
                                // $("#jqxgrid").jqxGrid('deleterow', id, datarow, currentDocId );
                                var fiboterm=datarow['fiboterm'];
                                //   alert("fiboterm to delete"+ fiboterm);
                        
                                $.post("deleterows_doc", { docSecId: currentDocId, fiboTerm: ""+fiboterm }, function(responce){
                                    //  alert("response:"+responce);
                        
                                    $("#jqxgrid").jqxGrid('deleterow', id);
                                });
   
                        
                            }
                        });
         
                        //add new FIBO term
                        $("#addrowbutton").bind('click', function () {
                            var datarow ;
                            //sending current docID to indentify in hte server
                    
                            var selectedrowindex = $("#jqxgrid_fiboterm").jqxGrid('getselectedrowindex');
                            var rowscount = $("#jqxgrid_fiboterm").jqxGrid('getdatainformation').rowscount;
                            //alert("selectedrowindex "+selectedrowindex +"rowscount "+rowscount );  
                            if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
                                var id = $("#jqxgrid_fiboterm").jqxGrid('getrowid', selectedrowindex);
                                var datarow= $("#jqxgrid_fiboterm").jqxGrid('getrowdata', selectedrowindex);
                                // $("#jqxgrid").jqxGrid('deleterow', id, datarow, currentDocId );
                                var fibodef=datarow['definition'];
                                var fiboterm=datarow['fiboterm'];
                                //  alert("fiboterm to delete");                        
                            }

                             
                            if (fibodef) {
                               // $("#jqxgrid_fiboterm").jqxGrid('deleterow', id);
                                datarow  = generatenewrow(fiboterm,10.274,fibodef);
                                $("#jqxgrid").jqxGrid('addrow', null, datarow, currentDocId);
                                //alert("Selected:" + item.label+"value:" + item.value);
                        
                            }
                            $.post("addrows_doc", { docSecId: currentDocId, fiboTerm: ""+datarow['fiboterm'] ,score: ""+datarow['score'],fiboTermDef: ""+datarow['definition']}, function(responce){
                                // alert("response:"+responce);
                            });
                        });
                
                        /////////combobox
                        var comboSource = [
              
                    <%
                        ArrayList<FiboTerm> fiboTermList = (ArrayList<FiboTerm>) session.getAttribute( "fiboTermList" );
                        Iterator<FiboTerm> iterator = fiboTermList.iterator();
                        FiboTerm fiboterm;
                        while ( iterator.hasNext() )
                        {
                            fiboterm = iterator.next();


                    %>
                                {html: "<div tabIndex=0 style='padding: 1px;'><div>Term:<%=fiboterm.getFiboTerm()%></div><div>Def:<%=fiboterm.getFiboDefinition()%>  </div></div>", label: "<%=fiboterm.getFiboTerm()%>",value: "<%=fiboterm.getFiboDefinition()%>", group: "FIBO Debt Terms" },
                               
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
                    <h2>Recommended FIBO Terms</h2>
                    <!--   <div style="float: left;" id="jqxgrid">
                        </div>-->
                    <div style='float: left;'>

                        <%if ( request.getMethod().equals( "POST" ) )
                            {%>
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
                        <%      } else
                        {%>
                        <form name="DocSections" action="savedocmarkupdata">


                            <div style='margin-left: 10px; float: left; position:absolute; left: 250px; top: 650px;'>
                                <br/>
                                <input style="width: 150px;" type="submit" value="MarkUp Onother Doc" />
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

                                    HashMap<String, ArrayList<DocSection>> map = (HashMap<String, ArrayList<DocSection>>) session.getAttribute( "SelectedDocSectionMap" );
                                    for ( Map.Entry entry : map.entrySet() )
                                    {
                                        ArrayList<DocSection> docsectionList = (ArrayList<DocSection>) entry.getValue();



                                %>
                                <li item-checked='true' item-expanded='true' item-value='<%=entry.getKey()%>'><%=entry.getKey()%>

                                    <ul>
                                        <% for ( int i = 0; i < docsectionList.size(); i++ )
                                            {
                                        %>
                                        <li item-value='<%=docsectionList.get( i ).getSectionId()%>'>  <%=docsectionList.get( i ).getSectionName()%></li>

                                        <%}%>
                                    </ul>
                                </li>
                                <%}%>
                            </ul>



                        </div>

                        <input type="hidden" name="selected_check_boxes" id="selected_check_boxes"/>



                        <div style='margin-left: 60px; float: left; '> 

                            <div style='margin-top: 10px;position:absolute; left: 520px; top: 185px;'>
                                <h3> Document Section Content </h3>
                                <div style='max-width: 500px; margin-top: 10px; color: black' id="docnamelog"></div>
                                <div style='max-width: 500px; margin-top: 10px; color: black' id="docsecnamelog"></div>
                                <br/>

                                <textarea id="td1" name="docSectionContenet" wrap='off' style="font: 100%/120% Verdana, Arial, Helvetica, sans-serif; width: 500px; height: 190px;" >
-->Click the tree elements to view content of each
                                </textarea>
                            </div>
                        </div>


                    </div>


                    <div style='margin-left: 10px; float: left; position:absolute; left: 510px; top: 505px;'>

                        <h3> Recommended FIBO Terms </h3>
                        <div style='margin-top: 10px;'>
                            <div style="float: bottom;" id="jqxgrid">

                            </div>

                            <div style="margin-top: 10px; size: 400px;">
                                <input id="deleterowbutton" type="button" value="Delete Selected Row" style="width: 150px;"/>
                                <!--                 <input type="button" value="Export to Excel" id='excelExport' />-->

                                <input type="button" value="Export to CSV" id='csvExport' />

                                <input type="button" value="Export to XML" id='xmlExport' />
                            </div>

                            <!--
                                                        <div style="margin-top: 10px; size: 250px;">
                                                            <div style='float: left;' id='jqxComboBox'></div>
                                                            <input id="addrowbutton" type="button" value="Add New Row" />
                                                           
                                                        </div>-->


                        </div>
                    </div>
                    <div style='margin-left: 10px; float: left; position:absolute; left: 510px; top: 810px;'>

                        <h3> FIBO Terms </h3>
                        <div style="float: bottom;" id="jqxgrid_fiboterm">

                        </div>

                        <div style="margin-top: 10px; size: 400px;">
                            <input id="addrowbutton" type="button" value="Add FIBO Term" style="width: 150px;"/>


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

