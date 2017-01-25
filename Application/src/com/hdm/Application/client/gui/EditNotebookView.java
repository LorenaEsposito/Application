package com.hdm.Application.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.hdm.Application.client.Application;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.Permission;
import com.hdm.Application.shared.bo.UserPermission;

public class EditNotebookView extends Update {

	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	private Notebook currentNotebook = new Notebook();
	
	private Notebook newNotebook = new Notebook();
	
	private int y = new Integer(0);
	
	private AppUser user = new AppUser();
	
	private AppUser permissionUser = new AppUser();
	
	private AppUser currentUser = new AppUser();
	
	private Permission permission = new Permission();
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	private ArrayList<Permission> newPermissions = new ArrayList<Permission>();
	
	private ArrayList<Permission> editPermissions = new ArrayList<Permission>();
	
	private ArrayList<UserPermission> userPermission = new ArrayList<UserPermission>();
	
	boolean isExisting = new Boolean(false);
	
	int index = new Integer(0);
	
	Date newDate = new Date();
	
	UserPermissionCell cell = new UserPermissionCell();
	
	public final static SingleSelectionModel<UserPermission> selectionModel = new SingleSelectionModel<UserPermission>();
	
	 // Create a data provider.
	ListDataProvider<UserPermission> dataProvider = new ListDataProvider<UserPermission>();
   
	List<UserPermission> list = dataProvider.getList();
	
//	NotebookNotesTreeViewModel nntvm = null;
	
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
	
	CellList<UserPermission> cellList = new CellList<UserPermission>(cell);
	Label rightsLabel = new Label("Berechtigung vergeben:");
	Label mainheadline = new Label("Notizbuch bearbeiten");
	Label creDateLabel = new Label("Erstelldatum: ");
	Label creDate = new Label();
	Label modDateLabel = new Label("Zuletzt bearbeitet am: ");
	Label modDate = new Label();
	TextBox notebookTitleTB = new TextBox();
	TextBox permissionTB = new TextBox();
	Button saveNBButton = new Button("Speichern");
	Button deleteNBButton = new Button("Loeschen");
	Button cancelButton = new Button("Abbrechen");
	final Button savePermissionButton = new Button("Speichern");
	final Button deletePermissionButton = new Button("Loeschen");
	final RadioButton readButton = new RadioButton("Leseberechtigung");
	final RadioButton editButton = new RadioButton("Bearbeitungsberechtigung");
	final RadioButton deleteButton = new RadioButton("Loeschberechtigung");
	
