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

import com.incapp.dao.GarageDao;

/**
 * Servlet implementation class AddEnquiry
 */
@WebServlet("/AddGarageOwner")
@MultipartConfig
public class AddGarageOwner extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name=request.getParameter("name");
			String gname=request.getParameter("gname");
			String phone=request.getParameter("phone");
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			String state=request.getParameter("state");
			String city=request.getParameter("city");
			String sec_vill=request.getParameter("sec_vill");
			String shop_no=request.getParameter("shop_no");
			InputStream photo1=null;
			Part p1=request.getPart("photo1");
			if(p1!=null) {
				photo1=p1.getInputStream();
			}
			InputStream photo2=null;
			Part p2=request.getPart("photo2");
			if(p2!=null) {
				photo2=p2.getInputStream();
			}
			HashMap garage=new HashMap();
			garage.put("name", name);
			garage.put("gname", gname);
			garage.put("phone", phone);
			garage.put("email", email);
			garage.put("password", password);
			garage.put("state", state);
			garage.put("city", city);
			garage.put("sec_vill", sec_vill);
			garage.put("shop_no", shop_no);
			garage.put("photo1", photo1);
			garage.put("photo2", photo2);
			
			GarageDao db=new GarageDao();
			String result=db.setGarageDao(garage);
			db.disconnect();
			
			HttpSession session=request.getSession();
			session.setAttribute("msg", result);
			response.sendRedirect("GarageOwner.jsp");
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd=request.getRequestDispatcher("ExpPage.jsp");
			rd.forward(request, response);
		}
	}

}
