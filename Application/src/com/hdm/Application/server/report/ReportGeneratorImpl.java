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
	
	private NotebookMapper notebookMapper= null;
	private NoteMapper noteMapper= null;
	
	

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
	  this.notebookMapper = NotebookMapper.notebookMapper();
	  this.noteMapper = NoteMapper.noteMapper();
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

 /*
  * Zun�chst legen wir uns einen leeren Report an.
  */
	 AllNotesFromUser result = new AllNotesFromUser();
      
 //Anlegen der Kopfzeile mit dem vollen Namen
	 Row TopRow = new Row();
	 TopRow.addColumn(new Column(user.getUserName()));
	 result.addRow(TopRow);
	 Vector<Note> notes = noteMapper.findAllNotesFromAppUser(user);
	    for (Note n : notes) {
	    	
	        // Eine leere Zeile anlegen.
	        Row SingleInfoRow = new Row();
	        
	      //Notizbuchtitel auslesen
	        if (n.getNbID() != 0){
			   	SingleInfoRow.addColumn(new Column (notebookMapper.findById(n.getNbID()).getNbTitle()));
		        }
		        else {
		    	SingleInfoRow.addColumn(new Column ("Notizbuchtitel unbekannt"));
		        }
	        
	        //Notiztitel auslesen
			SingleInfoRow.addColumn(new Column(n.getnTitle()));
			
			//Subtitel auslesen
	        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
	        
	        //Berechtigung
	        switch (n.getpType()) {
			case 1: 
				SingleInfoRow.addColumn(new Column("Leseberechtigung"));
				break;
			case 2: 
				SingleInfoRow.addColumn(new Column("Bearbeitungsberechtigung"));
				break;
			case 3: 
				SingleInfoRow.addColumn(new Column("Löschberechtigung"));
				break;

			default:				
				SingleInfoRow.addColumn(new Column("Eigene Notiz"));
				break;
			}
	        
	        //Erstellungsdatum  auslesen
	        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
			
	        //Content auslesen
	        Row contentrow = new Row();
	        contentrow.addColumn(new Column( n.getnContent()));
	        
	        // und schlie�lich die Zeile dem Report hinzuf�gen.
	        result.addRow(SingleInfoRow);
	        result.addRow(contentrow);

	      }		
    return result;   
}
  
  public AllNotesFromUser createOwnNotesFromUserReport(AppUser user) throws IllegalArgumentException {

	  /*
	   * Zun�chst legen wir uns einen leeren Report an.
	   */
	 	 AllNotesFromUser result = new AllNotesFromUser();
	       
	  //Anlegen der Kopfzeile mit dem vollen Namen
	 	 Row TopRow = new Row();
	 	 TopRow.addColumn(new Column(user.getUserName()));
	 	 result.addRow(TopRow);
	 	 Vector<Note> notes = noteMapper.findOwnNotesFromAppUser(user);
	 	    for (Note n : notes) {
	 	    	
	 	        // Eine leere Zeile anlegen.
	 	        Row SingleInfoRow = new Row();
	 	        
	 	      //Notizbuchtitel auslesen
	 	        if (n.getNbID() != 0){
	 			   	SingleInfoRow.addColumn(new Column (notebookMapper.findById(n.getNbID()).getNbTitle()));
	 		        }
	 		        else {
	 				   	SingleInfoRow.addColumn(new Column ("Notizbuchtitel unbekannt"));
	 		        }
	 	        
	 	        //Notiztitel auslesen
	 			SingleInfoRow.addColumn(new Column(n.getnTitle()));
	 			
	 			//Subtitel auslesen
	 	        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
	 	        
	 	       //Berechtigung
	 	        SingleInfoRow.addColumn(new Column("Eigene Notiz"));
	 	        
	 	      //Erstellungsdatum  auslesen
	 	        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
	 			
	 	        //Content auslesen
	 	        Row contentrow = new Row();
	 	        contentrow.addColumn(new Column( n.getnContent()));
	 	        
	 	        // und schlie�lich die Zeile dem Report hinzuf�gen.
	 	        
	 	        result.addRow(SingleInfoRow);
	 	        result.addRow(contentrow);

	 	      }		
	     return result;    
	 }

  public AllNotesFromUser createAllFilteredNotesLEB(AppUser user) throws IllegalArgumentException {

	  /*
	   * Zun�chst legen wir uns einen leeren Report an.
	   */
	 	 AllNotesFromUser result = new AllNotesFromUser();
	       
	  //Anlegen der Kopfzeile mit dem vollen Namen
	 	 Row TopRow = new Row();
	 	 TopRow.addColumn(new Column(user.getUserName()));
	 	 result.addRow(TopRow);
	 	 Vector<Note> notes = noteMapper.findByUserPermission1(user);
	 	    for (Note n : notes) {
	 	    	
	 	        // Eine leere Zeile anlegen.
	 	        Row SingleInfoRow = new Row();
	 	        
	 	      //Notizbuchtitel auslesen
	 	        if (n.getNbID() != 0){
	 			   	SingleInfoRow.addColumn(new Column (notebookMapper.findById(n.getNbID()).getNbTitle()));
	 		        }
	 		        else {
	 				   	SingleInfoRow.addColumn(new Column ("Notizbuchtitel unbekannt"));
	 		        }
	 	        
	 	        //Notiztitel auslesen
	 			SingleInfoRow.addColumn(new Column(n.getnTitle()));
	 			
	 			//Subtitel auslesen
	 	        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
	 	        
	 	     //Berechtigung
	 	        SingleInfoRow.addColumn(new Column("Leseberechtigung"));
	 	        
	 	      //Erstellungsdatum  auslesen
	 	        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
	 			
	 	        //Content auslesen
	 	        Row contentrow = new Row();
	 	        contentrow.addColumn(new Column( n.getnContent()));
	 	        
	 	        // und schlie�lich die Zeile dem Report hinzuf�gen.
	 	        
	 	        result.addRow(SingleInfoRow);
	 	        result.addRow(contentrow);

	 	      }		
	     return result;    
	 }
  
  public AllNotesFromUser createAllFilteredNotesLB(AppUser user) throws IllegalArgumentException {

	  /*
	   * Zun�chst legen wir uns einen leeren Report an.
	   */
	 	 AllNotesFromUser result = new AllNotesFromUser();
	       
	  //Anlegen der Kopfzeile mit dem vollen Namen
	 	 Row TopRow = new Row();
	 	 TopRow.addColumn(new Column(user.getUserName()));
	 	 result.addRow(TopRow);
	 	 Vector<Note> notes = noteMapper.findByUserPermission3(user);
	 	    for (Note n : notes) {
	 	    	
	 	        // Eine leere Zeile anlegen.
	 	        Row SingleInfoRow = new Row();
	 	        
	 	      //Notizbuchtitel auslesen
	 	        if (n.getNbID() != 0){
	 			   	SingleInfoRow.addColumn(new Column (notebookMapper.findById(n.getNbID()).getNbTitle()));
	 		        }
	 		        else {
	 				   	SingleInfoRow.addColumn(new Column ("Notizbuchtitel unbekannt"));
	 		        }
	 	        
	 	        //Notiztitel auslesen
	 			SingleInfoRow.addColumn(new Column(n.getnTitle()));
	 			
	 			//Subtitel auslesen
	 	        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
	 	        
	 	        //Berechtigung
	 	        SingleInfoRow.addColumn(new Column("Löschberechtigung"));
	 	        
	 	      //Erstellungsdatum  auslesen
	 	        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
	 			
	 	        //Content auslesen
	 	        Row contentrow = new Row();
	 	        contentrow.addColumn(new Column( n.getnContent()));
	 	        
	 	        // und schlie�lich die Zeile dem Report hinzuf�gen.
	 	        
	 	        result.addRow(SingleInfoRow);
	 	        result.addRow(contentrow);

	 	      }		
	     return result;
  }
  
  public AllNotesFromUser createAllFilteredNotesBB(AppUser user) throws IllegalArgumentException {

	  /*
	   * Zun�chst legen wir uns einen leeren Report an.
	   */
	 	 AllNotesFromUser result = new AllNotesFromUser();
	       
	  //Anlegen der Kopfzeile mit dem vollen Namen
	 	 Row TopRow = new Row();
	 	 TopRow.addColumn(new Column(user.getUserName()));
	 	 result.addRow(TopRow);
	 	 Vector<Note> notes = noteMapper.findByUserPermission2(user);
	 	    for (Note n : notes) {
	 	    	
	 	        // Eine leere Zeile anlegen.
	 	        Row SingleInfoRow = new Row();
	 	        
	 	      //Notizbuchtitel auslesen
	 	        if (n.getNbID() != 0){
	 			   	SingleInfoRow.addColumn(new Column (notebookMapper.findById(n.getNbID()).getNbTitle()));
	 		        }
	 		        else {
	 				   	SingleInfoRow.addColumn(new Column ("Notizbuchtitel unbekannt"));
	 		        }
	 	        
	 	        //Notiztitel auslesen
	 			SingleInfoRow.addColumn(new Column(n.getnTitle()));
	 			
	 			//Subtitel auslesen
	 	        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
	 	        
	 	     //Berechtigung
	 	        SingleInfoRow.addColumn(new Column("Bearbeitungsberechtigung"));
	 	        
	 	      //Erstellungsdatum  auslesen
	 	        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
	 			
	 	        //Content auslesen
	 	        Row contentrow = new Row();
	 	        contentrow.addColumn(new Column( n.getnContent()));
	 	        
	 	        // und schlie�lich die Zeile dem Report hinzuf�gen.
	 	        
	 	        result.addRow(SingleInfoRow);
	 	        result.addRow(contentrow);

	 	      }		
	     return result;
  }
