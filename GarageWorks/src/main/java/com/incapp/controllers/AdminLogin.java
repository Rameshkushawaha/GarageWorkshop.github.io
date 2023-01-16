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

import com.incapp.dao.AdminDao;

/**
 * Servlet implementation class AddEnquiry
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String id=request.getParameter("id");
			String password=request.getParameter("password");
			AdminDao db=new AdminDao();
			boolean r=db.getAdminLogin(id, password);
			db.disconnect();
			HttpSession session=request.getSession();
			if(r==true) {
				session.setAttribute("aname", "Admin");
				response.sendRedirect("AdminHome.jsp");
			}else {
				session.setAttribute("msg", "invalid");
				response.sendRedirect("Admin.jsp");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd=request.getRequestDispatcher("ExpPage.jsp");
			rd.forward(request, response);
		}
	}

}
