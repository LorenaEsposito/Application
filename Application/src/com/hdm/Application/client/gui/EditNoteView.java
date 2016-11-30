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

public class EditNoteView extends Update{

	protected String getHeadlineText() {
	    return "Edit Note";
}

protected void run() {

    // Ankündigung, was nun geschehen wird.

    this.append("");

    /**
	   * Erstellung aller Panels
	   */
	
    VerticalPanel createPanel = new VerticalPanel();

    /**
    * Erstellung aller Widgets
    */
    
    
    Label headlineLabel = new Label("Headline");
    TextBox noteHeadline = new TextBox();
    TextArea textArea = new TextArea();
    textArea.setVisibleLines(20);
    
    final Button saveButton = new Button("Save");
    final Button deleteButton = new Button("Delete");
    
	/**
   * Zuteilung der Widgets zum jeweiligen Panel
   */
    
    createPanel.add(saveButton);
    createPanel.add(noteHeadline);
    createPanel.add(headlineLabel);
    createPanel.add(textArea);
    createPanel.add(deleteButton);
    RootPanel.get("Details").add(createPanel);

	/**
   * Zuweisung eines Styles fuer die jeweiligen Widgets
   **/
    
    saveButton.setStyleName("notework-menubutton");
    deleteButton.setStyleName("notework-menubutton");
  
    /**
     * Erstellung der ClickHandler
     **/
    
    saveButton.addClickHandler(new ClickHandler() {
  	public void onClick(ClickEvent event) {
          /*
           * View instantiieren.
           */
          Update update = new ShowNoteView();
          
          RootPanel.get("Details").clear();
          RootPanel.get("Details").add(update);
    }
    });
    
    deleteButton.addClickHandler(new ClickHandler() {
  	public void onClick(ClickEvent event) {
          /*
           * View instantiieren.
           */
          Update update = new WelcomeView();
          
          RootPanel.get("Details").clear();
          RootPanel.get("Details").add(update);
    }
    });

}
}

