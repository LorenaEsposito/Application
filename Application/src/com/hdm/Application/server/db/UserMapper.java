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
<<<<<<< HEAD
	 * Aufruf eines USER-Mappers für Klassen, die keinen Zugriff auf den
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
			 * Statement ausfüllen und als Query an die DB schicken.
			 */

			ResultSet rs = stmt.executeQuery("SELECT id, userName FROM users" + "ORDER BY id");

			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein Profile-Objekt
			 * erstellt.
			 */
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setUserName(rs.getString("userName"));

				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(u);
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
	 * Insert-Methode - Ein User-Objekt wird übergeben und die zugehoerigen
	 * Werte in ein SQL-Statement geschrieben, welches ausgeführt wird, um das
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
			 * Primaerschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM users ");

			/**
			 * Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			 */
			if (rs.next()) {
				/**
				 * u erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschlüssel.
				 */
				u.setId(rs.getInt("maxid") + 1);

				/**
				 * Erzeugen eines neuen SQL-Statements.
				 */

				stmt = con.createStatement();

				/**
				 * Jetzt erst erfolgt die tatsaechliche Einfügeoperation
				 */
				stmt.executeUpdate(
						"INSERT INTO users (id, userName) "
								+ "VALUES (" + u.getId() + ",'" + u.getUserName() + "')");

				System.out.println(
						"INSERT INTO users (id, userName) "
								+ "VALUES (" + u.getId() + ",'" + u.getUserName() + "')");

				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * Delete-Methode - Ein User-Objekt wird übergeben, anhand dessen der
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
			 * Statement ausfüllen und als Query an die DB schicken
			 */

			stmt.executeUpdate("DELETE FROM users " + "WHERE id=" + u.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Edit-Methode - Ein Profil wird übergeben und die zugehoerigen Werte in ein
	 * SQL-Statement geschrieben, welches ausgeführt wird, um die
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

			SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
			String date = mySQLformat.format(u.getDateOfBirth());

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */

			stmt.executeUpdate("UPDATE users " + "SET userName=\"" + u.getUserName() + "\", "
			+ " WHERE id=" + u.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Um Analogie zu insert(User u) zu wahren, geben wir u zurück
		 */
		return u;
	}
}

	/**
	 * Read-Methode - Übergabe eines Suchprofils, anhand dessen User ausgelesen werden sollen,
	 * die Zugriff auf die eigenen Notizen und Notizbücher haben.
=======
<<<<<<< HEAD
	 * Aufruf eines USER-Mappers fï¿½r Klassen, die keinen Zugriff auf den
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
			 * Statement ausfï¿½llen und als Query an die DB schicken
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, userName FROM users " + "WHERE id='" + id
					+ "' ORDER BY id");

			/**
			 * Da id Primaerschlï¿½ssel ist, kann max. nur ein Tupel zurï¿½ckgegeben
			 * werden. Prï¿½fe, ob ein Ergebnis vorliegt.
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
	 * der Datenbank gesucht. Diese Methode ist vor Allem fï¿½r den Login
	 * relevant, der ï¿½ber die Google-Email-Adresse realisiert wird. Die
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
			 * Statement ausfï¿½llen und als Query an die DB schicken
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, userName FROM users " + "WHERE userName='" + name
					+ "' ORDER BY userName");

			/**
			 * Da id Primaerschlï¿½ssel ist, kann max. nur ein Tupel zurï¿½ckgegeben
			 * werden. Prï¿½fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				/**
				 * Ergebnis-Tupel in Objekt umwandeln
				 */
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
			 * Statement ausfï¿½llen und als Query an die DB schicken.
			 */

			ResultSet rs = stmt.executeQuery("SELECT id, userName FROM users" + "ORDER BY id");

			/**
			 * Fï¿½r jeden Eintrag im Suchergebnis wird nun ein Profile-Objekt
			 * erstellt.
			 */
			while (rs.next()) {
				User u = new User();
				u.setUserID(rs.getInt("id"));
				u.setUserName(rs.getString("userName"));

				/**
				 * Hinzufï¿½gen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Ergebnisvektor zurï¿½ckgeben
		 */
		return result;
	}

	/**
	 * Insert-Methode - Ein User-Objekt wird ï¿½bergeben und die zugehoerigen
	 * Werte in ein SQL-Statement geschrieben, welches ausgefï¿½hrt wird, um das
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
			 * Primaerschlï¿½sselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM users ");

			/**
			 * Wenn wir etwas zurï¿½ckerhalten, kann dies nur einzeilig sein
			 */
			if (rs.next()) {
				/**
				 * u erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschlï¿½ssel.
				 */
				u.setUserID(rs.getInt("maxid") + 1);

				/**
				 * Erzeugen eines neuen SQL-Statements.
				 */

				stmt = con.createStatement();

				/**
				 * Jetzt erst erfolgt die tatsaechliche Einfï¿½geoperation
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
	 * Delete-Methode - Ein User-Objekt wird ï¿½bergeben, anhand dessen der
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
			 * Statement ausfï¿½llen und als Query an die DB schicken
			 */

			stmt.executeUpdate("DELETE FROM users " + "WHERE id=" + u.getUserID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Edit-Methode - Ein Profil wird ï¿½bergeben und die zugehoerigen Werte in ein
	 * SQL-Statement geschrieben, welches ausgefï¿½hrt wird, um die
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

			SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
			String date = mySQLformat.format(u.getDateOfBirth());

			/**
			 * Statement ausfï¿½llen und als Query an die DB schicken
			 */

			stmt.executeUpdate("UPDATE users " + "SET userName=\"" + u.getUserName() + "\", "
			+ " WHERE id=" + u.getUserID());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Um Analogie zu insert(User u) zu wahren, geben wir u zurï¿½ck
		 */
		return u;
	}
}

	/**
	 * Read-Methode - ï¿½bergabe eines Suchprofils, anhand dessen User ausgelesen werden sollen,
	 * die Zugriff auf die eigenen Notizen und Notizbï¿½cher haben.
=======
	 * Aufruf eines USER-Mappers für Klassen, die keinen Zugriff auf den
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
			 * Statement ausfüllen und als Query an die DB schicken.
			 */

			ResultSet rs = stmt.executeQuery("SELECT id, userName FROM users" + "ORDER BY id");

			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein Profile-Objekt
			 * erstellt.
			 */
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setUserName(rs.getString("userName"));

				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(u);
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
	 * Insert-Methode - Ein User-Objekt wird übergeben und die zugehoerigen
	 * Werte in ein SQL-Statement geschrieben, welches ausgeführt wird, um das
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
			 * Primaerschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM users ");

			/**
			 * Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			 */
			if (rs.next()) {
				/**
				 * u erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschlüssel.
				 */
				u.setId(rs.getInt("maxid") + 1);

				/**
				 * Erzeugen eines neuen SQL-Statements.
				 */

				stmt = con.createStatement();

				/**
				 * Jetzt erst erfolgt die tatsaechliche Einfügeoperation
				 */
				stmt.executeUpdate(
						"INSERT INTO users (id, userName) "
								+ "VALUES (" + u.getId() + ",'" + u.getUserName() + "')");

				System.out.println(
						"INSERT INTO users (id, userName) "
								+ "VALUES (" + u.getId() + ",'" + u.getUserName() + "')");

				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * Delete-Methode - Ein User-Objekt wird übergeben, anhand dessen der
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
			 * Statement ausfüllen und als Query an die DB schicken
			 */

			stmt.executeUpdate("DELETE FROM users " + "WHERE id=" + u.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Edit-Methode - Ein Profil wird übergeben und die zugehoerigen Werte in ein
	 * SQL-Statement geschrieben, welches ausgeführt wird, um die
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

			SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
			String date = mySQLformat.format(u.getDateOfBirth());

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */

			stmt.executeUpdate("UPDATE users " + "SET userName=\"" + u.getUserName() + "\", "
			+ " WHERE id=" + u.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Um Analogie zu insert(User u) zu wahren, geben wir u zurück
		 */
		return u;
	}
}

	/**
	 * Read-Methode - Übergabe eines Suchprofils, anhand dessen User ausgelesen werden sollen,
	 * die Zugriff auf die eigenen Notizen und Notizbücher haben.
>>>>>>> refs/heads/master
>>>>>>> refs/heads/Lola
	 * 
	 * @author Marius Klepser
	 * @param searchProfile
	 *            Suchprofil mit Werten, nach denen die auszugebenden User
	 *            selektiert werden sollen
	 * @return Ausgabe aller User, die den Kriterien des Suchprofils
	 *         entsprechen
	 */
