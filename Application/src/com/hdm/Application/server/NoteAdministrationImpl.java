package com.hdm.Application.server;

import java.util.ArrayList;
import java.util.Vector;

import com.hdm.Application.server.db.*;
import com.hdm.Application.shared.*;
import com.hdm.Application.shared.bo.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.hdm.Application.shared.NoteAdministration;
import com.hdm.Application.shared.NoteAdministrationAsync;


@SuppressWarnings("serial")
public class NoteAdministrationImpl extends RemoteServiceServlet
    implements NoteAdministration {

	/**
	 * Das Profil des aktuellen Benutzer. Hier hinterlegt, um schnell darauf
	 * zurueckgreifen zu koennen.
	 */
	private User currentUser = null;

	/**
	 * Eindeutige SerialVersion ID. Wird zum Serialisieren der Klasse benoetigt.
	 */
	private static final long serialVersionUID = 1L;
     

  /**
   * Referenz auf das zugehörige Note-Objekt.
   */
  private Note Note = null;
  
  /**
	 * Referenz auf den DatenbankMapper, der Userobjekte mit der Datenbank
	 * abgleicht.
	 */
private UserMapper uMapper = null;

/**
 * Referenz auf den DatenbankMapper, der Noteobjekte mit der Datenbank
 * abgleicht.
 */
//private NoteMapper nMapper;

/**
 * Referenz auf den DatenbankMapper, der Permissionobjekte mit der Datenbank
 * abgleicht.
 */
//private PermissionMapper pMapper;

/**
 * Referenz auf den DatenbankMapper, der Notebookobjekte mit der Datenbank
 * abgleicht.
 */
//private NotebookMapper nbMapper;

/**
 * Referenz auf den DatenbankMapper, der Duedateobjekte mit der Datenbank
 * abgleicht.
 */
//private DueDateMapper ddMapper;

   
  public NoteAdministrationImpl() throws IllegalArgumentException {

  }

public void init() throws IllegalArgumentException {

    this.uMapper = UserMapper.userMapper();
    //this.nMapper = NoteMapper.noteMapper();
    //this.pMapper = PermissionMapper.permissionMapper();
    //this.ddMapper = DueDateMapper.duedateMapper();
    //this.nbMapper = NotebookMapper.notebookMapper();
    
  }

/**
 * Erstellt einen neuen User in der Datenbank. Dazu ruft sie mit dem
 * uebergebenen User den UserMapper auf, der dieses dann ueber eine
 * INSERT-Abfrage in die Datenbank einfuegt.
 * 
 * @author Lorena Esposito
 * @param u
 *            Der User, der in die Datenbank eingefuegt werden soll
 */
@Override
public void createUser(User u) throws IllegalArgumentException{
	this.uMapper.insert(u);
}

/**
 * Aktualisiert die Attribute eines Users in der
 * Datenbank. Dazu ruft sie mit dem uebergebenen User den UserMapper auf,
 * der dieses dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
 * 
 * @author Lorena Esposito
 * @param u
 *            Der User, der aktualisiert werden soll
 */
@Override
public void editUser(User u) throws IllegalArgumentException{
	this.uMapper.edit(u);
}

/**
 * Loescht den uebergebenen User endgueltig aus der Datenbank. Die Methode
 * ruft dazu, alle notwendigen Mapper (Notebook, Note, Permission) 
 * mit dem uebergebenen User auf, um ein
 * vollstaendiges Loeschen aller Userdaten sicherzustellen.
 * 
 * @author Lorena Esposito
 * @param u
 *            Der User, der aus der Datenbank entfernt werden soll
 */
@Override
public void deleteUser(User u) throws IllegalArgumentException {
    this.uMapper.delete(u);
    //this.nbMapper.delete(u);
    //this.nMapper.delete(u);
    //this.pMapper.delete(u);
  }

/**
 * Erstellt ein neues Notebook in der Datenbank. Dazu ruft sie mit dem
 * uebergebenen Notebook den NotebookMapper auf, der dieses dann ueber eine
 * INSERT-Abfrage in die Datenbank einfuegt.
 * 
 * @author Lorena Esposito
 * @param nb
 *            Das Notebook, das in die Datenbank eingefuegt werden soll
 */
@Override
public void createNotebook(Notebook nb) throws IllegalArgumentException {
//this.nbMapper.insert(nb);
}

/**
 * Aktualisiert die Attribute eines Notebooks in der
 * Datenbank. Dazu ruft sie mit dem uebergebenen Notebook den NotebookMapper auf,
 * der dieses dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
 * 
 * @author Lorena Esposito
 * @param nb
 *            Das Notebook, das aktualisiert werden soll
 */
@Override
public void editNotebook(Notebook nb) throws IllegalArgumentException {
//this.nbMapper.edit(nb);
}

/**
 * Loescht das uebergebene Notebook endgueltig aus der Datenbank. Die Methode
 * ruft dazu, alle notwendigen Mapper (Note, Permission) 
 * mit dem uebergebenen Notebook auf, um ein
 * vollstaendiges Loeschen aller Notebookdaten sicherzustellen.
 * 
 * @author Lorena Esposito
 * @param nb
 *            Das Notebook, das aus der Datenbank entfernt werden soll
 */
@Override
public void deleteNotebook(Notebook nb) throws IllegalArgumentException {
//this.nbMapper.delete(nb);
//this.nMapper.delete(nb);
//this.pMapper.delete(nb);
	}

/**
 * Erstellt eine neue Note in der Datenbank. Dazu ruft sie mit der
 * uebergebenen Note den NoteMapper auf, der diese dann ueber eine
 * INSERT-Abfrage in die Datenbank einfuegt.
 * 
 * @author Lorena Esposito
 * @param n
 *            Die Note, die in die Datenbank eingefuegt werden soll
 */
@Override
public void createNote(Note n) throws IllegalArgumentException {
//this.nMapper.insert(n);
}

/**
 * Aktualisiert die Attribute einer Note in der
 * Datenbank. Dazu ruft sie mit der uebergebenen Note den NoteMapper auf,
 * der diese dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
 * 
 * @author Lorena Esposito
 * @param n
 *            Die Note, die aktualisiert werden soll
 */
@Override
public void editNote(Note n) throws IllegalArgumentException {
//this.nMapper.edit(n);
}

/**
 * Loescht die uebergebene Note endgueltig aus der Datenbank. Die Methode
 * ruft dazu, alle notwendigen Mapper (Permission) 
 * mit der uebergebenen Note auf, um ein
 * vollstaendiges Loeschen aller Notedaten sicherzustellen.
 * 
 * @author Lorena Esposito
 * @param n
 *            Die Note, die aus der Datenbank entfernt werden soll
 */
@Override
public void deleteNote(Note n) throws IllegalArgumentException {
    //this.nMapper.delete(n);
	//this.pMapper.delete(n);
  }

/**
 * Erstellt eine neue Permission in der Datenbank. Dazu ruft sie mit der
 * uebergebenen Permission den PermissionMapper auf, der diese dann ueber eine
 * INSERT-Abfrage in die Datenbank einfuegt.
 * 
 * @author Lorena Esposito
 * @param p
 *            Die Permission, die in die Datenbank eingefuegt werden soll
 */
@Override
public void createPermission(Permission p) throws IllegalArgumentException{
	//this.pMapper.insert(p);
}

/**
 * Aktualisiert die Attribute einer Permission in der
 * Datenbank. Dazu ruft sie mit der uebergebenen Permission den PermissionMapper auf,
 * der dieses dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
 * 
 * @author Lorena Esposito
 * @param p
 *            Die Permission, die aktualisiert werden soll
 */
@Override
public void editPermission(Permission p) throws IllegalArgumentException{
//	this.pMapper.edit(p);
}
/**
 * Loescht die uebergebene Permission endgueltig aus der Datenbank.
 * 
 * @author Andra Weirich
 * @param p
 *            Die Permission, die aus der Datenbank entfernt werden soll
 */
@Override
public void deletePermission(Permission p) throws IllegalArgumentException {
//    this.pMapper.delete(p);
  }

/**
 * Erstellt ein neues Duedate in der Datenbank. Dazu ruft sie mit dem
 * uebergebenen Duedate den DuedateMapper auf, der diese dann ueber eine
 * INSERT-Abfrage in die Datenbank einfuegt.
 * 
 * @author Lorena Esposito
 * @param dd
 *            Das Duedate, das in die Datenbank eingefuegt werden soll
 */
@Override
public void createDuedate(DueDate dd) throws IllegalArgumentException{
//	this.ddMapper.insert(dd);
}

/**
 * Aktualisiert die Attribute eines Duedates in der
 * Datenbank. Dazu ruft sie mit dem uebergebenen Duedate den DuedateMapper auf,
 * der dieses dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
 * 
 * @author Lorena Esposito
 * @param dd
 *            Das Duedate, das aktualisiert werden soll
 */
@Override
public void editDuedate(DueDate dd) throws IllegalArgumentException{
//	this.ddMapper.edit(dd);
}
/**
 * Loescht das uebergebene Duedate endgueltig aus der Datenbank.
 * 
 * @author Andra Weirich
 * @param dd
 *            Das Duedate, das aus der Datenbank entfernt werden soll
 */
@Override
public void deleteDuedate(DueDate dd) throws IllegalArgumentException {
//    this.ddMapper.delete(dd);
  }

}



























