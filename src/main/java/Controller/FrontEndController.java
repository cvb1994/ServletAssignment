package Controller;

import dao.FishDao;
import dao.PondDao;
import model.Fish;
import model.Pond;
import utils.DBGenerate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class FrontEndController
 */
//@WebServlet("/Index")
//@WebServlet( urlPatterns={"","/Index"})
public class FrontEndController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	FishDao fishDao = new FishDao();
	PondDao pondDao = new PondDao();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBGenerate db = new DBGenerate();
		try {
			getPaginate(request, response);
		} catch (ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void getPaginate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String search = null;
		List<Integer> listPondId = null;
		final int ITEM_PER_PAGE = 8;
		float totalRecords;
		int maxPage;
		if(request.getParameter("searchString") != null) {
			search = request.getParameter("searchString");
			listPondId = pondDao.getArrayPondId(search);
			totalRecords = (float) fishDao.countTotalSearchRecords(search, listPondId);
			maxPage = (int) Math.ceil((totalRecords)/ITEM_PER_PAGE);
		} else {
			totalRecords = (float) fishDao.countTotalRecords();
			maxPage = (int) Math.ceil((totalRecords)/ITEM_PER_PAGE);
		}
		
		int page = 0;
		if (request.getParameter("page") != null) {
			page =  Integer.parseInt(request.getParameter("page"));
		} else {
			page = 1;
		}
		
		List<Pond> listPond = pondDao.getListPond();
		if(listPond!=null) {
			request.setAttribute("listPond", listPond);
		}
		
		List<Fish> fishList = fishDao.getFishPaginate(page-1,search, listPondId,ITEM_PER_PAGE);
		request.setAttribute("page", page);
		request.setAttribute("listFish", fishList);
    	request.setAttribute("maxPage", maxPage);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("Fish.jsp");
    	dispatcher.forward(request, response);
	}
}
