package com.hdm.Application.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchView extends Update {
	
	protected String getHeadlineText() {
	    return "Search";
}
	
	/**
	   * Erstellung aller Panels
	   */
  
  VerticalPanel searchPanel = new VerticalPanel();
	
  /**
  ** Erstellung aller Widgets
  */
  
  TextBox searchBox = new TextBox();

  final Button searchButton = new Button("Search");
  final RadioButton dueDateButton = new RadioButton("Duedate");
  final RadioButton userNameButton = new RadioButton("Username");
  final RadioButton noteButton = new RadioButton("Note");
  final RadioButton notebookButton = new RadioButton("Notebook");

protected void run() {

    // Ank�ndigung, was nun geschehen wird.

    this.append("");
    
	/**
   * Zuteilung der Widgets zum jeweiligen Panel
   */
    
    searchPanel.add(searchButton);
    searchPanel.add(searchBox);
    searchPanel.add(dueDateButton);
    searchPanel.add(userNameButton);
    searchPanel.add(noteButton);
    searchPanel.add(notebookButton);
    RootPanel.get("Details").add(searchPanel);
    
	/**
   * Zuweisung eines Styles fuer die jeweiligen Widgets
   **/
	
    searchButton.setStyleName("notework-menubutton");
    
    dueDateButton.setText("Duedate");
    userNameButton.setText("Username");
    noteButton.setText("Note");
    notebookButton.setText("Notebook");
    
	/**
     * Erstellung der ClickHandler
     **/
    
    searchButton.addClickHandler(new ClickHandler() {
  	public void onClick(ClickEvent event) {
          /*
           * Showcase instantiieren.
           */
  		  Update update = new ResultView();
          RootPanel.get("Details").clear();
          RootPanel.get("Details").add(update);
          }
          });
}
}