package com.hdm.Application.client;

import com.hdm.Application.client.Update;
//import com.hdm.Application.client.ClientsideSettings;
//import de.hdm.thies.bankProjekt.shared.BankAdministrationAsync;

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