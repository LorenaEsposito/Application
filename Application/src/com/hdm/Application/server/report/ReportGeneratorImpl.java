package com.hdm.Application.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.datanucleus.sco.simple.SqlDate;

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
 * analog zu {@lNoteAdministrationImplImpl}. FÃ¼r Details zu GWT RPC siehe dort.
 * 
 * @see ReportGenerator
 * @author thies
 * @author Weirich
 */
@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet
    implements ReportGenerator {

  /**
   * Ein ReportGenerator benÃ¶tigt Zugriff auf die NoteAdministration, da diese die
   * essentiellen Methoden fÃ¼r die Koexistenz von Datenobjekten (vgl.
   * bo-Package) bietet.
   */
	
	private NoteAdministration noteadministration = null;
	
	private NotebookMapper notebookMapper= null;
	private NoteMapper noteMapper= null;
	
	

  /**
   * <p>
   * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
   * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
   * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
   * Konstruktors ist durch die Client-seitige Instantiierung durch
   * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
   * mÃ¶glich.
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
     * Ein ReportGeneratorImpl-Objekt instantiiert fÃ¼r seinen Eigenbedarf eine
     * NoteAdministrationImpl-Instanz.
     */
	  this.notebookMapper = NotebookMapper.notebookMapper();
	  this.noteMapper = NoteMapper.noteMapper();
	  NoteAdministrationImpl a = new NoteAdministrationImpl();
	  a.init();
	  this.noteadministration = a;
  
  }

  /**
   * Auslesen der zugehÃ¶rigen NoteAdministration (interner Gebrauch).
   * 
   * @return das NoteAdministrationsobjekt
   */
  protected NoteAdministration getNoteAdministration() {
    return this.noteadministration;
  }

  public AllNotesFromUser createAllNotesFromUserReport(AppUser user) throws IllegalArgumentException {

 /*
  * Zunächst legen wir uns einen leeren Report an.
  */
	 AllNotesFromUser result = new AllNotesFromUser();
      
 //Anlegen der Kopfzeile mit dem vollen Namen
	 Row TopRow = new Row();
	 TopRow.addColumn(new Column(user.getUserName()));
	 result.addRow(TopRow);
	 Vector<Note> notes = noteMapper.findByUser(user);
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
	        
	        // und schließlich die Zeile dem Report hinzufügen.
	        
	        result.addRow(SingleInfoRow);
	        result.addRow(contentrow);

	      }		
    return result;
	    
	    
	    
}

@Override
public AllFilteredNotes createAllFilteredNotesReportED(Date erstellungsDatum) throws IllegalArgumentException {
	/*
	  * Zunächst legen wir uns einen leeren Report an.
	  */
	AllFilteredNotes result = new AllFilteredNotes();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column(erstellungsDatum.toString()));
		 result.addRow(TopRow);
		 /*
	  * Hier werden alle Notizen eines Users aufgelistet 
	  */
		 
		 Vector<Note> notes = noteMapper.findByCreationDate(erstellungsDatum);	
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
		        
		        // und schließlich die Zeile dem Report hinzufügen.
		        
		        result.addRow(SingleInfoRow);
		        result.addRow(contentrow);

		      }		
		    
		    
		    	return result;
}

@SuppressWarnings("deprecation")
@Override
public AllFilteredNotes createAllFilteredNotesReportDD(Date dueDate) throws IllegalArgumentException {
	/*
	  * Zunächst legen wir uns einen leeren Report an.
	  */
		AllFilteredNotes result = new AllFilteredNotes();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column(dueDate.toString()));
		 result.addRow(TopRow);
		 /*
	  * Hier werden alle Notizen eines Users aufgelistet 
	  */
		 int tag = dueDate.getDate();
		 int monat = dueDate.getMonth()+1;
		 int jahr = dueDate.getYear()+1900;
		 System.out.println("Datumsformat gefaked: "+jahr+"-"+monat+"-"+tag);

		 String datum = jahr+"-"+monat+"-"+tag;
		Vector<Note> notes = noteMapper.findByDueDate(java.sql.Date.valueOf(datum));	
		 System.out.println("notes size : "+notes.size());
		    for (Note n : notes) {
		    	
		        // Eine leere Zeile anlegen.
		        Row SingleInfoRow = new Row();
		        
		      //Notizbuchtitel auslesen
			SingleInfoRow.addColumn(new Column(notebookMapper.findById(n.getNbID()).getNbTitle()));
		        
		        //Notiztitel auslesen
				SingleInfoRow.addColumn(new Column(n.getnTitle()));
				
				//Subtitel auslesen
		        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
		        
		      //Erstellungsdatum  auslesen
		        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
				
		        //Content auslesen
		        Row contentrow = new Row();
		        contentrow.addColumn(new Column( n.getnContent()));
		        
		        // und schließlich die Zeile dem Report hinzufügen.
		        
		        result.addRow(SingleInfoRow);
		        result.addRow(contentrow);

		      }		
		    
		    
		    	return result;
}


@SuppressWarnings("null")
@Override
public AllFilteredNotes createAllFilteredNotesReport(String notebook) throws IllegalArgumentException {
	/*
	  * Zunächst legen wir uns einen leeren Report an.
	  */
		 AllFilteredNotes result = new AllFilteredNotes();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column(notebook));
		 result.addRow(TopRow);
		 //Auslesen aller Notebooks welche im Titel den übergebenen String enthalten
		 Vector<Notebook> notebooks = notebookMapper.findByTitle(notebook);
		 System.out.println("AUSGABE!!!!! Vector notebooks ist "+notebooks.size()+" groß");
		 //Vector für Notes aus dem jeweiligen Notebook
		 Vector<Note> notes = null;
		 
		 //For-Schleife welche die gefundenen Notebooks durchläuft
		 for (Notebook nb : notebooks) {
			 //Auslesen aller Notizen des jeweiligen Notebooks und Zwischenspeichern in notes
			 notes = noteMapper.findByNotebook(nb);
			 System.out.println("AUSGABE!!!!! Vector notes ist "+notes.size()+" groß");
			 
			 //Durchlaufen aller Notizen und das hinzufügen der Zeilen
			 for (Note n : notes) {			    	
			        // Eine leere Zeile anlegen.
			        Row SingleInfoRow = new Row();
			        
			      //Notizbuchtitel auslesen
			        
				   	SingleInfoRow.addColumn(new Column(nb.getNbTitle()));
			        
			        //Notiztitel auslesen
					SingleInfoRow.addColumn(new Column(n.getnTitle()));
					
					//Subtitel auslesen
			        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
			        
			      //Erstellungsdatum  auslesen
			        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
					
			        //Content auslesen
			        Row contentrow = new Row();
			        contentrow.addColumn(new Column( n.getnContent()));
			        
			        // und schließlich die Zeile dem Report hinzufügen.
			        
			        result.addRow(SingleInfoRow);
			        result.addRow(contentrow);
			      
			      }		
			    
		}
		   return result;
}
}