package com.hdm.Application.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class NotebookMapper {
private static NotebookMapper notebookMapper = null;
	
	protected NotebookMapper(){
	}
	
	public static NotebookMapper notebookMapper(){
		if(notebookMapper == null){
			notebookMapper = new NotebookMapper();
		}
		return notebookMapper;
	}
	
	public Notebook findById(int nbID){
		
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nbID, userID, nbTitle, nbCreDate, nbModDate, unbID FROM Notebook"
					+ "WHERE nbID=" + nbID );
				
			
			if (rs.next()){
				Notebook notebook = new Notebook();
				notebook.setNbId(rs.getInt("nbID"));
				notebook.setUserId(rs.getInt("userID"));
				notebook.setNbTitle(rs.getString("nbTitle"));
				notebook.setNbCreDate(rs.getDate("nbCreDate"));
				notebook.setNbModDate(rs.getDate("nbModDate"));
				//notebook.setUnbID(rs.getInt("unbID"));
				//unbID gibt es auch Methode dafuer? keine im Klassendiagramm
				return notebook;
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		
		return null;
		
	}
	
	public Vector<Notebook> findAll(){
		Connection con = DBConnection.connection();
		Vector<Notebook> result = new Vector<Notebook>();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nbID, userID, nbTitle, nbCreDate, nbModDate, unbID FROM Notebook" 
					+ "ORDER BY userID");
					
		
			while (rs.next()){
				
				Notebook notebook = new Notebook();
				notebook.setNbId(rs.getInt("nbID"));
				notebook.setUserId(rs.getInt("userID"));
				notebook.setNbTitle(rs.getString("nbTitle"));
				notebook.setNbCreDate(rs.getDate("nbCreDate"));
				notebook.setNbModDate(rs.getDate("nbModDate"));
			
				result.addElement(notebook);
			}
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	
	
	public Vector<Notebook> findByTitle(String nbTitle){
		Connection con = DBConnection.connection();
		Vector<Notebook> result = new Vector<Notebook>();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nbID, userID, nbTitle, nbCreDate, nbModDate, unbID FROM Notebook"
					+ "WHERE nbTitle LIKE" + nbTitle + "ORDER BY nCreDate");
			
			while(rs.next()){
				Notebook notebook = new Notebook();
				notebook.setNbId(rs.getInt("nbID"));
				notebook.setUserId(rs.getInt("userID"));
				notebook.setNbTitle(rs.getString("nbTitle"));
				notebook.setNbCreDate(rs.getDate("nbCreDate"));
				notebook.setNbModDate(rs.getDate("nbModDate"));
			
				result.addElement(notebook);
			}
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Notebook createNotebook(Notebook notebook){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(nbID) AS maxnbID FROM Notebook");
			
			if(rs.next()){
				notebook.setNbId(rs.getInt("maxnbID") + 1);
				stmt = con.createStatement();
				
				stmt.executeUpdate("INSERT INTO Notebook (nbID, userID, nbTitle, nbCreDate, nbModDate)"
						+ "VALUES (" + notebook.getNbId() + ",'" + notebook.getUserId() + "','" + notebook.getNbTitle() + "','" + notebook.getNbCreDate()
						+ "','" + notebook.getNbModDate() + "')" );

			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return notebook;
		
	}
	
	
	public Notebook updateNotebook(Notebook notebook){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE Notebook" + "SET nbTitle=\"" + notebook.getNbTitle()
			+ "\"," + "WHERE nbID=" + notebook.getNbId());
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return notebook;
	}
	
	public void deleteNotebook(Notebook notebook){
		Conenction con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Notebook" + "WHERE nbID=" + notebook.getNbId());
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	
	
	public User getNotebookOfUser(Notebook notebook){
		return UserMapper.userMapper().findById(notebook.getUserId());
	}

}
