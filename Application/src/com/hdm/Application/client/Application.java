package com.hdm.Application.client;


import com.hdm.Application.shared.FieldVerifier;
import com.hdm.Application.client.gui.ImpressumView;
import com.hdm.Application.client.gui.CreateNoteView;
import com.hdm.Application.client.gui.CreateNotebookView;
import com.hdm.Application.client.gui.EditNoteView;
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
import java.util.List;

import com.hdm.Application.client.gui.SearchView;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.AbstractCellTree;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.google.gwt.view.client.TreeViewModel.NodeInfo;

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
		private static NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();

		/**
		 * Die Instanz des aktuellen Benutzers ermoeglicht den schnellen Zugriff auf
		 * dessen Profileigenschaften.
		 */
		private AppUser currentUser = null;
		
		/**
		 * Eine ArrayList, in der Notebook-Objekte gespeichert werden
		 */
		private ArrayList<Notebook> notebooks = null;
		
		private ArrayList<Note> notes = null;
		
		TextCell cell = new TextCell();
		
		public final static SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
	    
		 // Create a data provider.
		public static ListDataProvider<String> dataProvider = new ListDataProvider<String>();
		
		public static List<String> list = dataProvider.getList();

//		private static class CustomTreeModel implements TreeViewModel{
//		/**
//	     * Get the {@link NodeInfo} that provides the children of the specified
//	     * value.
//	     */
//	    public <T> NodeInfo<?> getNodeInfo(T value) {
//	      if (value == null) {
//	        // LEVEL 0.
//	        // We passed null as the root value. Return the notebooks.
//
//	        // Create a data provider that contains the list of composers.
//	        ListDataProvider<Notebook> notebookProvider = new ListDataProvider<Notebook>(adminService.getNotebooksOfUser(adminService.getCurrentUser(getCurrentUserCallback()), getNotebooksOfUserCallback());
//
//	        // Create a cell to display a notebook.
//	        Cell<Notebook> cell = new AbstractCell<Notebook>() {
//	          @Override
//	          public void render(Context context, Notebook value, SafeHtmlBuilder sb) {
//	            if (value != null) {
//	              sb.appendEscaped(value.getNbTitle());
//	            }
//	          }
//	        };
//		}
		
		/**
		 * Eine ArrayList, in der Note-Objekte gespeichert werden
		 */
		//private ArrayList<Note> notes = null;
		
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
	  private HorizontalPanel headButtonPanel = new HorizontalPanel();
	  private VerticalPanel navPanel = new VerticalPanel();
	  private VerticalPanel navPanel2 = new VerticalPanel();
	  /**
	   * Erstellung aller Widgets
	   */

//	  private Label loginLabel = new Label("Bitte melde dich mit deinem Google Account an, um Notework nutzen zu können. Klicke auf Login und los geht's!");
//	  final Label headerLabel = new Label("Notework");

	  private Label loginLabel = new Label("");
	  final Label userLabel = new Label();
	  final Label usernameLabel = new Label("Username");
	  final Label passwordLabel = new Label("Password");
	  private Anchor signInLink = new Anchor("Login");
	  public final static ListBox listbox = new ListBox();
	  final Button createNoteButton = new Button("");
	  final Button createNotebookButton = new Button("");
	  final Button signOutButton = new Button("Ausloggen");
	  final Button searchButton = new Button("Suche");
	  final Button logoButton = new Button();
	  final Button impressumButton = new Button("Impressum");
	  final Button hilfeButton = new Button("Hilfe");
	  final CellList<String> cellList = new CellList<String>(cell);
	  
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
     loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
     public void onFailure(Throwable error) {
      }

      public void onSuccess(LoginInfo result) {
      if (Location.getParameter("url") != null) Cookies.setCookie("url", Location.getParameter("url"));
      loginInfo = result; 
      ClientsideSettings.setLoginInfo(result);
      if (loginInfo.isLoggedIn()){                 		
		   		loadGUI();
		   		if(Cookies.getCookie("url") != null) {
		   			Update update = new CreateNoteView();
		   			RootPanel.get("Details").clear();
			   		RootPanel.get("Details").add(update);		   		
	       		}
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

 		adminService.getUserByMail(ClientsideSettings.getLoginInfo().getEmailAddress(),
 				getCurrentUserCallback());
 		
 		Update update = new WelcomeView();
 		
 		listbox.setSelectedIndex(0);
 		
 		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

 	    // Add a selection model to handle user selection.
 	    
 	    cellList.setSelectionModel(selectionModel);
 	    
	 	 // Connect the list to the data provider.
 	    dataProvider.addDataDisplay(cellList);

 	    
	    /**
	     * Zuweisung eines Styles fuer die jeweiligen Widgets
	     **/

	    createNotebookButton.setStyleName("navObject");
 		createNoteButton.setStyleName("navObject2");
 		searchButton.setStyleName("headObject");
 		impressumButton.setStyleName("headObject");
 		hilfeButton.setStyleName("headObject");
	    signOutButton.setStyleName("headObject");
	    logoButton.setStyleName("notework-logo");
	    listbox.setStyleName("navListbox");
	    headPanel.setStyleName("headPanel");
	    navPanel.setStyleName("navPanel");
	    headButtonPanel.setStyleName("headButtonPanel");
	    
	    
	    
	    /**
	     * Zuteilung der Widgets zum jeweiligen Panel
	     */
	    
	    headButtonPanel.add(userLabel);
	    headButtonPanel.add(logoButton);
	    headButtonPanel.add(searchButton);
	    headButtonPanel.add(impressumButton);
	    headButtonPanel.add(hilfeButton);
	    headButtonPanel.add(signOutButton);
	    headPanel.add(headButtonPanel);
	    navPanel.add(listbox);
	    navPanel2.add(cellList);
	    navPanel2.add(createNotebookButton);
	    navPanel2.add(createNoteButton);
	    RootPanel.get("Header").add(headPanel);
	    RootPanel.get("Navigator").add(navPanel);
	    RootPanel.get("Navigator").add(navPanel2);
	    RootPanel.get("Details").add(update);
	    
	    /**
	     * Implementierung der jeweiligen ClickHandler fuer die einzelnen Widgets
	     */
	    
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				
				Update update = new EditNoteView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
	    });
	    
	    logoButton.addClickHandler(new ClickHandler() {
		  	public void onClick(ClickEvent event) {
		          /*
		           * Showcase instantiieren.
		           */
		          Update update = new WelcomeView();
		          RootPanel.get("Details").clear();
		          RootPanel.get("Details").add(update);
		    }
		    });
	    
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

	    impressumButton.addClickHandler(new ClickHandler() {
		  	public void onClick(ClickEvent event) {
		          /*
		           * Showcase instantiieren.
		           */
		          Update update = new ImpressumView();
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
	    		
//	    		Update update = new EditNotebookView();
//	    		
//	    		RootPanel.get("Details").clear();
//	    		RootPanel.get("Details").add(update);
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
     RootPanel.get("Header").add(headPanel);
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
		 
		 userLabel.setText(currentUser.getUserName());
		 
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
			 adminService.getNotesOfNotebook(listbox.getSelectedValue(), currentUser, getNotesOfNotebookCallback());
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
		 
		 notes = result;
		 dataProvider.getList().clear();
		 for(int i = 0; i < notes.size(); i++) {
			 dataProvider.getList().add(notes.get(i).getnTitle());
		 }
	 }
	 };
	 return asyncCallback;
 }
 }

