<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>        
        <meta charset="UTF-8" />
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
        <title>Login and Registration Form with HTML5 and CSS3</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <meta name="description" content="iMath Cloud" />
        <meta name="keywords" content="iMath Clound, iMath Connect, Data Science, Analytics, Python, R, Octave" />
        <meta name="author" content="iMath" />       
        
        <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>      
        <script src="js/loginPrettyInitialization.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css" />
        <link rel="stylesheet" type="text/css" href="css/login.css" />
        <link rel="stylesheet" type="text/css" href="css/prettyStyle.css" />       
		
		<style type="text/css">    
			body {
				background: url(img/background1.jpg) no-repeat center center fixed #000; 
				-webkit-background-size: cover;
				-moz-background-size: cover;
				-o-background-size: cover;
				background-size: cover;
			}
			
			.form
			{
				background-color: rgba(54,127,169,0.9);
				border: 5px solid rgba(255, 255, 255, 0.4);
			}
			
			h1 {
				color:White;
			}
			
			.title {
				text-align: center;
			}
			
			.change_link {
				color: White;
			}
		</style>  
    </head>
    <br><br>
    <body>
    <%
    if (request.getUserPrincipal() != null) {
    	// It means we are already authenticated!
    	response.sendRedirect("indexNew.jsp");     	
    }
	%>
    <br><br>
    
<!-- div id="fullscreen_bg" class="fullscreen_bg"/>
<div class="container""-->
  

<div class="row" style="margin-top:20px">
    <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">	
    
			<section class="backgroud">
			<a class="hiddenanchor" id="torecover"></a>
			<a class="hiddenanchor" id="toregister"></a>
			<a class="hiddenanchor" id="tologin"></a>	
				
				<div id="login" class="animated form in">
                       <form  action="login" method="POST" autocomplete="on">
	                       <h1 class="title">iMath Cloud - Log in</h1>
							<hr class="colorgraph">                                 
	                        <div class="form-group">                                    
	                               <input id="username" name="j_username" required="required" type="text" class="form-control input-lg" placeholder="Your username"/>
	                        </div>
	                        <div class="form-group">                                       
	                               <input id="password" name="j_password" required="required" type="password" class="form-control input-lg" placeholder="Your password" /> 
	                        </div>
	                           
	                        <span class="button-checkbox">
	                        	<button type="button" class="btn" data-color="info">Remember Me</button>
								<input type="checkbox" name="remember_me" id="remember_me" checked="checked" class="hidden">
								<a href="javascript:gotobox('recover');" class="to_register btn btn-link pull-right">Forgot Password?</a>
							</span>
				
							<hr class="colorgraph">
							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6">
				                       <input type="submit" class="btn btn-lg btn-success btn-block" value="Log In">
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6">
									<a href="javascript:gotobox('register');"" class="to_register btn btn-lg btn-primary btn-block">Join us</a>
								</div>
							</div>                                                                                            
	                   </form>
                 </div>
                 
                  <div id="register" class="animated form out">
                        <form  action="register" method="POST" autocomplete="on"> 
                            <h1 class="title"> iMath Cloud - Sign up </h1>
                            <hr class="colorgraph"> 
                            <div class="form-group"> 
                                <input id="usernamesignup" name="usernamesignup" required="required" type="text" class="form-control input-lg" placeholder="Your username" />
                            </div>
                            <div class="form-group"> 
                                <input id="emailsignup" name="emailsignup" required="required" type="email" class="form-control input-lg" placeholder="Your email"/> 
                            </div>
                            <div class="form-group"> 
                                <input id="passwordsignup" name="passwordsignup" required="required" type="password" class="form-control input-lg" placeholder="Your password"/>
                            </div>
                            <div class="form-group"> 
                                <input id="passwordsignup_confirm" name="passwordsignup_confirm" required="required" type="password" class="form-control input-lg" placeholder="Please confirm your password "/>
                            </div>
                            
                            <p class="change_link">  
								Already a member ?
								<a href="javascript:gotobox('login');" class="to_register"> Go and log in </a>
							</p>
                            
                            <hr class="colorgraph">
                            
                            <div class="col-xs-6 col-sm-6 col-md-6">
				                       <input type="submit" class="btn btn-lg btn-success btn-block" value="Sign In">
							</div>
                            
                        </form>
                </div>
                        
                <div id="recover" class="animated form out">
                        <form  action="recoverpassword" method="POST" autocomplete="on"> 
                                <h1 class="title">  iMathCloud - Recover password </h1>
                                <hr class="colorgraph">                                
                                <div class="form-group">
                                    <input id="emailsignup" name="emailsignup" required="required" type="email" class="form-control input-lg" placeholder="Your email"/> 
                                </div> 
                                <p class="change_link">
									Already a member ?
									<a href="javascript:gotobox('login');"  class="to_register"> Go and log in </a>
								</p>
								
								<hr class="colorgraph">
								                   
                                <div class="col-xs-6 col-sm-6 col-md-6">
									<input type="submit" class="btn btn-lg btn-success btn-block" value="Recover"/> 
								</div>
                               
                        </form>
                </div>
                 																
		</section>
	</div>
</div>

</div>
    </body>
</html>