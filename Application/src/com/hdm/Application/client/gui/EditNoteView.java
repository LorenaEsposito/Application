package com.hdm.Application.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.hdm.Application.shared.bo.UserPermission;

/**
 * Diese View ermoeglicht dem User das Bearbeiten einer Notiz, sofern er die noetige Berechtigung dafuer hat.
 * Ist diese nicht vorhanden, so kann er das Notizbuch und die Notizen nur lesen. Ebenfalls koennen die
 * Berechtigungen veraendert oder geloescht werden. Mit einer Loeschberechtigung kann das Notizbuch auch geloescht
 * werden.
 * @author Lorena
 *
 */

public class EditNoteView extends Update{


	
	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	//private Note n = new Note();
	
	private Note currentNote = new Note();
	
	private Notebook currentNB = new Notebook();
	
	private String noteTitle = new String();
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	private ArrayList<Permission> newPermissions = new ArrayList<Permission>();
	
	private ArrayList<Permission> editPermissions = new ArrayList<Permission>();
	
	private ArrayList<UserPermission> userPermission = new ArrayList<UserPermission>();
	
	private int y = new Integer(0);
	
	Permission newPermission = new Permission();
	
	private ArrayList<Note> notesOfNB = new ArrayList<Note>();
	
	private Note newNote = currentNote;
	
	private AppUser user = new AppUser();
	
//	private AppUser permissionUser = new AppUser();
	
	private AppUser currentUser = new AppUser();
	
	private Permission permission = new Permission();
	
	int index = new Integer(0);
	
	Date date = new Date();
	
	DueDate dueDate = null;
	
	DueDate newDueDate = new DueDate();
	
	UserPermissionCell cell = new UserPermissionCell();
	
	public final static SingleSelectionModel<UserPermission> selectionModel = new SingleSelectionModel<UserPermission>();
	
	 // Create a data provider.
    ListDataProvider<UserPermission> dataProvider = new ListDataProvider<UserPermission>();
    
    List<UserPermission> list = dataProvider.getList();
    
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
   final Button saveNoteButton = new Button("Speichern");
   final Button deleteNoteButton = new Button("Loeschen");
   final Button cancelButton = new Button("Abbrechen");
   final Button savePermissionButton = new Button("Speichern");
   final Button deletePermissionButton = new Button("Loeschen");
   final Button saveDuedateButton = new Button("Speichern");
   final Button deleteDuedateButton = new Button("Loeschen");
   final RadioButton readButton = new RadioButton("Leseberechtigung");
   final RadioButton editButton = new RadioButton("Bearbeitungsberechtigung");
   final RadioButton deleteButton = new RadioButton("Loeschberechtigung");
   DatePicker duedate = new DatePicker();
   CellList<UserPermission> cellList = new CellList<UserPermission>(cell);
   Label rightsLabel = new Label("Berechtigung vergeben:");
   Label duedateLabel = new Label("Enddatum vergeben:");
   Label mainheadline = new Label("Notiz bearbeiten");
   Label credateLabel = new Label("Erstellungsdatum ");
   Label credate = new Label("");
   Label moddateLabel = new Label("Zuletzt bearbeitet am ");
   Label moddate = new Label("");
   Label ownerLabel = new Label("");
   
//	private Note currentNote = null;

protected void run() {
    this.append("");
    
    mainPanel.setStyleName("detailsPanel");
    
    // Connect the table to the data provider.
    dataProvider.addDataDisplay(cellList);
    
    cellList.setSelectionModel(selectionModel);
    
    noteTitle = Application.notesSelectionModel.getSelectedObject();
    
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
    rightPanel.add(cellList);
    rightPanel.add(duedateLabel);
    leftPanel.add(ownerLabel);
    leftPanel.add(noteTitleTB);
    leftPanel.add(noteSubtitleTB);
    leftPanel.add(textArea); 
    datePanel.add(credateLabel);
    datePanel.add(credate);
    datePanel.add(moddateLabel);
    datePanel.add(moddate);
    
    
    rightPanel.add(duedate);
    rightPanel.add(saveDuedateButton);
    rightPanel.add(deleteDuedateButton);
 
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);
    
    RootPanel.get("Details").add(headlinePanel);
    RootPanel.get("Details").add(mainPanel);
    RootPanel.get("Details").add(buttonPanel); 
    
