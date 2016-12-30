package com.hdm.Application.shared.report;

import java.util.Vector;


/**
 * Ein <code>ReportWriter</code>, der Reports mittels HTML formatiert. Das im
 * Zielformat vorliegende Ergebnis wird in der Variable <code>reportText</code>
 * abgelegt und kann nach Aufruf der entsprechenden Prozessierungsmethode mit
 * <code>getReportText()</code> ausgelesen werden.
 * 
 * @author Thies
 * @author Bueschel
 */
public class HTMLReportWriter extends ReportWriter {

  /**
   * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
   * <code>process...</code>-Methoden) belegt. Format: HTML-Text
   */
  private String reportText = "";
 

  /**
   * Zurücksetzen der Variable <code>reportText</code>.
   */
  public void resetReportText() {
    this.reportText = "";
  }

  /**
   * Umwandeln eines <code>Paragraph</code>-Objekts in HTML.
   * 
   * @param p der Paragraph
   * @return HTML-Text
   */
  public String paragraph2HTML(Paragraph p) {
    if (p instanceof CompositeParagraph) {
      return this.paragraph2HTML((CompositeParagraph) p);
    }
    else {
      return this.paragraph2HTML((SimpleParagraph) p);
    }
  }

  /**
   * Umwandeln eines <code>CompositeParagraph</code>-Objekts in HTML.
   * 
   * @param p der CompositeParagraph
   * @return HTML-Text
   */
  public String paragraph2HTML(CompositeParagraph p) {
    StringBuffer result = new StringBuffer();

    for (int i = 0; i < p.getNumParagraphs(); i++) {
      result.append("<p>" + p.getParagraphAt(i) + "</p>");
    }

    return result.toString();
  }
  /**
   * HTML-Header-Text produzieren.
   * 
   * @return HTML-Text
   */
  public String getHeader() {
    StringBuffer result = new StringBuffer();

    result.append("<html><head><title>ClickandLove Report Generator</title></head><body>");
    return result.toString();
  }

  /**
   * HTML-Trailer-Text produzieren.
   * 
   * @return HTML-Text
   */
  public String getTrailer() {
    return "</body></html>";
  }

  /**
   * Prozessieren des übergebenen Reports und Ablage im Zielformat. Ein Auslesen
   * des Ergebnisses kann später mittels <code>getReportText()</code> erfolgen.
   * 
   * @param r der zu prozessierende Report
   */
  @Override
public void process(AllNotesFromUser r) {
    // Zunächst löschen wir das Ergebnis vorhergehender Prozessierungen.
    this.resetReportText();

    /*
     * In diesen Buffer schreiben wir während der Prozessierung sukzessive
     * unsere Ergebnisse.
     */
    StringBuffer result = new StringBuffer();

    /*
     * Nun werden Schritt für Schritt die einzelnen Bestandteile des Reports
     * ausgelesen und in HTML-Form übersetzt.
     */
    
    
    
    /*
     * Begonnen wird mit dem Titel des Einzelreports, der stets der volle Name des auszugebenden Profils ist.
     * Dieser bildet den Kopf der Tabelle und ihm wird die Klasse InfoReportHeader zugeordnet, damit er
     * später mit CSS gestaltet werden kann.
     */
    result.append("<table class=\"InfoReportTable\"><tr>");
    
    Vector<Row> rows = r.getRows();
    Row toprow = rows.elementAt(0);
    
  	result.append("<th class=\"InfoReportHeader\" colspan=\"3\">" + toprow.getColumnAt(0) + "</th></tr>");
	  
		
		
    	
  
    
    /*
     * Nun wird die Zeile mit den Infos des Profils aufgebaut.
     * Diese teilt sich in drei Spalten:
     *	- Angaben zu Pflichteigenschaften
     *	- angegebene Infos weiterer Eigenschaften
     *	- das Ähnlichkeitsmaß zum Report-Anforderer
     */

  	//Aufbau der Spalte/Tabelle zu den Pflichteigenschaften
    result.append("<tr><td> <table class=PflichtInfoTable>");
    for (int i = 1; i < 8; i++) {
        Row row = rows.elementAt(i);
        result.append("<tr>");
        for (int k = 0; k < row.getNumColumns(); k++) {
        	if(k==0){
        		result.append("<td class=EigColumn>" + row.getColumnAt(k) + "</td>");
        	}else{
        		result.append("<td class=InfoColumn>" + row.getColumnAt(k) + "</td>");
        	}
          }
        result.append("</tr>");
        }
    result.append("</table></td>");
    
  	//Aufbau der Spalte/Tabelle zu Infos freiwilliger Eigenschaften
    result.append("<td> <table class=InfoTable>");
    for (int i = 8; i < rows.size(); i++) {
        Row row = rows.elementAt(i);
        result.append("<tr>");
        for (int k = 0; k < row.getNumColumns(); k++) {
        	if(k==0){
        		result.append("<td class=EigColumn>" + row.getColumnAt(k) + "</td>");
        	}else{
        		result.append("<td class=InfoColumn>" + row.getColumnAt(k) + "</td>");
        	}
          }
        result.append("</tr>");
        }
    result.append("</table></td>");
      
  	/*
  	 * Aufbau der Spalte zum Ähnlichkeitsmaß
  	 * Damit dieses in einem optisch ansprechenden variabel gefüllten Kreis angezeigt werden kann,
  	 * bedienen wir uns eines DIV Containers, der mit einer gesonderten CSS Datei gestaltet wird.
  	 * Diese CSS Datei enthält 100 Sub-Klassen, in welcher das Aussehen des Kreises pro
  	 * möglicher Prozent-Angabe einzeln definiert ist.
  	 */
        result.append("<td class=\"InfoReportHeaderScore\"><div class=\"c100 p"+toprow.getColumnAt(1)+" dark\"> <span>"+ toprow.getColumnAt(1) +"%</span>  <div class=\"slice\">    <div class=\"bar\"></div>    <div class=\"fill\"></div>  </div></div></th></tr>");
    result.append("</table>");
    
    

    /*
     * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
     * reportText-Variable zugewiesen. Dadurch wird es möglich, anschließend das
     * Ergebnis mittels getReportText() auszulesen.
     */
    this.reportText = result.toString();
  }

