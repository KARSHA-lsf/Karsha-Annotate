<%-- 
    Document   : comparealldocs
    Created on : Nov 7, 2012, 1:56:01 PM
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





<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="org.karsha.entities.FiboTerm"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.lang.String"%>
<%@page import="java.util.HashMap"%>

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
        //var sectionName =["Risk Factors","Discription of Notes","Plan of Distribution","ERISA Considerations", "Tax Considerations"];
        var sectionCatogoryID= ["0","1","2","3","4"];
        
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
            documentName[<%=x%>]=["<%=pEntry.getKey()%>"];  
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
        
        
    
            /*
    

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
                datatype: "array"
            };
            //initising the column field
            var dataFields= new Array();
            for(var i=0;i<documentName.length;i++){
                dataFields[i]=["doc"+i];
            }
       
        
            var initrowdetails = function (index, parentElement, gridElement, datarecord) {
                var tabsdiv = null;
                var information = null;
                var notes = null;
                tabsdiv = $($(parentElement).children()[0]);
            
                //  alert(tabsdiv);
                var data2 = new Array();
            
           
            
                //var firstName1=datarecord.firstname;
                //var lastName1=datarecord.lastname;
                for (var i = 0; i < 10; i++) {
                    var row2 = {};
                    for (var j = 0; j < documentName.length; j++) {
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
                    datatype: "array"
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
                    tabsdiv.jqxGrid({source: source2, theme: theme, width: 700, height: 200,columns:columnsData});
                    //  var celbgcolor=["#CC99FF","#FF9999","#FFFF66","#00FF00","#CCFFFF","#FFFFFF"];
                    var celbgcolor=["#FFFFFF","#CCFFFF","#FFFF66","#00FF00","#FF9999","#CC99FF"];
                  
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
                            var celValue =  tabsdiv.jqxGrid('getcellvalue', y, dataFields[x]);
                            //  alert("_"+x+"_"+y+"_"+celValue);
                            
                            //check whether "celValue" type of "celColor" object exists- if not define new one
                            if(typeof  celColor[celValue] == 'undefined'){
                                celColor[celValue]= new Array();
                                celColor[celValue][0]=[dataFields[x]];
                        
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
                                
                                
                                if(!checkContains(celValue,dataFields[x])){
                                    var arrLenght=celColor[celValue].length;
                                    celColor[celValue][arrLenght]=[dataFields[x]];
                                } 
                                
                                //                                else{
                                //                                      var arrLenght=celColor[celValue].length;
                                //                                    celColor[celValue][arrLenght]=[dataFields[x]];
                                //                                   
                                //                                  }
                            } 
                                
                               
                        }
         
                    }
                        
            
                    //setting the hilight color for the cells
                                
                    for(var z=0; z<dataFields.length;z++){
                        //  alert(dataFields.length+ dataFields[z]);
                        tabsdiv.jqxGrid('setcolumnproperty', dataFields[z], 'cellsrenderer', function (row, columnfield, value, defaulthtml, columnproperties) {
                            
                            if (value != "") {        //if values is not checked there is a bug with rendring the child grid
                                
                                return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; background: ' +celbgcolor[celColor[value].length-1]+';">' + value + '</span>';
           
                            }
                        });
                    
                    }

                }
                
       
            }
        
 
            //create Parent Grid and call to raw details
            $("#jqxgrid").jqxGrid(
            {
                width: 770,
                // height: 450,
                source: source,
                theme: theme,
                rowdetails: true,
                pageable: true,
                autoheight: true,
                sortable: true,
                altrows: true,
                enabletooltips: true,
                //editable: true,
                selectionmode: 'multiplecellsextended',
            
                rowdetailstemplate: { rowdetails: "<div id='grid' style='margin: 10px;'></div>", rowdetailsheight: 220, rowdetailshidden: true },
                ready: function () {
                    $("#jqxgrid").jqxGrid('showrowdetails', 1);
  
                },
                initrowdetails: initrowdetails,
                columns: [
                
             
                    { text: 'Doc Section Name', datafield: 'sectionname', width: 770}
                ]
            });
            
            
            */
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
  
  
            var celbgcolor_1=["#FFFFFF","#CCFFFF","#FFFF66","#00FF00","#FF9999","#CC99FF"];
                  
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
  
           
        });
</script>

<div id='jqxWidget' style="font-size: 13px; font-family: Verdana; float: left;">
    <strong>Document Wise Comparison</strong>
    *(how many different terms are common to the documents)
    
    <br/>
    <br/>
    <strong>Color Code</strong>
    <!--    <input type="hidden" name="number_of_docs" id="number_of_docs" value="2"/>-->

    <label style="background-color:#CC99FF;">:6 Terms Similar</label>
    <label style="background-color:#FF9999;">:5 Terms Similar</label>
    <label style="background-color:#00FF00;">:4 Terms Similar</label>
    <label style="background-color:#FFFF66;">:3 Terms Similar</label>
    
    <label style="background-color:#CCFFFF;">:2 Terms Similar</label>
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
<!--    <strong>Section vise Comparison</strong>
    <br/>
    <br/>
    <strong>Color Code</strong>
        <input type="hidden" name="number_of_docs" id="number_of_docs" value="2"/>

    <label style="background-color:#CC99FF;">:6 Terms Similar</label>
    <label style="background-color:#FF9999;">:5 Terms Similar</label>
    <label style="background-color:#00FF00;">:4 Terms Similar</label>
    <label style="background-color:#FFFF66;">:3 Terms Similar</label>
    
    <label style="background-color:#CCFFFF;">:2 Terms Similar</label>
    <br/>
    <br/>



    <div id="jqxgrid"></div>-->


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
