package com.hdm.Application.client.gui;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.user.client.Cookies;
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
import com.google.gwt.view.client.ListDataProvider;
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
	HorizontalPanel radiobuttonPanel = new HorizontalPanel();
	VerticalPanel ergebnisPanel = new VerticalPanel();
	private final CellTable<Note> noteTable = new CellTable<Note>();
	private final CellTable<AppUser> userTable = new CellTable<AppUser>();
	private final CellTable<Notebook> notebookTable = new CellTable<Notebook>();
	
	
	private boolean dd;
	private boolean un;
	private boolean n;
	private boolean nb;
	/**
	 * Erstellung aller Widgets
	 */

	TextBox searchBox = new TextBox();
	DateBox searchDateBox = new DateBox(); 
	final Button searchButton = new Button("Search");
	final Button showNoteButton = new Button ("Anzeigen");
	final RadioButton dueDateButton = new RadioButton("Radiobutton-Group","Duedate");
	final RadioButton userNameButton = new RadioButton("Radiobutton-Group","Username");
	final RadioButton noteButton = new RadioButton("Radiobutton-Group","Note");
	final RadioButton notebookButton = new RadioButton("Radiobutton-Group","Notebook");
	final SingleSelectionModel<Note> selectionModel = new SingleSelectionModel<Note>(new ProvidesKey<Note>() {
	public Object getKey (Note object){
		return object.getnID();
	}
	});
	final SingleSelectionModel<Notebook> selectionModelA = new SingleSelectionModel<Notebook>(new ProvidesKey<Notebook>(){
		public Object getKey (Notebook object){
			return object.getNbID();
	}
	});
	final SingleSelectionModel<AppUser> selectionModelB = new SingleSelectionModel<AppUser>(new ProvidesKey<AppUser>(){
		public Object getKey (AppUser object){
			return object.getUserID();
	}
	});
	
	protected void run() {
		
		// Ankï¿½ndigung, was nun geschehen wird.
		//Data Tables werden erzeugt.
		noteTable();
		notebookTable();
		userTable();
		noteTable.setSelectionModel(selectionModel);
		notebookTable.setSelectionModel(selectionModelA);
		userTable.setSelectionModel(selectionModelB);
		
		
		this.append("");

		/**
		 * Zuteilung der Widgets zum jeweiligen Panel
		 */

		searchPanel.add(searchButton);
		searchPanel.add(searchBox);
		searchPanel.add(searchDateBox);
		radiobuttonPanel.add(dueDateButton);
		radiobuttonPanel.add(userNameButton);
		radiobuttonPanel.add(noteButton);
		radiobuttonPanel.add(notebookButton);
		searchPanel.add(radiobuttonPanel);
		searchPanel.add(ergebnisPanel);
		ergebnisPanel.clear();
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
				searchDateBox.showDatePicker();
				//noteTable();
				
			}
		});
		
			userNameButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				
				
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
				searchBox.setVisible(true);
				searchDateBox.setVisible(false);
				//userTable();
				
				}
			});
				
			noteButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				
				
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					searchBox.setVisible(true);
					searchDateBox.setVisible(false);
					//noteTable();
				}
			});
			notebookButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						searchBox.setVisible(true);
						searchDateBox.setVisible(false);
						//notebookTable();
					}
		
		});
				
		showNoteButton.addClickHandler(new ClickHandler(){
			@SuppressWarnings("unchecked")
			public void onClick(ClickEvent event){
			
			Note selectedNote = ((SingleSelectionModel<Note>) noteTable.getSelectionModel()).getSelectedObject();
			Window.alert("Titel der Notiz die ausgewählt wurde: " + selectedNote.getnTitle());		
		
			if (n || dd ){
				Note selectedNoteA = ((SingleSelectionModel<Note>) noteTable.getSelectionModel()).getSelectedObject();
				Cookies.setCookie("selectedNoteID", (String.valueOf(selectedNote.getnID())));
		   			Update update = new ShowNoteView();
		   			RootPanel.get("Details").clear();
			   		RootPanel.get("Details").add(update);
				
			}
			if (nb){
				Notebook selectedNoteB = ((SingleSelectionModel<Notebook>) notebookTable.getSelectionModel()).getSelectedObject();
				Cookies.setCookie("selectedNotebookID", (String.valueOf(selectedNoteB.getNbID())));
		   			Update update = new ShowNotebookView();
		   			RootPanel.get("Details").clear();
			   		RootPanel.get("Details").add(update);			}
			
			
			if (un){
				Window.alert("Nutzer nicht anzeigbar");
			}
			}
			}
		);
			
			
		
		searchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ergebnisPanel.clear();
				/*
				 * Showcase instantiieren.
				*/
				boolean rbselected = false;
				 dd = dueDateButton.getValue();
				 un = userNameButton.getValue();
				 n = noteButton.getValue();
				 nb = notebookButton.getValue();
				
				if(dd){	
				   rbselected = true;				   
				   adminService.searchForNoteByDD(searchDateBox.getValue(), new AsyncCallback<ArrayList<Note>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Suche nach DueDate hat nicht funktioniert.");
						}
					public void onSuccess(ArrayList<Note> result) {
						if(result.size() == 0 ){
							Window.alert("Zu deiner Suche konnten leider keine Ergebnisse gefunden werden.");
					 	 }else{
					 		ergebnisPanel.add(noteTable);
					 		ergebnisPanel.add(showNoteButton);
					 		noteTable.setRowCount(result.size(), false);
							noteTable.setRowData(0, result);
							ListDataProvider<Note> dataProvider = new ListDataProvider<Note>();
							dataProvider.addDataDisplay(noteTable);
							dataProvider.setList(result);
							ergebnisPanel.setVisible(true);
						}
						}
				   });
				}
				if(un){	
					   rbselected = true;
					   adminService.searchForUser(searchBox.getValue(), new AsyncCallback<ArrayList<AppUser>>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Suche nach User hat nicht funktioniert.");
							}
						public void onSuccess(ArrayList<AppUser> result) {
							if(result.size() == 0 ){
								Window.alert("Zu deiner Suche konnten leider keine Ergebnisse gefunden werden.");
						 	 }else{
						 		Window.alert("Resultsize: "+result.size());
						 		ergebnisPanel.add(userTable);
						 		ergebnisPanel.add(showNoteButton);
						 		userTable.setRowCount(result.size(), false);
								userTable.setRowData(0, result);
								ListDataProvider<AppUser> dataProvider = new ListDataProvider<AppUser>();
								dataProvider.addDataDisplay(userTable);
								dataProvider.setList(result);
								ergebnisPanel.setVisible(true);
							}
							}
					   });
					}
				if(n){			
						rbselected = true;
						adminService.searchForNote(searchBox.getValue(), new AsyncCallback<ArrayList<Note>>() {
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Fehler in searchForNote Call"+caught.toString());
							}

							@Override
							public void onSuccess(ArrayList<Note> result) {
								if(result.size() == 0 ){
									Window.alert("Zu deiner Suche konnten leider keine Ergebnisse gefunden werden.");
							 	 }else{
							 		Window.alert("Resultsize: "+result.size());
							 		ergebnisPanel.add(noteTable);
							 		ergebnisPanel.add(showNoteButton);
							 		noteTable.setRowCount(result.size(), false);
									noteTable.setRowData(0, result);
									ListDataProvider<Note> dataProvider = new ListDataProvider<Note>();
									dataProvider.addDataDisplay(noteTable);
									dataProvider.setList(result);
									ergebnisPanel.setVisible(true);
								}
							}
						});
					}
				
				
				if(nb){			
					   rbselected = true;
					   adminService.searchForNotebook(searchBox.getValue(), new AsyncCallback<ArrayList<Notebook>>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Suche nach Note hat nicht funktioniert.");
							}
						public void onSuccess(ArrayList<Notebook> result) {
							if(result.size() == 0 ){
								Window.alert("Zu deiner Suche konnten leider keine Ergebnisse gefunden werden.");
						 	 }else{
						 		Window.alert("Resultsize: "+result.size());
						 		ergebnisPanel.add(notebookTable);
						 		ergebnisPanel.add(showNoteButton);
						 		notebookTable.setRowCount(result.size(), false);
								notebookTable.setRowData(0, result);
								ListDataProvider<Notebook> dataProvider = new ListDataProvider<Notebook>();
								dataProvider.addDataDisplay(notebookTable);
								dataProvider.setList(result);
								ergebnisPanel.setVisible(true);
							}
						 }
					   });
					}
				
				if (!rbselected) {
					Window.alert("Bitte erst ein Radiobutton ausgewählen!");
					searchBox.setFocus(true);
					searchBox.setText("");
					searchDateBox.setValue(null);
				}
				
