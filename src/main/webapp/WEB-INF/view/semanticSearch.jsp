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

    <link rel="stylesheet" href="jqwidgets1/jqwidgets/styles/jqx.base.css" type="text/css" />
    <link rel="stylesheet" href="css/loader.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="css/style1.css" media="screen" type="text/css" />
    <link href="http://www.jqueryscript.net/css/top.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="jqwidgets1/scripts/gettheme.js"></script>
    <script type="text/javascript" src="scripts/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxpanel.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxtree.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxdata.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxgrid.selection.js"></script>
    <script type="text/javascript" src="jqwidgets1/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="scripts/jquery.nimble.loader.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            var theme = getDemoTheme();
            var params = {
                loaderClass   : "loading_bar_1",
                debug         : true,
                speed         : 'fast',
                hasBackground : false,
                zIndex        : 99
            };

            $.fn.nimbleLoader.setSettings(params);

            // create jqxTree
            $('#jqxTree').jqxTree({ height: '200px', hasThreeStates: true, checkboxes: true, width: '330px', theme: theme });
            $("#submit").jqxButton({ theme: theme });
            $("#submit").bind('click', function () {
                $("body").nimbleLoader("show", {
                    position             : "fixed",
                    loaderClass          : "loading_bar_body",
                    debug                : true,
                    speed                : 700,
                    hasBackground        : true,
                    zIndex               : 999,
                    backgroundColor      : "transparent",
                    backgroundOpacity    : 1
                });
                var str = "";
                var checkedItems= new Array();
                var items = $('#jqxTree').jqxTree('getCheckedItems');
                for (var i = 0; i < items.length; i++) {
                    var item = items[i];
                    str += item.label + ",";
                    checkedItems[i]=item.label;
                }
                $.post("getsimilardocs", {items:JSON.stringify(checkedItems)}, function(response){
                    $("body").nimbleLoader("hide");
                    // prepare the data
                    var data = new Array();
                    var docIds;
                    var simScores;

                     var parentStr= new Array();
                     parentStr=response.split("#");
                     for(var i=0;i<parentStr.length;i++){
                     if(parentStr[i]!=null){
                     var childStr=parentStr[i].split("$");
                     var row = {};
                     row["docID"] =childStr[0];
                     row["simScore"]=childStr[1];
                     data[i] = row;
                     }
                     }
                    var source =
                    {
                        localdata: data,
                        datatype: "array"
                    };
                    var dataAdapter = new $.jqx.dataAdapter(source, {
                        loadComplete: function (data) { },
                        loadError: function (xhr, status, error) { }
                    });
                    $("#jqxgrid").jqxGrid(
                            {
                                width: '540px',
                                source: dataAdapter,
                                columns: [
                                    { text: 'Doc ID', datafield: 'docID', width: '430px' },
                                    { text: 'SimScore', datafield: 'simScore', width: '100px' }
                                ]
                            });

                });
            });
        });
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
<body class='default'>
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
        <%@ page import="org.karsha.data.FiboDB" %>

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

        <div id="indexRightColumn">

            <h2>Find Similar Documents (FIBO based)</h2>
            <h3>FIBO Terms (Tick Terms To Search)</h3>

            <div id='jqxWidget'>
                <div style='float: left;'>
                    <div id='jqxTree' style='float: left; margin-left: 20px;'>
                        <ul>
                            <%
                                List children = (List)session.getAttribute("children");
                                for(int i=0;i<children.size();i++){
                                    List  subChil= (List)session.getAttribute("childrenof"+i);
                            %>
                            <li item-expanded='false'>
                                <%=FiboDB.getFiboTermById(Integer.parseInt((String)children.get(i))).getFiboTerm()%>
                                <ul>
                                    <%
                                        for(int x=0;x<subChil.size();x++) {

                                    %>
                                    <li>
                                        <%=FiboDB.getFiboTermById(Integer.parseInt((String)subChil.get(x))).getFiboTerm()%>
                                        <ul>
                                            <%
                                                try{
                                                    List subChil2 = (List) session.getAttribute("subChiof"+i+""+x);
                                                    for(int z=0;z<subChil2.size();z++) {
                                            %>
                                            <li>
                                                <%=FiboDB.getFiboTermById(Integer.parseInt((String)subChil2.get(z))).getFiboTerm()%>
                                                <%
                                                        }

                                                    }catch (Exception e){}
                                                %>

                                            </li>
                                        </ul>
                                        <%
                                            }
                                        %>

                                    </li>
                                </ul>
                            </li>
                            <%
                                }
                            %>

                        </ul>
                    </div>
                    <div style='margin-left: 60px; float: left;'>
                    </div>
                </div>
            </div>
            <div><input id="submit" type="submit" /></div>
            </br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br>
            <div id="jqxgrid" style='float: left; margin-left: 20px;'></div>

            <br/>
            <br/>
        </div>
    </div>
    <div id="results"></div>
    <div class="clearingdiv">&nbsp;</div>

    <div id="footer">
        <p>&copy; 2013 <a href="www.opensource.lk">Lanka Software Foundation</a></p>
    </div>

</div>
</body>
</html>