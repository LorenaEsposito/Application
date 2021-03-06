package com.hdm.Application.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.server.db.NoteMapper;
import com.hdm.Application.server.db.DueDateMapper;

/**
 * Notebook Mapper Klasse bildet Notebook-Objekte auf eine relationale Datenbank
 * ab. Diese Klasse stellt Methoden zur Verfuegung, die das erstellen,
 * editieren, auslesen/suchen und loeschen der gewuenschten Datensaezte
 * erlauben. Die Mapperklasse stellt die Verbindungsschicht zwischen Datenbank
 * und Applikationslogik dar. Datenbank-Strukturen koennen in Objekte
 * umgewandelt werden, jedoch auch Objekte in Datenbankstrukturen
 */

public class NotebookMapper {

	/**
	 * Klasse wird nur einmal instantiiert(Singleton)
	 * 
	 */

	private static NotebookMapper notebookMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */

	protected NotebookMapper() {
	}

	/**
	 * Diese Methode ist statisch. Sie stellt die Singleton-Eigenschaft sicher,
	 * es kann nur eine Instanz von NotebeookMapper existieren.
	 * 
	 * @return NotebookMapper-Objekt
	 */

	public static NotebookMapper notebookMapper() {
		if (notebookMapper == null) {
			notebookMapper = new NotebookMapper();
		}
		return notebookMapper;
	}

	/**
	 * Ein spezielles Notebook wird ueber die NotebookID (nbID) gesucht. Es wird
	 * genau ein Objekt zurueck gegeben
	 * 
	 * @param nbID
	 *            (Primaerschluessel DB)
	 * @return Notebook-Objekt, das die gesuchte nbID besitzt - null bei nicht
	 *         vorhandenem Tupel
	 */

	public Notebook findById(int nbID) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL Statement anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an DB schicken

			ResultSet rs = stmt

					.executeQuery("SELECT nbid, title, creadate, moddate FROM notebooks "
							+ "WHERE nbid=" + nbID);

			/**
			 * Es kann max. ein Ergebnis zurueck gegeben werden, da die id der
			 * Primaerschluessel ist. Daher wird geprueft ob ein Ergebnis
			 * vorliegt
			 */

			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Notebook notebook = new Notebook();
				notebook.setNbID(rs.getInt("nbid"));
				notebook.setNbTitle(rs.getString("title"));
				notebook.setNbCreDate(rs.getDate("creadate"));
				notebook.setNbModDate(rs.getDate("moddate"));
				return notebook;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	public Vector<Notebook> findByUser(AppUser user) {

		// DB-Verbindung holen und Variablen zurücksetzen
		int appUserID = user.getUserID();
		Connection con = DBConnection.connection();
		int nbID = 0;

		Vector<Notebook> result = new Vector<Notebook>();
		
		Vector<Integer> nbIDs = new Vector<Integer>();
		
		try{
			//Leeres SQL Statement anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausfuellen und als Query an DB schicken
			ResultSet rs = stmt.executeQuery("SELECT nbid FROM permissions "
					+ "WHERE appuserid=" + appUserID);
			
			while (rs.next()){
				
			
			nbID = rs.getInt("nbid");
			
			nbIDs.add(nbID);
			
			}

	}
	catch (SQLException e){
		e.printStackTrace();
		return null;
	}
	
	try{
		
		for(int i = 0; i < nbIDs.size(); i++){
			
			nbID = nbIDs.get(i);
			
				//Leeres SQL Statement anlegen
				Statement stmt2 = con.createStatement();
				
				//Statement ausfuellen und als Query an DB schicken
				ResultSet rs2 = stmt2.executeQuery("SELECT nbid, title, creadate, moddate FROM notebooks "
						+ "WHERE nbid=" + nbID);
				
				while (rs2.next()){
					
				
					Notebook notebook = new Notebook();
					notebook.setNbID(rs2.getInt("nbid"));
					notebook.setNbTitle(rs2.getString("title"));
					notebook.setNbCreDate(rs2.getDate("creadate"));
					notebook.setNbModDate(rs2.getDate("moddate"));
					
					// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
					result.addElement(notebook);
				
				}
		}
	}
		catch (SQLException e){

			e.printStackTrace();
			return null;
		}
		return result;

	}

	/**
	 * Auslesen aller Notebooks
	 * 
	 * @return Vektor mit Notebook Objekten, der alle Notebooks enthaelt. Trifft
	 *         eine Exception ein wird ein teilweise gefuellter oder leerer
	 *         Vektor ausgegeben
	 */

