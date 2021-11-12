<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Fish" %>
<%@ page import="model.Pond" %>
<% 
	String active = "active";
	int maxPage = (int) request.getAttribute("maxPage");
	int currentPage = (int) request.getAttribute("page");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Index</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400" rel="stylesheet" />
	<link href="./public/css/templatemo-style.css" rel="stylesheet" />
	<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script> -->
</head>
<!--

Simple House

https://templatemo.com/tm-539-simple-house

-->
<body>

	<div class="container">
	<!-- Top box -->
		<!-- Logo & Site Name -->
		<div class="placeholder">
			<div class="parallax-window" data-parallax="scroll" data-image-src="./public/images/banner.png">
				<div class="tm-header">
					<div class="row tm-header-inner">
						<div class="col-md-6 col-12">
							<img src="./public/images/logo.jpg" alt="Logo" class="tm-site-logo" width="60" height="60"/>
							<div class="tm-site-text-box">
								<h1 class="tm-site-title">Mavin Group</h1>
							</div>
						</div>
						<nav class="col-md-6 col-12 tm-nav">
							<ul class="tm-nav-ul">
								<li class="tm-nav-li"><a href="login.jsp" class="tm-nav-link">Đăng nhập</a></li>
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>

		<main>
			<header class="row tm-welcome-section">
				<h2 class="col-12 text-center tm-section-title"></h2>
			</header>

			<!-- Gallery -->
			<div class="row tm-gallery">
				<!-- gallery page 1 -->
				<div id="tm-gallery-page-pizza" class="tm-gallery-page">
				<%
				List<Fish> listFish = (List<Fish>) request.getAttribute("listFish");
            	List<Pond> listPond = (List<Pond>) request.getAttribute("listPond");
            	for (Fish fish : listFish){
            		%>
            		<article class="col-lg-3 col-md-4 col-sm-6 col-12 tm-gallery-item">
						<figure >
							<div style="height:200px">
								<img src="<%=fish.getImage()%>" alt="Image" class="img-fluid tm-gallery-img" style="height:200px"/>
							</div>
							<figcaption>
								<h4 class="tm-gallery-title"><%=fish.getName() %></h4>
								<p class="tm-gallery-description">Kích thước: <%=fish.getMin_size() %> - <%=fish.getMax_size() %> Kg</p>
								<p class="tm-gallery-description">Số lượng: <%=fish.getAmount() %></p>
								<%
                               		for (Pond item : listPond){
                                		if(item.getId() == fish.getPond().getId()){
                                			%>
                                				<p class="tm-gallery-description"><%=item.getPondName() %></p>
                                			<%
                                		}
                                	}
                                %>
							</figcaption>
						</figure>
					</article>
            		<%
            	}
				%>
				</div> <!-- gallery page 1 -->
				
				<div class="container">
					<div class="center" style="text-align:center">
					  <div class="pagination">
					  <a class="<% if(currentPage==1){out.print("disabled");}%>" href="Index?page=<%=currentPage-1%>">&laquo;</a>
						  <%
					    	if(currentPage == 1){
					    		for (int i=0;i<3;i++){
					    			%>
					    			<a class="<% if(i==0){out.print(active);}%>" href="Index?page=<%=currentPage%>"><%=currentPage%></a>
					    			<%
					    			currentPage++;
					    			if(currentPage > maxPage){
					    				break;
					    			}
					    		}
					    	} else {
					    		int temPage = currentPage -1;
					    		for (int i=0;i<3;i++){
					    			%>
					    			<a class="<% if(i==1){out.print(active);}%>" href="Index?page=<%=temPage%>"><%=temPage%></a>
					    			<%
					    			temPage++;
					    			if(temPage > maxPage){
					    				break;
					    			}
					    		}
					    	}
					    %>
					 
					  <a class="<% if(currentPage==maxPage){out.print("disabled");}%>" href="Index?page=<%=currentPage+1 %>">&raquo;</a>
					  </div>
					</div>
				</div>
				
				
	            <!--<nav aria-label="Page navigation example">
				  <ul class="pagination justify-content-center">
				    <li class="page-item <% if(currentPage==1){out.print("disabled");}%>"><a class="page-link" href="Index?page=<%=currentPage-1%>">Previous</a></li>
				    <%
				    	if(currentPage == 1){
				    		for (int i=0;i<3;i++){
				    			%>
				    			<li class="page-item <% if(i==0){out.print(active);}%>"><a class="page-link" href="Index?page=<%=currentPage%>"><%=currentPage%></a></li>
				    			<%
				    			currentPage++;
				    			if(currentPage > maxPage){
				    				break;
				    			}
				    		}
				    	} else {
				    		int temPage = currentPage -1;
				    		for (int i=0;i<3;i++){
				    			%>
				    			<li class="page-item <% if(i==1){out.print(active);}%>"><a class="page-link" href="Index?page=<%=temPage%>"><%=temPage%></a></li>
				    			<%
				    			temPage++;
				    			if(temPage > maxPage){
				    				break;
				    			}
				    		}
				    	}
				    %>
				    <li class="page-item <% if(currentPage==maxPage){out.print("disabled");}%>"><a class="page-link" href="Index?page=<%=currentPage+1 %>">Next</a></li>
				  </ul>
				</nav>  -->
			</div>
		</main>
	</div>
	<script src="./public/js/jquery.min.js"></script>
	<script src="./public/js/parallax.min.js"></script>
	<script>
		$(document).ready(function(){
			// Handle click on paging links
			$('.tm-paging-link').click(function(e){
				e.preventDefault();

				var page = $(this).text().toLowerCase();
				$('.tm-gallery-page').addClass('hidden');
				$('#tm-gallery-page-' + page).removeClass('hidden');
				$('.tm-paging-link').removeClass('active');
				$(this).addClass("active");
			});
		});
	</script>
</body>
</html>
    