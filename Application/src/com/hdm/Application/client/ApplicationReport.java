package com.hdm.Application.client;

import java.sql.Date;
import java.time.LocalDate;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.google.gwt.user.datepicker.client.DateBox;
import com.hdm.Application.client.gui.LoginService;
import com.hdm.Application.client.gui.LoginServiceAsync;
import com.hdm.Application.shared.LoginInfo;
import com.hdm.Application.shared.NoteAdministrationAsync;
import com.hdm.Application.shared.ReportGeneratorAsync;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.report.AllFilteredNotes;
import com.hdm.Application.shared.report.AllNotes;
import com.hdm.Application.shared.report.AllNotesFromUser;
import com.hdm.Application.shared.report.HTMLReportWriter; 


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
	
	//Buttons der Navigation. Müssen final sein, damit Callbacks sie verändern können
	final Button showReportAllUserNotesButton = new Button("Alle Notizen eines Nutzers anzeigen");
	final Button showReportFilteredNotesButton = new Button("Alle gefilterten Notizen");
	final Button showReportAllNotes = new Button ("Alle Notizen");
	final Button showEditor = new Button("Zurück zum Editor");
	final DateBox searchDateBox = new DateBox();
	private VerticalPanel loginTextPanel = new VerticalPanel();

	

	
	//die beiden Hauptpanel definieren
	private VerticalPanel navigationPanel = new VerticalPanel();	
	private VerticalPanel detailPanel = new VerticalPanel();
	private HorizontalPanel filterPanel = new HorizontalPanel();
	private HorizontalPanel radiobuttonPanel = new HorizontalPanel();
	private VerticalPanel searchUserPanel = new VerticalPanel();
	private VerticalPanel notePanel = new VerticalPanel();
	private HorizontalPanel hpRadiobuttonPanel = new HorizontalPanel();
	private VerticalPanel ergebnisPanel = new VerticalPanel();
	private Label welcomeLabel = new Label();
	final Button logoButton = new Button();
	private HorizontalPanel headPanel = new HorizontalPanel();
	
	boolean leb;
	boolean bb;
	boolean lb;
	boolean en; 
	boolean an;
	
	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benötigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */

	@Override
	public void onModuleLoad() {

		
		/*
		 * Login Status mittels LoginService checken. Übergabeparameter isReportGen ist true,
		 * weil wir uns im Report Generator befinden 
		 */
 			loginService.login( GWT.getHostPageBaseURL()+GWT.getModuleName()+".html", new AsyncCallback<LoginInfo>() {
 				
 				public void onFailure(Throwable error) {
 					Window.alert("Fehler beim Login: " + error.getMessage());
 				}
 				public void onSuccess(LoginInfo result) {
 					 loginInfo = result;
 					 
 					 // Wenn Nutzer noch nicht eingeloggt ist, wird der Login Dialog aufgebaut.
 					if (!loginInfo.isLoggedIn()){
 						loadLogin();}
 					else{
 						 Cookies.setCookie("userMail", loginInfo.getEmailAddress()); 	
 						
 						adminService.getUserByEmail(loginInfo.getEmailAddress(), new AsyncCallback<AppUser>(){
 							@Override
 							public void onFailure(Throwable caught) {
 								Window.alert("Es ist ein Fehler beim Abgleich mit der Datenbank entstanden." + caught); 
 							}
 							
 							@Override
 							public void onSuccess(final AppUser result) {								
			 								
			
					welcomeLabel.setText("Willkommen im Report Generator, bitte wählen Sie in der Navigation zuerst aus welchen Report Sie generieren möchten.");
					filterPanel.add(welcomeLabel);	
					searchDateBox.setStyleName("gwt-DateBox");
					showReportAllUserNotesButton.setStylePrimaryName("notework-searchbutton");
					showReportFilteredNotesButton.setStylePrimaryName("notework-searchbutton");
					showReportAllNotes.setStylePrimaryName("notework-searchbutton");
					navigationPanel.add(showReportAllUserNotesButton);
					navigationPanel.add(showReportFilteredNotesButton);
					navigationPanel.add(showReportAllNotes);
					detailPanel.add(filterPanel);
					detailPanel.add(ergebnisPanel);
					RootPanel.get("Navigator").add(navigationPanel);
					RootPanel.get("Details").add(detailPanel);
					
					showReportAllUserNotesButton.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							loadSearchUserPanel();
							showReportFilteredNotesButton.setEnabled(true);
							showReportAllNotes.setEnabled(true);
							
						}
					});
					
					showReportFilteredNotesButton.addClickHandler(new ClickHandler() {
						
			
						@Override
						public void onClick(ClickEvent event) {
							loadRadiobuttonPanel();
							showReportAllNotes.setEnabled(true);
							showReportAllUserNotesButton.setEnabled(true);
				
						}
					});
					showReportAllNotes.addClickHandler(new ClickHandler() {
			
						public void onClick (ClickEvent event) {
							loadNotePanel();
							showReportFilteredNotesButton.setEnabled(true);
							showReportAllUserNotesButton.setEnabled(true);								
									;}
					});
				}				
						});
					}
				} 
			});

	}//close onmoduleload

	
	protected void loadSearchUserPanel() {
		
		detailPanel.clear();
		detailPanel.add(filterPanel);
		
		final Label usernameLabel = new Label();
		usernameLabel.setText("Nutzer-Email");
		usernameLabel.setStylePrimaryName("usernamelabel");
		final TextBox username = new TextBox(); 
		username.setStylePrimaryName("usernamelabel");
		final RadioButton lebB = new RadioButton("Radiobutton-Group","Leseberechtigung");
		lebB.setStylePrimaryName("usernamelabel");
		final RadioButton lbB = new RadioButton("Radiobutton-Group","Löschberechtigung");
		lbB.setStylePrimaryName("usernamelabel");
		final RadioButton bbB = new RadioButton("Radiobutton-Group","Bearbeitungsberechtigung");
		bbB.setStylePrimaryName("usernamelabel");
		final RadioButton enB = new RadioButton("Radiobutton-Group","Eigene Notizen");
		enB.setStylePrimaryName("usernamelabel");
		final RadioButton anB = new RadioButton("Radiobutton-Group","Alle Notizen eines Users");
		anB.setStylePrimaryName("usernamelabel");
		final Button userSearchButton = new Button("Report generieren");
		userSearchButton.setStylePrimaryName("usernamelabel");
		
		

		searchUserPanel.clear();
		searchUserPanel.add(usernameLabel);
		searchUserPanel.add(username);
		searchUserPanel.add(lebB);
		searchUserPanel.add(bbB);
		searchUserPanel.add(lbB);
		searchUserPanel.add(enB);
		searchUserPanel.add(anB);
		searchUserPanel.add(userSearchButton);

		filterPanel.clear();
		filterPanel.add(searchUserPanel);
			
			userSearchButton.addClickHandler(new ClickHandler() {
				
				@Override
	public void onClick(ClickEvent event) {
							 
							 adminService.getUserByEmail(username.getText(), new AsyncCallback<AppUser>(){
						
						@Override
						public void onFailure(Throwable caught) {
							//Wenn kein eingeloggtes AppUser gefunden wurde, wird der User benachrichtigt und der Button wieder freigegeben
							Window.alert("Generieren des Reports fehlgeschlagen: Eingeloggtes AppUser nicht gefunden");
							showReportFilteredNotesButton.setEnabled(true);

						}
						@SuppressWarnings("unchecked")

						@Override
						public void onSuccess(final AppUser user) {
							boolean rbselected = false;
							 leb = lebB.getValue();
							 bb = bbB.getValue();
							 lb = lbB.getValue();
							 en = enB.getValue();
							 an = anB.getValue();
							if(user != null){								
								if(leb){
									rbselected = true;
									reportGenerator.createAllFilteredNotesLEB(user, new AsyncCallback<AllNotesFromUser>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("onFailure of createAllFiltereredNotesLEB: "+caught);
									
								}

								@Override
								public void onSuccess(AllNotesFromUser result) {
									Window.alert("Resultgröße: " + result.getRows().size());
									if (result.getRows().size() > 1) {
										//Um den Report in HTML-Text zu �berf�hren ben�tigen wir einen HTMLReportWriter
										HTMLReportWriter writer = new HTMLReportWriter();
										//Wir �bergeben den erhaltenen Report an den HTMLReportWriter
										writer.process(result);
										//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
										searchUserPanel.clear();
										//Wir bef�llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
										detailPanel.add(new HTML(writer.getReportText()));
										//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
									}
									else{
										Label noresult = new Label();
										String message = "Für den Nutzer: "+ user.getUserName()+" wurden keine Notizen gefunden.";
										noresult.setText(message);
										detailPanel.clear();
										detailPanel.add(noresult);
									}
									
								}
								
							});
									}
								
							if(bb){
								rbselected = true;
								reportGenerator.createAllFilteredNotesBB(user, new AsyncCallback<AllNotesFromUser>(){

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("onFailure of createAllFiltereredNotesBB: "+caught);										
									}

									@Override
									public void onSuccess(AllNotesFromUser result) {
										if (result.getRows().size() > 1) {
											//Um den Report in HTML-Text zu �berf�hren ben�tigen wir einen HTMLReportWriter
											HTMLReportWriter writer = new HTMLReportWriter();
											//Wir �bergeben den erhaltenen Report an den HTMLReportWriter
											writer.process(result);
											//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
											searchUserPanel.clear();
											//Wir bef�llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
											detailPanel.add(new HTML(writer.getReportText()));
											//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
										}
										else{
											Label noresult = new Label();
											String message = "Für den Nutzer: "+ user.getUserName()+" wurden keine Notizen gefunden.";
											noresult.setText(message);
											detailPanel.clear();
											detailPanel.add(noresult);
										}
									}
									
								});
							}
							if(lb){
								rbselected = true;
								reportGenerator.createAllFilteredNotesLB(user, new AsyncCallback<AllNotesFromUser>(){

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("onFailure of createAllFiltereredNotesLB: "+caught);										
									}

									@Override
									public void onSuccess(AllNotesFromUser result) {
										if (result.getRows().size() > 1) {
											//Um den Report in HTML-Text zu �berf�hren ben�tigen wir einen HTMLReportWriter
											HTMLReportWriter writer = new HTMLReportWriter();
											//Wir �bergeben den erhaltenen Report an den HTMLReportWriter
											writer.process(result);
											//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
											searchUserPanel.clear();
											//Wir bef�llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
											detailPanel.add(new HTML(writer.getReportText()));
											//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
										}
										else{
											Label noresult = new Label();
											String message = "Für den Nutzer: "+ user.getUserName()+" wurden keine Notizen gefunden.";
											noresult.setText(message);
											detailPanel.clear();
											detailPanel.add(noresult);
										}
									}
									
								});
							}
							
							if(leb){
								rbselected = true;
								reportGenerator.createAllFilteredNotesLEB(user, new AsyncCallback<AllNotesFromUser>(){

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("onFailure of createAllFiltereredNotesLEB: "+caught);										
									}

									@Override
									public void onSuccess(AllNotesFromUser result) {
										if (result.getRows().size() > 1) {
											//Um den Report in HTML-Text zu �berf�hren ben�tigen wir einen HTMLReportWriter
											HTMLReportWriter writer = new HTMLReportWriter();
											//Wir �bergeben den erhaltenen Report an den HTMLReportWriter
											writer.process(result);
											//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
											searchUserPanel.clear();
											//Wir bef�llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
											detailPanel.add(new HTML(writer.getReportText()));
											//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
										}
										else{
											Label noresult = new Label();
											String message = "Für den Nutzer: "+ user.getUserName()+" wurden keine Notizen gefunden.";
											noresult.setText(message);
											detailPanel.clear();
											detailPanel.add(noresult);
										}
									}
									
								});
							}
							if(en){
								rbselected = true;
								reportGenerator.createOwnNotesFromUserReport(user, new AsyncCallback<AllNotesFromUser>(){

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("onFailure of createOwnNotes: "+caught);										
									}

									@Override
									public void onSuccess(AllNotesFromUser result) {
										if (result.getRows().size() > 1) {
											//Um den Report in HTML-Text zu �berf�hren ben�tigen wir einen HTMLReportWriter
											HTMLReportWriter writer = new HTMLReportWriter();
											//Wir �bergeben den erhaltenen Report an den HTMLReportWriter
											writer.process(result);
											//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
											searchUserPanel.clear();
											//Wir bef�llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
											detailPanel.add(new HTML(writer.getReportText()));
											//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
										}
										else{
											Label noresult = new Label();
											String message = "Für den Nutzer: "+ user.getUserName()+" wurden keine Notizen gefunden.";
											noresult.setText(message);
											detailPanel.clear();
											detailPanel.add(noresult);
										}
									}
									
								});
							}
							if(an){
								rbselected = true;
								reportGenerator.createAllNotesFromUserReport(user, new AsyncCallback<AllNotesFromUser>(){

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("onFailure of createAllNotesFromUser: "+caught);										
									}

									@Override
									public void onSuccess(AllNotesFromUser result) {
										if (result.getRows().size() > 1) {
											//Um den Report in HTML-Text zu �berf�hren ben�tigen wir einen HTMLReportWriter
											HTMLReportWriter writer = new HTMLReportWriter();
											//Wir �bergeben den erhaltenen Report an den HTMLReportWriter
											writer.process(result);
											//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
											searchUserPanel.clear();
											//Wir bef�llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
											detailPanel.add(new HTML(writer.getReportText()));
											//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
										}
										else{
											Label noresult = new Label();
											String message = "Für den Nutzer: "+ user.getUserName()+" wurden keine Notizen gefunden.";
											noresult.setText(message);
											detailPanel.clear();
											detailPanel.add(noresult);
										}
									}
									
								});
							}
							
							
							}
							else{
								Window.alert("Du funktionierst nicht richtig.");
							}
							
			
						}
						
					});
					
				 }

			});
		
	}
	
		

		
