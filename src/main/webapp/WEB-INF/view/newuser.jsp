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


                </div>

            </div>

            <div id="content"  style="height:auto; min-height: 800px">

                <!--   <div id="content"  style="height:auto;">-->





<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.karsha.entities.User"%>


<script language="JavaScript">
function validate(form) 
{
    if (form.username.value=="") 
    {
            alert("Please fill in your user name");
            form.username.focus();
    }
    else if (form.email.value=="") 
    {
            alert("Please fill in your email");
            form.email.focus();
    }
    else if (form.password.value=="") 
    {
            alert("Please fill in your password");
            form.password.focus();
    }
    else if (form.confirm_password.value=="") 
    {
            alert("Please fill in your please enter your password again");
            form.confirm_password.focus();
    }
    else if (form.password.value != form.confirm_password.value) 
    {
        alert("The passwords do not match. Please enter them again.");
        form.password = ''
        form.confirm_password = ''
        form.password.focus();
    }
    else 
    {
           form.submit();
    }
}
</script>


<div id="indexRightColumn">
    <%String noAdminDeleteMsg=(String)session.getAttribute("noAdminDelete");
    if(noAdminDeleteMsg != null){
    %>
      <h2 style="color:red;"><%=noAdminDeleteMsg%></h2>
    <%
}
%>
    <h1>Enter your name and contact information</h1>
        <%

                
        String error =  request.getParameter("error");
        if(error != null){
        if(request.getParameter("error").equals("existing_user")){
            %>
            <p> User already exists. Please try another name </p>   
        <%
               }    
        }
        %>
    
        
        <%
        User user=(User)session.getAttribute("user");
if(user==null) {%>
        <form action="newuser" method=post>
                
        <table border="0" cellpadding="5">
        <tr>
            <td></td>
            <td align=left>Required <font color=red>*</font></td>
        </tr>
        <tr>
            <td align=right>User Name</td>
            <td><input type="text" name="username" value="" size="20" maxlength=20>
                    <font color=red>*</font></td>
        </tr>
        <tr>
            <td align=right>EMail</td>
            <td><input type=text name="email" value="" size=20>
                    <font color=red>*</font></td>
        </tr>
        <tr>
            <td align=right>Password</td>
            <td><input type=text name="password" value="" size=20>
                    <font color=red>*</font></td>
        </tr>
        <tr>
            <td align=right>Confirm Password</td>
            <td><input type=text name="confirm_password" value="" size=20>
            <font color=red>*</font></td>
        </tr>

        <tr>
            <td align=right>&nbsp;</td>
            <td><input type="button" value="Save" onClick="validate(this.form)"></td>
        </tr>
    </table>

</form>
            
            <%}
else {        
%>
<form action="edituser" method=post>
    
    
        
        <table border="0" cellpadding="5">
        <tr>
            <td></td>
            <td align=left>Required <font color=red>*</font></td>
        </tr>
        <tr>
            <td align=right>User Name</td>
            <td><input type="text" name="username" value="<%=user.getUserName()%>" size="20" maxlength=20>
                    <font color=red>*</font></td>
        </tr>
        <tr>
            <td align=right>EMail</td>
            <td><input type=text name="email" value="<%=user.getEmailAddress()%>" size=20>
                    <font color=red>*</font></td>
        </tr>
        <tr>
            <td align=right>Password</td>
            <td><input type=text name="password" value="<%=user.getPassword()%>" size=20>
                    <font color=red>*</font></td>
        </tr>
        <tr>
            <td align=right>Confirm Password</td>
            <td><input type=text name="confirm_password" value="<%=user.getPassword()%>" size=20>
            <font color=red>*</font></td>
        </tr>

        <tr>
            <td align=right>&nbsp;</td>
            <td><input type="button" value="Save" onClick="validate(this.form)"></td>
        </tr>
    </table>

</form>
    
           <%
}
         
   %>  
  
   
    <br>
    
    <table border="1" class="gridtable">
        <thead>
            <tr>
                <th >User Name</th>
                <th>EMail</th>
                <th>Delete</th>
                <th>Edit</th>
            </tr>
        </thead>
        <tbody>
            <%ArrayList<User> Users=(ArrayList<User>)session.getAttribute("users");
            
for(int j=0;j<Users.size();j++){
    %>
    
      <tr valign="top">
                <td>
                    <%=Users.get(j).getUserName()%>
                </td>
                <td>
                   <%=Users.get(j).getEmailAddress()%>
                </td>
                <td>
                    
                    <a href="userdelete?userId=<%=Users.get(j).getUserId()%>&amp;userName=<%=Users.get(j).getUserName()%>">  Delete </a>
                   
                      
                    
                </td>
                <td>
                    <a> 
                       <a href="useredit?userId=<%=Users.get(j).getUserId()%>&amp;userName=<%=Users.get(j).getUserName()%>">  Edit </a>
                    </a>
                </td>
            </tr>
    <%
    
}

%>
            
         
        </tbody>
    </table>
</div>
            
            </div>
        
            <div class="clearingdiv">&nbsp;</div>            

            <div id="footer">
                    <p>&copy; 2012 <a href="www.opensource.lk">Lanka Software Foundation</a></p>
            </div>

        </div>
    </body>
</html>
