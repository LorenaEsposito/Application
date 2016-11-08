package com.hdm.Application.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
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

    VerticalPanel createPanel = new VerticalPanel();
    RootPanel.get("Details").add(createPanel);
    //BankAdministrationAsync bankVerwaltung = ClientsideSettings.getBankVerwaltung();
    
    Label headlineLabel = new Label("Headline");
    createPanel.add(headlineLabel);
    
    TextBox noteHeadline = new TextBox();
    createPanel.add(noteHeadline);
    
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
    textArea.setVisibleLines(5);
    
    final Button saveButton = new Button("Save");
    
    saveButton.setStyleName("notework-menubutton");
    
    createPanel.add(saveButton);
    
    saveButton.addClickHandler(new ClickHandler() {
  	public void onClick(ClickEvent event) {
          /*
           * Showcase instantiieren.
           */
          Update update = new NoteOverviewView();
          
          RootPanel.get("Details").clear();
          RootPanel.get("Details").add(update);
    }
    });
    
final Button deleteButton = new Button("Delete");
    
    deleteButton.setStyleName("notework-menubutton");
    
    createPanel.add(deleteButton);
    
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

