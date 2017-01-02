package com.hdm.Application.server.report;

import java.util.ArrayList;
import java.util.Date;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.hdm.Application.server.*;
import com.hdm.Application.server.db.NoteMapper;
import com.hdm.Application.server.db.NotebookMapper;
import com.hdm.Application.shared.*;
import com.hdm.Application.shared.ReportGenerator;
import com.hdm.Application.shared.bo.*;
import com.hdm.Application.shared.report.*;







/**
 * Implementierung des <code>ReportGenerator</code>-Interface. Die technische
 * Realisierung bzgl. <code>RemoteServiceServlet</code> bzw. GWT RPC erfolgt
 * analog zu {@lNoteAdministrationImplImpl}. F√ºr Details zu GWT RPC siehe dort.
 * 
 * @see ReportGenerator
 * @author thies
 * @author Weirich
 */
@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet
    implements ReportGenerator {

  /**
   * Ein ReportGenerator ben√∂tigt Zugriff auf die NoteAdministration, da diese die
   * essentiellen Methoden f√ºr die Koexistenz von Datenobjekten (vgl.
   * bo-Package) bietet.
   */
	
	private NoteAdministration noteadministration = null;
	
	private NotebookMapper notebookMapper= null;
	
	

  /**
   * <p>
   * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
   * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
   * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
   * Konstruktors ist durch die Client-seitige Instantiierung durch
   * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
   * m√∂glich.
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
     * Ein ReportGeneratorImpl-Objekt instantiiert f√ºr seinen Eigenbedarf eine
     * NoteAdministrationImpl-Instanz.
     */
	  this.notebookMapper = NotebookMapper.notebookMapper();
	  NoteAdministrationImpl a = new NoteAdministrationImpl();
	  a.init();
	  this.noteadministration = a;
  
  }

  /**
   * Auslesen der zugeh√∂rigen NoteAdministration (interner Gebrauch).
   * 
   * @return das NoteAdministrationsobjekt
   */
  protected NoteAdministration getNoteAdministration() {
    return this.noteadministration;
  }

  public AllNotesFromUser createAllNotesFromUserReport(AppUser user) throws IllegalArgumentException {

 /*
  * Zun‰chst legen wir uns einen leeren Report an.
  */
	 AllNotesFromUser result = new AllNotesFromUser();
      
 //Anlegen der Kopfzeile mit dem vollen Namen
	 Row TopRow = new Row();
	 TopRow.addColumn(new Column(user.getUserName()));
	 result.addRow(TopRow);
	 /*
  * Hier werden alle Notizen eines Users aufgelistet 
  */
	 Note note1 = new Note();
	 Note note2 = new Note();
	 
	 note1.setNbID(1);
	 note1.setnTitle("Jacke kaufen");
	 note1.setnContent("Ich habe bei Primark eine Jacke gesehen.");
	 note1.setnSubtitle("Primark Jacke");
	 Date date = new Date();
	 note1.setnCreDate(date);
	 
	 note2.setNbID(1);
	 note2.setnTitle("Jacke kaufen");
	 note2.setnContent("Ich habe bei Primark eine Jacke gesehen.");
	 note2.setnSubtitle("Primark Jacke");
	 Date date1 = new Date();
	 note2.setnCreDate(date1);
	 
	 ArrayList<Note> notes = new ArrayList<Note>();	
	 notes.add(note1);
	 notes.add(note2);
	 System.out.println("notes size : "+notes.size());
	    for (Note n : notes) {
	    	
	        // Eine leere Zeile anlegen.
	        Row SingleInfoRow = new Row();
	        
	      //Notizbuchtitel auslesen
			SingleInfoRow.addColumn(new Column(notebookMapper.findById(n.getNbID()).getTitle()));
	        
	        //Notiztitel auslesen
			SingleInfoRow.addColumn(new Column(n.getnTitle()));
			
			//Subtitel auslesen
	        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
	        
	      //Erstellungsdatum  auslesen
	        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
			
	        //Content auslesen
	        Row contentrow = new Row();
	        contentrow.addColumn(new Column( n.getnContent()));
	        
	        // und schlieﬂlich die Zeile dem Report hinzuf¸gen.
	        
	        result.addRow(SingleInfoRow);
	        result.addRow(contentrow);

	      }		
    return result;
	    
	    
	    
}

