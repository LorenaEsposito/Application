package com.hdm.Application.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hdm.Application.shared.bo.AppUser;
import com.hdm.Application.shared.report.AllFilteredNotes;
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

	void createAllFilteredNotesReportDD(Date dueDate, AsyncCallback<AllFilteredNotes> callback);

	void createAllFilteredNotesReport(String notebook, AsyncCallback<AllFilteredNotes> callback);

}
