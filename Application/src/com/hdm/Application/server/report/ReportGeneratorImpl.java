package com.hdm.Application.server.report;

import java.util.ArrayList;
import java.util.Date;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.hdm.Application.server.*;
import com.hdm.Application.shared.*;
import com.hdm.Application.shared.ReportGenerator;
import com.hdm.Application.shared.bo.*;
import com.hdm.Application.shared.report.*;




/**
 * Implementierung des <code>ReportGenerator</code>-Interface. Die technische
 * Realisierung bzgl. <code>RemoteServiceServlet</code> bzw. GWT RPC erfolgt
 * analog zu {@lNoteAdministrationImplImpl}. Für Details zu GWT RPC siehe dort.
 * 
 * @see ReportGenerator
 * @author thies
 * @author Weirich
 */
@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet
    implements ReportGenerator {

  /**
   * Ein ReportGenerator benötigt Zugriff auf die NoteAdministration, da diese die
   * essentiellen Methoden für die Koexistenz von Datenobjekten (vgl.
   * bo-Package) bietet.
   */
	
	private NoteAdministration noteadministration = null;

  /**
   * <p>
   * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
   * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
   * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
   * Konstruktors ist durch die Client-seitige Instantiierung durch
   * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
   * möglich.
   * </p>
   * <p>
   * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
   * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
   * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
   * </p>
   */
  public ReportGeneratorImpl() throws IllegalArgumentException {
  }

  /**
   * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor.
   * 
   * @see #ReportGeneratorImpl()
   */
  @Override
public void init() throws IllegalArgumentException {
    /*
     * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf eine
     * NoteAdministrationImpl-Instanz.
     */
	  
	  NoteAdministrationImpl a = new NoteAdministrationImpl();
	  a.init();
	  this.noteadministration = a;
  
  }

  /**
   * Auslesen der zugehörigen NoteAdministration (interner Gebrauch).
   * 
   * @return das NoteAdministrationsobjekt
   */
  protected NoteAdministration getNoteAdministration() {
    return this.noteadministration;
  }

public AllNotesFromUser createAllNotesFromUserReport(AppUser user) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}
}