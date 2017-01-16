package com.hdm.Application.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.hdm.Application.shared.bo.AppUser;

/**
 * Die Mapper-Klasse UserMapper stellt eine Schnittstelle zwischen Applikation
 * und Datenbank dar. Die zu persistierenden User werden hier auf eine
 * relationale Ebene projiziert. Die abzurufenden Profile werden aus den
 * relationalen Tabellen zusammengestellt.
 * 
 * @author Marius Klepser
 */

public class UserMapper {

	/**
	 * Instanziieren des Mappers
	 */
	private static UserMapper userMapper = null;

	/**
	 * Mithilfe des <code>protected</code>-Attributs im Konstruktor wird
	 * verhindert, dass von anderen Klassen eine neue Instanz der Klasse
	 * geschaffen werden kann.
	 */
	protected UserMapper() {
	}


	/**
	 * Aufruf eines USER-Mappers f�r Klassen, die keinen Zugriff auf den
	 * Konstruktor haben.
	 * 
	 * @return Einzigartige Mapper-Instanz zur Benutzung in der
	 *         Applikationsschicht
	 */
	public static UserMapper userMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}

		return userMapper;
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

	public AppUser findByKey(int id) {
		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */

			ResultSet rs = stmt.executeQuery("SELECT appuserid, mail, username FROM users " + "WHERE appuserid='" + id
					+ "' ORDER BY appuserid");

			/**
			 * Da id Primaerschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
			 * werden. Pr�fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {

				AppUser u = new AppUser();
				u.setUserID(rs.getInt("appuserid"));
				u.setUserName(rs.getString("username"));
				u.setMail(rs.getString("mail"));
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	public AppUser findByMail(String mail) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */
			ResultSet rs = stmt.executeQuery("SELECT appuserid, mail, username FROM users " + "WHERE mail='" + mail
					+ "' ORDER BY mail");

			/**
			 * Da id Primaerschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
			 * werden. Pr�fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {

				AppUser u = new AppUser();
				u.setUserID(rs.getInt("appuserid"));
				u.setUserName(rs.getString("username"));
				u.setMail(rs.getString("mail"));
				return u;
			}
		} catch (SQLException e) {
			System.out.println("SQL Abfrage fehlgeschlagen. findbyMail");
			e.printStackTrace();
			
			return null;
		}

		return null;
	}
	
	/**
	 * Read-Methode - Anhand eines Usernamen wird der dazu gehoerige User in
	 * der Datenbank gesucht. Diese Methode ist vor Allem f�r den Login
	 * relevant, der �ber die Google-Email-Adresse realisiert wird. Die
	 * Google-Adresse fungiert also als Username, der von Haus aus eindeutig
	 * ist.
	 * 
	 * @author Marius Klepser
	 * @param id
	 *            Der Username zum User, der aus der Datenbank gelesen werden
	 *            soll
	 * @return Das durch die id referenzierte User-Objekt
	 */

	public Vector<AppUser> findByName(String name) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();
		Vector<AppUser> result = new Vector<AppUser>();
		
		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */

			ResultSet rs = stmt.executeQuery("SELECT appuserid, mail, username FROM users " + "WHERE username='" + name
					+ "' ORDER BY username");

			/**
			 * F�r jeden Eintrag im Suchergebnis wird nun ein Profile-Objekt
			 * erstellt.
			 */
			
			while (rs.next()) {
				AppUser u = new AppUser();
				u.setUserID(rs.getInt("appuserid"));
				u.setUserName(rs.getString("username"));
				u.setMail(rs.getString("mail"));

				/**
				 * Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(u);
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
	 * Read-Methode - Auslesen aller User in einen Vektor.
	 * 
	 * @author Marius Klepser
	 * @return Liste aller derzeit in der Datenbank eingetragenen User.
	 */

	public Vector<AppUser> findAll() {
		Connection con = DBConnection.connection();
		/**
		 * Ergebnisvektor vorbereiten
		 */
		Vector<AppUser> result = new Vector<AppUser>();

		/**
		 * Erzeugen eines neuen SQL-Statements.
		 */

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken.
			 */

			ResultSet rs = stmt.executeQuery("SELECT appuserid, mail, username FROM users" + "ORDER BY appuserid");

			/**
			 * F�r jeden Eintrag im Suchergebnis wird nun ein Profile-Objekt
			 * erstellt.
			 */
			while (rs.next()) {
				AppUser u = new AppUser();
				u.setUserID(rs.getInt("appuserid"));
				u.setUserName(rs.getString("username"));
				u.setMail(rs.getString("mail"));

				/**
				 * Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(u);
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
	 * Insert-Methode - Ein User-Objekt wird �bergeben und die zugehoerigen
	 * Werte in ein SQL-Statement geschrieben, welches ausgef�hrt wird, um das
	 * Objekt in die Datenbank einzutragen.
	 * 
	 * @author Marius Klepser
	 * @param u
	 *            User, der in die Datenbank geschrieben werden soll
	 * @return User-Objekt, das in die Datenbank geschrieben wurde
	 */

	public AppUser insert(AppUser u) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Zunaechst schauen wir nach, welches der momentan hoechste
			 * Primaerschl�sselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(appuserid) AS maxid " + "FROM users ");

			/**
			 * Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
			 */
			if (rs.next()) {
				/**
				 * u erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschl�ssel.
				 */
				u.setUserID(rs.getInt("maxid") + 1);

				/**
				 * Erzeugen eines neuen SQL-Statements.
				 */

				stmt = con.createStatement();

				/**
				 * Jetzt erst erfolgt die tatsaechliche Einf�geoperation
				 */
				stmt.executeUpdate(
						"INSERT INTO users (appuserid, mail, username) "
								+ "VALUES (" + u.getUserID() + ",'" + u.getMail() + "','" + u.getUserName() +"')");

				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * Delete-Methode - Ein User-Objekt wird �bergeben, anhand dessen der
	 * zugehoerige Eintrag in der Datenbank geloescht wird
	 * 
	 * @author Marius Klepser
	 * @param u
	 *            User, der geloescht werden soll
	 */

	public void delete(AppUser u) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */

			stmt.executeUpdate("DELETE FROM users " + "WHERE appuserid=" + u.getUserID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		
	}


	/**
	 * Edit-Methode - Ein Profil wird �bergeben und die zugehoerigen Werte in ein
	 * SQL-Statement geschrieben, welches ausgef�hrt wird, um die
	 * Informationswerte des Profils in der Datenbank zu aktualisieren.
	 * 
	 * @author Marius Klepser
	 * @param profile
	 *            Der User, dessen Variablen in der DB geaendert werden soll.
	 */

	public AppUser edit(AppUser u) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			
			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */

			stmt.executeUpdate("UPDATE users " + "SET username='" + u.getUserName() + "' WHERE appuserid=" + u.getUserID());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Um Analogie zu insert(User u) zu wahren, geben wir u zur�ck
		 */
		return u;
	}
}
