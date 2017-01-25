package com.hdm.Application.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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
	
	HorizontalPanel headlinePanel = new HorizontalPanel();
	VerticalPanel impressumPanel = new VerticalPanel();
	VerticalPanel textPanel = new VerticalPanel();
	
	/**
	 * Erstellung aller Widgets
	 */
	
	
	Label mainheadline = new Label("Impressum");
    Label logoImpressum = new Label("NoteWork");
    Label adress = new Label("Hochschule der Medien Stuttgart");
    Label names1 = new Label("Marius Klepser, Lorena Esposito, Dominik Dach, Lisa Rathfelder, Andra Weirich, Sina Koritko"); 
	
	
	protected void run() {
		this.append("");
		
		
		logoImpressum.setStyleName("logoImpressum");
		impressumPanel.setStyleName("detailsPanel");
		headlinePanel.setStyleName("headlinePanel");
		
		headlinePanel.add(mainheadline);
		textPanel.add(logoImpressum);
		textPanel.add(adress);
		textPanel.add(names1);
		impressumPanel.add(textPanel);
		
		RootPanel.get("Details").add(headlinePanel);
		RootPanel.get("Details").add(impressumPanel);
		
    }
}
