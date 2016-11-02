package com.hdm.Application.server.db;

import java.sql.*;
import java.util.Vector;

import com.hdm.Application.shared.bo.DueDate;

public class DueDateMapper {

	private static DueDateMapper dueDateMapper = null;
	protected DueDateMapper(){
	}
	
	public static DueDateMapper dueDateMapper(){
		if(dueDateMapper == null){
			dueDateMapper = new DueDateMapper();
		}
		return dueDateMapper;
	}
	
	
	public Date findByDate(Date dueDate){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ddID, dDate, nID FROM DueDate" 
					+ "WHERE dDate=" + dDate + "ORDER BY nID");
					
			
			if(rs.next()){
				Date dD = new Date();
				dD.setDdId(rs.getInt("ddID"));
				dD.setDueDate(rs.getDate("dueDate"));
				dD.setnID(rs.getInt("nID"));
				
				
				return dD;
			}
		}
		
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return null;
		
	}
	
	
	public Vector<DueDate> findByNoteID(String nID){
		Connection con = DBConnection.connection();
		Vector<DueDate> result = new Vector<DueDate>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ddID, dueDate, nID" + "FROM DueDate"
			+ "WHERE nID LIKE'" + nID);
			
			while(rs.next()){
				Date dD = new Date();
				dD.setDdId(rs.getInt("ddID"));
				dD.setDueDate(rs.getDate("dueDate"));
				dD.setnID(rs.getInt("nID"));
				
				result.addElement(dD);
			}
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Date createDueDate(Date dueDate){
		Connection con = DBConnection.connection();
		
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ddID, dueDate, nID" + "FROM DueDate");
			
			if(rs.next()){
				dD.setDdId
				dD.setDueDate(rs.getDate("dueDate"));
				// wie setze ich DueDate? 
				
				stmt.executeUpdate("INSERT INTO DueDate (dueDate, nID)" + "VALUES ("
				+ dueDate.getDueDate() + ",'" + dueDate.getnID() + "')" );
				
			}
			
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return dueDate;
		
	}
	
	public DueDate updateDueDate(DueDate dueDate){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE DueDate" + "SET dueDate=\"" + dueDate.getDueDate()
			+ "WHERE nID" + dueDate.getnID());
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return dueDate;
	}
	
	
	public void deleteDueDate(DueDate dueDate){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM DueDate" + "WHERE nID=" + dueDate.getnID());
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	
	
	
}
