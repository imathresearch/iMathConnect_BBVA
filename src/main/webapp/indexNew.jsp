<%@ page import="java.security.Principal" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta charset="UTF-8">
        <title>iMath Connect</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <!-- bootstrap 3.0.2 -->
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- font Awesome -->
        <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Ionicons -->
        <link href="css/ionicons.min.css" rel="stylesheet" type="text/css" />
        <!-- Morris chart -->
        <link href="css/morris/morris.css" rel="stylesheet" type="text/css" />
        <!-- jvectormap -->
        <link href="css/jvectormap/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />
        <!-- Date Picker -->
        <link href="css/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
        <!-- Daterange picker -->
        <link href="css/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
        <!-- bootstrap wysihtml5 - text editor -->
        <link href="css/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" rel="stylesheet" type="text/css" />
        <!-- Theme style -->
        <link href="css/AdminLTE.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="images/favicon.ico"> 
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
        
        <style>
        
        
		.wrapImgProfile {
			width: 250px;
			height: 268px; 
			overflow: hidden;
			background: #fff;
			margin: 10px;
			text-align: center;
			line-height: 150px;			
			position: relative
		}
		
		.wrapImgTable {
			width: 32px;
			height: 32px; 
			overflow: hidden;
			background: #fff;
			margin: 10px;
			text-align: center;
			line-height: 150px;			
			position: relative
		}
		
		.wrapImgUserAccount {
			width: 90px;
			height: 90px; 
			overflow: hidden;
			background: #3c8dbc;
			margin: 10px;
			text-align: center;
			line-height: 150px;			
			position: relative
		
		}
		
		.wrapImgUserState {
			width: 45px;
			height: 45px; 
			overflow: hidden;
			margin: 10px;
			text-align: center;
			line-height: 150px;			
			position: relative		
		}
		
		.wrapImgProfile img, .wrapImgTable img, .wrapImgUserAccount img, .wrapImgUserState img {
			max-width: 100%;
			max-height: 100%;
			vertical-align: middle;
			margin:0;
			position: absolute;
    		top: 50%;
    		left: 50%;
    		margin-right: -50%;
    		transform: translate(-50%, -50%)
		
		}
		
		td.profile {
		    padding: 8px;		    
		    display: inline-block;
		    margin: 0;
		    border: 0;
		    width: 250px;
		}
		
		td.spaced {
			margin-left: 40px;
		}
        
        </style>
        
    </head>
    <body class="skin-blue">
        <!-- header logo: style can be found in header.less -->
        <header class="header">
            <a href="indexNew.jsp" class="logo">
                <!-- Add the class icon to your logo image or logo icon to add the margining -->
                iMath CONNECT
            </a>
            <!-- Header Navbar: style can be found in header.less -->
            <nav class="navbar navbar-static-top" role="navigation">
                <!-- Sidebar toggle button-->
                <a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                <div class="navbar-right">
                    <ul class="nav navbar-nav">
                        <!-- Notifications: style can be found in dropdown.less -->
                        <li class="dropdown notifications-menu">
                            <a href="#" class="dropdown-toggle number-notifications" data-toggle="dropdown">
                                <!-- i class="fa fa-warning"></i>
                                <span class="label label-warning">2</span-->
                            </a>
                            <ul class="dropdown-menu list-notifications">                                
                            </ul>
                        </li>
                        
                        <!-- User Account: style can be found in dropdown.less -->
                        <li class="dropdown user user-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="glyphicon glyphicon-user"></i>
                                <span class="username"></span> <i class="caret"></i>
                            </a>
                            <ul class="dropdown-menu">
                                <!-- User image -->
                                <li class="user-header bg-light-blue">
                                    <div class="wrapImgUserAccount"><img id="userphoto" src="img/avatar04.png" class="img-circle userImg" alt="User Image" /></div>
                                    <p class="username">-</p>
                                    <p class="usercreationdate">-</p>
                                </li>
                                <!-- Menu Footer-->
                                <li class="user-footer">
                                    <div class="pull-left">
                                    <!-- This link has to open a pop-up to see the user's information and for modify the password and picture. -->
                                        <a class="btn btn-primary active" id="profileForm">Profile</a>
                                    </div>
                                    <div class="pull-right">
                                        <a href="logout" class="btn btn-primary active">Sign out</a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
        <div class="wrapper row-offcanvas row-offcanvas-left">
            <!-- Left side column. contains the logo and sidebar -->
            <aside class="left-side sidebar-offcanvas">
                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">
                    <!-- Sidebar user panel -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <div class="wrapImgUserState"><img id="userphoto2" src="img/avatar04.png" class="img-circle userImg" alt="User Image" /></div>
                        </div>
                        <div class="pull-left info">
                            <p class="username">-</p>
                            <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>
                    <!-- search form -->
                    <form action="#" method="get" class="sidebar-form">
                        <div class="input-group">
                            <input type="text" name="q" class="form-control" placeholder="Search..."/>
                            <span class="input-group-btn">
                                <button type='submit' name='seach' id='search-btn' class="btn btn-flat"><i class="fa fa-search"></i></button>
                            </span>
                        </div>
                    </form>
                    <!-- /.search form -->
                    <!-- sidebar menu: : style can be found in sidebar.less -->
                    <ul id="left_tabs" class="sidebar-menu">
                        <li id="imath-id-dashboard-menu" class="imath-menu">
                            <a onclick='placeDashboard()' style='cursor: pointer;')>
                                <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                            </a>
                        </li>
                        <li id="imath-id-projects-menu" class="imath-menu">
                            <a onclick='placeLayoutProjects()' style='cursor: pointer;')>
                                <i class="fa fa-bar-chart-o"></i> <span>My Projects</span>
                            </a>
                        </li>
                         <!--li id="imath-iMathCloud-menu" class="imath-menu">
                            <a  onclick='placeiMathCloud()' style='cursor: pointer;')>
                                <i class="fa fa-bar-chart-o"></i> <span>My iMathCloud</span>
                            </a>
                        </li-->

                        <!-- li id="imath-id-instances-menu" class="imath-menu">
                            <a onclick='placeLayoutInstances()')>
                                <i class="fa fa-cog"></i> <span>My Instances</span>
                            </a>
                        </li-->
                    </ul>
                </section>
                <!-- /.sidebar -->
            </aside>

            <!-- Right side column. Contains the navbar and content of the page -->
            <aside class="right-side" >
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1 class="imath-title-menu">
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li class="active">Dashboard</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">

                    <!-- Small boxes (Stat box) -->
                    <div class="row">
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-aqua">
                                <div class="inner">
                                    <h3>
                                        <span class="imath-num-projects">-</span>
                                    </h3>
                                    <p>
                                        Projects
                                    </p>
                                </div>
                                <div class="icon">
                                    <i class="ion ion-bag"></i>
                                </div>
                            </div>
                        </div><!-- ./col -->
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-green">
                                <div class="inner">
                                    <h3>
                                        <span class="imath-num-instances">-</span>
                                    </h3>
                                    <p>
                                        Instances
                                    </p>
                                </div>
                                <div class="icon">
                                    <i class="ion ion-stats-bars"></i>
                                </div>
                            </div>
                        </div><!-- ./col -->
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-yellow">
                                <div class="inner">
                                    <h3>
                                        <span class="imath-num-users">-</span>
                                    </h3>
                                    <p>
                                        Users
                                    </p>
                                </div>
                                <div class="icon">
                                    <i class="ion ion-person-add"></i>
                                </div>
                            </div>
                        </div><!-- ./col -->
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-red">
                                <div class="inner">
                                    <h3>
                                        <span class="imath-num-users-col">-</span>
                                    </h3>
                                    <p>
                                        Collaborations
                                    </p>
                                </div>
                                <div class="icon">
                                    <i class="ion ion-pie-graph"></i>
                                </div>
                            </div>
                        </div><!-- ./col -->
                    </div><!-- /.row -->

                    <!-- Main row -->
                    <div class="row imath-main-row">
 						<section class="col-lg-7 connectedSortable">
 							<!-- Box Own projects -->
 							<div class="box imath-waiting-creation imath-waiting-own-projects">
                                <div class="box-header">
                                    <h3 class="box-title"><i class="fa fa-bar-chart-o"></i>&nbsp;&nbsp;&nbsp; My Projects</h3>
                                    <div class="box-tools pull-right">
										<div class="btn-group" data-toggle="btn-toggle">
											<button id="imath-id-new-project-button-dashboard" type="button" class="btn btn-primary active">New</button>
										</div>
									</div>
                                </div><!-- /.box-header -->
                                <div class="box-body">
                                    <table class="table table-bordered imath-own-projects">
                                    </table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box Own projects-->
                            
                            <!-- Box Collaborative projects -->
                            <div class="box imath-waiting-col-projects">
                                <div class="box-header">
                                    <h3 class="box-title"><i class="fa fa-bar-chart-o"></i>&nbsp;&nbsp;&nbsp; Collaborations</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body">
                                    <table class="table table-bordered imath-collaborations">
                                    </table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box collaborations-->
 						</section> 
 						<section class="col-lg-5 connectedSortable">
 						    <!-- Box Own instances -->
                            <div class="box imath-waiting-own-instances">
                                <div class="box-header">
                                    <h3 class="box-title"><i class="fa fa-cog"></i>&nbsp;&nbsp;&nbsp; My Instances</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body">
                                    <table class="table table-bordered imath-own-instances">
                                    </table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box Own Instances-->
 						 
 							<!-- Box public instances -->
                            <div class="box imath-waiting-pub-instances">
                                <div class="box-header">
                                	<i class="fa-cop"></i> 
                                    <h3 class="box-title"><i class="fa fa-cog"></i>&nbsp;&nbsp;&nbsp; Public Instances</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body">
                                    <table class="table table-bordered imath-public-instances">
                                    </table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box public instances-->
 						</section>
                   	</div><!-- /.row (main row) -->

                </section><!-- /.content -->
                	
                <section id="section_iMathCloud">
	                <div id="wrap_iMathCloud">
			        	<div id="div_embebed_imath">
							<iframe id="embebed_imath" height="100%" width="100%" name="imath_iframe"></iframe>
						</div>
					</div>
                </section> <!-- /.section iMathCloud -->
            
            </aside><!-- /.right-side -->
        </div><!-- ./wrapper -->
        
        
        <!-- COMPOSE MESSAGE MODAL FOR NOTIFICATIONS -->
        <div class="modal fade" id="notification-detail" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title"><i class="fa fa-info-circle"></i>  Notification Details</h3>
					</div>
					<div class= "notification-info">					
					</div>					
				</div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

		<!-- COMPOSE MESSAGE MODAL FOR CONFIRMATIONS-->
        <div class="modal fade" id="imath-id-conf-message" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title"><i class="fa fa-warning danger"></i> Confirmation! </h4>
					</div>
					<div class="modal-body">
						<p class="imath-conf-message"></p>
					</div>
					<div class="modal-footer clearfix">
						<button id="imath-id-ok-button-select" type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times"></i> OK</button>
						<button id="imath-id-cancel-button-select" type="button" class="btn" data-dismiss="modal"><i class="fa fa-times"></i> Cancel</button>
					</div>
				</div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

		<!-- COMPOSE MESSAGE MODAL FOR ERROR MESSAGES-->
        <div class="modal fade" id="imath-id-error-message-col" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title"><i class="fa fa-warning danger"></i> Error</h4>
					</div>
					<div class="modal-body">
						<p class="imath-error-message"></p>
					</div>
					<div class="modal-footer clearfix">
						<button id="imath-id-cancel-button-select" type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times"></i> OK</button>
					</div>
				</div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!-- The following modals permit to visualize user's profile, modify password and modify photograph -->

        <!-- COMPOSE MESSAGE MODAL FOR PROFILE-->
        <div class="modal fade" id="imath-profile-user" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button id="closeModalProfile" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h3 class="modal-title"><i class="fa fa-user"></i>   Profile</h3>
                    </div>
                    <div class="modal-body">
                        <form id="profileForm" class="input-group" action="#" method="post" autocomplete="off" >
                            <center>
                            <table>
                            <tr>
                            <td class="profile">
                            <p>                                
                                <center><div class="wrapImgProfile"><img class ="userImg" id="photoUser" src="img/avatar04.png" alt="User Image"/></div></center>
                            </p>                           
                            </td>                           
                            <td  class="profile spaced">                          
                            <div>
                            <p>
                                <br/>
                                <div class="form-group">
                                <label>Username</label>                                
                                <input class="form-control" id="username" name="username" type="text" placeholder="" disabled />
                                </div>
                            </p>
                            <p>
                            	<div class="form-group">
                                <label>Email</label>                                                              
                                <input class="form-control" id="email" name="email" type="text" placeholder="" disabled />                                
                            	</div>
                            </p>
                            
                                                     
                            </div>
                            </td>
                            </tr>
                            <tr>
                            <td class="profile">
                            	<div>
                            	<center><input id="changePhotograph" type="button" value="Change Photograph" class="btn btn-primary active"/></center>
                            	</div>
                            </td>
                            <td class="profile spaced">
                            	<div>
                            	<center><input id="changePassButton" type="button" value="Change Password" class="btn btn-primary active"/></center>
                            	</div>
                            </td>
                            </tr>
                            </table>
                            </center>                                                     
                        </form>
                    </div>
                    <div class="modal-footer clearfix">
                        <div style="float:right"><input id="closeProfile" type="button" value="Close" class="btn btn-danger"/></div>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!-- COMPOSE MESSAGE MODAL FOR MODIFY PASSWORD-->
        <div class="modal fade" id="imath-modify-password-user" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <form id="userPasswordForm" class="input-group" action="#" method="post" autocomplete="off" >
                <div class="modal-content">
                    <div class="modal-header">
                        <button id="closeModalPassword" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h3 class="modal-title"><i class="fa fa-lock"></i>   Password Modification</h3>
                    </div>
                    <div class="modal-body">
                            <p>
                            <label for="passwordsignup" class="youpasswd" data-icon="p">Your current password           </label>
                            <input class="form-control" id="passwordOld" name="passwordOld" required="required" type="password" placeholder="eg. X8df!90EO"/>
                            </p>
                            <p>
                                <label for="passwordsignup" class="youpasswd" data-icon="p">Your new password               </label>
                                <input class="form-control" id="passwordNew" name="passwordNew" required="required" type="password" placeholder="eg. X8df!90EO"/>
                            </p>
                            <p>
                                <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Please confirm your new password </label>
                                <input class="form-control" id="passwordNewConf" name="passwordNewConf" required="required" type="password" placeholder="eg. X8df!90EO"/>
                            </p>
                            <br/>
                            <p>
                                <div style="float:right"><input id="changePassword" type="button" value="Change Password" class="btn btn-primary active"/></div>
                            </p>
                            <br/>
                    </div>
                    <div class="modal-footer clearfix">
                            <div id="profilePasswordMsg" style="float:left"></div>
                            <div style="float:right"><input id="closePassword" type="button" value="Close" class="btn btn-danger"/></div>
                            <p>
                            </p>
                    </div>
                </div><!-- /.modal-content -->
                </form>
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!-- COMPOSE MESSAGE MODAL MODIFY PHOTOGRAPH-->
        <div class="modal fade" id="imath-modify-user-photograph" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <form action="#" class="form-group" id="changePhoto" name="changePhoto" method="post" enctype="multipart/form-data" class="sidebar-form">
                <div class="modal-content">
                    <div class="modal-header">
                        <button id="closeModalPhotograph" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Photograph Modification</h4>
                    </div>
                    <div class="modal-body">
                            <label for="passwordsignup" class="youpasswd" data-icon="">Select new photograph: </label>
                            <input type="file" id="uploadedFile" name="uploadedFile" size="50" />
                            <div style="float:right"><input type="button" name="uploadFile" id="uploadFile" value="Upload It" class="btn btn-primary active" /></div>
                            <p>
                            </p>
                    </div>
                    <div class="modal-footer clearfix">
                        <div id="profilePhotographsg" style="float:left"></div>
                        <div style="float:right"><input type="button" name="closeFile" id="closeFile" value="Close" class="btn btn-danger" /></div>
                        <p>
                        </p>
                    </div>
                </div><!-- /.modal-content -->
                </form>
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        
        


        <!-- jQuery 2.0.2 -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <!-- jQuery UI 1.10.3 -->
        <script src="js/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
        <!-- Bootstrap -->
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <!-- Sparkline -->
        <script src="js/plugins/sparkline/jquery.sparkline.min.js" type="text/javascript"></script>
        <!-- jvectormap -->
        <script src="js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js" type="text/javascript"></script>
        <script src="js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js" type="text/javascript"></script>
        <!-- daterangepicker -->
        <script src="js/plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>
        <!-- datepicker -->
        <script src="js/plugins/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
        <!-- iCheck -->
        <script src="js/plugins/iCheck/icheck.min.js" type="text/javascript"></script>
		<script src="js/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js" type="text/javascript"></script>
        <!-- AdminLTE App -->
        <script src="js/AdminLTE/app.js" type="text/javascript"></script>
		<script src="js/plugins/ionslider/ion.rangeSlider.min.js" type="text/javascript"></script>
        <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
        <script src="js/AdminLTE/dashboard.js" type="text/javascript"></script>

        <!-- AdminLTE for demo purposes -->
        <script src="js/AdminLTE/demo.js" type="text/javascript"></script>
		<script type="text/javascript">
			var userName = "<%= request.getUserPrincipal().getName() %>";
		</script>
		<script src="js/imath/dashboard.js" type="text/javascript"></script>
		<script src="js/imath/projects.js" type="text/javascript"></script>
		<script src="js/imath/instances.js" type="text/javascript"></script>
		<script src="js/imath/notifications.js" type="text/javascript"></script>
		<script src="js/imath/photo.js" type="text/javascript"></script>
		<script src="js/imath/changePassword.js" type="text/javascript"></script>
		<script src="js/imath/Profile.js" type="text/javascript"></script>
		<script>
			function getProperHeight() {
				var gheight = $(window).height();
				return gheight;
			}
		</script>
    </body>
	
</html>