package com.incapp.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.incapp.dao.GarageDao;

/**
 * Servlet implementation class AddEnquiry
 */
@WebServlet("/AddService")
public class AddService extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email=request.getParameter("email");
			String service=request.getParameter("service");
			
			GarageDao db=new GarageDao();
			db.setService(email, service);
			db.disconnect();
			
			HttpSession session=request.getSession();
			session.setAttribute("msg", "Service Added Successfully!");
			response.sendRedirect("GarageOwnerHome.jsp");
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd=request.getRequestDispatcher("ExpPage.jsp");
			rd.forward(request, response);
		}
	}

}
