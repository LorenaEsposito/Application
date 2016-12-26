package com.hdm.Application.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hdm.Application.client.Application;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.Note;

public class ShowNoteView extends Update{

	protected String getHeadlineText() {
	    return "";
}
	
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	
	public static Note note = new Note();
	
	private AppUser user = new AppUser();
	
	private String noteTitle = new String();
	
	private String notebookTitle = new String();

	/**
	   * Erstellung aller Panels
	   */

	VerticalPanel showPanel = new VerticalPanel();
	HorizontalPanel titlePanel = new HorizontalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();
	PopupPanel infoPanel = new PopupPanel();

	/**
	 * Erstellung aller Widgets
	 */
	Label testLabel = new Label();
	Label titleLabel = new Label("Title");
	Label infoLabel = new Label("Info");
	Label subtitleLabel = new Label("Subtitle");
	Label noticeLabel = new Label("Notice");
//	TextBox noteTitle = new TextBox();
//	TextBox noteSubtitle = new TextBox();
//	TextArea textArea = new TextArea();
	//final Button createButton = new Button("Create");
	final Button editButton = new Button("Edit");
	final Button deleteButton = new Button("Delete");
	
protected void run() {

    // Ankündigung, was nun geschehen wird.

    this.append("");
    
    noteTitle = Application.selectionModel.getSelectedObject();
    
    notebookTitle = Application.listbox.getSelectedValue();
   
	/**
   * Zuteilung der Widgets zum jeweiligen Panel
   */
  infoPanel.add(testLabel);
  titlePanel.add(titleLabel);
  titlePanel.add(infoLabel);
  showPanel.add(titlePanel);
  showPanel.add(subtitleLabel);
  showPanel.add(noticeLabel);
  showPanel.add(buttonPanel);
  buttonPanel.add(editButton);
  buttonPanel.add(deleteButton);
  RootPanel.get("Details").add(showPanel);
      
  
  /**
   * Zuweisung eines Styles fuer die jeweiligen Widgets
   **/
  	deleteButton.setStyleName("savePermission-button");
    editButton.setStyleName("savePermission-button");
    
	 adminService.getCurrentUser(getCurrentUserCallback());
	
  /**
   * Erstellung der Clickhandler
   **/
  
  editButton.addClickHandler(new ClickHandler() {
	public void onClick(ClickEvent event) {
        /*
         * Showcase instantiieren.
         */
        Update update = new EditNoteView();
        
        RootPanel.get("Details").clear();
        RootPanel.get("Details").add(update);
        
  }
  });
  
  deleteButton.addClickHandler(new ClickHandler() {
	public void onClick(ClickEvent event) {
        /*
         * Showcase instantiieren.
         */
        Update update = new WelcomeView();
        
        RootPanel.get("Details").clear();
        RootPanel.get("Details").add(update);
  }
  });
  
  infoLabel.addMouseOverHandler(new MouseOverHandler() {
	  public void onMouseOver(MouseOverEvent event) {
		  testLabel.setText("Das ist ein Test");
		  infoPanel.showRelativeTo(infoLabel);
	  }
  });
  
  infoLabel.addMouseOutHandler(new MouseOutHandler() {
	public void onMouseOut(MouseOutEvent event) {
		infoPanel.hide();
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
		 
		 adminService.getNotesOfNotebook(notebookTitle, user, getNotesOfNotebookCallback());
		 
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
		 for(int i = 0; i < result.size(); i++){
			 if(noteTitle == result.get(i).getnTitle()) {
				 note = result.get(i);
				 titleLabel.setText(note.getnTitle());
				 subtitleLabel.setText(note.getnSubtitle());
				 noticeLabel.setText(note.getnContent());
			 }
		 }
	 }
	};
	return asyncCallback;
}

}



