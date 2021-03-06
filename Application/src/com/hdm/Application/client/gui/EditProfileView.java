package com.hdm.Application.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hdm.Application.client.Application;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;

public class EditProfileView extends Update{

	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	private AppUser currentUser = null;
	
	private AppUser updateUser = null;
	
	/**
	   * Erstellung aller Panels
	   */

	VerticalPanel labelPanel = new VerticalPanel();
	VerticalPanel accountPanel = new VerticalPanel();
	HorizontalPanel headlinePanel = new HorizontalPanel();
	HorizontalPanel mainPanel = new HorizontalPanel();
	
	/**
	   * Erstellung aller Widgets
	   */
	
	Label username = new Label("Profilname");
	Label accountLabel = new Label("Kontoverwaltung");
	TextBox userNameTB = new TextBox();
	Label mail = new Label();
	Button saveButton = new Button("Speichern");
	Button deleteButton = new Button("Konto loeschen");
	
	
	
	protected String getHeadlineText() {
		return "";
	}

	protected void run() {
		
		adminService.getCurrentUser(getCurrentUserCallback());
		
		/**
	     * Zuteilung der Widgets zum jeweiligen Panel
	     */
		
		mainPanel.add(headlinePanel);
		
		labelPanel.add(username);
		mainPanel.add(mail);
		mainPanel.add(userNameTB);
		mainPanel.add(saveButton);
		accountPanel.add(accountLabel);
		accountPanel.add(deleteButton);
		
		RootPanel.get("Details").add(labelPanel);
		RootPanel.get("Details").add(mainPanel);
		RootPanel.get("Details").add(accountPanel);
		
		/**
	     * Zuweisung eines Styles fuer die jeweiligen Widgets
	     **/
		
		labelPanel.setStyleName("labelPanel");
		accountPanel.setStyleName("labelPanel");
		saveButton.setStyleName("savePermission-button");
		deleteButton.setStyleName("savePermission-button3");
		userNameTB.setStyleName("style-Textbox");
		
		/**
	     * Erstellung der Clickhandler
	     **/
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				updateUser = currentUser;
				if(userNameTB.getText() != null){
				updateUser.setUserName(userNameTB.getValue());
				adminService.editUser(updateUser, editUserCallback());
				}
			}
		});
		
		deleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				adminService.deleteUser(currentUser, deleteUserCallback());
	    		 Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(), "_self", "");
	    		 Cookies.removeCookie("userid");
			}
		});
		
	}
	
	/**
	 * Erstellen aller asynchronen Callbacks
	 * @return
	 */
	
	private AsyncCallback<AppUser> getCurrentUserCallback() {
    	AsyncCallback<AppUser> asyncCallback = new AsyncCallback<AppUser>(){
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(AppUser result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success GetCurrentUserCallback: " + result.getClass().getSimpleName());
    		 currentUser = result;
    		 /*
    		  * Hier werden die noetigen Daten geholt und in die View eingesetzt.
    		  */
    		 if(currentUser.getUserName() != null){
    			 userNameTB.setText(currentUser.getUserName());
    		 }else{
    			 userNameTB.setText("");
    		 }
    	 }
    	};
    	return asyncCallback;
    }
	
	private AsyncCallback<AppUser> editUserCallback() {
    	AsyncCallback<AppUser> asyncCallback = new AsyncCallback<AppUser>(){
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(AppUser result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success EditUserCallback: " + result.getClass().getSimpleName());
    		 currentUser = result;
    		 
    		 Update update = new WelcomeView();
    		 RootPanel.get("Details").clear();
    		 RootPanel.get("Details").add(update);
    	 }
    	};
    	return asyncCallback;

    }
	
	private AsyncCallback<Void> deleteUserCallback() {
    	AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>(){
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(Void result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success DeleteNoteCallback: " + result.getClass().getSimpleName());
    		 Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(), "_self", "");
    		 Cookies.removeCookie("userid");
    	 }
    	};
    	return asyncCallback;

    }

}
