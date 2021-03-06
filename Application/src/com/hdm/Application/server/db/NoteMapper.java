package com.hdm.Application.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.AppUser;

/**Notebook Mapper Klasse bildet Note-Objekte auf eine relationale Datenbank ab.
 * Diese Klasse stellt Methoden zur Verfuegung, die das erstellen, editieren, auslesen/suchen und loeschen 
 * der gewuenschten Datensaezte erlauben. Die Mapperklasse stellt die Verbindungsschicht zwischen Datenbank
 * und Applikationslogik dar. Datenbank-Strukturen koennen in Objekte umgewandelt werden, jedoch auch Objekte 
 * in Datenbankstrukturen
*/




public class NoteMapper {
	/**Klasse wird nur einmal instantiiert(Singleton)
	 * 
	 */
	 private static NoteMapper noteMapper = null;
	 
	 /**
	  * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	  * Instanzen dieser Klasse zu erzeugen.
	  * 
	  */


	 protected NoteMapper() {
	 }
	 
	 /**Diese Methode ist statisch. Sie stellt die 
		 * Singleton-Eigenschaft sicher, es kann nur eine Instanz von 
		 * NoteMapper  existieren.
		 * 
		 * @return NoteMapper-Objekt
		 */
	 

	 public static NoteMapper noteMapper() {

		    if (noteMapper == null) {
		      noteMapper = new NoteMapper();
		    }

		    return noteMapper;
		  }
	 
	 
	 
	 /**
		 * Ein Note-Objekt wird in der Datenbank hinzugefuegt. Der Primaerschluessel wird ueberprueft 
		 * und gegebenenfalls neu zugeordnet.
		 * @param note (Objekt, das gespeichert werden soll)
		 * @return neu uebergebenes Objekt, mit angepasster ID
		 */
		
		
	 
	 public Note createNote(Note note) {
		    // DB-Verbindung holen
		    Connection con = DBConnection.connection();

		    try {
		    	//Leeres SQL Statement anlegen
		      Statement stmt = con.createStatement();
		    //Statement ausfuellen und als Query an DB schicken
		      ResultSet rs = stmt.executeQuery("SELECT MAX(nID) AS maxnID" + " FROM notes");

				String modDate = null;
				if (note.getnModDate() != null) {
					SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
					modDate = mySQLformat.format(note.getnModDate());
				}
				
				String creDate = null;
				if (note.getnCreDate() != null) {
					SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					creDate = mySQLformat.format(note.getnCreDate());
				}
				
		      	/**
				 * Es kann max. ein Ergebnis zurueck gegeben werden, da die id der Primaerschluessel ist.
				 * Daher wird geprueft ob ein Ergebnis vorliegt
				 */
		      
		      if (rs.next()) {
		    	//Ergebnis-Tupel in Objekt umwandeln
		    	  note.setnID(rs.getInt("maxnid") + 1);
		    	  stmt = con.createStatement();
		    	  
		    	  stmt.executeUpdate("INSERT INTO notes (nid, nbid, title, subtitle, content, source, creadate, moddate) "
		    	            + "VALUES (" + note.getnID() + "," + note.getNbID() + ",'"
		    	            + note.getnTitle() + "','" + note.getnSubtitle() + "','" + note.getnContent() + "','" + note.getSource() + "','" 
		    	            + creDate + "','" + modDate + "')");
		    	  			// Zweimal Creation-Date, da bei der Creation Mod-Date = Creation-Date
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		 /**
		 * Note wird zurueckgegeben, da sich das Objekt eventuell im Laufe der Methode veraendert hat
		 */
		    
		    return note;
		    
	 	}

		
	 	/**
		 * Note-Objekt wird ueberarbeitet ind ie Datenbank geschrieben
		 * 
		 * @param note (Objekt, dass ueberarbeitet in DB geschrieben wird)
		 * @return als Parameter uebergebenes Objekt.
		 */
		
	 
	 
	 public Note updateNote(Note note){
		 Connection con = DBConnection.connection();
			
			String modDate = null;
			if (note.getnModDate() != null) {
				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				modDate = mySQLformat.format(note.getnModDate());
			}

		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeUpdate("UPDATE notes " + "SET title='" + note.getnTitle() + "'," + "subtitle='" + note.getnSubtitle() + "'," 
			 	+"content='" + note.getnContent() + "',"
			 	+ "moddate='" + modDate + "' WHERE nid=" + note.getnID());
		 }
		 catch(SQLException e){
			 e.printStackTrace();
		 }
		 
			
			return note;
	 }
	 
	 
	 

		/**
		 * Ein Note-Objekt soll mit seinen Daten aus der DB geloescht werden.
		 * 
		 * @param note (Objekt wird aus DB geloescht)
		 */
	 
	 
	 public void deleteNote(Note note){
		 Connection con = DBConnection.connection();

		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeUpdate("DELETE FROM notes " + "WHERE nid=" +note.getnID());	 
		 }
		 catch (SQLException e){
			 e.printStackTrace();
		 }
	 }
		
	 
	 public static void deleteAllUserNotes(AppUser u){
		 
			Connection con = DBConnection.connection();
			
			try{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT userid, nid, FROM permissions" 
						+ "WHERE userid =" + u.getUserID() + "AND WHERE isowner = 1");
				
				while (rs.next()){
					
					Integer idInt = new Integer(rs.getInt("nid"));
					Statement stmt2 = con.createStatement();
					stmt2.executeUpdate("DELETE FROM notes"
							+ "WHERE nID=" + idInt.intValue()); 
				}
				
			}
			
