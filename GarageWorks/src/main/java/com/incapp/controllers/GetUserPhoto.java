package com.incapp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
@WebServlet("/GetUserPhoto")
public class GetUserPhoto extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email=request.getParameter("email");
			UserDao db=new UserDao();
			byte photo[]=db.getUserPhoto(email);
			db.disconnect();
			if(photo.length==0) {
				ServletContext ctx=getServletContext();
				InputStream i=ctx.getResourceAsStream("resource/user.png");
				photo=new byte[300000];
				i.read(photo);
			}
			response.getOutputStream().write(photo);
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd=request.getRequestDispatcher("ExpPage.jsp");
			rd.forward(request, response);
		}
	}

}
