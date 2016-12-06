package com.hdm.Application.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
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

	VerticalPanel showPanel = new VerticalPanel();
	HorizontalPanel titlePanel = new HorizontalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();
	PopupPanel infoPanel = new PopupPanel();

	/**
	 * Erstellung aller Widgets
	 */
	Label testLabel = new Label();
	Label titleLabel = new Label("Title");
	Label infoLabel = new Label("Info");
	Label subtitleLabel = new Label("Subtitle");
	Label noticeLabel = new Label("Notice");
//	TextBox noteTitle = new TextBox();
//	TextBox noteSubtitle = new TextBox();
//	TextArea textArea = new TextArea();
	//final Button createButton = new Button("Create");
	final Button editButton = new Button("Edit");
	final Button deleteButton = new Button("Delete");
	
protected void run() {

    // Ankündigung, was nun geschehen wird.

    this.append("");
   
	/**
   * Zuteilung der Widgets zum jeweiligen Panel
   */
  
  titlePanel.add(titleLabel);
  titlePanel.add(infoLabel);
  showPanel.add(titlePanel);
  //createPanel.add(noteTitle);
  showPanel.add(subtitleLabel);
  //showPanel.add(noteSubtitle);
  showPanel.add(noticeLabel);
  //showPanel.add(textArea); 
  showPanel.add(buttonPanel);
  buttonPanel.add(editButton);
  buttonPanel.add(deleteButton);
  //buttonPanel.add(createButton);
  RootPanel.get("Details").add(showPanel);
      
  
  /**
   * Zuweisung eines Styles fuer die jeweiligen Widgets
   **/
  	deleteButton.setStyleName("notework-menubutton");
    editButton.setStyleName("notework-menubutton");
  //createButton.setStyleName("notework-menubutton");
	//textArea.setVisibleLines(20);
	//textArea.setPixelSize(420, 350);
	
	titleLabel.setText(note.getnTitle());
	subtitleLabel.setText(note.getnSubtitle());
	
	noticeLabel.setText(note.getnContent());
	
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
  
  infoLabel.addMouseOverHandler(new MouseOverHandler() {
	  public void onMouseOver(MouseOverEvent event) {
		  testLabel.setText("Das ist ein Test");
		  infoPanel.add(testLabel);
	  }
  });
  
  
  
	}


}