			catch (SQLException e){
				e.printStackTrace();
			}
		 
	 }
	 
	 	/**
		 * Eine spezielle Notiz (Note) wird ueber die NoteID (nbID) gesucht. Es wird genau ein Objekt zurueck gegeben
		 * @param nID (Primaerschluessel DB)
		 * @return Note-Objekt, das die gesuchte nID besitzt - null bei nicht vorhandenem Tupel
		 */

	 public Note findByID(int nID){
		 Connection con = DBConnection.connection();
		 
		 try{
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT nid, nbid, title, subtitle, content, source, creadate, moddate " 
			 + "FROM notes " + "WHERE nid=" + nID + " ORDER BY title");
			
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
		return null;
	 }
	 
	 
	 /**
		 * Auslesen aller Notes
		 * @return Vektor mit Note Objekten, der alle Notes enthaelt. 
		 * Trifft eine Exception ein wird ein teilweise gefuellter oder leerer Vektor ausgegeben
		 */
		
	 
	 
	 public Vector<Note> findAll(){
		 Connection con = DBConnection.connection();
		 
		 //Ergebnisvektor vorbereiten
		 Vector<Note> result = new Vector<Note>();
		 
		 try {
			 Statement stmt = con.createStatement();

			 ResultSet rs = stmt.executeQuery("SELECT nid, nbid, title, subtitle, content, source, creadate, moddate "
					 + "FROM notes " + "ORDER BY nbid");
			
			 // Fuer jeden Eintrag wird ein Notebook-Objekt erstellt	
			 while(rs.next()){
				 Note note = new Note();
				 note.setnID(rs.getInt("nid"));
				 note.setNbID(rs.getInt("nbid"));
				 note.setnTitle(rs.getString("title"));
				 note.setnSubtitle(rs.getString("subtitle"));
				 note.setnContent(rs.getString("content"));
				 note.setSource(rs.getString("source"));
				 note.setnCreDate(rs.getDate("creadate"));
				 note.setnModDate(rs.getDate("moddate"));
				 
				// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				 result.addElement(note);
				 
			 }
		 }
		 catch(SQLException e){
			 e.printStackTrace();
		 }
		 
		//Vektor wird zurueckgegeben
		 return result;
	 }


	 /**
		 * Auslesen aller Notes
		 * @return Vektor mit Note Objekten, der alle Notes enthaelt. 
		 * Trifft eine Exception ein wird ein teilweise gefuellter oder leerer Vektor ausgegeben
		 */
	 
	 public Vector<Note> findByDueDate(java.sql.Date dDate){
		 Connection con = DBConnection.connection();
		 Vector<Note> result = new Vector<Note>();
		 
		 try{
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT *	FROM notes INNER JOIN duedates ON duedates.nid = notes.nid WHERE duedate = '"+dDate.toString()+"';");
			 
			//Fuer jeden Eintrag im Suchergebnis wird ein Note-Objekt erstellt.
			 while(rs.next()) {
				 Note note = new Note();
				 note.setnID(rs.getInt("nid"));
				 note.setNbID(rs.getInt("nbid"));
				 note.setnTitle(rs.getString("title"));
				 note.setnSubtitle(rs.getString("subtitle"));
				 note.setnContent(rs.getString("content"));
				 note.setSource(rs.getString("source"));
				 note.setnCreDate(rs.getDate("creadate"));
				 note.setnModDate(rs.getDate("moddate"));
				 
				//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				 result.addElement(note);
				 
			 }
		 }
		 
		 catch (SQLException e){
			 e.printStackTrace();
		 }
		 
		//Vektor wird zurueckgegeben
		 return result; 
	 }
	 
	 /**
		 * Alle Note-Objekte mit gesuchtem Titel werden ausgelesen
		 * 
		 * @param nTitle
		 * @return Vektor mit Note-Objekten, die den gesuchten Note-Titel (nTitle) enthalten.
		 * Bei Exceptions wird ein teilweise gefuellter oder leerer Vektor zurueckgegeben
		 */
		
	 
	 public Vector<Note> findByTitle(String nTitle){
		 Connection con = DBConnection.connection();
		 Vector<Note> result = new Vector<Note>();
		 String sql = "SELECT *"
					 + " FROM notes" + " WHERE title LIKE '%" + nTitle + "%' ORDER BY creadate;";
		 System.out.println("SQL Statement: "+sql);		 
		 
		 try{
			 Statement stmt = con.createStatement();			 
			 ResultSet rs = stmt.executeQuery("SELECT *"
					 + " FROM notes" + " WHERE title LIKE '%" + nTitle + "%' ");
			 			 
			//Fuer jeden Eintrag im Suchergebnis wird ein Note-Objekt erstellt.
			 while(rs.next()) {
				 Note note = new Note();
				 note.setnID(rs.getInt("nid"));
				 note.setNbID(rs.getInt("nbid"));
				 //note.setUserID(rs.getInt("userID"));
				 note.setnTitle(rs.getString("title"));
				 note.setnSubtitle(rs.getString("subtitle"));
				 note.setnContent(rs.getString("content"));
				 note.setSource(rs.getString("source"));
				 note.setnCreDate(rs.getDate("creadate"));
				 note.setnModDate(rs.getDate("moddate"));
				 
				//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				 result.addElement(note);
				 
			 } 
		 }
		 
		 catch (SQLException e){
			 System.out.println("SQL FEHLER: "+e);
			 e.printStackTrace();
		 }
		 
		//Vektor wird zurueckgegeben
		 return result;
	 }
		 
	 public Vector<Note> findByCreationDate(java.sql.Date creadate){
		 Connection con = DBConnection.connection();
		 Vector<Note> result = new Vector<Note>();
		 	 
		 
		 try{
			 Statement stmt = con.createStatement();			 
			 ResultSet rs = stmt.executeQuery("SELECT * FROM notes WHERE creadate = '" +creadate.toString()+ "';");
			 
			 
			//Fuer jeden Eintrag im Suchergebnis wird ein Note-Objekt erstellt.
			 while(rs.next()) {
				 Note note = new Note();
				 note.setnID(rs.getInt("nid"));
				 note.setNbID(rs.getInt("nbid"));
				 note.setnTitle(rs.getString("title"));
				 note.setnSubtitle(rs.getString("subtitle"));
				 note.setnContent(rs.getString("content"));
				 note.setSource(rs.getString("source"));
				 note.setnCreDate(rs.getDate("creadate"));
				 note.setnModDate(rs.getDate("moddate"));
				 
				//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				 result.addElement(note);
				 
			 } 
		 }
		 
		 catch (SQLException e){
			 System.out.println("SQL FEHLER: "+e);
			 e.printStackTrace();
		 }
		 
		//Vektor wird zurueckgegeben
		 return result;
	 }

	 public Vector<Note> findByNotebook (Notebook notebook){
			
			//DB-Verbindung holen
			
			Connection con = DBConnection.connection();
			
			Vector<Note> result = new Vector<Note>();
			
			try{
				//Leeres SQL Statement anlegen
				Statement stmt = con.createStatement();
				
				//Statement ausfuellen und als Query an DB schicken
				String sql;
				
				if(notebook.getNbID() != 0){
					sql= "SELECT nid, nbid, title, subtitle, content, source, creadate, moddate FROM notes"
							+ " WHERE nbid =" + notebook.getNbID();
				}else{
					sql = "SELECT nid, nbid, title, subtitle, content, source, creadate, moddate FROM notes"
							+ " WHERE title LIKE '%" + notebook.getNbTitle()+"%'";
				}
				
				ResultSet rs = stmt.executeQuery(sql);
				
				// Fuer jeden Eintrag wird ein Notebook-Objekt erstellt	
				while (rs.next()){
					
					Note note = new Note();
					note.setnID(rs.getInt("nid"));
					note.setNbID(rs.getInt("nbid"));
					note.setnTitle(rs.getString("title"));
					note.setnSubtitle(rs.getString("subtitle"));
					note.setnContent(rs.getString("content"));
					note.setSource(rs.getString("source"));
					note.setnCreDate(rs.getDate("creadate"));
					note.setnModDate(rs.getDate("moddate"));
					
					
					// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
					result.addElement(note);
				}
			}
			catch (SQLException e){
				e.printStackTrace();
				return null;
			}
			return result;
		}
	 
		public Vector<Note> findByUser(AppUser user) {

			// DB-Verbindung holen und Variablen zurücksetzen
			int appUserID = user.getUserID();
			Connection con = DBConnection.connection();
			int nID = 0;

			Vector<Note> result = new Vector<Note>();
			
			Vector<Integer> nIDs = new Vector<Integer>();
			
			try{
				//Leeres SQL Statement anlegen
				Statement stmt = con.createStatement();
				
				//Statement ausfuellen und als Query an DB schicken
				ResultSet rs = stmt.executeQuery("SELECT nid FROM permissions "
						+ "WHERE appuserid=" + appUserID
						+ " AND isowner=1");
				
				while (rs.next()){
					
				
				nID = rs.getInt("nid");
				
				nIDs.add(nID);
				
				}

		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		
		try{
			
			for(int i = 0; i < nIDs.size(); i++){
				
				nID = nIDs.get(i);
				
					//Leeres SQL Statement anlegen
					Statement stmt2 = con.createStatement();
					
					//Statement ausfuellen und als Query an DB schicken
					ResultSet rs2 = stmt2.executeQuery("SELECT nid, nbid, title, subtitle, content, source, creadate, moddate FROM notes "
							+ "WHERE nid=" + nID);
					
					while (rs2.next()){
						
					
						Note note = new Note();
						note.setNbID(rs2.getInt("nbid"));
						note.setnID(rs2.getInt("nid"));
						note.setnTitle(rs2.getString("title"));
						note.setnSubtitle(rs2.getString("subtitle"));
						note.setnContent(rs2.getString("content"));
						note.setSource(rs2.getString("source"));
						note.setnCreDate(rs2.getDate("creadate"));
						note.setnModDate(rs2.getDate("moddate"));
						
						// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
						result.addElement(note);
					
					}
			}
		}
			catch (SQLException e){

				e.printStackTrace();
				return null;
			}
		
			return result;

		}


public Vector<Note> findByUserPermission3(AppUser user) {

	// DB-Verbindung holen und Variablen zurücksetzen
	int appUserID = user.getUserID();
	Connection con = DBConnection.connection();
	int nID = 0;

	Vector<Note> result = new Vector<Note>();
	
	Vector<Integer> nIDs = new Vector<Integer>();
	
	try{
		//Leeres SQL Statement anlegen
		Statement stmt = con.createStatement();
		
		//Statement ausfuellen und als Query an DB schicken
		ResultSet rs = stmt.executeQuery("SELECT nid FROM permissions WHERE appuserid= " + appUserID +" AND permtype = 3");
		
		while (rs.next()){
			
		
		nID = rs.getInt("nid");
		
		nIDs.add(nID);
		
		}

}
catch (SQLException e){
	e.printStackTrace();
	return null;
}

try{
	
	for(int i = 0; i < nIDs.size(); i++){
		
		nID = nIDs.get(i);
		
			//Leeres SQL Statement anlegen
			Statement stmt2 = con.createStatement();
			
			//Statement ausfuellen und als Query an DB schicken
			ResultSet rs2 = stmt2.executeQuery("SELECT nid, nbid, title, subtitle, content, source, creadate, moddate FROM notes "
					+ "WHERE nid=" + nID);
			
			while (rs2.next()){
				
			
				Note note = new Note();
				note.setnID(rs2.getInt("nid"));
				note.setNbID(rs2.getInt("nbid"));
				note.setnTitle(rs2.getString("title"));
				note.setnSubtitle(rs2.getString("subtitle"));
				note.setnContent(rs2.getString("content"));
				note.setSource(rs2.getString("source"));
				note.setnCreDate(rs2.getDate("creadate"));
				note.setnModDate(rs2.getDate("moddate"));
				
				// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				result.addElement(note);
			
			}
	}
}
	catch (SQLException e){

		e.printStackTrace();
		return null;
	}

	return result;

}

public Vector<Note> findByUserPermission2(AppUser user) {

	// DB-Verbindung holen und Variablen zurücksetzen
	int appUserID = user.getUserID();
	Connection con = DBConnection.connection();
	int nID = 0;

	Vector<Note> result = new Vector<Note>();
	
	Vector<Integer> nIDs = new Vector<Integer>();
	
	try{
		//Leeres SQL Statement anlegen
		Statement stmt = con.createStatement();
		
		//Statement ausfuellen und als Query an DB schicken
		ResultSet rs = stmt.executeQuery("SELECT nid FROM permissions WHERE appuserid= " + appUserID +" AND permtype=2");
		
		while (rs.next()){
			
		
		nID = rs.getInt("nid");
		
		nIDs.add(nID);
		
		}

}
catch (SQLException e){
	e.printStackTrace();
	return null;
}

try{
	
	for(int i = 0; i < nIDs.size(); i++){
		
		nID = nIDs.get(i);
		
			//Leeres SQL Statement anlegen
			Statement stmt2 = con.createStatement();
			
			//Statement ausfuellen und als Query an DB schicken
			ResultSet rs2 = stmt2.executeQuery("SELECT nid, nbid, title, subtitle, content, source, creadate, moddate FROM notes "
					+ "WHERE nid=" + nID);
			
			while (rs2.next()){
				
			
				Note note = new Note();
				note.setnID(rs2.getInt("nid"));
				note.setNbID(rs2.getInt("nbid"));
				note.setnTitle(rs2.getString("title"));
				note.setnSubtitle(rs2.getString("subtitle"));
				note.setnContent(rs2.getString("content"));
				note.setSource(rs2.getString("source"));
				note.setnCreDate(rs2.getDate("creadate"));
				note.setnModDate(rs2.getDate("moddate"));
				
				// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				result.addElement(note);
			
			}
	}
}
	catch (SQLException e){

		e.printStackTrace();
		return null;
	}

	return result;

}

public Vector<Note> findByUserPermission1(AppUser user) {

	// DB-Verbindung holen und Variablen zurücksetzen
	int appUserID = user.getUserID();
	Connection con = DBConnection.connection();
	int nID = 0;

	Vector<Note> result = new Vector<Note>();
	
	Vector<Integer> nIDs = new Vector<Integer>();
	
	try{
		//Leeres SQL Statement anlegen
		Statement stmt = con.createStatement();
		
		//Statement ausfuellen und als Query an DB schicken
		ResultSet rs = stmt.executeQuery("SELECT nid FROM permissions WHERE appuserid= " + appUserID +" AND permtype=1");
		
		while (rs.next()){
			
		
		nID = rs.getInt("nid");
		
		nIDs.add(nID);
		
		}

}
catch (SQLException e){
	e.printStackTrace();
	return null;
}

try{
	
	for(int i = 0; i < nIDs.size(); i++){
		
		nID = nIDs.get(i);
		
			//Leeres SQL Statement anlegen
			Statement stmt2 = con.createStatement();
			
			//Statement ausfuellen und als Query an DB schicken
			ResultSet rs2 = stmt2.executeQuery("SELECT nid, nbid, title, subtitle, content, source, creadate, moddate FROM notes "
					+ "WHERE nid=" + nID);
			
			while (rs2.next()){
				
			
				Note note = new Note();
				note.setnID(rs2.getInt("nid"));
				note.setNbID(rs2.getInt("nbid"));
				note.setnTitle(rs2.getString("title"));
				note.setnSubtitle(rs2.getString("subtitle"));
				note.setnContent(rs2.getString("content"));
				note.setSource(rs2.getString("source"));
				note.setnCreDate(rs2.getDate("creadate"));
				note.setnModDate(rs2.getDate("moddate"));
				
				// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				result.addElement(note);
			
			}
	}
}
	catch (SQLException e){

		e.printStackTrace();
		return null;
	}

	return result;

}
public Vector<Note> findAllNotesFromAppUser(AppUser user) {

	// DB-Verbindung holen und Variablen zurücksetzen
	int appUserID = user.getUserID();
	Connection con = DBConnection.connection();
	Vector<Note> result = new Vector<Note>();
	
	
try{
	
			//Leeres SQL Statement anlegen
			Statement stmt2 = con.createStatement();
			
			//Statement ausfuellen und als Query an DB schicken
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM notes INNER JOIN permissions ON notes.nid = permissions.nid"
					+" WHERE (permtype > 0 AND appuserid = "+appUserID+") OR (isowner = 1 AND appuserid = "+appUserID+");");
		 			
			while (rs2.next()){
				
			
				Note note = new Note();
				note.setnID(rs2.getInt("nid"));
				note.setNbID(rs2.getInt("nbid"));
				note.setnTitle(rs2.getString("title"));
				note.setnSubtitle(rs2.getString("subtitle"));
				note.setnContent(rs2.getString("content"));
				note.setSource(rs2.getString("source"));
				note.setnCreDate(rs2.getDate("creadate"));
				note.setnModDate(rs2.getDate("moddate"));
				note.setpType(rs2.getInt("permtype"));
				note.setUserID(appUserID);
				
				// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				result.addElement(note);
			
			}
	
}
	catch (SQLException e){

		e.printStackTrace();
		return null;
	}

	return result;

}


		 			 

	 

