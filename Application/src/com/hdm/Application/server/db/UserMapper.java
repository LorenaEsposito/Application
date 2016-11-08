package com.hdm.Application.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import com.hdm.Application.shared.bo.User;

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

	public User findByKey(int id) {
		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, gid, name FROM users " + "WHERE id='" + id
					+ "' ORDER BY id");

			/**
			 * Da id Primaerschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
			 * werden. Pr�fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {

				User u = new User();
				u.setUserID(rs.getInt("id"));
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
	 * 
	 */

	public Vector<User> findByName(String name) {
		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		Vector<User> result = new Vector<User>();
		
		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, , gid, name FROM users " + "WHERE userName='" + name
					+ "' ORDER BY userName");

			/**
			 * F�r jeden Eintrag im Suchergebnis wird nun ein Profile-Objekt
			 * erstellt.
			 */
			
			while (rs.next()) {
				User u = new User();
				u.setUserID(rs.getInt("id"));
				u.setUserName(rs.getString("userName"));

				/**
				 * Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read-Methode - Auslesen aller User in einen Vektor.
	 * 
	 * @author Marius Klepser
	 * @return Liste aller derzeit in der Datenbank eingetragenen User.
	 */

	public Vector<User> findAll() {
		Connection con = DBConnection.connection();
		/**
		 * Ergebnisvektor vorbereiten
		 */
		Vector<User> result = new Vector<User>();

		/**
		 * Erzeugen eines neuen SQL-Statements.
		 */

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken.
			 */

			ResultSet rs = stmt.executeQuery("SELECT id, gid, name FROM users" + "ORDER BY id");

			/**
			 * F�r jeden Eintrag im Suchergebnis wird nun ein Profile-Objekt
			 * erstellt.
			 */
			while (rs.next()) {
				User u = new User();
				u.setUserID(rs.getInt("id"));
				u.setUserName(rs.getString("userName"));

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

	public User insert(User u) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Zunaechst schauen wir nach, welches der momentan hoechste
			 * Primaerschl�sselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM users ");

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
						"INSERT INTO users (id, userName) "
								+ "VALUES (" + u.getUserID() + ",'" + u.getUserName() + "')");

				System.out.println(
						"INSERT INTO users (id, userName) "
								+ "VALUES (" + u.getUserID() + ",'" + u.getUserName() + "')");

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

	public void delete(User u) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */

			stmt.executeUpdate("DELETE FROM users " + "WHERE id=" + u.getUserID());
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

	public User edit(User u) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			
			/**
			 * Statement ausf�llen und als Query an die DB schicken
			 */

			stmt.executeUpdate("UPDATE users " + "SET userName=\"" + u.getUserName() + "\", "
			+ " WHERE id=" + u.getUserID());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Um Analogie zu insert(User u) zu wahren, geben wir u zur�ck
		 */
		return u;
	}
}
