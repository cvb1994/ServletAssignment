<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Fish Insert</title>
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
                    <h4 class="page-title">Thêm mới cá</h4>
                </div>

            </div>
        </div>
        <div class="container-fluid">
            <form id="myForm" method="post" action="FishInsert" enctype="multipart/form-data">
                <div id="fishNameMessage" class="alert alert-danger hiddenField" role="alert"></div>
                <div class="mb-3">
                    <label for="fishName" class="col-form-label">Tên loài cá:</label>
                    <input type="text" class="form-control" id="fishName" name="fishName" required oninvalid="this.setCustomValidity('Vui lòng nhập tên')"
                           oninput="setCustomValidity('')">
                </div>
                <div id="fishSizeMesage" class="alert alert-danger hiddenField" role="alert"></div>
                <div class="mb-3">
                    <label for="minSize" class="col-form-label">Trọng lượng tối thiểu (Kg):</label>
                    <input type="text" class="form-control" id="minSize" name="minSize">
                </div>
                <div class="mb-3">
                    <label for="maxSize" class="col-form-label">Trọng lượng tối đa (Kg):</label>
                    <input type="text" class="form-control" id="maxSize" name="maxSize">
                </div>
                <div class="mb-3">
                    <label for="fishImage" class="col-form-label">Ảnh mô tả:</label>
                    <input type="file" accept=".jpg,.jpeg,.png" class="form-control" id="fishImage" name="fishImage">
                </div>
                <div id="fishAmountMessage" class="alert alert-danger hiddenField" role="alert"></div>
                <div class="mb-3">
                    <label for="amount" class="col-form-label">Số lượng:</label>
                    <input type="number" class="form-control" id="amount" name="amount" required oninvalid="this.setCustomValidity('Vui lòng nhập số lượng')"
                           oninput="setCustomValidity('')">
                </div>
                <div class="mb-3">
                    <label for="pondNameSelect" class="col-form-label">Chọn hồ cá:</label>
                    <select class="form-control" id="pondNameSelect" name="pondId" required>
                        <c:forEach var="pond" items="${listPond }">
                            <option value="${pond.id }" >${pond.pondName }</option>
                        </c:forEach>
                    </select>
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
    <script>
    $("#fishImage").change(function(){
    	let reader = new FileReader();
	       var fileName = this.files[0].name;
	       var fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
	       if((fileExtension == "jpg") || (fileExtension == "png") || (fileExtension == "jpeg")){
	  	     	$("#showMessage").addClass("fade");
				$("button").removeAttr("disabled");
	       } else {
	    	   $("#showMessage").removeClass("fade");
				$("#showMessage").text("Ảnh không hợp lệ");
				$("button").attr("disabled", true);
	       }
    });
    </script>
    
</body>
</html>