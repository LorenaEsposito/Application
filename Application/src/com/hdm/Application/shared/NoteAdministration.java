<<<<<<< HEAD
package com.hdm.Application.shared;
=======
>>>>>>> refs/remotes/origin/Sina

<<<<<<< HEAD
=======
package com.hdm.Application.shared;

>>>>>>> refs/remotes/origin/Sina
import java.util.ArrayList;

<<<<<<< HEAD
=======

>>>>>>> refs/remotes/origin/Sina
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import com.hdm.Application.shared.bo.*;

@RemoteServiceRelativePath("noteadministration")
public interface NoteAdministration extends RemoteService {

public void init() throws IllegalArgumentException;

public void createUser(AppUser u) throws IllegalArgumentException;

public void editUser(AppUser u) throws IllegalArgumentException;

public void deleteUser(AppUser u) throws IllegalArgumentException;

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

<<<<<<< HEAD
public ArrayList<AppUser> searchForUser(String userName) throws IllegalArgumentException;
=======

public ArrayList<User> searchForUser(String userName) throws IllegalArgumentException;
>>>>>>> refs/remotes/origin/Sina

<<<<<<< HEAD
public ArrayList<Notebook> searchForNotebook(String title) throws IllegalArgumentException;

public ArrayList<Note> searchForNote(String title) throws IllegalArgumentException;

public ArrayList<Note> searchForNoteByDD(DueDate duedate) throws IllegalArgumentException;
=======
public ArrayList<Notebook> searchForNotebook(String title) throws IllegalArgumentException;

public ArrayList<Note> searchForNote(String title) throws IllegalArgumentException;

>>>>>>> refs/remotes/origin/Sina

}