@SuppressWarnings("deprecation")
@Override
public AllFilteredNotes createAllFilteredNotesReportED(Date erstellungsDatum) throws IllegalArgumentException {
	/*
	  * Zun�chst legen wir uns einen leeren Report an.
	  */
	AllFilteredNotes result = new AllFilteredNotes();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column(erstellungsDatum.toString()));
		 result.addRow(TopRow);
		 /*
	  * Hier werden alle Notizen eines Users aufgelistet 
	  */
		int tag = erstellungsDatum.getDate();
		int monat = erstellungsDatum.getMonth()+1;
		 int jahr = erstellungsDatum.getYear()+1900;

		 String datum = jahr+"-"+monat+"-"+tag;
		Vector<Note> notes = noteMapper.findByCreationDate(java.sql.Date.valueOf(datum));	
		    for (Note n : notes) {
		    	
		    	
		        // Eine leere Zeile anlegen.
		        Row SingleInfoRow = new Row();
		        
		      //Notizbuchtitel auslesen
		        if (n.getNbID() != 0){
				   	SingleInfoRow.addColumn(new Column (notebookMapper.findById(n.getNbID()).getNbTitle()));
			        }
			        else {
					   	SingleInfoRow.addColumn(new Column ("Notizbuchtitel unbekannt"));
			        }
		        
		        //Notiztitel auslesen
				SingleInfoRow.addColumn(new Column(n.getnTitle()));
				
				//Subtitel auslesen
		        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
		        
		      //Erstellungsdatum  auslesen
		        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
				
		        //Content auslesen
		        Row contentrow = new Row();
		        contentrow.addColumn(new Column( n.getnContent()));
		        
		        // und schlie�lich die Zeile dem Report hinzuf�gen.
		        
		        result.addRow(SingleInfoRow);
		        result.addRow(contentrow);

		      }		
		    	return result;
}

