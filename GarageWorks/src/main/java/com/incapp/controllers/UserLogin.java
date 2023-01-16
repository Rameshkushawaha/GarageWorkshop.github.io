package com.incapp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.incapp.dao.UserDao;

/**
 * Servlet implementation class AddEnquiry
 */
@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			UserDao db=new UserDao();
			HashMap userDetails=db.getUserLogin(email,password);
			db.disconnect();
			HttpSession session=request.getSession();
			if(userDetails!=null) {
				session.setAttribute("userDetails", userDetails);
				response.sendRedirect("UserHome.jsp");
			}else {
				session.setAttribute("msg", "invalid");
				response.sendRedirect("User.jsp");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd=request.getRequestDispatcher("ExpPage.jsp");
			rd.forward(request, response);
		}
	}

}
