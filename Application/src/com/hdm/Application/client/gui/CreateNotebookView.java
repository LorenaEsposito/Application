package com.hdm.Application.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;

public class CreateNotebookView extends Update{

	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	protected String getHeadlineText() {
		return "Create Notebook";
	}
	
	protected void run() {
		
		// Ankündigung, was nun geschehen wird.

	    this.append("Hier kann ein neues Notizbuch angelegt werden");
	    
	    VerticalPanel createPanel = new VerticalPanel();
	    HorizontalPanel buttonBox = new HorizontalPanel();
	    
	    
	    RootPanel.get("Details").add(createPanel);
	    adminService = ClientsideSettings.getAdministration();
	    
	    Label headlineLabel = new Label("Headline");
	    createPanel.add(headlineLabel);
	    
	    TextBox notebookHeadline = new TextBox();
	    createPanel.add(notebookHeadline);
	    
	    Label noticeLabel = new Label("Notice");
	    createPanel.add(noticeLabel);

		
	}

}