//				Update update = new ResultView();
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").add(update);
			}
		});
	}

	private void userTable(){
		
		Column<AppUser, Boolean> checkColumn = new Column<AppUser, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(AppUser object) {
				return selectionModelB.isSelected(object);
			}
			
		};
		   TextColumn<AppUser> nameCol = new TextColumn<AppUser>() {

		    @Override
		    public String getValue(AppUser name) {

		     return (String)name.getUserName();
		    }
		    
		   };

		   TextColumn<AppUser> googleIDCol = new TextColumn<AppUser>() {

		    @Override
		    public String getValue(AppUser id) {
		     
		     return (String)id.getGoogleID()+"@gmail.com";
		    }
		   };
		  
		 userTable.addColumn(nameCol, "Name");
		 userTable.addColumn(googleIDCol, "E-Mail");
		 userTable.addColumn(checkColumn);
	}
	
	protected void notebookTable(){
			
		Column<Notebook, Boolean> checkColumn = new Column<Notebook, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Notebook object) {
				return selectionModelA.isSelected(object);
			}
		};
		
		
		TextColumn<Notebook> titleCol = new TextColumn<Notebook>() {

			@Override
			public String getValue(Notebook name) {

				return (String)name.getNbTitle();
			}
		};


		TextColumn<Notebook> moddateCol = new TextColumn<Notebook>() {

		
			public String getValue(Notebook str) {
				String date;			
				if(str.getNbModDate() == null){					
					date = "unbekannt";
				}else{
					
					date = str.getNbModDate().toString();
				}
				return (date);
			}
		};

		TextColumn<Notebook> createCol = new TextColumn<Notebook>() {
			public String getValue(Notebook str) {
				String date;			
				if(str.getNbCreDate() == null){					
					date = "unbekannt";
				}else{
					
					date = str.getNbCreDate().toString();
				}
				return (date);
			}
		};

		notebookTable.addColumn(titleCol, "Notizbuch Titel");
		notebookTable.addColumn(createCol, "erstellt am:");
		notebookTable.addColumn(moddateCol, "zuletzt bearbeitet:");
		notebookTable.addColumn(checkColumn);
		
	}

	
	
   protected void noteTable(){
	   Column<Note, Boolean> checkColumn = new Column<Note, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Note object) {
				return selectionModel.isSelected(object);
			}
			
		};
	
		TextColumn<Note> titleCol = new TextColumn<Note>() {

			@Override
			public String getValue(Note title) {

				return (String)title.getnTitle();
			}
		};

		TextColumn<Note> subCol = new TextColumn<Note>() {

			@Override
			public String getValue(Note subtitle) {

				return (String)subtitle.getnSubtitle();
			}
		};

		TextColumn<Note> createCol = new TextColumn<Note>() {
			
		
			public String getValue(Note str) {
				String date;			
				if(str.getnCreDate() == null){					
					date = "unbekannt";
				}else{
					
					date = str.getnCreDate().toString();
				}
				return (date);
			}
		};
		
		noteTable.addColumn(titleCol, "Titel");
		noteTable.addColumn(subCol, "Untertitel");
		noteTable.addColumn(createCol, "erstellt am:");
		noteTable.addColumn(checkColumn);
		

   }
}
