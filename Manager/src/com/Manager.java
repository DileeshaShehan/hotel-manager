package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Manager {
	
	public Connection connect()
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.cj.jdbc.Driver"); 
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotel1", "root", ""); 
	 //For testing
	 System.out.print("Successfully connected"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 
		}
	
	public String insertItem(String loginid, String user, String pass, String email) {
		
		 String output = "";
		 
		 try {
		
		Connection con = connect();
		if (con == null) 
		{ 
		return "Error while connecting to the database"; 
		}
		
		// create a prepared statement
		String query = "insert into manager (id, loginId, loginUser, loginPass, loginEmail)"
				 + " values (?, ?, ?, ?, ?)"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, 0); 
		preparedStmt.setString(2, loginid); 
		preparedStmt.setString(3, user); 
		preparedStmt.setString(4, pass); 
		preparedStmt.setString(5, email);
		
		System.out.println(loginid);
		 
		 preparedStmt.execute(); 
		 con.close(); 
		 
		 String newItems = readManager();
		 output =  "{\"status\":\"success\", \"data\": \"" + 
				 newItems + "\"}"; 
		 } 

		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";  
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}
	
	public String readManager()
	{ 
			 String output = ""; 
			try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for reading."; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Login Id</th>" 
			 +"<th>Login User</th><th>Login Password</th>"
			 + "<th>Login Email</th>" 
			 + "<th>Update</th><th>Remove</th></tr>"; 
			 String query = "select * from manager"; 
			 java.sql.Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
			 String id = Integer.toString(rs.getInt("id")); 
			 String loginid = rs.getString("loginID"); 
			 String user = rs.getString("loginUser"); 
			 String pass = rs.getString("loginPass"); 
			 String email = rs.getString("loginEmail"); 
			 // Add a row into the html table
			 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + id + "'>"
					 + loginid + "</td>";
			 output += "<td>" + user + "</td>"; 
			 output += "<td>" + pass + "</td>"; 
			 
			 output += "<td>" + email + "</td>";
			 // buttons
			 output += "<td><input name='btnUpdate' " 
			 + " type='button' value='Update' class =' btnUpdate btn btn-secondary'data-itemid='" + id + "'></td>"
			 + "<td><form method='post' action='Manager.jsp'>"
			 + "<input name='btnRemove' " 
			 + " type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" + id + "'>"
			 + "<input name='hidItemIDDelete' type='hidden' " 
			 + " value='" + id + "'>" + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
			 } 
			catch (Exception e) 
			 { 
			 output = "Error while reading the items."; 
			 System.err.println(e.getMessage()); 
			 } 
			return output; 
		}

		
	public String deleteItem(String id)
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for deleting."; 
	 } 
	 // create a prepared statement
	 String query = "delete from room where id=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(id)); 
	 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 String newItems = readManager();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";  
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
		}
	
	public String updateItem(String id, String loginid, String user, String pass, String email)
	//4
	{
	String output = "";
	try {
	Connection conn = connect();
	if (conn == null) {
	return "Error while connecting to the database for updating.";
	}

	// create a prepared statement
	String query = "UPDATE manager SET loginId=?,loginUser=?,loginPass=?,loginEmail=? WHERE id=?";
	PreparedStatement preparedStmt = conn.prepareStatement(query);
	//binding values
	preparedStmt.setString(1, loginid);
	preparedStmt.setString(2, user);
	preparedStmt.setString(3, pass);
	preparedStmt.setString(4, email);
	preparedStmt.setInt(5, Integer.parseInt(id));
	//execute the statement
	preparedStmt.execute();
	conn.close();
	String newItems = readManager();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while Updating the item.\"}";  
	
	System.err.println(e.getMessage());
	}
	return output;
	}

}
