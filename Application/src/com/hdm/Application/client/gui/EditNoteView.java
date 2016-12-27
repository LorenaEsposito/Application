package com.hdm.Application.client.gui;

import java.util.ArrayList;
import java.util.Date;

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
	
	private Note n = new Note();
	
	private Note currentNote = new Note();
	
	private Notebook currentNB = new Notebook();
	
	private String currentNBTitle = Application.listbox.getSelectedValue();
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	private Note newNote = currentNote;
	
	private AppUser user = new AppUser();
	
	//private String currentDateTime =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());;
	
	Date date = new Date();
	
	TextCell cell = new TextCell();
	
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
   final Button saveNoteButton = new Button("Save Note");
   final Button deleteNoteButton = new Button("Delete Note");
   final Button cancelButton = new Button("Cancel");
   final Button savePermissionButton = new Button("Save Permission");
   final RadioButton readButton = new RadioButton("Leseberechtigung");
   final RadioButton editButton = new RadioButton("Bearbeitungsberechtigung");
   DatePicker duedate = new DatePicker();
   CellList<String> cellList = new CellList<String>(cell);
   Label rightsLabel = new Label("Berechtigung vergeben:");
   Label duedateLabel = new Label("Enddatum vergeben:");
   Label mainheadline = new Label("Notiz bearbeiten");
   
//	private Note currentNote = null;

protected void run() {
    this.append("");
    
    mainPanel.setStyleName("detailsPanel");
    
    currentNBTitle = Application.listbox.getSelectedItemText();
    
    currentNote = ShowNoteView.note;
    
 // Create a data provider.
    ListDataProvider<String> dataProvider = new ListDataProvider<String>();
    
 // Connect the table to the data provider.
    dataProvider.addDataDisplay(cellList);

	/**
     * Zuteilung der Widgets zum jeweiligen Panel
     */
    
    
    headlinePanel.add(mainheadline);
    buttonPanel.add(saveNoteButton);
    buttonPanel.add(deleteNoteButton);
    buttonPanel.add(cancelButton);
    rightPanel.add(rightsLabel);
    rightPanel.add(permissionPanel);
    permissionPanel.add(permissionText);
    permissionPanel.add(readButton);
    permissionPanel.add(editButton);
    permissionPanel.add(savePermissionButton);
    rightPanel.add(duedateLabel);
    leftPanel.add(noteTitle);
    leftPanel.add(noteSubtitle);
    leftPanel.add(textArea); 
    
    leftPanel.add(buttonPanel);
    
    
    rightPanel.add(duedate);
    rightPanel.add(cellList);
 
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);
    
    RootPanel.get("Details").add(headlinePanel);
    RootPanel.get("Details").add(mainPanel);
        
    textArea.setVisibleLines(20);
    textArea.setPixelSize(420, 350);
    
    noteTitle.setText(currentNote.getnTitle());
    noteSubtitle.setText(currentNote.getnSubtitle());
    textArea.setText(currentNote.getnContent());
    readButton.setText("Leseberechtigung");
    editButton.setText("Bearbeitungsberechtigung");
    permissionText.setText("Name des Berechtigten");
    
    /**
     * Zuweisung eines Styles fuer die jeweiligen Widgets
     **/
    headlinePanel.setStyleName("headlinePanel");
    noteTitle.setStyleName("noteTitle");
    noteSubtitle.setStyleName("noteTitle");
    saveNoteButton.setStyleName("savePermission-button");
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
		
		newNote.setnTitle(noteTitle.getText());
		newNote.setnSubtitle(noteSubtitle.getText());
		newNote.setnContent(textArea.getText());
		newNote.setnModDate(date);
		
		 adminService.getCurrentUser(getCurrentUserCallback());
		//adminService.editNote(newNote, editNoteCallback());

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
		 
		 user = result;
		 
		 adminService.getNotebookByID(currentNote.getNbID(), getNotebookByIDCallback());
		 
	 }
	};
	return asyncCallback;
}

private AsyncCallback<Notebook> getNotebookByIDCallback(){
	AsyncCallback<Notebook> asyncCallback = new AsyncCallback<Notebook>() {
		
		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}
	 
	 @Override
	 public void onSuccess(Notebook result) {
		 ClientsideSettings.getLogger().
		 severe("Success GetOwnedNotebookPermissionsCallback: " + result.getClass().getSimpleName());
		 
		 currentNB = result;
		
		 adminService.getNotesOfNotebook(currentNB.getNbTitle(), user, getNotesOfNotebookCallback());
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
			 adminService.editNote(newNote, editNoteCallback());
  		 }
  		 if(isExisting == true){
  			Window.alert("Diese Notiz existiert bereits im ausgewaehlten Notizbuch");
  		 }
  	 }
  	 };
  	 return asyncCallback;
   }
   

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

    private AsyncCallback<Void> editNoteCallback() {
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