@Override
public AllFilteredNotes createAllFilteredNotesReportED(Date erstellungsDatum) throws IllegalArgumentException {
	/*
	  * Zun‰chst legen wir uns einen leeren Report an.
	  */
		 AllNotesFromUser result = new AllNotesFromUser();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column(erstellungsDatum.toString()));
		 result.addRow(TopRow);
		 /*
	  * Hier werden alle Notizen eines Users aufgelistet 
	  */
		 Note note1 = new Note();
		 Note note2 = new Note();
		 
		 note1.setNbID(1);
		 note1.setnTitle("Jacke kaufen");
		 note1.setnContent("Ich habe bei Primark eine Jacke gesehen.");
		 note1.setnSubtitle("Primark Jacke");
		 Date date = new Date();
		 note1.setnCreDate(date);
		 
		 note2.setNbID(1);
		 note2.setnTitle("Jacke kaufen");
		 note2.setnContent("Ich habe bei Primark eine Jacke gesehen.");
		 note2.setnSubtitle("Primark Jacke");
		 Date date1 = new Date();
		 note2.setnCreDate(date1);
		 
		 ArrayList<Note> notes = new ArrayList<Note>();	
		 notes.add(note1);
		 notes.add(note2);
		 System.out.println("notes size : "+notes.size());
		    for (Note n : notes) {
		    	
		        // Eine leere Zeile anlegen.
		        Row SingleInfoRow = new Row();
		        
		      //Notizbuchtitel auslesen
				//SingleInfoRow.addColumn(new Column(notebookMapper.findById(n.getNbID()).getTitle()));
		        
		        //Notiztitel auslesen
				SingleInfoRow.addColumn(new Column(n.getnTitle()));
				
				//Subtitel auslesen
		        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
		        
		      //Erstellungsdatum  auslesen
		        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
				
		        //Content auslesen
		        Row contentrow = new Row();
		        contentrow.addColumn(new Column( n.getnContent()));
		        
		        // und schlieﬂlich die Zeile dem Report hinzuf¸gen.
		        
		        result.addRow(SingleInfoRow);
		        result.addRow(contentrow);

		      }		
		    
		    
		    	return null;
}

@Override
public AllFilteredNotes createAllFilteredNotesReportDD(Date dueDate) throws IllegalArgumentException {
	/*
	  * Zun‰chst legen wir uns einen leeren Report an.
	  */
		 AllFilteredNotes result = new AllFilteredNotes();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column(dueDate.toString()));
		 result.addRow(TopRow);
		 /*
	  * Hier werden alle Notizen eines Users aufgelistet 
	  */
		 Note note1 = new Note();
		 Note note2 = new Note();
		 
		 note1.setNbID(1);
		 note1.setnTitle("Jacke kaufen");
		 note1.setnContent("Ich habe bei Primark eine Jacke gesehen.");
		 note1.setnSubtitle("Primark Jacke");
		 Date date = new Date();
		 note1.setnCreDate(date);
		 
		 note2.setNbID(1);
		 note2.setnTitle("Jacke kaufen");
		 note2.setnContent("Ich habe bei Primark eine Jacke gesehen.");
		 note2.setnSubtitle("Primark Jacke");
		 Date date1 = new Date();
		 note2.setnCreDate(date1);
		 
		 ArrayList<Note> notes = new ArrayList<Note>();	
		 notes.add(note1);
		 notes.add(note2);
		 System.out.println("notes size : "+notes.size());
		    for (Note n : notes) {
		    	
		        // Eine leere Zeile anlegen.
		        Row SingleInfoRow = new Row();
		        
		      //Notizbuchtitel auslesen
				//SingleInfoRow.addColumn(new Column(notebookMapper.findById(n.getNbID()).getTitle()));
		        
		        //Notiztitel auslesen
				SingleInfoRow.addColumn(new Column(n.getnTitle()));
				
				//Subtitel auslesen
		        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
		        
		      //Erstellungsdatum  auslesen
		        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
				
		        //Content auslesen
		        Row contentrow = new Row();
		        contentrow.addColumn(new Column( n.getnContent()));
		        
		        // und schlieﬂlich die Zeile dem Report hinzuf¸gen.
		        
		        result.addRow(SingleInfoRow);
		        result.addRow(contentrow);

		      }		
		    
		    
		    	return null;
}

@Override
public AllFilteredNotes createAllFilteredNotesReport(String notebook) throws IllegalArgumentException {
	/*
	  * Zun‰chst legen wir uns einen leeren Report an.
	  */
		 AllFilteredNotes result = new AllFilteredNotes();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column(notebook));
		 result.addRow(TopRow);
		 /*
	  * Hier werden alle Notizen eines Users aufgelistet 
	  */
		 Note note1 = new Note();
		 Note note2 = new Note();
		 
		 note1.setNbID(1);
		 note1.setnTitle("Jacke kaufen");
		 note1.setnContent("Ich habe bei Primark eine Jacke gesehen.");
		 note1.setnSubtitle("Primark Jacke");
		 Date date = new Date();
		 note1.setnCreDate(date);
		 
		 note2.setNbID(1);
		 note2.setnTitle("Jacke kaufen");
		 note2.setnContent("Ich habe bei Primark eine Jacke gesehen.");
		 note2.setnSubtitle("Primark Jacke");
		 Date date1 = new Date();
		 note2.setnCreDate(date1);
		 
		 ArrayList<Note> notes = new ArrayList<Note>();	
		 notes.add(note1);
		 notes.add(note2);
		 System.out.println("notes size : "+notes.size());
		    for (Note n : notes) {
		    	
		        // Eine leere Zeile anlegen.
		        Row SingleInfoRow = new Row();
		        
		      //Notizbuchtitel auslesen
				//SingleInfoRow.addColumn(new Column(notebookMapper.findById(n.getNbID()).getTitle()));
		        
		        //Notiztitel auslesen
				SingleInfoRow.addColumn(new Column(n.getnTitle()));
				
				//Subtitel auslesen
		        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
		        
		      //Erstellungsdatum  auslesen
		        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
				
		        //Content auslesen
		        Row contentrow = new Row();
		        contentrow.addColumn(new Column( n.getnContent()));
		        
		        // und schlieﬂlich die Zeile dem Report hinzuf¸gen.
		        
		        result.addRow(SingleInfoRow);
		        result.addRow(contentrow);

		      }		
		    
		    
		    	return null;
}
}