package Controller;

import dao.UserDao;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class LoginController
 */
//@WebServlet("/LoginController")
@WebServlet( urlPatterns={"/LoginController","/admin/LoginController"})
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao userDao = new UserDao();
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String userName = request.getParameter("username");
		String pass = request.getParameter("pass");
		
		String name = userDao.checkAuth(userName, pass);
		
		if(!name.equals("")) {
        	HttpSession session = request.getSession();
        	session.setAttribute("userName", name);
        	
        	response.sendRedirect("admin/PondIndex");
        	
        } else {
        	request.setAttribute("mess", "Tên tài khoản hoặc mật khẩu không đúng");
        	RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        	dispatcher.forward(request, response);
        }
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("userName");
		response.sendRedirect("Index");
	}
}
