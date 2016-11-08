package com.hdm.Application.client;

import com.hdm.Application.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		VerticalPanel navPanel = new VerticalPanel();

	    /*
	     * Das VerticalPanel wird einem DIV-Element namens "Navigator" in der
	     * zugehörigen HTML-Datei zugewiesen und erhält so seinen Darstellungsort.
	     */
	    RootPanel.get("Navigator").add(navPanel);
	    
	    /*
	     * Erstellung des ersten Navibar-Buttons zum hinzufügen neuer Notizen
	     */
	    
	    final Button createNoteButton = new Button("+");
	    
	    createNoteButton.setStyleName("notework-menubutton");
	    
	    navPanel.add(createNoteButton);
	    
	    createNoteButton.addClickHandler(new ClickHandler() {
	  	public void onClick(ClickEvent event) {
	          /*
	           * Showcase instantiieren.
	           */
	          Update update = new CreateNoteView();
	          
	          RootPanel.get("Details").clear();
	          RootPanel.get("Details").add(update);
	    }
	    });
final Button notebookButton = new Button("+");
	    
	    notebookButton.setStyleName("notework-menubutton");
	    
	    navPanel.add(notebookButton);
	    
	    notebookButton.addClickHandler(new ClickHandler() {
	  	public void onClick(ClickEvent event) {
	          /*
	           * Showcase instantiieren.
	           */
	          Update update = new NoteOverviewView();
	          
	          RootPanel.get("Details").clear();
	          RootPanel.get("Details").add(update);
	    }
	    });
	    
	}
}
