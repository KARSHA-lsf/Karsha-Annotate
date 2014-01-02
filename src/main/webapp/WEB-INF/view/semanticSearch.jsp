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
            $("input").jqxButton();
            $("input").click(function () {
                var checkedItems = $('#jqxTree').jqxTree('getCheckedItems');
                alert("Value of the first checked item: " +checkedItems[0].value);
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

            <table border="0" cellspacing="2">
                <tbody>
                <tr>
                    <form method="post" action="getsimilardocs">
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
                                        <%=children.get(i)%>
                                        <ul>
                                            <%
                                                for(int x=0;x<subChil.size();x++) {

                                            %>
                                            <li>
                                                <%=subChil.get(x)%>
                                                <ul>
                                                    <%
                                                        try{
                                                            List subChil2 = (List) session.getAttribute("subChiof"+i+""+x);
                                                            for(int z=0;z<subChil2.size();z++) {
                                                    %>
                                                    <li>
                                                        <%=subChil2.get(z)%>
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
    <div id="results"></div>
    <div class="clearingdiv">&nbsp;</div>

    <div id="footer">
        <p>&copy; 2013 <a href="www.opensource.lk">Lanka Software Foundation</a></p>
    </div>

</div>


</body>
</html>