public Vector<Note> findOwnNotesFromAppUser(AppUser user) {

	// DB-Verbindung holen und Variablen zurücksetzen
	int appUserID = user.getUserID();
	Connection con = DBConnection.connection();
	int nID = 0;

	Vector<Note> result = new Vector<Note>();
	
	Vector<Integer> nIDs = new Vector<Integer>();
	
	try{
		//Leeres SQL Statement anlegen
		Statement stmt = con.createStatement();
		
		//Statement ausfuellen und als Query an DB schicken
		ResultSet rs = stmt.executeQuery("SELECT nid FROM permissions WHERE appuserid= " + appUserID +" AND  isowner  >0");
		
		while (rs.next()){
			
		
		nID = rs.getInt("nid");
		
		nIDs.add(nID);
		
		}

}
catch (SQLException e){
	e.printStackTrace();
	return null;
}

try{
	
	for(int i = 0; i < nIDs.size(); i++){
		
		nID = nIDs.get(i);
		
			//Leeres SQL Statement anlegen
			Statement stmt2 = con.createStatement();
			
			//Statement ausfuellen und als Query an DB schicken
			ResultSet rs2 = stmt2.executeQuery("SELECT nid, nbid, title, subtitle, content, source, creadate, moddate FROM notes "
					+ "WHERE nid=" + nID);
			
			while (rs2.next()){
				
			
				Note note = new Note();
				note.setnID(rs2.getInt("nid"));
				note.setNbID(rs2.getInt("nbid"));
				note.setnTitle(rs2.getString("title"));
				note.setnSubtitle(rs2.getString("subtitle"));
				note.setnContent(rs2.getString("content"));
				note.setSource(rs2.getString("source"));
				note.setnCreDate(rs2.getDate("creadate"));
				note.setnModDate(rs2.getDate("moddate"));
				
				// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				result.addElement(note);
			
			}
	}
}
	catch (SQLException e){

		e.printStackTrace();
		return null;
	}

	return result;

}

