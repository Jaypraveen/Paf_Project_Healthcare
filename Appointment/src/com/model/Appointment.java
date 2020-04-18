package com.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.util.DBConnect;

public class Appointment {

	DBConnect connect = new DBConnect();

	public String getAvailableDocs() {

		String output = "";
		try {
			Connection connection = connect.connect();

			if (connection == null) {
				return "Error while connecting to the database";
			}

			output = "<table border=\"1\">" + "<th>Doctor Name</th>" + "<th>Availability</th>";

			String query = "select D_Name from doctor  where Availability = '1'";
			
			// Not set yet String query = "Select D_Name from doctor"; Statement
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String docName = resultSet.getString("D_Name"); //
				String availability = resultSet.getString("Availability");

				// Add into the html table

				output += "<tr><td><input id=\"docName\"name=\"docName\"type=\"hidden\" value=\"" + docName + "\">"
						+ docName + "</td>";

				// buttons

				output += "<td><form method=\"post\" action=\"displayAvailableDocs.jsp\">"
						+ "<button class=\"btn btn-success\" name = \"availabilityBtn\" type=\"submit\"></from>"
						+ "</td></tr>";
			}

			connection.close();

			output += "</table>";

		} catch (Exception e) { // TODO: handle exception
			System.out.println("Error while connecting to the database " + e);

			System.err.println(e.getMessage());
		}
		return output;

	}

	public String readAppointments() {

		String output = "";
		try {
			Connection connection = connect.connect();

			if (connection == null) {
				return "Error while connecting to the database";
			}

			output = "<table border=\"1\">" + "<th>Appointment Id</th>" + "<th>Appointment Id</th>" + "<th>User Id</th>"
					+ "<th>Doctor Id</th>" + "<th>Doctor Specialisation</th>" + "<th>Hospital Id</th>" + "</th>";

			// Not set yet
			String query = "Select * from appointments";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String appoId = resultSet.getString("Appointment_Id");
				String userId = resultSet.getString("User_Id");
				String docId = resultSet.getString("Doctor_Id");
				String docSpec = resultSet.getString("Doctor_spec");
				String hospId = resultSet.getString("Hosp_Id");
				String date = resultSet.getString("Date");

				// Add into the html table
				output += "<tr><td><input id=\"readAppo\"name=\"readAppo\"type=\"hidden\" value=\""

						+ appoId + "\">" + appoId + "</td>";

				output += "<td>" + userId + "</td>";

				output += "<td>" + docId + "</td>";

				output += "<td>" + docSpec + "</td>";

				output += "<td>" + hospId + "</td>";

				output += "<td>" + date + "</td>";

				// buttons

				output += "<td><input name=\"btnUpdate\" type=\"submit\"value=\"Update\" class=\"btn btn-warning btnUpdate\"></td>"

						+ "<td><form method=\"post\" action=\"bookAppointment.jsp\">"

						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"

						+ "<input name=\"\" type=\"hidden\" value=\"" + appoId + "\">"

						+ "</form></td></tr>";
			}

			connection.close();

			output += "</table>";

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error while connecting to the database " + e);

			System.err.println(e.getMessage());
		}
		return output;

	}

	public String setAppointment(String userId, String docId, String hospId, String docSpec, String date) {
		String output = "";

		try {

			Connection connection = connect.connect();

			if (connection == null) {

				return "Error while connecting to the database for inserting.";

			}

			// create a prepared statement

			String query = " insert into appointments(`User_Id`,`Doctor_Id`,`Doctor_spec`,`Hosp_Id`,`Date`)"

					+ " values (?, ?, ?, ?)";

			PreparedStatement preparedStmt = connection.prepareStatement(query);

			preparedStmt.setInt(1, Integer.parseInt(userId));

			preparedStmt.setInt(2, Integer.parseInt(docId));

			preparedStmt.setInt(3, Integer.parseInt(hospId));

			preparedStmt.setString(4, docSpec);

			preparedStmt.setDate(5, Date.valueOf(date));

			preparedStmt.execute();

			connection.close();

			output = "Inserted successfully";

		} catch (Exception e) {

			output = "Error while inserting the Appointments.";

			System.err.println(e.getMessage());

		}

		return output;

	}

	public String updateAppointment(String userId, String docId, String hospId, String docSpec, String date) {
		String output = "";

		try {

			Connection connection = connect.connect();

			if (connection == null) {

				return "Error while connecting to the database for updating.";

			}

			// update

			// create a prepared statement

			String query = "UPDATE appointments SET User_Id=?,Doctor_Id=?,Doctor_spec=?,Hosp_Id=? WHERE Appointment_Id=?";

			PreparedStatement preparedStmt = connection.prepareStatement(query);

			// binding values

			preparedStmt.setInt(1, Integer.parseInt(userId));

			preparedStmt.setInt(2, Integer.parseInt(docId));

			preparedStmt.setInt(3, Integer.parseInt(hospId));

			preparedStmt.setString(4, docSpec);

			preparedStmt.setDate(5, Date.valueOf(date));

			preparedStmt.execute();

			connection.close();

			// execute the statement

			preparedStmt.execute();

			connection.close();

			output = "Appointment Details Updated successfully";

		} catch (Exception e) {

			output = "Error while updating the Appointment.";

			System.err.println(e.getMessage());

		}

		return output;

	}

	public String calcelAppointment(String appoId) {
		String output = "";

		try {

			Connection connection = connect.connect();

			if (connection == null) {

				return "Error while connecting to the database for deleting.";

			}

			// create a prepared statement

			String query = "delete from appointments where Appointment_Id=?";

			PreparedStatement preparedStmt = connection.prepareStatement(query);

			// binding values

			preparedStmt.setInt(1, Integer.parseInt(appoId));

			// execute the statement

			preparedStmt.execute();

			connection.close();

			output = " Deleted Appointment";

		} catch (Exception e) {

			// output = "Doctors Are Registered to this Hospital.";

			System.err.println(e.getMessage());

		}

		return output;

	}
}
