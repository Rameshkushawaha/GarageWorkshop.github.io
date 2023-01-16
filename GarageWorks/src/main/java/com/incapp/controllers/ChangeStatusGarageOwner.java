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
@WebServlet("/ChangeStatusGarageOwner")
@MultipartConfig
public class ChangeStatusGarageOwner extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email=request.getParameter("email");
			String status=request.getParameter("status");
			
			HttpSession session=request.getSession();
			
			AdminDao db=new AdminDao();
			int result=db.updateGarageStatus(email,status);
			db.disconnect();
			if(result!=0) {
				String r;
				if(status.equalsIgnoreCase("accept")) {
					r=mailcode.SendMailCode.sendMail(email, "Status Updated", "Your Garage has been Activated!");
				}else {
					r=mailcode.SendMailCode.sendMail(email, "Status Updated", "Your Garage has been Rejected!");
				}
				session.setAttribute("msg", "Status Updated Successfully! "+r);
			}else {
				session.setAttribute("msg", "Status Updation Failed! ");
			}
			response.sendRedirect("AdminHome.jsp");
			
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd=request.getRequestDispatcher("ExpPage.jsp");
			rd.forward(request, response);
		}
	}

}
