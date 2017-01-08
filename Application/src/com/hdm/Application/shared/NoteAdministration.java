package com.hdm.Application.shared;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.hdm.Application.shared.bo.*;

@RemoteServiceRelativePath("noteadministration")
public interface NoteAdministration extends RemoteService {

	public void init() throws IllegalArgumentException;

	public AppUser getCurrentUser() throws IllegalArgumentException;

	public AppUser getUserByGoogleID(String name) throws IllegalArgumentException;
	
	public AppUser getUserByID(int userID) throws IllegalArgumentException;

	public void createUser(AppUser u) throws IllegalArgumentException;

	public void editUser(AppUser u) throws IllegalArgumentException;

	public void deleteUser(AppUser u) throws IllegalArgumentException;

	public Notebook createNotebook(Notebook nb) throws IllegalArgumentException;

	public void editNotebook(Notebook nb) throws IllegalArgumentException;

	public void deleteNotebook(Notebook nb) throws IllegalArgumentException;

	public Note createNote(Note n) throws IllegalArgumentException;

	public Note editNote(Note n) throws IllegalArgumentException;

	public void deleteNote(Note n) throws IllegalArgumentException;

	public void createPermissions(ArrayList<Permission> p) throws IllegalArgumentException;

	public void editPermission(Permission p) throws IllegalArgumentException;

	public void deletePermission(Permission p) throws IllegalArgumentException;

	public void createDuedate(DueDate dd) throws IllegalArgumentException;

	public void editDuedate(DueDate dd) throws IllegalArgumentException;

	public void deleteDuedate(DueDate dd) throws IllegalArgumentException;

	public ArrayList<AppUser> searchForUser(String userName) throws IllegalArgumentException;

	public ArrayList<Notebook> searchForNotebook(String title) throws IllegalArgumentException;

	public ArrayList<Note> searchForNote(String title) throws IllegalArgumentException;

	public ArrayList<Note> searchForNoteByDD(Date duedate) throws IllegalArgumentException;
	
	public Notebook getNotebookByID(int nbID) throws IllegalArgumentException;

	public ArrayList<Notebook> getNotebooksOfUser(AppUser user) throws IllegalArgumentException;

	public ArrayList<Note> getNotesOfNotebook(String nbTitle, AppUser u) throws IllegalArgumentException;

	public ArrayList<Permission> getOwnedNotebookPermissions(AppUser user) throws IllegalArgumentException;
	
	public ArrayList<Notebook> getOwnedNotebooks(AppUser user) throws IllegalArgumentException;
	
	public ArrayList<Permission> getPermissions(int nbID, int nID) throws IllegalArgumentException;

	public AppUser searchUserByGoogleID(String googleID) throws IllegalArgumentException;

}