protected void loadRadiobuttonPanel() {
	
	detailPanel.clear();
	detailPanel.add(filterPanel);
	
	final Label vonLabel = new Label();
	vonLabel.setText("Von");
	vonLabel.setStylePrimaryName("loadRadiobuttonPanel");
	final DateBox vonBox = new DateBox();
	final Label bisLabel = new Label();
	bisLabel.setStylePrimaryName("loadRadiobuttonPanel");
	bisLabel.setText("Bis");
	final DateBox bisBox = new DateBox();
	final Button searchButton = new Button("Report anzeigen");
	searchButton.setStylePrimaryName("loadRadiobuttonPanel");
	
	radiobuttonPanel.clear();
	radiobuttonPanel.add(vonLabel);
	radiobuttonPanel.add(vonBox);
	radiobuttonPanel.add(bisLabel);
	radiobuttonPanel.add(bisBox);
	radiobuttonPanel.add(searchButton);
	searchDateBox.setVisible(true);
	filterPanel.clear();
	filterPanel.add(radiobuttonPanel);

	DateTimeFormat datumsFormat = DateTimeFormat.getFormat("dd.MM.yyyy");
	searchDateBox.setFormat(new DateBox.DefaultFormat(datumsFormat));
	searchDateBox.getDatePicker().setYearArrowsVisible(true);
	searchDateBox.getDatePicker().setYearAndMonthDropdownVisible(true);
    searchDateBox.getDatePicker().setVisibleYearCount(10);
	
    
	
	searchButton.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) { 
			
		
			
			if (vonBox.getValue() == null && bisBox.getValue() != null ){
				vonBox.getValue().setTime(0);
			}
			if (vonBox.getValue() != null && bisBox.getValue() == null){
				
				java.util.Date date = new java.util.Date();
				bisBox.setValue(date);
			}
			
			if ( vonBox.getValue() == null && bisBox.getValue() == null){
				
			}
				reportGenerator.findByBetweenCreationDate(vonBox.getValue(), bisBox.getValue(), new AsyncCallback<AllFilteredNotes>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub	
					}
					@Override
					public void onSuccess(AllFilteredNotes result) {
						if (result.getRows().size() > 1) {
							//Um den Report in HTML-Text zu �berf�hren ben�tigen wir einen HTMLReportWriter
							HTMLReportWriter writer = new HTMLReportWriter();
							//Wir �bergeben den erhaltenen Report an den HTMLReportWriter
							writer.process(result);
							//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
							searchUserPanel.clear();
							//Wir bef�llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
							detailPanel.add(new HTML(writer.getReportText()));
							//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
						}
						else{
							Label noresult = new Label();
							String message = "Es wurden keine Notizen gefunden.";
							noresult.setText(message);
							detailPanel.clear();
							detailPanel.add(noresult);
						}
						
					}
				});
				
			
				
		}
	});

	
	
	
			
		
	}
		private void loadNotePanel(){
			
			detailPanel.clear();
			detailPanel.add(filterPanel);
			
			final Label usernameLabel = new Label();
			usernameLabel.setText("Titelstichwort");
			usernameLabel.setStylePrimaryName("usernamelabel");
			final TextBox sucheNachNotiz = new TextBox(); 
			sucheNachNotiz.setStylePrimaryName("usernamelabel");
			final RadioButton nb = new RadioButton("Radiobutton-Group","Notizbuch");
			final RadioButton no = new RadioButton("Radiobutton-Group","Notiz");
			final RadioButton aNo = new RadioButton("Radiobutton-Group","Alle Notizen");
			final Button noteSearchButton = new Button("Report generieren");
			noteSearchButton.setStylePrimaryName("usernamelabel");
			hpRadiobuttonPanel.setStylePrimaryName("usernamelabel");
			
			notePanel.clear();
			notePanel.add(usernameLabel);
			notePanel.add(sucheNachNotiz);
			notePanel.add(hpRadiobuttonPanel);
			hpRadiobuttonPanel.add(no);
			hpRadiobuttonPanel.add(nb);
			hpRadiobuttonPanel.add(aNo);
			notePanel.add(noteSearchButton);
			
			filterPanel.clear();
			filterPanel.add(notePanel);
			
			
			no.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				
				
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					sucheNachNotiz.setVisible(true);
					;
					
				}
			});
			
			
			nb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				
				
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					sucheNachNotiz.setVisible(true);
					
				
					
				}
			});
			aNo.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				
				
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					sucheNachNotiz.setVisible(true);
					
					
				}
			});
			 
			noteSearchButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {	
					
					if (no.getValue()){
						reportGenerator.findByTitle(sucheNachNotiz.getText(), new AsyncCallback<AllNotes>() {
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("createAllFilteredNotesReport fehler");
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(AllNotes result) {
								
								if (result.getRows().size() > 1) {
									Window.alert("Result: "+ result.getRows().size());
									//Um den Report in HTML-Text zu �berf�hren ben�tigen wir einen HTMLReportWriter
									HTMLReportWriter writer = new HTMLReportWriter();
									//Wir �bergeben den erhaltenen Report an den HTMLReportWriter
									writer.process(result);
									//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
									detailPanel.clear();
									//Wir bef�llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
									detailPanel.add(new HTML(writer.getReportText()));
									//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
								}
								else{
									Label noresult = new Label();
									String message = "Es wurden keine Notizen gefunden.";
									noresult.setText(message);
									detailPanel.clear();
									detailPanel.add(noresult);
								}
							}
						});
						
					}
					
					
					if (nb.getValue()){
						reportGenerator.createAllFilteredNotesReport(sucheNachNotiz.getText(), new AsyncCallback<AllNotes>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("createAllFilteredNotesReport fehler");
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(AllNotes result) {
								
								if (result.getRows().size() > 1) {
									Window.alert("Result: "+ result.getRows().size());
									//Um den Report in HTML-Text zu �berf�hren ben�tigen wir einen HTMLReportWriter
									HTMLReportWriter writer = new HTMLReportWriter();
									//Wir �bergeben den erhaltenen Report an den HTMLReportWriter
									writer.process(result);
									//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
									detailPanel.clear();
									//Wir bef�llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
									detailPanel.add(new HTML(writer.getReportText()));
									//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
								}
								else{
									Label noresult = new Label();
									String message = "Es wurden keine Notizen gefunden.";
									noresult.setText(message);
									detailPanel.clear();
									detailPanel.add(noresult);
								}
							}
						});
						
					}
					
					if(aNo.getValue()){
						reportGenerator.createAllNotesReport(new AsyncCallback<AllNotes>() {
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Generieren des Reports fehlgeschlagen");					
							}

							@Override
							public void onSuccess(AllNotes result) {
								Window.alert("Resultgröße: " + result.getRows().size());
								if (result.getRows().size() > 1) {
									//Um den Report in HTML-Text zu �berf�hren ben�tigen wir einen HTMLReportWriter
									HTMLReportWriter writer = new HTMLReportWriter();
									//Wir �bergeben den erhaltenen Report an den HTMLReportWriter
									writer.process(result);
									//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
									searchUserPanel.clear();
									//Wir bef�llen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
									detailPanel.add(new HTML(writer.getReportText()));
									//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
								}
								else{
									Label noresult = new Label();
									String message = "Es wurden keine Notizen gefunden.";
									noresult.setText(message);
									detailPanel.clear();
									detailPanel.add(noresult);
								}
								
								
							}
						});
					}
					
					
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
		
		
		
		//ClickHandler f�r den Button zum Report f�r alle Notizen eines Users
		showReportAllUserNotesButton.addClickHandler(new ClickHandler() {
			@Override
			
			public void onClick(ClickEvent event) {
				//Button deaktivieren, damit nicht mehrere Anfragen auf einmal gesendet werden
				showReportFilteredNotesButton.setEnabled(false);
				
				/*
				 * Prüfung ob zum eingeloggten AppUser ein AppUser in der Datenbank vorhanden ist.
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
		
		//ClickHandler für den Button zum Navigieren zurück zum Editor
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
	 * Diese Nested Class wird als Callback für das Erzeugen des
	 * AllNotesFromUserReport benötigt.
	 * 
	 * @author weirich
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
				//Um den Report in HTML-Text zu überführen ben�tigen wir einen HTMLReportWriter
				HTMLReportWriter writer = new HTMLReportWriter();
				//Wir übergeben den erhaltenen Report an den HTMLReportWriter
				writer.process(report);
				//Wir leeren das DetailPanel, damit etwaige vorherige Reports nicht mehr angezeigt werden
				detailPanel.clear();
				//Wir befüllen das DetailPanel mit dem HTML-Text den wir vom ReporWriter erhalten
				detailPanel.add(new HTML(writer.getReportText()));
				//Wir aktivieren den Button zur Anforderung eines Reports wieder, da der angeforderte Report ausgegeben ist
				showReportFilteredNotesButton.setEnabled(true);

			}
		}

	}
	
}