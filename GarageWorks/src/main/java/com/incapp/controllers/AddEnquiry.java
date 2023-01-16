package com.incapp.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.incapp.dao.UserDao;

/**
 * Servlet implementation class AddEnquiry
 */
@WebServlet("/AddEnquiry")
public class AddEnquiry extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name=request.getParameter("Name");
			String phone=request.getParameter("Phone");
			String email=request.getParameter("Email");
			
			UserDao db=new UserDao();
			db.setEnquiry(name, phone, email);
			db.disconnect();
			
			HttpSession session=request.getSession();
			session.setAttribute("msg", "Enquiry Submitted Successfully!");
			response.sendRedirect("index.jsp");
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd=request.getRequestDispatcher("ExpPage.jsp");
			rd.forward(request, response);
		}
	}

}
