package com.hdm.Application.client.gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	
	private Note n = new Note();
	
	private Notebook currentNB = new Notebook();
	
	private ArrayList<Permission> permissions = new ArrayList<Permission>();
	
	private AppUser user = new AppUser();
	
	protected String getHeadlineText() {
	    return "Create Note";
}
	
	/**
	   * Erstellung aller Panels
	   */

  VerticalPanel createPanel = new VerticalPanel();
  HorizontalPanel buttonPanel = new HorizontalPanel();

  
  /**
   * Erstellung aller Widgets
   */
    
   Label headlineLabel = new Label("Headline");
   Label noticeLabel = new Label("Notice");
   TextBox noteTitle = new TextBox();
   TextBox noteSubtitle = new TextBox();
   TextBox permissionText = new TextBox();
   TextArea textArea = new TextArea();
   final Button createButton = new Button("Create");
   final Button cancelButton = new Button("Cancel");
   final Button savePermissionButton = new Button("Save");
   //final Button editButton = new Button("Edit");
   //final Button deleteButton = new Button("Delete");
   final RadioButton readButton = new RadioButton("Leseberechtigung");
   final RadioButton editButton = new RadioButton("Bearbeitungsberechtigung");
   
	
//	private Note currentNote = null;

protected void run() {
    this.append("");
    
    adminService = ClientsideSettings.getAdministration();
    
 
	/**
     * Zuteilung der Widgets zum jeweiligen Panel
     */
    
    createPanel.add(headlineLabel);
    createPanel.add(noteTitle);
    createPanel.add(noteSubtitle);
    createPanel.add(noticeLabel);
    createPanel.add(textArea); 
    createPanel.add(permissionText);
    createPanel.add(readButton);
    createPanel.add(editButton);
    createPanel.add(savePermissionButton);
    buttonPanel.add(createButton);
    buttonPanel.add(cancelButton);
    //buttonPanel.add(editButton);
    //buttonPanel.add(deleteButton);
    createPanel.add(buttonPanel);
    RootPanel.get("Details").add(createPanel);
        
    textArea.setVisibleLines(20);
    textArea.setPixelSize(420, 350);
    
    noteTitle.setText("Title");
    readButton.setText("Leseberechtigung");
    editButton.setText("Bearbeitungsberechtigung");
    
    /**
     * Zuweisung eines Styles fuer die jeweiligen Widgets
     **/
    
    createButton.setStyleName("notework-menubutton");
    cancelButton.setStyleName("notework-menubutton");
    //editButton.setStyleName("notework-menubutton");
    //deleteButton.setStyleName("notework-menubutton");
  	
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
    		savePermissionButton.setStylePrimaryName("");
    		
    		
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
    			
    			adminService.createPermission(permission, createPermissionCallback());
    			
    			permissions.add(permission);
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
		//createButton.setStylePrimaryName("");
		
		Note note = new Note();
		note.setnTitle(noteTitle.getText());
		note.setnContent(textArea.getText());
		//Date date = new Date();
		//note.setnCreDate(date);
		adminService.createNote(note, createNoteCallback());

          /*
           * Showcase instantiieren.
           */
          Update update = new NoteOverviewView();
          
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