  /**
   * Prozessieren des übergebenen Reports und Ablage im Zielformat. Ein Auslesen
   * des Ergebnisses kann später mittels <code>getReportText()</code> erfolgen.
   * 
   * @param r der zu prozessierende Report
   */

public void process(AllFilteredNotes r) {
    // Zunächst löschen wir das Ergebnis vorhergehender Prozessierungen.
    this.resetReportText();

    /*
     * In diesen Buffer schreiben wir während der Prozessierung sukzessive
     * unsere Ergebnisse.
     */
    StringBuffer result = new StringBuffer();

    /*
     * Nun werden Schritt für Schritt die einzelnen Bestandteile des Reports
     * ausgelesen und in HTML-Form übersetzt.
     */
    
    result.append("<H1>" + r.gettitel() + "</H1>");
    result.append("<div id=\"MetaData\">");
    result.append("<div id=\"RequestDetails\">");
    result.append("<H3>Report Details</H3>");
    result.append(paragraph2HTML(r.getRequestDetails()));
    result.append("<p>Report erstellt am " + r.getCreated().toString() + "</p>");
    result.append("</div>");
    
    result.append("<div id=\"SPDetails\">");
    result.append("<H3>Ihre gewählten Suchkriterien:</H3>");
    //result.append(paragraph2HTML(r.getSuchprofilDetails()));
    result.append("</div>");
    
   
    result.append("</div>");
    result.append("<div id=\"Rahmen\">");
    result.append("<H2>Gefundene Profile</H2>\n");
    result.append("</div>");
    /*
     * Da AllRelevantProfilesReport ein CompositeReport ist, enthält r
     * eine Menge von Teil-Reports des Typs createAllInfosOfProfileReport. Für
     * jeden dieser Teil-Reports rufen wir processcreateAllInfosOfProfileReport
     * auf. Das Ergebnis des jew. Aufrufs fügen wir dem Buffer hinzu.
     */
    for (int i = 0; i < r.getNumSubReports(); i++) {
      /*
       * createAllInfosOfProfileReport wird als Typ der SubReports vorausgesetzt.
       * Sollte dies in einer erweiterten Form des Projekts nicht mehr gelten,
       * so müsste hier eine detailliertere Implementierung erfolgen.
       */
      AllNotesFromUser subReport = (AllNotesFromUser) r.getSubReportAt(i);

      this.process(subReport);
      
      result.append(this.reportText + "\n");

      /*
       * Nach jeder Übersetzung eines Teilreports und anschließendem Auslesen
       * sollte die Ergebnisvariable zurückgesetzt werden.
       */
      this.resetReportText();
    }

    
    
    /*
     * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der
     * reportText-Variable zugewiesen. Dadurch wird es m�glich, anschlie�end das
     * Ergebnis mittels getReportText() auszulesen.
     */
    this.reportText = result.toString();
    
  }

  /**
   * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
   * 
   * @return ein String im HTML-Format
   */
  
  

	  /**
	   * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
	   * 
	   * @return ein String im HTML-Format
	   */
	  public String getReportText() {
	    return this.getHeader() + this.reportText + this.getTrailer();
	  }

}