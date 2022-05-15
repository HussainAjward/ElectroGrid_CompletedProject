package model;

import java.sql.*;

public class Reader
{
	private Connection connect()
	{
		Connection con = null;

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con =
					DriverManager.getConnection(
							"jdbc:mysql://127.0.0.1:3306/electro_grid", "root", "root");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return con;
	}

	public String readReader()
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
			output = "<table class='table table-hover'><thead class='thead-dark'><tr>"
					+ "<th scope='col'>Reader Name</th><th scope='col'>Reader Email</th>"
					+ "<th scope='col'>Reader Phone</th><th scope='col'>Reader Password</th>"
					+ "<th scope='col'>Update</th><th scope='col'>Remove</th></tr></thead>";

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
				output += "<tr><td><input id='hidReaderIDUpdate' "
						+ "name='hidReaderIDUpdate'"
						+ "type='hidden' value='" + readerID 
						+ "'>" + readerName + "</td>";
				output += "<td>" + readerEmail + "</td>";
				output += "<td>" + readerPhone + "</td>";
				output += "<td>" + readerPassword + "</td>";

				// buttons
				output += "<td><input name='btnUpdate'"
						+ "type='button' value='Update'"
						+ "class='btnUpdate btn btn-secondary btn-sm'"
						+ "data-Readerid='"
						+ readerID + "'></td>"
						+ "<td><input name='btnRemove' "
						+ "type='button' value='Remove'"
						+ "class='btnRemove btn btn-danger btn-sm'"
						+ "data-Readerid='"
						+ readerID + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the Readers.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertReader(String name, String email, String phone, String password)
	{
		String output = "";

		try
		{
			Connection con = connect();

			if (con == null)
			{
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into reader"
					+ "(`readerID`,`readerName`,`readerEmail`,`readerPhone`,`readerPassword`)"
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

			String newReader = readReader();
			output = "{\"status\":\"success\", \"data\": \"" +
					newReader + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":"
					+ "\"Error while inserting the reader details.\"}";
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
			{
				return "Error while connecting to the database for updating.";
			}

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

			String newReader = readReader();
			output = "{\"status\":\"success\", \"data\": \"" +
					newReader + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the reader details.\"}";
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
			{
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from reader where readerID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(readerID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newReader = readReader();
			output = "{\"status\":\"success\", \"data\": \"" +
					newReader + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the reader details.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}
}