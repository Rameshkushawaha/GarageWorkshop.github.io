package com.incapp.dao;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;

public class AdminDao {
	private Connection c;
	public AdminDao() throws SQLException,ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		c=DriverManager.getConnection("jdbc:mysql://localhost:3306/garageworks","root","Incapp@12");
	}
	public void disconnect()throws SQLException {
		c.close();
	}
	
	
	public boolean getAdminLogin(String id,String password)throws SQLException {
		PreparedStatement p=c.prepareStatement("select * from admin where id=? and password=?");
		p.setString(1, id);
		p.setString(2, password);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	public ArrayList<HashMap> getGarageByStatus(String status) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from garage where status=?");
		p.setString(1, status);
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap> allGarage=new ArrayList<HashMap>();
		while(rs.next()) {
			HashMap garageDetails=new HashMap();
			garageDetails.put("name", rs.getString("name"));
			garageDetails.put("gname", rs.getString("gname"));
			garageDetails.put("email", rs.getString("email"));
			garageDetails.put("phone", rs.getString("phone"));
			garageDetails.put("state", rs.getString("state"));
			garageDetails.put("city", rs.getString("city"));
			garageDetails.put("sec_vill", rs.getString("sec_vill"));
			garageDetails.put("shop_no", rs.getString("shop_no"));
			garageDetails.put("status", rs.getString("status"));
			allGarage.add(garageDetails);
		}
		return allGarage;
	}
	public HashMap getGarageByEmail(String email) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from garage where email=?");
		p.setString(1, email);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			HashMap garageDetails=new HashMap();
			garageDetails.put("name", rs.getString("name"));
			garageDetails.put("gname", rs.getString("gname"));
			garageDetails.put("email", rs.getString("email"));
			garageDetails.put("phone", rs.getString("phone"));
			garageDetails.put("state", rs.getString("state"));
			garageDetails.put("city", rs.getString("city"));
			garageDetails.put("sec_vill", rs.getString("sec_vill"));
			garageDetails.put("shop_no", rs.getString("shop_no"));
			garageDetails.put("status", rs.getString("status"));
			return garageDetails;
		}else {
			return null;
		}
	}
	public int updateGarageStatus(String email,String status) throws SQLException{
		PreparedStatement p=c.prepareStatement("update garage set status=? where email=?");
		p.setString(1, status);
		p.setString(2, email);
		int result= p.executeUpdate();
		return result;
	}
	public String[] getAdminDetails() throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from admin");
		ResultSet rs=p.executeQuery();
		String adminDetails[]=new String[2];
		if(rs.next()) {
			adminDetails[0]=rs.getString("id");
			adminDetails[1]=rs.getString("password");
		}
		return adminDetails;
	}
}
