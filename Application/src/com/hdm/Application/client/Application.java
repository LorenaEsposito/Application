package com.hdm.Application.client;


import com.hdm.Application.client.gui.ImpressumView;
import com.hdm.Application.client.gui.CreateNoteView;
import com.hdm.Application.client.gui.CreateNotebookView;
import com.hdm.Application.client.gui.DeveloperView;
import com.hdm.Application.client.gui.EditNoteView;
import com.hdm.Application.client.gui.EditNotebookView;
import com.hdm.Application.client.gui.EditProfileView;
import com.hdm.Application.client.gui.LoginService;
import com.hdm.Application.client.gui.LoginServiceAsync;
import com.hdm.Application.client.gui.NotebookCell;
//import com.hdm.Application.client.gui.NotebookNotesTreeViewModel;
import com.hdm.Application.client.gui.Update;
import com.hdm.Application.client.gui.WelcomeView;
import com.hdm.Application.shared.LoginInfo;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.bo.Notebook;
import com.hdm.Application.shared.bo.Permission;
import com.hdm.Application.client.ClientsideSettings;

import java.util.ArrayList;
import java.util.List;

import com.hdm.Application.client.gui.SearchView;
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
import com.google.gwt.user.cellview.client.CellTree;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.google.gwt.view.client.TreeViewModel.NodeInfo;
import com.google.gwt.core.client.GWT;

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
		
		TextCell noteCell = new TextCell();
		
		NotebookCell nbCell = new NotebookCell();
	    static ProvidesKey<Notebook> keyProvider = new ProvidesKey<Notebook>() {
		      public Object getKey(Notebook item) {
		        // Always do a null check.
		        return (item == null) ? null : item.getNbID();

			}
		    };
		
		public final static SingleSelectionModel<String> notesSelectionModel = new SingleSelectionModel<String>();
	    
		public final static SingleSelectionModel<Notebook> nbSelectionModel = new SingleSelectionModel<Notebook>(keyProvider);
		
		// Create a data provider.
		public static ListDataProvider<String> notesDataProvider = new ListDataProvider<String>();
		
		public static List<String> notesList = notesDataProvider.getList();		
		
		
		// Create a data provider.
		public static ListDataProvider<Notebook> nbDataProvider = new ListDataProvider<Notebook>();
				
		public static List<Notebook> nbList = nbDataProvider.getList();	
		
	    /*
	     * Define a key provider for a Contact. We use the unique ID as the key,
	     * which allows to maintain selection even if the name changes.
	     */
	
		
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
	  private VerticalPanel navPanel3 = new VerticalPanel();
	  private VerticalPanel navPanel4 = new VerticalPanel();
	  /**
	   * Erstellung aller Widgets
	   */

//	  private Label loginLabel = new Label("Bitte melde dich mit deinem Google Account an, um Notework nutzen zu kÃ¶nnen. Klicke auf Login und los geht's!");
//	  final Label headerLabel = new Label("Notework");

	  private Label loginLabel = new Label("Bitte loggen Sie sich mit Ihrem Google-Account ein:");
	  public static Label userLabel = new Label();
	  final Label usernameLabel = new Label("Username");
	  final Label passwordLabel = new Label("Password");
	  private Anchor signInLink = new Anchor("Login");
	  public final static Button createNoteButton = new Button("Notiz erstellen");
	  final Button createNotebookButton = new Button("Notizbuch erstellen");
	  final Button signOutButton = new Button("Ausloggen");
	  final Button searchButton = new Button("Suche");
	  final Button impressumButton = new Button("Impressum");
	  final Button profileButton = new Button("Profil bearbeiten");
	  final Button developerButton = new Button("Entwickler");
	  final CellList<String> noteCellList = new CellList<String>(noteCell);
	  final CellList<Notebook> nbCellList = new CellList<Notebook>(nbCell, keyProvider);
	  final Label notebookLabel = new Label();
	  final Label notebookLabel2 = new Label();
	  final Label noteLabel = new Label();
	  final Label noteLabel2 = new Label();
	  
	  
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
    	  adminService.getUserByMail(loginInfo.getEmailAddress(), new AsyncCallback<AppUser>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Fehler: Nutzer konnte in der Datenbank nicht gefunden werden. Details: "+caught);
					
				}

				@Override
				public void onSuccess(AppUser currentUser) {
					if (currentUser != null) {
						Cookies.setCookie("userid", String.valueOf(currentUser.getUserID()));
						loadGUI();
						if(Cookies.getCookie("url") != null) {
				   			Update update = new CreateNoteView();
				   			RootPanel.get("Details").clear();
					   		RootPanel.get("Details").add(update);		   		
						}							
					}else{
						Cookies.removeCookie("userid");
						loginInfo.setLoggedIn(false);
						
					}
					
				}
	   			
	   			
	   		
	   		});
	   				   		
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
 		
 		noteCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

 	    // Add a selection model to handle user selection.
 	    
 	    noteCellList.setSelectionModel(notesSelectionModel);
 	    
	 	 // Connect the list to the data provider.
 	    notesDataProvider.addDataDisplay(noteCellList);
 	    
 	    
 		nbCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

 	    // Add a selection model to handle user selection.
 	    
 	    nbCellList.setSelectionModel(nbSelectionModel);
 	    
	 	 // Connect the list to the data provider.
 	    nbDataProvider.addDataDisplay(nbCellList);
 	    	    
	    /**
	     * Zuweisung eines Styles fuer die jeweiligen Widgets
	     **/

	    createNotebookButton.setStyleName("navObject");
 		createNoteButton.setStyleName("navObject2");
 		searchButton.setStyleName("headObject");
 		impressumButton.setStyleName("headObject");
 		profileButton.setStyleName("headObject");
 		developerButton.setStyleName("headObject"); 
	    signOutButton.setStyleName("headObjectSignout");
	    headPanel.setStyleName("headPanel");
	    navPanel.setStyleName("navPanel");
	    navPanel2.setStyleName("navPanel2");
	    navPanel3.setStyleName("navPanel3");
	    navPanel4.setStyleName("navPanel4");
	    headButtonPanel.setStyleName("headButtonPanel");
	    notebookLabel.setStyleName("navLabel");
	    noteLabel.setStyleName("navLabel");
	    noteLabel2.setStyleName("navLabel");
	    userLabel.setStyleName("usernameLabel");
	    createNoteButton.setStyleName("createNoteButton");
	    createNotebookButton.setStyleName("createNoteButton");
	    
