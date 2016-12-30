package com.hdm.Application.shared.report;



import java.io.Serializable;
import java.util.Date;

import com.hdm.Application.shared.report.Paragraph;

/**
 * <p>
 * Basisklasse aller Reports. Reports sind als <code>Serializable</code>
 * deklariert, damit sie von dem Server an den Client gesendet werden kÃ¶nnen.
 * Der Zugriff auf Reports erfolgt also nach deren Bereitstellung lokal auf dem
 * Client.
 * </p>
 * <p>
 * Ein Report besitzt eine Reihe von Standardelementen. Sie werden mittels
 * Attributen modelliert und dort dokumentiert.
 * </p>
 * 
 * @see Report
 * @author Thies
 * @author Weirich
 */


public abstract class Report implements Serializable {

 
  private static final long serialVersionUID = 1L;
 
  
  
  /**
   * Informationen zu diesem Bericht, wie bspw. Name des Anforderes
   * oder Anzahl der gefundenen Notizen
   */
  private Paragraph requestdetails = null;
 

  /**
   * Jeder Bericht kann einen individuellen Titel besitzen.
   */
  private String titel = "Report";

  /**
   * Datum der Erstellung des Berichts.
   */

  private Date created = new Date();
  


  
  /**
   * Auslesen der Berichtdetails.
   * 
   * @return CompositeParagraph mit Berichtdetails
   */
  public Paragraph getRequestDetails() {
		return requestdetails;
	}

  /**
   * Setzen der Berichtdetails.
   * 
   * @param requestdetails Berichtdetails vom Typ Paragraph
   */
	public void setRequestDetails(Paragraph requestdetails) {
		this.requestdetails = requestdetails;
	}
  
 
  /**
   * Auslesen des Berichtstitels.
   * 
   * @return Titeltext
   */
  public String gettitel() {
    return this.titel;
  }
  /**
   * Setzen des Berichtstitels.
   * 
   * @param title Titeltext
   */
  public void settitel(String titel) {
    this.titel = titel;
  }
  
  /**
   * Auslesen des Erstellungsdatums.
   * 
   * @return Datum der Erstellung des Berichts
   */
  public Date getCreated() {
    return this.created;
  }

  /**
   * Setzen des Erstellungsdatums. <b>Hinweis:</b> Der Aufruf dieser Methoden
   * ist nicht unbedingt erforderlich, da jeder Report bei seiner Erstellung
   * automatisch den aktuellen Zeitpunkt festhÃ¤lt.
   * 
   * @param created Zeitpunkt der Erstellung
   */
  
  public void setCreated(Date created) {
    this.created = created;
  }

}


