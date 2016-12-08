package com.hdm.Application.client.gui;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;

public class ImpressumView extends Update{

	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	AppUser currentUser = new AppUser();

	protected String getHeadlineText(){
		return "";
	}
	/**
	 * Erstellung aller Panels
	 */
	
	
	
	/**
	 * Erstellung aller Widgets
	 */
	
	protected void run() {
		this.append("");
		
		VerticalPanel impressumPanel = new VerticalPanel();
		impressumPanel.setStyleName("impressumPanel");
		RootPanel.get("Details").add(impressumPanel);
    }
}
