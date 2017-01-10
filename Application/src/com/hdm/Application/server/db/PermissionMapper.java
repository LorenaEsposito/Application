package com.hdm.Application.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.hdm.Application.shared.bo.Permission;

/**
 * Die Mapper-Klasse PermissionMapper stellt eine Schnittstelle zwischen
 * Applikation und Datenbank dar. Die zu persistierenden Permissions werden hier
 * auf eine relationale Ebene projiziert.
 * 
 * @author Marius Klepser
 */

public class PermissionMapper {

	/**
	 * Instanziieren des Mappers
	 */
	private static PermissionMapper permissionMapper = null;

	/**
	 * Mithilfe des <code>protected</code>-Attributs im Konstruktor wird
	 * verhindert, dass von anderen Klassen eine neue Instanz der Klasse
	 * geschaffen werden kann.
	 */
	protected PermissionMapper() {
	}

	/**
	 * Aufruf eines Permission-Mappers f�r Klassen, die keinen Zugriff auf den
	 * Konstruktor haben.
	 * 
	 * @return Einzigartige Mapper-Instanz zur Benutzung in der
	 *         Applikationsschicht
	 */
	public static PermissionMapper permissionMapper() {
		if (permissionMapper == null) {
			permissionMapper = new PermissionMapper();
		}

		return permissionMapper;
	}

	/**
	 * Read-Methode - Anhand einer vorgegebenen id wird die dazu gehoerige
	 * Permission in der Datenbank gesucht.
	 * 
	 * @author Marius Klepser
	 * @param id
	 *            Die id der Permission, die aus der Datenbank gelesen werden
	 *            soll
	 * @return Das durch die id referenzierte Permission-Objekt
	 * 
	 */

