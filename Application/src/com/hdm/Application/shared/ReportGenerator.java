package com.hdm.Application.shared;

import com.hdm.Application.shared.bo.*;
import com.hdm.Application.shared.report.AllFilteredNotes;
import com.hdm.Application.shared.report.AllNotesFromUser;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("report")
public interface ReportGenerator extends RemoteService {

	
	public AllNotesFromUser createAllNotesFromUserReport(AppUser user) throws IllegalArgumentException;
	
	
	public void init() throws IllegalArgumentException;
}