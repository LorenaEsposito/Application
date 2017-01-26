package com.hdm.Application.shared;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.bo.Note;
import com.hdm.Application.shared.report.AllFilteredNotes;
import com.hdm.Application.shared.report.AllNotes;
import com.hdm.Application.shared.report.AllNotesFromUser;

/**
 * Das asynchrone Gegenstück des Interface {@link ReportGenerator}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link ReportGenerator}.
 * 
 * @author thies
 */
public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> callback);

	void createAllNotesFromUserReport(AppUser user, AsyncCallback<AllNotesFromUser> callback);

	void createAllFilteredNotesReportED(Date erstellungsDatum, AsyncCallback<AllFilteredNotes> callback);

	void createAllFilteredNotesReportDD(Date date, AsyncCallback<AllFilteredNotes> callback);

	void createAllFilteredNotesReport(String notebook, AsyncCallback<AllNotes> callback);

	void createAllNotesReport(AsyncCallback<AllNotes> callback);

	void createAllFilteredNotesLEB(AppUser user, AsyncCallback<AllNotesFromUser> callback);
	
	void createAllFilteredNotesBB(AppUser user, AsyncCallback<AllNotesFromUser> callback);
	
	void createAllFilteredNotesLB(AppUser user, AsyncCallback<AllNotesFromUser> callback);
	
	void  createOwnNotesFromUserReport(AppUser user, AsyncCallback<AllNotesFromUser> callback);
	
	void findByBetweenCreationDate(Date von, Date bis, AsyncCallback<AllFilteredNotes> callback);

	void findByTitle(String nTitle, AsyncCallback<AllNotes> callback);

	void findByBetweenDueDate(Date von, Date bis,	AsyncCallback<AllFilteredNotes> callback);

	void findByBetweenModiDate(Date von, Date bis,
			AsyncCallback<AllFilteredNotes> callback);
}