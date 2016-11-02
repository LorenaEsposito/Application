package com.hdm.Application.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import test.Conenction;
import test.Note;
import test.NoteMapper;

public class NoteMapper {
	 private static NoteMapper noteMapper = null;

	 protected NoteMapper() {
	 }
	
	 public static NoteMapper customerMapper() {
		    if (noteMapper == null) {
		      noteMapper = new NoteMapper();
		    }

		    return noteMapper;
		  }
	 
	 public Note createNote(Note note) {
		    // DB-Verbindung holen
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery("SELECT MAX(nID) AS maxnID" + "FROM Note");

		      if (rs.next()) {
		    	  
		    	  note.setId(rs.getInt("maxnID") + 1);
		    	  stmt = con.createStatement();
		    	  
		    	  stmt.executeUpdate("INSERT INTO Note (nID, nbID, nTitle, nSubtitle, nContent, source, nCreDate, nModDate, userID) "
		    	            + "VALUES (" + note.getnID() + "," + note.getNbID() + ","
		    	            + note.getnTitle() + "," + note.getnSubtitle() + "," + note.getnContent() + "," + note.getSource() + "," 
		    	            + note.getncreDate() + "," + note.getnModDate() +  "," + note.getUserID() + "')");

		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    return note;
		    
	 	}

	 
	 
	 
	 public Note updateNote(Note note){
		 Connection con = DBConnection.connection();
		 
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeUpdate("UPDATE Note" + "SET nTitle=\"" + note.getnTitle() + "\"," + "nSubtitle=\"" + note.getLastName() + "\"" 
			 	+"nContent=\"" + note.getnContent() +"\"," + "source=\"" + note.getSource() + "\"," + "nCreDate=\"" + note.getnCreDate() 
			 	+ "nModDate=\"" + note.getnModDate() + "userID=\"" + note.getUserID() + "WHERE nID=" + note.getnID());
			 //modification Date hinzufuegen --> wie mach ich dass es auf jednefall automatisch hinzugefuegt wird?
			 
		 }
		 catch(SQLException e){
			 e.printStackTrace();
		 }
		 
		 return note;
	 }
	 
	 
	 
	 
	 public void deleteNote(Note note){
		 Conenction con = DBConnection.connection();
		 
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeUpdate("DELETE FROM Note" + "WHERE nID=" +note.getnID());	 
		 }
		 catch (SQLException e){
			 e.printStackTrace();
		 }
	 }
	 
	 

	
	 
	 public Note findByID(int nID){
		 Connection con = DBConnection.connection();
		 
		 try{
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT nID, nbID, userID, nTitle, nSubtitle, nContent, source, nCreDate, nModDate" 
			 + "WHERE nID=" + nID + "ORDER BY nTitle");
			
			 if(rs.next()){
				 Note note = new Note();
				 note.setnID(rs.getInt("nID"));
				 note.setNbID(rs.getInt("NbID"));
				 note.setUserID(rs.getInt("userID"));
				 note.setnTitle(rs.getString("nTitle"));
				 note.setnSubtitle(rs.getString("nSubtitle"));
				 note.setnContent(rs.getString("nContent"));
				 note.setSource(rs.getString("source"));
				 note.setnCreDate(rs.getDate("nCreDate"));
				 note.setnModDate(rs.getDate("nModDate"));
				 
				 return note;
			 }
		 }
		 catch (SQLException e){
			 e.printStackTrace();
			 return null;
		 }
	 }
	 
	 
	 public Vector<Note> findAll(){
		 Connecetion con = DBConnection.connection();
		 Vector<Note> result = new Vector<Note>();
		 
		 try {
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT nID, nbID, userID, nTitle, nSubtitle, nContent, source, nCreDate, nModDate"
					 + "FROM Note" + "ORDER BY nbID");
			 
			 while(rs.next()){
				 Note note = new Note();
				 note.setnID(rs.getInt("nID"));
				 note.setNbID(rs.getInt("nbID"));
				 note.setUserID(rs.getInt("userID"));
				 note.setnTitle(rs.getString("nTitle"));
				 note.setnSubtitle(rs.getString("nSubtitle"));
				 note.setnContent(rs.getString("nContent"));
				 note.setSource(rs.getString("source"));
				 note.setnCreDate(rs.getDate("nCreDate"));
				 note.setnModDate(rs.getDate("nModDate"));
				 
				 result.addElement(note);
				 
			 }
		 }
		 catch(SQLException e){
			 e.printStackTrace();
		 }
		 return result;
	 }

	 
	 public Vector<Note> findByTitle(Note note){
		 Conenction con = DBConnection();
		 Vector<Note> result = new Vector<Note>();
		 
		 try{
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT nID, nbID, userID, nTitle, nSubtitle, nContent, source, nCreDate, nModDate"
					 + "FROM Note" + "WHERE nTitle LIKE '" + nTitle + "'ORDER BY nCreDate");
			 
			 while(rs.next()) {
				 Note note = new Note();
				 note.setnID(rs.getInt("nID"));
				 note.setNbID(rs.getInt("nbID"));
				 note.setUserID(rs.getInt("userID"));
				 note.setnTitle(rs.getString("nTitle"));
				 note.setnSubtitle(rs.getString("nSubtitle"));
				 note.setnContent(rs.getString("nContent"));
				 note.setSource(rs.getString("source"));
				 note.setnCreDate(rs.getDate("nCreDate"));
				 note.setnModDate(rs.getDate("nModDate"));
				 
				 result.addElement(note);
				 
			 }
		 }
		 
		 catch (SQLException e){
			 e.printStackTrace();
		 }
		 
		 return result;
	 }
	 
	 
}