//	    cellTree.setAnimationEnabled(true);
	    
	    
	    
	    /**
	     * Zuteilung der Widgets zum jeweiligen Panel
	     */
	    
	    headButtonPanel.add(userLabel);
	    headButtonPanel.add(searchButton);
	    headButtonPanel.add(impressumButton);
	    headButtonPanel.add(profileButton);
	    headButtonPanel.add(developerButton); 
	    headButtonPanel.add(signOutButton);
	    headPanel.add(headButtonPanel);
	    navPanel.add(notebookLabel);
	    navPanel.add(nbCellList);
	    navPanel2.add(noteLabel);
	    navPanel2.add(noteCellList);
	    navPanel3.add(createNotebookButton);
	    navPanel3.add(createNoteButton);
	    navPanel4.add(noteLabel2);
//	    navPanel2.add(cellTree);
	    RootPanel.get("Header").add(headPanel);
	    RootPanel.get("Navigator").add(navPanel3);
	    RootPanel.get("Navigator").add(navPanel);
	    RootPanel.get("Navigator").add(navPanel4);
	    RootPanel.get("Navigator").add(navPanel2);
	   
	    
	    createNoteButton.setEnabled(false);
	    
	    notebookLabel.setText("Eigene Notizbuecher");
	    noteLabel.setText("Waehle eine Notiz");
	    noteLabel2.setText("Notizbuecher mit Berechtigung");
	    
	    /**
	     * Implementierung der jeweiligen ClickHandler fuer die einzelnen Widgets
	     */
	    
	    notesSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				
				Update update = new EditNoteView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
	    });
	    
	    nbSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				notesSelectionModel.setSelected(notesSelectionModel.getSelectedObject(), false);
				adminService.getNotesOfNotebook(nbSelectionModel.getSelectedObject(), getNotesOfNotebookCallback());
				adminService.getPermission(currentUser.getUserID(), nbSelectionModel.getSelectedObject().getNbID(), 0, getPermissionCallback());

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


	    developerButton.addClickHandler(new ClickHandler() {
	  	public void onClick(ClickEvent event) {
	          /*
	           * Showcase instantiieren.
	           */
	          Update update = new DeveloperView();
	          
	          RootPanel.get("Details").clear();
	          RootPanel.get("Details").add(update);
	    }
	    });
	    
	    profileButton.addClickHandler(new ClickHandler() {
	  	public void onClick(ClickEvent event) {
	          /*
	           * Showcase instantiieren.
	           */
	          Update update = new EditProfileView();
	          
	          RootPanel.get("Details").clear();
	          RootPanel.get("Details").add(update);
	    }
	    });

	    
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
	    
	}
 
 private void loadLogin() {
     
     Cookies.setCookie("usermail", null);
     signInLink.setHref(loginInfo.getLoginUrl());
     signInLink.setStyleName("loginLink");
     loginLabel.setStyleName("loginLink2");
     loginPanel.setStyleName("login");
     
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

		 userLabel.setText("Du bist eingeloggt als: " + currentUser.getMail());
		 Update update = new WelcomeView();
		 RootPanel.get("Details").add(update);
		 
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
				 nbDataProvider.getList().add(notebooks.get(x));

			 }
//			 nbCellList.setVisibleRange(0, 5);
//			 adminService.getNotesOfNotebook(nbSelectionModel.getSelectedObject(), getNotesOfNotebookCallback());
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
		 notesDataProvider.getList().clear();
		 for(int i = 0; i < notes.size(); i++) {
			 notesDataProvider.getList().add(notes.get(i).getnTitle());
		 }
			Update update = new EditNotebookView();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(update);
	 }
	 };
	 return asyncCallback;
 }
 
 private AsyncCallback<Permission> getPermissionCallback() {
	 AsyncCallback<Permission> asyncCallback = new AsyncCallback<Permission>(){
	 
	 @Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}
	 
	 @Override
	 public void onSuccess(Permission result) {
		 ClientsideSettings.getLogger().
		 severe("Success GetPermissionCallback: " + result.getClass().getSimpleName());
		 if(result.getPermissionType() == 2 || result.getPermissionType() == 3){
			 createNoteButton.setEnabled(true);
		 }
	 }
	 };
	 return asyncCallback;
 }
 
 }