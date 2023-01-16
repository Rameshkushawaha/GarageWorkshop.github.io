package com.incapp.dao;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;

public class UserDao {
	private Connection c;
	public UserDao() throws SQLException,ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		c=DriverManager.getConnection("jdbc:mysql://localhost:3306/garageworks","root","Incapp@12");
	}
	public void disconnect()throws SQLException {
		c.close();
	}
	
	public void setEnquiry(String name,String phone,String email) throws SQLException{
		PreparedStatement p=c.prepareStatement("insert into enquries (name,phone,email) values(?,?,?)");
		p.setString(1, name);
		p.setString(2, phone);
		p.setString(3, email);
		p.executeUpdate();
	}
	public String setUser(HashMap user) throws SQLException{
		try {
			PreparedStatement p=c.prepareStatement("insert into users (email,name,phone,password,photo) values(?,?,?,?,?)");
			p.setString(1, (String)user.get("email"));
			p.setString(2, (String)user.get("name"));
			p.setString(3, (String)user.get("phone"));
			p.setString(4, (String)user.get("password"));
			p.setBinaryStream(5, (InputStream)user.get("photo"));
			p.executeUpdate();
			return "success";
		}catch (SQLIntegrityConstraintViolationException e) {
			return "failed";
		}
	}
	public HashMap getUserLogin(String email,String password)throws SQLException {
		PreparedStatement p=c.prepareStatement("select * from users where email=? and password=?");
		p.setString(1, email);
		p.setString(2, password);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			HashMap userDetails=new HashMap();
			userDetails.put("name", rs.getString("name"));
			userDetails.put("email", rs.getString("email"));
			userDetails.put("phone", rs.getString("phone"));
			return userDetails;
		}else {
			return null;
		}
	}
	public byte[] getUserPhoto(String email) throws SQLException{
		PreparedStatement p=c.prepareStatement("select photo from users where email=?");
		p.setString(1, email);
		ResultSet rs=p.executeQuery();
		rs.next();
		byte photo[]=rs.getBytes("photo");
		return photo;
	}
	public String updateUserPhoto(String email,InputStream photo) throws SQLException{
			PreparedStatement p=c.prepareStatement("update users set photo=? where email=?");
			p.setBinaryStream(1, photo);
			p.setString(2, email);
			p.executeUpdate();
			return "success";
	}

	public int updateUserPassword(String email,String op,String np) throws SQLException{
			PreparedStatement p=c.prepareStatement("update users set password=? where email=? and password=?");
			p.setString(1, np);
			p.setString(2, email);
			p.setString(3, op);
			int result= p.executeUpdate();
			return result;
	}
	public ArrayList<HashMap> getGarages(String state,String city) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from garage where state=? and city=? and status='accepted'");
		p.setString(1, state);
		p.setString(2, city);
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap> allGarageDetails=new ArrayList<HashMap>();
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
			allGarageDetails.add(garageDetails);
		}
		return allGarageDetails;
	}
}
