package com.hdm.Application.client.gui;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
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
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.hdm.Application.client.Application;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.DueDate;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.Permission;
import com.hdm.Application.shared.bo.UserPermission;

public class CreateNoteView extends Update{

	
	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	int y = 0;
	
	private Notebook currentNB = Application.nbSelectionModel.getSelectedObject();
	
	private Note currentN = new Note();
	
	private	Note note = new Note();
	
	Permission permission = new Permission();
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	private ArrayList<Permission> notePermissions = new ArrayList<Permission>();
	
	private ArrayList<UserPermission> userPermission = new ArrayList<UserPermission>();
	
	private AppUser user = null;
	
	private AppUser currentUser = new AppUser();
	
	Date date = new Date();
	
	UserPermissionCell cell = new UserPermissionCell();
    
    CellList<UserPermission> cellList = new CellList<UserPermission>(cell); 
    
    final SingleSelectionModel<UserPermission> selectionModel = new SingleSelectionModel<UserPermission>();
    
 // Create a data provider.
    ListDataProvider<UserPermission> dataProvider = new ListDataProvider<UserPermission>();
	
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
    
   TextBox noteTitle = new TextBox();
   TextBox noteSubtitle = new TextBox();
   TextBox permissionText = new TextBox();
   TextArea textArea = new TextArea();
   final Button createButton = new Button("Erstellen");
   final Button cancelButton = new Button("Abbrechen");
   final Button savePermissionButton = new Button("Speichern");
   final Button deletePermissionButton = new Button("Loeschen");
   final RadioButton readButton = new RadioButton("Leseberechtigung");
   final RadioButton editButton = new RadioButton("Bearbeitungsberechtigung");
   final RadioButton deleteButton = new RadioButton("Loeschberechtigung");
   DatePicker datePicker = new DatePicker();
   Label rightsLabel = new Label("Berechtigung vergeben:");
   Label duedateLabel = new Label("Enddatum vergeben:");
   Label mainheadline = new Label("Neue Notiz");

protected void run() {
    this.append("");
    
    adminService.getCurrentUser(getCurrentUserCallback());
    
    mainPanel.setStyleName("detailsPanel"); 
    
    cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

    // Add a selection model to handle user selection.
    
    cellList.setSelectionModel(selectionModel);
    
 // Connect the list to the data provider.
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
    permissionPanel.add(deletePermissionButton);
    rightPanel.add(cellList);
    rightPanel.add(duedateLabel);
    leftPanel.add(noteTitle);
    leftPanel.add(noteSubtitle);
    leftPanel.add(textArea); 
    rightPanel.add(datePicker);
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);
    
    RootPanel.get("Details").add(headlinePanel);
    RootPanel.get("Details").add(mainPanel);
    RootPanel.get("Details").add(buttonPanel);
    
    textArea.setVisibleLines(20);
    textArea.setPixelSize(420, 350);

    noteTitle.setText("Titel");
    noteSubtitle.setText("Untertitel");
    if(Cookies.getCookie("url") != null){
        textArea.setText(
          "Hier finden Sie den Link den Sie speichern wollten: "
          + ""
          + ""+
          Cookies.getCookie("url")
          
          );
       }   
    
    readButton.setText("Leseberechtigung");
    editButton.setText("Bearbeitungsberechtigung");
    deleteButton.setText("Loeschberechtigung");
    permissionText.setText("Name des Berechtigten");
    
    /**
     * Zuweisung eines Styles fuer die jeweiligen Widgets
     **/
    headlinePanel.setStyleName("headlinePanel");
    leftPanel.setStyleName("CreateLeftPanel");
    rightPanel.setStyleName("CreateRightPanel");
    noteTitle.setStyleName("noteTitle");
    noteSubtitle.setStyleName("noteTitle");
    createButton.setStyleName("savePermission-button");
    cancelButton.setStyleName("savePermission-button");
    readButton.setStyleName("savePermission-button");
    editButton.setStyleName("savePermission-button");
    deleteButton.setStyleName("savePermission-button");
    permissionText.setStyleName("style-Textbox");
    datePicker.setStyleName("datepicker");
    textArea.setStyleName("TextArea");
    savePermissionButton.setStyleName("savePermission-button");
    deletePermissionButton.setStyleName("savePermission-button2");
    buttonPanel.setStyleName("buttonPanel");
    permissionPanel.setStyleName("permissionPanel");
    rightsLabel.setStyleName("headline");
    duedateLabel.setStyleName("headline");
    
    createButton.setEnabled(false);
    cancelButton.setEnabled(false);
    readButton.setEnabled(false);
    editButton.setEnabled(false);
    deleteButton.setEnabled(false);
    savePermissionButton.setEnabled(false);
    deletePermissionButton.setEnabled(false);
    
    
    
