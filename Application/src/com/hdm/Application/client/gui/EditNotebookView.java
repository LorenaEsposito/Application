package com.hdm.Application.client.gui;

import java.util.ArrayList;
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
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.Permission;

public class EditNotebookView extends Update {
	
	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	private Notebook currentNotebook = new Notebook();
	
	private AppUser user = new AppUser();
	
	private AppUser permissionUser = new AppUser();
	
	private Permission permission = new Permission();
	
	private Notebook currentNB = new Notebook();
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	TextCell cell = new TextCell();
	
	public final static SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
	
	 // Create a data provider.
	ListDataProvider<String> dataProvider = new ListDataProvider<String>();
   
	List<String> list = dataProvider.getList();
	
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
	
	CellList<String> cellList = new CellList<String>(cell);
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
	final Button savePermissionButton = new Button("Save Permission");
	final Button deletePermissionButton = new Button("Berechtigung loeschen");
	final RadioButton readButton = new RadioButton("Leseberechtigung");
	final RadioButton editButton = new RadioButton("Bearbeitungsberechtigung");
	final RadioButton deleteButton = new RadioButton("Loeschberechtigung");
	
	protected void run() {
//	    this.append("");
	    
	    mainPanel.setStyleName("detailsPanel");
	    
	    /**
	     * Zuteilung der Widgets zum jeweiligen Panel
	     */
	    
	    headlinePanel.add(mainheadline);
	    leftPanel.add(creDateLabel);
	    leftPanel.add(creDate);
	    leftPanel.add(modDateLabel);
	    leftPanel.add(modDate);
	    leftPanel.add(notebookTitleTB);
	    leftPanel.add(buttonPanel);
	    buttonPanel.add(saveNBButton);
	    buttonPanel.add(deleteNBButton);
	    buttonPanel.add(cancelButton);
	    permissionPanel.add(permissionTB);
	    permissionPanel.add(readButton);
	    permissionPanel.add(editButton);
	    permissionPanel.add(deleteButton);
	    permissionPanel.add(savePermissionButton);
	    permissionPanel.add(deletePermissionButton);
	    rightPanel.add(permissionPanel);
	    rightPanel.add(cellList);
	    mainPanel.add(leftPanel);
	    mainPanel.add(rightPanel);
	    
	    RootPanel.get("Details").add(headlinePanel);
	    RootPanel.get("Details").add(mainPanel);
	    
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
	    		readButton.setEnabled(false);
	    		editButton.setEnabled(false);
	    		savePermissionButton.setStylePrimaryName("savePermission-button");
	    		
	    		
	    		String mail = new String();
	    		
	    		mail = permissionTB.getValue();
	    		
	    		adminService.searchUserByMail(mail, searchUserByMailCallback());
	    		

	    	}
	    });
	    
	    deletePermissionButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event){
	    		deletePermissionButton.setEnabled(false);
	    		savePermissionButton.setEnabled(false);
	    		
	    		adminService.getUserByMail(selectionModel.getSelectedObject(), getUserForPermissionDeleteCallback()); 
	    	}
	    });
	    
	}
	private AsyncCallback<AppUser> searchUserByMailCallback() {
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
        			
        			permission.setUserID(user.getUserID());
        			
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
        			}
        			
        			permissions.add(permission);
        			dataProvider.getList().add(user.getMail());
        			}
        			
        			if(isExisting == true){
        				Window.alert("Es wurde bereits eine Berechtigung an diesen User vergeben");
        			}
        		}
    		}
    	};
    	return asyncCallback;
    }
	
	private AsyncCallback<AppUser> getUserForPermissionDeleteCallback() {
    	AsyncCallback<AppUser> asyncCallback = new AsyncCallback<AppUser>() {
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    		
    		@Override
    		public void onSuccess(AppUser result) {
    			ClientsideSettings.getLogger().
    			severe("Success GetUserForPermissionDeleteCallback: " + result.getClass().getSimpleName());
    			permissionUser = result;
    			adminService.getPermission(permissionUser.getUserID(), currentNB.getNbID(), 0, getPermissionCallback());
    		}
    	};
    	return asyncCallback;
    }
	
	private AsyncCallback<Permission> getPermissionCallback() {
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
