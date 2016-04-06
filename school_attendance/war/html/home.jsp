<%--
  Created by IntelliJ IDEA.
  User: Rono
  Date: 8/9/2015
  Time: 2:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Attendance</title>
    <meta http-equiv="cache-control" content="max-age=0"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="Metronic Launcher"/>
    <meta content="" name="KeenThemes"/>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:300italic,400italic,700italic,400,300,700&subset=latin,cyrillic-ext,greek-ext,cyrillic,latin-ext,vietnamese,greek'
          rel='stylesheet' type='text/css'>

    <link href="../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet"
          type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN THEME STYLES -->
    <link href="../assets/global/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>
    <link href="../assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
    <link id="style_color" href="../assets/admin/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>

    <link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo">
<!-- BEGIN HEADER -->
<div class="page-header navbar navbar-fixed-top">
    <!-- BEGIN HEADER INNER -->
    <div class="page-header-inner">
        <!-- BEGIN LOGO -->
        <div class="page-logo">
            <%--<a href="">--%>
            <%--</a>--%>
            <div class="menu-toggler sidebar-toggler">
            </div>
        </div>
        <!-- END LOGO -->
        <!-- BEGIN HORIZANTAL MENU -->
        <!-- DOC: Remove "hor-menu-light" class to have a horizontal menu with theme background instead of white background -->
        <!-- DOC: This is desktop version of the horizontal menu. The mobile version is defined(duplicated) in the responsive menu below along with sidebar menu. So the horizontal menu has 2 seperate versions -->
        <div class="hor-menu hor-menu-light hidden-sm hidden-xs">
            <ul class="nav navbar-nav">
                <!-- DOC: Remove data-hover="megadropdown" and data-close-others="true" attributes below to disable the horizontal opening on mouse hover -->
                <li class="classic-menu-dropdown active">
                    <a href=""> Dashboard <span class="selected"></span></a>
                </li>
                <li class="classic-menu-dropdown">
                    <a data-toggle="dropdown" href="javascript:;">
                        Manage Staff <i class="fa fa-angle-down"></i>
                    </a>
                    <ul class="dropdown-menu pull-left">
                        <!-- Content container to add padding -->
                        <li>
                            <a id="AddStaff" href="javascript:void(0)" class="addStaffClass"> <i
                                    class=" fa fa-angle-right "></i>Add
                                Staff </a>
                        </li>

                        <li>
                            <a id="ModifyStaff" href="javascript:void(0)" class="modifyStaffClass"><i
                                    class=" fa fa-angle-right "></i>Modify
                                Staff </a>
                        </li>

                        <li>
                            <a id="ViewAcc" href="javascript:void(0)" class="viewAccClass"> <i
                                    class=" fa fa-angle-right"></i>View Account Information </a>
                        </li>
                        <li>
                            <a id="viewStaffTable" class="viewAllStaff" href="javascript:void(0)"><i
                                    class=" fa fa-angle-right"></i> View/Edit/Delete Staff </a>
                        </li>
                    </ul>
                </li>
                <li class="classic-menu-dropdown">
                    <a data-toggle="dropdown" href="javascript:;">
                        Attendance <i class="fa fa-angle-down"></i>
                    </a>
                    <ul class="dropdown-menu pull-left">
                        <!-- Content container to add padding -->
                        <li>
                            <a id="AddStudent" href="javascript:void(0)" class="addStudentClass"> <i
                                    class="fa fa-angle-right"></i>Add Student Record </a>
                        </li>

                        <li>
                            <a id="AddAttendance" href="javascript:void(0)" class="addAttendanceClass"><i
                                    class="fa fa-angle-right"></i>
                                Add Attendance </a>
                        </li>

                        <li>
                            <a id="ViewAttendance" href="javascript:void(0)" class="viewAttendanceClass"> <i
                                    class="fa fa-angle-right"></i>View Attendance </a>
                        </li>
                    </ul>
                </li>
                <li class="classic-menu-dropdown">
                    <a data-toggle="dropdown" href="javascript:;">
                        Contact Us
                    </a>
                </li>

                <li class="classic-menu-dropdown">
                    <a class="logoutClass" href="javascript:void(0)">
                        Log Out
                    </a>
                </li>
            </ul>
        </div>
        <!-- END HORIZONTAL MENU -->

        <!-- BEGIN TOP NAVIGATION MENU -->
        <div class="top-menu">
            <ul class="nav navbar-nav pull-right">

                <!-- BEGIN USER LOGIN DROPDOWN -->
                <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                <li class="dropdown dropdown-user">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
                       data-close-others="true">
                        <%--<img alt="" class="img-circle" src="../assets/admin/layout/img/avatar3_small.jpg"/>--%>
					<span class="username username-hide-on-mobile">
                        ${lecturerCategory}</span>
                        <i class="fa fa-angle-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-default" href="javascript:void(0)">
                        <li>
                            <a class="logoutClass">
                                <i class="icon-key"></i> Log Out </a>
                        </li>
                    </ul>
                </li>
                <!-- END USER LOGIN DROPDOWN -->
            </ul>
        </div>
        <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END HEADER INNER -->
