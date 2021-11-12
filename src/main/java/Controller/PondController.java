package Controller;

import dao.FishDao;
import dao.PondDao;
import model.Pond;
import validate.DuplicateException;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class PondController
 */
//@WebServlet("/PondController")
@WebServlet( urlPatterns={"/admin/PondIndex","/admin/ShowPondInsert","/admin/ShowPondEdit","/admin/PondDelete","/admin/PondCheck","/admin/PondInsert","/admin/PondEdit"})
public class PondController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PondDao pondDao = new PondDao();
	FishDao fishDao = new FishDao();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getServletPath();
		switch(action) {
		case "/admin/PondInsert":
			insertPond(request, response);
			break;
		case "/admin/PondEdit":
			editPond(request, response);
			break;
		case "/admin/PondCheck":
			getPondCheck(request, response);
			break;
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch(action) {
		case "/admin/PondIndex":
			getPaginate(request, response);
			break;
		case "/admin/ShowPondEdit":
			getEdit(request, response);
			break;
		case "/admin/ShowPondInsert":
			response.sendRedirect("PondInsert.jsp");
			break;

		case "/admin/PondDelete":
			getDelete(request, response);
			break;
		}
		
	}
	
	// api kiểm tra tên đã tồn tại hay chưa
	protected void getPondCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		if(pondDao.checkPond(name, id)) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().write("Tên đã tồn tại");
		} else {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().write("");
		}
	}
	
	protected void getPaginate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = null;
		float totalRecords;
		int maxPage;
		final int ITEM_PER_PAGE = 3;
		String searchString = request.getParameter("searchString");
		if(request.getParameter("searchString") != null) {
			search = request.getParameter("searchString");
			totalRecords = (float) pondDao.countTotalSearchRecords(search);
			maxPage = (int) Math.ceil((totalRecords)/ITEM_PER_PAGE);
		} else {
			totalRecords = (float) pondDao.countTotalRecords();
			maxPage = (int) Math.ceil((totalRecords)/ITEM_PER_PAGE);
		}
		
		int page = 0;
		if (request.getParameter("page") != null) {
			page =  Integer.parseInt(request.getParameter("page"));
		} else {
			page = 1;
		}
		int start = (page-1) * ITEM_PER_PAGE ;
		List<Pond> pondList = pondDao.getPondPaginate(page-1,search, ITEM_PER_PAGE);
		request.setAttribute("searchString",searchString);
		request.setAttribute("page", page);
		request.setAttribute("start", start);
		request.setAttribute("listPond", pondList);
    	request.setAttribute("maxPage", maxPage);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("PondIndex.jsp");
    	dispatcher.forward(request, response);
	}
	
	protected void getEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int pondID = Integer.parseInt(request.getParameter("id"));
		Pond editPond = pondDao.getEditPond(pondID);
		request.setAttribute("editPond", editPond);
		RequestDispatcher dispatcher = request.getRequestDispatcher("PondEdit.jsp");
    	dispatcher.forward(request, response);
	}
	
	protected void getDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int pondId = Integer.parseInt(request.getParameter("id"));
		try {
			pondDao.deletePond(pondId);
			request.setAttribute("message", "Xóa thành công");
			getPaginate(request, response);
			
		} catch (Exception e) {
			request.setAttribute("message", e.getMessage());
			getPaginate(request, response);
		}
	}
	
	protected void insertPond(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		String pondName = request.getParameter("pondName");
		String pondAddress = request.getParameter("pondAddress");
		String pondPhone = request.getParameter("pondPhone");

		Pond pond = new Pond(pondName,pondAddress, pondPhone);
		pondDao.insertPond(pond);
		
		request.setAttribute("message", "Thêm mới thành công");
		getPaginate(request, response);
	}
	
	protected void editPond(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String pondName = request.getParameter("pondName");
		String pondAddress = request.getParameter("pondAddress");
		String pondPhone = request.getParameter("pondPhone");
		
		try {
			Pond pond = new Pond(id,pondName, pondAddress, pondPhone);
			pondDao.editPond(pond);
			request.setAttribute("message", "Cập nhật thành công");
		} catch (DuplicateException e) {
			request.setAttribute("message", e.getMessage());
		}
		getPaginate(request, response);
	}

}
