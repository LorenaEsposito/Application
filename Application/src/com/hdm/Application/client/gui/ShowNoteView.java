package com.hdm.Application.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.Note;

public class ShowNoteView extends Update{

	protected String getHeadlineText() {
	    return "Edit Note";
}
	
	//private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	private Note note = new Note();
	

	/**
	   * Erstellung aller Panels
	   */

	VerticalPanel createPanel = new VerticalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();


	/**
	 * Erstellung aller Widgets
	 */
  
	Label titleLabel = new Label("Title");
	Label subtitleLabel = new Label("Subtitle");
	Label noticeLabel = new Label("Notice");
	TextBox noteTitle = new TextBox();
	TextBox noteSubtitle = new TextBox();
	TextArea textArea = new TextArea();
	//final Button createButton = new Button("Create");
	final Button editButton = new Button("Edit");
	final Button deleteButton = new Button("Delete");
	
protected void run() {

    // Ankündigung, was nun geschehen wird.

    this.append("");
   
	/**
   * Zuteilung der Widgets zum jeweiligen Panel
   */
  
  createPanel.add(titleLabel);
  createPanel.add(noteTitle);
  createPanel.add(subtitleLabel);
  createPanel.add(noteSubtitle);
  createPanel.add(noticeLabel);
  createPanel.add(textArea); 
  createPanel.add(buttonPanel);
  buttonPanel.add(editButton);
  buttonPanel.add(deleteButton);
  //buttonPanel.add(createButton);
  RootPanel.get("Details").add(createPanel);
      
  
  /**
   * Zuweisung eines Styles fuer die jeweiligen Widgets
   **/
  	deleteButton.setStyleName("notework-menubutton");
    editButton.setStyleName("notework-menubutton");
  //createButton.setStyleName("notework-menubutton");
	textArea.setVisibleLines(20);
	textArea.setPixelSize(420, 350);
	
	noteTitle.setText(note.getnTitle());
	
	textArea.setText(note.getnContent());
	
  /**
   * Erstellung der Clickhandler
   **/
  
//  createButton.addClickHandler(new ClickHandler() {
//	public void onClick(ClickEvent event) {
//		
//		/*
//		 * Speichern der eingegebenen Werte blockieren, um
//		 * Mehrfach-Klicks und daraus entstehende, unnoetige Eintraege in
//		 * der Datenbank zu verhindern.
//		 */
//		createButton.setEnabled(false);
//		createButton.setStylePrimaryName("");
//
//        /*
//         * Showcase instantiieren.
//         */
//        Update update = new ShowNoteView();
//        
//        RootPanel.get("Details").clear();
//        RootPanel.get("Details").add(update);
//  }
//  });
  
  editButton.addClickHandler(new ClickHandler() {
	public void onClick(ClickEvent event) {
        /*
         * Showcase instantiieren.
         */
        Update update = new EditNoteView();
        
        RootPanel.get("Details").clear();
        RootPanel.get("Details").add(update);
  }
  });
  
  deleteButton.addClickHandler(new ClickHandler() {
	public void onClick(ClickEvent event) {
        /*
         * Showcase instantiieren.
         */
        Update update = new WelcomeView();
        
        RootPanel.get("Details").clear();
        RootPanel.get("Details").add(update);
  }
  });
  
  
  
	}


}


