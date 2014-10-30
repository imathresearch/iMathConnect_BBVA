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
        <script src="js/loginInitialization.js" type="text/javascript"></script>
        
        <link href="css/font-awesome.css" rel="stylesheet" type="text/css" />
        <!-- link href="css/AdminLTE.css" rel="stylesheet" type="text/css" /-->
        <!-- link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" /-->
        <link href="css/ionicons.min.css" rel="stylesheet" type="text/css" />
        
        <link rel="shortcut icon" href="images/favicon.ico"> 
        <link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style2.css" />
		<!-- link rel="stylesheet" type="text/css" href="css/animate-custom.css" /-->
		<link rel="stylesheet" type="text/css" href="css/login.css" />
		
		<style type="text/css">    
			body {
				background: url(img/background7.jpg) no-repeat center center fixed #000; 
				-webkit-background-size: cover;
				-moz-background-size: cover;
				-o-background-size: cover;
				background-size: cover;
			}
			.container-centered {
				position: absolute;
                top: 0;
                bottom: 0;
                left: 0;
                right: 0;
                display: flex;
                justify-content: space-around;
                align-items: center;
                flex-wrap: wrap;
    		}
    		
    		.google-login{    		
    			color: rgb(57, 191, 215);
				background: rgb(247, 247, 247);
				border: 1px solid rgb(74, 179, 198);
				font-weight: bold;
				padding: 5px 6px;
				margin-left: 10px;
				text-decoration: none;
				border-radius: 4px;
				font-size: 16px;
				text-align: right;
				font-family: "Trebuchet MS","Myriad Pro",Arial,sans-serif;
				outline: none;
				transition: all 0.4s linear;
				transition-property: all;
				transition-duration: 0.4s;
				transition-timing-function: linear;
				transition-delay: initial;	
				cursor:pointer;			
    			
    		}
    		button.google-login i { font-size: 20pt; width: 22px; }
		</style>
		
    </head>
    <body>
    <%
    if (request.getUserPrincipal() != null) {
    	// It means we are already authenticated!
    	response.sendRedirect("indexNew.jsp");     	
    }
	%>
        <div class="container">
            <section>				
                <div id="container_demo" class="container-centered" >
                    <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    <a class="hiddenanchor" id="torecover"></a>
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="animated form in">
                            <form  action="login" method="POST" autocomplete="on"> 
                                <h1> iMath Cloud - Log in </h1> 
                                <p> 
                                    <label for="username" class="uname" data-icon="u" > Your username </label>
                                    <input id="username" name="j_username" required="required" type="text" placeholder="myusername"/>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p"> Your password </label>
                                    <input id="password" name="j_password" required="required" type="password" placeholder="eg. X8df!90EO" /> 
                                </p>
                                <p class="keeplogin"> 
									<input type="checkbox" name="loginkeeping" id="loginkeeping" value="loginkeeping" /> 
									<label for="loginkeeping">Keep me logged in</label>
								</p>								
                                <p class="login button"> 
                                	
                                    <input type="submit" value="Login" /> 
								</p>
								<p>
								  <a href="javascript:gotobox('recover');" class="to_register">Did you forget your password ?</a>
								</p>
								
								<p align="right" style="font-size:14px; color: rgb(127, 124, 124);">
									Login using a third-party account
								</p>
								<div class="list-group">
								<p align="right">
																	
										<button type="button" class="btn btn-primary google-login" onclick="location.href='logingoogle';">
											<i class="fa fa-google-plus fa-fw"></i> 
										</button>
									
										<button type="button" class="btn btn-primary google-login" onclick="location.href='loginlinkedin';">
											<i class="fa fa-linkedin fa-fw"></i>
										</button>
									
									
										<button type="button" class="btn btn-primary google-login" onclick="location.href='logingithub';">
											<i class="fa fa-github fa-fw"></i>
										</button>
									
								</p>
								</div>
								<br/>
								
								<!-- p align="right">									
									<button type="button" class="btn btn-primary google-login" onclick="location.href='loginlinkedin';">
										<i class="fa fa-linkedin"></i> Login with LinkedIn
									</button>
								</p-->
								<!-- p>									
									<a class="btn btn-social-icon btn-google-plus"><i class="fa fa-google-plus"></i></a>								
								</p-->
								
                                <p class="change_link">
									Not a member yet ?
									<a href="javascript:gotobox('register');" class="to_register">Join us</a>
								</p>
                            </form>
                        </div>

                        <div id="register" class="animated form out">
                            <form  action="register" method="POST" autocomplete="on"> 
                                <h1> iMath Cloud - Sign up </h1> 
                                <p> 
                                    <label for="usernamesignup" class="uname" data-icon="u">Your username</label>
                                    <input id="usernamesignup" name="usernamesignup" required="required" type="text" placeholder="mysuperusername690" />
                                </p>
                                <p> 
                                    <label for="emailsignup" class="youmail" data-icon="e" > Your email</label>
                                    <input id="emailsignup" name="emailsignup" required="required" type="email" placeholder="mysupermail@mail.com"/> 
                                </p>
                                <p> 
                                    <label for="passwordsignup" class="youpasswd" data-icon="p">Your password </label>
                                    <input id="passwordsignup" name="passwordsignup" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p> 
                                    <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Please confirm your password </label>
                                    <input id="passwordsignup_confirm" name="passwordsignup_confirm" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p class="signin button"> 
									<input type="submit" value="Sign up"/> 
								</p>
                                <p class="change_link">  
									Already a member ?
									<a href="javascript:gotobox('login');" class="to_register"> Go and log in </a>
								</p>
                            </form>
                        </div>
                        
                         <div id="recover" class="animated form out">
                            <form  action="recoverpassword" method="POST" autocomplete="on"> 
                                <h1>  iMathCloud - Recover password </h1>                                 
                                <p> 
                                    <label for="emailsignup" class="youmail" data-icon="e" > Your email</label>
                                    <input id="emailsignup" name="emailsignup" required="required" type="email" placeholder="mysupermail@mail.com"/> 
                                </p>                               
                                <p class="signin button"> 
									<input type="submit" value="Recover"/> 
								</p>
                               <p class="change_link">
									Already a member ?
									<a href="javascript:gotobox('login');"  class="to_register"> Go and log in </a>
								</p>
                            </form>
                        </div>
						
                    </div>
                </div>
                <p>  
                	<script type="text/javascript">
                		var FF = !(window.mozInnerScreenX == null);
						if (!FF) {
							document.write("<b>Get a better experience with <a href='http://www.firefox.com'>Mozilla Firefox<a></b>");
						}                	
                	</script>
                </p>
            </section>
        </div>
    </body>
</html>