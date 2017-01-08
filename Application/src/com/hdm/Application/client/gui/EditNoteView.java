package com.hdm.Application.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RichTextArea;
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

public class EditNoteView extends Update{


	
	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	//private Note n = new Note();
	
	private Note currentNote = new Note();
	
	private Notebook currentNB = new Notebook();
	
	private String currentNBTitle = new String();
	
	private String noteTitle = new String();
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	private ArrayList<Note> notesOfNB = new ArrayList<Note>();
	
	private Note newNote = currentNote;
	
	private AppUser user = new AppUser();
	
	private AppUser currentUser = new AppUser();
	
	Date date = new Date();
	
	TextCell cell = new TextCell();
	
	 // Create a data provider.
    ListDataProvider<String> dataProvider = new ListDataProvider<String>();
    
    List<String> list = dataProvider.getList();
	
	protected String getHeadlineText() {
	    return "";
}
	
	/**
	   * Erstellung aller Panels
	   */

  HorizontalPanel headlinePanel = new HorizontalPanel();
  HorizontalPanel mainPanel = new HorizontalPanel();
  HorizontalPanel datePanel = new HorizontalPanel();
  HorizontalPanel buttonPanel = new HorizontalPanel();
  VerticalPanel permissionPanel = new VerticalPanel();
  VerticalPanel leftPanel = new VerticalPanel();
  VerticalPanel rightPanel = new VerticalPanel();
 
  
  
  /**
   * Erstellung aller Widgets
   */
    
   TextBox noteTitleTB = new TextBox();
   TextBox noteSubtitleTB = new TextBox();
   TextBox permissionTB = new TextBox();
   TextArea textArea = new TextArea();
   final Button saveNoteButton = new Button("Save Note");
   final Button deleteNoteButton = new Button("Delete Note");
   final Button cancelButton = new Button("Cancel");
   final Button savePermissionButton = new Button("Save Permission");
   final Button deletePermissionButton = new Button("Berechtigung loeschen");
   final RadioButton readButton = new RadioButton("Leseberechtigung");
   final RadioButton editButton = new RadioButton("Bearbeitungsberechtigung");
   final RadioButton deleteButton = new RadioButton("Loeschberechtigung");
   DatePicker duedate = new DatePicker();
   CellList<String> cellList = new CellList<String>(cell);
   Label rightsLabel = new Label("Berechtigung vergeben:");
   Label duedateLabel = new Label("Enddatum vergeben:");
   Label mainheadline = new Label("Notiz bearbeiten");
   Label credateLabel = new Label("Erstellungsdatum ");
   Label credate = new Label("");
   Label moddateLabel = new Label("Zuletzt bearbeitet am ");
   Label moddate = new Label("");
   
//	private Note currentNote = null;

protected void run() {
    this.append("");
    
    mainPanel.setStyleName("detailsPanel");
    
    // Connect the table to the data provider.
    dataProvider.addDataDisplay(cellList);
    
    noteTitle = Application.selectionModel.getSelectedObject();
    
    currentNBTitle = Application.listbox.getSelectedItemText();

	/**
     * Zuteilung der Widgets zum jeweiligen Panel
     */
    
    
    headlinePanel.add(mainheadline);
    buttonPanel.add(saveNoteButton);
    buttonPanel.add(deleteNoteButton);
    buttonPanel.add(cancelButton);
    rightPanel.add(rightsLabel);
    rightPanel.add(permissionPanel);
    permissionPanel.add(permissionTB);
    permissionPanel.add(readButton);
    permissionPanel.add(editButton);
    permissionPanel.add(deleteButton);
    permissionPanel.add(savePermissionButton);
    permissionPanel.add(deletePermissionButton);
    rightPanel.add(duedateLabel);
    leftPanel.add(noteTitleTB);
    leftPanel.add(noteSubtitleTB);
    leftPanel.add(textArea); 
    datePanel.add(credateLabel);
    datePanel.add(credate);
    datePanel.add(moddateLabel);
    datePanel.add(moddate);
    
    leftPanel.add(buttonPanel);
    
    
    rightPanel.add(duedate);
    rightPanel.add(cellList);
 
    mainPanel.add(datePanel);
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);
    
    RootPanel.get("Details").add(headlinePanel);
    RootPanel.get("Details").add(mainPanel);
        
    textArea.setVisibleLines(20);
    textArea.setPixelSize(420, 350);
    