    textArea.setVisibleLines(20);
    textArea.setPixelSize(420, 350);
    readButton.setText("Leseberechtigung");
    editButton.setText("Bearbeitungsberechtigung");
    deleteButton.setText("Loeschberechtigung");
    permissionTB.setText("");
    
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
    leftPanel.setStyleName("CreateLeftPanel");
    rightPanel.setStyleName("CreateRightPanel");
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
    deletePermissionButton.setStyleName("savePermission-button2");
    buttonPanel.setStyleName("buttonPanel");
    permissionPanel.setStyleName("permissionPanel");
    rightsLabel.setStyleName("headline");
    duedateLabel.setStyleName("headline");
    deleteNoteButton.setStyleName("savePermission-button");
    saveDuedateButton.setStyleName("savePermission-button");
    deleteDuedateButton.setStyleName("savePermission-button2");

    
    
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
        		savePermissionButton.setEnabled(true);
        		deletePermissionButton.setEnabled(true); 
        		readButton.setEnabled(true);
        		editButton.setEnabled(true);
        		deleteButton.setEnabled(true);
    		}
    		else{
    			
    			UserPermission editUP = new UserPermission();
    			editUP = selectionModel.getSelectedObject();
    			if(editUP.getNID() != 0){
    			if(readButton.getValue() == true){
    				editUP.setPermissionType(1);
    				userPermission.set(userPermission.indexOf(selectionModel.getSelectedObject()), editUP);
    				dataProvider.getList().set(dataProvider.getList().indexOf(selectionModel.getSelectedObject()), editUP);
    			}
    			if(editButton.getValue() == true){
    				editUP.setPermissionType(2);
    				userPermission.set(userPermission.indexOf(selectionModel.getSelectedObject()), editUP);
    				dataProvider.getList().set(dataProvider.getList().indexOf(selectionModel.getSelectedObject()), editUP);
    			}
    			if(deleteButton.getValue() == true){
    				editUP.setPermissionType(3);
    				userPermission.set(userPermission.indexOf(selectionModel.getSelectedObject()), editUP);
    				dataProvider.getList().set(dataProvider.getList().indexOf(selectionModel.getSelectedObject()), editUP);
    			}
    			if(readButton.getValue() == false && editButton.getValue() == false && deleteButton.getValue() == false){
    				Window.alert("Bitte waehlen Sie eine Art der Berechtigung aus");
    			}
    			}else{
    				Window.alert("Notizbuch-Berechtigungen koennen nicht geaendert werden");
    			}
    			permissionTB.setText(""); 
        		savePermissionButton.setEnabled(true);
        		deletePermissionButton.setEnabled(true); 
        		readButton.setEnabled(true);
        		editButton.setEnabled(true);
        		deleteButton.setEnabled(true);
    		}
    		
    	}
    });
    
    deletePermissionButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event){
    		savePermissionButton.setEnabled(false);
    		deletePermissionButton.setEnabled(false);
    		readButton.setEnabled(false);
    		editButton.setEnabled(false);
    		deleteButton.setEnabled(false);
    		readButton.setValue(false);
    		editButton.setValue(false);
    		deleteButton.setValue(false);
    		savePermissionButton.setStylePrimaryName("savePermission-button");
    		if(selectionModel.getSelectedObject() == null){
    			Window.alert("Bitte wahelen Sie eine Person aus.");
        		savePermissionButton.setEnabled(true);
        		deletePermissionButton.setEnabled(true); 
        		readButton.setEnabled(true);
        		editButton.setEnabled(true);
        		deleteButton.setEnabled(true);
        		savePermissionButton.setStylePrimaryName("savePermission-button");
    		}else{
    			boolean deletePossible = new Boolean(true);
    			for(int i = 0; i < permissions.size(); i++){
    				if(permissions.get(i).getPermissionID() == selectionModel.getSelectedObject().getPermissionID() && permissions.get(i).getNID() == 0){
    					deletePossible = false;
    				}
    			}
    			if(deletePossible == true){
    				
    				dataProvider.getList().remove(selectionModel.getSelectedObject());
    				adminService.getPermission(selectionModel.getSelectedObject().getUserID(), currentNB.getNbID(), currentNote.getnID(), getPermissionForDeleteCallback()); 
    			}else{
    				Window.alert("Die ausgewaehlte Person kann nicht von der Berechtigungsliste geloescht werden.");
	        		savePermissionButton.setEnabled(true);
	        		readButton.setEnabled(true);
	        		editButton.setEnabled(true);
	        		deleteButton.setEnabled(true);
	        		savePermissionButton.setStylePrimaryName("savePermission-button");
    			}
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
		
		newNote.setnID(currentNote.getnID());
		newNote.setnTitle(noteTitleTB.getText());
		newNote.setnSubtitle(noteSubtitleTB.getText());
		newNote.setnContent(textArea.getText());
		newNote.setnCreDate(currentNote.getnCreDate());
		newNote.setnModDate(date);
		
		if(dueDate == null && newDueDate.getdDate() != null){
			newDueDate.setnID(currentNote.getnID());
			adminService.createDuedate(newDueDate, createDuedateCallback());

		}
		if(dueDate != null){

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

    }
    });
    
    deleteNoteButton.addClickHandler(new ClickHandler() {
    	public void onClick(ClickEvent event) {
    		deleteNoteButton.setEnabled(false);
    		Application.notesDataProvider.getList().remove(Application.notesSelectionModel.getSelectedObject());
    		adminService.deleteNote(currentNote, deleteNoteCallback());
   		 Update update = new EditNotebookView();
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
    		duedate.setValue(null);
    		
    	}
    });

}

