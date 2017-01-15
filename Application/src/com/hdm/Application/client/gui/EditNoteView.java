package com.hdm.Application.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import com.google.gwt.view.client.SingleSelectionModel;
import com.hdm.Application.client.Application;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.DueDate;
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
	
	Permission newPermission = new Permission();
	
	private ArrayList<Note> notesOfNB = new ArrayList<Note>();
	
	private Note newNote = currentNote;
	
	private AppUser user = new AppUser();
	
	private AppUser permissionUser = new AppUser();
	
	private AppUser currentUser = new AppUser();
	
	private Permission permission = new Permission();
	
	Date date = new Date();
	
	DueDate dueDate = new DueDate();
	
	DueDate newDueDate = new DueDate();
	
	TextCell cell = new TextCell();
	
	public final static SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
	
	 // Create a data provider.
    ListDataProvider<String> dataProvider = new ListDataProvider<String>();
    
    List<String> list = dataProvider.getList();
    
//    NotebookNotesTreeViewModel nntvm = null;
	
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
   final Button saveDuedateButton = new Button("Speichern");
   final Button deleteDuedateButton = new Button("Loeschen");
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
    
    cellList.setSelectionModel(selectionModel);
    
    noteTitle = Application.notesSelectionModel.getSelectedObject();
    
    currentNBTitle = Application.listbox.getSelectedItemText();
    
    currentNB = Application.nbSelectionModel.getSelectedObject();

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
    
    rightPanel.add(cellList);
    rightPanel.add(duedate);
    rightPanel.add(saveDuedateButton);
    rightPanel.add(deleteDuedateButton);
 
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
    
    noteTitleTB.setEnabled(false);
    noteSubtitleTB.setEnabled(false);
    permissionTB.setEnabled(false);
    textArea.setEnabled(false);
    saveNoteButton.setEnabled(false);
    deleteNoteButton.setEnabled(false);
    savePermissionButton.setEnabled(false);
    deletePermissionButton.setEnabled(false);
    saveDuedateButton.setEnabled(false);
    deleteDuedateButton.setEnabled(false);
    readButton.setEnabled(false);
    editButton.setEnabled(false);
    deleteButton.setEnabled(false);
    
    
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
    		
    		
    		if(permissionTB.getValue() != ""){
    		
    		adminService.searchUserByMail(permissionTB.getValue(), searchUserByMailCallback());
    		}
    		if(selectionModel.getSelectedObject() == null){
    			Window.alert("Erstellen Sie eine neue Berechtigung oder bearbeiten Sie eine bestehende.");
    		}
    		if(permissionTB.getValue() == ""){
    			adminService.searchUserByMail(selectionModel.getSelectedObject(), searchUserByMailCallback());
    		}
    		
    	}
    });
    
    deletePermissionButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event){
    		deletePermissionButton.setEnabled(false);
    		savePermissionButton.setEnabled(false);
    		
    		adminService.getUserByMail(selectionModel.getSelectedObject(), getUserForPermissionDeleteCallback()); 
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
		
		newNote.setnID(currentNote.getnID());
		newNote.setnTitle(noteTitleTB.getText());
		newNote.setnSubtitle(noteSubtitleTB.getText());
		newNote.setnContent(textArea.getText());
		newNote.setnCreDate(currentNote.getnCreDate());
		newNote.setnModDate(date);
		
		if(dueDate == null){
			newDueDate.setnID(currentNote.getnID());
			adminService.createDuedate(newDueDate, createDuedateCallback());
		}else{
		adminService.editDuedate(dueDate, editDuedateCallback());
		}
//		 adminService.getCurrentUser(getCurrentUserCallback());
//		 adminService.editNote(newNote, editNoteCallback());
		
		boolean isExisting = new Boolean(false);
		if(newNote.getnTitle() == currentNote.getnTitle()){
			adminService.editNote(newNote, editNoteCallback());
		}
		else{
 		 for(int y = 0; y < notesOfNB.size(); y++) {
 			 if(noteTitleTB.getText() == notesOfNB.get(y).getnTitle()) {
 				 noteTitleTB.setText(currentNote.getnTitle());
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
		}

          /*
           * Showcase instantiieren.
           */
          Update update = new EditNoteView();
          
          RootPanel.get("Details").clear();
          RootPanel.get("Details").add(update);
    }
    });
    
    deleteNoteButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event) {
    		deleteNoteButton.setEnabled(false);
    		
    		adminService.deleteNote(currentNote, deleteNoteCallback());
    		
    		Update update = new WelcomeView();
    		
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
    
    saveDuedateButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event){
    		if(dueDate != null){
        		dueDate.setdDate(duedate.getValue());	
    		}else{
    		newDueDate.setdDate(duedate.getValue());
    		}

    	}
    });
    
    deleteDuedateButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event){
    		adminService.deleteDuedate(dueDate, deleteDuedateCallback());
    		
    	}
    });

//    editButton.addClickHandler(new ClickHandler() {
//  	public void onClick(ClickEvent event) {
//          /*
//           * Showcase instantiieren.
//           */
//          Update update = new EditNoteView();
//          
//          RootPanel.get("Details").clear();
//          RootPanel.get("Details").add(update);
//    }
//    });
//    
//    deleteButton.addClickHandler(new ClickHandler() {
//  	public void onClick(ClickEvent event) {
//          /*
//           * Showcase instantiieren.
//           */
//          Update update = new NoteOverviewView();
//          
//          RootPanel.get("Details").clear();
//          RootPanel.get("Details").add(update);
//    }
//    });
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
		 
		 adminService.getNotesOfNotebookTitle(currentNBTitle, currentUser, getNotesOfNotebookTitleCallback());
		 
	 }
	};
	return asyncCallback;
}

