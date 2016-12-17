package com.hdm.Application.server.db;

import java.sql.*;
import java.util.Vector;

import com.hdm.Application.shared.bo.DueDate;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.AppUser;

/**
 * DueDate Mapper Klasse bildet DueDate-Objekte auf eine relationale Datenbank
 * ab. Diese Klasse stellt Methoden zur Verfuegung, die das erstellen,
 * editieren, auslesen/suchen und loeschen der gewuenschten Datensaezte
 * erlauben. Die Mapperklasse stellt die Verbindungsschicht zwischen Datenbank
 * und Applikationslogik dar. Datenbank-Strukturen koennen in Objekte
 * umgewandelt werden, jedoch auch Objekte in Datenbankstrukturen
 */

public class DueDateMapper {

	/**
	 * Klasse wird nur einmal instantiiert(Singleton)
	 * 
	 */

	private static DueDateMapper dueDateMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */

	protected DueDateMapper() {
	}

	/**
	 * Diese Methode ist statisch. Sie stellt die Singleton-Eigenschaft sicher,
	 * es kann nur eine Instanz von DueDateMapper existieren.
	 * 
	 * @return DueDateMapper-Objekt
	 */

	public static DueDateMapper dueDateMapper() {
		if (dueDateMapper == null) {
			dueDateMapper = new DueDateMapper();
		}
		return dueDateMapper;
	}

	/**
	 * Alle DueDate-Objekte mit gesuchtem Datum werden ausgelesen
	 * 
	 * @param dueDate
	 * @return Vektor mit DueDate-Objekten, die das gesuchte DueDate (dDate)
	 *         enthalten. Bei Exceptions wird ein teilweise gefuellter oder
	 *         leerer Vektor zurueckgegeben
	 */

	public Vector<DueDate> findByDate(Date dDate) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();
		Vector<DueDate> result = new Vector<DueDate>();

		try {
			// Leeres SQL Statement anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT ddID, dDate, nID FROM DueDate"
							+ "WHERE dDate LIKE" + dDate + "ORDER BY nID");

			// Für jeden Eintrag im Suchergebnis wird ein DueDate-Objekt
			// erstellt.
			while (rs.next()) {
				DueDate dueDate = new DueDate();
				dueDate.setDdID(rs.getInt("ddID"));
				dueDate.setdDate(rs.getDate("dDate"));
				dueDate.setnID(rs.getInt("nID"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.addElement(dueDate);
			}
		}

		catch (SQLException e) {
			e.printStackTrace();

		}
		// Ergebnisvektor zurueckgeben
		return result;

	}

	/**
	 * Auslesen aller DueDates
	 * 
	 * @return Vektor mit DueDate-Objekten, der alle DueDates enthaelt. Trifft
	 *         eine Exception ein wird ein teilweise gefuellter oder leerer
	 *         Vektor ausgegeben
	 */

	public Vector<DueDate> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		Vector<DueDate> result = new Vector<DueDate>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ddID, dDate, nID"
					+ "FROM DueDate" + "ORDER BY dDate");
			// Fuer jeden Eintrag im Suchergebnis wird nun ein DueDate-Objekt
			// erstellt

			while (rs.next()) {
				DueDate dueDate = new DueDate();
				dueDate.setDdID(rs.getInt("ddID"));
				dueDate.setdDate(rs.getDate("dDate"));
				dueDate.setnID(rs.getInt("nID"));

				// Hinzufuegen des neuen Objekts im Ergebnisvektor
				result.addElement(dueDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Vektor zurueckgeben
		return result;
	}

	/**
	 * Ein spezielles DueDate wird ueber die NoteID (nID) gesucht. Es wird genau
	 * ein Objekt zurueck gegeben
	 * 
	 * @param nID
	 * @return Date-Objekt, das die gesuchte nID besitzt - null bei nicht
	 *         vorhandenem Tupel
	 */

	public DueDate findByNoteID(int nID) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ddID, dDate, nID"
					+ "FROM DueDate" + "WHERE nID LIKE'" + nID);

			/*
			 * Da nID Primärschlüssel ist, kann max. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */

			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				DueDate dueDate = new DueDate();
				dueDate.setDdID(rs.getInt("ddID"));
				dueDate.setdDate(rs.getDate("dDate"));
				dueDate.setnID(rs.getInt("nID"));

				return dueDate;
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Ein DueDate-Objekt wird in der Datenbank hinzugefuegt. Der
	 * Primaerschluessel wird ueberprueft und gegebenenfalls neu zugeordnet.
	 * 
	 * @param dueDate
	 *            (Objekt, das gespeichert werden soll)
	 * @return neu uebergebenes Objekt, mit angepasster ID
	 */

	public DueDate createDueDate(DueDate dueDate) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Ueberpruefen welches der aktuell hoechste Primaerschluessel ist.
			ResultSet rs = stmt.executeQuery("SELECT Max(ddID) AS maxDdId"
					+ "FROM DueDate");

			if (rs.next()) {
				// notebook erhaelt den aktuell hoechsten und um 1
				// inkrementierten Primaerschluessel

				dueDate.setDdID(rs.getInt("maxDdId") + 1);
				stmt = con.createStatement();

				// Neues Objekt wird eingefuegt
				stmt.executeUpdate("INSERT INTO DueDate (ddID, dDate, nID)"
						+ "VALUES (" + dueDate.getDdID() + ",'"
						+ dueDate.getdDate() + "," + dueDate.getnID() + "')");

			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * dueDate wird zurueckgegeben, da sich das Objekt eventuell im Laufe
		 * der Methode veraendert hat
		 */

		return dueDate;

	}

	/**
	 * DueDate-Objekt wird ueberarbeitet in die Datenbank geschrieben
	 * 
	 * @param dueDate
	 *            (Objekt, dass ueberarbeitet in DB geschrieben wird)
	 * @return als Parameter uebergebenes Objekt.
	 */

	public DueDate updateDueDate(DueDate dueDate) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE DueDate" + "SET ddID=\""
					+ dueDate.getDdID() + "SET dDate=\"" + dueDate.getdDate()
					+ "WHERE nID" + dueDate.getnID());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dueDate;
	}

	/**
	 * Ein DueDate-Objekt soll mit seinen Daten aus der DB geloescht werden.
	 * 
	 * @param dueDate
	 *            (Objekt wird aus DB geloescht)
	 */

	public void deleteDueDate(DueDate dueDate) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM duedates" + "WHERE ddID="
					+ dueDate.getDdID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteAllNoteDueDates(Note note) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM duedates" + "WHERE nID="
					+ note.getnID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteAllNotebookDueDates(Notebook nb) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nid, nbid, FROM notes"
					+ "WHERE nbid =" + nb.getNbID());

			// Fuer jeden Eintrag wird ein Notebook-Objekt erstellt
			while (rs.next()) {

				Integer idInt = new Integer(rs.getInt("nid"));
				Statement stmt2 = con.createStatement();
				stmt2.executeUpdate("DELETE FROM duedates" + "WHERE nID="
						+ idInt.intValue());
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void deleteAllUserDueDates(AppUser u) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT userid, nid, FROM permissions"
							+ "WHERE userid =" + u.getUserID()
							+ "AND WHERE isowner = 1");

			// Fuer jeden Eintrag wird ein Notebook-Objekt erstellt
			while (rs.next()) {

				Integer idInt = new Integer(rs.getInt("nid"));
				Statement stmt2 = con.createStatement();
				stmt2.executeUpdate("DELETE FROM duedates" + "WHERE nID="
						+ idInt.intValue());
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
