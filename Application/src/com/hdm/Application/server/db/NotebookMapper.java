package com.hdm.Application.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.User;


/**Notebook Mapper Klasse bildet Notebook-Objekte auf eine relationale Datenbank ab.
 * Diese Klasse stellt Methoden zur Verfuegung, die das erstellen, editieren, auslesen/suchen und loeschen 
 * der gewuenschten Datensaezte erlauben. Die Mapperklasse stellt die Verbindungsschicht zwischen Datenbank
 * und Applikationslogik dar. Datenbank-Strukturen koennen in Objekte umgewandelt werden, jedoch auch Objekte 
 * in Datenbankstrukturen
*/

public class NotebookMapper {
	/**Klasse wird nur einmal instantiiert(Singleton)
	 * 
	 */
	
private static NotebookMapper notebookMapper = null;
	
/**
 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
 * Instanzen dieser Klasse zu erzeugen.
 * 
 */

	protected NotebookMapper(){
	}
	/**Diese Methode ist statisch. Sie stellt die 
	 * Singleton-Eigenschaft sicher, es kann nur eine Instanz von 
	 * NotebeookMapper  existieren.
	 * 
	 * @return NotebookMapper-Objekt
	 */
	
	public static NotebookMapper notebookMapper(){
		if(notebookMapper == null){
			notebookMapper = new NotebookMapper();
		}
		return notebookMapper;
	}
	
	
	/**
	 * Ein spezielles Notebook wird ueber die NotebookID (nbID) gesucht. Es wird genau ein Objekt zurueck gegeben
	 * @param nbID (Primaerschluessel DB)
	 * @return Notebook-Objekt, das die gesuchte nbID besitzt - null bei nicht vorhandenem Tupel
	 */
	
	public Notebook findById(int nbID){
		//DB-Verbindung holen
		Connection con = DBConnection.connection();
		
		try{
			//Leeres SQL Statement anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausfuellen und als Query an DB schicken
			ResultSet rs = stmt.executeQuery("SELECT nbID, userID, nbTitle, nbCreDate, nbModDate FROM Notebook"
					+ "WHERE nbID=" + nbID );
			
			/**
			 * Es kann max. ein Ergebnis zurueck gegeben werden, da die id der Primaerschluessel ist.
			 * Daher wird geprueft ob ein Ergebnis vorliegt
			 */
			
			if (rs.next()){
				//Ergebnis-Tupel in Objekt umwandeln
				Notebook notebook = new Notebook();
				notebook.setNbId(rs.getInt("nbID"));
				notebook.setUserId(rs.getInt("userID"));
				notebook.setNbTitle(rs.getString("nbTitle"));
				notebook.setNbCreDate(rs.getDate("nbCreDate"));
				notebook.setNbModDate(rs.getDate("nbModDate"));
				return notebook;
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		
		return null;
		
	}
	
	/**
	 * Auslesen aller Notebooks
	 * @return Vektor mit Notebook Objekten, der alle Notebooks enthaelt. 
	 * Trifft eine Exception ein wird ein teilweise gefuellter oder leerer Vektor ausgegeben
	 */
	
	public Vector<Notebook> findAll(){
		Connection con = DBConnection.connection();
		//Ergebnisvektor vorbereiten
		Vector<Notebook> result = new Vector<Notebook>();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nbID, userID, nbTitle, nbCreDate, nbModDate, unbID FROM Notebook" 
					+ "ORDER BY userID");
			
			// Fuer jeden Eintrag wird ein Notebook-Objekt erstellt	
			while (rs.next()){
				
				Notebook notebook = new Notebook();
				notebook.setNbId(rs.getInt("nbID"));
				notebook.setUserId(rs.getInt("userID"));
				notebook.setNbTitle(rs.getString("nbTitle"));
				notebook.setNbCreDate(rs.getDate("nbCreDate"));
				notebook.setNbModDate(rs.getDate("nbModDate"));
				
				// Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				result.addElement(notebook);
			}
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
		
		//Vektor wird zurueckgegeben
		return result;
		
	}
	
	
	/**
	 * Alle Notebook-Objekte mit gesuchtem Titel werden ausgelesen
	 * 
	 * @param nbTitle
	 * @return Vektor mit Notebook-Objekten, die den gesuchten Notebook-Titel (nbTitle) enthalten.
	 * Bei Exceptions wird ein teilweise gefuellter oder leerer Vektor zurueckgegeben
	 */
	
	
	public Vector<Notebook> findByTitle(String nbTitle){
		Connection con = DBConnection.connection();
		Vector<Notebook> result = new Vector<Notebook>();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nbID, userID, nbTitle, nbCreDate, nbModDate, unbID FROM Notebook"
					+ "WHERE nbTitle LIKE" + nbTitle + "ORDER BY nCreDate");
			
			//Fuer jeden Eintrag im Suchergebnis wird ein Notebook-Objekt erstellt.
			while(rs.next()){
				Notebook notebook = new Notebook();
				notebook.setNbId(rs.getInt("nbID"));
				notebook.setUserId(rs.getInt("userID"));
				notebook.setNbTitle(rs.getString("nbTitle"));
				notebook.setNbCreDate(rs.getDate("nbCreDate"));
				notebook.setNbModDate(rs.getDate("nbModDate"));
				
				//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
				result.addElement(notebook);
			}
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
		
		//Vektor wird zurueckgegeben
		return result;
	}
	
