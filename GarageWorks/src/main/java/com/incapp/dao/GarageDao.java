package com.incapp.dao;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;

public class GarageDao {
	private Connection c;
	public GarageDao() throws SQLException,ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		c=DriverManager.getConnection("jdbc:mysql://localhost:3306/garageworks","root","Incapp@12");
	}
	public void disconnect()throws SQLException {
		c.close();
	}
	
	public String setGarageDao(HashMap garage) throws SQLException{
		try {
			PreparedStatement p=c.prepareStatement("insert into garage (email,name,gname,phone,password,state,city,sec_vill,shop_no,photo1,photo2,status) values(?,?,?,?,?,?,?,?,?,?,?,'pending')");
			p.setString(1, (String)garage.get("email"));
			p.setString(2, (String)garage.get("name"));
			p.setString(3, (String)garage.get("gname"));
			p.setString(4, (String)garage.get("phone"));
			p.setString(5, (String)garage.get("password"));
			p.setString(6, (String)garage.get("state"));
			p.setString(7, (String)garage.get("city"));
			p.setString(8, (String)garage.get("sec_vill"));
			p.setString(9, (String)garage.get("shop_no"));
			p.setBinaryStream(10, (InputStream)garage.get("photo1"));
			p.setBinaryStream(11, (InputStream)garage.get("photo2"));
			p.executeUpdate();
			return "success";
		}catch (SQLIntegrityConstraintViolationException e) {
			return "failed";
		}
	}
	public HashMap getGarageOwnerLogin(String email,String password)throws SQLException {
		PreparedStatement p=c.prepareStatement("select * from garage where email=? and password=?");
		p.setString(1, email);
		p.setString(2, password);
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
	public byte[] getGaragePhoto(String email,String photo_no) throws SQLException{
		PreparedStatement p=c.prepareStatement("select photo1,photo2 from garage where email=?");
		p.setString(1, email);
		ResultSet rs=p.executeQuery();
		rs.next();
		byte photo[];
		if(photo_no.equalsIgnoreCase("photo1"))
			photo=rs.getBytes("photo1");
		else
			photo=rs.getBytes("photo2");
		return photo;
	}
	public void setService(String email,String service) throws SQLException{
		PreparedStatement p=c.prepareStatement("insert into services (garage_email,service) values(?,?)");
		p.setString(1, email);
		p.setString(2, service);
		p.executeUpdate();
	}
	public void deleteService(int id) throws SQLException{
		PreparedStatement p=c.prepareStatement("delete from services where id=?");
		p.setInt(1, id);
		p.executeUpdate();
	}
	public ArrayList<HashMap> getGarageServicesByEmail(String email)throws SQLException {
		PreparedStatement p=c.prepareStatement("select * from services where garage_email=? ");
		p.setString(1, email);
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap> allServices=new ArrayList();
		while(rs.next()) {
			HashMap servive=new HashMap();
			servive.put("id",rs.getInt("id"));
			servive.put("service",rs.getString("service"));
			allServices.add(servive);
		}
		return allServices;
	}
}

