<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Fish Index</title>
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
				<form id="searchForm" role="search" action="FishIndex">
					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="Search..." name="searchString" value="${searchString}">
						<button class="btn btn-outline-secondary" onclick="document.getElementById('searchForm').submit()" type="button" id="button-addon2"><i class="fa fa-search"></i></button>
					</div>
				</form>

				<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
					<a type="button" class="btn btn-primary" href="ShowFishInsert">Thêm mới</a>
				</div>

			</div>
		</div>
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-12">
					<div class="white-box">
						<div class="table-responsive" style="height: 480px;">
							<table class="table text-nowrap table-bordered">
								<thead>
								<tr>
									<th class="border-top-0" style="width:5%; text-align:center">STT</th>
									<th class="border-top-0" style="width:20%; text-align:center">Tên Loài</th>
									<th class="border-top-0" style="width:15%; text-align:center">Trọng lượng</th>
									<th class="border-top-0" style="width:15%; text-align:center">Ảnh</th>
									<th class="border-top-0" style="width:20%; text-align:center">Hồ Cá</th>
									<th class="border-top-0" style="width:15%; text-align:center">Số lượng</th>
									<th class="border-top-0" style="width:10%; text-align:center">Action</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach var="fish" items="${listFish}"  varStatus="loop" >
									<tr>
										<td class="text-center"><c:out value="${loop.count + start}"/></td>
										<td style="text-align:center">${fish.name}</td>
										<td style="text-align:center">${fish.min_size}- ${fish.max_size} Kg</td>
										<td >
											<img src="${fish.image}" width="200" height="120">
										</td>
										<td style="text-align:center">${fish.pond.pondName}</td>
										<td style="text-align:center">${fish.amount}</td>
										<td class="text-center">
											<div class="btn-group">
												<a href="#" class="list-icons-item" data-toggle="dropdown" data-boundary="viewport" data-bs-toggle="dropdown">
													Action
												</a>
												<div class="dropdown-menu dropdown-menu-right">
													<a class="dropdown-item" href="ShowFishEdit?id=${fish.id}"><span>Edit</span></a>
													<a class="dropdown-item" onclick="return confirm('Bạn có chắc muốn xóa?')" href="FishDelete?id=${fish.id}"><span>Delete</span></a>
												</ul>
											</div>

										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>

		</div>

		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">

				<c:if test="${page != 1 }">
					<li class="page-item "><a class="page-link" href="FishIndex?page=${page -1}">Prev</a></li>
				</c:if>
				<c:forEach begin="1" end="${maxPage }" var="i">
					<c:choose>
						<c:when test="${page eq i }">
							<a class="paginate page-link active">${i }</a>
						</c:when>
					</c:choose>
				</c:forEach>
				<c:if test="${page lt  maxPage  }">
					<li class="page-item "><a class="page-link" href="FishIndex?page=${page + 1}">Next</a></li>
				</c:if>
			</ul>
		</nav>
	</div>
    
    <!-- Footer -->
    <!-- ============================================================== -->

    
</body>
</html>