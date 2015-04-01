<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<title>The Room Mate Agreement</title>
<head>
<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="js/userValidation.js"></script>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrapValidator.min.css" />
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>

<body>
<div class="container" align="center">
    <div id="w" style="margin-top:6em">
        <img src="images/roommatelogo.jpg" width="440" height="340"/>
        <br><br>
        <b>Task tracker for room mates.</b>
        <br><br>
        <p>
            <table>
                <tr>
                    <td>
                        <button class="btn btn-primary" data-toggle="modal" data-target="#loginModal">&nbsp;&nbsp;Login&nbsp;&nbsp;</button>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;</td>
                    <td>
                        <button class="btn btn-primary" data-toggle="modal" data-target="#signupModal">&nbsp;Signup&nbsp;</button>
                    </td>
                </tr>
            </table>    
        </p>
        <!-- Login modal begins -->
        <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:35%;align:center;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Login</h4>
                    </div>
                    <div class="modal-body">
                    <!-- The form is placed inside the body of modal -->
                        <form id="loginForm" method="post" class="form-horizontal" action=ValidateLogin>
                            <div class="form-group">
                                <div class="col-md-3"></div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="username" placeholder="Username"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-3"></div>
                                <div class="col-md-6">
                                    <input type="password" class="form-control" name="password" placeholder="Password" />
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-6 col-md-offset-3">
                                    <button type="submit" class="btn btn-primary"style="width:50%;">Login</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- Login modal ends -->
        <!-- Signup modal begins -->
        <div class="modal fade" id="signupModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Register</h4>
                    </div>
                    <div class="modal-body">
                    
                    <!-- Registration form -->
                    
                        <form id="signupForm" method="post" class="form-horizontal" action=RegisterUser>
                            <div class="form-group row">
                                <div class="col-md-6">
                                    <input type="text" name="firstname" id="firstname" class="form-control" placeholder="Firstname" tabindex = 1>
                                </div>
                                <div class="col-md-6">
                                    <input type="text" name="username" id="username" class="form-control" placeholder="Username"tabindex = 4>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-md-6">
                                    <input type="text" name="lastname" id="lastname" class="form-control" placeholder="Lastname"tabindex = 2>
                                </div>
                                 <div class="col-md-6">
                                    <input type="password" name="password" id="password" class="form-control" placeholder="Password"tabindex = 5>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-md-6">
                                    <input type="text" name="email" id="email" class="form-control" placeholder="Enter Email"tabindex = 3>
                                </div>
                                 <div class="col-md-6">
                                    <input type="password" name="repassword" id="repassword" class="form-control" placeholder="Confirm Password"tabindex = 6>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div style="align:center;">
                                    <button type="submit" class="btn btn-success">Create an account !</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- Signup modalends -->
    </div>
</div>
</body>
</html>
