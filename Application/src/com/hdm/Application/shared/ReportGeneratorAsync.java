package com.hdm.Application.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hdm.Application.shared.bo.AppUser;
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

}