</div>
<!-- END HEADER -->
<div class="clearfix">
</div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <!-- BEGIN SIDEBAR -->
    <div class="page-sidebar-wrapper">
        <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
        <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
        <div class="page-sidebar navbar-collapse collapse">
            <!-- BEGIN SIDEBAR MENU1 -->
            <ul class="page-sidebar-menu hidden-sm hidden-xs" data-auto-scroll="true" data-slide-speed="200">
                <!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
                <!-- DOC: This is mobile version of the horizontal menu. The desktop version is defined(duplicated) in the header above -->
                <li class="start active open">
                    <a href="javascript:;">
                        <i class="fa fa-cogs"></i>
                        <span class="title"> Home </span>
					<span class="arrow open">
					</span>
                        <span class="selected"></span>
                    </a>
                </li>

                <li class="last">
                    <%--future pages on sideBar--%>
                </li>
            </ul>
            <!-- END SIDEBAR MENU1 -->
        </div>
    </div>
    <!-- END SIDEBAR -->
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <div class="page-content">
            <!-- END STYLE CUSTOMIZER -->
            <!-- BEGIN PAGE HEADER-->

            <div>
                <button type="button" id="graphBtn" class="btn default" style="margin-left: 10%;"
                        onclick="graphView();">
                    Graph
                    View
                </button>
                <button type="button" id="tableBtn" class="btn default" style="margin-left: 5%;" onclick="tableView();">
                    Table View
                </button>
                <select id="sort" style="margin-left: 5%;" onchange="sortFunc();">
                    <option selected disabled>Sort Options</option>
                    <option value="semester" style="color: #FFFFFF ; background-color: #000000 ;">Sort by Semester
                    </option>
                    <option value="module" style="color: #FFFFFF;background-color:#000000;" selected>Sort by
                        Module
                    </option>
                    <option value="date" style="color: #FFFFFF ; background-color: #000000 ;">Sort by Date</option>
                    <option value="absentday" style="color: #FFFFFF ; background-color: #000000 ;">Absent by Specific
                        Day
                    </option>
                    <option value="absentmod" style="color: #FFFFFF ; background-color: #000000 ;">Absent by Module
                    </option>
                    <option value="absentweek" style="color: #FFFFFF ; background-color: #000000 ;">Absent by Week
                    </option>
                </select>

                <div id="tableStudent">
                    <div class="row">
                        <div class="col-md-6">

                            <!-- BEGIN SAMPLE TABLE PORTLET-->
                            <div class="portlet box blue" style="width: 700px;">
                                <div class="portlet-title" style="width: inherit; ">
                                    <div class="caption">
                                        <i class="fa fa-cogs"></i>Attendance
                                    </div>
                                    <div class="tools">
                                        <a href="javascript:;" class="collapse">
                                        </a>
                                        <a href="#portlet-config" data-toggle="modal" class="config">
                                        </a>
                                        <a href="javascript:void(0)" class="reload">
                                        </a>
                                        <a href="" class="fullscreen">
                                        </a>
                                        <a href="javascript:;" class="remove">
                                        </a>
                                    </div>
                                </div>
                                <div class="portlet-body" style="overflow: scroll; height: 500px; width: 700px;">
                                    <table class="table table-hover" id="my">
                                        <thead>
                                        <tr>
                                            <th>Student ID</th>
                                            <th>Last Name</th>
                                            <th>First Name</th>
                                            <th>Date Taken</th>
                                            <th>Session</th>
                                            <th>Status</th>
                                        </tr>
                                        </thead>
                                        <tbody id="tableTbody"></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%--end table--%>
                <div id="graph" style="display: none;">
                    <!-- BEGIN CHART PORTLET-->
                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="icon-bar-chart font-green-haze"></i>
                                <span class="caption-subject bold uppercase font-green-haze"> Bar Charts</span>
                                <span class="caption-helper">column and line mix</span>
                            </div>
                            <div class="tools">
                                <a href="javascript:;" class="collapse">
                                </a>
                                <a href="#portlet-config" data-toggle="modal" class="config">
                                </a>
                                <a href="javascript:;" class="reload">
                                </a>
                                <a href="javascript:;" class="fullscreen">
                                </a>
                                <a href="javascript:;" class="remove">
                                </a>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div id="chart_1" class="chart" style="height: 500px;">
                            </div>
                        </div>
                    </div>
                    <!-- END CHART PORTLET-->

                </div>
                <div id="staffTable">
                    <div class="portlet box blue">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa fa-edit"></i>Manage Staff Table
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div style="height: 400px; overflow: scroll;">
                                <table class="table table-striped table-hover table-bordered"
                                       id="editableStaffTable">
                                    <thead>
                                    <tr>
                                        <th> First Name</th>
                                        <th> Last Name</th>
                                        <th> Email</th>
                                        <th> Date of Birth</th>
                                        <th> Category</th>
                                        <th> Gender</th>
                                        <th> Mobile</th>
                                        <th> Edit</th>
                                        <th> Delete</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
            <div id="workPlace">

                <div id="dataEntry">

                    <div style="width: 40%; float: left; height: 100%">
                        <form id="inputs">
                            <div style="display: inline; text-align: center; color: darkred;">
                                <b id="title"></b>
                            </div>
                            <br> <br>

                            <div id="I1" style="display: inline; ">
                                <label id="lbl1" style="width: 20%" for="input1"> </label><br>
                                <input class="form-control" type="text" id="input1" name="input1" title=""/>
                            </div>
                            <br><br>

                            <div id="I2" style="display: inline">
                                <label id="lbl2" style="width: 20%" for="input2"> </label><br>
                                <input class="form-control" type="text" id="input2"/>
                            </div>

                            <br><br>

                            <div id="I3" style="display: inline">

                                <label id="lbl3" style="width: 20%" for="input3"> </label><br>
                                <input class="form-control" type="text" id="input3"/>
                            </div>

                            <br><br>

                            <div id="I4" style="display: inline">
                                <label id="lbl4" style="width: 20%" for="inputGender"> </label><br>
                                <select class="layout-style-option form-control input-small" id="inputGender"
                                        name="drop">
                                    <option value="Male" selected></option>
                                    <option value="Female"></option>
                                </select>
                            </div>

                            <br><br>

                            <div id="I5" style="display: inline">

                                <%--<label id="labelSelectProgram" style="width: 30%" for="selectProgram"> </label>--%>


                                <label id="lbl5" style="width: 20%" for="input5"> </label><br>
                                <input class="form-control" type="text" id="input5"/>
                                <select class="layout-style-option form-control input-small"
                                        id="selectProgram" name="drop" style="display: none">
                                </select>
                                <select id="selectModule" name="drop" style="display: none">

                                </select>
                            </div>

                            <br><br>

                            <div id="I6" style="display: inline">
                                <label id="lbl6" style="width: 20%" for="drop"> </label><br>
                                <select class="layout-style-option form-control input-small" id="drop" name="drop">
                                    <option value="absent"></option>
                                    <option value="present" selected></option>
                                </select>
                            </div>
                            <br><br>

                        </form>
                    </div>
                    <div id="module" style="width: 60%; float: right; height: 100%; display:none;">
                        <br><br>

                        <div id="I7" style="display: inline">
                            <label id="lbl7" style="width: 20%" for="input7"> </label>
                            <input class="form-control" type="date" id="input7"/>

                        </div>
                        <br> <br>

                        <div id="I8" style="display: inline">
                            <label id="lbl8" style="width: 10%" for="session"> </label>
                            <select id="session" class="layout-style-option form-control input-small">
                                <option value="Lecture" selected>Lecture</option>
                                <option value="Lab">Lab</option>
                                <option value="Both">Both</option>
                            </select>
                        </div>
                        <br><br>
                        <div id="I9" style="display: inline">
                            <label id="lbl9" style="width: 20%" for="userName"> </label>
                            <input class="form-control" type="text" id="userName"/>

                        </div>
                        <br> <br>

                        <div id="I10" style="display: inline">
                            <label id="lbl10" style="width: 10%" for="userPassword"> </label>
                            <input class="form-control" type="password" id="userPassword"/>
                            <br><br>
                        </div>
                        <button class="btn default" type="button" id="submitButton" style="margin-left: 5%"
                                onclick="submitClick();"></button>
                        <br><br>
                        <select id="studentSelect" size="15" class="layout-style-option form-control input-small">
                            <option disabled> Select Student</option>
                        </select>
                    </div>
                </div>
            </div>


        </div>
    </div>
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<div class="page-footer">
    <div class="page-footer-inner">
        2014 &copy; Metronic by keenthemes. <a
            href="http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes"
            title="Purchase Metronic just for 27$ and get lifetime updates for free" target="_blank">Purchase
        Metronic!</a>
    </div>
    <div class="scroll-to-top">
        <i class="icon-arrow-up"></i>
    </div>
</div>
<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../assets/global/plugins/respond.min.js"></script>
<script src="../assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="../js_scripts/lib/md5.js" type="text/javascript"/>
<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="../assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
        type="text/javascript"></script>
<script src="../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
        type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="../assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="../assets/global/plugins/amcharts/amcharts/amcharts.js" type="text/javascript"></script>
<script src="../assets/global/plugins/amcharts/amcharts/serial.js" type="text/javascript"></script>
<script src="../js_scripts/index.js" type="text/javascript"></script>
<script src="../js_scripts/homeJavaScript.js" type="text/javascript"></script>

<%--<script src="../js_scripts/table-editable.js" type="text/javascript"></script>--%>

<script>
    jQuery(document).ready(function () {
        Metronic.init(); // init metronic core components
        Layout.init(); // init current layout
//        QuickSidebar.init(); // init quick sidebar
//        Demo.init(); // init demo features
//        TableEditable.init();
    });


</script>
<script>
    var lecturerId =${lecturerId}, lecturerCategory = "${lecturerCategory}";
</script>
<!-- END JAVASCRIPTS -->

</body>
</html>
