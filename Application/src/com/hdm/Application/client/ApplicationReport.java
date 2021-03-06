package com.hdm.Application.client;

import java.sql.Date;

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
 * @author Andra Weirich
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
	final Button showReportAllUserNotesButton = new Button("Alle Notizen bzgl. Nutzers");
	final Button showReportFilteredNotesButton = new Button("Alle Notizen bzgl. eines Datums");
	final Button showReportAllNotes = new Button ("Alle Notizen");
	final Button showEditor = new Button("Zurück zum Editor");
	final DateBox searchDateBox = new DateBox();
	private VerticalPanel loginTextPanel = new VerticalPanel();

	

	
	//die beiden Hauptpanel definieren
	private VerticalPanel navigationPanel = new VerticalPanel();	
	private VerticalPanel detailPanel = new VerticalPanel();
	private HorizontalPanel filterPanel = new HorizontalPanel();
	private HorizontalPanel radiobuttonPanel = new HorizontalPanel();
	private HorizontalPanel searchPanel = new HorizontalPanel();
	private HorizontalPanel searchUserPanel = new HorizontalPanel();
	private HorizontalPanel notePanel = new HorizontalPanel();
	private VerticalPanel ergebnisPanel = new VerticalPanel();
	private Label welcomeLabel = new Label();
	final Button logoButton = new Button();
	private HorizontalPanel headPanel = new HorizontalPanel();
	
	boolean leb;
	boolean bb;
	boolean lb;
	boolean en; 
	boolean an;
	
	private boolean dd;
	private boolean md;
	private boolean cd;
	
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

	/**Diese Methode wird aufgerufen, wenn auf den Button Notizen bzgl. Nutzer geklickt wird
	 * Das searchUserPanel wird aufgebaut und benötigte Button hinzugefügt.
	 */
	protected void loadSearchUserPanel() {
		
		detailPanel.clear();
		detailPanel.add(filterPanel);
		
		final Label usernameLabel = new Label();
		usernameLabel.setText("Nutzer-Email:");
		final TextBox username = new TextBox(); 
		final Button userSearchButton = new Button("Report generieren");
		final RadioButton lebB = new RadioButton("Radiobutton-Group","Leseberechtigung");
		final RadioButton lbB = new RadioButton("Radiobutton-Group","Löschberechtigung");
		final RadioButton bbB = new RadioButton("Radiobutton-Group","Bearbeitungsberechtigung");
		final RadioButton enB = new RadioButton("Radiobutton-Group","Eigene Notizen");
		final RadioButton anB = new RadioButton("Radiobutton-Group","Alle Notizen eines Users");

		searchUserPanel.clear();
		searchUserPanel.add(usernameLabel);
		searchUserPanel.add(username);
		searchUserPanel.add(userSearchButton);
		searchUserPanel.add(lebB);
		searchUserPanel.add(bbB);
		searchUserPanel.add(lbB);
		searchUserPanel.add(enB);
		searchUserPanel.add(anB);

		filterPanel.clear();
		filterPanel.add(searchUserPanel);
			
		
		/**Bei klicken auf den userSearchButton wird zu erst nach einem Nutzer gesucht und daraufhin - falls Nutzer gefunden - wird überprüft
		 *welcher Radiobutton selected ist. Sobald einer ausgewählt wurde, wird die passende Methode ausgeführt
		 * 
		 */
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
	
		
/**Diese Methode wird nach Klicken des "Notizen bzg. eines Datum" Button ausgeführt
  * 
  */
protected void loadRadiobuttonPanel() {
	Window.alert("loadRadiobutton Panel ausgeführt");
	detailPanel.clear();
	detailPanel.add(filterPanel);
	
	final RadioButton duedate = new RadioButton("Radiobuttongroup", "Fälligkeitsdatum");
	final RadioButton modidate = new RadioButton("Radiobuttongroup", "Modifikationsdatum");
	final RadioButton creadate = new RadioButton("Radiobuttongroup", "Erstellungsdatum");

	final Label vonLabel = new Label();
	vonLabel.setText("Von:");
	final DateBox vonBox = new DateBox();
	final Label bisLabel = new Label();
	bisLabel.setText("Bis:");
	final DateBox bisBox = new DateBox();
	final Button searchButton = new Button("Report anzeigen");
	radiobuttonPanel.clear();
	radiobuttonPanel.add(duedate);
	radiobuttonPanel.add(modidate);
	radiobuttonPanel.add(creadate);
	searchPanel.clear();
	searchPanel.add(vonLabel);
	searchPanel.add(vonBox);
	searchPanel.add(bisLabel);
	searchPanel.add(bisBox);
	searchPanel.add(searchButton);
	searchDateBox.setVisible(true);
	filterPanel.clear();
	filterPanel.add(radiobuttonPanel);
	detailPanel.add(searchPanel);
	

	DateTimeFormat datumsFormat = DateTimeFormat.getFormat("dd.MM.yyyy");
	vonBox.setFormat(new DateBox.DefaultFormat(datumsFormat));
	vonBox.getDatePicker().setYearArrowsVisible(true);
	vonBox.getDatePicker().setYearAndMonthDropdownVisible(true);
    vonBox.getDatePicker().setVisibleYearCount(10);
    
	bisBox.setFormat(new DateBox.DefaultFormat(datumsFormat));
	bisBox.getDatePicker().setYearArrowsVisible(true);
	bisBox.getDatePicker().setYearAndMonthDropdownVisible(true);
    bisBox.getDatePicker().setVisibleYearCount(10);
	
    
	/**wenn der Button geklickt wird, wird geprüft welche DateBox Inhalt halt und welche nicht. Je nach befüllung 
	 * wird entweder alle Notizen vor einem, nach einem Datum oder zwischen zwei Daten ausgegeben
	 */
	searchButton.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) { 
			 
			boolean rbselected = false;
			 dd = duedate.getValue();
			 md = modidate.getValue();
			 cd = creadate.getValue();
			 
			if(dd){
				Window.alert("DueDate ausgewählt");
				 rbselected = true;	
				 
				 if (vonBox.getValue() == null && bisBox.getValue() != null ){
						java.util.Date date = new java.util.Date(0);
						vonBox.setValue(date);
					}
					if (vonBox.getValue() != null && bisBox.getValue() == null){
						
						java.util.Date date = new java.util.Date();
						bisBox.setValue(date);
					}
					
					if ( vonBox.getValue() == null && bisBox.getValue() == null){
						
					}
					reportGenerator.findByBetweenDueDate(vonBox.getValue(), bisBox.getValue(), new AsyncCallback<AllFilteredNotes>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub	
						}
						@Override
						public void onSuccess(AllFilteredNotes result) {
							Window.alert("between duedate onsucess");

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
			
			if(md){
				Window.alert("Modidate ausgewählt");
				 rbselected = true;	
				 
				 if (vonBox.getValue() == null && bisBox.getValue() != null ){
						java.util.Date date = new java.util.Date(0);
						vonBox.setValue(date);
					}
					if (vonBox.getValue() != null && bisBox.getValue() == null){
						
						java.util.Date date = new java.util.Date();
						bisBox.setValue(date);
					}
					
					if ( vonBox.getValue() == null && bisBox.getValue() == null){
						
					}
					reportGenerator.findByBetweenModiDate(vonBox.getValue(), bisBox.getValue(), new AsyncCallback<AllFilteredNotes>() {
						

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub	
						}
						@Override
						public void onSuccess(AllFilteredNotes result) {
							Window.alert("between Modidate onsucess");

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
					if(cd){
						Window.alert("Creaddate ausgewählt");

						 rbselected = true;	
						 
						 if (vonBox.getValue() == null && bisBox.getValue() != null ){
								java.util.Date date = new java.util.Date(0);
								vonBox.setValue(date);
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
									Window.alert("between creadate fehlgeschlaegn");
								}
								@Override
								public void onSuccess(AllFilteredNotes result) {
									Window.alert("between creadate onsucess");

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

/**Diese Methode wird aufgerufen, wenn der Button "Alle Notizen geklickt wurde"
 * 
 */
		private void loadNotePanel(){
			
			detailPanel.clear();
			detailPanel.add(filterPanel);
			
			final Label usernameLabel = new Label();
			usernameLabel.setText("Titelstichwort:");
			final TextBox sucheNachNotiz = new TextBox(); 
			final Button noteSearchButton = new Button("Report generieren");
			final RadioButton no = new RadioButton("Radiobutton-Group","Notiz");
			final RadioButton nb = new RadioButton("Radiobutton-Group","Notizbuch");
			final RadioButton aNo = new RadioButton("Radiobutton-Group","Alle Notizen");
			
			notePanel.clear();
			notePanel.add(usernameLabel);
			notePanel.add(sucheNachNotiz);
			notePanel.add(noteSearchButton);
			notePanel.add(no);
			notePanel.add(nb);
			notePanel.add(aNo);
			
			filterPanel.clear();
			filterPanel.add(notePanel);
			
			
			
			
		
			/**Bei Klicken auf diesen Button werden die Ifs ausgeführt und geschaut welcher Radiobutton geklickt wurde.
			 * Daraufhin wird nach einem Stichwort in Notizbuch, Notiz gesucht. Oder wenn kein Stichwort 
			 * vorhanden, dann werden alle Notizen ausgegeben 
			 */
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