/**
 * Erstellen aller asynchronen Callbacks
 * @return
 */

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
		 
		 adminService.getNotesOfNotebook(currentNB, getNotesOfNotebookCallback());
		 
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
		 /*
		  * Hier findet das Setzen aller Daten statt, die in der View angezeigt werden sollen.
		  */
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
		 /*
		  * Hier werden die Berechtigungen aus der Datenbank geholt, um sie spaeter anzeigen
		  * zu koennen. Ausserdem wird die Berechtigung des aktuellen Nutzers ausgelesen und die
		  * entsprechenden Buttons freigegeben.
		  */
		 for(int i = 0; i < result.size(); i++) {
			 if(currentUser.getUserID() == result.get(i).getUserID()){
				 if(result.get(i).getIsOwner() == true){
					 ownerLabel.setText("Du bist Eigentuemer dieser Notiz");
				 }
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
 				UserPermission up = new UserPermission();
 				up.setMail(null);
 				up.setUserID(result.get(i).getUserID());
 				up.setPermissionID(result.get(i).getPermissionID());
 				up.setPermissionType(result.get(i).getPermissionType());
 				up.setNID(result.get(i).getNID()); 
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
		 /*
		  * Auslesen und setzen der Usermail und UserID zur Anzeige der Berechtigungen und 
		  * zur spaeteren Verwendung
		  */
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
    		 
    		 index = Application.notesDataProvider.getList().indexOf(currentNote.getnTitle());
    		 Application.notesList.set(index, result.getnTitle());

    		 
             Update update = new EditNoteView();
             
             RootPanel.get("Details").clear();
             RootPanel.get("Details").add(update);
    		 /*
    		  * Hier werden die neu hinzugefuegten Berechtigungen per Callback erstellt und die 
    		  * bereits bestehenden werden editiert, falls sich die Berechtigungsart geaendert hat.
    		  */
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
    				 permission.setNbID(currentNB.getNbID());
    				 permission.setNID(result.getnID());
    				 permission.setPermissionType(dataProvider.getList().get(x).getPermissionType());
    				 permission.setUserID(dataProvider.getList().get(x).getUserID());
    				 newPermissions.add(permission);
    			 }
    		 }
    		 adminService.createPermissions(newPermissions, createPermissionsCallback());
    		 adminService.editPermissions(editPermissions, editPermissionsCallback());
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
       		 Application.notesSelectionModel.setSelected(noteTitleTB.getValue(), true);
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
    			/*
    			 * In diesem Callback wird ueberprueft, ob der eingegeben User fuer eine Berechtigung
    			 * im System existiert.
    			 */
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
        			
        			dataProvider.getList().add(userP);
        			permissionTB.setText("");
	        		savePermissionButton.setEnabled(true);
	        		deletePermissionButton.setEnabled(true); 
	        		readButton.setEnabled(true);
	        		editButton.setEnabled(true);
	        		deleteButton.setEnabled(true);
	        		readButton.setValue(false);
	        		editButton.setValue(false);
	        		deleteButton.setValue(false);
	        		savePermissionButton.setStylePrimaryName("savePermission-button");
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
        		readButton.setEnabled(true);
        		editButton.setEnabled(true);
        		deleteButton.setEnabled(true);
        		savePermissionButton.setStylePrimaryName("savePermission-button");
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
          		 Application.notesSelectionModel.setSelected(noteTitleTB.getValue(), true);
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
    
}

