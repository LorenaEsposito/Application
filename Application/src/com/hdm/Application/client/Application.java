package com.hdm.Application.client;

import com.hdm.Application.client.gui.CreateNoteView;
import com.hdm.Application.client.gui.LoginService;
import com.hdm.Application.client.gui.LoginServiceAsync;
import com.hdm.Application.client.gui.NoteOverviewView;
import com.hdm.Application.client.gui.Update;
import com.hdm.Application.shared.LoginInfo;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {
 /**
  * The message displayed to the user when the server cannot be reached or
  * returns an error.
  */
 
// LoginInfo loginInfo = new LoginInfo();
//  private VerticalPanel loginPanel = new VerticalPanel();
//  private Label loginLabel = new Label("Bitte melde dich mit deinem Google Account an, um Notework nutzen zu k�nnen. Klicke auf Login und los geht's!");
//  private Anchor signInLink = new Anchor("Login");
//  public Anchor signOutLink = new Anchor("Logout");
//  public final FlowPanel detailPanel = new FlowPanel();
//  final VerticalPanel navigationPanel = new VerticalPanel();
//  LoginServiceAsync loginService = GWT.create(LoginService.class);
// private static final String SERVER_ERROR = "An error occurred while "
//   + "attempting to contact the server. Please check your network " + "connection and try again.";

 /**
  * Create a remote service proxy to talk to the server-side Greeting service.
  */
// private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

 
 public void onModuleLoad() {
    
//   navigationPanel.setVisible(false); 
//    
//      // Check login status using login service.
//     LoginServiceAsync loginService = GWT.create(LoginService.class);
//     loginService.login("http://127.0.0.1:8888/Application.html", new AsyncCallback<LoginInfo>() {
//     public void onFailure(Throwable error) {
//
//      // DialogBox d = new DialogBox();
//      Window.alert("Fehler: Harter Fehler");
//       // d.show();
//      }
//
//      public void onSuccess(LoginInfo result) {
//        loginInfo = result;  
//      if (loginInfo.isLoggedIn()){ 
//       Window.alert("Erfolgreich eingeloggt");
//       
//        }
//     
//        else{ 
//        	loadLogin();
//            Window.alert("Einloggen fehlgeschlagen");
//           }
//           }
//           });
//          }
// 
// private void loadLogin() {
//     
//     Cookies.setCookie("usermail", null);
//     loginPanel.setStyleName("login");
//     signInLink.setHref(loginInfo.getLoginUrl());
//     
//     signInLink.setStyleName("loginLink");
//     loginLabel.setStyleName("loginLink2");
//     
//     
//     loginPanel.add(loginLabel);
//     loginPanel.add(signInLink);
//     
//     Cookies.setCookie("userMail", null);
//     Cookies.setCookie("userID", null);
//     RootPanel.get("Starter").add(loginPanel);     
//    }
// 
// public void loadGUI() {
	 
	 HorizontalPanel headPanel = new HorizontalPanel();
		VerticalPanel navPanel = new VerticalPanel();
		
	    /*
	     * Das VerticalPanel wird einem DIV-Element namens "Navigator" in der
	     * zugehörigen HTML-Datei zugewiesen und erhält so seinen Darstellungsort.
	     */
	    
	    /*
	     * Erstellung des ersten Navibar-Buttons zum hinzufügen neuer Notizen
	     */
	    
	    final Button createNoteButton = new Button("+");
	    
	    final Label headerLabel = new Label("Notework");
	    
	    headerLabel.setStyleName("notework-headline");
	    createNoteButton.setStyleName("notework-menubutton");
	    headPanel.add(headerLabel);
	    navPanel.add(createNoteButton);
	    RootPanel.get("Header").add(headPanel);
	    RootPanel.get("Navigator").add(navPanel);
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
	    final Button notebookButton = new Button("My Recipes");
	    
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

