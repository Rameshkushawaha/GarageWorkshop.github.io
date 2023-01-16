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
@WebServlet("/UserPasswordChange")
@MultipartConfig
public class UserPasswordChange extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email=request.getParameter("email");
			String op=request.getParameter("o_password");
			String np=request.getParameter("n_password");
			String cp=request.getParameter("c_password");
			
			HttpSession session=request.getSession();
			
			if(np.equals(cp)) {
				UserDao db=new UserDao();
				int result=db.updateUserPassword(email,op,np);
				db.disconnect();
				if(result!=0) {
					session.setAttribute("msg", "Password Updated Successfully!");
				}else {
					session.setAttribute("msg", "Old Password is Wrong!");
				}
			}else {
				session.setAttribute("msg", "Password Mismatch!");
			}
			response.sendRedirect("UserProfile.jsp");
			
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd=request.getRequestDispatcher("ExpPage.jsp");
			rd.forward(request, response);
		}
	}

}