    readButton.setText("Leseberechtigung");
    editButton.setText("Bearbeitungsberechtigung");
    deleteButton.setText("Loeschberechtigung");
    permissionTB.setText("Name des Berechtigten");
    
    /**
     * Zuweisung eines Styles fuer die jeweiligen Widgets
     **/
    headlinePanel.setStyleName("headlinePanel");
    noteTitleTB.setStyleName("noteTitle");
    noteSubtitleTB.setStyleName("noteTitle");
    saveNoteButton.setStyleName("savePermission-button");
    cancelButton.setStyleName("savePermission-button");
    readButton.setStyleName("savePermission-button");
    editButton.setStyleName("savePermission-button");
    deleteButton.setStyleName("savePermission-button");
    permissionTB.setStyleName("style-Textbox");
    duedate.setStyleName("datepicker");
    textArea.setStyleName("TextArea");
    savePermissionButton.setStyleName("savePermission-button");
    deletePermissionButton.setStyleName("savePermission-button");
    buttonPanel.setStyleName("buttonPanel");
    permissionPanel.setStyleName("permissionPanel");
    rightsLabel.setStyleName("headline");
    duedateLabel.setStyleName("headline");
    credateLabel.setStyleName("");
    credate.setStyleName("");
    moddateLabel.setStyleName("");
    moddate.setStyleName("");
    
	adminService.getCurrentUser(getCurrentUserCallback());
    
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
    		
    		
    		AppUser user = new AppUser();
    		String googleID = new String();
			Permission permission = new Permission();
    		
    		int atIndex = permissionTB.getText().indexOf("@");
    		googleID = permissionTB.getText().substring(0, atIndex);
    		
    		adminService.searchUserByGoogleID(googleID, searchUserByGoogleIDCallback());
    		
    		if(user != null){
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
    		}
    	}
    });
    
    saveNoteButton.addClickHandler(new ClickHandler() {
  	public void onClick(ClickEvent event) {
  		
  		/*
		 * Speichern der eingegebenen Werte blockieren, um
		 * Mehrfach-Klicks und daraus entstehende, unnoetige Eintraege in
		 * der Datenbank zu verhindern.
		 */
		saveNoteButton.setEnabled(false);
		//createButton.setStylePrimaryName("");
		
		newNote.setnTitle(noteTitleTB.getText());
		newNote.setnSubtitle(noteSubtitleTB.getText());
		newNote.setnContent(textArea.getText());
		newNote.setnModDate(date);
		
//		 adminService.getCurrentUser(getCurrentUserCallback());
//		 adminService.editNote(newNote, editNoteCallback());
		
		boolean isExisting = new Boolean(false);
 		 for(int y = 0; y < notesOfNB.size(); y++) {
 			 if(noteTitleTB.getText() == notesOfNB.get(y).getnTitle()) {
 				 noteTitleTB.setText("");
 				 isExisting = true;
 				 break;
 			 }   			 
 		 }
 		 if(isExisting == false){
			 adminService.editNote(newNote, editNoteCallback());
 		 }
 		 if(isExisting == true){
 			Window.alert("Diese Notiz existiert bereits im ausgewaehlten Notizbuch");
 		 }

          /*
           * Showcase instantiieren.
           */
          Update update = new EditNoteView();
          
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
    		
    		adminService.deleteNote(currentNote, deleteNoteCallback());
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
		 
		 adminService.getNotesOfNotebook(currentNBTitle, currentUser, getNotesOfNotebookCallback());
		 
	 }
	};
	return asyncCallback;
}

private AsyncCallback<ArrayList<Note>> getNotesOfNotebookCallback() {
	AsyncCallback<ArrayList<Note>> asyncCallback = new AsyncCallback<ArrayList<Note>>() {
		
		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}
	 
	 @Override
	 public void onSuccess(ArrayList<Note> result) {
		 ClientsideSettings.getLogger().
		 severe("Success GetNotesOfNotebookCallback: " + result.getClass().getSimpleName());
		 notesOfNB = result;
		 
		 for(int i = 0; i < notesOfNB.size(); i++){
			 if(noteTitle == notesOfNB.get(i).getnTitle()) {
				 currentNote = notesOfNB.get(i);
				 noteTitleTB.setText(currentNote.getnTitle());
				 noteSubtitleTB.setText(currentNote.getnSubtitle());
				 textArea.setText(currentNote.getnContent());
				 credate.setText(currentNote.getnCreDate().toString());
				 moddate.setText(currentNote.getnModDate().toString());
				 adminService.getPermissions(currentNote.getnID(), currentNote.getNbID(), getPermissionsCallback());
				 break;
			 }
		 }
	 }
	};
	return asyncCallback;
}

