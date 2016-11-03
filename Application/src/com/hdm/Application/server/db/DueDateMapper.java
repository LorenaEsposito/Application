package com.hdm.Application.server.db;

import java.sql.*;
import java.util.Vector;

import com.hdm.Application.shared.bo.DueDate;

/**DueDate Mapper Klasse bildet DueDate-Objekte auf eine relationale Datenbank ab.
 * Diese Klasse stellt Methoden zur Verfuegung, die das erstellen, editieren, auslesen/suchen und loeschen 
 * der gewuenschten Datensaezte erlauben. Die Mapperklasse stellt die Verbindungsschicht zwischen Datenbank
 * und Applikationslogik dar. Datenbank-Strukturen koennen in Objekte umgewandelt werden, jedoch auch Objekte 
 * in Datenbankstrukturen
*/

public class DueDateMapper {
	
	/**Klasse wird nur einmal instantiiert(Singleton)
	 * 
	 */
	
	private static DueDateMapper dueDateMapper = null;
	
	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */

	
	protected DueDateMapper(){
	}
	
	/**Diese Methode ist statisch. Sie stellt die 
	 * Singleton-Eigenschaft sicher, es kann nur eine Instanz von 
	 * DueDateMapper  existieren.
	 * 
	 * @return DueDateMapper-Objekt
	 */
	
	public static DueDateMapper dueDateMapper(){
		if(dueDateMapper == null){
			dueDateMapper = new DueDateMapper();
		}
		return dueDateMapper;
	}
	
	
	/**
	 * Ein spezielles DueDate wird ueber das dueDate (gesuchtes Datum, dDate) gesucht. Es werden 
	 * alle Objekte mit dem gewuscnhten duedate zurueckgegeben
	 * @param dueDate 
	 * @return Date-Objekte, die das gesuchte dueDate besitzten - null bei nicht vorhandenem Tupel
	 */
	
	
	public Vector<DueDate> findByDate(Date dueDate){
		//DB-Verbindung holen
		Connection con = DBConnection.connection();
		Vector<DueDate> result = new Vector<DueDate>();
		
		try{
			//Leeres SQL Statement anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausfuellen und als Query an DB schicken
			ResultSet rs = stmt.executeQuery("SELECT ddID, dDate, nID FROM DueDate" 
					+ "WHERE dDate LIKE" + dueDate + "ORDER BY nID");
			
		
			// Für jeden Eintrag im Suchergebnis wird ein DueDate-Objekt erstellt.
			while (rs.next()){
				Date dueDate = new Date();
				dueDate.setDdId(rs.getInt("ddID"));
				dueDate.setdDate(rs.getDate("dDate"));
				dueDate.setnID(rs.getInt("nID"));
				
				 // Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.addElement(dueDate);
			}
		}
		
		catch (SQLException e){
			e.printStackTrace();
			
		}
		//Ergebnisvektor zurueckgeben
		return result;
		
	}
	
	/**
	 * Auslesen aller DueDates
	 * @return Vektor mit DueDate-Objekten, der alle DueDates enthaelt. 
	 * Trifft eine Exception ein wird ein teilweise gefuellter oder leerer Vektor ausgegeben
	 */
	
	public Vector<DueDate> findAll(){
		Connection con = DBConnection.connection();
		//Ergebnisvektor vorbereiten
		Vector<DueDate> result = new Vector<DueDate>();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ddID, dDate, nID"
					+ "FROM DueDate" + "ORDER BY dDate");
			// Fuer jeden Eintrag im Suchergebnis wird nun ein DueDate-Objekt erstellt
			
			while(rs.next()){
				Date dueDate = new Date();
				dueDate.setDdId(rs.getInt("ddID"));
				dueDate.setdDate(rs.getDate("dDate"));
				dueDate.setnID(rs.getInt("nID"));
				
				//Hinzufuegen des neuen Objekts im Ergebnisvektor
				result.addElement(dueDate);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		//Vektor zurueckgeben
		return result;
	}
	

	
	public Vector<DueDate> findByNoteID(int nID){
		Connection con = DBConnection.connection();
		Vector<DueDate> result = new Vector<DueDate>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ddID, dDate, nID" + "FROM DueDate"
			+ "WHERE nID LIKE'" + nID);
			
			while(rs.next()){
				Date dueDate = new Date();
				dueDate.setDdId(rs.getInt("ddID"));
				dueDate.setdDate(rs.getDate("dDate"));
				dueDate.setnID(rs.getInt("nID"));
				
				result.addElement(dueDate);
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
			ResultSet rs = stmt.executeQuery("SELECT Max(ddID) AS maxDdId+ "FROM DueDate");
			
			if(rs.next()){
				dueDate.setDdId(rs.getInt("maxDdId") + 1);
	
				stmt = con.createStatement();
				
				stmt.executeUpdate("INSERT INTO DueDate (ddID, dDate, nID)" + "VALUES ("
				+ dueDate.getDdId() + ",'" + dueDate.getdDate() + "," + dueDate.getnID() + "')" );
				
			}
			
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return dueDate;
		
	}
	
	public Date updateDueDate(Date dueDate){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE DueDate" + "SET ddID=\"" + dueDate.getDdId()
			+ "SET dDate=\"" + dueDate.getdDate() + "WHERE nID" + dueDate.getnID());
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return dueDate;
	}
	
	
	public void deleteDueDate(Date dueDate){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM DueDate" + "WHERE ddID=" + dueDate.getDdId());
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	
	
	
}
