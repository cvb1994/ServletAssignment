<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- Start header -->
       <!-- ============================================================== -->
       <header class="topbar" data-navbarbg="skin5">
		<nav class="navbar top-navbar navbar-expand-md navbar-dark">
		    <div class="navbar-header" data-logobg="skin6">
		        <!-- ============================================================== -->
		        <!-- Logo -->
		        <!-- ============================================================== -->
		        <a class="navbar-brand" href="PondIndex">
		            <!-- Logo icon -->
		            <b class="logo-icon">
		                <!-- Dark Logo icon -->
		                <img src="../public/plugins/images/logo-icon.png" alt="homepage" />
		            </b>
		            <!--End Logo icon -->
		            <!-- Logo text -->
		            <span class="logo-text">
		                <!-- dark Logo text -->
		                <img src="../public/plugins/images/logo-text.png" alt="homepage" />
		            </span>
		        </a>
		        <!-- ============================================================== -->
		        <!-- End Logo -->
		        <!-- ============================================================== -->
		        <!-- ============================================================== -->
		        <!-- toggle and nav items -->
		        <!-- ============================================================== -->
		        <a class="nav-toggler waves-effect waves-light text-dark d-block d-md-none"
		            href="javascript:void(0)"><i class="ti-menu ti-close"></i></a>
		    </div>
		    <!-- ============================================================== -->
		    <!-- End Logo -->
		    <!-- ============================================================== -->
		    <div class="navbar-collapse collapse" id="navbarSupportedContent" data-navbarbg="skin5">
		        <ul class="navbar-nav d-none d-md-block d-lg-none">
		            <li class="nav-item">
		                <a class="nav-toggler nav-link waves-effect waves-light text-white"
		                    href="javascript:void(0)"><i class="ti-menu ti-close"></i></a>
		            </li>
		        </ul>
		        <!-- ============================================================== -->
		        <!-- Right side toggle and nav items -->
		        <!-- ============================================================== -->
		        <ul class="navbar-nav ms-auto d-flex align-items-center">
		            <!-- User profile -->
		            <!-- ============================================================== -->
		            <li>
		            	<%
		            		session = request.getSession();
		            		String userName = (String) session.getAttribute("userName");
		            	%>
		                <div class="btn-group">
                          
                          <a class="profile-pic dropdown-toggle" role="button" href="#" data-toggle="dropdown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                    <img src="../public/plugins/images/users/varun.jpg" alt="user-img" width="36"
			                        class="img-circle"><span class="text-white font-medium"><%=userName%></span></a>
                          <div class="dropdown-menu">
                            <a href="LoginController" class="dropdown-item" style="margin-left:20px">Logout</a>
                          </div>
                        </div>
		            </li>
		            <!-- ============================================================== -->
		            <!-- User profile and search -->
		            <!-- ============================================================== -->
		        </ul>
		    </div>
	    </nav>
	</header>
	<!-- ============================================================== -->
	<!-- End Topbar header -->
	<!-- ============================================================== -->
	<!-- Left Sidebar - style you can find in sidebar.scss  -->
	<!-- ============================================================== -->
	<aside class="left-sidebar" data-sidebarbg="skin6">
	    <!-- Sidebar scroll-->
	<div class="scroll-sidebar">
	    <!-- Sidebar navigation-->
	    <nav class="sidebar-nav">
	        <ul id="sidebarnav">
	            <!-- User Profile-->
	            <li class="sidebar-item">
	                <a style="text-decoration: none;" class="sidebar-link waves-effect waves-dark sidebar-link" href="PondIndex"
	                    aria-expanded="false">
	                    <i class="fa fa-table" aria-hidden="true"></i>
	                    <span class="hide-menu">Quản lý hồ cá</span>
	                </a>
	            </li>
	            <li class="sidebar-item">
	                <a style="text-decoration: none;" class="sidebar-link waves-effect waves-dark sidebar-link" href="FishIndex"
	                    aria-expanded="false">
	                    <i class="fa fa-table" aria-hidden="true"></i>
	                    <span class="hide-menu">Quản lý cá</span>
	                </a>
	            </li>
	        </ul>
	
	    </nav>
	    <!-- End Sidebar navigation -->
	</div>
	<!-- End Sidebar scroll-->
	</aside>