@SuppressWarnings("deprecation")
@Override
public AllFilteredNotes createAllFilteredNotesReportDD(Date dueDate) throws IllegalArgumentException {
	/*
	  * Zun�chst legen wir uns einen leeren Report an.
	  */
		AllFilteredNotes result = new AllFilteredNotes();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column(dueDate.toString()));
		 result.addRow(TopRow);
		 /*
	  * Hier werden alle Notizen eines DueDates aufgelistet 
	  */
		 int tag = dueDate.getDate();
		 int monat = dueDate.getMonth()+1;
		 int jahr = dueDate.getYear()+1900;

		 String datum = jahr+"-"+monat+"-"+tag;
		Vector<Note> notes = noteMapper.findByDueDate(java.sql.Date.valueOf(datum));	
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
		        
		        // und schlie�lich die Zeile dem Report hinzuf�gen.
		        
		        result.addRow(SingleInfoRow);
		        result.addRow(contentrow);

		      }		
		    
		    
		    	return result;
}


@Override
public AllNotes createAllFilteredNotesReport(String notebook) throws IllegalArgumentException {
	/*
	  * Zun�chst legen wir uns einen leeren Report an.
	  */
		 AllNotes result = new AllNotes();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column(notebook));
		 result.addRow(TopRow);
		 //Auslesen aller Notebooks welche im Titel den �bergebenen String enthalten
		 Vector<Notebook> notebooks = notebookMapper.findByTitle(notebook);

		 //Vector f�r Notes aus dem jeweiligen Notebook
		 Vector<Note> notes = null;
		 
		 //For-Schleife welche die gefundenen Notebooks durchl�uft
		 for (Notebook nb : notebooks) {
			 //Auslesen aller Notizen des jeweiligen Notebooks und Zwischenspeichern in notes
			 notes = noteMapper.findByNotebook(nb);
			 
			 //Durchlaufen aller Notizen und das hinzuf�gen der Zeilen
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
			        
			        // und schlie�lich die Zeile dem Report hinzuf�gen.
			        
			        result.addRow(SingleInfoRow);
			        result.addRow(contentrow);
			      
			      }		
			    
		}
		   return result;

}
public AllNotes createAllNotesReport() throws IllegalArgumentException {
	/*
	  * Zun�chst legen wir uns einen leeren Report an.
	  */
		 AllNotes result = new AllNotes();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column());
		 result.addRow(TopRow);
		 
		 //Auslesen aller Notes 
		 Vector<Note> note = noteMapper.findAll();
		    
		 
		
			 //Durchlaufen aller Notizen und das hinzuf�gen der Zeilen
			 for (Note n : note) {			    	
			        // Eine leere Zeile anlegen.
			        Row SingleInfoRow = new Row();
			        
			      //nbtitle auslesen
			        
			        if (n.getNbID() != 0){
				   	SingleInfoRow.addColumn(new Column (notebookMapper.findById(n.getNbID()).getNbTitle()));
			        }
			        else {
					   	SingleInfoRow.addColumn(new Column ("Notizbuchtitel unbekannt"));
			        }
			        
			        //Notiztitel auslesen
					SingleInfoRow.addColumn(new Column(n.getnTitle()));
					
					//Subtitel auslesen
			        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
			        
			      //Permissiontype auslesen
			        
			    
			        
			        
			      //Erstellungsdatum  auslesen
			        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
			       
					
			        //Content auslesen
			        Row contentrow = new Row();
			        contentrow.addColumn(new Column( n.getnContent()));
			        
			        // und schlie�lich die Zeile dem Report hinzuf�gen.
			        
			        result.addRow(SingleInfoRow);
			        result.addRow(contentrow);
			      
			      }		
			    
		
		   return result;
}