    /*
	 * Sperren der Eingabemoeglichkeit im DatePicker bei zukuenftigen Daten
	 */
	datePicker.addShowRangeHandlerAndFire(new ShowRangeHandler<java.util.Date>() {
		@Override
		public void onShowRange(ShowRangeEvent<Date> event) {
			Date start = event.getStart();
			Date temp = CalendarUtil.copyDate(start);
			Date end = event.getEnd();

			Date today = new Date();

			while (temp.before(end)) {
				if (temp.before(today) && datePicker.isDateVisible(temp)) {
					datePicker.setTransientEnabledOnDates(false, temp);
				}
				CalendarUtil.addDaysToDate(temp, 1);
			}
		}
	});
  	
    /**
     * Erstellung der Clickhandler
     **/
	noteTitle.addClickHandler(new ClickHandler(){
		public void onClick(ClickEvent event){
			noteTitle.setText("");
		}
	});
	
	noteSubtitle.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event){
			noteSubtitle.setText("");
		}
	});
	
	permissionText.addClickHandler(new ClickHandler(){
		public void onClick(ClickEvent event){
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
    		if(readButton.getValue() == true || readButton.getValue() == true){
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
    		
    		
if(permissionText.getValue() != ""){
	if(permissionText.getText() == currentUser.getMail()){
		Window.alert("Als Eigentuemer der Notiz brauchen Sie keine Berechtigung fuer sich selbst anlegen.");
		savePermissionButton.setEnabled(true);
		readButton.setEnabled(true);
		editButton.setEnabled(true);
		deleteButton.setEnabled(true);
		readButton.setValue(false);
		editButton.setValue(false);
		deleteButton.setValue(false); 
		savePermissionButton.setStylePrimaryName("savePermission-button");
	}else{
        		
		adminService.searchUserByMail(permissionText.getValue(), searchUserByMailCallback());
        }
}

        		
        	if(selectionModel.getSelectedObject() == null && permissionText.getValue() == ""){
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
            		readButton.setEnabled(true);
            		editButton.setEnabled(true);
            		deleteButton.setEnabled(true);
        		}
    		
    	}
    });
    
    deletePermissionButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event) {
    		if(selectionModel.getSelectedObject() == null){
    			Window.alert("Bitte wahelen Sie eine Person aus.");
    		}else{
    			boolean deletePossible = new Boolean(true);
    			for(int i = 0; i < permissions.size(); i++){
    				if(permissions.get(i).getPermissionID() == selectionModel.getSelectedObject().getPermissionID()){
    					deletePossible = false;
    				}
    			}
    			if(deletePossible == true){
    				
    				dataProvider.getList().remove(selectionModel.getSelectedObject());
    			}else{
    				Window.alert("Die ausgewaehlte Person kann nicht von der Berechtigungsliste geloescht werden.");
    			}
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
		cancelButton.setEnabled(false);
		savePermissionButton.setEnabled(false);
		deletePermissionButton.setEnabled(false);
		//createButton.setStylePrimaryName("");
		
		note.setnTitle(noteTitle.getText());
		note.setnSubtitle(noteSubtitle.getText());
		note.setnContent(textArea.getText());
		note.setnCreDate(date);
		note.setnModDate(date);
		note.setNbID(currentNB.getNbID());
		
		adminService.getNotesOfNotebook(currentNB, getNotesOfNotebookCallback());
    }
    });
    
    cancelButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event){
    		cancelButton.setEnabled(false);
    		createButton.setEnabled(false);
    		
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
    		 
    		    createButton.setEnabled(true);
    		    cancelButton.setEnabled(true);
    		    readButton.setEnabled(true);
    		    editButton.setEnabled(true);
    		    deleteButton.setEnabled(true);
    		    savePermissionButton.setEnabled(true);
    		    deletePermissionButton.setEnabled(true);
    		    
    		    adminService.getPermissions(0, currentNB.getNbID(), getPermissionsCallback()); 
    		    
    		 
    	 }
		};
		return asyncCallback;
	}
	

    private AsyncCallback<Note> createNoteCallback() {
    	AsyncCallback<Note> asyncCallback = new AsyncCallback<Note>(){
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(Note result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success CreateNoteCallback: " + result.getClass().getSimpleName());
    		 currentN = result;
    		 boolean existingP = new Boolean(false);
    		 for(int i = 0; i < permissions.size(); i++){
    			 if(permissions.get(i).getUserID() == currentUser.getUserID() && permissions.get(i).getIsOwner() == true){
    				 existingP = true;
    			 }
    		 }
    		 if(existingP == false){

    			permission.setIsOwner(true);
    			permission.setNbID(currentNB.getNbID());
    			permission.setNID(currentN.getnID());
    			permission.setPermissionType(3);
    			permission.setUserID(currentUser.getUserID());
    			
    			notePermissions.add(permission);
    		 }
    		 boolean savePermission = new Boolean(true);
    		 for(int x = 0; x < dataProvider.getList().size(); x++){
    			 for(int z = 0; z < permissions.size(); z++){
    				 if(permissions.get(z).getUserID() == dataProvider.getList().get(x).getUserID()){
    					 savePermission = false;
    				 }
    			 }
    			 if(savePermission == true){
    				 Window.alert("Permission wird erstellt");
    				 Permission permission = new Permission();
    				 permission.setIsOwner(false);
    				 permission.setNbID(currentNB.getNbID());
    				 permission.setNID(currentN.getnID());
    				 permission.setPermissionType(dataProvider.getList().get(x).getPermissionType());
    				 permission.setUserID(dataProvider.getList().get(x).getUserID());
    				 Window.alert("Permission hinzugefuegt"); 
    				 notePermissions.add(permission);
    			 }
    		 }
// 
//	   			 Update update = new EditNoteView();
//		          
//		          RootPanel.get("Details").clear();
//		          RootPanel.get("Details").add(update);
		        if(notePermissions.size() != 0){  
				adminService.createPermissions(notePermissions, createPermissionsCallback());
		        }
				
				if(datePicker.getValue() != null) {
					DueDate duedate = new DueDate();
					duedate.setdDate(datePicker.getValue());
					duedate.setnID(currentN.getnID());
					adminService.createDuedate(duedate, createDuedateCallback());
				}
	    		 Application.notesList.add(note.getnTitle());
	    		 Application.notesSelectionModel.setSelected(result.getnTitle(), true);
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
    			Window.alert("Permissions erzeugt");
    		}
    	};
    	return asyncCallback;
    }
    
    private AsyncCallback<Void> createDuedateCallback() {
    	AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    		
    		@Override
    		public void onSuccess(Void result) {
    			ClientsideSettings.getLogger().
    			severe("Success CreateDuedateCallback: " + result.getClass().getSimpleName());
    		}
    	};
    	return asyncCallback;
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
    			severe("Success SearchUserByMailCallback: " + result.getClass().getSimpleName());
    			user = result;
        		
    			if(user.getMail() == "error"){
    				Window.alert("Der eingegebene Nutzer existiert nicht. Ueberpruefe deine bitte Ihre Angaben.");
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
        			
        			if(readButton.getValue() == true){

            			userP.setPermissionType(1);
            			dataProvider.getList().add(userP);
        			}
        			if(editButton.getValue() == true){

            			userP.setPermissionType(2);
            			dataProvider.getList().add(userP);
        			}
        			if(deleteButton.getValue() == true){
            			userP.setPermissionType(3);
            			dataProvider.getList().add(userP);
        			}
        			if(readButton.getValue() == false && editButton.getValue() == false && deleteButton.getValue() == false){
        				Window.alert("Bitte waehle eine Berechtigung aus");
        				savePermissionButton.setEnabled(true);
        			}
        			
        			}
        			
        			if(isExisting == true){
        				Window.alert("Es wurde bereits eine Berechtigung an diesen User vergeben");
        			}

        		}
    			savePermissionButton.setEnabled(true);
    			permissionText.setText("");
    			readButton.setEnabled(true);
    			editButton.setEnabled(true);
    			deleteButton.setEnabled(true);
    			readButton.setValue(false);
    			editButton.setValue(false);
    			deleteButton.setValue(false);
    		}
    	};
    	return asyncCallback;
    }
    
    private AsyncCallback<ArrayList<Note>> getNotesOfNotebookCallback() {
   	 AsyncCallback<ArrayList<Note>> asyncCallback = new AsyncCallback<ArrayList<Note>>(){
   	 
   	 @Override
   		public void onFailure(Throwable caught) {
   			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
   		}
   	 
   	 @Override
   	 public void onSuccess(ArrayList<Note> result) {
   		 ClientsideSettings.getLogger().
   		 severe("Success GetNotesOfNotebookCallback: " + result.getClass().getSimpleName());
   		 
   		 boolean isExisting = new Boolean(false);
   		 for(int y = 0; y < result.size(); y++) {
   			 if(noteTitle.getText() == result.get(y).getnTitle()) {
   				 noteTitle.setText("");
   				 isExisting = true;
   				 break;
   			 }   			 
   		 }
   		 if(isExisting == false){
			 adminService.createNote(note, createNoteCallback());
   		 }
   		 if(isExisting == true){
   			Window.alert("Es existiert bereits eine Notiz mit diesem Titel");
   		 }

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
    			severe("Success GetPermissionsCallback: " + result.getClass().getSimpleName());
    			for(int i = 0; i < result.size(); i++){
    				if(result.get(i).getUserID() != currentUser.getUserID()){
    				UserPermission up = new UserPermission();
    				up.setMail(null);
    				up.setUserID(result.get(i).getUserID());
    				up.setPermissionID(result.get(i).getPermissionID());
    				up.setPermissionType(result.get(i).getPermissionType());
    				userPermission.add(up);
    				}
    			}
    			permissions = result;
    			y = 0;
    			adminService.getUserByID(userPermission.get(y).getUserID(), getUserByIDCallback());
    		}
    	};
    	return asyncCallback;
    }
    
    private AsyncCallback<AppUser> getUserByIDCallback() {
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
    
}