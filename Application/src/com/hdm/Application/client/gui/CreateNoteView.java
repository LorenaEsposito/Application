package com.hdm.Application.client.gui;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.cell.client.TextCell;
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
import com.google.gwt.view.client.MultiSelectionModel;
import com.hdm.Application.client.Application;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.DueDate;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.Permission;

public class CreateNoteView extends Update{

	
	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	private String currentNBTitle = new String();
	
	private Notebook currentNB = new Notebook();
	
	private Note currentN = new Note();
	
	private	Note note = new Note();
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	private ArrayList<Permission> notePermissions = new ArrayList<Permission>();
	
	private ArrayList<Notebook> notebooks = new ArrayList<Notebook>();
	
	private AppUser user = new AppUser();
	
	Date date = new Date();
	
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
    
   TextBox noteTitle = new TextBox();
   TextBox noteSubtitle = new TextBox();
   TextBox permissionText = new TextBox();
   TextArea textArea = new TextArea();
   final Button createButton = new Button("Create");
   final Button cancelButton = new Button("Cancel");
   final Button savePermissionButton = new Button("Save");
   final RadioButton readButton = new RadioButton("Leseberechtigung");
   final RadioButton editButton = new RadioButton("Bearbeitungsberechtigung");
   final RadioButton deleteButton = new RadioButton("Loeschberechtigung");
   DatePicker datePicker = new DatePicker();
   Label rightsLabel = new Label("Berechtigung vergeben:");
   Label duedateLabel = new Label("Enddatum vergeben:");
   Label mainheadline = new Label("Neue Notiz");

protected void run() {
    this.append("");
    
    mainPanel.setStyleName("detailsPanel");

    currentNBTitle = Application.listbox.getSelectedItemText();
    
    
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
    noteSubtitle.setText("Subtitel");
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
    buttonPanel.setStyleName("buttonPanel");
    permissionPanel.setStyleName("permissionPanel");
    rightsLabel.setStyleName("headline");
    duedateLabel.setStyleName("headline");
    
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
    		savePermissionButton.setStylePrimaryName("savePermission-button");
    	
    		if(permissionText.getText() == ""){
    			Window.alert("Bitte eine E-Mail-Adresse eingeben");
    			savePermissionButton.setEnabled(true);
    			readButton.setEnabled(true);
    			readButton.setValue(false);
    			editButton.setEnabled(true);
    			editButton.setValue(false);
    			deleteButton.setEnabled(true);
    			deleteButton.setValue(false);
    		}

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
		//createButton.setStylePrimaryName("");
		
		note.setnTitle(noteTitle.getText());
		note.setnSubtitle(noteSubtitle.getText());
		note.setnContent(textArea.getText());
		note.setnCreDate(date);
		note.setnModDate(date);

		adminService.searchForNotebook(currentNBTitle, searchForNotebookCallback());
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
    

	private AsyncCallback<ArrayList<Notebook>> searchForNotebookCallback(){
		AsyncCallback<ArrayList<Notebook>> asyncCallback = new AsyncCallback<ArrayList<Notebook>>(){
			
			@Override
			public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(ArrayList<Notebook> result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success SearchForNotebookCallback: " + result.getClass().getSimpleName());
    		 
    		 notebooks = result;
    		 
    		 adminService.getCurrentUser(getCurrentUserCallback());
    		 
    	 }
		};
		return asyncCallback;
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
    		 
    		 user = result;
    		 
    		 adminService.getOwnedNotebookPermissions(user, getOwnedNotebookPermissionsCallback());
    		 
    	 }
		};
		return asyncCallback;
	}
	
	private AsyncCallback<ArrayList<Permission>> getOwnedNotebookPermissionsCallback(){
		AsyncCallback<ArrayList<Permission>> asyncCallback = new AsyncCallback<ArrayList<Permission>>() {
			
			@Override
			public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(ArrayList<Permission> result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success GetOwnedNotebookPermissionsCallback: " + result.getClass().getSimpleName());
    		 
    		 permissions = result;
    		 
    		 for(int i = 0; i < notebooks.size(); i++){
    			 for(int x = 0; x < permissions.size(); x++){
    				 if(notebooks.get(i).getNbID() == permissions.get(x).getNbID()){
    					 currentNB = notebooks.get(i);
    				 }
    			 }
    		 }
    		 note.setNbID(currentNB.getNbID());
    		 adminService.getNotesOfNotebook(currentNBTitle, user, getNotesOfNotebookCallback());
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
    		 
    		 Application.list.add(note.getnTitle());
    		 currentN = result;

    		 Permission permission = new Permission();
    			permission.setIsOwner(true);
    			permission.setNbID(currentNB.getNbID());
    			permission.setPermissionType(3);
    			permission.setUserID(user.getUserID());
    			
    			notePermissions.add(permission);
    			
    			for(int i = 0; i < notePermissions.size(); i++){
    				permission = notePermissions.get(i);
    				permission.setNID(currentN.getnID());
    				permission.setNbID(currentNB.getNbID());
    				
    			}
	   			 Update update = new EditNoteView();
		          
		          RootPanel.get("Details").clear();
		          RootPanel.get("Details").add(update);
		          
				adminService.createPermissions(notePermissions, createPermissionsCallback());
				
				if(datePicker.getValue() != null) {
					DueDate duedate = new DueDate();
					duedate.setdDate(datePicker.getValue());
					duedate.setnID(currentN.getnID());
					adminService.createDuedate(duedate, createDuedateCallback());
				}
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
        			
        			notePermissions.add(permission);
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
   			Window.alert("Diese Notiz existiert bereits im ausgewaehlten Notizbuch");
   		 }
   	 }
   	 };
   	 return asyncCallback;
    }
    
}