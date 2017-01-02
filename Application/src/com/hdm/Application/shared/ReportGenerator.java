package com.hdm.Application.shared;

import com.hdm.Application.shared.bo.*;
import com.hdm.Application.shared.report.AllFilteredNotes;
import com.hdm.Application.shared.report.AllNotesFromUser;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("report")
public interface ReportGenerator extends RemoteService {

	
	public AllNotesFromUser createAllNotesFromUserReport(AppUser user) throws IllegalArgumentException;
	
	public AllFilteredNotes createAllFilteredNotesReportED (Date erstellungsDatum) throws IllegalArgumentException;
	
	public AllFilteredNotes createAllFilteredNotesReportDD (Date dueDate) throws IllegalArgumentException;
	
	public AllFilteredNotes createAllFilteredNotesReport (String notebook) throws IllegalArgumentException;


	
	public void init() throws IllegalArgumentException;
}