public Vector<Note> findByBetweenCreationDate(java.sql.Date von, java.sql.Date bis){
	 Connection con = DBConnection.connection();
	 Vector<Note> result = new Vector<Note>();
	 	 
	 
	 try{
		 Statement stmt = con.createStatement();			 
		 ResultSet rs = stmt.executeQuery("SELECT * FROM notes WHERE creadate BETWEEN '"+von.toString()+"' AND '"+bis.toString()+"';");
		 
		 
		//Fuer jeden Eintrag im Suchergebnis wird ein Note-Objekt erstellt.
		 while(rs.next()) {
			 Note note = new Note();
			 note.setnID(rs.getInt("nid"));
			 note.setNbID(rs.getInt("nbid"));
			 note.setnTitle(rs.getString("title"));
			 note.setnSubtitle(rs.getString("subtitle"));
			 note.setnContent(rs.getString("content"));
			 note.setSource(rs.getString("source"));
			 note.setnCreDate(rs.getDate("creadate"));
			 note.setnModDate(rs.getDate("moddate"));
			 
			//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
			 result.addElement(note);
			 
		 } 
	 }
	 
	 catch (SQLException e){
		 System.out.println("SQL FEHLER: "+e);
		 e.printStackTrace();
	 }
	 
	//Vektor wird zurueckgegeben
	 return result;
}

