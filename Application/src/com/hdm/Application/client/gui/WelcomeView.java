package com.hdm.Application.client.gui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;


public class WelcomeView extends Update {
	
	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	AppUser currentUser = new AppUser();

	protected String getHeadlineText(){
		return "Welcome";
	}
	/**
	 * Erstellung aller Panels
	 */
	VerticalPanel welcomePanel = new VerticalPanel();
	
	
	/**
	 * Erstellung aller Widgets
	 */
	Label welcomeLabel = new Label();
	
	protected void run() {
		this.append("");
		
		adminService = ClientsideSettings.getAdministration();
		
   	 	/**
		 * Auslesen des Profils vom aktuellen Benutzer aus der Datenbank.
		 */
		int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
		adminService.getUserByGoogleID(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex),
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
		 }
		 };
		 return asyncCallback;
	 }
	
}
