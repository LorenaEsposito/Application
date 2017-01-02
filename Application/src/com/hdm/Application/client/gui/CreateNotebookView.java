package com.hdm.Application.client.gui;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.Permission;

public class CreateNotebookView extends Update{

	
	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	private Note n = new Note();
	
	private String currentNBTitle = new String();
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	private AppUser user = new AppUser();

	Date date = new Date();

	//private String currentDateTime =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
	
	protected String getHeadlineText() {
	    return "";
}
	
	/**
	   * Erstellung aller Panels
	   */

  HorizontalPanel headlinePanel = new HorizontalPanel();
  HorizontalPanel mainPanel = new HorizontalPanel();
  HorizontalPanel buttonPanel = new HorizontalPanel();
  VerticalPanel permissionPanel = new VerticalPanel();
  VerticalPanel leftPanel = new VerticalPanel();
  VerticalPanel rightPanel = new VerticalPanel();
 
  
  
  /**
   * Erstellung aller Widgets
   */
   
   TextBox notebookTitle = new TextBox();
   TextBox permissionText = new TextBox();
   final Button createButton = new Button("Create");
   final Button cancelButton = new Button("Cancel");
   final Button savePermissionButton = new Button("Save");
   final RadioButton readButton = new RadioButton("Leseberechtigung");
   final RadioButton editButton = new RadioButton("Bearbeitungsberechtigung");
   CellTable<AppUser> table = new CellTable<AppUser>(); 
   Label rightsLabel = new Label("Berechtigung vergeben:");
   Label mainheadline = new Label("Neue Notiz");


protected void run() {
    this.append("");
    
    mainPanel.setStyleName("detailsPanel");
    
    //currentNBTitle = Application.listbox.getSelectedItemText();
    
    TextColumn<AppUser> nameColumn = new TextColumn<AppUser>(){
    	@Override
    	public String getValue(AppUser user){
    		return user.getGoogleID();
    	}
    };
    
    table.addColumn(nameColumn, "");
    
 // Create a data provider.
    ListDataProvider<AppUser> dataProvider = new ListDataProvider<AppUser>();
    
 // Connect the table to the data provider.
    dataProvider.addDataDisplay(table);

	/**
     * Zuteilung der Widgets zum jeweiligen Panel
     */
    
    
    headlinePanel.add(mainheadline);
    buttonPanel.add(createButton);
    buttonPanel.add(cancelButton);
    rightPanel.add(rightsLabel);
    rightPanel.add(permissionPanel);
    permissionPanel.add(permissionText);
    permissionPanel.add(readButton);
    permissionPanel.add(editButton);
    permissionPanel.add(savePermissionButton);
    rightPanel.add(table);
    leftPanel.add(notebookTitle);
    
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);
    
    RootPanel.get("Details").add(headlinePanel);
    RootPanel.get("Details").add(mainPanel);
    RootPanel.get("Details").add(buttonPanel);
    
    
    notebookTitle.setText("Überschrift");
    readButton.setText("Leseberechtigung");
    editButton.setText("Bearbeitungsberechtigung");
    permissionText.setText("Name des Berechtigten");
    
    /**
     * Zuweisung eines Styles fuer die jeweiligen Widgets
     **/
    headlinePanel.setStyleName("headlinePanel");
    leftPanel.setStyleName("CreateLeftPanel");
    rightPanel.setStyleName("CreateRightPanel");
    notebookTitle.setStyleName("noteTitle");
    createButton.setStyleName("savePermission-button");
    cancelButton.setStyleName("savePermission-button");
    readButton.setStyleName("savePermission-button");
    editButton.setStyleName("savePermission-button");
    permissionText.setStyleName("style-Textbox");
    savePermissionButton.setStyleName("savePermission-button");
    buttonPanel.setStyleName("buttonPanel");
    permissionPanel.setStyleName("permissionPanel");
    rightsLabel.setStyleName("headline");
  	
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
    		savePermissionButton.setStylePrimaryName("savePermission-button");
    		
    		
    		AppUser user = new AppUser();
    		String googleID = new String();
			Permission permission = new Permission();
    		
    		int atIndex = permissionText.getText().indexOf("@");
    		googleID = permissionText.getText().substring(0, atIndex);
    		
    		adminService.searchUserByGoogleID(googleID, searchUserByGoogleIDCallback());
    		
    		if(user != null){
    			permission.setUserID(user.getUserID());
    			
    			if(readButton.getValue() == true){
    	//			permission.setPermissionType(false);
    			}
    			if(editButton.getValue() == true){
    		//		permission.setPermissionType(true);
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
  		
  		/*
		 * Speichern der eingegebenen Werte blockieren, um
		 * Mehrfach-Klicks und daraus entstehende, unnoetige Eintraege in
		 * der Datenbank zu verhindern.
		 */
		createButton.setEnabled(false);
		Notebook notebook = new Notebook();
		notebook.setNbTitle(notebookTitle.getText());
		notebook.setNbCreDate(date);
		//Date date = new Date();
		//note.setnCreDate(date);
		adminService.createNotebook(notebook, createNotebookCallback());

          /*
           * Showcase instantiieren.
           */
          Update update = new ShowNoteView();
          
          RootPanel.get("Details").clear();
          RootPanel.get("Details").add(update);
    }
    });
    
    cancelButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event){
    		cancelButton.setEnabled(false);
    		
    		Update update = new WelcomeView();
    		
    		RootPanel.get("Details").clear();
    		RootPanel.get("Details").add(update);
    	}
    });

}
    

    private AsyncCallback<Void> createNotebookCallback() {
    	AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>(){
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(Void result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success CreateNoteCallback: " + result.getClass().getSimpleName());
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