public Vector<Note> findByBetweenDueDate(java.sql.Date von, java.sql.Date bis){
Connection con = DBConnection.connection();
Vector<Note> result = new Vector<Note>();
	 

try{
	 Statement stmt = con.createStatement();			 
	 ResultSet rs = stmt.executeQuery("SELECT *	FROM notes INNER JOIN duedates ON duedates.nid = notes.nid WHERE duedate  BETWEEN '"+von.toString()+"' AND '"+bis.toString()+"';");

	 
	//Fuer jeden Eintrag im Suchergebnis wird ein Note-Objekt erstellt.
	 while(rs.next()) {
		 Note note = new Note();
		 note.setnID(rs.getInt("nid"));
		 note.setNbID(rs.getInt("nbid"));
		 note.setnTitle(rs.getString("title"));
		 note.setnSubtitle(rs.getString("subtitle"));
		 note.setnContent(rs.getString("content"));
		 note.setSource(rs.getString("source"));
		 note.setnCreDate(rs.getDate("creadate"));
		 note.setnModDate(rs.getDate("moddate"));
		 note.setdDate(rs.getDate("duedate"));
		//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
		 result.addElement(note);
		 
	 } 
}



catch (SQLException e){
	 System.out.println("SQL FEHLER: "+e);
	 e.printStackTrace();
}

//Vektor wird zurueckgegeben
return result;
}
public Vector<Note> findByBetweenModiDate(java.sql.Date von, java.sql.Date bis){
Connection con = DBConnection.connection();
Vector<Note> result = new Vector<Note>();
	 

try{
	 Statement stmt = con.createStatement();			 
	 ResultSet rs = stmt.executeQuery("SELECT * FROM notes WHERE moddate BETWEEN '"+von.toString()+"' AND '"+bis.toString()+"';");
	 
	 
	//Fuer jeden Eintrag im Suchergebnis wird ein Note-Objekt erstellt.
	 while(rs.next()) {
		 Note note = new Note();
		 note.setnID(rs.getInt("nid"));
		 note.setNbID(rs.getInt("nbid"));
		 note.setnTitle(rs.getString("title"));
		 note.setnSubtitle(rs.getString("subtitle"));
		 note.setnContent(rs.getString("content"));
		 note.setSource(rs.getString("source"));
		 note.setnCreDate(rs.getDate("creadate"));
		 note.setnModDate(rs.getDate("moddate"));
		 
		//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
		 result.addElement(note);
		 
	 } 
}



catch (SQLException e){
	 System.out.println("SQL FEHLER: "+e);
	 e.printStackTrace();
}

//Vektor wird zurueckgegeben
return result;
}		 			 

}