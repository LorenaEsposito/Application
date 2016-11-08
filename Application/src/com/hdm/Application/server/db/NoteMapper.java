package com.hdm.Application.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.hdm.Application.shared.bo.Note;


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
	 
	 public static NoteMapper customerMapper() {
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
		      ResultSet rs = stmt.executeQuery("SELECT MAX(nID) AS maxnID" + "FROM Note");

		      	/**
				 * Es kann max. ein Ergebnis zurueck gegeben werden, da die id der Primaerschluessel ist.
				 * Daher wird geprueft ob ein Ergebnis vorliegt
				 */
		      
		      if (rs.next()) {
		    	//Ergebnis-Tupel in Objekt umwandeln
		    	  note.setId(rs.getInt("maxnID") + 1);
		    	  stmt = con.createStatement();
		    	  
		    	  stmt.executeUpdate("INSERT INTO Note (nID, nbID, nTitle, nSubtitle, nContent, source, nCreDate, nModDate, userID) "
		    	            + "VALUES (" + note.getnID() + "," + note.getNbID() + ","
		    	            + note.getnTitle() + "," + note.getnSubtitle() + "," + note.getnContent() + "," + note.getSource() + "," 
		    	            + note.getnCreDate() + "," + note.getnModDate() +  "," + note.getUserID() + "')");

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
		 
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeUpdate("UPDATE Note" + "SET nTitle=\"" + note.getnTitle() + "\"," + "nSubtitle=\"" + note.getnSubtitle() + "\"" 
			 	+"nContent=\"" + note.getnContent() +"\"," + "source=\"" + note.getSource() + "\"," + "nCreDate=\"" + note.getnCreDate() 
			 	+ "nModDate=\"" + note.getnModDate() + "userID=\"" + note.getUserID() + "WHERE nID=" + note.getnID());
			 //modification Date hinzufuegen --> wie mach ich dass es auf jednefall automatisch hinzugefuegt wird?
			 
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
			 stmt.executeUpdate("DELETE FROM Note" + "WHERE nID=" +note.getnID());	 
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
			 ResultSet rs = stmt.executeQuery("SELECT nID, nbID, userID, nTitle, nSubtitle, nContent, source, nCreDate, nModDate"
					 + "FROM Note" + "ORDER BY nbID");
			
			 // Fuer jeden Eintrag wird ein Notebook-Objekt erstellt	
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
		 * Alle Note-Objekte mit gesuchtem Titel werden ausgelesen
		 * 
		 * @param nTitle
		 * @return Vektor mit Note-Objekten, die den gesuchten Note-Titel (nTitle) enthalten.
		 * Bei Exceptions wird ein teilweise gefuellter oder leerer Vektor zurueckgegeben
		 */
		
	 
	 public Vector<Note> findByTitle(Note note){
		 Connection con = DBConnection.connection();
		 Vector<Note> result = new Vector<Note>();
		 
		 try{
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT nID, nbID, userID, nTitle, nSubtitle, nContent, source, nCreDate, nModDate"
					 + "FROM Note" + "WHERE nTitle LIKE '" + nTitle + "'ORDER BY nCreDate");
			 
			//Fuer jeden Eintrag im Suchergebnis wird ein Note-Objekt erstellt.
			 while(rs.next()) {
				 Note note1 = new Note();
				 note1.setnID(rs.getInt("nID"));
				 note1.setNbID(rs.getInt("nbID"));
				 note1.setUserID(rs.getInt("userID"));
				 note1.setnTitle(rs.getString("nTitle"));
				 note1.setnSubtitle(rs.getString("nSubtitle"));
				 note1.setnContent(rs.getString("nContent"));
				 note1.setSource(rs.getString("source"));
				 note1.setnCreDate(rs.getDate("nCreDate"));
				 note1.setnModDate(rs.getDate("nModDate"));
				 
				//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				 result.addElement(note1);
				 
			 }
		 }
		 
		 catch (SQLException e){
			 e.printStackTrace();
		 }
		 
		//Vektor wird zurueckgegeben
		 return result;
	 }
	 
	 
}