	public Permission findByKey(int id) {
		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */
			ResultSet rs = stmt
					.executeQuery("SELECT id, permissionType, userID, nID, nbID FROM permissions "
							+ "WHERE id='" + id + "' ORDER BY id");

			/**
			 * Da id Primaerschl�ssel ist, kann max. nur ein Tupel
			 * zur�ckgegeben werden. Pr�fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {

				Permission p = new Permission();
				p.setPermissionID(rs.getInt("id"));
				p.setPermissionType(rs.getInt("permissionType"));
				p.setUserID(rs.getInt("userID"));
				p.setNID(rs.getInt("nID"));
				p.setNbID(rs.getInt("nbID"));
				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Read-Methode - Auslesen aller Permissions in einen Vektor.
	 * 
	 * @author Marius Klepser
	 * @return Liste aller derzeit in der Datenbank eingetragenen Permissions.
	 */

	public Vector<Permission> findAll() {
		Connection con = DBConnection.connection();
		/**
		 * Ergebnisvektor vorbereiten
		 */
		Vector<Permission> result = new Vector<Permission>();

		/**
		 * Erzeugen eines neuen SQL-Statements.
		 */

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken.
			 */

			ResultSet rs = stmt
					.executeQuery("SELECT id, uID, nID, nbID, permissionType FROM permissions"
							+ "ORDER BY id");

			/**
			 * F�r jeden Eintrag im Suchergebnis wird nun ein Profile-Objekt
			 * erstellt.
			 */
			while (rs.next()) {

				Permission p = new Permission();
				p.setPermissionID(rs.getInt("id"));
				p.setPermissionType(rs.getInt("permissionType"));
				p.setUserID(rs.getInt("userID"));
				p.setNID(rs.getInt("nID"));
				p.setNbID(rs.getInt("nbID"));

				/**
				 * Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Ergebnisvektor zur�ckgeben
		 */
		return result;
	}

	/**
	 * Insert-Methode - Ein Permission-Objekt wird �bergeben und die
	 * zugehoerigen Werte in ein SQL-Statement geschrieben, welches ausgef�hrt
	 * wird, um das Objekt in die Datenbank einzutragen.
	 * 
	 * @author Marius Klepser
	 * @param p
	 *            Permission, die in die Datenbank geschrieben werden soll
	 * @return Permission-Objekt, das in die Datenbank geschrieben wurde
	 */

	public Permission insert(Permission p) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Zunaechst schauen wir nach, welches der momentan hoechste
			 * Primaerschl�sselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(pid) AS maxid "
					+ "FROM permissions ");

			/**
			 * Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
			 */
			if (rs.next()) {
				/**
				 * p erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschl�ssel.
				 */
				p.setPermissionID(rs.getInt("maxid") + 1);

				/**
				 * Erzeugen eines neuen SQL-Statements.
				 */

				stmt = con.createStatement();

				/**
				 * Jetzt erst erfolgt die tatsaechliche Einf�geoperation
				 */
				stmt.executeUpdate("INSERT INTO permissions (pid, permtype, appuserid, nid, nbid, isowner) "
						+ "VALUES ("
						+ p.getPermissionID()
						+ ","
						+ p.getPermissionType()
						+ ","
						+ p.getUserID()
						+ ","
						+ p.getNID()
						+ ","
						+ p.getNbID()
						+ ","
						+ p.getIsOwner()
						+ ")");

				System.out
						.println("INSERT INTO permissions (pid, permtype, appuserid, nid, nbid, isowner) "
								+ "VALUES ("
								+ p.getPermissionID()
								+ ","
								+ p.getPermissionType()
								+ ","
								+ p.getUserID()
								+ ","
								+ p.getNID()
								+ ","
								+ p.getNbID()
								+ ","
								+ p.getIsOwner()
								+ ")");

				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * Delete-Methode - Ein Permission-Objekt wird �bergeben, anhand dessen
	 * der zugehoerige Eintrag in der Datenbank geloescht wird
	 * 
	 * @author Marius Klepser
	 * @param p
	 *            Permission, die geloescht werden soll
	 */

	public void delete(Permission p) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */

			stmt.executeUpdate("DELETE FROM permissions " + "WHERE id="
					+ p.getPermissionID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		public Vector<Permission> findOwnerByUserId(int appUserID){
			
			//DB-Verbindung holen und Variablen zurücksetzen
			Connection con = DBConnection.connection();
			
			Vector<Permission> result = new Vector<Permission>();
			
			try{
				//Leeres SQL Statement anlegen
				Statement stmt = con.createStatement();
				
				//Statement ausfuellen und als Query an DB schicken
				ResultSet rs = stmt.executeQuery("SELECT pid, appuserid, isowner, nbid, nid, permtype FROM permissions "
						+ "WHERE appuserid=" + appUserID
						+ " AND isowner=1");
				
				while (rs.next()) {
					
					Permission p = new Permission();
					p.setPermissionID(rs.getInt("pid"));
					p.setUserID(rs.getInt("appuserid"));
					p.setIsOwner(rs.getBoolean("isowner"));
					p.setNbID(rs.getInt("nbid"));
					p.setNID(rs.getInt("nid"));
					p.setPermissionType(rs.getInt("permtype"));

					/**
					 * Hinzuf�gen des neuen Objekts zum Ergebnisvektor
					 */
					result.addElement(p);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;
	}
		

public Vector<Permission> findOwnedNotebooks(int appUserID){
			
			//DB-Verbindung holen und Variablen zurücksetzen
			Connection con = DBConnection.connection();
			
			Vector<Permission> result = new Vector<Permission>();
			
			try{
				//Leeres SQL Statement anlegen
				Statement stmt = con.createStatement();
				
				//Statement ausfuellen und als Query an DB schicken
				ResultSet rs = stmt.executeQuery("SELECT pid, appuserid, isowner, nbid, nid, permtype FROM permissions "
						+ "WHERE appuserid=" + appUserID + " AND nid=0"
						+ " AND isowner=1");
				
				while (rs.next()) {
					
					Permission p = new Permission();
					p.setPermissionID(rs.getInt("pid"));
					p.setUserID(rs.getInt("appuserid"));
					p.setIsOwner(rs.getBoolean("isowner"));
					p.setNbID(rs.getInt("nbid"));
					p.setNID(rs.getInt("nid"));
					p.setPermissionType(rs.getInt("permtype"));

					/**
					 * Hinzuf�gen des neuen Objekts zum Ergebnisvektor
					 */

					result.addElement(p);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;
	}
		
		public Vector<Permission> findByNotebookID(int nbID){
			Connection con = DBConnection.connection();
			Vector<Permission> result = new Vector<Permission>();
			
			try{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT pid, appuserid, isowner, nbid, nid, permtype FROM permissions"
						+ " WHERE nbid LIKE " + nbID + " ORDER BY pid");
				
				//Fuer jeden Eintrag im Suchergebnis wird ein Permission-Objekt erstellt.
				while(rs.next()){
					Permission permission = new Permission();
					permission.setPermissionID(rs.getInt("pid"));
					permission.setUserID(rs.getInt("appuserid"));
					permission.setIsOwner(rs.getBoolean("isowner"));
					permission.setNbID(rs.getInt("nbid"));
					permission.setNID(rs.getInt("nid"));
					permission.setPermissionType(rs.getInt("permtype"));
					//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
					result.addElement(permission);
				}
			}
			
			catch (SQLException e){
				e.printStackTrace();
			}
			
			//Vektor wird zurueckgegeben
			return result;
		}
		
		public Vector<Permission> findByNoteID(int nID){
			Connection con = DBConnection.connection();
			Vector<Permission> result = new Vector<Permission>();
			
			try{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT pid, appuserid, isowner, nbid, nid, permtype FROM permissions"
						+ " WHERE nid LIKE " + nID + " ORDER BY pid");
				
				//Fuer jeden Eintrag im Suchergebnis wird ein Permission-Objekt erstellt.
				while(rs.next()){
					Permission permission = new Permission();
					permission.setPermissionID(rs.getInt("pid"));
					permission.setUserID(rs.getInt("appuserid"));
					permission.setIsOwner(rs.getBoolean("isowner"));
					permission.setNbID(rs.getInt("nbid"));
					permission.setNID(rs.getInt("nid"));
					permission.setPermissionType(rs.getInt("permtype"));
					//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
					result.addElement(permission);
				}
			}
			
			catch (SQLException e){
				e.printStackTrace();
			}
			
			//Vektor wird zurueckgegeben
			return result;
		}
		
		
		public Vector<Permission> findByUserID(int uID){
			Connection con = DBConnection.connection();
			Vector<Permission> result = new Vector<Permission>();
			
			try{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT pid, appuserid, isowner, nbid, nid, permtype FROM permissions"
						+ " WHERE appuserid LIKE " + uID + " ORDER BY pid");
				
				//Fuer jeden Eintrag im Suchergebnis wird ein Permission-Objekt erstellt.
				while(rs.next()){
					Permission permission = new Permission();
					permission.setPermissionID(rs.getInt("pid"));
					permission.setUserID(rs.getInt("appuserid"));
					permission.setIsOwner(rs.getBoolean("isowner"));
					permission.setNbID(rs.getInt("nbid"));
					permission.setNID(rs.getInt("nid"));
					permission.setPermissionType(rs.getInt("permtype"));
					//Neues Objekt wird dem Ergebnisvektor hinzugefuegt
					result.addElement(permission);
				}
			}
			
			catch (SQLException e){
				e.printStackTrace();
			}
			
			//Vektor wird zurueckgegeben
			return result;
		}
	

	public Permission edit(Permission p) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */

			stmt.executeUpdate("UPDATE permissions " + "SET permtype=\""
					+ p.getPermissionType() + "\", " + " WHERE id="
					+ p.getPermissionID());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Um Analogie zu insert (Permission p) zu wahren, geben wir p zur�ck
		 */
		return p;
	}
}
