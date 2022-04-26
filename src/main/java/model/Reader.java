package model;

import java.sql.*;

public class Reader
{		//A common method to connect to the DB
		private Connection connect()
		{
				Connection con = null;
				
				try
				{
					Class.forName("com.mysql.jdbc.Driver");

					//Provide the correct details: DBServer/DBName, username, password
					con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electro_grid", "root", "root");
				}
				catch (Exception e)
				{e.printStackTrace();}

				return con;
		}
		
		public String insertReader(String name, String email, String phone, String password)
		{
				String output = "";
				
				try
				{
						Connection con = connect();
 
						if (con == null)
						{return "Error while connecting to the database for inserting."; }
						
						// create a prepared statement
						String query = " insert into reader (`readerID`,`readerName`,`readerEmail`,`readerPhone`,`readerPassword`)" 
										+ " values (?, ?, ?, ?, ?)";
 
						PreparedStatement preparedStmt = con.prepareStatement(query);
 
						// binding values
						preparedStmt.setInt(1, 0);
						preparedStmt.setString(2, name);
						preparedStmt.setString(3, email);
						preparedStmt.setString(4, phone);
						preparedStmt.setString(5, password);
 
						// execute the statement
						preparedStmt.execute();
						con.close();
 
						output = "Inserted successfully";
				}
				catch (Exception e)
				{
						output = "Error while inserting the item.";
						System.err.println(e.getMessage());
				}
				
				return output;
		}

		public String readReader()
		{
				String output = "";
 
				try
				{
 
						Connection con = connect();
 
						if (con == null)
						{return "Error while connecting to the database for reading."; }
 
						// Prepare the html table to be displayed
						output = "<table border='1'><tr><th>Reader ID</th><th>Reader Name</th>" +
								"<th>Reader Email</th>" +
								"<th>Reader Phone</th>" +
								"<th>Update</th><th>Remove</th></tr>";

						String query = "select * from reader";
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
 
						// iterate through the rows in the result set
						while (rs.next())
						{
								String readerID = Integer.toString(rs.getInt("readerID"));
								String readerName = rs.getString("readerName");
								String readerEmail = rs.getString("readerEmail");
								String readerPhone = rs.getString("readerPhone");
								String readerPassword = rs.getString("readerPassword");
 
								// Add into the html table
								output += "<tr><td>" + readerName + "</td>";
								output += "<td>" + readerEmail + "</td>";
								output += "<td>" + readerPhone + "</td>";
								output += "<td>" + readerPassword + "</td>";
 
								// buttons
								output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>" + "<td><form method='post' action='items.jsp'>" + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
										+ "<input name='itemID' type='hidden' value='" + readerID
										+ "'>" + "</form></td></tr>";
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

		public String updateReader(String ID, String name, String email, String phone, String password)
		{
				String output = "";
				
				try
				{
						Connection con = connect();
						if (con == null)
						{return "Error while connecting to the database for updating."; }
	 
						// create a prepared statement
						String query = "UPDATE reader SET readerName=?,readerEmail=?,readerPhone=?,readerPassword=? WHERE readerID=?";
						
						PreparedStatement preparedStmt = con.prepareStatement(query);
	 
						// binding values
						preparedStmt.setString(1, name);
						preparedStmt.setString(2, email);
						preparedStmt.setString(3, phone);
						preparedStmt.setString(4, password);
						preparedStmt.setInt(5, Integer.parseInt(ID));
	 
						// execute the statement
						preparedStmt.execute();
						con.close();
	 
						output = "Updated successfully";
				}
				catch (Exception e)
				{
						output = "Error while updating the item.";
						System.err.println(e.getMessage());
				}
	 
				return output;
		}
	
		public String deleteReader(String readerID)
		{
				String output = "";
				
				try
				{
						Connection con = connect();
	 
						if (con == null)
						{return "Error while connecting to the database for deleting."; }
	 
						// create a prepared statement
						String query = "delete from reader where readerID=?";
	 
						PreparedStatement preparedStmt = con.prepareStatement(query);
	 
						// binding values
						preparedStmt.setInt(1, Integer.parseInt(readerID));
	 
						// execute the statement
						preparedStmt.execute();
						con.close();
	 
						output = "Deleted successfully";
				}
				catch (Exception e)
				{
						output = "Error while deleting the item.";
						System.err.println(e.getMessage());
				}
	 
				return output;
		}
} 