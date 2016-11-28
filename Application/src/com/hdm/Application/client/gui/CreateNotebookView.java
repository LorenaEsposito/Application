package com.hdm.Application.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.Permission;

public class CreateNotebookView extends Update{

	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	private AppUser user = new AppUser();
	
	protected String getHeadlineText() {
		return "Create Notebook";
	}
	
	 /**
	   * Erstellung aller Panels
	   */
	
	HorizontalPanel createPanel = new HorizontalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();
	
	 /**
	   * Erstellung aller Widgets
	   */
  
	Label headlineLabel = new Label("Headline");
	TextBox notebookHeadline = new TextBox();
	//Label noticeLabel = new Label("Notice");
	TextBox permissionBox = new TextBox();
	RadioButton readButton = new RadioButton("Leseberechtigung");
	RadioButton editButton = new RadioButton("Bearbeitungsberechtigung");
	Button savePermissionButton = new Button("Save");
	Button createButton = new Button("Create");
	Button cancelButton = new Button("Cancel");
	
	
	
	protected void run() {
	    this.append("");
	
		/**
	     * Zuteilung der Widgets zum jeweiligen Panel
	     */
		
	    createPanel.add(headlineLabel);
		createPanel.add(notebookHeadline);
		createPanel.add(permissionBox);
		createPanel.add(readButton);
		createPanel.add(editButton);
		createPanel.add(savePermissionButton);
		//createPanel.add(noticeLabel);
		RootPanel.get("Details").add(createPanel);
		
		/**
	     * Zuweisung eines Styles fuer die jeweiligen Widgets
	     **/
		
		readButton.setText("Leseberechtigung");
		editButton.setText("Bearbeitungsberechtigung");
		
		/**
	     * Erstellung der Clickhandler
	     **/
	    readButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		if(editButton.getValue() == true){
	    			editButton.setValue(false);
	    			readButton.setValue(true);
	    		}
	    		
	    		if(editButton.getValue() != true){
	    			readButton.setValue(true);
	    		}
	    	}
	    });
	    
	    editButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event){
	    		if(readButton.getValue() == true){
	    			readButton.setValue(false);
	    			editButton.setValue(true);
	    		}
	    		
	    		if(readButton.getValue() != true){
	    			editButton.setValue(true);
	    		}
	    	}
	    });
	    
	    savePermissionButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		
	    		savePermissionButton.setEnabled(false);
	    		readButton.setEnabled(false);
	    		editButton.setEnabled(false);
	    		savePermissionButton.setStylePrimaryName("");
	    		
	    		
	    		AppUser user = new AppUser();
	    		String googleID = new String();
				Permission permission = new Permission();
	    		
	    		int atIndex = permissionBox.getText().indexOf("@");
	    		googleID = permissionBox.getText().substring(0, atIndex);
	    		
	    		adminService.searchUserByGoogleID(googleID, searchUserByGoogleIDCallback());
	    		
	    		if(user == null){
	    			Window.alert("Der eingegebene Nutzer existiert nicht. Ueberpruefen Sie bitte Ihre Angaben.");
	    		}
	    		
	    		if(user != null){
	    			permission.setUserID(user.getUserID());
	    			
	    			if(readButton.getValue() == true){
	    				permission.setPermissionType(false);
	    			}
	    			if(editButton.getValue() == true){
	    				permission.setPermissionType(true);
	    			}
	    			if(readButton.getValue() == false && editButton.getValue() == false){
	    				Window.alert("Bitte waehlen Sie eine Art der Berechtigung aus");
	    			}
	    			
	    			adminService.createPermission(permission, createPermissionCallback());
	    			
	    			permissions.add(permission);
	    		}
	    	}
	    });

	    createButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		
	    		createButton.setEnabled(false);
	    		
	    		Notebook notebook = new Notebook();
	    		notebook.setNbTitle(notebookHeadline.getText());
	    		//notebook.setNbCreDate(Date date);
	    		adminService.createNotebook(notebook, createNotebookCallback());
	    	}
	    });
		
	}
	
	private AsyncCallback<Void> createNotebookCallback() {
		AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
			
			@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
			
			@Override
    		public void onSuccess(Void result) {
    			ClientsideSettings.getLogger().
    			severe("Success CreateNotebookCallback: " + result.getClass().getSimpleName());
    		}
		};
		return asyncCallback;
	}
	
	private AsyncCallback<Void> createPermissionCallback() {
    	AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    		
    		@Override
    		public void onSuccess(Void result) {
    			ClientsideSettings.getLogger().
    			severe("Success CreatePermissionCallback: " + result.getClass().getSimpleName());
    		}
    	};
    	return asyncCallback;
    }
    
    private AsyncCallback<AppUser> searchUserByGoogleIDCallback() {
    	AsyncCallback<AppUser> asyncCallback = new AsyncCallback<AppUser>() {
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    		
    		@Override
    		public void onSuccess(AppUser result) {
    			ClientsideSettings.getLogger().
    			severe("Success SearchUserByGoogleIDCallback: " + result.getClass().getSimpleName());
    			user = result;
    		}
    	};
    	return asyncCallback;
    }

}
