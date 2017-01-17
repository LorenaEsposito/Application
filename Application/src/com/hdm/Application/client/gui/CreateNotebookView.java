package com.hdm.Application.client.gui;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
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
import com.google.gwt.view.client.MultiSelectionModel;
import com.hdm.Application.client.Application;
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
	
	private AppUser currentUser = new AppUser();

	private Notebook notebook = new Notebook();
	
	TextCell cell = new TextCell();
    
    CellList<String> cellList = new CellList<String>(cell); 
    
    final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>();
    
 // Create a data provider.
    ListDataProvider<String> dataProvider = new ListDataProvider<String>();
	
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
   final RadioButton deleteButton = new RadioButton("Loeschberechtigung");
   Label rightsLabel = new Label("Berechtigung vergeben:");
   Label mainheadline = new Label("Neue Notiz");


protected void run() {
    this.append("");
    
    mainPanel.setStyleName("detailsPanel");
    
    //currentNBTitle = Application.listbox.getSelectedItemText();
    
    cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

    // Add a selection model to handle user selection.
    
    cellList.setSelectionModel(selectionModel);
    
 // Connect the table to the data provider.
    dataProvider.addDataDisplay(cellList);


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
    permissionPanel.add(deleteButton);
    permissionPanel.add(savePermissionButton);
    rightPanel.add(cellList);
    leftPanel.add(notebookTitle);
    
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);
    
    RootPanel.get("Details").add(headlinePanel);
    RootPanel.get("Details").add(mainPanel);
    RootPanel.get("Details").add(buttonPanel);
    
    
    notebookTitle.setText("Überschrift");
    readButton.setText("Leseberechtigung");
    editButton.setText("Bearbeitungsberechtigung");
    deleteButton.setText("Loeschberechtigung");
    permissionText.setText("Name des Berechtigten");
    
    /**
     * Zuweisung eines Styles fuer die jeweiligen Widgets
     **/
    headlinePanel.setStyleName("headlinePanel");
    leftPanel.setStyleName("CreateNotebookLeftPanel");
    rightPanel.setStyleName("CreateRightPanel");
    notebookTitle.setStyleName("noteTitle");
    createButton.setStyleName("savePermission-button");
    cancelButton.setStyleName("savePermission-button");
    readButton.setStyleName("savePermission-button");
    editButton.setStyleName("savePermission-button");
    deleteButton.setStyleName("savePermission-button");
    permissionText.setStyleName("style-Textbox");
    savePermissionButton.setStyleName("savePermission-button");
    buttonPanel.setStyleName("buttonPanel");
    permissionPanel.setStyleName("permissionPanel");
    rightsLabel.setStyleName("headline");
  	
    /**
     * Erstellung der Clickhandler
     **/
    notebookTitle.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event) {
    		notebookTitle.setText("");
    	}
    });
    
    permissionText.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event) {
    		permissionText.setText("");
    	}
    });
    
    readButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event) {
    		if(editButton.getValue() == true || deleteButton.getValue() == true){
    			editButton.setValue(false);
    			deleteButton.setValue(false);
    			readButton.setValue(true);
    		}
    		
    		if(editButton.getValue() != true && deleteButton.getValue() != true){
    			readButton.setValue(true);
    		}
    	}
    });
    
    editButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event){
    		if(readButton.getValue() == true || deleteButton.getValue() == true){
    			readButton.setValue(false);
    			deleteButton.setValue(false);
    			editButton.setValue(true);
    		}
    		
    		if(readButton.getValue() != true && deleteButton.getValue() != true){
    			editButton.setValue(true);
    		}
    	}
    });
    
    deleteButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event) {
    		if(readButton.getValue() == true || editButton.getValue() == true){
    			readButton.setValue(false);
    			editButton.setValue(false);
    			deleteButton.setValue(true);
    		}
    		
    		if(readButton.getValue() != true && editButton.getValue() != true){
    			deleteButton.setValue(true);
    		}
    	}
    });
    
    savePermissionButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event) {
    		
    		savePermissionButton.setEnabled(false);
    		readButton.setEnabled(false);
    		editButton.setEnabled(false);
    		deleteButton.setEnabled(false);
    		savePermissionButton.setStylePrimaryName("savePermission-button");
    		
    		String mail = new String();
    		
    		mail = permissionText.getValue();
    		
    		adminService.searchUserByMail(mail, searchUserByGoogleIDCallback());
    		
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

		notebook.setNbTitle(notebookTitle.getText());
		notebook.setNbCreDate(date);
		notebook.setNbModDate(date);
	    adminService.getCurrentUser(getCurrentUserCallback());
	    
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
    
	private AsyncCallback<AppUser> getCurrentUserCallback(){
		AsyncCallback<AppUser> asyncCallback = new AsyncCallback<AppUser>() {
			
			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}
		 
		 @Override
		 public void onSuccess(AppUser result) {
			 ClientsideSettings.getLogger().
			 severe("Success GetCurrentUserCallback: " + result.getClass().getSimpleName());
			 
			 currentUser = result;
			 
			 adminService.getOwnedNotebooks(currentUser, getOwnedNotebooksCallback());
			 
		 }
		};
		return asyncCallback;
	}

	private AsyncCallback<ArrayList<Notebook>> getOwnedNotebooksCallback(){
		AsyncCallback<ArrayList<Notebook>> asyncCallback = new AsyncCallback<ArrayList<Notebook>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().
				severe("Error: " + caught.getMessage());
			}
			
			@Override
			public void onSuccess(ArrayList<Notebook> result){
				ClientsideSettings.getLogger().
				severe("Success GetOwnedNotebooksCallback: " + result.getClass().getSimpleName());
				
				boolean isExisting = new Boolean(false);
				for(int i = 0; i < result.size(); i++) {
					if(notebookTitle.getText() == result.get(i).getNbTitle()) {
						isExisting = true;
						break;
					}
				}
				if(isExisting == false) {
					adminService.createNotebook(notebook, createNotebookCallback());
				}
				if(isExisting == true) {
					Window.alert("Es existiert bereits ein Notizbuch mit diesem Titel");
					notebookTitle.setText("");
					createButton.setEnabled(true);
				}
				
			}
		};
		return asyncCallback;
	}
	
    private AsyncCallback<Notebook> createNotebookCallback() {
    	AsyncCallback<Notebook> asyncCallback = new AsyncCallback<Notebook>(){
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(Notebook result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success CreateNoteCallback: " + result.getClass().getSimpleName());
    		 
    		 Application.listbox.addItem(notebook.getNbTitle());
    		 
    		 Permission permission = new Permission();
				permission.setIsOwner(true);
				permission.setNID(0);
				permission.setPermissionType(3);
				permission.setUserID(currentUser.getUserID());
				permissions.add(permission);
				
				for(int i = 0; i < permissions.size(); i++){
					permission = permissions.get(i);
					permission.setNbID(result.getNbID());
				}
				adminService.createPermissions(permissions, createPermissionCallback());
		          Update update = new EditNoteView();
		          
		          RootPanel.get("Details").clear();
		          RootPanel.get("Details").add(update);
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
    			
    			Permission permission = new Permission();
    			
    			if(user == null){
        			Window.alert("Der eingegebene Nutzer existiert nicht. Ueberpruefen Sie bitte Ihre Angaben.");
        		}
        		
        		if(user != null){
        			boolean isExisting = new Boolean(false);
        			for(int i = 0; i < dataProvider.getList().size(); i++) {
        				if(user.getUserName() == dataProvider.getList().get(i)) {
        					isExisting = true;
        					break;
        				}
        			}
        			if(isExisting == false){
        			permission.setUserID(user.getUserID());
        			permission.setIsOwner(false);
        			
        			if(readButton.getValue() == true){
        				permission.setPermissionType(1);
        			}
        			if(editButton.getValue() == true){
        				permission.setPermissionType(2);
        			}
        			if(deleteButton.getValue() == true){
        				permission.setPermissionType(3);
        			}
        			if(readButton.getValue() == false && editButton.getValue() == false && deleteButton.getValue() == false){
        				Window.alert("Bitte waehlen Sie eine Art der Berechtigung aus");
        				savePermissionButton.setEnabled(true);
        			}
        			
        			permissions.add(permission);
        			dataProvider.getList().add(user.getUserName());
        			}
        			
        			if(isExisting == true){
        				Window.alert("Es wurde bereits eine Berechtigung an diesen User vergeben");
        			}
        			
        			savePermissionButton.setEnabled(true);
        			permissionText.setText("Name des Berechtigten");
        			readButton.setEnabled(true);
        			editButton.setEnabled(true);
        			deleteButton.setEnabled(true);
        			readButton.setValue(false);
        			editButton.setValue(false);
        			deleteButton.setValue(false);
        		}
    		}
    	};
    	return asyncCallback;
    }
}

