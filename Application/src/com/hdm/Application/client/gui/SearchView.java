package com.hdm.Application.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchView extends Update {
	
	protected String getHeadlineText() {
	    return "";
}
	
	/**
	   * Erstellung aller Panels
	   */
  
  VerticalPanel detailsPanel = new VerticalPanel();
  VerticalPanel searchPanel = new VerticalPanel();
  HorizontalPanel buttonPanel = new HorizontalPanel();
  HorizontalPanel headlinePanel = new HorizontalPanel();
	
  /**
  ** Erstellung aller Widgets
  */
  
  TextBox searchBox = new TextBox();

  final Button searchButton = new Button("Suchen");
  final RadioButton dueDateButton = new RadioButton("Duedate");
  final RadioButton userNameButton = new RadioButton("Username");
  final RadioButton noteButton = new RadioButton("Note");
  final RadioButton notebookButton = new RadioButton("Notebook");
  final Label mainheadline = new Label("Suche");

protected void run() {

    // Ank�ndigung, was nun geschehen wird.

    this.append("");
    
	/**
   * Zuteilung der Widgets zum jeweiligen Panel
   */
    
    headlinePanel.add(mainheadline);
    searchPanel.add(searchBox);
    searchPanel.add(buttonPanel);
    buttonPanel.add(dueDateButton);
    buttonPanel.add(userNameButton);
    buttonPanel.add(noteButton);
    buttonPanel.add(notebookButton);
    searchPanel.add(searchButton);
   
    detailsPanel.add(searchPanel);
    RootPanel.get("Details").add(headlinePanel);
    RootPanel.get("Details").add(detailsPanel);
    
	/**
   * Zuweisung eines Styles fuer die jeweiligen Widgets
   **/
	
    headlinePanel.setStyleName("headlinePanel");
    detailsPanel.setStyleName("detailsPanel");
    searchPanel.setStyleName("searchPanel");
    buttonPanel.setStyleName("SearchButtonPanel");
    searchButton.setStyleName("savePermission-button");
    dueDateButton.setStyleName("savePermission-button");
    userNameButton.setStyleName("savePermission-button");
    noteButton.setStyleName("savePermission-button");
    notebookButton.setStyleName("savePermission-button");
    searchBox.setStyleName("searchbox");
    
    searchBox.setText("Suche");
    dueDateButton.setText("Enddatum");
    userNameButton.setText("Benutzer");
    noteButton.setText("Notiz");
    notebookButton.setText("Notizbuch");
    
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