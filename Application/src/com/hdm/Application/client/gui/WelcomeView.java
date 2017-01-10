package com.hdm.Application.client.gui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.google.gwt.user.client.ui.RootPanel; 


public class WelcomeView extends Update {
	
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
	VerticalPanel welcomePanel = new VerticalPanel();
	
	
	/**
	 * Erstellung aller Widgets
	 */
	Label mainheadline = new Label("Willkommen");
	Label welcomeLabel = new Label();
	
	
	protected void run() {
		this.append("");

		welcomePanel.setStyleName("detailsPanel");
		headlinePanel.setStyleName("headlinePanel");
		
		headlinePanel.add(mainheadline);
		welcomePanel.add(welcomeLabel);
		RootPanel.get("Details").add(headlinePanel);
		RootPanel.get("Details").add(welcomePanel);

   	 	/**
		 * Auslesen des Profils vom aktuellen Benutzer aus der Datenbank.
		 */

		adminService.getUserByMail(ClientsideSettings.getLoginInfo().getEmailAddress(),
				getCurrentUserCallback());
			
	}
	
	private AsyncCallback<AppUser> getCurrentUserCallback() {
		 AsyncCallback<AppUser> asyncCallback = new AsyncCallback<AppUser>() {
		 
		 @Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}
		 
		 @Override
		 public void onSuccess(AppUser result){
			 ClientsideSettings.getLogger()
				.severe("Success GetCurrentUserCallback: " + result.getClass().getSimpleName());
			 currentUser = result;

			 welcomeLabel.setText("Willkommen " + currentUser.getUserName() + 
					 " zu deinem ganz persoenlichen Notizbuch-System.");
		 }
		 };
		 return asyncCallback;
	 }
	
}
