package com.hdm.Application.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.Note;

public class CreateNoteView extends Update{

	
	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	protected String getHeadlineText() {
	    return "Create Note";
}
	
//	private Note currentNote = null;

protected void run() {

    // Ankündigung, was nun geschehen wird.

    this.append("Hier kann eine neue Notiz angelegt werden");

    VerticalPanel createPanel = new VerticalPanel();
    HorizontalPanel buttonBox = new HorizontalPanel();
    
    
    RootPanel.get("Details").add(createPanel);
    adminService = ClientsideSettings.getAdministration();
    
    Label headlineLabel = new Label("Headline");
    createPanel.add(headlineLabel);
    
    TextBox noteHeadline = new TextBox();
    createPanel.add(noteHeadline);
    
    Label noticeLabel = new Label("Notice");
    createPanel.add(noticeLabel);
    
   // RichTextArea area = new RichTextArea();
  //  area.ensureDebugId("cwRichText-area");
  //  area.setSize("100%", "14em");
    
    //RichTextToolbar toolbar = new RichTextToolbar(area);
   // toolbar.ensureDebugId("cwRichText-toolbar");
   // toolbar.setWidth("100%");

   // Grid grid = new Grid(2, 1);
    // grid.setStyleName("cw-RichText");
   // grid.setWidget(0, 0, toolbar);
   // grid.setWidget(1, 0, area);
   // return grid;
    
    TextArea textArea = new TextArea();
    textArea.setVisibleLines(20);
    textArea.setPixelSize(420, 350);
    createPanel.add(textArea);
    
    final Button createButton = new Button("Create");
    
    createButton.setStyleName("notework-menubutton");
    
    buttonBox.add(createButton);
    
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
    
final Button editButton = new Button("Edit");
    
    editButton.setStyleName("notework-menubutton");
    
    buttonBox.add(editButton);
    
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

final Button deleteButton = new Button("Delete");
    
    deleteButton.setStyleName("notework-menubutton");
    
    buttonBox.add(deleteButton);
    
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
    
    createPanel.add(buttonBox);


}
}