private AsyncCallback<ArrayList<Permission>> getPermissionsCallback(){
	AsyncCallback<ArrayList<Permission>> asyncCallback = new AsyncCallback<ArrayList<Permission>>() {
		
		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}
	 
	 @Override
	 public void onSuccess(ArrayList<Permission> result) {
		 ClientsideSettings.getLogger().
		 severe("Success GetPermissionsCallback: " + result.getClass().getSimpleName());
		 for(int i = 0; i < result.size(); i++) {
			 adminService.getUserByID(result.get(i).getUserID(), getUserByIDCallback());
		 }
		 
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
		 list.add(result.getUserName());
	 }
	};
	return asyncCallback;
}

//private AsyncCallback<AppUser> getCurrentUserCallback(){
//	AsyncCallback<AppUser> asyncCallback = new AsyncCallback<AppUser>() {
//		
//		@Override
//		public void onFailure(Throwable caught) {
//			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
//		}
//	 
//	 @Override
//	 public void onSuccess(AppUser result) {
//		 ClientsideSettings.getLogger().
//		 severe("Success GetCurrentUserCallback: " + result.getClass().getSimpleName());
//		 
//		 user = result;
//		 
//		 adminService.getNotebookByID(currentNote.getNbID(), getNotebookByIDCallback());
//		 
//	 }
//	};
//	return asyncCallback;
//}
//
//private AsyncCallback<Notebook> getNotebookByIDCallback(){
//	AsyncCallback<Notebook> asyncCallback = new AsyncCallback<Notebook>() {
//		
//		@Override
//		public void onFailure(Throwable caught) {
//			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
//		}
//	 
//	 @Override
//	 public void onSuccess(Notebook result) {
//		 ClientsideSettings.getLogger().
//		 severe("Success GetOwnedNotebookPermissionsCallback: " + result.getClass().getSimpleName());
//		 
//		 currentNB = result;
//		
//		 adminService.getNotesOfNotebook(currentNB.getNbTitle(), user, getNotesOfNotebookCallback());
//	 }
//	};
//	return asyncCallback;
//}
//
//private AsyncCallback<ArrayList<Note>> getNotesOfNotebookCallback() {
//  	 AsyncCallback<ArrayList<Note>> asyncCallback = new AsyncCallback<ArrayList<Note>>(){
//  	 
//  	 @Override
//  		public void onFailure(Throwable caught) {
//  			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
//  		}
//  	 
//  	 @Override
//  	 public void onSuccess(ArrayList<Note> result) {
//  		 ClientsideSettings.getLogger().
//  		 severe("Success GetNotesOfNotebookCallback: " + result.getClass().getSimpleName());
//  		 
//  		 boolean isExisting = new Boolean(false);
//  		 for(int y = 0; y < result.size(); y++) {
//  			 if(noteTitleTB.getText() == result.get(y).getnTitle()) {
//  				 noteTitleTB.setText("");
//  				 isExisting = true;
//  				 break;
//  			 }   			 
//  		 }
//  		 if(isExisting == false){
//			 adminService.editNote(newNote, editNoteCallback());
//  		 }
//  		 if(isExisting == true){
//  			Window.alert("Diese Notiz existiert bereits im ausgewaehlten Notizbuch");
//  		 }
//  	 }
//  	 };
//  	 return asyncCallback;
//   }
   
	private AsyncCallback<Void> deleteNoteCallback() {
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

    private AsyncCallback<Note> editNoteCallback() {
    	AsyncCallback<Note> asyncCallback = new AsyncCallback<Note>(){
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    	 
    	 @Override
    	 public void onSuccess(Note result) {
    		 ClientsideSettings.getLogger().
    		 severe("Success EditNoteCallback: " + result.getClass().getSimpleName());
    	 }
    	};
    	return asyncCallback;

    }
    
//    private AsyncCallback<Void> createPermissionCallback() {
//    	AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
//    		
//    		@Override
//    		public void onFailure(Throwable caught) {
//    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
//    		}
//    		
//    		@Override
//    		public void onSuccess(Void result) {
//    			ClientsideSettings.getLogger().
//    			severe("Success CreatePermissionCallback: " + result.getClass().getSimpleName());
//    		}
//    	};
//    	return asyncCallback;
//    }
//    
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

