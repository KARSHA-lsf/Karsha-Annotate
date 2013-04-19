<%-- 
    Document   : termsgrid
    Created on : Sep 19, 2012, 10:26:53 AM
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





                <%@page import="org.karsha.entities.FiboTerm"%>
                <%@page import="java.util.ArrayList"%>
                <%@page import="java.util.Map"%>
                <%@page import="java.lang.String"%>
                <%@page import="java.util.HashMap"%>
                <%@page contentType="text/html" pageEncoding="UTF-8"%>
                <!DOCTYPE html>

                <link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
                <script type="text/javascript" src="scripts/jquery-1.8.1.min.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxdata.js"></script> 
                <script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxmenu.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxgrid.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxgrid.grouping.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxgrid.selection.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxgrid.columnsresize.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxgrid.sort.js"></script> 
                <script type="text/javascript" src="jqwidgets/jqxgrid.pager.js"></script> 
                <script type="text/javascript" src="jqwidgets/jqxgrid.selection.js"></script> 
                <script type="text/javascript" src="jqwidgets/jqxgrid.edit.js"></script> 

                <script type="text/javascript" src="jqwidgets/jqxdata.export.js"></script> 
                <script type="text/javascript" src="jqwidgets/jqxgrid.export.js"></script> 

                <script type="text/javascript" src="jqwidgets/jqxcombobox.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxlistbox.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxdropdownlist.js"></script>
                <script type="text/javascript" src="jqwidgets/jqxtabs.js"></script>
                <script type="text/javascript" src="scripts/gettheme.js"></script>
                <script type="text/javascript" src="scripts/generatedata.js"></script>



                <script type="text/javascript">
                    $(document).ready(function () {
                        var theme = getTheme();
                        // prepare the data
                        var data = new Array();
                        var documentName= new Array();
                        //var documentName=["HSBC inc.","City Bank"];
                        var sectionName =new Array();
                        var termColor1=new Array();
                        var termColor2={};
                        //var sectionName =["Risk Factors","Discription of Notes","Plan of Distribution","ERISA Considerations", "Tax Considerations"];
                        var sectionCatogoryID= ["0","1","2","3","4"];
                        var celbgcolor=["#FFFFFF","#CCFFFF","#00FF00","#FFFF66","#FF9999","#CC99FF"];
                        var docSec=new Array();
 
                        var fiboTermsForDoc=new Array();
           
                        var maxRowCount;
           
                    <% HashMap<String, HashMap<String, Integer>> termsForDocMap = (HashMap<String, HashMap<String, Integer>>) session.getAttribute("termsForDocMap");
                        int x = 0;
                        int maxRowCount = 0;
                        for (Map.Entry pEntry : termsForDocMap.entrySet()) {
                            HashMap<String, Integer> fiboTerms = (HashMap<String, Integer>) pEntry.getValue();
                            if (maxRowCount < fiboTerms.size()) {
                                maxRowCount = fiboTerms.size();
                            }
                    %>
          
                            fiboTermsForDoc[<%=x%>]= new Array();
                    <%
                        int y = 0;
                        for (Map.Entry cEntry : fiboTerms.entrySet()) {
                    %>
                            fiboTermsForDoc[<%=x%>][<%=y%>]=["<%=cEntry.getKey()%>"];
                    <%
                                y++;
                            }

                            x++;
                        }
                    %>maxRowCount=<%=maxRowCount%>;<%
                    %>
        
        
                    <% HashMap<String, Integer> termColor = (HashMap<String, Integer>) session.getAttribute("termColor");
                        int termCount = 0;
                        for (Map.Entry entry : termColor.entrySet()) {

                    %>
                            termColor1[<%=termCount%>]=["<%=entry.getKey().toString()%>"];
                            termColor2["<%=entry.getKey().toString()%>"]=["<%=(Integer) entry.getValue()%>"];
                    <%
                            termCount++;
                        }
                    %>
 
                    <% HashMap<String, HashMap<String, ArrayList<FiboTerm>>> organizedDocSection = (HashMap<String, HashMap<String, ArrayList<FiboTerm>>>) session.getAttribute("organizedDocSection");

                        ArrayList<String> documentOrder = (ArrayList<String>) session.getAttribute("documentOrder");
                        int p = 0;

                        for (Map.Entry grandParentEntry : organizedDocSection.entrySet()) {


                    %>
                            sectionName[<%=p%>]=["<%=grandParentEntry.getKey().toString()%>"];
                            docSec[<%=p%>]=new Array();
                    <%
                        // int q = 0;
                        //  grades = new ArrayList<ArrayList<String>>();

                        HashMap<String, ArrayList<FiboTerm>> fiboTerms_DocSec = (HashMap<String, ArrayList<FiboTerm>>) grandParentEntry.getValue();
                        // for (Map.Entry parentEntry : fiboTerms_DocSec.entrySet()) {
                        for (int q = 0; q < documentOrder.size(); q++) {
                            if (fiboTerms_DocSec.containsKey(documentOrder.get(q).toString())) {


                    %>
            
                            documentName[<%=q%>]=["<%=documentOrder.get(q).toString()%>"];  
                            docSec[<%=p%>][<%=q%>]= new Array();
                    <%
                        //int r = 0;
                        // row = new ArrayList<String>();

                        ArrayList<FiboTerm> termList = (ArrayList<FiboTerm>) fiboTerms_DocSec.get(documentOrder.get(q).toString());
                        for (int r = 0; r < termList.size(); r++) {

            //for (Map.Entry childEntry : termDefinition.entrySet()                ) {
                %>
                        docSec[<%=p%>][<%=q%>][<%=r%>]=["<%=termList.get(r).getFiboTerm()%>"];
                    <%
                                        if (r > 15) {
                                            break;
                                        }

                                        //row.add(childEntry.getKey().toString());
                                    }
                                    // q++;
                                    //grades.add(row);
                                }
                            }
                            p++;
                            //  topTerms.add(grades);
                        }


                    %>
            
            
            
            
                            //creating a hidden element to store the no of docs
            
                            //            var input = document.createElement("input");
                            //
                            //input.setAttribute("type", "hidden");
                            //
                            //input.setAttribute("name", "name_you_want");
                            //input.setAttribute("id", "name_you_want");
                            //
                            //input.setAttribute("value",documentName.length);
                            //
                            ////append to form element that you want .
                            //document.appendChild(input);
                            // document.getElementById('number_of_docs').value =documentName.length;
                            // create nested arrays
                            /*    
                        for(var i=0; i<sectionName.length; i++){
                            docSec[i]=new Array();
                            for(var j=0; j<documentName.length; j++){
                                docSec[i][j]= new Array();
                            }
                        }
        
  
                        docSec[0][0]=["Putable","Calleble","assets managed by","Asset Pool Equity","Strippable"];
                        docSec[0][1]= ["Putable","Strippable","Asset Pool Equity","Strippable"];
                        docSec[0][2]= ["Putable"];
                        docSec[0][3]= ["Bond Pool","Bullet Bond","Cash CDO"];
        
        
                        docSec[1][0]=[];
                        docSec[1][1]= ["Putable","Strippable","Asset Pool Equity","Strippable"];
                        docSec[1][2]=  ["Calleble","assets managed by","Asset Pool Equity","Strippable"];
                        docSec[1][3]=  ["Bond Pool","Cash CDO"];
        
      
                        docSec[2][0]=["Management Style","Seniority","CDO Portfolio Manager"];
                        docSec[2][1]= ["Putable","Strippable","Asset Pool Equity","Strippable"];
                        docSec[2][2]=    [];
                        docSec[2][3]=  ["Bond Pool","Cash CDO"];
        

                        docSec[3][0]=["Value At Issue"];
                        docSec[3][1]= ["Putable","Strippable","Asset Pool Equity","Strippable"];
                        docSec[3][2]=  ["Calleble","assets managed by","Asset Pool Equity","Strippable","Cash CDO"];
                        docSec[3][3]=  [];
        

                        docSec[4][0]=["Value At Issue"];
                        docSec[4][1]= ["Putable","Strippable","Asset Pool Equity","Strippable"];
                        docSec[4][2]=  ["Calleble","assets managed by","Asset Pool Equity","Strippable","Value At Issue"];
                        docSec[4][3]=  ["Strippable","Asset Pool Equity"];
                             */

                            var k = 0;
                            for (var i = 0; i < sectionName.length; i++) {
                                var row = {};
                                row["sectionname"] = sectionName[k];
                                row["sectioncatogory"] = k;
                                //row["sectioncatogory"] = sectionCatogoryID[k];

                                data[i] = row;
                                k++;
                            }
                            var source =
                                {
                                localdata: data,
                                datatype: "array",
                                datafields: [
                                    { name: 'sectionname', type: 'string' },
                                    { name: 'sectioncatogory', type: 'string' }
                  
                                ] 
                            };
                            //initising the column field
                            var dataFields= new Array();
                            for(var i=0;i<documentName.length;i++){
                                dataFields.push({name:'doc'+i, type:'string'});
                            }
                            var childGridObj = {};
        
                            var initrowdetails = function (index, parentElement, gridElement, datarecord) {
                                var tabsdiv = null;
                                var information = null;
                                var notes = null;
                                tabsdiv = $($(parentElement).children()[0]);
                                childGridObj[index]=tabsdiv;
                                //  alert(tabsdiv);
                                var data2 = new Array();
            
           
            
                                //var firstName1=datarecord.firstname;
                                //var lastName1=datarecord.lastname;
                                for (var i = 0; i < 15; i++) {
                                    var row2 = {};
                                    for (var j = 0; j < documentName.length; j++) {
                                        //                        if(docSec[datarecord.sectioncatogory][j][i]==null){
                                        //                            break;
                                        //                        }
                                        if(typeof  docSec[datarecord.sectioncatogory][j] == 'undefined') {
                                            row2["doc"+j]="";
                                        }
                                        else{
                                            row2["doc"+j] = docSec[datarecord.sectioncatogory][j][i];
                                        }
                                        // row2["doc1"] = docSec[datarecord.sectioncatogory][1][i];
                                        // row2["doc2"]= docSec[datarecord.sectioncatogory][2][i];
                                        //row2["doc3"]= docSec[datarecord.sectioncatogory][3][i];
                    
                                    }
                                    data2[i] = row2;

            
                                }
                                var source2 =
                                    {
                                    localdata: data2,
                                    datatype: "array",
                                    datafields:dataFields
                                };
                                var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties) {
            
                                    return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000ff;">' + value + '</span>';
            
                                }
            
           
            
            
                                //adding data to columns
                                var columnsData = [];
                                for (var i = 0; i < documentName.length; i++) {
                                    columnsData.push({ text: documentName[i], datafield:'doc'+i , width: 150 });
                                };

                                if (tabsdiv != null) {        //if sub grid is not null
                                    var dataAdapter2 = new $.jqx.dataAdapter(source2);
                                    tabsdiv.jqxGrid({source:dataAdapter2 , theme: theme, width: 700,height: 200,pageable: true , enabletooltips: true,columns:columnsData,selectionmode: 'singlecell'});
                                    // display selected row index.
                                    //height: 200 changed toautoheight: true,
                   
                                    //  var celbgcolor=["#CC99FF","#FF9999","#FFFF66","#00FF00","#CCFFFF","#FFFFFF"];
                   
                  
                                    //////////////////////////
                                    //total number of rows for each sub-grid
                                    var rowcount= tabsdiv.jqxGrid('getrows').length;        
                    
                                    //celColor contain the number of equal terms withing each colunm
                                    var celColor= new Array(); 
                   
                                    //eterate over each cell to find out similer cells 
                                    for(var x=0; x<dataFields.length;x++){
                                        for(var y=0;y<rowcount;y++){
                
                                            //  var celValue = $('#jqxgrid').jqxGrid('getcellvalue', y, dataFields[x]);
                                            //get the cell value
                                            var celValue =  tabsdiv.jqxGrid('getcellvalue', y, dataFields[x].name);
                                            //  alert("_"+x+"_"+y+"_"+celValue);
                            
                                            //check whether "celValue" type of "celColor" object exists- if not define new one
                                            if(typeof  celColor[celValue] == 'undefined'){
                                                celColor[celValue]= new Array();
                                                celColor[celValue][0]=[dataFields[x].name];
                        
                                            }
                    
                                            // if exists                    
                                            else{
                                
                                                //function for check ing whether perticuler "celColor[element]" contains in the same datafield
                                                // or ina diffrent one
                                                function checkContains(element, target){
                       
                                                    var is_found = false;
                                                    //                                       for(var i=0;i<element.length;i++){
                                                    //                                           
                                                    //                                        if(element[i] == target){
                                                    //                                            is_found = true;
                                                    //                                            break;
                                                    //                                        }
                                                    //                                    }
                                                    for(var i=0;i<celColor[element].length;i++){
                                                        //  alert(celColor[element][i] +" &&"+target);
                                                        // alert(celColor[element][i]==target);
                                                        if(celColor[element][i] == target){
                                                            is_found = true;
                                                            break;
                                                        }
                                                    }
                                                    return is_found;
                                                }
                                
                                
                                                if(!checkContains(celValue,dataFields[x].name)){
                                                    var arrLenght=celColor[celValue].length;
                                                    celColor[celValue][arrLenght]=[dataFields[x].name];
                                                } 
                                
                                                //                                else{
                                                //                                      var arrLenght=celColor[celValue].length;
                                                //                                    celColor[celValue][arrLenght]=[dataFields[x]];
                                                //                                   
                                                //                                  }
                                            } 
                                
                               
                                        }
         
                                    }
                        
            
            
                    
                                    tabsdiv.bind('cellselect', function (event) {
                                        var celValue =  tabsdiv.jqxGrid('getcellvalue', event.args.rowindex, event.args.datafield);
                                        var noofrows=  $("#jqxgrid").jqxGrid('getrows').length;    
                                        for(var i=0;i<noofrows;i++){
                                            //setting the hilight color for the cells
                                            if(typeof  childGridObj[i] != 'undefined'){
                                                for(var z=0; z<dataFields.length;z++){
                                                    //  alert(dataFields.length+ dataFields[z]);
                                                    childGridObj[i].jqxGrid('setcolumnproperty', dataFields[z].name, 'cellsrenderer', function (row, columnfield, value, defaulthtml, columnproperties) {
                            
                                                        if (value != "" && value==celValue) {        //if values is not checked there is a bug with rendring the child grid
                                                            return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; background: ' +celbgcolor[termColor2[value]-1]+';">' + "|_"+value + "_|"+'</span>';
                                                      
                                                        }
                                                    });
                    
                                                }
                                            }
                                        }
                         
                         
                         
                                        var columnheader = tabsdiv.jqxGrid('getcolumn', event.args.datafield).text;
                                        // var celValue =  tabsdiv.jqxGrid('getcellvalue', event.args.rowindex, event.args.datafield);
                                        // $("#selectedcell").html("Row: " + event.args.rowindex + ", Column: " + columnheader+ ", CellValue: "+celValue);
                                        $("#selectedcell").html("Selected FIBO term:"+"'"+celValue+"'"+" appears in "+termColor2[celValue]+" documents");
                      
                                    });

                                }
                
       
                            }
        
                            var dataAdapter = new $.jqx.dataAdapter(source);
                            //create Parent Grid and call to raw details
                            $("#jqxgrid").jqxGrid(
                            {
                                width: 770,
                                //height: 600,
                                autoheight: true,
                                source:dataAdapter ,
                                theme: theme,
                                rowdetails: true,
            
                                rowdetailstemplate: { rowdetails: "<div id='grid' style='margin: 10px;'></div>", rowdetailsheight: 220, rowdetailshidden: true },
                                ready: function () {
                                    for(var i=0;i<6;i++){
                                        $("#jqxgrid").jqxGrid('showrowdetails',i);
                                    }
                                },
                                initrowdetails: initrowdetails,
                                columns: [
                
             
                                    { text: 'Doc Section Name', datafield: 'sectionname', width: 770}
                                ]
                            });
            
                            $("#excelExport").jqxButton({ theme: theme });
                            $("#xmlExport").jqxButton({ theme: theme });
                            $("#csvExport").jqxButton({ theme: theme });
    
            
                            $("#excelExport").click(function () {
                                // alert("test");
                                $("#jqxgrid").jqxGrid('exportdata', 'xls','doccomparison');  
              
                            });
            
                            $("#xmlExport").click(function () {
                                // alert("test");
                                $("#jqxgrid").jqxGrid('exportdata', 'xml','doccomparison');  
              
                            });
          
                            $("#csvExport").click(function () {
                                $("#jqxgrid").jqxGrid('exportdata', 'csv','doccomparison');
                            });
           
           
              
                                
                                //create grid to display document vice comparison
            
            
                            var dataFields_1= new Array();
                            for(var i=0;i<documentName.length;i++){
                                dataFields_1[i]=["docname"+i];
                            }
                   
                            var data_1 = new Array();
            
  
                            for (var i = 0; i < maxRowCount; i++) {
                                var row_1 = {};
                                for (var j = 0; j < documentName.length; j++) {
                                    //to test-dummy value-3
                                    if(typeof  fiboTermsForDoc[j] == 'undefined') {
                                        row_1["docname"+j]="";
                                    }
                                    else{
                                        row_1["docname"+j] = fiboTermsForDoc[j][i];
                                    }
                                    // row2["doc1"] = docSec[datarecord.sectioncatogory][1][i];
                                    // row2["doc2"]= docSec[datarecord.sectioncatogory][2][i];
                                    //row2["doc3"]= docSec[datarecord.sectioncatogory][3][i];
                                }
                                data_1[i] = row_1;

            
                            }
                            var columnsData_1 = [];
                            var dataFields_1=[];
                            for (var i = 0; i < documentName.length; i++) {
                                columnsData_1.push({ text: documentName[i], datafield:'docname'+i , width: 150 });
                                dataFields_1.push({name: 'docname'+i, type: 'string'});
                                //columnWidth=columnWidth+150;
                            };
            
                            var source_1 =
                                {
                                localdata: data_1,
                                datatype: "array",
                                datafields:dataFields_1
                   
                            };
    
                            var dataAdapter = new $.jqx.dataAdapter(source_1);
                            //adding data to columns
                            var columnWidth=600;
           
                
 
        
                            $("#jqxgrid_1").jqxGrid(
                            {
                                width: columnWidth,
                                height: 450,
                                //source: source_1,
                                source:dataAdapter,
                                theme: theme,
                                //pageable: true,
                                // autoheight: true,
                                // sortable: true,
                                altrows: true,
                                enabletooltips: true,
                                //editable: true,
                                selectionmode: 'multiplecellsextended',
                                columns: columnsData_1
                            });
            
  
  
  
  
  
                            /*
                             * cellcoloring happence here
                             */
  
  
                            var celbgcolor_1=["#FFFFFF","#CCFFFF","#00FF00","#FFFF66","#FF9999","#CC99FF"];
                  
                            //////////////////////////
                            //total number of rows for each sub-grid
                            var rowcount= maxRowCount;       
                    
                            //celColor contain the number of equal terms withing each colunm
                            var celColor_1= new Array(); 
                   
                            //eterate over each cell to find out similer cells 
                            for(var x=0; x<dataFields_1.length;x++){
                                for(var y=0;y<rowcount;y++){
          
                
                                    //  var celValue = $('#jqxgrid').jqxGrid('getcellvalue', y, dataFields[x]);
                                    //get the cell value
                                    var celValue_1 =   $("#jqxgrid_1").jqxGrid('getcellvalue', y, dataFields_1[x].name);
                                    //  alert("_"+x+"_"+y+"_"+celValue_1);
                            
                                    //check whether "celValue" type of "celColor" object exists- if not define new one
                                    if(typeof  celColor_1[celValue_1] == 'undefined'){
                                        celColor_1[celValue_1]= new Array();
                                        celColor_1[celValue_1][0]=[dataFields_1[x].name];
                                        //  alert("test-if");
                                    }
                    
                                    // if exists                    
                                    else{
                                        //  alert("test-else")      
                                        //function for check ing whether perticuler "celColor[element]" contains in the same datafield
                                        // or ina diffrent one
                                        function checkContains(element, target){
                       
                                            var is_found = false;
                                            //                                       for(var i=0;i<element.length;i++){
                                            //                                           
                                            //                                        if(element[i] == target){
                                            //                                            is_found = true;
                                            //                                            break;
                                            //                                        }
                                            //                                    }
                                            for(var i=0;i<celColor_1[element].length;i++){
                                                //  alert(celColor[element][i] +" &&"+target);
                                                // alert(celColor[element][i]==target);
                                                if(celColor_1[element][i] == target){
                                                    is_found = true;
                                                    break;
                                                }
                                            }
                                            return is_found;
                                        }
                                
                                
                                        if(!checkContains(celValue_1,dataFields_1[x].name)){
                                            var arrLenght=celColor_1[celValue_1].length;
                                            celColor_1[celValue_1][arrLenght]=[dataFields_1[x].name];
                                        } 
                                
                            
                                    } 
                               
                               
                                }
         
                            }
                      
  
                            for(var z=0; z<dataFields_1.length;z++){
                                //  alert(dataFields.length+ dataFields[z]);
                                $("#jqxgrid_1").jqxGrid('setcolumnproperty', dataFields_1[z].name, 'cellsrenderer', function (row, columnfield, value, defaulthtml, columnproperties) {
                            
                                    if (value != "") {        //if values is not checked there is a bug with rendring the child grid
                                        //    return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; background: #FFCC00;">' + value + '</span>';
                      
                                        return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; background: '+celbgcolor_1[celColor_1[value].length-1]+';">' + value + '</span>';
           
                                    }
                                });
                    
                            }
  
                            $("#excelExport").jqxButton({ theme: theme });
                            $("#xmlExport").jqxButton({ theme: theme });
                            $("#csvExport").jqxButton({ theme: theme });
            
                            $("#excelExport").click(function () {
                                // alert("test");
                                $("#jqxgrid_1").jqxGrid('exportdata', 'xls','doccomparison');  
              
                            });
            
                            $("#xmlExport").click(function () {
                                // alert("test");
                                $("#jqxgrid_1").jqxGrid('exportdata', 'xml','doccomparison');  
              
                            });
          
                            $("#csvExport").click(function () {
                                $("#jqxgrid_1").jqxGrid('exportdata', 'csv','doccomparison');
                            });
  
           
           
           
                            /*
                             * Combo box configurations
                             */
                     

                            // var k = 0;
                            for (var i = 0; i < termColor1.length; i++) {
                                var row = {};
                                row["name"] = termColor1[i];
                                //                row["population"] = k;
                                //                row["continentCode"] = k;
                                //                row["adminName1"] = k;
                

                                data[i] = row;
                                //  k++;
                            }
           
                            var source =
                                {
                                localdata: data,
                                datatype: "array",
                                datafields: [
                                    { name: 'name' },
                                    //                    { name: 'name' },
                                    //                    { name: 'population', type: 'float' },
                                    //                    { name: 'continentCode' },
                                    //                    { name: 'adminName1' }
                                ]
                            };
           
                            ////////new source
                            var dataAdapter = new $.jqx.dataAdapter(source,
                            {
                                formatData: function (data) {
                                    data.name_startsWith = $("#jqxcombobox").jqxComboBox('searchString');
                                    return data;
                                }
                            }
                        );
                            $("#jqxcombobox").jqxComboBox(
                            {
                                width: 250,
                                height: 25,
                                source: dataAdapter,
                                remoteAutoComplete: true,
                                theme: theme,
                                selectedIndex: 4,
                                displayMember: "name",
                                valueMember: "name",
                                renderer: function (index, label, value) {
                                    var item = dataAdapter.records[index];
                                    if (item != null) {
                                        //  var label = item.name + ", " + item.countryName + ", " + item.adminName1;
                                        var label = item.name;
                                        return label;
                                    }
                                    return "";
                                },
                                renderSelectedItem: function(index, item)
                                {
                                    var item = dataAdapter.records[index];
                                    if (item != null) {
                                        // var label = item.name + ", " + item.countryName + ", " + item.adminName1;
                                        var label = item.name;
                                        return label;
                                    }
                                    return "";   
                                },
                                search: function (searchString) {
                                    dataAdapter.dataBind();
                                }
                            });
            
                            var item22 = $("#jqxcombobox").jqxComboBox('getSelectedItem'); 
                            // var args = event.args;
                            if (item22) {
                                // var index = args.index;
                                // var item = args.item;
                                // get item's label and value.
                                // var label = item.label;
                                var cValue = item22.value;
                    
                                /*
                                 * colouring the cells
                                 */
                                var noofrows=  $("#jqxgrid").jqxGrid('getrows').length; 
                     
                                for(var i=0;i<noofrows;i++){
                                    //setting the hilight color for the cells
                                    if(typeof  childGridObj[i] != 'undefined'){
                                        for(var z=0; z<dataFields.length;z++){
                                            //  alert(dataFields.length+ dataFields[z]);
                                            childGridObj[i].jqxGrid('setcolumnproperty', dataFields[z].name, 'cellsrenderer', function (row, columnfield, value, defaulthtml, columnproperties) {
                            
                                                if (value != "" && value==cValue) {        //if values is not checked there is a bug with rendring the child grid
                                                    return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; background: ' +celbgcolor[termColor2[value]-1]+';">' + "|_"+value + "_|"+'</span>';
                                                      
                                                }
                                            });
                    
                                        }
                                    }
                                }
                        
                         
                         
                         
                                //   var columnheader = tabsdiv.jqxGrid('getcolumn', event.args.datafield).text;
                                //  var celValue =  tabsdiv.jqxGrid('getcellvalue', event.args.rowindex, event.args.datafield);
                                // $("#selectedcell").html("Row: " + event.args.rowindex + ", Column: " + columnheader+ ", CellValue: "+celValue);
                                $("#selectedcell").html("Selected FIBO term:"+"'"+cValue+"'"+" appears in "+termColor2[cValue]+" documents");
                                //alert(cValue);
                            }
                            // alert(item22.value);
            
                            $('#jqxcombobox').bind('select', function (event) 
                            {
                                var args = event.args;
                                if (args) {
                                    var index = args.index;
                                    var item = args.item;
                                    // get item's label and value.
                                    var label = item.label;
                                    var cValue = item.value;
                    
                                    /*
                                     * colouring the cells
                                     */
                                    var noofrows=  $("#jqxgrid").jqxGrid('getrows').length; 
                     
                                    for(var i=0;i<noofrows;i++){
                                        //setting the hilight color for the cells
                                        if(typeof  childGridObj[i] != 'undefined'){
                                            for(var z=0; z<dataFields.length;z++){
                                                //  alert(dataFields.length+ dataFields[z]);
                                                childGridObj[i].jqxGrid('setcolumnproperty', dataFields[z].name, 'cellsrenderer', function (row, columnfield, value, defaulthtml, columnproperties) {
                            
                                                    if (value != "" && value==cValue) {        //if values is not checked there is a bug with rendring the child grid
                                                        return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; background: ' +celbgcolor[termColor2[value]-1]+';">' + "|_"+value + "_|"+'</span>';
                                                      
                                                    }
                                                });
                    
                                            }
                                        }
                                    }
                        
                         
                         
                         
                                    //   var columnheader = tabsdiv.jqxGrid('getcolumn', event.args.datafield).text;
                                    //  var celValue =  tabsdiv.jqxGrid('getcellvalue', event.args.rowindex, event.args.datafield);
                                    // $("#selectedcell").html("Row: " + event.args.rowindex + ", Column: " + columnheader+ ", CellValue: "+celValue);
                                    $("#selectedcell").html("Selected FIBO term:"+"'"+cValue+"'"+" appears in "+termColor2[cValue]+" documents");
                                    //alert(cValue);
                                }
                            }); 
   
                        });
                </script>

                <div id='jqxWidget' style="font-size: 13px; font-family: Verdana; float: left;">
                    <h2>Document Comparison Based on FIBO Terms</h2>
                    <h3>Document Section Wise Comparison</h3>
                    <strong>Color Code: Selected term appears in</strong>
                    <!--    <input type="hidden" name="number_of_docs" id="number_of_docs" value="2"/>-->
                    <script>
                        //        alert(document.getElementById('name_you_want').value+"test");
                        //        var newlabel = document.createElement("Label");
                        ////newlabel.setAttribute("for",id_from_input);
                        //newlabel.innerHTML = "Here goes the text";
                        //parentDiv.appendChild(newlabel);
                    </script>
                    <label style="background-color:#CC99FF;">:6 Documents</label>
                    <label style="background-color:#FF9999;">:5 Documents</label>
                    <label style="background-color:#FFFF66;">:4 Documents</label>
                    <label style="background-color:#00FF00;">:3 Documents</label>
                    <label style="background-color:#CCFFFF;">:2 Documents</label>
                    <br/>
                    <br/>
                    <strong><span>*Click on a term in the grid to compare OR </span></strong>
                    <br/>
                    <strong><span>   Select a FIBO term form dropdown to find in document sections</span></strong>
                    <div style="margin-top: 7px; margin-bottom: 5px;" id="jqxcombobox"></div>

                    <br/>
                    <span id="selectedcell"></span>
                    <br />
                    <br/>
                    <div id="jqxgrid"></div>

                    <br/>

                    <div style="margin-top: 10px;">
                        <h3>Document Wise Comparison</h3>
                        <br/>
                        <strong>Color Code: </strong>
                        <br/>
                        <br/>
                        The color on the term indicates that it appears in
                        <br/>
                        <!--    <input type="hidden" name="number_of_docs" id="number_of_docs" value="2"/>-->

                        <label style="background-color:#CC99FF;">:6 Documents</label>
                        <label style="background-color:#FF9999;">:5 Documents</label>
                        <label style="background-color:#FFFF66;">:4 Documents</label>
                        <label style="background-color:#00FF00;">:3 Documents</label>
                        <label style="background-color:#CCFFFF;">:2 Documents</label>
                        <br/>
                        <br/>
                        <div id="jqxgrid_1"></div>
                        <br/>
                        <div style='float: left;'>
                            <!--        <input type="button" value="Export to Excel" id='excelExport' />-->

                            <input type="button" value="Export to CSV" id='csvExport' />

                            <input type="button" value="Export to XML" id='xmlExport' />
                        </div>
                        <br/>
                        <br/>

                    </div>
                    <!--    <div style='float: left;'>
                            <input type="button" value="Export to Excel" id='excelExport' />
                    
                            <input type="button" value="Export to CSV" id='csvExport' />
                    
                            <input type="button" value="Export to XML" id='xmlExport' />
                        </div>-->


                    <form name="iterate" action="savedata">
                        <br/>

                        <input type="submit" value="Start Again" />
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