	protected void run() {
//	    this.append("");
		
		adminService.getCurrentUser(getCurrentUserCallback());
		  
	    mainPanel.setStyleName("detailsPanel");
	    
	    // Connect the table to the data provider.
	    dataProvider.addDataDisplay(cellList);
	    
	    cellList.setSelectionModel(selectionModel);
	    
	    /**
	     * Zuteilung der Widgets zum jeweiligen Panel
	     */
	    
	    headlinePanel.add(mainheadline);
	    leftPanel.add(notebookTitleTB);
	    buttonPanel.add(saveNBButton);
	    buttonPanel.add(deleteNBButton);
	    buttonPanel.add(cancelButton);
	    buttonPanel.add(creDateLabel);
	    buttonPanel.add(creDate);
	    buttonPanel.add(modDateLabel);
	    buttonPanel.add(modDate);
	    permissionPanel.add(permissionTB);
	    permissionPanel.add(readButton);
	    permissionPanel.add(editButton);
	    permissionPanel.add(deleteButton);
	    permissionPanel.add(savePermissionButton);
	    permissionPanel.add(deletePermissionButton);
	    rightPanel.add(rightsLabel);
	    rightPanel.add(permissionPanel);
	    rightPanel.add(cellList);
	    mainPanel.add(leftPanel);
	    mainPanel.add(rightPanel);
	    
	    RootPanel.get("Details").add(headlinePanel);
	    RootPanel.get("Details").add(mainPanel);
	    RootPanel.get("Details").add(buttonPanel);
	    
	    headlinePanel.setStyleName("headlinePanel");
	    leftPanel.setStyleName("CreateLeftPanel");
	    rightPanel.setStyleName("CreateRightPanel");
	    notebookTitleTB.setStyleName("noteTitle");
	    deleteNBButton.setStyleName("savePermission-button");
	    saveNBButton.setStyleName("savePermission-button");
	    cancelButton.setStyleName("savePermission-button");
	    readButton.setStyleName("savePermission-button");
	    editButton.setStyleName("savePermission-button");
	    deleteButton.setStyleName("savePermission-button");
	    permissionTB.setStyleName("style-Textbox");
	    savePermissionButton.setStyleName("savePermission-button");
	    buttonPanel.setStyleName("buttonPanel");
	    permissionPanel.setStyleName("permissionPanel");
	    rightsLabel.setStyleName("headline");
	    deletePermissionButton.setStyleName("savePermission-button2");
	    
	    readButton.setText("Leseberechtigung");
	    editButton.setText("Bearbeitungsberechtigung");
	    deleteButton.setText("Loeschberechtigung");
	    
	    notebookTitleTB.setEnabled(false);
	    permissionTB.setEnabled(false);
	    saveNBButton.setEnabled(false);
	    deleteNBButton.setEnabled(false);
	    savePermissionButton.setEnabled(false);
	    deletePermissionButton.setEnabled(false);
	    readButton.setEnabled(false);
	    editButton.setEnabled(false);
	    deleteButton.setEnabled(false); 
	    
	    /**
	     * Erstellung der Clickhandler
	     **/
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
	    	public void onClick(ClickEvent event){
	    		if(readButton.getValue() == true || editButton.getValue() == true){
	    			readButton.setValue(false);
	    			editButton.setValue(false);
	    			deleteButton.setValue(true);
	    		}
	    		
	    		if(readButton.getValue() != true && deleteButton.getValue() != true){
	    			deleteButton.setValue(true);
	    		}
	    	}
	    });
	    
	    savePermissionButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		
	    		savePermissionButton.setEnabled(false);
	    		deletePermissionButton.setEnabled(false);
	    		readButton.setEnabled(false);
	    		editButton.setEnabled(false);
	    		deleteButton.setEnabled(false); 
	    		savePermissionButton.setStylePrimaryName("savePermission-button");
	    		
	    		
	    		if(permissionTB.getValue() != ""){
		    		if(permissionTB.getText() == currentUser.getMail()){
		    			Window.alert("Als Eigentuemer der Notiz brauchen Sie keine Berechtigung fuer sich selbst anlegen.");
		        		savePermissionButton.setEnabled(true);
		        		deletePermissionButton.setEnabled(true); 
		        		readButton.setEnabled(true);
		        		editButton.setEnabled(true);
		        		deleteButton.setEnabled(true);
		        		readButton.setValue(false);
		        		editButton.setValue(false);
		        		deleteButton.setValue(false);
		        		savePermissionButton.setStylePrimaryName("savePermission-button");
		    		}else{
	        		
	        		adminService.searchUserByMail(permissionTB.getValue(), searchUserByMailNewCallback());
	        		}
	    		}

	        		
	        	if(selectionModel.getSelectedObject() == null && permissionTB.getValue() == ""){
	        			Window.alert("Erstellen Sie eine neue Berechtigung oder bearbeiten Sie eine bestehende.");
	        		}
	        		else{
	        			UserPermission editUP = new UserPermission();
	        			editUP = selectionModel.getSelectedObject();
	        			if(readButton.getValue() == true){
	        				editUP.setPermissionType(1);
	        				dataProvider.getList().set(dataProvider.getList().indexOf(selectionModel.getSelectedObject()), editUP);
	        			}
	        			if(editButton.getValue() == true){
	        				editUP.setPermissionType(2);
	        				dataProvider.getList().set(dataProvider.getList().indexOf(selectionModel.getSelectedObject()), editUP);
	        			}
	        			if(deleteButton.getValue() == true){
	        				editUP.setPermissionType(3);
	        				dataProvider.getList().set(dataProvider.getList().indexOf(selectionModel.getSelectedObject()), editUP);
	        			}
	        			if(readButton.getValue() == false && editButton.getValue() == false && deleteButton.getValue() == false){
	        				Window.alert("Bitte waehlen Sie eine Art der Berechtigung aus");
	        			} 
	        			savePermissionButton.setEnabled(true);
	        			deletePermissionButton.setEnabled(true); 
		        		readButton.setEnabled(true);
		        		editButton.setEnabled(true);
		        		deleteButton.setEnabled(true);
		        		readButton.setValue(false);
		        		editButton.setValue(false);
		        		deleteButton.setValue(false);
	        		}
	        		

	    	}
	    });
	    
	    deletePermissionButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event){
	    		deletePermissionButton.setEnabled(false);
	    		savePermissionButton.setEnabled(false);
	    		if(selectionModel.getSelectedObject() == null){
	    			Window.alert("Bitte wahelen Sie eine Person aus.");
	    		}else{
    				dataProvider.getList().remove(selectionModel.getSelectedObject());
    				adminService.getPermission(selectionModel.getSelectedObject().getUserID(), currentNotebook.getNbID(), 0, getPermissionForDeleteCallback());
	    		}
	    	}
	    });
	    
	    saveNBButton.addClickHandler(new ClickHandler() {
	      	public void onClick(ClickEvent event) {
	      		
	      		/*
	    		 * Speichern der eingegebenen Werte blockieren, um
	    		 * Mehrfach-Klicks und daraus entstehende, unnoetige Eintraege in
	    		 * der Datenbank zu verhindern.
	    		 */
	    		saveNBButton.setEnabled(false);
	    		//createButton.setStylePrimaryName("");
	    		
	    		newNotebook.setNbID(currentNotebook.getNbID());
	    		newNotebook.setNbTitle(notebookTitleTB.getValue());
	    		newNotebook.setNbCreDate(currentNotebook.getNbCreDate());
	    		newNotebook.setNbModDate(newDate);
	    		
	    		
	   			 adminService.getOwnedNotebooks(currentUser, getOwnedNotebooksCallback());
	    		

	        }
	        });
	    
	    deleteNBButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		deleteNBButton.setEnabled(false);
	    		
	    		adminService.deleteNotebook(currentNotebook, deleteNotebookCallback());
	    		Application.nbDataProvider.getList().remove(Application.nbSelectionModel.getSelectedObject());
	    		Application.notesDataProvider.getList().clear();
	    		Update update = new WelcomeView();
	    		
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
			 
			 currentNotebook = Application.nbSelectionModel.getSelectedObject();
			 
			    creDate.setText(currentNotebook.getNbCreDate().toString());
			    modDate.setText(currentNotebook.getNbModDate().toString());
			    notebookTitleTB.setText(currentNotebook.getNbTitle());
			    
			    adminService.getPermissions(0, currentNotebook.getNbID(), getPermissionsCallback());
			 
		 }
		};
		return asyncCallback;
	}
	
	private AsyncCallback<ArrayList<Permission>> getPermissionsCallback() {
    	AsyncCallback<ArrayList<Permission>> asyncCallback = new AsyncCallback<ArrayList<Permission>>() {
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    		
    		@Override
    		public void onSuccess(ArrayList<Permission> result) {
    			ClientsideSettings.getLogger().
    			severe("Success GetUserForPermissionDeleteCallback: " + result.getClass().getSimpleName());
    			 for(int i = 0; i < result.size(); i++) {
    				 if(currentUser.getUserID() == result.get(i).getUserID()){
    			if(result.get(i).getPermissionType() == 2){
    			    notebookTitleTB.setEnabled(true);
    			    permissionTB.setEnabled(true);
    			    saveNBButton.setEnabled(true);
    			    savePermissionButton.setEnabled(true);
    			    deletePermissionButton.setEnabled(true); 
    			    readButton.setEnabled(true);
    			    editButton.setEnabled(true);
    			    deleteButton.setEnabled(true);
    			}
    			if(result.get(i).getPermissionType() == 3){
    			    notebookTitleTB.setEnabled(true);
    			    permissionTB.setEnabled(true);
    			    saveNBButton.setEnabled(true);
    			    deleteNBButton.setEnabled(true);
    			    savePermissionButton.setEnabled(true);
    			    deletePermissionButton.setEnabled(true);
    			    readButton.setEnabled(true);
    			    editButton.setEnabled(true);
    			    deleteButton.setEnabled(true);
    			}
    			}else{
    				UserPermission up = new UserPermission();
     				up.setMail(null);
     				up.setUserID(result.get(i).getUserID());
     				up.setPermissionID(result.get(i).getPermissionID());
     				up.setPermissionType(result.get(i).getPermissionType());
     				userPermission.add(up);
    			}
    				 }
    					y = 0;
    					adminService.getUserByID(userPermission.get(y).getUserID(), getUserByIDCallback());
    					permissions = result;
    			}
    	};
    	return asyncCallback;
    }
	
	private AsyncCallback<AppUser> getUserByIDCallback(){
		AsyncCallback<AppUser> asyncCallback = new AsyncCallback<AppUser>() {
			
			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}
		 
		 @Override
		 public void onSuccess(AppUser result) {
			 ClientsideSettings.getLogger().
			 severe("Success GetUserByIDCallback: " + result.getClass().getSimpleName());
			 userPermission.get(y).setMail(result.getMail());
				y++;
				if(y < userPermission.size()){
					adminService.getUserByID(userPermission.get(y).getUserID(), getUserByIDCallback());
				}else{
					for(int x = 0; x < userPermission.size(); x++){
						dataProvider.getList().add(userPermission.get(x));
					}
				}
		 }
		};
		return asyncCallback;
	}
	
	private AsyncCallback<ArrayList<Notebook>> getOwnedNotebooksCallback(){
		AsyncCallback<ArrayList<Notebook>> asyncCallback = new AsyncCallback<ArrayList<Notebook>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}
		 
		 @Override
		 public void onSuccess(ArrayList<Notebook> result) {
			 ClientsideSettings.getLogger().
			 severe("Success GetOwnedNotebooksCallback: " + result.getClass().getSimpleName());
			 for(int y = 0; y < result.size(); y++) {
     			 if(newNotebook.getNbTitle() == result.get(y).getNbTitle() && newNotebook.getNbID() != result.get(y).getNbID()) {
     				 notebookTitleTB.setText(currentNotebook.getNbTitle());
     				 isExisting = true;
     				 break;
     			 }   			 
     		 }
     		 if(isExisting == false){
    			 adminService.editNotebook(newNotebook, editNotebookCallback());

//	    		 Application.nbDataProvider.refresh();
        		 
                 /*
                  * Showcase instantiieren.
                  */
                 Update update = new EditNotebookView();
                 
                 RootPanel.get("Details").clear();
                 RootPanel.get("Details").add(update);
     		 }
     		 if(isExisting == true){
     			Window.alert("Dieses Notizbuch existiert bereits.");
     			saveNBButton.setEnabled(true);
     		 }
			 
		 }
		};
		return asyncCallback;
	}
	
	private AsyncCallback<Notebook> editNotebookCallback() {
    	AsyncCallback<Notebook> asyncCallback = new AsyncCallback<Notebook>(){
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(Notebook result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success EditNoteCallback: " + result.getClass().getSimpleName());
    		 boolean savePermission = new Boolean(true);
    		 for(int x = 0; x < dataProvider.getList().size(); x++){
    			 savePermission = true;
    			 for(int z = 0; z < permissions.size(); z++){
    				 if(permissions.get(z).getUserID() == dataProvider.getList().get(x).getUserID()){
        				 Permission editPermission = new Permission();
        				 editPermission = permissions.get(z);
        				 editPermission.setPermissionType(dataProvider.getList().get(x).getPermissionType());
        				 editPermissions.add(editPermission);
    					 savePermission = false;
    				 }
    			 }
    			 if(savePermission == true){
    				 Permission permission = new Permission();
    				 permission.setIsOwner(false);
    				 permission.setNbID(currentNotebook.getNbID());
    				 permission.setNID(0);
    				 permission.setPermissionType(dataProvider.getList().get(x).getPermissionType());
    				 permission.setUserID(dataProvider.getList().get(x).getUserID());
    				 Window.alert("Permission anlegen"); 
    				 newPermissions.add(permission);
    			 }
    		 }
    		 adminService.createPermissions(newPermissions, createPermissionsCallback());
    		 adminService.editPermissions(editPermissions, editPermissionsCallback());
    		 Application.nbDataProvider.getList().clear();
    		 adminService.getNotebooksOfUser(currentUser, new AsyncCallback<ArrayList<Notebook>>(){
    	    		
    	    		@Override
    	    		public void onFailure(Throwable caught) {
    	    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    	    		}
    	    	 
    	    	 @Override
    	    	 public void onSuccess(ArrayList<Notebook> result) {
    	    		 ClientsideSettings.getLogger().
    	    		 severe("Success EditNoteCallback: " + result.getClass().getSimpleName());
    	    		 for(int i = 0; i < result.size(); i++){
    	    		 Application.nbDataProvider.getList().add(result.get(i));
    	    		 }
    	    	 }
    		 });
    		 
    	 }
    	};
    	return asyncCallback;

    }
	
    private AsyncCallback<Void> createPermissionsCallback() {
    	AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    		
    		@Override
    		public void onSuccess(Void result) {
    			ClientsideSettings.getLogger().
    			severe("Success CreatePermissionCallback: " + result.getClass().getSimpleName());

        		 Application.nbSelectionModel.setSelected(newNotebook, true);
        		 Update update = new EditNotebookView();
        		 RootPanel.get("Details").clear();
        		 RootPanel.get("Details").add(update);
    		}
    	};
    	return asyncCallback;
    }
    
    private AsyncCallback<ArrayList<Permission>> editPermissionsCallback() {
    	AsyncCallback<ArrayList<Permission>> asyncCallback = new AsyncCallback<ArrayList<Permission>>() {
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    		
    		@Override
    		public void onSuccess(ArrayList<Permission> result) {
    			ClientsideSettings.getLogger().
    			severe("Success GetUserForPermissionDeleteCallback: " + result.getClass().getSimpleName());
    			deletePermissionButton.setEnabled(true);
    			savePermissionButton.setEnabled(true);
    			

        		 Application.nbSelectionModel.setSelected(newNotebook, true);
        		 Update update = new EditNotebookView();
        		 RootPanel.get("Details").clear();
        		 RootPanel.get("Details").add(update); 
    		}
    	};
    	return asyncCallback;
    }
	
    private AsyncCallback<AppUser> searchUserByMailNewCallback() {
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
    			if(user.getMail() == "error"){
        			Window.alert("Der eingegebene Nutzer existiert nicht. Ueberpruefen Sie bitte Ihre Angaben.");
        		}else{
        			boolean isExisting = new Boolean(false);
        			for(int i = 0; i < dataProvider.getList().size(); i++) {
        				if(user.getMail() == dataProvider.getList().get(i).getMail()) {
        					isExisting = true;
        					break;
        				}
        			}
        			
        			if(isExisting == false){
            			UserPermission userP = new UserPermission();
            			userP.setMail(user.getMail());
            			userP.setUserID(user.getUserID());
        			
        			permission.setUserID(user.getUserID());
        			
        			if(readButton.getValue() == true){
        				userP.setPermissionType(1);
        			}
        			if(editButton.getValue() == true){
        				userP.setPermissionType(2);
        			}
        			if(deleteButton.getValue() == true){
        				userP.setPermissionType(3);
        			}
        			if(readButton.getValue() == false && editButton.getValue() == false && deleteButton.getValue() == false){
        				Window.alert("Bitte waehlen Sie eine Art der Berechtigung aus");
        			}
        			permissionTB.setText("");
        			dataProvider.getList().add(userP);
	        		savePermissionButton.setEnabled(true);
	        		deletePermissionButton.setEnabled(true);
	        		readButton.setEnabled(true);
	        		editButton.setEnabled(true);
	        		deleteButton.setEnabled(true);
	        		readButton.setValue(false);
	        		editButton.setValue(false);
	        		deleteButton.setValue(false);
        			}
        			
        		}
    			
    		}
    	};
    	return asyncCallback;
    }
	
    private AsyncCallback<Permission> getPermissionForDeleteCallback() {
    	AsyncCallback<Permission> asyncCallback = new AsyncCallback<Permission>() {
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    		
    		@Override
    		public void onSuccess(Permission result) {
    			ClientsideSettings.getLogger().
    			severe("Success GetUserForPermissionDeleteCallback: " + result.getClass().getSimpleName());
    			adminService.deletePermission(result, deletePermissionCallback());
    		}
    	};
    	return asyncCallback;
    }
	
	private AsyncCallback<Void> deletePermissionCallback() {
    	AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    		
    		@Override
    		public void onSuccess(Void result) {
    			ClientsideSettings.getLogger().
    			severe("Success GetUserForPermissionDeleteCallback: " + result.getClass().getSimpleName());
    			deletePermissionButton.setEnabled(true);
    			savePermissionButton.setEnabled(true);
    		}
    	};
    	return asyncCallback;
    }
	
	private AsyncCallback<Void> deleteNotebookCallback() {
		AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
			
			@Override
			public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(Void result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success DeleteNoteCallback: " + result.getClass().getSimpleName());
    		 
    	 }
		};
		return asyncCallback;
	}
    
	
//	public void setNntvm(NotebookNotesTreeViewModel nntvm) {
//		this.nntvm = nntvm;
//	}
//
//	public void setSelected(Notebook nb) {
//		if (nb != null) {
//			currentNotebook = nb;
//			notebookTitleTB.setText(currentNotebook.getNbTitle());
//		} else {
//			notebookTitleTB.setText("");
//		}
//	}
	
}
