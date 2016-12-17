package com.hdm.Application.client.gui;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import com.google.gwt.user.datepicker.client.DateBox;
import com.hdm.Application.client.ClientsideSettings;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.DueDate;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;

public class SearchView extends Update {

	protected String getHeadlineText() {

		return "Search";
	}

	/**
	 * Erstellung aller Panels
	 */
	
	private NoteAdministrationAsync adminService = ClientsideSettings
			.getAdministration();

	
	VerticalPanel searchPanel = new VerticalPanel();

	/**
	 * Erstellung aller Widgets
	 */

	TextBox searchBox = new TextBox();
	DateBox searchDateBox = new DateBox(); 
	final Button searchButton = new Button("Search");
	final RadioButton dueDateButton = new RadioButton("Radiobutton-Group","Duedate");
	final RadioButton userNameButton = new RadioButton("Radiobutton-Group","Username");
	final RadioButton noteButton = new RadioButton("Radiobutton-Group","Note");
	final RadioButton notebookButton = new RadioButton("Radiobutton-Group","Notebook");

	protected void run() {

		// Ank�ndigung, was nun geschehen wird.

		this.append("");

		/**
		 * Zuteilung der Widgets zum jeweiligen Panel
		 */

		searchPanel.add(searchButton);
		searchPanel.add(searchBox);
		searchPanel.add(searchDateBox);
		searchPanel.add(dueDateButton);
		searchPanel.add(userNameButton);
		searchPanel.add(noteButton);
		searchPanel.add(notebookButton);
		RootPanel.get("Details").add(searchPanel);

		/**
		 * Zuweisung eines Styles fuer die jeweiligen Widgets
		 **/
		searchDateBox.setVisible(false);		
		searchButton.setStyleName("notework-menubutton");
		dueDateButton.setText("Duedate");
		userNameButton.setText("Username");
		noteButton.setText("Note");
		notebookButton.setText("Notebook");

		/**
		 * Erstellung der ClickHandler
		 **/
		dueDateButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				searchBox.setVisible(false);
				searchDateBox.setVisible(true);
				
			Window.alert("Changehandler ausgef�hrt");	
			}
			});
		
			userNameButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				
				
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
				searchBox.setVisible(true);
				searchDateBox.setVisible(false);
					
				Window.alert("Changehandler ausgef�hrt");
				
				}
			});
				
				noteButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
					
					
					@Override
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						searchBox.setVisible(true);
						searchDateBox.setVisible(false);
						
					Window.alert("Changehandler ausgef�hrt");
					
					}
				});
					notebookButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
						
						
						@Override
						public void onValueChange(ValueChangeEvent<Boolean> event) {
							searchBox.setVisible(true);
							searchDateBox.setVisible(false);
							
						Window.alert("Changehandler ausgef�hrt");
			
				
			}
			
		});
		
		searchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				/*
				 * Showcase instantiieren.
				*/
				
				boolean dd = dueDateButton.getValue();
				boolean un = userNameButton.getValue();
				boolean n = noteButton.getValue();
				boolean nb = notebookButton.getValue();
				if(dd){					
				   adminService.searchForNoteByDD(searchDateBox.getValue(), new AsyncCallback<ArrayList<Note>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Suche nach DueDate hat nicht funktioniert.");
						}
					public void onSuccess(ArrayList<Note> result) {
						Window.alert("Ergebnis: "+result.size());
						}
				   });
				}
				if(un){					
					   adminService.searchForUser(searchBox.getValue(), new AsyncCallback<ArrayList<AppUser>>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Suche nach User hat nicht funktioniert.");
							}
						public void onSuccess(ArrayList<AppUser> result) {
							Window.alert("Ergebnis: "+result.size());
							}
					   });
					}
				if(n){					
					   adminService.searchForNote(searchBox.getValue(), new AsyncCallback<ArrayList<Note>>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Suche nach Note hat nicht funktioniert.");
							}
						public void onSuccess(ArrayList<Note> result) {
							Window.alert("Ergebnis: "+result.size());
							}
					   });
					}
				if(nb){					
					   adminService.searchForNotebook(searchBox.getValue(), new AsyncCallback<ArrayList<Notebook>>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Suche nach Note hat nicht funktioniert.");
							}
						public void onSuccess(ArrayList<Notebook> result) {
							Window.alert("Ergebnis: "+result.size());
							}
					   });
					}
				

				
				
				Update update = new ResultView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
	}
}