	/**
	 * Ein Notebook-Objekt wird in der Datenbank hinzugefuegt. Der Primaerschluessel wird ueberprueft 
	 * und gegebenenfalls neu zugeordnet.
	 * @param notebook (Objekt, das gespeichert werden soll)
	 * @return neu uebergebenes Objekt, mit angepasster ID
	 */
	
	public Notebook createNotebook(Notebook notebook){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			//Ueberpruefen welches der aktuell hoechste Primaerschluessel ist.
			ResultSet rs = stmt.executeQuery("SELECT MAX(nbID) AS maxnbID FROM Notebook");
			
			if(rs.next()){
				//notebook erhaelt den aktuell hoechsten und um 1 inkrementierten Primaerschluessel
				
				notebook.setNbId(rs.getInt("maxnbID") + 1);
				stmt = con.createStatement();
				
				//Nun folgt das einfuegen des neuen Objektes.
				stmt.executeUpdate("INSERT INTO Notebook (nbID, userID, nbTitle, nbCreDate, nbModDate)"
						+ "VALUES (" + notebook.getNbId() + ",'" + notebook.getUserId() + "','" + notebook.getNbTitle() + "','" + notebook.getNbCreDate()
						+ "','" + notebook.getNbModDate() + "')" );

			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		/**
		 * Notebook wird zurueckgegeben, da sich das Objekt eventuell im Laufe der Methode veraendert hat
		 */
		
		return notebook;
		
	}
	
	/**
	 * Notebook-Objekt wird ueberarbeitet ind ie Datenbank geschrieben
	 * 
	 * @param notebook (Objekt, dass ueberarbeitet in DB geschrieben wird)
	 * @return als Parameter uebergebenes Objekt.
	 */
	
	
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
	
	
	/**
	 * Ein Notebook-Objekt soll mit seinen Daten aus der DB geloescht werden.
	 * 
	 * @param notebook (Objekt wird aus DB geloescht)
	 */
	
	
	public void deleteNotebook(Notebook notebook){
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Notebook" + "WHERE nbID=" + notebook.getNbId());
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Zugehoerige Notebook-Elemente eines gegebenen Users auslesen
	 * @param user, dessen Notebooks ausgelesen werden sollen
	 * @return ein Vektor mit allen Notebooks des Users
	 */
	
	
	public Vector<Notebook> getNotebookOfUser(User user){
		
		/**
		 * Der UserMapper wird benoetigt. Diesem wird der in den User-Objekten enthaltene 
		 * Primaerschluessel uebergeben. Der Notebook Mapper loest diese ID in eine Reihe von Notebook-Objekten auf.
		 */
		return UserMapper.userMapper().findByUser(user);
	}

}
