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

public class ExternerButton extends Update {
 
 protected String getHeadlineText() {
     return "Search";
}

protected void run() {

    // Ankündigung, was nun geschehen wird.

    this.append("");

    VerticalPanel searchPanel = new VerticalPanel();
    RootPanel.get("Details").add(searchPanel);
    final Button searchButton = new Button("Simulation");
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
}
}