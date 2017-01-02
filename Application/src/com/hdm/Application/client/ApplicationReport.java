package com.hdm.Application.client;

import java.sql.Date;

import org.apache.jsp.ah.searchDocumentBody_jsp;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hdm.Application.client.gui.LoginService;
import com.hdm.Application.client.gui.LoginServiceAsync;
import com.hdm.Application.shared.LoginInfo;
import com.hdm.Application.shared.NoteAdministration;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.ReportGeneratorAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.google.gwt.user.datepicker.client.DateBox;
import com.hdm.Application.shared.report.*;


/**
 * Entry-Point-Klasse des <b>application Report-Generators</b>.
 */
public class ApplicationReport implements EntryPoint {

	
	
	LoginInfo loginInfo = new LoginInfo();
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte melden Sie sich mit Ihren Google Account, um einen Zugriff auf die App zu haben.");
	private Anchor signInLink = new Anchor("Login");
	public Anchor signOutLink = new Anchor("Logout");
	

	
	LoginServiceAsync loginService = GWT.create(LoginService.class);
	private NoteAdministrationAsync adminService = ClientsideSettings.getAdministration();
	private ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
	//Buttons der Navigation. MÃ¼ssen final sein, damit Callbacks sie verÃ¤ndern kÃ¶nnen
	final Button showReportAllUserNotesButton = new Button("Alle Notizen eines Nutzers anzeigen");
	final Button showReportFilteredNotesButton = new Button("Alle gefilterten Notizen");
	final Button showEditor = new Button("Zurück zum Editor");
	final DateBox searchDateBox = new DateBox();
	private VerticalPanel loginTextPanel = new VerticalPanel();

	

	
	//die beiden Hauptpanel definieren
	private VerticalPanel navigationPanel = new VerticalPanel();	
	private VerticalPanel detailPanel = new VerticalPanel();
	private HorizontalPanel filterPanel = new HorizontalPanel();
	private HorizontalPanel radiobuttonPanel = new HorizontalPanel();
	private HorizontalPanel searchUserPanel = new HorizontalPanel();
	private VerticalPanel ergebnisPanel = new VerticalPanel();
	private Label welcomeLabel = new Label();
	final Button logoButton = new Button();
	private HorizontalPanel headPanel = new HorizontalPanel();
	
	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benÃ¶tigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */

	@Override
	public void onModuleLoad() {

//		if (reportGenerator == null) {
//			reportGenerator = ClientsideSettings.getReportGenerator();
//		}
//
//		if ( noteadministration == null) {
//			Window.alert("NoteAdministration Proxy noch nicht erzeugt.");
//			
//			noteadministration = GWT.create(NoteAdministrationAsync.class);
//		}	
//		
		
		
		
		welcomeLabel.setText("Willkommen im Report Generator, bitte wählen Sie in der Navigation zuerst aus welchen Report Sie generieren möchten.");
		filterPanel.add(welcomeLabel);	
		searchDateBox.setStyleName("gwt-DateBox");
		showReportAllUserNotesButton.setStylePrimaryName("notework-searchbutton");
		showReportFilteredNotesButton.setStylePrimaryName("notework-searchbutton");
		navigationPanel.add(showReportAllUserNotesButton);
		navigationPanel.add(showReportFilteredNotesButton);
		detailPanel.add(filterPanel);
		detailPanel.add(ergebnisPanel);
		RootPanel.get("Navigator").add(navigationPanel);
		RootPanel.get("Details").add(detailPanel);
		
		showReportAllUserNotesButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadSearchUserPanel();
			
				
			}
		});
		
		showReportFilteredNotesButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadRadiobuttonPanel();
				
				
			
				
			}
		});
		
		/*
		 * Login Status mittels LoginService checken. Ãœbergabeparameter isReportGen ist true,
		 * weil wir uns im Report Generator befinden 
		 */