@SuppressWarnings("deprecation")
@Override
public AllFilteredNotes findByBetweenCreationDate(Date von, Date bis) throws IllegalArgumentException{
	/*
	  * Zun�chst legen wir uns einen leeren Report an.
	  */
		AllFilteredNotes result = new AllFilteredNotes();
	      
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column(von.toString() + bis.toString()));
		 result.addRow(TopRow);
		 /*
	  * Hier werden alle Notizen eines DueDates aufgelistet 
	  */
		 int tag = von.getDate();
		 int monat = von.getMonth()+1;
		 int jahr = von.getYear()+1900; 
		 String vonBox = jahr+"-"+monat+"-"+tag;
		 
		 int tag1 = bis.getDate();
		 int monat1 = bis.getMonth()+1;
		 int jahr1 = bis.getYear()+1900;
		 String bisBox = jahr1+"-"+monat1+"-"+tag1;
		 
		 
		Vector<Note> notes = noteMapper.findByBetweenCreationDate(java.sql.Date.valueOf(vonBox), java.sql.Date.valueOf(bisBox));

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
		        
		        // und schlie�lich die Zeile dem Report hinzuf�gen.
		        
		        result.addRow(SingleInfoRow);
		        result.addRow(contentrow);

		      }		
		    
		    
		    	return result;
}

@Override
public AllNotes findByTitle(String nTitle) throws IllegalArgumentException {
	
	AllNotes result = new AllNotes();
    
	 //Anlegen der Kopfzeile mit dem vollen Namen
		 Row TopRow = new Row();
		 TopRow.addColumn(new Column());
		 result.addRow(TopRow);
		 
		 //Auslesen aller Notes 
		 Vector<Note> note = noteMapper.findByTitle(nTitle);
		    
		 
		
			 //Durchlaufen aller Notizen und das hinzuf�gen der Zeilen
			 for (Note n : note) {			    	
			        // Eine leere Zeile anlegen.
			        Row SingleInfoRow = new Row();
			        
			      //Notizbuchtitel auslesen
			        
			        if (n.getNbID() != 0){
				   	SingleInfoRow.addColumn(new Column (notebookMapper.findById(n.getNbID()).getNbTitle()));
			        }
			        else {
					   	SingleInfoRow.addColumn(new Column ("Notizbuchtitel unbekannt"));
			        }			        
			        //Notiztitel auslesen
					SingleInfoRow.addColumn(new Column(n.getnTitle()));
					
					//Subtitel auslesen
			        SingleInfoRow.addColumn(new Column(n.getnSubtitle()));
			        
			      //Erstellungsdatum  auslesen
			        SingleInfoRow.addColumn(new Column(n.getnCreDate().toString()));
			       
			        //Content auslesen
			        Row contentrow = new Row();
			        contentrow.addColumn(new Column( n.getnContent()));
			        
			        // und schlie�lich die Zeile dem Report hinzuf�gen.
			        
			        result.addRow(SingleInfoRow);
			        result.addRow(contentrow);
			      
			      }		
			    
		
		   return result;
}


}