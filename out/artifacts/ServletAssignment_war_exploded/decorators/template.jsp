<%--
  Created by IntelliJ IDEA.
  User: vuong
  Date: 11/12/2021
  Time: 10:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<html>
<head>
    <title><decorator:title /></title>
    <jsp:include page="head.jsp" />
    <decorator:head />
</head>
<body onload="getPopup();openForm()">
    <div class="preloader">
        <div class="lds-ripple">
            <div class="lds-pos"></div>
            <div class="lds-pos"></div>
        </div>
    </div>
    <div id="main-wrapper" data-layout="vertical" data-navbarbg="skin5" data-sidebartype="full"
         data-sidebar-position="absolute" data-header-position="absolute" data-boxed-layout="full">

        <jsp:include page="header2.jsp" />

        <!-- ============================================================== -->
        <!-- End header  -->
        <decorator:body />

    </div>

<jsp:include page="footer.jsp" />
</body>
</html>
