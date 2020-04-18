package AmbulanceService.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import AmbulanceService.model.Ambulance;
import AmbulanceService.utill.DBConnection;

public class AmbulanceController {
	private static Connection connection;
	private static PreparedStatement ps;
	private static ResultSet rs;

	public String AddAmbulance(Ambulance ambulance) {
		String output = "";
		try {
			connection = DBConnection.getConnection();
			 if (connection == null)
			 {return "Error while connecting to the database for inserting."; } 
			
			ps = connection.prepareStatement(
					"INSERT INTO  ambulance_service(Amb_ID,Amb_No,Amb_Cont,Driver_Name,Ride_Date) "
							+ "	VALUES (?,?,?,?,?)");

			ps.setInt(1, 0);
			ps.setString(2, ambulance.getAmb_No());
			ps.setInt(3, ambulance.getAmb_Cont());
			ps.setString(4, ambulance.getDriver_Name());
			ps.setDate(5, ambulance.getRide_Date());
	
		

			ps.execute();
			 connection.close();
			 output = "Inserted successfully"; 

		}
		 catch (Exception e)
		 {
		 output = "Error while inserting the Ambulance.";
		 System.err.println(e.getMessage());
		 }
		 return output; 

		
	}

	public List<Ambulance> readAmbulance() {
		List<Ambulance> ambulances = new ArrayList<>();
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				System.err.println("connecting failed.");
			}
			// Prepare the html table to be displayed
			

			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from ambulance_service");
			

			// iterate through the rows in the result set
			while (rs.next()) {
				Ambulance ambulance = new Ambulance();
				ambulance.setAmb_ID(rs.getInt("Amb_ID"));
				ambulance.setAmb_No(rs.getString("Amb_No"));
				ambulance.setAmb_Cont(rs.getInt("Amb_Cont"));
				ambulance.setDriver_Name(rs.getString("Driver_Name"));
				ambulance.setRide_Date(rs.getDate("Ride_Date"));


				ambulances.add(ambulance);
			}
			connection.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return ambulances;
	}

	public String updateAmbulance(Ambulance Ambulance) {
		String output = "";
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			ps = connection.prepareStatement(
					"UPDATE ambulance_service SET Amb_No=?,Amb_Cont=?,Driver_Name=?,Ride_Date=? WHERE Amb_ID=?");

			// binding values
			ps.setInt(1, Ambulance.getAmb_Cont());
			ps.setString(2, Ambulance.getDriver_Name());
			ps.setString(3,Ambulance.getDriver_Name());
			ps.setDate(4, Ambulance.getRide_Date());
			ps.setInt(5, Ambulance.getAmb_ID());
			
			
			// execute the statement
			ps.execute();
			connection.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the lab test.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteAmbulance(String Amb_ID) {
		String output = "";
		try {
			connection = DBConnection.getConnection();
			if (connection == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement

			connection = DBConnection.getConnection();
			ps = connection.prepareStatement("delete from ambulance_service where Amb_ID=?");
			// binding values
			ps.setInt(1, Integer.parseInt(Amb_ID));
			// execute the statement
			ps.execute();
			connection.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Ambulance. -"+ e.getMessage();
			System.err.println(e.getMessage());
		}
		return output;
	}

}
