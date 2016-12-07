package com.hdm.Application.client;


import com.hdm.Application.shared.FieldVerifier;


import com.hdm.Application.client.gui.CreateNoteView;
import com.hdm.Application.client.gui.CreateNotebookView;
import com.hdm.Application.client.gui.LoginService;
import com.hdm.Application.client.gui.LoginServiceAsync;
import com.hdm.Application.client.gui.Update;
import com.hdm.Application.client.gui.WelcomeView;
import com.hdm.Application.shared.LoginInfo;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.client.ClientsideSettings;

import java.util.ArrayList;

import com.hdm.Application.client.gui.SearchView;
import com.hdm.Application.client.gui.ShowNoteView;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {
	
	/**
	 * Der LoginService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	  LoginServiceAsync loginService = GWT.create(LoginService.class);
	  
	  /**
		 * Die Instanz von LoginInfo dient als Hilfsklasse fuer das Login und stellt
		 * erforderliche Variablen und Operationen bereit.
		 */
	  private LoginInfo loginInfo = null;
	  
	  /**
		 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
		 * Applikationslogik.
		 */
		private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();

		/**
		 * Die Instanz des aktuellen Benutzers ermoeglicht den schnellen Zugriff auf
		 * dessen Profileigenschaften.
		 */
		private AppUser currentUser = null;
		
		/**
		 * Eine ArrayList, in der Notebook-Objekte gespeichert werden
		 */
		private ArrayList<Notebook> notebooks = null;

		/**
		 * Eine ArrayList, in der Note-Objekte gespeichert werden
		 */
		private ArrayList<Note> notes = null;
		
	  /**
	   * The message displayed to the user when the server cannot be reached or
	   * returns an error.
	   */
//	  private static final String SERVER_ERROR = "An error occurred while "
//			   + "attempting to contact the server. Please check your network " + "connection and try again.";
	  
	  /**
	   * Erstellung aller Panels
	   */
	  private VerticalPanel loginPanel = new VerticalPanel();
	  private VerticalPanel loginTextPanel = new VerticalPanel();
	  public final FlowPanel detailPanel = new FlowPanel();
	  private HorizontalPanel headPanel = new HorizontalPanel();
	  private VerticalPanel navPanel = new VerticalPanel();
	  
	  /**
	   * Erstellung aller Widgets
	   */

//	  private Label loginLabel = new Label("Bitte melde dich mit deinem Google Account an, um Notework nutzen zu können. Klicke auf Login und los geht's!");
//	  final Label headerLabel = new Label("Notework");

	  private Label loginLabel = new Label("You need an GMail-Account for using Notework");
	  final Label userLabel = new Label();
	  final Label usernameLabel = new Label("Username");
	  final Label passwordLabel = new Label("Password");
	  private Anchor signInLink = new Anchor("Login");
//	  public final static ListBox listbox = new ListBox();
//	  final Button createNoteButton = new Button("");
//	  final Button noteButton = new Button("My Recipes");
//	  final Button signOutButton = new Button("Sign out");
	  public final static ListBox listbox = new ListBox();
	  final Button createNoteButton = new Button("New Note +");
	  final Button createNotebookButton = new Button("New Notebook");
	  final Button signOutButton = new Button("");
	  final Button searchButton = new Button("Search");
	  final Button logoButton = new Button();
	  final TextBox usernameBox = new TextBox();
	  final TextBox passwordBox = new TextBox();
	  
	  
 /**
  * Create a remote service proxy to talk to the server-side Greeting service.
  */
// private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

/**
 * Das ist die EntryPointMethode 
 */
 public void onModuleLoad() {
	  
	 
	 /**
	  * Setzen des headerLabel 
	  */
    
      // Check login status using login service.
     LoginServiceAsync loginService = GWT.create(LoginService.class);
     loginService.login("http://127.0.0.1:8888/Application.html", new AsyncCallback<LoginInfo>() {
     public void onFailure(Throwable error) {
      }

      public void onSuccess(LoginInfo result) {
    	  
        loginInfo = result; 
        ClientsideSettings.setLoginInfo(result);
        
      if (loginInfo.isLoggedIn()){ 
       loadGUI();
        }
     
        else{ 
        	loadLogin();
           }
         }
       });
 }

     public void loadGUI() {
    	 
    	 /**
 		 * Auslesen des Profils vom aktuellen Benutzer aus der Datenbank.
 		 */
 		int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
 		adminService.getUserByGoogleID(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex),
 				getCurrentUserCallback());
 		
 		Update update = new WelcomeView();

 	    
	    /**
	     * Zuweisung eines Styles fuer die jeweiligen Widgets
	     **/

	    createNotebookButton.setStyleName("navObject");
 		createNoteButton.setStyleName("navObject");
	    signOutButton.setStyleName("signout-button");
	    searchButton.setStyleName("navObject");
	    logoButton.setStyleName("notework-logo");
	    listbox.setStyleName("listbox");
	    
	    
	    
	    /**
	     * Zuteilung der Widgets zum jeweiligen Panel
	     */
	    
	    headPanel.add(userLabel);
	    headPanel.add(logoButton);
	    headPanel.add(signOutButton);
	    navPanel.add(listbox);
	    navPanel.add(createNoteButton);
	    navPanel.add(searchButton);
	    RootPanel.get("Header").add(headPanel);
	    RootPanel.get("Navigator").add(navPanel);
	    RootPanel.get("Details").add(update);
	    
	    /**
	     * Implementierung der jeweiligen ClickHandler fuer die einzelnen Widgets
	     */
	    
	    createNotebookButton.addClickHandler(new ClickHandler() {
	  	public void onClick(ClickEvent event) {
	          /*
	           * Showcase instantiieren.
	           */
	          Update update = new CreateNotebookView();
	          RootPanel.get("Details").clear();
	          RootPanel.get("Details").add(update);
	    }
	    });
	    
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


//	    noteButton.addClickHandler(new ClickHandler() {
//	  	public void onClick(ClickEvent event) {
//	          /*
//	           * Showcase instantiieren.
//	           */
//	          Update update = new ShowNoteView();
//	          
//	          RootPanel.get("Details").clear();
//	          RootPanel.get("Details").add(update);
//	    }
//	    });

	    
	    signOutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				/*
				 * Laden der Logout-URL der Google Accounts API und Anzeige des
				 * LoginPanel.
				 */
				RootPanel.get("Details").setWidth("65%");
				Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(), "_self", "");

			}
		});
	    
	    searchButton.addClickHandler(new ClickHandler() {
	  	public void onClick(ClickEvent event) {
	          /*
	           * Showcase instantiieren.
	           */
	          Update update = new SearchView();
	          
	          RootPanel.get("Details").clear();
	          RootPanel.get("Details").add(update);
	    }
	    });

	   
	    listbox.addChangeHandler(new ChangeHandler() {
	    	public void onChange(ChangeEvent event) {
	    		adminService.getNotesOfNotebook(listbox.getSelectedItemText(), currentUser, getNotesOfNotebookCallback());
	    	}
	    });
	}
 
 private void loadLogin() {
     
     Cookies.setCookie("usermail", null);
     signInLink.setHref(loginInfo.getLoginUrl());
     signInLink.setStyleName("loginLink");
     loginLabel.setStyleName("loginLink2");
     loginPanel.setStyleName("login");
     logoButton.setStyleName("notework-logo");
     
     
     
     headPanel.add(logoButton);
     loginTextPanel.add(loginLabel);
     loginTextPanel.add(signInLink);
     loginTextPanel.setStyleName("loginTextPanel");
     
//     loginTextPanel.add(usernameLabel);
//     loginTextPanel.add(usernameBox);
//     loginTextPanel.add(passwordLabel);
//     loginTextPanel.add(passwordBox);
     loginPanel.add(loginTextPanel);
     
     
     Cookies.setCookie("userMail", null);
     Cookies.setCookie("userID", null);
     RootPanel.get("Details").clear();
     RootPanel.get("Details").add(loginPanel);     
    }
 
 private AsyncCallback<AppUser> getCurrentUserCallback() {
	 AsyncCallback<AppUser> asyncCallback = new AsyncCallback<AppUser>() {
	 
	 @Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}
	 
	 @Override
	 public void onSuccess(AppUser result){
		 ClientsideSettings.getLogger()
			.severe("Success GetCurrentUserCallback: " + result.getClass().getSimpleName());
		 currentUser = result;
		 
		 userLabel.setText(currentUser.getGoogleID());
		 
		 adminService.getNotebooksOfUser(currentUser, getNotebooksOfUserCallback());
		 
		 
	 }
	 };
	 return asyncCallback;
 }
 
 private AsyncCallback<ArrayList<Notebook>> getNotebooksOfUserCallback() {
	 AsyncCallback<ArrayList<Notebook>> asyncCallback = new AsyncCallback<ArrayList<Notebook>>() {
		 
		 @Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}
		 
		 @Override
		 public void onSuccess(ArrayList<Notebook> result){
			 ClientsideSettings.getLogger()
				.severe("Success GetNotebooksOfUserCallback: " + result.getClass().getSimpleName());
			 notebooks = result;
			 
			 for (int x = 0; x < notebooks.size(); x++ ){
				 listbox.addItem(notebooks.get(x).getNbTitle());
				 
				 listbox.setVisibleItemCount(1);
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
		 for(int i = 0; i < result.size(); i++){
			 final Button nButton = new Button(result.get(i).getnTitle());
			 nButton.addStyleName("notework-menubutton");
			 navPanel.add(nButton);
		 }
	 }
	 };
	 return asyncCallback;
 }
 }

