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
	    this.append("");
		
		
		/**
		   * Erstellung aller Panels
		   */
		
		VerticalPanel createPanel = new VerticalPanel();
	    //HorizontalPanel buttonPanel = new HorizontalPanel();
		
	    /**
		   * Erstellung aller Widgets
		   */
	    
	    Label headlineLabel = new Label("Headline");
	    TextBox notebookHeadline = new TextBox();
	    Label noticeLabel = new Label("Notice");
		
		/**
	     * Zuteilung der Widgets zum jeweiligen Panel
	     */
		
	    createPanel.add(headlineLabel);
		createPanel.add(notebookHeadline);
		createPanel.add(noticeLabel);
		RootPanel.get("Details").add(createPanel);
		
		/**
	     * Zuweisung eines Styles fuer die jeweiligen Widgets
	     **/
		
		//In Entstehung
		
		
	
	    
	    
	    
	    
	    
	    adminService = ClientsideSettings.getAdministration();
	    
	    
	    

		
	}

}
