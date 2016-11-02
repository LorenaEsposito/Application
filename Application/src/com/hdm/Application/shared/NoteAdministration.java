package com.hdm.Application.shared;


import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import com.hdm.Application.shared.bo.*;

@RemoteServiceRelativePath("noteadministration")
public interface NoteAdministration extends RemoteService {

public void init() throws IllegalArgumentException;

public void createUser(User u) throws IllegalArgumentException;

public void editUser(User u) throws IllegalArgumentException;

public void deleteUser(User u) throws IllegalArgumentException;

public void createNotebook(Notebook nb) throws IllegalArgumentException;

public void editNotebook(Notebook nb) throws IllegalArgumentException;

public void deleteNotebook(Notebook nb) throws IllegalArgumentException;

public void createNote(Note n) throws IllegalArgumentException;

public void editNote(Note n) throws IllegalArgumentException;

public void deleteNote(Note n) throws IllegalArgumentException;

public void createPermission(Permission p) throws IllegalArgumentException;

public void editPermission(Permission p) throws IllegalArgumentException;

public void deletePermission(Permission p) throws IllegalArgumentException;

public void createDuedate(DueDate dd) throws IllegalArgumentException;

public void editDuedate(DueDate dd) throws IllegalArgumentException;

public void deleteDuedate(DueDate dd) throws IllegalArgumentException;

}
