package com.hdm.Application.client.gui;

public class NoteOverviewView extends Update{

protected String getHeadlineText() {
		    return "Notiz-Übersicht anzeigen";
}

protected void run() {
	
	    // Ankündigung, was nun geschehen wird.
	
	    this.append("Übersicht der Notizen im Notizbuch");

	    //BankAdministrationAsync bankVerwaltung = ClientsideSettings.getBankVerwaltung();
}
}