//			loginService.login( GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
//				public void onFailure(Throwable error) {
//					Window.alert("Fehler beim Login: " + error.getMessage());
//				}
//				public void onSuccess(LoginInfo result) {
//					 loginInfo = result;
//					 
//					 //Wenn Nutzer noch nicht eingeloggt ist, wird der Login Dialog aufgebaut.
//					if (!loginInfo.isLoggedIn()){
//						loadLogin();}
//					else{
//						//Cookies.setCookie("userMail", loginInfo.getEmailAddress()); 	
//						
//						adminService.getUserByEmail(loginInfo.getEmailAddress(), new AsyncCallback<AppUser>(){
//							@Override
//							public void onFailure(Throwable caught) {
//								Window.alert("Es ist ein Fehler beim Abgleich mit der Datenbank entstanden." + caught); 
//							}
//							
//							@Override
//							public void onSuccess(final AppUser result) {								
//								detailPanel.add(filterPanel);
//								detailPanel.add(ergebnisPanel);
//								welcomeLabel.setText("Willkommen im Report Generator, bitte wählen Sie in der Navigation zuerst aus welchen Report Sie generieren möchten.");
//								filterPanel.add(welcomeLabel);
//								navigationPanel.add(showReportAllUserNotesButton);
//								navigationPanel.add(showReportFilteredNotesButton);
//							}
//							
//						});
//					}
//				} 
//			});
//					
//			
		
		
	
	}//close onmoduleload
	
		protected void loadSearchUserPanel() {
			
			final Label usernameLabel = new Label();
			usernameLabel.setText("Nutzer-Email:");
			final TextBox username = new TextBox(); 
			final Button userSearchButton = new Button("Report generieren");			
			searchUserPanel.clear();
			searchUserPanel.add(usernameLabel);
			searchUserPanel.add(username);
			searchUserPanel.add(userSearchButton);
			filterPanel.clear();
			filterPanel.add(searchUserPanel);
			
			userSearchButton.addClickHandler(new ClickHandler() {
				
				@Override
	public void onClick(ClickEvent event) {
					Window.alert("UserSearchbutton geklickt!");
//					adminService.getUserByEmail("weirichandra@yahoo.de",new AsyncCallback<AppUser>(){
//
//						@Override
//						public void onFailure(Throwable caught) {
//							// TODO Auto-generated method stub
//							
//						}
//
//						@Override
//						public void onSuccess(AppUser result) {
//														
//						}
//						
//						
//					});
//					
//				}
				AppUser result = new AppUser();	
				result.setUserID(0);
				result.setUserName("Andra Weirich");
				reportGenerator.createAllNotesFromUserReport(result, new AsyncCallback<AllNotesFromUser>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(AllNotesFromUser result) {
						Window.alert("Result: "+result.gettitel());
						if (result != null) {
							//Um den Report in HTML-Text zu überführen benötigen wir einen HTMLReportWriter
							HTMLReportWriter writer = new HTMLReportWriter();
							//Wir übergeben den erhaltenen Report an den HTMLReportWriter
							writer.process(result);
							//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
							detailPanel.clear();
							//Wir befüllen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
							detailPanel.add(new HTML(writer.getReportText()));
							//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
							

						}
						
					}
					
				}); }

			});
		
	}
	
		

		
