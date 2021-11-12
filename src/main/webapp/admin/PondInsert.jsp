<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Pond Insert</title>
</head>
<body onload="getPopup();openForm()">
    <c:if test="${message != null}">
        <div class="form-popup" id="myForm1" onclick="closeForm()">
            <div class="form-container">
                <div class="pop">
                    <h2 id="popupname">${message}</h2>
                </div>
            </div>
        </div>
    </c:if>
    <div class="page-wrapper">

        <div class="page-breadcrumb bg-white">
            <div class="row align-items-center">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">Thêm mới hồ cá</h4>
                </div>

            </div>
        </div>
        <div class="container-fluid" style="width: 90%">
            <form id="myForm" method="post" action="PondInsert">
                <div id="showMessage" class="alert alert-danger hiddenField" role="alert"></div>

                <div class="mb-3">
                    <label for="pondName" class="col-form-label">Tên hồ:</label>
                    <input type="text" class="form-control" id="pondName" name="pondName" required oninvalid="this.setCustomValidity('Vui lòng nhập tên')"
                           oninput="setCustomValidity('')">
                </div>
                <div class="mb-3">
                    <label for="pondAddress" class="col-form-label">Địa chỉ:</label>
                    <input type="text" class="form-control" id="pondAddress" name="pondAddress">
                </div>
                <div class="mb-3">
                    <label for="pondPhone" class="col-form-label">Số điện thoại:</label>
                    <input type="tel" class="form-control" id="pondPhone" name="pondPhone" pattern="[0-9]{10}" oninvalid="this.setCustomValidity('Vui lòng nhập SĐT 10 số')"
                           oninput="setCustomValidity('')">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Thêm mới</button>
                </div>
            </form>

        </div>
    </div>
    
    <!-- Footer -->
    <!-- ============================================================== -->

    <script src="../public/js/validate.js"></script>
</body>
</html>