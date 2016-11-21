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

protected void run() {

    // Ankündigung, was nun geschehen wird.

    this.append("");

    VerticalPanel searchPanel = new VerticalPanel();
    RootPanel.get("Details").add(searchPanel);
    
    TextBox searchBox = new TextBox();
    searchPanel.add(searchBox);
    
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
    
  
    
    final Button searchButton = new Button("Search");
    
    searchButton.setStyleName("notework-menubutton");
    
    searchPanel.add(searchButton);
    
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
          
          
          RadioButton dueDateButton = new RadioButton("");
          searchPanel.add(dueDateButton);
          
          RadioButton userNameButton = new RadioButton("Username");
          searchPanel.add(userNameButton);
          
   
    
    
    


}
}