protected void loadRadiobuttonPanel() {
	
	
	final TextBox searchBox = new TextBox();
	final Button searchButton = new Button("Report anzeigen");
	final RadioButton dueDateButton = new RadioButton("Radiobutton-Group","Fälligkeitsdatum");
	final RadioButton erstellungsdatumButton = new RadioButton("Radiobutton-Group","Erstellungsdatum");
	final RadioButton notebookButton = new RadioButton("Radiobutton-Group","Notebook");
	radiobuttonPanel.clear();
	radiobuttonPanel.add(searchButton);
	radiobuttonPanel.add(searchBox);
	radiobuttonPanel.add(searchDateBox);
	searchDateBox.setVisible(false);
	radiobuttonPanel.add(dueDateButton);
	radiobuttonPanel.add(erstellungsdatumButton);
	radiobuttonPanel.add(notebookButton);
	filterPanel.clear();
	filterPanel.add(radiobuttonPanel);
	
	searchButton.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			
			Window.alert("Button hat noch keine Funktion.");
		}
	});
	
	dueDateButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
		
		
		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			searchBox.setVisible(false);
			searchDateBox.setVisible(true);
			searchDateBox.showDatePicker();
			//noteTable();
			
		}
	});
			
		
	}

		private void buildPanels(){
			
		//Logout Link URL abrufen und 
		signOutLink.setStylePrimaryName("application-logout");
		signOutLink.setHref(loginInfo.getLogoutUrl());
		
		//Setzen der Style CSS Klasse
		showReportAllUserNotesButton.setStylePrimaryName("application-menubutton");
		showReportFilteredNotesButton.setStylePrimaryName("application-menubutton");
		showEditor.setStylePrimaryName("application-menubutton");

		//Label definieren, das angezeigt wird, solange der User wartet, bis sein Report generiert ist
		final Label LoadingLabel = new Label("Dein Report wird generiert. Bitte habe einen Moment Geduld...");
		
		
		
		//ClickHandler für den Button zum Report nicht besuchter AppUsere
		showReportAllUserNotesButton.addClickHandler(new ClickHandler() {
			@Override
			
			public void onClick(ClickEvent event) {
				//Button deaktivieren, damit nicht mehrere Anfragen auf einmal gesendet werden
				showReportFilteredNotesButton.setEnabled(false);
				
				/*
				 * PrÃ¼fung ob zum eingeloggten AppUser ein AppUser in der Datenbank vorhanden ist.
				 * Dieser Schritt verhindert das Generieren von Reports durch nicht-eingloggte Benutzer
				 */
				adminService.getUserByEmail(Cookies.getCookie("userMail"), new AsyncCallback<AppUser>(){
					
					@Override
					public void onFailure(Throwable caught) {
						//Wenn kein eingeloggtes AppUser gefunden wurde, wird der User benachrichtigt und der Button wieder freigegeben
						Window.alert("Generieren des Reports fehlgeschlagen: Eingeloggtes AppUser nicht gefunden");
						showReportFilteredNotesButton.setEnabled(true);

					}

					@Override
					public void onSuccess(AppUser result) {
						//Detail Panel leeren
						detailPanel.clear();
						//Label anzeigen, das den User informiert, dass sein Report nun generiert wird
						detailPanel.add(LoadingLabel);
						//Callback senden, der den angeforderten Report generiert
						//reportGenerator.createAllNotesFromUserReport(result, new createAllNotesFromUserReportCallback());
						
					}
					
				});
			
				

			}
		});
		
		
		//ClickHandler fÃ¼r den Button zum Report nach Suchprofil
		
		
		//ClickHandler fÃ¼r den Button zum Navigieren zurÃ¼ck zum Editor
		showEditor.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign(GWT.getHostPageBaseURL());
				
			}
			
		});
		
		
	
		RootPanel.get("Navigator").add(navigationPanel);
		RootPanel.get("Details").add(detailPanel);
		
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
		     
//		     loginTextPanel.add(usernameLabel);
//		     loginTextPanel.add(usernameBox);
//		     loginTextPanel.add(passwordLabel);
//		     loginTextPanel.add(passwordBox);
		     loginPanel.add(loginTextPanel);
		     
		     
		     Cookies.setCookie("userMail", null);
		     Cookies.setCookie("userID", null);
		     RootPanel.get("Details").clear();
		     RootPanel.get("Details").add(loginPanel);     
		    }
		 

	/**
	 * Diese Nested Class wird als Callback fÃ¼r das Erzeugen des
	 * AllAppUseresNotVisitedReport benÃ¶tigt.
	 * 
	 * @author bueschel
	 */
	class createAllNotesFromUserReportCallback implements
			AsyncCallback<AllFilteredNotes> {
		@Override
		public void onFailure(Throwable error) {
			Window.alert("Fehler onFailure: " + error.getMessage());
			showReportFilteredNotesButton.setEnabled(true);
		}

		@Override
		public void onSuccess(AllFilteredNotes report) {
			if (report != null) {
				//Um den Report in HTML-Text zu Ã¼berfÃ¼hren benötigen wir einen HTMLReportWriter
				HTMLReportWriter writer = new HTMLReportWriter();
				//Wir Ã¼bergeben den erhaltenen Report an den HTMLReportWriter
				writer.process(report);
				//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
				detailPanel.clear();
				//Wir befÃ¼llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
				detailPanel.add(new HTML(writer.getReportText()));
				//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
				showReportFilteredNotesButton.setEnabled(true);

			}
		}

	}
	
}