private AsyncCallback<ArrayList<Note>> getNotesOfNotebookTitleCallback() {
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
				 adminService.getDuedate(currentNote.getnID(), getDuedateCallback());
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
			 if(currentUser.getUserID() == result.get(i).getUserID()){
				 if(result.get(i).getPermissionType() == 2){
					 noteTitleTB.setEnabled(true);
					 noteSubtitleTB.setEnabled(true);
					    permissionTB.setEnabled(true);
					    textArea.setEnabled(true);
					    saveNoteButton.setEnabled(true);
					    savePermissionButton.setEnabled(true);
					    deletePermissionButton.setEnabled(true);
					    saveDuedateButton.setEnabled(true);
					    deleteDuedateButton.setEnabled(true);
					    readButton.setEnabled(true);
					    editButton.setEnabled(true);
					    deleteButton.setEnabled(true);
				 }
				 if(result.get(i).getPermissionType() == 3){
					 noteTitleTB.setEnabled(true);
					    noteSubtitleTB.setEnabled(true);
					    permissionTB.setEnabled(true);
					    textArea.setEnabled(true);
					    saveNoteButton.setEnabled(true);
					    deleteNoteButton.setEnabled(true);
					    savePermissionButton.setEnabled(true);
					    deletePermissionButton.setEnabled(true);
					    saveDuedateButton.setEnabled(true);
					    deleteDuedateButton.setEnabled(true);
					    readButton.setEnabled(true);
					    editButton.setEnabled(true);
					    deleteButton.setEnabled(true);
				 }
			 }
			 else{
			 adminService.getUserByID(result.get(i).getUserID(), getUserByIDCallback());
			 }
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
		 list.add(result.getMail());
	 }
	};
	return asyncCallback;
}

private AsyncCallback<DueDate> getDuedateCallback(){
	AsyncCallback<DueDate> asyncCallback = new AsyncCallback<DueDate>() {
		
		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}
	 
	 @Override
	 public void onSuccess(DueDate result) {
		 ClientsideSettings.getLogger().
		 severe("Success GetDuedateCallback: " + result.getClass().getSimpleName());
		 dueDate = result;
		 duedate.setValue(dueDate.getdDate());
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
    		 
    		 Update update = new EditNotebookView();
    		 RootPanel.get("Details").clear();
    		 RootPanel.get("Details").add(update);
    		 
    	 }
		};
		return asyncCallback;
	}
	
	   
		private AsyncCallback<Void> deleteDuedateCallback() {
			AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
				
				@Override
				public void onFailure(Throwable caught) {
	    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	    		}
	    	 
	    	 @Override
	    	 public void onSuccess(Void result) {
	    		 ClientsideSettings.getLogger().
	    		 severe("Success DeleteDuedateCallback: " + result.getClass().getSimpleName());
	    		 
	    		 duedate.setValue(null);
	    		 
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
    		 
    		 Application.notesList.remove(currentNote.getnTitle());
    		 Application.notesList.add(result.getnTitle());
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
        				if(user.getMail() == dataProvider.getList().get(i)) {
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
        				
        				if(readButton.getValue() == true){
            				newPermission.setPermissionType(1);
            			}
            			if(editButton.getValue() == true){
            				newPermission.setPermissionType(2);
            			}
            			if(deleteButton.getValue() == true){
            				newPermission.setPermissionType(3);
            			}
            			if(readButton.getValue() == false && editButton.getValue() == false && deleteButton.getValue() == false){
            				Window.alert("Bitte waehlen Sie eine Art der Berechtigung aus");
            			}
        				
        				adminService.getPermission(user.getUserID(), currentNB.getNbID(), currentNote.getnID(), getPermissionCallback());
        				
        			}
        		}
    		}
    	};
    	return asyncCallback;
    }
    
    private AsyncCallback<Void> editDuedateCallback() {
    	AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
    		
    		@Override
    		public void onFailure(Throwable caught) {
    			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    		}
    		
    		@Override
    		public void onSuccess(Void result) {
    			ClientsideSettings.getLogger().
    			severe("Success EditDuedateCallback: " + result.getClass().getSimpleName());
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
    			adminService.getPermission(permissionUser.getUserID(), currentNB.getNbID(), currentNote.getnID(), getPermissionForDeleteCallback());
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

    			newPermission.setIsOwner(result.getIsOwner());
    			newPermission.setNbID(result.getNbID());
    			newPermission.setNID(result.getNID());
    			newPermission.setPermissionID(result.getPermissionID());
    			newPermission.setUserID(result.getUserID());
    			
    			adminService.editPermission(newPermission, editPermissionCallback());
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
    
    private AsyncCallback<Void> editPermissionCallback() {
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
    
//	public void setNntvm(NotebookNotesTreeViewModel nntvm) {
//		this.nntvm = nntvm;
//	}
//    
//    /*
//	 * Wenn der anzuzeigende Kunde gesetzt bzw. gelöscht wird, werden die
//	 * zugehörenden Textfelder mit den Informationen aus dem Kundenobjekt
//	 * gefüllt bzw. gelöscht.
//	 */
//	public void setSelected(Note n) {
//		if (n != null) {
//			currentNote = n;
//			noteTitleTB.setText(currentNote.getnTitle());
//			noteSubtitleTB.setText(currentNote.getnSubtitle());
//			textArea.setText(currentNote.getnContent());
//		} else {
//			noteTitleTB.setText("");
//			noteSubtitleTB.setText("");
//			textArea.setText("");
//		}
//	}
    
}

