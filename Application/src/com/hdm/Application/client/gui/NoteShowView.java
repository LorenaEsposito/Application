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

public class NoteShowView extends Update{

	protected String getHeadlineText() {
	    return "Edit Note";
}

	/**
	   * Erstellung aller Panels
	   */

	VerticalPanel createPanel = new VerticalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();


	/**
	 * Erstellung aller Widgets
	 */
  
	Label headlineLabel = new Label("Headline");
	Label noticeLabel = new Label("Notice");
	TextBox noteHeadline = new TextBox();
	TextArea textArea = new TextArea();
	final Button createButton = new Button("Create");
	final Button editButton = new Button("Edit");
	final Button deleteButton = new Button("Delete");
	
protected void run() {

    // Ankündigung, was nun geschehen wird.

    this.append("");
   
	/**
   * Zuteilung der Widgets zum jeweiligen Panel
   */
  
  createPanel.add(textArea); 
  createPanel.add(noticeLabel);
  createPanel.add(noteHeadline);
  createPanel.add(headlineLabel);
  createPanel.add(buttonPanel);
  buttonPanel.add(createButton);
  RootPanel.get("Details").add(createPanel);
      
 
  
  /**
   * Zuweisung eines Styles fuer die jeweiligen Widgets
   **/
  
  createButton.setStyleName("notework-menubutton");
	textArea.setVisibleLines(20);
	textArea.setPixelSize(420, 350);
	
  /**
   * Erstellung der Clickhandler
   **/
  
  createButton.addClickHandler(new ClickHandler() {
	public void onClick(ClickEvent event) {
		
		/*
		 * Speichern der eingegebenen Werte blockieren, um
		 * Mehrfach-Klicks und daraus entstehende, unnoetige Eintraege in
		 * der Datenbank zu verhindern.
		 */
		createButton.setEnabled(false);
		createButton.setStylePrimaryName("");

        /*
         * Showcase instantiieren.
         */
        Update update = new NoteOverviewView();
        
        RootPanel.get("Details").clear();
        RootPanel.get("Details").add(update);
  }
  });
  
  editButton.setStyleName("notework-menubutton");
  
  buttonPanel.add(editButton);
  
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


  
  deleteButton.setStyleName("notework-menubutton");
  
  buttonPanel.add(deleteButton);
  
  deleteButton.addClickHandler(new ClickHandler() {
	public void onClick(ClickEvent event) {
        /*
         * Showcase instantiieren.
         */
        Update update = new NoteOverviewView();
        
        RootPanel.get("Details").clear();
        RootPanel.get("Details").add(update);
  }
  });
  
  
  
	}


}



