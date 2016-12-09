package com.hdm.Application.client.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
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
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.ListDataProvider;

import com.hdm.Application.client.Application;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
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
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	private ArrayList<Permission> notePermissions = new ArrayList<Permission>();
	
	private ArrayList<Notebook> notebooks = new ArrayList<Notebook>();
	
	private AppUser user = new AppUser();
	
	Date date = new Date();
	
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
   DatePicker duedate = new DatePicker();
   CellTable<AppUser> table = new CellTable<AppUser>(); 
   Label testLabel = new Label();
   Label rightsLabel = new Label("Berechtigung vergeben:");
   Label duedateLabel = new Label("Enddatum vergeben:");
   Label mainheadline = new Label("Neue Notiz");
   

protected void run() {
    this.append("");
    
    mainPanel.setStyleName("detailsPanel");
    
    currentNBTitle = Application.listbox.getSelectedItemText();
    
    testLabel.setText(date.toString());
    
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
    buttonPanel.add(testLabel);
    rightPanel.add(rightsLabel);
    rightPanel.add(permissionPanel);
    permissionPanel.add(permissionText);
    permissionPanel.add(readButton);
    permissionPanel.add(editButton);
    permissionPanel.add(savePermissionButton);
    rightPanel.add(table);
    rightPanel.add(duedateLabel);
    leftPanel.add(noteTitle);
    leftPanel.add(noteSubtitle);
    leftPanel.add(textArea); 
    
    rightPanel.add(duedate);
    
  
  
    //buttonPanel.add(editButton);
    //buttonPanel.add(deleteButton);
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);
    
    RootPanel.get("Details").add(headlinePanel);
    RootPanel.get("Details").add(mainPanel);
    RootPanel.get("Details").add(buttonPanel);
    
    textArea.setVisibleLines(20);
    textArea.setPixelSize(420, 350);
    
    noteTitle.setText("Überschrift");
    noteSubtitle.setText("Subtitel");
    readButton.setText("Leseberechtigung");
    editButton.setText("Bearbeitungsberechtigung");
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
    permissionText.setStyleName("style-Textbox");
    duedate.setStyleName("datepicker");
    textArea.setStyleName("TextArea");
    savePermissionButton.setStyleName("savePermission-button");
    buttonPanel.setStyleName("buttonPanel");
    permissionPanel.setStyleName("permissionPanel");
    rightsLabel.setStyleName("headline");
    duedateLabel.setStyleName("headline");

    //editButton.setStyleName("notework-menubutton");
    //deleteButton.setStyleName("notework-menubutton");
    
    adminService.searchForNotebook(currentNBTitle, searchForNotebookCallback());
    
    /*
	 * Sperren der Eingabemoeglichkeit im DatePicker bei zukuenftigen Daten
	 */
	duedate.addShowRangeHandlerAndFire(new ShowRangeHandler<java.util.Date>() {
		@Override
		public void onShowRange(ShowRangeEvent<Date> event) {
			Date start = event.getStart();
			Date temp = CalendarUtil.copyDate(start);
			Date end = event.getEnd();

			Date today = new Date();

			while (temp.before(end)) {
				if (temp.before(today) && duedate.isDateVisible(temp)) {
					duedate.setTransientEnabledOnDates(false, temp);
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
	
	permissionText.addClickHandler(new ClickHandler(){
		public void onClick(ClickEvent event){
			permissionText.setText("");
		}
	});
	
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
    		
    		String googleID = new String();
    		
    		int atIndex = permissionText.getText().indexOf("@");
    		googleID = permissionText.getText().substring(0, atIndex);
    		
    		adminService.searchUserByGoogleID(googleID, searchUserByGoogleIDCallback());	
    		
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
		
		Note note = new Note();
		note.setnTitle(noteTitle.getText());
		note.setnSubtitle(noteSubtitle.getText());
		note.setnContent(textArea.getText());
		note.setnCreDate(date);
		note.setnModDate(date);
		note.setNbID(currentNB.getNbID());

		adminService.createNote(note, createNoteCallback());
		

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
    		cancelButton.setStylePrimaryName("");
    		
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
    		 
    		 adminService.getOwnedNotebooks(user, getOwnedNotebooksCallback());
    		 
    	 }
		};
		return asyncCallback;
	}
	
	private AsyncCallback<ArrayList<Permission>> getOwnedNotebooksCallback(){
		AsyncCallback<ArrayList<Permission>> asyncCallback = new AsyncCallback<ArrayList<Permission>>() {
			
			@Override
			public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(ArrayList<Permission> result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success GetOwnedNotebooksCallback: " + result.getClass().getSimpleName());
    		 
    		 permissions = result;
    		 
    		 for(int i = 0; i < notebooks.size(); i++){
    			 for(int x = 0; x < permissions.size(); x++){
    				 if(notebooks.get(i).getNbID() == permissions.get(x).getNbID()){
    					 currentNB = notebooks.get(i);
    				 }
    			 }
    		 }
    		 
    	 }
		};
		return asyncCallback;
	}

    private AsyncCallback<Void> createNoteCallback() {
    	AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>(){
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(Void result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success CreateNoteCallback: " + result.getClass().getSimpleName());
    		 
    		 adminService.getNotesOfNotebook(currentNBTitle, user, getNotesOfNotebookCallback());

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
        			permission.setUserID(user.getUserID());
        			permission.setIsOwner(false);
        			permission.setNbID(currentNB.getNbID());
        			
        			if(readButton.getValue() == true){
        				permission.setPermissionType(false);
        			}
        			if(editButton.getValue() == true){
        				permission.setPermissionType(true);
        			}
        			if(readButton.getValue() == false && editButton.getValue() == false){
        				Window.alert("Bitte waehlen Sie eine Art der Berechtigung aus");
        				savePermissionButton.setEnabled(true);
        			}
        			
        			permissions.add(permission);
        			
        			savePermissionButton.setEnabled(true);
        			permissionText.setText("Name des Berechtigten");
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
   		 
   		 for(int x = 0; x < result.size(); x++){
   			 if(noteTitle.getText() == result.get(x).getnTitle()){
   				 currentN = result.get(x);
   			 }
   		 }
   		 
		 Permission permission = new Permission();
			permission.setIsOwner(true);
			permission.setNbID(currentNB.getNbID());
			permission.setPermissionType(true);
			permission.setUserID(user.getUserID());
			
			notePermissions.add(permission);
			
			for(int i = 0; i < notePermissions.size(); i++){
				permission = notePermissions.get(i);
				permission.setNID(currentN.getnID());
				adminService.createPermission(permission, createPermissionCallback());
			}	
   	 }
   	 };
   	 return asyncCallback;
    }
    
}