	public Vector<Notebook> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		Vector<Notebook> result = new Vector<Notebook>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT nbid, title, creadate, moddate FROM notebooks"
							+ "ORDER BY appuserid");

			// Fuer jeden Eintrag wird ein Notebook-Objekt erstellt
			while (rs.next()) {

				Notebook notebook = new Notebook();
				notebook.setNbID(rs.getInt("nbid"));
				notebook.setNbTitle(rs.getString("title"));
				notebook.setNbCreDate(rs.getDate("creadate"));
				notebook.setNbModDate(rs.getDate("moddate"));

				// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				result.addElement(notebook);
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		// Vektor wird zurueckgegeben
		return result;

	}

	/**
	 * Alle Notebook-Objekte mit gesuchtem Titel werden ausgelesen
	 * 
	 * @param nbTitle
	 * @return Vektor mit Notebook-Objekten, die den gesuchten Notebook-Titel
	 *         (nbTitle) enthalten. Bei Exceptions wird ein teilweise gefuellter
	 *         oder leerer Vektor zurueckgegeben
	 */

	public Vector<Notebook> findByTitle(String nbTitle) {
		Connection con = DBConnection.connection();
		Vector<Notebook> result = new Vector<Notebook>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM notebooks"
					+ " WHERE title LIKE '%" + nbTitle + "%' ORDER BY creadate");
			
			//Fuer jeden Eintrag im Suchergebnis wird ein Notebook-Objekt erstellt.
			while(rs.next()){
				Notebook notebook = new Notebook();
				notebook.setNbID(rs.getInt("nbid"));
				notebook.setNbTitle(rs.getString("title"));
				notebook.setNbCreDate(rs.getDate("creadate"));
				notebook.setNbModDate(rs.getDate("moddate"));

				// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				result.addElement(notebook);
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		// Vektor wird zurueckgegeben
		return result;
	}

	/**
	 * Ein Notebook-Objekt wird in der Datenbank hinzugefuegt. Der
	 * Primaerschluessel wird ueberprueft und gegebenenfalls neu zugeordnet.
	 * 
	 * @param notebook
	 *            (Objekt, das gespeichert werden soll)
	 * @return neu uebergebenes Objekt, mit angepasster ID
	 */

	public Notebook createNotebook(Notebook notebook) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Ueberpruefen welches der aktuell hoechste Primaerschluessel ist.
			ResultSet rs = stmt
					.executeQuery("SELECT MAX(nbid) AS 'maxnbID' FROM notebooks");
			
			String creDate = null;
			if (notebook.getNbCreDate() != null) {
				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				creDate = mySQLformat.format(notebook.getNbCreDate());
			}

			if (rs.next()) {
				// notebook erhaelt den aktuell hoechsten und um 1
				// inkrementierten Primaerschluessel

				notebook.setNbID(rs.getInt("maxnbID") + 1);
				stmt = con.createStatement();

				// Neues Objekt wird eingefuegt
				stmt.executeUpdate("INSERT INTO notebooks (nbid, title, creadate, moddate) "
						+ "VALUES ("
						+ notebook.getNbID()
						+ ", '"
						+ notebook.getNbTitle()
						+ "', '"
						+ creDate
						+ "', '"
						+ creDate + "')");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Notebook wird zurueckgegeben, da sich das Objekt eventuell im Laufe
		 * der Methode veraendert hat
		 */

		return notebook;

	}

	/**
	 * Notebook-Objekt wird ueberarbeitet in die Datenbank geschrieben
	 * 
	 * @param notebook
	 *            (Objekt, dass ueberarbeitet in DB geschrieben wird)
	 * @return als Parameter uebergebenes Objekt.
	 */

	public Notebook updateNotebook(Notebook notebook) {
		Connection con = DBConnection.connection();
		
		String modDate = null;
		if (notebook.getNbModDate() != null) {
			SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			modDate = mySQLformat.format(notebook.getNbModDate());
		}

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE notebooks" + " SET title='"
					+ notebook.getNbTitle() + "', " + "moddate='" + modDate + "' WHERE nbid="
					+ notebook.getNbID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update successfull");
		return notebook;
	}

	/**
	 * Ein Notebook-Objekt soll mit seinen Daten aus der DB geloescht werden.
	 * 
	 * @param notebook
	 *            (Objekt wird aus DB geloescht)
	 */

	public void deleteNotebook(Notebook notebook) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM notebooks " + "WHERE nbid="
					+ notebook.getNbID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteAllUserNotebooks(AppUser u) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nbid FROM permissions"
					+ "WHERE appuserid =" + u.getUserID()
					+ "AND WHERE isowner = 1");

			// Fuer jeden Eintrag wird ein Notebook-Objekt erstellt
			while (rs.next()) {

				Integer idInt = new Integer(rs.getInt("nid"));
				Statement stmt2 = con.createStatement();
				stmt2.executeUpdate("DELETE FROM notebooks" + "WHERE nbid="
						+ idInt.intValue());
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object getNbID() {
		// TODO Auto-generated method stub
		return null;
	}

}
