package com.hdm.Application.client;


import com.hdm.Application.shared.FieldVerifier;


import com.hdm.Application.client.gui.CreateNoteView;
import com.hdm.Application.client.gui.LoginService;
import com.hdm.Application.client.gui.LoginServiceAsync;
import com.hdm.Application.client.gui.NoteOverviewView;
import com.hdm.Application.client.gui.Update;
import com.hdm.Application.shared.LoginInfo;
import com.hdm.Application.client.ClientsideSettings;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
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
	   * The message displayed to the user when the server cannot be reached or
	   * returns an error.
	   */
//	  private static final String SERVER_ERROR = "An error occurred while "
//			   + "attempting to contact the server. Please check your network " + "connection and try again.";
	  
	  /**
	   * Erstellung aller Panels
	   */
	  private VerticalPanel loginPanel = new VerticalPanel();
	  public final FlowPanel detailPanel = new FlowPanel();
	  private HorizontalPanel headPanel = new HorizontalPanel();
	  private VerticalPanel navPanel = new VerticalPanel();
	  
	  /**
	   * Erstellung aller Widgets
	   */
	  private Label loginLabel = new Label("Bitte melde dich mit deinem Google Account an, um Notework nutzen zu k�nnen. Klicke auf Login und los geht's!");
	  private Anchor signInLink = new Anchor("Login");
	  final Button createNoteButton = new Button("+");
	  final Button noteButton = new Button("My Recipes");
	  final Label headerLabel = new Label("Notework");
	  final Button signOutButton = new Button("Sign out");

 /**
  * Create a remote service proxy to talk to the server-side Greeting service.
  */
// private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

/**
 * Das ist die EntryPointMethode 
 */
 public void onModuleLoad() {
	  
    
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
	     * Zuweisung eines Styles fuer die jeweiligen Widgets
	     **/
	    headerLabel.setStyleName("notework-headline");
	    createNoteButton.setStyleName("notework-menubutton");
	    noteButton.setStyleName("notework-menubutton");
	    signOutButton.setStyleName("notework-menubutton");
	    
	    
	    /**
	     * Zuteilung der Widgets zum jeweiligen Panel
	     */
	    headPanel.add(headerLabel);
	    navPanel.add(createNoteButton);
	    navPanel.add(noteButton);
	    RootPanel.get("Header").add(headPanel);
	    RootPanel.get("Navigator").add(navPanel);
	    
	    /**
	     * Implementierung der jeweiligen ClickHandler fuer die einzelnen Widgets
	     */
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

	    noteButton.addClickHandler(new ClickHandler() {
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
 
 private void loadLogin() {
     
     Cookies.setCookie("usermail", null);
     loginPanel.setStyleName("login");
     signInLink.setHref(loginInfo.getLoginUrl());
     
     signInLink.setStyleName("loginLink");
     loginLabel.setStyleName("loginLink2");
     
     
     loginPanel.add(loginLabel);
     loginPanel.add(signInLink);
     
     Cookies.setCookie("userMail", null);
     Cookies.setCookie("userID", null);
     RootPanel.get("Details").clear();
     RootPanel.get("Details").add(loginPanel);     
    }
  }

