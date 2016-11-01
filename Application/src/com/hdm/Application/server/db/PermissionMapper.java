package com.hdm.Application.server.db;

	import java.sql.Connection;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.text.SimpleDateFormat;
	import java.util.ArrayList;
	import java.util.Vector;

	import com.hdm.Application.shared.bo.Permission;

	/**
	 * Die Mapper-Klasse PermissionMapper stellt eine Schnittstelle zwischen Applikation
	 * und Datenbank dar. Die zu persistierenden Permissions werden hier auf eine
	 * relationale Ebene projiziert.
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
		 * Aufruf eines Permission-Mappers für Klassen, die keinen Zugriff auf den
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
		 * Read-Methode - Anhand einer vorgegebenen id wird der dazu gehoerige User
		 * in der Datenbank gesucht.
		 * 
		 * @author Marius Klepser
		 * @param id
		 *            Die id des Users, der aus der Datenbank gelesen werden soll
		 * @return Das durch die id referenzierte User-Objekt
		 * 
		 */

		public User findByKey(int id) {
			/**
			 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
			 */
			Connection con = DBConnection.connection();

			try {
				Statement stmt = con.createStatement();

				/**
				 * Statement ausfüllen und als Query an die DB schicken
				 */
				ResultSet rs = stmt.executeQuery("SELECT id, userName FROM users " + "WHERE id='" + id
						+ "' ORDER BY id");

				/**
				 * Da id Primaerschlüssel ist, kann max. nur ein Tupel zurückgegeben
				 * werden. Prüfe, ob ein Ergebnis vorliegt.
				 */
				if (rs.next()) {

					User u = new User();
					u.setId(rs.getInt("id"));
					u.setUserName(rs.getString("userName"));
					return u;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

			return null;
		}

		/**
		 * Read-Methode - Anhand eines Usernamen wird der dazu gehoerige User in
		 * der Datenbank gesucht. Diese Methode ist vor Allem für den Login
		 * relevant, der über die Google-Email-Adresse realisiert wird. Die
		 * Google-Adresse fungiert also als Username, der von Haus aus eindeutig
		 * ist.
		 * 
		 * @author Marius Klepser
		 * @param id
		 *            Der Username zum User, der aus der Datenbank gelesen werden
		 *            soll
		 * @return Das durch die id referenzierte User-Objekt
		 * 
		 */

		public User findByName(String name) {
			/**
			 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
			 */
			Connection con = DBConnection.connection();

			try {
				Statement stmt = con.createStatement();

				/**
				 * Statement ausfüllen und als Query an die DB schicken
				 */
				ResultSet rs = stmt.executeQuery("SELECT id, userName FROM users " + "WHERE userName='" + name
						+ "' ORDER BY userName");

				/**
				 * Da id Primaerschlüssel ist, kann max. nur ein Tupel zurückgegeben
				 * werden. Prüfe, ob ein Ergebnis vorliegt.
				 */
				if (rs.next()) {
					/**
					 * Ergebnis-Tupel in Objekt umwandeln
					 */
					User u = new User();
					u.setId(rs.getInt("id"));
					u.setUserName(rs.getString("userName"));
					
					return u;
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
				 * Statement ausfüllen und als Query an die DB schicken.
				 */

				ResultSet rs = stmt.executeQuery("SELECT id, uID, nID, nbID, permissionType FROM permissions" + "ORDER BY id");

				/**
				 * Für jeden Eintrag im Suchergebnis wird nun ein Profile-Objekt
				 * erstellt.
				 */
				while (rs.next()) {
					Permission p = new Permission();
					p.setId(rs.getInt("id"));
					p.setuID(rs.getInt("uID"));
					p.setnID(rs.getInt("nID"));
					p.setnbID(rs.getInt("nbID"));
					p.setPermissionType(rs.getInt("permissionType"));


					/**
					 * Hinzufügen des neuen Objekts zum Ergebnisvektor
					 */
					result.addElement(p);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			/**
			 * Ergebnisvektor zurückgeben
			 */
			return result;
		}

		/**
		 * Insert-Methode - Ein Permission-Objekt wird übergeben und die zugehoerigen
		 * Werte in ein SQL-Statement geschrieben, welches ausgeführt wird, um das
		 * Objekt in die Datenbank einzutragen.
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
				 * Primaerschlüsselwert ist.
				 */
				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM permissions ");

				/**
				 * Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
				 */
				if (rs.next()) {
					/**
					 * p erhaelt den bisher maximalen, nun um 1 inkrementierten
					 * Primaerschlüssel.
					 */
					p.setId(rs.getInt("maxid") + 1);

					/**
					 * Erzeugen eines neuen SQL-Statements.
					 */

					stmt = con.createStatement();

					/**
					 * Jetzt erst erfolgt die tatsaechliche Einfügeoperation
					 */
					stmt.executeUpdate(
							"INSERT INTO permissions (id, uID, nID, nbID, permissionType) "
									+ "VALUES (" + p.getId() + ",'" + p.getuID() + ",'" + p.getnID()
									+ ",'" + p.getnbID() + ",'" + p.getPermissionType() + "')");

					System.out.println(
							"INSERT INTO permissions (id, uID, nID, nbID, permissionType) "
									+ "VALUES (" + p.getId() + ",'" + p.getuID() + ",'" + p.getnID()
									+ ",'" + p.getnbID() + ",'" + p.getPermissionType() + "')");

					return p;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

			return null;

		}

		/**
		 * Delete-Methode - Ein Permission-Objekt wird übergeben, anhand dessen der
		 * zugehoerige Eintrag in der Datenbank geloescht wird
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
				 * Statement ausfüllen und als Query an die DB schicken
				 */

				stmt.executeUpdate("DELETE FROM permissions " + "WHERE id=" + p.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

		


