package com.hdm.Application.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.hdm.Application.shared.bo.*;

import com.hdm.Application.shared.NoteAdministration;

/**
 * Das asynchrone Gegenstueck des Interface {@link NoteAdministration} fuer
 * RPCs, um die Geschaeftsobjekte zu verwalten. Es wird semiautomatisch durch das
 * Google Plugin erstellt und gepflegt. Daher erfolgt hier keine weitere
 * Dokumentation. Fuer weitere Informationen: siehe das synchrone Interface
 * {@link NoteAdministration}
 * 
 * @author Lorena Esposito
 */

public interface NoteAdministrationAsync {

	public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void getCurrentUser(AsyncCallback<AppUser> callback) throws IllegalArgumentException;

	public void getUserByGoogleID(String name, AsyncCallback<AppUser> callback) throws IllegalArgumentException;
	
	public void createUser(AppUser u, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void editUser(AppUser u, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void deleteUser(AppUser u, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void createNotebook(Notebook nb, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void editNotebook(Notebook nb, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void deleteNotebook(Notebook nb, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void createNote(Note n, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void editNote(Note n, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void deleteNote(Note n, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void createPermission(Permission p, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void editPermission(Permission p, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void deletePermission(Permission p, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void createDuedate(DueDate dd, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void editDuedate(DueDate dd, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void deleteDuedate(DueDate dd, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void searchForUser(String userName, AsyncCallback<ArrayList<AppUser>> callback) throws IllegalArgumentException;
	
	public void searchForNotebook(String title, AsyncCallback<ArrayList<Notebook>> callback) throws IllegalArgumentException;
	
	public void searchForNote(String title, AsyncCallback<ArrayList<Note>> callback) throws IllegalArgumentException;
	
	public void searchForNoteByDD(DueDate duedate, AsyncCallback<ArrayList<Note>> callback) throws IllegalArgumentException;
	
	public void getNotebooksOfUser(AppUser user, AsyncCallback<ArrayList<Notebook>> callback) throws IllegalArgumentException;

	public void getNotesOfNotebook(String nbTitle, AppUser u, AsyncCallback<ArrayList<Note>> callback) throws IllegalArgumentException;

	public void searchUserByGoogleID(String googleID, AsyncCallback<AppUser> callback) throws IllegalArgumentException;
	
}
