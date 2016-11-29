package com.hdm.Application.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;
/**
 * Verwalten einer Verbindung zur Datenbank.
 * 
 * @author Marius Klepser
 */

public class DBConnection {

    /**
     * Instantiieren der Connection und Festlegung der Einzigartigkeit durch <code>static</code>
     */
	
    public static Connection con = null;

    /**
     * Die URL fuer die Google Cloud-SQL Datenbank - angesprochen uebern die Projekt-, sowie Instanz-ID.
     * In deploytem Status soll die Datenbank von der Application nur ueber einen root-Zugang und den entsprechenden Google-Treibern angesprochen werden.
     */
    private static String googleUrl = "jdbc:google:mysql://notework-147007:noteworkinstance/noteworkdatabase?user=root";
    
    /**
     *   Die URL fuer die Google Cloud-SQL Datenbank zum Ansteuern der Datenbank von einer
     *   lokalen Entwicklungsumgebung (nicht <code>deployed</code>). Hier muss die Datenbank ueber die
     *   bereitgestellte IPv4-Adresse sowie einen eingerichteten Zugang angesprochen werden.
     */
    
    private static String localUrl = "jdbc:mysql://104.199.71.36:3006/noteworkdatabase";

    /**
     * Statische Methode zum Aufruf der <code>DBConnection.connection()</code>.
     * Weitere Sicherstellung der Einzigartigkeit einer DBConnection-Instanz.
     * 
     * @author Marius Klepser
     * @return DAS <code>DBConnection</code>-Objekt.
     * @see con
     */
    
    public static Connection connection() {
    	
        /**
         *  Pruefen, ob es bisher eine Connection aufgebaut ist.
         */
    	
        if (con == null) {
        	
        	/**
        	 * Je nach Entwicklungsumgebung wird url benutzt, um entweder die Verbindung von der lokalen Entwicklungsumgebung
        	 * oder der deployten Applikation aufzubauen.
        	 */
        	
            String url = null;
            
            /**
             * Zugangsdaten fuer einen Nicht-root-Account zur Cloud SQL, der vollen Zugriff auf die DB hat.
             */
    
             String user = "root";
             String password = "619464532511633203";   
            
            try {
                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                    /** Load the class that provides the new
                     *	"jdbc:google:mysql://" prefix.
                     */
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;

                } else {
                    /**
                     *  Local MySQL instance to use during development.
                     */
                    Class.forName("com.mysql.jdbc.Driver");
                    url = localUrl;
                }

                // Fuer Local development wieder url, user, password, driver und url anpassen
                /**
                 * Hier gibt der DriverManager eine Verbindung, hergestellt durch die angegebene
                 * URL und den User-Account mit vollem Zugriff, zurueck
                 * 
                 * Diese Verbindung wird dann in der statischen Variable con
                 * abgespeichert und fortan verwendet.
                 */
              // Fuer Deployment hier nur (url) uebergeben und in if/else jeweils GoogleDriver und local
                con = DriverManager.getConnection(url, user, password);

            } 
            
            /**
             * Falls die Verbindung fehlschlaegt, soll die dazugehoerige Exception ausgegeben werden.
             */
           
            catch (Exception e) {
                con = null;
                e.printStackTrace();
            }
        }

        /**
         *  Zurueckgegeben der Verbindung
         */
        return con;
    }

}
