package Controller;

import dao.FishDao;
import dao.PondDao;
import model.Fish;
import model.Pond;
import utils.Utilities;
import validate.FileTypeException;
import validate.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 * Servlet implementation class FishController
 */
//@WebServlet("/FishController")
@WebServlet( urlPatterns={"/admin/FishIndex","/admin/ShowFishInsert","/admin/ShowFishEdit","/admin/FishDelete","/admin/FishCheck","/admin/FishInsert",
		"/admin/FishEdit","/admin/FishController"})
@MultipartConfig
public class FishController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	FishDao fishDao = new FishDao();
	Validate validate = new Validate();
	Utilities utils = new Utilities();
	PondDao pondDao = new PondDao();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch(action) {
		case "/admin/ShowFishInsert":
			try {
				getInsert(request, response);
			} catch (SQLException | ServletException | IOException e) {
			}
			break;
		case "/admin/ShowFishEdit":
			try {
				getEdit(request, response);
			} catch (ServletException | IOException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "/admin/FishIndex":
			try {
				getPaginate(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/admin/FishDelete":
			try {
				getDelete(request, response);
			} catch (IOException | ServletException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getServletPath();
		switch(action) {
		case "/admin/FishInsert":
			try {
				insertFish(request, response);
			} catch (IOException | ServletException | FileTypeException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/admin/FishEdit":
			try {
				editFish(request, response);
			} catch (IOException | ServletException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/admin/FishCheck":
			getFishCheck(request, response);
			break;
		}
	}
	
	protected void getInsert(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int pondCount = pondDao.countTotalRecords();
		if (pondCount == 0) {
			request.setAttribute("message", "Vui lòng thêm mới hồ cá trước");
			RequestDispatcher dispatcher = request.getRequestDispatcher("FishIndex");
	    	dispatcher.forward(request, response);
		} else {
			List<Pond> listPond = pondDao.getListPond();
			request.setAttribute("listPond", listPond);
			RequestDispatcher dispatcher = request.getRequestDispatcher("FishInsert.jsp");
	    	dispatcher.forward(request, response);
		}
	}
	
	// API kiểm tra trùng lặp trong hồ cá
	protected void getFishCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fishName = request.getParameter("fishName");
		int pondId = Integer.parseInt(request.getParameter("pondId"));
		int fishId = Integer.parseInt(request.getParameter("id"));
		if(fishDao.checkFish(fishName, pondId, fishId)) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().write("Tên đã tồn tại. Vui lòng chọn tên hoặc hồ cá khác");
		} else {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().write("");
		}
	}
	
	protected void getPaginate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String search = null;
		List<Integer> listPondId = null;
		float totalRecords;
		final int ITEM_PER_PAGE = 3;
		int maxPage;
		String searchString = request.getParameter("searchString");
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
		int start = (page-1) * ITEM_PER_PAGE ;
		List<Fish> fishList = fishDao.getFishPaginate(page-1,search, listPondId,ITEM_PER_PAGE);
		request.setAttribute("searchString",searchString);
		request.setAttribute("page", page);
		request.setAttribute("start", start);
		request.setAttribute("listFish", fishList);
    	request.setAttribute("maxPage", maxPage);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("FishIndex.jsp");
    	dispatcher.forward(request, response);
	}
	
	protected void getEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int fishId = Integer.parseInt(request.getParameter("id"));
		Fish editFist = fishDao.getEditFish(fishId);
		request.setAttribute("editFish", editFist);
		List<Pond> listPond = pondDao.getListPond();
		request.setAttribute("listPond", listPond);
		RequestDispatcher dispatcher = request.getRequestDispatcher("FishEdit.jsp");
    	dispatcher.forward(request, response);
	}
	
	protected void getDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
		int fishId = Integer.parseInt(request.getParameter("id"));
		String message = fishDao.deleteFish(fishId);
		request.setAttribute("message", message);
		RequestDispatcher dispatcher = request.getRequestDispatcher("FishIndex");
    	dispatcher.forward(request, response);
	}

	protected void insertFish(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, FileTypeException, SQLException {
		request.setCharacterEncoding("utf-8");
		String fishName = request.getParameter("fishName");
		float minSize = Float.parseFloat(request.getParameter("minSize"));
		float maxSize = Float.parseFloat(request.getParameter("maxSize"));
		
		Part filePath = request.getPart("fishImage");
		String fileName = Paths.get(filePath.getSubmittedFileName()).getFileName().toString();
		String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
	    InputStream fileContent = filePath.getInputStream();
	    String rootPath = getServletContext().getRealPath(getServletInfo());
	    String projectName = request.getContextPath();
	    int pondID = Integer.parseInt(request.getParameter("pondId"));
	    int amount = Integer.parseInt(request.getParameter("amount"));
	    Pond pond = pondDao.getPond(pondID);
	    if (validate.FileTypeValidate(fileExtension)) {
	    	String imagePath = utils.copyFile(fileContent,fileExtension,rootPath,projectName);

	    	Fish fish = new Fish(0,fishName,minSize,maxSize,imagePath,pond,amount);
	    	String message = fishDao.insertFish(fish);
	    	request.setAttribute("message",message);
	    } 
	    getPaginate(request, response);
	}
	
	protected void editFish(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
		request.setCharacterEncoding("utf-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String fishName = request.getParameter("fishName");
		float minSize = Float.parseFloat(request.getParameter("minSize"));
		float maxSize = Float.parseFloat(request.getParameter("maxSize"));
		String imagePath = null;
		Part filePath = request.getPart("fishImage");
		String fileName = Paths.get(filePath.getSubmittedFileName()).getFileName().toString();
		int pondID = Integer.parseInt(request.getParameter("pondId"));
	    int amount = Integer.parseInt(request.getParameter("amount"));
		Pond pond = pondDao.getPond(pondID);
		if (!(fileName.equals(""))) {
			String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
		    InputStream fileContent = filePath.getInputStream();
		    String rootPath = getServletContext().getRealPath(getServletInfo());
		    String projectName = request.getContextPath();
		    if (validate.FileTypeValidate(fileExtension)) {
		    	imagePath = utils.copyFile(fileContent,fileExtension,rootPath,projectName);
		    	Fish fish = new Fish(id,fishName,minSize,maxSize,imagePath,pond,amount);
		    	String message = fishDao.editFish(fish);
				request.setAttribute("message", message);
		    }
		} else {
			Fish fish = new Fish(id,fishName,minSize,maxSize,imagePath,pond,amount);
			String message = fishDao.editFish(fish);
			request.setAttribute("message", message);
		}
		getPaginate(request, response);
	}

}
